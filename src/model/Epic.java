package model;

import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> subtaskIDs = new ArrayList<>();

    //**Создание объекта model.Epic
    public Epic(String taskName, String description) {
        super(taskName, description, Statuses.NEW);
    }

//**Переопределения методов

    @Override
    public String toString() {
        return "model.Epic{" +
                "epicName='" + super.getTaskName() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                ", status=" + super.getStatus() +
                ", subtasks=" + getSubtaskIDs() +
                '}';
    }

//**Сеттеры и геттеры по переменным model.Epic

    public void addSubtaskID(int taskID) {
        this.subtaskIDs.add(taskID);
    }

    public ArrayList<Integer> getSubtaskIDs() {
        return this.subtaskIDs;
    }

    public void clearSubtasks() {
        this.subtaskIDs.clear();
    }

}