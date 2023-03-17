package test.manager;

import main.manager.FileBackedTasksManager;
import main.manager.TaskManager;
import main.task.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static main.manager.FileBackedTasksManager.FILE_BACKED_NAME;
import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    @BeforeEach
    public void initTest() {
        super.manager = new FileBackedTasksManager();
        manager.deleteAll();
    }

    @Test
    public void FileBackedTasksManagerSaveTest() {
        // Пустой список задач.
        assertTrue(manager.getTaskList().size() == 0);

        // Эпик без подзадач.
        Task epic = new Task("Эпик", "Эпик без подзадач", TaskType.EPIC);
        manager.createTask(epic);
        assertEquals(1, manager.getEpicList().size());

        // Пустой список истории.
        Task task = new Task("Task", "описание Task", TaskType.TASK);
        Task task2 = new Task("Task2", "описание Task2", TaskType.TASK);
        Task task3 = new Task("Task3", "описание Task3", TaskType.TASK);
        manager.createTask(task);
        manager.createTask(task2);
        manager.createTask(task3);
        assertNull(FileBackedTasksManager.historyToString(manager.getHistoryManager()));
    }

    @Test
    public void saveTest() {
        File file = new File(FILE_BACKED_NAME);
        file.delete();
        Task task1 = new Task("Проверить manager.TaskManager", "Проверить основные методы класса manager.TaskManager", TaskType.TASK);
        manager.createTask(task1);
        assertTrue(file.exists());
    }

    @Test
    public void historyToStringTest() {
        Task task1 = new Task("Проверить manager.TaskManager", "Проверить основные методы класса manager.TaskManager", TaskType.TASK);
        manager.createTask(task1);
        manager.getById(task1.getTaskId());
        assertEquals(FileBackedTasksManager.historyToString(manager.getHistoryManager()), task1.getTaskId() + "");
    }

    @Test
    public void historyFromStringTest() {
        Task task = new Task("Task", "описание Task", TaskType.TASK);
        Task task2 = new Task("Task2", "описание Task2", TaskType.TASK);
        Task task3 = new Task("Task3", "описание Task3", TaskType.TASK);
        manager.createTask(task);
        manager.createTask(task2);
        manager.createTask(task3);
        manager.getById(task.getTaskId());
        manager.getById(task2.getTaskId());
        manager.getById(task3.getTaskId());
        System.out.println();
        assertEquals(FileBackedTasksManager.historyFromString(FileBackedTasksManager.historyToString(manager.getHistoryManager())).size(), 3);
    }

    @Test
    public void restoreTaskManagerTest() {
        Task task = new Task("Task", "описание Task", TaskType.TASK);
        Task task2 = new Task("Task2", "описание Task2", TaskType.TASK);
        Task task3 = new Task("Task3", "описание Task3", TaskType.TASK);
        manager.createTask(task);
        manager.createTask(task2);
        manager.createTask(task3);
        manager.getById(task.getTaskId());
        manager.getById(task2.getTaskId());
        manager.getById(task3.getTaskId());
        String managerFileContents = "";
        try {
            managerFileContents = Files.readString(Path.of(FILE_BACKED_NAME));
        } catch (IOException e) {
            System.out.println("Возможно, файл не находится в нужной директории. Проверяемый путь: " + FILE_BACKED_NAME);
        }

        List<Task> taskList = new ArrayList<>(manager.getAllTaskList());
        List<Integer> historyList = FileBackedTasksManager.historyFromString(FileBackedTasksManager.historyToString(manager.getHistoryManager()));
        TaskManager restoredManager = FileBackedTasksManager.restoreTaskManagerFromLists(taskList, historyList);
        String restoredManagerFileContents = "";
        try {
            restoredManagerFileContents = Files.readString(Path.of(FILE_BACKED_NAME));
        } catch (IOException e) {
            System.out.println("Возможно, файл не находится в нужной директории. Проверяемый путь: " + FILE_BACKED_NAME);
        }

        assertEquals(managerFileContents, restoredManagerFileContents);
    }

    @Test
    public void taskToStringTest() {
        Task task = new Task("Task", "описание Task", TaskType.TASK);
        assertFalse(FileBackedTasksManager.taskToString(task).isEmpty());
    }
}