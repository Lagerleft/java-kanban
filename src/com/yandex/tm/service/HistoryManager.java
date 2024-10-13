package com.yandex.tm.service;

import com.yandex.tm.model.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    List<Task> getHistory();
}
