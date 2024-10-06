package com.yandex.tm;

import com.yandex.tm.model.*;
import com.yandex.tm.service.Managers;
import com.yandex.tm.service.TaskManager;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager manager = Managers.getDefault();
        //**Создать таски, эпик и сабтаски
        createData(manager);
        //**Распечатать созданные таски, эпик и сабтаски
        printAllTasks(manager.getAllTasks());
        printAllEpics(manager.getAllEpics());
        printAllSubtasks(manager.getAllSubtasks());

        //**Обновить и распечатать таск, сабтаск и эпик новыми значениями
        updTask(manager, 1);
        updEpic(manager, 3);
        updSubtask(manager, 8);

//** Распечатать историю задач
        System.out.println("История задач: ");
        System.out.println(manager.getHistoryMan());

//**Распечатать и удалить эпик, распечатать сабтаски после удаления
        System.out.println();
        System.out.println("Распечатать и удалить эпик, распечатать сабтаски после удаления:");
        System.out.println(manager.getEpic(4));
        manager.deleteOneEpic(4);
        System.out.println(manager.getEpic(4));
        printAllSubtasks(manager.getAllSubtasks());

//** Удалить сабтаск 5, распечатать новый список Сабтасков и Эпик 3
        System.out.println("Удаляем сабтаск 5:");
        manager.deleteOneSubtask(5);
        printAllSubtasks(manager.getAllSubtasks());
        System.out.println("Сабтаски для эпика 3:");
        System.out.println(manager.getSubtasksForEpic(3));

        //** Финальная история задач
        System.out.println("История задач: ");
        System.out.println(manager.getHistoryMan());

    }

    public static void createData(TaskManager manager) {
        manager.createNewTask(new Task("Погреть еду", "Надо погреть еду перед завтраком", Statuses.NEW));
        manager.createNewTask(new Task("Гулять", "Надо выйти подышать воздухом", Statuses.NEW));
        manager.createNewEpic(new Epic("Отпуск", "Приготовиться к отпуску"));
        manager.createNewEpic(new Epic("Переезд", "Подготовиться к переезду"));
        manager.createNewSubtask(new Subtask("Купить билеты", "Купить подешевле билеты на всю семью", Statuses.NEW, 3));
        manager.createNewSubtask(new Subtask("Собрать чемоданы", "Упаковать все чемоданы и сумки", Statuses.NEW, 3));
        manager.createNewSubtask(new Subtask("Пристроить кота", "Пристроить кота к знакомым", Statuses.NEW, 3));
        manager.createNewSubtask(new Subtask("Забронировать гостиницу", "Позвонить в отель, узнать цену, договориться", Statuses.NEW, 3));
        manager.createNewSubtask(new Subtask("Купить коробки", "Купить коробки разных размеров", Statuses.NEW, 4));
    }

    public static void printAllTasks(ArrayList<Task> allEntries) {
        System.out.println();
        System.out.println("Список всех Tasks: ");
        System.out.println(allEntries);
    }

    public static void printAllEpics(ArrayList<Epic> allEntries) {
        System.out.println();
        System.out.println("Список всех Epics: ");
        System.out.println(allEntries);
    }

    public static void printAllSubtasks(ArrayList<Subtask> allEntries) {
        System.out.println();
        System.out.println("Список всех Subtasks: ");
        System.out.println(allEntries);
    }

    public static void updTask(TaskManager manager, int TaskID) {
        Task taskUpdated = new Task ("Охладить еду", "Надо охладить еду перед завтраком", Statuses.IN_PROGRESS, TaskID);
        manager.updateTask(taskUpdated);
        System.out.println();
        System.out.println("Обновленный Task: ");
        System.out.println(manager.getTask(TaskID));
    }

    public static void updEpic(TaskManager manager, int TaskID) {
        ArrayList<Integer> subtaskIDs = new ArrayList<>();
        Epic epicUpdated = new Epic ("Отпуск-2", "Приготовиться к зимнему отпуску", Statuses.NEW, subtaskIDs);
        epicUpdated.setTaskID(TaskID);
        manager.updateEpic(epicUpdated);
        System.out.println();
        System.out.println("Обновленный Epic: ");
        System.out.println(manager.getEpic(TaskID));
    }

    public static void updSubtask(TaskManager manager, int TaskID) {
        Subtask subtaskUpdated = new Subtask("Забронировать другую гостиницу", "Отменить одну бронь, сделать другую", Statuses.IN_PROGRESS, TaskID);
        manager.updateSubtask(subtaskUpdated);
        System.out.println();
        System.out.println("Обновленный Subtask: ");
        System.out.println(manager.getSubtask(TaskID));
    }
}
