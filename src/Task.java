public class Task {

    private String taskName;
    private String description;
    private Statuses status;

    //**Создание объекта Task
    public Task(String taskName, String description, Statuses status) {
        this.taskName = taskName;
        this.description = description;
        this.status = status;
    }

    //**Переопределения методов
    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

//**Геттеры по переменным Task

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
}
