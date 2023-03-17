package main.task;

import java.time.LocalDateTime;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String title, String description, TaskType type, Epic epic) {
        super(title, description, TaskType.SUB_TASK);
        if (type != TaskType.SUB_TASK)
            System.out.println("Когда создаешь подзадачу тип должен быть SubTask (я за тебя все исправил)");
        if (epic.getType() != TaskType.EPIC) {
            System.out.println("в конструктор переданы некорректные параметры, главная задача должна быть типа Epic");
            return;
        }
        this.epicId = epic.getTaskId();
        epic.getSubTaskIdSet().add(getTaskId());
        updateEpicStatus(epic);
        updateEpicTimer(epic);
    }

    public SubTask(String title, String description, int taskId, TaskStatus status, TaskType type, int epicId) {
        super(title, description, taskId, status, TaskType.SUB_TASK);
        if (type != TaskType.SUB_TASK)
            System.out.println("Когда создаешь подзадачу тип должен быть SubTask (я за тебя все исправил)");
        if (!Task.allTask.containsKey(epicId) || !Task.allTask.get(epicId).getType().equals(TaskType.EPIC)) {
            System.out.println("до создания SUB_TASK нужен EPIC, EPIC по данному id не найден");
            return;
        }
        this.epicId = epicId;
        Epic epic = (Epic) Task.allTask.get(epicId);
        epic.getSubTaskIdSet().add(taskId);
        updateEpicStatus(epic);
        updateEpicTimer(epic);
    }

    public SubTask(String title, String description, int taskId, TaskStatus status, TaskType type, int duration, LocalDateTime startTime, int epicId) {
        super(title, description, taskId, status, TaskType.SUB_TASK, duration, startTime);
        if (type != TaskType.SUB_TASK)
            System.out.println("Когда создаешь подзадачу тип должен быть SubTask (я за тебя все исправил)");
        if (!Task.allTask.containsKey(epicId) || !Task.allTask.get(epicId).getType().equals(TaskType.EPIC)) {
            System.out.println("до создания SUB_TASK нужен EPIC, EPIC по данному id не найден");
            return;
        }
        this.epicId = epicId;
        Epic epic = (Epic) Task.allTask.get(epicId);
        epic.getSubTaskIdSet().add(taskId);
        updateEpicStatus(epic);
        updateEpicTimer(epic);
    }

    public void updateEpicStatus(Epic epic) {
        if (epic.getStatus() == TaskStatus.DONE) {
            if (epic.getSubTaskIdSet().size() == 1) {
                epic.setStatus(TaskStatus.NEW);
            } else epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    public void updateEpicTimer(Epic epic) {
        int epicDuration = 0;
        if (this.startTime == null) {
            epic.setDuration(epic.getDuration() + this.getDuration());
            return;
        }
        LocalDateTime epicStartTime = this.getStartTime();
        LocalDateTime epicEndTime = this.getEndTime();
        for (int subTaskId : epic.getSubTaskIdSet()) {
            if (allTask.get(subTaskId).getStartTime() == null) continue;
            epicDuration = epicDuration + Task.allTask.get(subTaskId).getDuration();
            epicStartTime = (Task.allTask.get(subTaskId).getStartTime().isAfter(epicStartTime)) ?
                    epicStartTime : Task.allTask.get(subTaskId).getStartTime();
            epicEndTime = (Task.allTask.get(subTaskId).getEndTime().isBefore(epicEndTime)) ?
                    epicEndTime : Task.allTask.get(subTaskId).getEndTime();
        }
        epic.setDuration(epicDuration);
        epic.setStartTime(epicStartTime);
        epic.setEndTime(epicEndTime);
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" -> epic=|" + Task.allTask.get(epicId).getTitle() + "/" + epicId + "|");
        return String.valueOf(sb);
    }
}