package com.yandex.tm.service;

import com.yandex.tm.model.Statuses;
import com.yandex.tm.model.Task;
import com.yandex.tm.model.Subtask;
import com.yandex.tm.model.Epic;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    @Test //1. Экземпляры Task равны друг другу, если равен их ID
    public void equalTasksIfEqualIDs() {
        TaskManager tManager = Managers.getDefault();
        Task task1 = new Task("Test1name", "Test1Description", Statuses.NEW, 0);
        tManager.createNewTask(new Task("Test1name", "Test1Description", Statuses.NEW));
        Task task2 = tManager.getTask(1);
        task1.setTaskID(task2.getTaskID());

        assertEquals(task1, task2, "Задачи не равны!");

    }

    @Test //2. Наследники Task равны друг другу, если равен их ID
    public void equalChildrenIfEqualIDs() {
        TaskManager tManager = Managers.getDefault();
        Epic e1 = new Epic ("TestEpic Name", "TestEpic Description");
        tManager.createNewEpic(new Epic("TestEpic Name", "TestEpic Description"));
        Epic e2 = tManager.getEpic(1);
        e1.setTaskID(e2.getTaskID());

        Subtask st1 = new Subtask("TestSubtask Name", "TestSubtask Description", Statuses.NEW, e2.getTaskID());
        tManager.createNewSubtask(new Subtask("TestSubtask Name", "TestSubtask Description", Statuses.NEW, e2.getTaskID()));
        Subtask st2 = tManager.getSubtask(2);
        st1.setTaskID(st2.getTaskID());


        assertEquals(st1, st2, "Подзадачи не равны!");
        assertEquals(e1, e2, "Эпики не равны!");

    }

    @Test //3. Эпик нельзя добавить в себя самого в виде подзадачи. Также 6. InMemoryTaskManager добавляет задачи разного типа и может найти их по ID
    public void epicCannotBeAddedToItself() {
        TaskManager tManager = Managers.getDefault();
        tManager.createNewEpic(new Epic("TestEpic Name", "TestEpic Description"));
        Epic e1 = tManager.getEpic(1);
        e1.addSubtaskID(e1.getTaskID());
        Subtask subTest = tManager.getSubtask(e1.getTaskID());

        assertNull(subTest, "Подзадачи не равны!");
    }

    @Test //4. Сабтаск нельзя сделать своим же эпиком
    public void subtaskCannotBecomeOwnEpic() {
//**Структура кода не позволяет проверить это условие
    }

    @Test
    void checkForIDConflicts() { //7. Задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;
        TaskManager tManager = Managers.getDefault();

        Task task1 = new Task("Test7name", "Test7Description", Statuses.NEW, 1);
        tManager.tasksDirectAdd(1, task1);
        tManager.createNewTask(new Task("Test7name2", "Test7Description2", Statuses.NEW));
        Task task2 = tManager.getTask(2);

        assertEquals(2, task2.getTaskID(), "Прямое добавление задачи конфликтует с автоматическим!");


    }

    @Test
    void taskKeepsTheSameAfterAddition() { //8. Неизменность задачи по всем полям после добавления в менеджер
        TaskManager tManager = Managers.getDefault();
        tManager.createNewTask(new Task("Test1name", "Test1Description", Statuses.NEW));
        Task task = tManager.getTask(1);

        assertEquals("Test1name", task.getTaskName(), "Имя задачи не сохранилось!");
        assertEquals("Test1Description", task.getDescription() , "Описание задачи не сохранилось!");
        assertEquals(1, task.getTaskID(), "ID задачи не сохранился!");
        assertEquals(Statuses.NEW, task.getStatus(), "Статус задачи не сохранился!");
    }

    @Test
    void historyManagerKeepsData() { //9. Задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
        TaskManager tManager = Managers.getDefault();

        tManager.createNewTask(new Task("Test9name", "Test9Description", Statuses.NEW));
        Task task = tManager.getTask(1);
        tManager.deleteOneTask(1);
        ArrayList<Task> historyTest = tManager.getHistoryMan();
        Task taskTest = historyTest.get(0);

        assertEquals("Test9name", taskTest.getTaskName(), "Имя задачи не сохранилось в истории!");
        assertEquals("Test9Description", taskTest.getDescription() , "Описание задачи не сохранилось в истории!");
        assertEquals(1, taskTest.getTaskID(), "ID задачи не сохранился в истории!");
        assertEquals(Statuses.NEW, taskTest.getStatus(), "Статус не сохранился в истории!");

    }

}