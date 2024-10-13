package com.yandex.tm.service;

import com.yandex.tm.model.Statuses;
import com.yandex.tm.model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    @Test
    void historyManagerKeepsData() { //9. Задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
        TaskManager tManager = Managers.getDefault();

        tManager.createNewTask(new Task("Test9name", "Test9Description", Statuses.NEW));
        tManager.deleteOneTask(1);
        List<Task> historyTest = tManager.getHistory();
        Task taskTest = historyTest.getFirst();

        assertEquals("Test9name", taskTest.getTaskName(), "Имя задачи не сохранилось в истории!");
        assertEquals("Test9Description", taskTest.getDescription() , "Описание задачи не сохранилось в истории!");
        assertEquals(1, taskTest.getTaskID(), "ID задачи не сохранился в истории!");
        assertEquals(Statuses.NEW, taskTest.getStatus(), "Статус не сохранился в истории!");

    }
}