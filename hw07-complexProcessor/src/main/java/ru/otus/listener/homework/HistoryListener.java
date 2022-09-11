package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private Map<Long, Message> storage = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        var historyMsg = msg.toBuilder().build();
        storage.put(historyMsg.getId(), historyMsg);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(storage.get(id));
    }
}
