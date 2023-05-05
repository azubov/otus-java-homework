package ru.otus.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.otus.domain.Message;

public interface MessageRepository extends ReactiveCrudRepository<Message, Long> {

    @Query("select * from message where " +
            "CASE " +
                "WHEN :room_id != '1408' THEN room_id = :room_id " +
                "WHEN :room_id = '1408' THEN room_id != '1408' " +
            "END " +
            "order by id")
    Flux<Message> findByRoomId(@Param("roomId") String roomId);
}
