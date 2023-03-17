import task.*;

import java.util.*;
import java.util.List;

public class TaskManager {
    protected Map<Integer, Task> taskMap;
    protected Map<Integer, Task> subTaskMap;
    protected Map<Integer, Task> epicMap;

    public TaskManager() {
        this.taskMap = new HashMap<>();
        this.subTaskMap = new HashMap<>();
        this.epicMap = new HashMap<>();
    }

    public Map<Integer, Task> getTaskMap() {
        return taskMap;
    }

    public Map<Integer, Task> getSubTaskMap() {
        return subTaskMap;
    }

    public Map<Integer, Task> getEpicMap() {
        return epicMap;
    }

    public static Collection<Task> getTaskListFromMap(Map<Integer, Task> taskMap) {
        return taskMap.values();
    }

    public Collection<Task> getAllTaskList() {
        Map<Integer, Task> allTaskMap = new HashMap<>();
        allTaskMap.putAll(this.getTaskMap());
        allTaskMap.putAll(this.getSubTaskMap());
        allTaskMap.putAll(this.getEpicMap());
        return getTaskListFromMap(allTaskMap);
    }

    public Collection<Task> getTaskList() {
        return getTaskListFromMap(this.getTaskMap());
    }

    public Collection<Task> getSubTaskList() {
        return getTaskListFromMap(this.getSubTaskMap());
    }

    public Collection<Task> getEpicList() {
        return getTaskListFromMap(this.getEpicMap());
    }

    public void deleteAll() {
        Task.allTask.clear();
        this.getTaskMap().clear();
        this.getSubTaskMap().clear();
        this.getEpicMap().clear();
    }

    public void deleteAllTask() {
        for (int taskId : this.getTaskMap().keySet()) {
            Task.allTask.remove(taskId);
        }
        this.getTaskMap().clear();
    }

    public void deleteAllSubTask() {
        for (int subTaskId : this.getSubTaskMap().keySet()) {
            Task.allTask.remove(subTaskId);
        }
        for (Task task : this.getEpicMap().values()) {
            task.setStatus(TaskStatus.NEW);
            ((Epic) task).getSubTaskIdSet().clear();
        }
        this.getSubTaskMap().clear();
    }

    public void deleteAllEpic() {
        this.deleteAllSubTask();
        for (int epicId : this.getEpicMap().keySet()) {
            Task.allTask.remove(epicId);
        }
        this.getEpicMap().clear();
    }

    public Task getById(int taskId) {
        if (this.getTaskMap().containsKey(taskId)) return this.getTaskMap().get(taskId);
        if (this.getSubTaskMap().containsKey(taskId)) return this.getSubTaskMap().get(taskId);
        if (this.getEpicMap().containsKey(taskId)) return this.getEpicMap().get(taskId);
        return null;
    }

    public Task getTaskById(int taskId) {
        if (this.getTaskMap().containsKey(taskId)) return this.getTaskMap().get(taskId);
        return null;
    }

    public Task getSubTaskById(int taskId) {
        if (this.getSubTaskMap().containsKey(taskId)) return this.getSubTaskMap().get(taskId);
        return null;
    }

    public Task getEpicById(int taskId) {
        if (this.getEpicMap().containsKey(taskId)) return this.getEpicMap().get(taskId);
        return null;
    }

