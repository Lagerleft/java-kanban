package com.yandex.tm.service;

import com.yandex.tm.model.Epic;
import com.yandex.tm.model.Subtask;
import com.yandex.tm.model.Task;

import java.util.ArrayList;

public interface TaskManager {
    //**Создание задач (com.yandex.app.model.Task, com.yandex.app.model.Epic, com.yandex.app.model.Subtask) в менеджере
    void createNewTask(Task task);
    void createNewEpic(Epic epic);
    void createNewSubtask(Subtask subtask);
    void tasksDirectAdd(Integer key, Task value);

    //**Получение задач (tasks, epics, subtasks) по taskGlobalID в менеджере
    Task getTask(int taskID);
    Epic getEpic(int taskID);
    Subtask getSubtask(int taskID);

    //**Обновление задач (tasks, epics, subtasks) по taskGlobalID
    void updateTask(Task newTask);
    void updateEpic(Epic newEpic);
    void updateSubtask(Subtask newSubtask);

    //**Получение списка всех задач
    ArrayList<Task> getAllTasks();
    ArrayList<Epic> getAllEpics();
    ArrayList<Subtask> getAllSubtasks();
    ArrayList<Subtask> getSubtasksForEpic(int epicID);

    //**Удаление всех задач
    void deleteAllTasks();
    void deleteAllEpics();
    void deleteAllSubtasks();

    //**Удаление задач (tasks, epics, subtasks) по идентификатору
    void deleteOneTask(int taskID);
    void deleteOneEpic(int epicID);
    void deleteOneSubtask(int subtaskID);

    //**История задач
    ArrayList<Task> getHistoryMan ();
}
