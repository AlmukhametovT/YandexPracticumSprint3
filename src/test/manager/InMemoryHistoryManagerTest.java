package test.manager;

import main.manager.HistoryManager;
import main.manager.InMemoryHistoryManager;
import main.task.Task;
import main.task.TaskType;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {
    HistoryManager historyManager;

    @BeforeEach
    public void initTest() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    public void addTest() {
        Task task = new Task("Task", "описание Task", TaskType.TASK);
        historyManager.add(task);
        List<Task> history = historyManager.getHistory();
        assertTrue(history.contains(task));
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    public void emptyHistoryTest() {
        assertNull(historyManager.getHistory());
    }

    @Test
    public void duplicationHistoryTest() {
        Task task = new Task("Task", "описание Task", TaskType.TASK);
        Task task2 = new Task("Task2", "описание Task2", TaskType.TASK);
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task);
        historyManager.add(task2);
        assertTrue(historyManager.getHistory().size() == 2);
    }

    @Test
    public void getHistoryTest() {
        Task task = new Task("Task", "описание Task", TaskType.TASK);
        historyManager.add(task);
        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
    }

    @Test
    public void removeTest() {
        Task task = new Task("Task", "описание Task", TaskType.TASK);
        Task task2 = new Task("Task2", "описание Task2", TaskType.TASK);
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.remove(task.getTaskId());
        assertFalse(historyManager.getHistory().contains(task));
    }

    @Test
    public void removeFirstTest() {
        Task task = new Task("Task", "описание Task", TaskType.TASK);
        Task task2 = new Task("Task2", "описание Task2", TaskType.TASK);
        Task task3 = new Task("Task3", "описание Task3", TaskType.TASK);
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task.getTaskId());
        assertFalse(historyManager.getHistory().contains(task));
    }

    @Test
    public void removeMidTest() {
        Task task = new Task("Task", "описание Task", TaskType.TASK);
        Task task2 = new Task("Task2", "описание Task2", TaskType.TASK);
        Task task3 = new Task("Task3", "описание Task3", TaskType.TASK);
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task2.getTaskId());
        assertFalse(historyManager.getHistory().contains(task2));
    }

    @Test
    public void removeLastTest() {
        Task task = new Task("Task", "описание Task", TaskType.TASK);
        Task task2 = new Task("Task2", "описание Task2", TaskType.TASK);
        Task task3 = new Task("Task3", "описание Task3", TaskType.TASK);
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task3.getTaskId());
        assertFalse(historyManager.getHistory().contains(task3));
    }
}