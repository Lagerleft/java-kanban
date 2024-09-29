import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static int taskGlobalID = 1;
    private Task taskFound;
    private Epic epicFound;
    private Subtask subtaskFound;
    public HashMap<Integer, Task> Tasks = new HashMap<>();
    public HashMap<Integer, Epic> Epics = new HashMap<>();
    public HashMap<Integer, Subtask> Subtasks = new HashMap<>();

    //**Создание задач (Task, Epic, Subtask) в менеджере
    public void createNewTask(String taskName, String description, Statuses status) {
        Tasks.put(getTaskGlobalID(), new Task(taskName, description, status));
        increaseTaskGlobalID();
    }

    public void createNewEpic(String taskName, String description, Statuses status) {
        Epics.put(getTaskGlobalID(), new Epic(taskName, description, status));
        increaseTaskGlobalID();
    }

    public void createNewSubtask(String taskName, String description, Statuses status, int epicID) {
        Subtasks.put(getTaskGlobalID(), new Subtask(taskName, description, status, epicID));
        int ID = getTaskGlobalID();
        increaseTaskGlobalID();
        Epic tempEpic = Epics.get(epicID);
        tempEpic.addSubtaskID(ID);
        updateEpicByGlobalID(epicID, tempEpic);
        checkAndSetEpicStatus(epicID);
    }

    //**Получение задач (Task, Epic, Subtask) по taskGlobalID в менеджере
    public Task getTaskByGlobalID(int taskGlobalID) {
        taskFound = new Task("", "", Statuses.NEW);
        for (Integer ID : Tasks.keySet()) {
            if (taskGlobalID == ID) {
                setTaskFound(Tasks.get(ID));
                break;
            }
        }
        return getTaskFound();
    }

    public Epic getEpicByGlobalID(int taskGlobalID) {
        epicFound = new Epic("", "", Statuses.NEW);
        for (Integer ID : Epics.keySet()) {
            if (taskGlobalID == ID) {
                setEpicFound(Epics.get(ID));
                break;
            }
        }
        return getEpicFound();
    }

    public Subtask getSubtaskByGlobalID(int taskGlobalID) {
        subtaskFound = new Subtask("", "", Statuses.NEW, 0);
        for (Integer ID : Subtasks.keySet()) {
            if (taskGlobalID == ID) {
                setSubtaskFound(Subtasks.get(ID));
                break;
            }
        }
        return getSubtaskFound();
    }

    //**Обновление задач (Tasks, Epics, Subtasks) по taskGlobalID
    public void updateTaskByGlobalID(int taskGlobalID, Task newTask) {
        for (Integer ID : Tasks.keySet()) {
            if (taskGlobalID == ID) {
                Tasks.put(ID, newTask);
                break;
            }
        }
    }

    public void updateEpicByGlobalID(int taskGlobalID, Epic newEpic) {
        for (Integer ID : Epics.keySet()) {
            if (taskGlobalID == ID) {
                Epics.put(ID, newEpic);
                checkAndSetEpicStatus(taskGlobalID);
                break;
            }
        }
    }

    public void updateSubtaskByGlobalID(int taskGlobalID, Subtask newSubtask) {
        for (Integer ID : Subtasks.keySet()) {
            if (taskGlobalID == ID) {
                Subtasks.put(ID, newSubtask);
                checkAndSetEpicStatus(newSubtask.getEpicID());
                break;
            }
        }
    }

    //**Получение списка всех задач
    public HashMap<Integer, Task> getTasks() {
        return Tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return Epics;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return Subtasks;
    }

    //**Удаление всех задач
    public void deleteAllTasks() {
        Tasks.clear();
    }

    public void deleteAllEpics() {
        Epics.clear();
        Subtasks.clear();
    }

    public void deleteAllSubtasks() {
        Subtasks.clear();
        for (Integer epicID : Epics.keySet()) { //**Удаляем сабтаски из всех эпиков
            deleteAllSubtasksFromOneEpic(epicID);
            checkAndSetEpicStatus(epicID);
        }
    }

    //**Удаление задач (Tasks, Epics, Subtasks) по идентификатору
    public void deleteOneTask(int taskID) {
        Tasks.remove(taskID);
    }

    public void deleteOneEpic(int epicID) {
        Epics.remove(epicID);
        deleteEpicAssignmentInSubtask(epicID);
    }

    public void deleteOneSubtask(int subtaskID) {
        Subtasks.remove(subtaskID);
        for (Integer epicID : Epics.keySet()) {
            Epic tempEpic = Epics.get(epicID);
            ArrayList<Integer> tempSubtaskList = tempEpic.getSubtaskIDs();
            for (int subtask : tempSubtaskList) {
                if(subtask == subtaskID) {
                    tempSubtaskList.remove(subtask);
                }
            }
            tempEpic.setSubtaskIDsList(tempSubtaskList);
            Epics.put(epicID, tempEpic);
            checkAndSetEpicStatus(epicID);
        }
    }

//**Получение списка всех подзадач определенного эпика

    public HashMap<Integer, Subtask> getSubtasksForEpic (int epicID) {
        HashMap<Integer, Subtask> specificSubtasks = new HashMap<>();
        for (Integer key : Subtasks.keySet()) {
            Subtask tempSubtask = Subtasks.get(key);
            if (tempSubtask.getEpicID() == epicID) {
                specificSubtasks.put(key, tempSubtask);
            }
        }
        return specificSubtasks;
    }

//**Вспомогательные методы

    public void deleteEpicAssignmentInSubtask(int epicID) {
        for (Integer key : Subtasks.keySet()) {
            Subtask tempSubtask = Subtasks.get(key);
            if (tempSubtask.getEpicID() == epicID) {
                Subtasks.remove(key);
            }
        }
    }
    public void deleteAllSubtasksFromOneEpic(int epicID) {
        Epic tempEpic = Epics.get(epicID);
        tempEpic.clearSubtasks();
        Epics.put(epicID, tempEpic);
    }

    public void checkAndSetEpicStatus(int epicID) {
        Epic tempEpic = Epics.get(epicID);
        ArrayList<Integer> tempEpicSubtaskList = tempEpic.getSubtaskIDs();
        int checkAllNew = 1;
        int checkAllDone = 1;

        if(!tempEpicSubtaskList.isEmpty()) {
        for (int i = 0; i < tempEpicSubtaskList.size(); i++) {
            int st1 = tempEpicSubtaskList.get(i);
            Subtask tempSubtask = Subtasks.get(st1);
                if (tempSubtask.getStatus() != Statuses.NEW) {
                    checkAllNew = 0;
                }
                if (tempSubtask.getStatus() != Statuses.DONE) {
                    checkAllDone = 0;
                }
            }
        }
        if(checkAllNew == 1) {
            tempEpic.setStatus(Statuses.NEW);
        } else if (checkAllDone == 1) {
            tempEpic.setStatus(Statuses.DONE);
        } else {
            tempEpic.setStatus(Statuses.IN_PROGRESS);
        }
    }


//**Сеттеры и геттеры для переменных TaskManager

public static int getTaskGlobalID() {
    return taskGlobalID;
}

public static void increaseTaskGlobalID() {
    taskGlobalID++;
}

public void setTaskFound(Task taskFound) {
    this.taskFound = taskFound;
}

public Task getTaskFound() {
    return taskFound;
}

public void setEpicFound(Epic epicFound) {
    this.epicFound = epicFound;
}

public Epic getEpicFound() {
    return epicFound;
}

public void setSubtaskFound(Subtask subtaskFound) {
    this.subtaskFound = subtaskFound;
}

public Subtask getSubtaskFound() {
    return subtaskFound;
}
}
