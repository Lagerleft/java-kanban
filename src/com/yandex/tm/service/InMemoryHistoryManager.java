package com.yandex.tm.service;

import com.yandex.tm.model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private final ArrayList<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (history.size() == 10) {
            history.remove(9);
        }
        history.addFirst(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        if (history.isEmpty()) {
            return null;
        }
        return history;
    }
}
