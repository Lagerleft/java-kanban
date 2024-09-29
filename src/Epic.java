import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subtaskIDs;

    //**Создание объекта Epic
    public Epic(String taskName, String description, Statuses status) {
        super(taskName, description, status);
        this.subtaskIDs = new ArrayList<>();
    }

//**Переопределения методов

    @Override
    public String toString() {
        return "Epic{" +
                "epicName='" + super.getTaskName() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                ", status=" + super.getStatus() +
                ", subtasks=" + getSubtaskIDs() +
                '}';
    }

//**Сеттеры и геттеры по переменным Epic

    public void addSubtaskID(int taskID) {
        this.subtaskIDs.add(taskID);
    }

    public void setSubtaskIDsList(ArrayList<Integer> newSubtasksList) {
        this.subtaskIDs = newSubtasksList;
    }

    public ArrayList<Integer> getSubtaskIDs() {
        return this.subtaskIDs;
    }

    public void clearSubtasks() {
        this.subtaskIDs.clear();
    }

}