    public boolean createTask(Task task) {
        if (task.getType() == TaskType.TASK) {
            this.getTaskMap().put(task.getTaskId(), task);
            return true;
        } else if (task.getType() == TaskType.SUB_TASK) {
            this.getSubTaskMap().put(task.getTaskId(), task);
            if (task.getStatus() == TaskStatus.IN_PROGRESS) {
                Task.allTask.get(((SubTask) task).getEpicId()).setStatus(TaskStatus.IN_PROGRESS);
            }
            if (task.getStatus() == TaskStatus.DONE) {
                boolean checkAllSubTaskStatusDone = true;
                for (int subTaskId : ((Epic) Task.allTask.get(((SubTask) task).getEpicId())).getSubTaskIdSet()) {
                    if (Task.allTask.get(subTaskId).getStatus() == TaskStatus.DONE) continue;
                    checkAllSubTaskStatusDone = false;
                    break;
                }
                if (checkAllSubTaskStatusDone) {
                    Task.allTask.get(((SubTask) task).getEpicId()).setStatus(TaskStatus.DONE);
                } else Task.allTask.get(((SubTask) task).getEpicId()).setStatus(TaskStatus.IN_PROGRESS);
            }
            if (task.getStatus() == TaskStatus.NEW) {
                boolean checkAllSubTaskStatusNew = true;
                for (int subTaskId : ((Epic) Task.allTask.get(((SubTask) task).getEpicId())).getSubTaskIdSet()) {
                    if (Task.allTask.get(subTaskId).getStatus() == TaskStatus.NEW) continue;
                    checkAllSubTaskStatusNew = false;
                    break;
                }
                if (checkAllSubTaskStatusNew) {
                    Task.allTask.get(((SubTask) task).getEpicId()).setStatus(TaskStatus.NEW);
                } else Task.allTask.get(((SubTask) task).getEpicId()).setStatus(TaskStatus.IN_PROGRESS);
            }
            return true;
        } else if (task.getType() == TaskType.EPIC) {
            this.getEpicMap().put(task.getTaskId(), task);
            return true;
        }
        return false;
    }

    public boolean updateTask(Task task) {
        return this.createTask(task);
    }

    public boolean deleteTaskById(int taskId) {
        if (!Task.allTask.containsKey(taskId)) return false;
        if (this.taskMap.containsKey(taskId)) {
            this.taskMap.remove(taskId);
            Task.allTask.remove(taskId);
            return true;
        } else if (this.subTaskMap.containsKey(taskId)) {
            this.subTaskMap.remove(taskId);
            ((Epic) Task.allTask.get(((SubTask) Task.allTask.get(taskId)).getEpicId())).getSubTaskIdSet().remove(taskId);
            if (((Epic) Task.allTask.get(((SubTask) Task.allTask.get(taskId)).getEpicId())).getSubTaskIdSet().isEmpty()) {
                Task.allTask.get(((SubTask) Task.allTask.get(taskId)).getEpicId()).setStatus(TaskStatus.NEW);
                Task.allTask.remove(taskId);
                return true;
            }
            boolean checkDoneEpic = true;
            for (int id : ((Epic) Task.allTask.get(((SubTask) Task.allTask.get(taskId)).getEpicId())).getSubTaskIdSet()) {
                if (Task.allTask.get(id).getStatus() == TaskStatus.DONE) continue;
                checkDoneEpic = false;
                break;
            }
            if (checkDoneEpic) {
                Task.allTask.get(((SubTask) Task.allTask.get(taskId)).getEpicId()).setStatus(TaskStatus.DONE);
            } else Task.allTask.get(((SubTask) Task.allTask.get(taskId)).getEpicId()).setStatus(TaskStatus.IN_PROGRESS);
            boolean checkNewEpic = true;
            for (int id : ((Epic) Task.allTask.get(((SubTask) Task.allTask.get(taskId)).getEpicId())).getSubTaskIdSet()) {
                if (Task.allTask.get(id).getStatus() == TaskStatus.NEW) continue;
                checkNewEpic = false;
                break;
            }
            if (checkNewEpic)
                Task.allTask.get(((SubTask) Task.allTask.get(taskId)).getEpicId()).setStatus(TaskStatus.NEW);
            Task.allTask.remove(taskId);
            return true;
        } else if (this.epicMap.containsKey(taskId)) {
            this.epicMap.remove(taskId);
            Map<Integer, Task> tempTaskMap = Task.allTask;
            for (int id : ((Epic) Task.allTask.get(taskId)).getSubTaskIdSet()) {
                this.epicMap.remove(id);
                tempTaskMap.remove(id);
            }
            Task.allTask = tempTaskMap;
            Task.allTask.remove(taskId);
            return true;
        } else return false;
    }

    public List<Task> getSubTaskListByEpic(Epic epic) {
        if (epic.getType() != TaskType.EPIC) {
            System.out.println("Метод принимает аргументом только Epic");
            return null;
        }
        List<Task> subTaskListByEpic = new ArrayList<>();
        for (int taskId : epic.getSubTaskIdSet()) {
            subTaskListByEpic.add(this.getSubTaskById(taskId));
        }
        return subTaskListByEpic;
    }
}