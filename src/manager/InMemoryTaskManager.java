package manager;

import task.*;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected Map<Integer, Task> taskMap;
    protected Map<Integer, Task> subTaskMap;
    protected Map<Integer, Task> epicMap;
    public HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.taskMap = new HashMap<>();
        this.subTaskMap = new HashMap<>();
        this.epicMap = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
    }

    @Override
    public Map<Integer, Task> getTaskMap() {
        return taskMap;
    }

    @Override
    public Map<Integer, Task> getSubTaskMap() {
        return subTaskMap;
    }

    @Override
    public Map<Integer, Task> getEpicMap() {
        return epicMap;
    }

    public static Collection<Task> getTaskListFromMap(Map<Integer, Task> taskMap) {
        return taskMap.values();
    }

    @Override
    public Collection<Task> getAllTaskList() {
        Map<Integer, Task> allTaskMap = new HashMap<>();
        allTaskMap.putAll(this.getTaskMap());
        allTaskMap.putAll(this.getSubTaskMap());
        allTaskMap.putAll(this.getEpicMap());
        return getTaskListFromMap(allTaskMap);
    }

    @Override
    public Collection<Task> getTaskList() {
        return getTaskListFromMap(taskMap);
    }

    @Override
    public Collection<Task> getSubTaskList() {
        return getTaskListFromMap(subTaskMap);
    }

    @Override
    public Collection<Task> getEpicList() {
        return getTaskListFromMap(epicMap);
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    @Override
    public void deleteAll() {
        Task.allTask.clear();
        taskMap.clear();
        subTaskMap.clear();
        epicMap.clear();
    }

    @Override
    public void deleteAllTask() {
        for (int taskId : taskMap.keySet()) {
            Task.allTask.remove(taskId);
        }
        taskMap.clear();
    }

    @Override
    public void deleteAllSubTask() {
        for (int subTaskId : subTaskMap.keySet()) {
            Task.allTask.remove(subTaskId);
        }
        for (Task task : epicMap.values()) {
            task.setStatus(TaskStatus.NEW);
            ((Epic) task).getSubTaskIdSet().clear();
        }
        subTaskMap.clear();
    }

    @Override
    public void deleteAllEpic() {
        deleteAllSubTask();
        for (int epicId : epicMap.keySet()) {
            Task.allTask.remove(epicId);
        }
        epicMap.clear();
    }

    @Override
    public Task getById(int taskId) {
        historyManager.add(Task.allTask.get(taskId));
        if (taskMap.containsKey(taskId)) return taskMap.get(taskId);
        if (subTaskMap.containsKey(taskId)) return subTaskMap.get(taskId);
        if (epicMap.containsKey(taskId)) return epicMap.get(taskId);
        return null;
    }

    @Override
    public Task getTaskById(int taskId) {
        if (taskMap.containsKey(taskId)) {
            historyManager.add(Task.allTask.get(taskId));
            return taskMap.get(taskId);
        }
        return null;
    }

    @Override
    public Task getSubTaskById(int taskId) {
        if (subTaskMap.containsKey(taskId)) {
            historyManager.add(Task.allTask.get(taskId));
            return subTaskMap.get(taskId);
        }
        return null;
    }

    @Override
    public Task getEpicById(int taskId) {
        if (epicMap.containsKey(taskId)) {
            historyManager.add(Task.allTask.get(taskId));
            return epicMap.get(taskId);
        }
        return null;
    }

    @Override
    public boolean createTask(Task task) {
        if (task.getType() == TaskType.TASK) {
            taskMap.put(task.getTaskId(), task);
            return true;
        } else if (task.getType() == TaskType.SUB_TASK) {
            subTaskMap.put(task.getTaskId(), task);
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
            epicMap.put(task.getTaskId(), task);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateTask(Task task) {
        return this.createTask(task);
    }

    @Override
    public boolean deleteAnyTaskById(int taskId) {
        if (!Task.allTask.containsKey(taskId)) return false;
        if (taskMap.containsKey(taskId)) {
            historyManager.remove(taskId);
            taskMap.remove(taskId);
            Task.allTask.remove(taskId);
            return true;
        } else if (subTaskMap.containsKey(taskId)) {
            historyManager.remove(taskId);
            subTaskMap.remove(taskId);
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
        } else if (epicMap.containsKey(taskId)) {
            historyManager.remove(taskId);
            epicMap.remove(taskId);
            Map<Integer, Task> tempTaskMap = Task.allTask;
            for (int id : ((Epic) Task.allTask.get(taskId)).getSubTaskIdSet()) {
                historyManager.remove(id);
                subTaskMap.remove(id);
                tempTaskMap.remove(id);
            }
            Task.allTask = tempTaskMap;
            Task.allTask.remove(taskId);
            return true;
        } else return false;
    }

    @Override
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