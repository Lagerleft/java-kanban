package model;

public class Subtask extends Task {
    private final int epicID;

    //**Создание объекта model.Subtask
    public Subtask(String taskName, String description, Statuses status, int epicID) {
        super(taskName, description, status);
        this.epicID = epicID;
    }


//**Переопределения методов

    @Override
    public String toString() {
        return "model.Subtask{" +
                "subtaskName='" + super.getTaskName() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                ", status=" + super.getStatus() +
                ", epics=" + epicID +
                '}';
    }

    //**Сеттеры и геттеры
    public int getEpicID() {
        return epicID;
    }

}
