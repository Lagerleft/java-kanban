package com.yandex.tm.model;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subtaskIDs = new ArrayList<>();

    //**Создание объекта com.yandex.app.model.Epic
    public Epic(String taskName, String description) {
        super(taskName, description, Statuses.NEW);
    }

    public Epic(String taskName, String description, Statuses status, ArrayList<Integer> subtaskIDs) {
        super(taskName, description, Statuses.NEW);
        this.subtaskIDs = subtaskIDs;
    }

//**Переопределения методов

    @Override
    public String toString() {
        return "Epic{" +
                "ID= " + super.getTaskID() +
                ", epicName='" + super.getTaskName() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                ", status=" + super.getStatus() +
                ", subtasks=" + getSubtaskIDs() +
                '}';
    }

//**Сеттеры и геттеры по переменным com.yandex.app.model.Epic

    public void addSubtaskID(int etaskID) {
        this.subtaskIDs.add(etaskID);
    }

    public void deleteSubtaskID(int taskID) {
        this.subtaskIDs.remove((Integer) taskID);
    }

    public ArrayList<Integer> getSubtaskIDs() {
        return this.subtaskIDs;
    }

    public void clearSubtasks() {
        this.subtaskIDs.clear();
    }

    public void setSubtaskIDs(ArrayList<Integer> subtaskIDs) {
        this.subtaskIDs = subtaskIDs;
    }

}