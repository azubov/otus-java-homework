package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

public class CustomerService {

    private static final Comparator<Customer> ORDER_BY_SCORE_ASC = Comparator.comparingLong(Customer::getScores);
    private final Map<Customer, String> map = new TreeMap<>(ORDER_BY_SCORE_ASC);

    public Map.Entry<Customer, String> getSmallest() {
        return map.entrySet().stream().findFirst().map(this::copy).orElse(null);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Predicate<Map.Entry<Customer, String>> higherScore = e -> customer != null && e.getKey().getScores() > customer.getScores();
        return map.entrySet().stream().filter(higherScore).findFirst().map(this::copy).orElse(null);
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> copy(Map.Entry<Customer, String> e) {
        return Map.entry(new Customer(e.getKey()), e.getValue());
    }
}
