package task;

import java.util.HashMap;
import java.util.Map;

public class Task {
    public static int lastTaskId = 1_000_000; // подготовленное значение для ID следующей задачи
    public static Map<Integer, Task> allTask = new HashMap<>(); // хранилище всех задач
    protected String title;
    protected String description;
    private int taskId;
    protected TaskStatus status;
    protected TaskType type;

    public Task(String title, String description, TaskType type) {
        this.title = title;
        this.description = description;
        this.taskId = lastTaskId;
        lastTaskId++;
        this.status = TaskStatus.NEW;
        this.type = type;
        allTask.put(this.getTaskId(), this);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTaskId() {
        return taskId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("task {" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskId=" + taskId +
                ", status=" + status +
                ", type=" + type + '}');
        return String.valueOf(sb);
    }
}