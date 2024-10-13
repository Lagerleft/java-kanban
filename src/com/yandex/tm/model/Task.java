package com.yandex.tm.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Task anotherTask = (Task) obj;
        return Objects.equals(taskID, anotherTask.taskID) &&
                Objects.equals(taskName, anotherTask.taskName) &&
                Objects.equals(description, anotherTask.description);

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
