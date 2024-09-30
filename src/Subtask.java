
public class Subtask extends Task {
    private final int epicID;

    //**Создание объекта Subtask
    public Subtask(String taskName, String description, Statuses status, int epicID) {
        super(taskName, description, status);
        this.epicID = epicID;
    }


//**Переопределения методов

    @Override
    public String toString() {
        return "Subtask{" +
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
