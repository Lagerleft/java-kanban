package com.yandex.tm.service;

import com.yandex.tm.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int HISTORY_SIZE = 10;
    private static int entrySlot = 0;
    private final List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (entrySlot == HISTORY_SIZE) {
            entrySlot = 0;
        }
        if (history.size() == HISTORY_SIZE) {
            history.remove(entrySlot);
        }
        history.add(entrySlot, task);
        entrySlot++;
    }


    @Override
    public List<Task> getHistory() {
        return List.copyOf(history);
    }

}