package task;

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
        if (epic.getStatus() == TaskStatus.DONE) {
            if (epic.getSubTaskIdSet().size() == 1) {
                epic.setStatus(TaskStatus.NEW);
            } else epic.setStatus(TaskStatus.IN_PROGRESS);
        }
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
        ((Epic) Task.allTask.get(epicId)).getSubTaskIdSet().add(taskId);
        if (Task.allTask.get(epicId).getStatus() == TaskStatus.DONE) {
            if (((Epic) Task.allTask.get(epicId)).getSubTaskIdSet().size() == 1) {
                Task.allTask.get(epicId).setStatus(TaskStatus.NEW);
            } else Task.allTask.get(epicId).setStatus(TaskStatus.IN_PROGRESS);
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