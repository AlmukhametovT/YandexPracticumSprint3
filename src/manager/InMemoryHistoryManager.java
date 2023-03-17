package manager;

import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    protected CustomLinkedList connectedList;
    protected Map<Integer, Node> nodeMap; // ключом будет id задачи, просмотр которой требуется удалить, а значением — место просмотра этой задачи в списке history
    protected final static int HISTORY_SIZE = 10;

    public InMemoryHistoryManager() {
        connectedList = new CustomLinkedList();
        nodeMap = new HashMap<>();
    }

    @Override
    public boolean add(Task task) {
        if (!Task.allTask.containsValue(task)) return false;
        if (nodeMap.containsKey(task.getTaskId())) {
            remove(task.getTaskId());
        }
        if (connectedList.getCounter() >= HISTORY_SIZE) {
            while (connectedList.getCounter() > HISTORY_SIZE - 1) {
                connectedList.removeNode(connectedList.getFirst());
            }
        }
        connectedList.linkLast(task);
        nodeMap.put(task.getTaskId(), connectedList.getLast());
        return true;
    }

    @Override
    public List<Task> getHistory() {
        return connectedList.getTasks();
    }

    @Override
    public boolean remove(int id) {
        if (!nodeMap.containsKey(id)) return false;
        connectedList.removeNode(nodeMap.get(id));
        nodeMap.remove(id);
        return true;
    }

    @Override
    public void printHistory() {
        System.out.println("Печать истории:");
        for (int i = 0; i < getHistory().size(); i++) {
            System.out.print(i + 1 + "   ");
            System.out.println(getHistory().get(i));
        }
    }
}