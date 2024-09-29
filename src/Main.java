public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager manager = new TaskManager();

//**Создать таски, эпик и сабтаски
        manager.createNewTask("Погреть еду", "Надо погреть еду перед завтраком", Statuses.NEW);
        manager.createNewTask("Гулять", "Надо выйти подышать воздухом", Statuses.NEW);
        manager.createNewEpic("Отпуск", "Приготовиться к отпуску", Statuses.NEW);
        manager.createNewEpic("Переезд", "Подготовиться к переезду", Statuses.NEW);
        manager.createNewSubtask("Купить билеты", "Купить подешевле билеты на всю семью", Statuses.NEW, 3);
        manager.createNewSubtask("Собрать чемоданы", "Упаковать все чемоданы и сумки", Statuses.NEW, 3);
        manager.createNewSubtask("Пристроить кота", "Пристроить кота к знакомым", Statuses.NEW, 3);
        manager.createNewSubtask("Забронировать гостиницу", "Позвонить в отель, узнать цену, договориться", Statuses.NEW, 3);
        manager.createNewSubtask("Купить коробки", "Купить коробки разных размеров", Statuses.IN_PROGRESS, 4);

        //**Распечатать созданные таски, эпик и сабтаски
        System.out.println();
        System.out.println("Список всех Tasks: ");
        System.out.println(manager.getTasks());
        System.out.println();
        System.out.println("Список всех Epics: ");
        System.out.println(manager.getEpics());
        System.out.println();
        System.out.println("Список всех Subtasks: ");
        System.out.println(manager.getSubtasks());

//**Обновить таск и сабтаск новыми значениями
        manager.updateTaskByGlobalID(2,new Task ("Охладить еду", "Надо охладить еду перед завтраком", Statuses.IN_PROGRESS));
        manager.updateSubtaskByGlobalID(7,new Subtask ("Забронировать другую гостиницу", "Отменить одну бронь, сделать другую", Statuses.IN_PROGRESS, 3));


//**Распечатать измененные таски, эпик и сабтаски поштучно
        System.out.println();
        System.out.println("Обновленный Epic: ");
        System.out.println(manager.getEpicByGlobalID(3));
        System.out.println();
        System.out.println("Обновленный Task: ");
        System.out.println(manager.getTaskByGlobalID(2));
        System.out.println();
        System.out.println("Обновленный Subtask: ");
        System.out.println(manager.getSubtaskByGlobalID(7));

//**Распечатать и удалить эпик, распечатать сабтаски после удаления

        System.out.println(manager.getEpicByGlobalID(4));
        manager.deleteOneEpic(4);
        System.out.println(manager.getEpicByGlobalID(4));
        System.out.println();
        System.out.println("Список всех Subtasks: ");
        System.out.println(manager.getSubtasks());

    }
}
