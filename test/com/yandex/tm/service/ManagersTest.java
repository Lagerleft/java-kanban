package com.yandex.tm.service;

import com.yandex.tm.model.Statuses;
import com.yandex.tm.model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    @Test
    void getDefault() { //**5. Утилитарный класс возвращает рабочие экземпляры менеджеров (Task)
        TaskManager tm = Managers.getDefault();
        tm.createNewTask(new Task("Test1name", "Test1Description", Statuses.NEW));
        Task task = tm.getTask(1);
        assertNotNull(task, "Таск-менеджер не работает");
    }

    @Test
    void getDefaultHistory() { //**5. Утилитарный класс возвращает рабочие экземпляры менеджеров (History)
        HistoryManager hm = Managers.getDefaultHistory();
        Task task1 = new Task("Test5name", "Test5Description", Statuses.NEW, 1);
        hm.add(task1);
        List<Task> historyTest = hm.getHistory();
        assertNotNull(historyTest, "Хистори-менеджер не работает!");
    }
}