package com.yandex.tm;

import com.yandex.tm.model.*;
import com.yandex.tm.service.TaskManager;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager manager = new TaskManager();

//**Создать таски, эпик и сабтаски
        manager.createNewTask(new Task("Погреть еду", "Надо погреть еду перед завтраком", Statuses.NEW));
        manager.createNewTask(new Task("Гулять", "Надо выйти подышать воздухом", Statuses.NEW));
        manager.createNewEpic(new Epic("Отпуск", "Приготовиться к отпуску"));
        manager.createNewEpic(new Epic("Переезд", "Подготовиться к переезду"));
        manager.createNewSubtask(new Subtask("Купить билеты", "Купить подешевле билеты на всю семью", Statuses.NEW, 3));
        manager.createNewSubtask(new Subtask("Собрать чемоданы", "Упаковать все чемоданы и сумки", Statuses.NEW, 3));
        manager.createNewSubtask(new Subtask("Пристроить кота", "Пристроить кота к знакомым", Statuses.NEW, 3));
        manager.createNewSubtask(new Subtask("Забронировать гостиницу", "Позвонить в отель, узнать цену, договориться", Statuses.NEW, 3));
        manager.createNewSubtask(new Subtask("Купить коробки", "Купить коробки разных размеров", Statuses.NEW, 4));

        //**Распечатать созданные таски, эпик и сабтаски
        System.out.println();
        System.out.println("Список всех Tasks: ");
        System.out.println(manager.getAllTasks());
        System.out.println();
        System.out.println("Список всех Epics: ");
        System.out.println(manager.getAllEpics());
        System.out.println();
        System.out.println("Список всех Subtasks: ");
        System.out.println(manager.getAllSubtasks());

//**Обновить таск и сабтаск и эпик новыми значениями
        Task taskUpdated = new Task ("Охладить еду", "Надо охладить еду перед завтраком", Statuses.IN_PROGRESS);
        Subtask subtaskUpdated = new Subtask("Забронировать другую гостиницу", "Отменить одну бронь, сделать другую", Statuses.IN_PROGRESS, 3);
        Epic epicUpdated = new Epic ("Отпуск-2", "Приготовиться к зимнему отпуску");
        taskUpdated.setTaskID(1);
        subtaskUpdated.setTaskID(8);
        epicUpdated.setTaskID(3);
        manager.updateTask(taskUpdated);
        manager.updateSubtask(subtaskUpdated);
        manager.updateEpic(epicUpdated);


//**Распечатать измененные таски, эпик и сабтаски поштучно
        System.out.println();
        System.out.println("Обновленный com.yandex.app.model.Epic: ");
        System.out.println(manager.getEpicByGlobalID(3));
        System.out.println();
        System.out.println("Обновленный com.yandex.app.model.Task: ");
        System.out.println(manager.getTaskByGlobalID(1));
        System.out.println();
        System.out.println("Обновленный com.yandex.app.model.Subtask: ");
        System.out.println(manager.getSubtaskByGlobalID(8));

//**Распечатать и удалить эпик, распечатать сабтаски после удаления
        System.out.println();
        System.out.println("Распечатать и удалить эпик, распечатать сабтаски после удаления:");
        System.out.println(manager.getEpicByGlobalID(4));
        manager.deleteOneEpic(4);
        System.out.println(manager.getEpicByGlobalID(4));
        System.out.println();
        System.out.println("Список всех Subtasks: ");
        System.out.println(manager.getAllSubtasks());

        System.out.println("Удаляем сабтаск:");
        manager.deleteOneSubtask(5);

        System.out.println("Все таски:");
        System.out.println(manager.getAllTasks());
        System.out.println("Все сабтаски:");
        System.out.println(manager.getAllSubtasks());
        System.out.println("Сабтаски для эпика 3:");
        System.out.println(manager.getSubtasksForEpic(3));

    }
}
