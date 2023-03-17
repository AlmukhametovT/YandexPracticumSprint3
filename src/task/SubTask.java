package task;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String title, String description, TaskType type, Epic epic) {
        super(title, description, TaskType.SUB_TASK);
        if (type != TaskType.SUB_TASK)
            System.out.println("Когда создаешь подзадачу тип должен быть SubTask (я за тебя все исправил)");
        if (epic.getType() != TaskType.EPIC) {
            System.out.println("в конструктор переданы некорректные параметры, главная задача должна быть типа task.Epic");
            return;
        }
        this.epicId = epic.getTaskId();
        epic.getSubTaskIdSet().add(this.getTaskId());
        if (epic.getStatus() == TaskStatus.DONE) {
            if (epic.getSubTaskIdSet().size() == 1) {
                epic.setStatus(TaskStatus.NEW);
            } else epic.setStatus(TaskStatus.IN_PROGRESS);
        }
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