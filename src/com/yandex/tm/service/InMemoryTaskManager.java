package com.yandex.tm.service;

import com.yandex.tm.model.Epic;
import com.yandex.tm.model.Statuses;
import com.yandex.tm.model.Subtask;
import com.yandex.tm.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private int taskGlobalID = 1;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyMan = Managers.getDefaultHistory();

    //**Создание задач (com.yandex.app.model.Task, com.yandex.app.model.Epic, com.yandex.app.model.Subtask) в менеджере
    @Override
    public void createNewTask(Task task) {
        int taskID = getGlobalTaskID();
        task.setTaskID(taskID);
        while (tasks.containsKey(task.getTaskID())) { //ключ уже есть в списке
            task.setTaskID(getGlobalTaskID());
        }
        taskID = task.getTaskID();
        tasks.put(taskID, task);
    }

    @Override
    public void createNewEpic(Epic epic) {
        int epicID = getGlobalTaskID();
        epic.setTaskID(epicID);
        while (epics.containsKey(epic.getTaskID())) { //ключ уже есть в списке
            epic.setTaskID(getGlobalTaskID());
        }
        epicID = epic.getTaskID();
        epics.put(epicID, epic);
    }

    @Override
    public void createNewSubtask(Subtask subtask) {
        int subtaskID = getGlobalTaskID();
        subtask.setTaskID(subtaskID);
        while (subtasks.containsKey(subtask.getTaskID())) { //ключ уже есть в списке
            subtask.setTaskID(getGlobalTaskID());
        }
        subtaskID = subtask.getTaskID();
        subtasks.put(subtaskID, subtask);
        Epic tempEpic = epics.get(subtask.getEpicID());
        tempEpic.addSubtaskID(subtaskID);
        checkAndSetEpicStatus(subtask.getEpicID());
    }

    //**Получение задач (tasks, epics, subtasks) по taskGlobalID в менеджере
    @Override
    public Task getTask(int taskID) {
        Task task = tasks.get(taskID);
        if (task != null) {
            Task newTask = new Task(task.getTaskName(), task.getDescription(), task.getStatus());
            newTask.setTaskID(task.getTaskID());
            historyMan.add(newTask);
        }
        return task;
    }

    @Override
    public Epic getEpic(int taskID) {
        Epic epic = epics.get(taskID);
        if (epic != null) {
            Epic newEpic = new Epic(epic.getTaskName(), epic.getDescription());
            newEpic.setTaskID(epic.getTaskID());
            newEpic.setSubtaskIDs(epic.getSubtaskIDs());
            newEpic.setStatus(epic.getStatus());
            historyMan.add(newEpic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtask(int taskID) {
        Subtask subtask = subtasks.get(taskID);
        if (subtask != null) {
            Subtask newSubtask = new Subtask(subtask.getTaskName(), subtask.getDescription(), subtask.getStatus(), subtask.getEpicID());
            newSubtask.setTaskID(subtask.getTaskID());
            historyMan.add(newSubtask);
        }
        return subtask;
    }

    //**Обновление задач (tasks, epics, subtasks) по taskGlobalID
    @Override
    public void updateTask(Task newTask) {
        if (tasks.containsKey(newTask.getTaskID())) {
            tasks.put(newTask.getTaskID(), newTask);
        }
    }

    @Override
    public void updateEpic(Epic newEpic) {
        final Epic oldEpic = epics.get(newEpic.getTaskID());
        if (oldEpic != null) {
            oldEpic.setTaskName(newEpic.getTaskName());
            oldEpic.setDescription(newEpic.getDescription());
        }
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        if (subtasks.containsKey(newSubtask.getTaskID())) {
            subtasks.put(newSubtask.getTaskID(), newSubtask);
            checkAndSetEpicStatus(newSubtask.getEpicID());
        }
    }

    //**Получение списка всех задач
    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<Epic>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<Subtask>(subtasks.values());
    }

    //**Удаление всех задач
    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) { //**Удаляем сабтаски из всех эпиков
            epic.clearSubtasks();
            checkAndSetEpicStatus(epic.getTaskID());
        }
    }

    //**Удаление задач (tasks, epics, subtasks) по идентификатору
    @Override
    public void deleteOneTask(int taskID) {
        tasks.remove(taskID);
    }

    @Override
    public void deleteOneEpic(int epicID) {
        deleteEpicAssignmentInSubtask(epicID);
        epics.remove(epicID);
    }

    @Override
    public void deleteOneSubtask(int subtaskID) {
        int epicID = subtasks.get(subtaskID).getEpicID();
        Epic tempEpic = epics.get(epicID);

        tempEpic.deleteSubtaskID(subtaskID);
        checkAndSetEpicStatus(epicID);
        subtasks.remove(subtaskID); //удалить сабтаск
    }

//**Получение списка всех подзадач определенного эпика

    @Override
    public ArrayList<Subtask> getSubtasksForEpic(int epicID) {
        ArrayList<Subtask> list = new ArrayList<>();
        Epic epic = epics.get(epicID);
        for (Integer sID : epic.getSubtaskIDs()) {
            list.add(subtasks.get(sID));
        }
        return list;
    }

//**Вспомогательные методы

    private void deleteEpicAssignmentInSubtask(int epicID) {
        Epic tempEpic = epics.get(epicID);
        for (Integer id : tempEpic.getSubtaskIDs()) {
            subtasks.remove(id);
        }
    }

    private void checkAndSetEpicStatus(int epicID) {
        Epic tempEpic = epics.get(epicID);
        ArrayList<Integer> tempEpicSubtaskList = tempEpic.getSubtaskIDs();
        boolean checkAllNew = true;
        boolean checkAllDone = true;

        for (int subtaskId : tempEpicSubtaskList) {
            Subtask tempSubtask = subtasks.get(subtaskId);
            if (tempSubtask.getStatus() != Statuses.NEW) {
                checkAllNew = false;
            }
            if (tempSubtask.getStatus() != Statuses.DONE) {
                checkAllDone = false;
            }
        }

        if (checkAllNew) {
            tempEpic.setStatus(Statuses.NEW);
        } else if (checkAllDone) {
            tempEpic.setStatus(Statuses.DONE);
        } else {
            tempEpic.setStatus(Statuses.IN_PROGRESS);
        }
    }

    //**Переопределение equals()


//**Сеттеры и геттеры для переменных com.yandex.app.service.TaskManager

    public int getGlobalTaskID() {
        return taskGlobalID++;
    }

    public ArrayList<Task> getHistoryMan() {
        return historyMan.getHistory();
    }

    public void tasksDirectAdd(Integer key, Task value) {
        tasks.put(key, value);
    }


}


