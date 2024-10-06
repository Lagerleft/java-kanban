package com.yandex.tm.service;
import com.yandex.tm.model.Task;
import java.util.ArrayList;

public interface HistoryManager {

    void add(Task task);
    ArrayList<Task> getHistory();
}
