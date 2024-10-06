package com.yandex.tm.model;

public class Task {

    private String taskName;
    private String description;
    private Statuses status;
    private int taskID;

    //**Создание объекта com.yandex.app.model.Task
    public Task(String taskName, String description, Statuses status) {
        this.taskName = taskName;
        this.description = description;
        this.status = status;
    }

    public Task(String taskName, String description, Statuses status, int taskID) {
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.taskID = taskID;
    }

    //**Переопределения методов
    @Override
    public String toString() {
        return "Task{" +
                "ID= " + getTaskID() +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

//**Геттеры по переменным com.yandex.app.model.Task

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public Statuses getStatus() {
        return status;
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
