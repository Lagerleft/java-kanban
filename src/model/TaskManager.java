package model;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int taskGlobalID = 1;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    //**Создание задач (model.Task, model.Epic, model.Subtask) в менеджере
    public void createNewTask(Task task) {
        tasks.put(getTaskGlobalID(), task);
        increaseTaskGlobalID();
    }

    public void createNewEpic(Epic epic) {
        epics.put(getTaskGlobalID(), epic);
        increaseTaskGlobalID();
    }

    public void createNewSubtask(Subtask subtask) {
        subtasks.put(getTaskGlobalID(), subtask);
        int ID = getTaskGlobalID();
        increaseTaskGlobalID();
        Epic tempEpic = epics.get(subtask.getEpicID());
        tempEpic.addSubtaskID(ID);
        updateEpicByGlobalID(subtask.getEpicID(), tempEpic);
        checkAndSetEpicStatus(subtask.getEpicID());
    }

    //**Получение задач (tasks, epics, subtasks) по taskGlobalID в менеджере
    public Task getTaskByGlobalID(int taskGlobalID) {
        return tasks.get(taskGlobalID);
    }

    public Epic getEpicByGlobalID(int taskGlobalID) {
        return epics.get(taskGlobalID);
    }

    public Subtask getSubtaskByGlobalID(int taskGlobalID) {
        return subtasks.get(taskGlobalID);
    }

    //**Обновление задач (tasks, epics, subtasks) по taskGlobalID
    public void updateTaskByGlobalID(int taskGlobalID, Task newTask) {
        for (Integer ID : tasks.keySet()) {
            if (taskGlobalID == ID) {
                tasks.put(ID, newTask);
                break;
            }
        }
    }

    public void updateEpicByGlobalID(int taskGlobalID, Epic newEpic) {
        for (Integer ID : epics.keySet()) {
            if (taskGlobalID == ID) {
                epics.put(ID, newEpic);
                checkAndSetEpicStatus(taskGlobalID);
                break;
            }
        }
    }

    public void updateSubtaskByGlobalID(int taskGlobalID, Subtask newSubtask) {
        for (Integer ID : subtasks.keySet()) {
            if (taskGlobalID == ID) {
                subtasks.put(ID, newSubtask);
                checkAndSetEpicStatus(newSubtask.getEpicID());
                break;
            }
        }
    }

    //**Получение списка всех задач
    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
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
        for (Integer epicID : epics.keySet()) { //**Удаляем сабтаски из всех эпиков
            deleteAllSubtasksFromOneEpic(epicID);
            checkAndSetEpicStatus(epicID);
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

    public HashMap<Integer, Subtask> getSubtasksForEpic(int epicID) {
        HashMap<Integer, Subtask> specificSubtasks = new HashMap<>();
        for (Integer key : subtasks.keySet()) {
            Subtask tempSubtask = subtasks.get(key);
            if (tempSubtask.getEpicID() == epicID) {
                specificSubtasks.put(key, tempSubtask);
            }
        }
        return specificSubtasks;
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

//**Сеттеры и геттеры для переменных model.TaskManager

    public int getTaskGlobalID() {
        return taskGlobalID;
    }

    public void increaseTaskGlobalID() {
        taskGlobalID++;
    }
}
