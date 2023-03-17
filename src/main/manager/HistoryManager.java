package main.manager;

import main.task.Task;

import java.util.List;

public interface HistoryManager {
    public boolean add(Task task);

    public boolean remove(int id);

    public List<Task> getHistory();

    public void printHistory();
}