package manager;

import task.*;

import java.util.*;
import java.util.List;

public interface TaskManager {

    public Map<Integer, Task> getTaskMap();

    public Map<Integer, Task> getSubTaskMap();

    public Map<Integer, Task> getEpicMap();

    public Collection<Task> getAllTaskList();

    public Collection<Task> getTaskList();

    public Collection<Task> getSubTaskList();

    public Collection<Task> getEpicList();

    public HistoryManager getHistoryManager();

    public void deleteAll();

    public void deleteAllTask();

    public void deleteAllSubTask();

    public void deleteAllEpic();

    public Task getById(int taskId);

    public Task getTaskById(int taskId);

    public Task getSubTaskById(int taskId);

    public Task getEpicById(int taskId);

    public boolean createTask(Task task);

    public boolean updateTask(Task task);

    public boolean deleteAnyTaskById(int taskId);

    public List<Task> getSubTaskListByEpic(Epic epic);
}