package com.yandex.tm.service;

import com.yandex.tm.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final int HISTORY_SIZE = 10;
    private final List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (history.size() == HISTORY_SIZE) {
            history.removeLast();
        }
        history.addFirst(task);
    }

    @Override
    public List<Task> getHistory() {
        List<Task> historyList = List.copyOf(history);
        return historyList;
    }
}
