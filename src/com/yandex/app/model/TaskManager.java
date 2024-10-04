package com.yandex.app.model;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int taskGlobalID = 1;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    //**Создание задач (com.yandex.app.model.Task, com.yandex.app.model.Epic, com.yandex.app.model.Subtask) в менеджере
    public void createNewTask(Task task) {
        tasks.put(getTaskGlobalID(), task);
        task.setTaskID(getTaskGlobalID());
        increaseTaskGlobalID();
    }

    public void createNewEpic(Epic epic) {
        epics.put(getTaskGlobalID(), epic);
        epic.setTaskID(getTaskGlobalID());
        increaseTaskGlobalID();
    }

    public void createNewSubtask(Subtask subtask) {
        subtasks.put(getTaskGlobalID(), subtask);
        int ID = getTaskGlobalID();
        subtask.setTaskID(getTaskGlobalID());
        increaseTaskGlobalID();
        Epic tempEpic = epics.get(subtask.getEpicID());
        tempEpic.addSubtaskID(ID);
        checkAndSetEpicStatus(subtask.getEpicID());
    }

    //**Получение задач (tasks, epics, subtasks) по taskGlobalID в менеджере
    public Task getTaskByGlobalID(int taskID) {
        return tasks.get(taskID);
    }

    public Epic getEpicByGlobalID(int taskID) {
        return epics.get(taskID);
    }

    public Subtask getSubtaskByGlobalID(int taskID) {
        return subtasks.get(taskID);
    }

    //**Обновление задач (tasks, epics, subtasks) по taskGlobalID
    public void updateTask(Task newTask) {
        if (tasks.containsKey(newTask.getTaskID())) {
            tasks.put(newTask.getTaskID(), newTask);
        }
    }

    public void updateEpic(Epic newEpic) {
        if (epics.containsKey(newEpic.getTaskID())) {
            epics.put(newEpic.getTaskID(), newEpic);
        }
    }

    public void updateSubtask(Subtask newSubtask) {
        if (subtasks.containsKey(newSubtask.getTaskID())) {
            subtasks.put(newSubtask.getTaskID(), newSubtask);
            checkAndSetEpicStatus(newSubtask.getEpicID());
        }
    }

    //**Получение списка всех задач
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> list = new ArrayList<>();
        list.addAll(tasks.values());
        return list;
    }

    public ArrayList<Task> getAllEpics() {
        ArrayList<Task> list = new ArrayList<>();
        list.addAll(epics.values());
        return list;
    }

    public ArrayList<Task> getAllSubtasks() {
        ArrayList<Task> list = new ArrayList<>();
        list.addAll(subtasks.values());
        return list;
    }

    //**Удаление всех задач
    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) { //**Удаляем сабтаски из всех эпиков
            epic.clearSubtasks();
            checkAndSetEpicStatus(epic.getTaskID());
        }
    }

    //**Удаление задач (tasks, epics, subtasks) по идентификатору
    public void deleteOneTask(int taskID) {
        tasks.remove(taskID);
    }

    public void deleteOneEpic(int epicID) {
        epics.remove(epicID);
        deleteEpicAssignmentInSubtask(epicID);
    }

    public void deleteOneSubtask(int subtaskID) {
        subtasks.remove(subtaskID);
        for (Integer epicID : epics.keySet()) {
            Epic tempEpic = epics.get(epicID);
            ArrayList<Integer> tempSubtaskList = tempEpic.getSubtaskIDs();
            for (int subtask : tempSubtaskList) {
                if (subtask == subtaskID) {
                    tempSubtaskList.remove(subtask);
                }
            }
            epics.put(epicID, tempEpic);
            checkAndSetEpicStatus(epicID);
        }
    }

//**Получение списка всех подзадач определенного эпика

    public ArrayList<Task> getSubtasksForEpic(int epicID) {
        ArrayList<Task> list = new ArrayList<>();
        Epic epic = epics.get(epicID);
        for (Integer sID : epic.getSubtaskIDs()) {
            list.add(subtasks.get(sID));
        }
        return list;
    }


//**Вспомогательные методы

    public void deleteEpicAssignmentInSubtask(int epicID) {
        for (Integer key : subtasks.keySet()) {
            Subtask tempSubtask = subtasks.get(key);
            if (tempSubtask.getEpicID() == epicID) {
                subtasks.remove(key);
            }
        }
    }

    public void deleteAllSubtasksFromOneEpic(int epicID) {
        Epic tempEpic = epics.get(epicID);
        tempEpic.clearSubtasks();
        epics.put(epicID, tempEpic);
    }

    public void checkAndSetEpicStatus(int epicID) {
        Epic tempEpic = epics.get(epicID);
        ArrayList<Integer> tempEpicSubtaskList = tempEpic.getSubtaskIDs();
        int checkAllNew = 1;
        int checkAllDone = 1;

        if (!tempEpicSubtaskList.isEmpty()) {
            for (int i = 0; i < tempEpicSubtaskList.size(); i++) {
                int st1 = tempEpicSubtaskList.get(i);
                Subtask tempSubtask = subtasks.get(st1);
                if (tempSubtask.getStatus() != Statuses.NEW) {
                    checkAllNew = 0;
                }
                if (tempSubtask.getStatus() != Statuses.DONE) {
                    checkAllDone = 0;
                }
            }
        }
        if (checkAllNew == 1) {
            tempEpic.setStatus(Statuses.NEW);
        } else if (checkAllDone == 1) {
            tempEpic.setStatus(Statuses.DONE);
        } else {
            tempEpic.setStatus(Statuses.IN_PROGRESS);
        }
    }

//**Сеттеры и геттеры для переменных com.yandex.app.model.TaskManager

    public int getTaskGlobalID() {
        return taskGlobalID;
    }

    public void increaseTaskGlobalID() {
        taskGlobalID++;
    }
}
