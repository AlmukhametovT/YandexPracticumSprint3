package task;

import java.util.HashSet;
import java.util.Set;

public class Epic extends Task {
    private Set<Integer> subTaskIdSet;

    public Epic(String title, String description, TaskType type) {
        super(title, description, TaskType.EPIC);
        if (type != TaskType.EPIC)
            System.out.println("Когда создаешь главную задачу тип должен быть Epic (я за тебя все исправил)");
        this.subTaskIdSet = new HashSet<>();
    }

    public Set<Integer> getSubTaskIdSet() {
        return subTaskIdSet;
    }

    public void setSubTaskIdSet(Set<Integer> subTaskId) {
        this.subTaskIdSet = subTaskId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" -> subTaskList=");
        if (subTaskIdSet.isEmpty()) {
            sb.append("подзадачи отсутсвуют!!!");
            return String.valueOf(sb);
        }
        for (int subTaskId : subTaskIdSet) {
            sb.append("|");
            sb.append(Task.allTask.get(subTaskId).getTitle());
            sb.append("/");
            sb.append(subTaskId);
            sb.append("|");
        }
        return String.valueOf(sb);
    }
}