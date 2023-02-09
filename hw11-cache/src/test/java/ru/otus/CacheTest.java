package ru.otus;

import org.assertj.core.api.Assertions;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import ru.otus.base.TestContainersConfig;
import ru.otus.cachehw.MyCache;
import ru.otus.core.repository.executor.DbExecutorImpl;
import ru.otus.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.crm.datasource.DriverManagerDataSource;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.CachedDbServiceClientImpl;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.jdbc.mapper.DataTemplateJdbc;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.jdbc.mapper.impl.EntityClassMetaDataImpl;
import ru.otus.jdbc.mapper.impl.EntitySQLMetaDataImpl;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@DisplayName("Кеш должен ")
class CacheTest {

    private static TestContainersConfig.CustomPostgreSQLContainer CONTAINER;
    private final String dbUrl = System.getProperty("app.datasource.demo-db.jdbcUrl");
    private final String dbUserName = System.getProperty("app.datasource.demo-db.username");
    private final String dbPassword = System.getProperty("app.datasource.demo-db.password");

    private MyCache<Long, Client> cache;
    private DBServiceClient service;
    private DBServiceClient cachedService;

    @BeforeAll
    public static void init() {
        CONTAINER = TestContainersConfig.CustomPostgreSQLContainer.getInstance();
        CONTAINER.start();
    }

    @AfterAll
    public static void shutdown() {
        CONTAINER.stop();
    }

    @BeforeEach
    void setUp() {
        var dataSource = new DriverManagerDataSource(dbUrl, dbUserName, dbPassword);
        flywayMigrations(dataSource);
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

        EntityClassMetaData<Client> entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<Client>(dbExecutor, entitySQLMetaDataClient, entityClassMetaDataClient);

        cache = new MyCache<>();
        service = new DbServiceClientImpl(transactionRunner, dataTemplateClient);
        cachedService = new CachedDbServiceClientImpl(service, cache);

        IntStream.range(0, 100).forEach(id -> cachedService.saveClient(new Client("" + id)));
    }

    @AfterEach
    void tearDown() throws SQLException {
        var dataSource = new DriverManagerDataSource(dbUrl, dbUserName, dbPassword);
        try (var connection = dataSource.getConnection()) {
            connection.createStatement().executeUpdate("delete from client");
            connection.commit();
        }
    }

    @DisplayName("отдавать данные в 500x раз быстрее, чем бд")
    @Test
    void cacheIsFaster500xTimesThanDb() {
        var ids = service.findAll().stream().map(Client::getId).toList();

        var readStart = Instant.now();
        var dbClients = getClientsByIds(ids, service);
        var dbTime = Duration.between(readStart, Instant.now());
        var dbTimeIn500xTimes = dbTime.dividedBy(500L);

        var cacheReadStart = Instant.now();
        var cachedClients = getClientsByIds(ids, cachedService);
        var cacheTime = Duration.between(cacheReadStart, Instant.now());

        Assertions.assertThat(cachedClients).hasSameSizeAs(dbClients);
        Assertions.assertThat(cacheTime).isLessThan(dbTime).isLessThan(dbTimeIn500xTimes);
    }

    @DisplayName("очищаться при вызове gc")
    @Test
    void cacheIsClearedAfterGc() {
        Assertions.assertThat(cache.isEmpty()).isFalse();
        //-verbose:gc
        System.gc();

        await().atMost(10, TimeUnit.SECONDS).until(cache::isEmpty);
        Assertions.assertThat(cache.isEmpty()).isTrue();
    }

    private List<Client> getClientsByIds(List<Long> ids, DBServiceClient dbServiceClient) {
        return ids.stream()
                .map(dbServiceClient::getClient)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());
    }

    private void flywayMigrations(DataSource dataSource) {
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db")
                .load();
        flyway.migrate();
    }
}
