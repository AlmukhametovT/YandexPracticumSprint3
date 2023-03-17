package manager;

import task.Task;
import task.TaskType;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {
    protected String[][] historyWithTimeAndMethodAndTaskId;
    protected LinkedList<Task> history;
    public final int HISTORY_SIZE = 10;

    public InMemoryHistoryManager() {
        this.historyWithTimeAndMethodAndTaskId = new String[3][HISTORY_SIZE];
        this.history = new LinkedList<>();
    }

    @Override
    public boolean add(Task task) {
        if (!Task.allTask.containsValue(task)) return false;
        this.setHistory(task, "");
        return true;
    }

    @Override
    public LinkedList<Task> getHistory() {
        return this.history;
    }

    @Override
    public String[][] getHistoryWithTimeAndMethodAndTaskId() {
        return this.historyWithTimeAndMethodAndTaskId;
    }

    public void setHistoryWithTimeAndMethodAndTaskId(int taskId, String event) {
        for (int i = HISTORY_SIZE - 1; i > 0; i--) {
            historyWithTimeAndMethodAndTaskId[0][i] = historyWithTimeAndMethodAndTaskId[0][i - 1];
            historyWithTimeAndMethodAndTaskId[1][i] = historyWithTimeAndMethodAndTaskId[1][i - 1];
            historyWithTimeAndMethodAndTaskId[2][i] = historyWithTimeAndMethodAndTaskId[2][i - 1];
        }
        historyWithTimeAndMethodAndTaskId[0][0] = String.valueOf(LocalDateTime.now());
        historyWithTimeAndMethodAndTaskId[1][0] = event;
        historyWithTimeAndMethodAndTaskId[2][0] = taskId + "";
    }

    public void setHistory(Task task, String event) {
        if (history.size() >= HISTORY_SIZE) {
            while (history.size() > HISTORY_SIZE - 1) {
                history.removeLast();
            }
        }
        history.addFirst(task);
        if (event.isEmpty()) {
            if (task.getType() == TaskType.TASK)
                event = "Вызов метода getTaskById() / \"Получение задачи по Id\"";
            if (task.getType() == TaskType.SUB_TASK)
                event = "Вызов метода getSubTaskById() / \"Получение подзадачи по Id\"";
            if (task.getType() == TaskType.EPIC)
                event = "Вызов метода getEpicById() / \"Получение главной задачи по Id\"";
        }
        setHistoryWithTimeAndMethodAndTaskId(task.getTaskId(), event);
    }
}