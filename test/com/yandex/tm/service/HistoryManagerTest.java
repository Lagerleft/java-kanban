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
        assertEquals("Test9Description", taskTest.getDescription(), "Описание задачи не сохранилось в истории!");
        assertEquals(1, taskTest.getTaskID(), "ID задачи не сохранился в истории!");
        assertEquals(Statuses.NEW, taskTest.getStatus(), "Статус не сохранился в истории!");

    }


    @Test
    void historyManagerLimitWorks() {
        TaskManager tManager = Managers.getDefault();
        for (int i = 1; i < 13; i++) { //**Гененация задач
            tManager.createNewTask(new Task(("TstName" + i), "TstDesc", Statuses.NEW));
        }
        for (int i = 0; i < 12; i++) { //** Чтение задач
            tManager.getTask(i);
        }
        Task task1 = tManager.getHistory().getFirst();
        Task task2 = tManager.getHistory().get(1);

        assertEquals("TstName10", task1.getTaskName(), "Некорректная запись в очередь!");
        assertEquals("TstName11", task2.getTaskName(), "Некорректная запись в очередь!");
    }
}