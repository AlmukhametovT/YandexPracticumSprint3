package manager;

import task.Task;

import java.util.LinkedList;

public interface HistoryManager {
    public boolean add(Task task);

    public LinkedList<Task> getHistory();

    public String[][] getHistoryWithTimeAndMethodAndTaskId();
}