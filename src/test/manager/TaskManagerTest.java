package test.manager;

import main.manager.InMemoryTaskManager;
import main.manager.TaskManager;
import main.task.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    T manager;

    @Test
    public void getTaskMapTest() {
        manager.deleteAll();
        Task task1 = new Task("Проверить manager.TaskManager", "Проверить основные методы класса manager.TaskManager", TaskType.TASK);
        manager.createTask(task1);
        assertEquals(1, manager.getTaskMap().size());
    }

    @Test
    public void getSubTaskMapTest() {
        manager.deleteAll();
        Task epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их", TaskType.EPIC);
        Task subTask1Epic2 = new SubTask("English", "London is the capital of Great Britain",
                TaskType.SUB_TASK, (Epic) epic2);
        manager.createTask(epic2);
        manager.createTask(subTask1Epic2);
        assertEquals(1, manager.getSubTaskMap().size());
    }

    @Test
    public void getEpicMapTest() {
        manager.deleteAll();
        Task epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их", TaskType.EPIC);
        manager.createTask(epic2);
        assertEquals(1, manager.getEpicMap().size());
    }

    @Test
    public void getAllTaskListTest() {
        manager.deleteAll();
        Task task1 = new Task("Проверить manager.TaskManager", "Проверить основные методы класса manager.TaskManager", TaskType.TASK);
        manager.createTask(task1);
        Task epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их", TaskType.EPIC);
        manager.createTask(epic2);
        Task subTask1Epic2 = new SubTask("English", "London is the capital of Great Britain",
                TaskType.SUB_TASK, (Epic) epic2);
        manager.createTask(subTask1Epic2);
        assertEquals(3, manager.getAllTaskList().size());
    }

    @Test
    public void getTaskListTest() {
        manager.deleteAll();
        Task task1 = new Task("Проверить manager.TaskManager", "Проверить основные методы класса manager.TaskManager", TaskType.TASK);
        manager.createTask(task1);
        assertEquals(1, manager.getTaskList().size());
    }

    @Test
    public void getSubTaskListTest() {
        manager.deleteAll();
        Task epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их", TaskType.EPIC);
        Task subTask1Epic2 = new SubTask("English", "London is the capital of Great Britain",
                TaskType.SUB_TASK, (Epic) epic2);
        manager.createTask(epic2);
        manager.createTask(subTask1Epic2);
        assertEquals(1, manager.getSubTaskList().size());
    }

    @Test
    public void getEpicListTest() {
        manager.deleteAll();
        Task epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их", TaskType.EPIC);
        manager.createTask(epic2);
        assertEquals(1, manager.getEpicList().size());
    }

    @Test
    public void deleteAllTest() {
        manager.deleteAll();
        Task task1 = new Task("Проверить manager.TaskManager", "Проверить основные методы класса manager.TaskManager", TaskType.TASK);
        manager.createTask(task1);
        Task epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их", TaskType.EPIC);
        manager.createTask(epic2);
        Task subTask1Epic2 = new SubTask("English", "London is the capital of Great Britain",
                TaskType.SUB_TASK, (Epic) epic2);
        manager.createTask(subTask1Epic2);
        manager.deleteAll();
        assertEquals(0, manager.getAllTaskList().size());
        assertTrue(manager.getAllTaskList().isEmpty());
    }

    @Test
    public void deleteAllTaskTest() {
        manager.deleteAll();
        Task task1 = new Task("Проверить manager.TaskManager", "Проверить основные методы класса manager.TaskManager", TaskType.TASK);
        manager.createTask(task1);
        Task epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их", TaskType.EPIC);
        manager.createTask(epic2);
        Task subTask1Epic2 = new SubTask("English", "London is the capital of Great Britain",
                TaskType.SUB_TASK, (Epic) epic2);
        manager.createTask(subTask1Epic2);
        manager.deleteAllTask();
        assertEquals(0, manager.getTaskList().size());
        assertTrue(manager.getTaskList().isEmpty());
    }

    @Test
    public void deleteAllSubTaskTest() {
        manager.deleteAll();
        Task epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их", TaskType.EPIC);
        manager.createTask(epic2);
        Task subTask1Epic2 = new SubTask("English", "London is the capital of Great Britain",
                TaskType.SUB_TASK, (Epic) epic2);
        manager.createTask(subTask1Epic2);
        manager.deleteAllSubTask();
        assertEquals(0, manager.getSubTaskList().size());
        assertTrue(manager.getSubTaskList().isEmpty());
    }

    @Test
    public void deleteAllEpicTest() {
        manager.deleteAll();
        Task epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их", TaskType.EPIC);
        manager.createTask(epic2);
        Task subTask1Epic2 = new SubTask("English", "London is the capital of Great Britain",
                TaskType.SUB_TASK, (Epic) epic2);
        manager.createTask(subTask1Epic2);
        manager.deleteAllEpic();
        assertEquals(0, manager.getEpicList().size());
        assertEquals(0, manager.getSubTaskList().size());
        assertTrue(manager.getEpicList().isEmpty());
        assertTrue(manager.getSubTaskList().isEmpty());
    }

    @Test
    public void getByIdTest() {
        manager.deleteAll();
        Task task1 = new Task("Проверить manager.TaskManager",
                "Проверить основные методы класса manager.TaskManager", 100000001, TaskStatus.DONE, TaskType.TASK);
        manager.createTask(task1);
        Epic epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их",
                100000002, TaskStatus.DONE, TaskType.EPIC);
        manager.createTask(epic2);
        Task subTask1Epic2 = new SubTask("English", "London is the capital of Great Britain",
                100000003, TaskStatus.DONE, TaskType.SUB_TASK, epic2.getTaskId());
        manager.createTask(subTask1Epic2);
        assertEquals(manager.getById(100000001), task1);
        assertEquals(manager.getById(100000002), epic2);
        assertEquals(manager.getById(100000003), subTask1Epic2);
    }

    @Test
    public void getTaskByIdTest() {
        manager.deleteAll();
        Task task1 = new Task("Проверить manager.TaskManager",
                "Проверить основные методы класса manager.TaskManager", 100000001, TaskStatus.DONE, TaskType.TASK);
        manager.createTask(task1);
        assertEquals(manager.getTaskById(100000001), task1);
    }

    @Test
    public void getSubTaskByIdTest() {
        manager.deleteAll();
        Epic epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их",
                100000002, TaskStatus.DONE, TaskType.EPIC);
        manager.createTask(epic2);
        Task subTask1Epic2 = new SubTask("English", "London is the capital of Great Britain",
                100000003, TaskStatus.DONE, TaskType.SUB_TASK, epic2.getTaskId());
        manager.createTask(subTask1Epic2);
        assertEquals(manager.getSubTaskById(100000003), subTask1Epic2);
    }

    @Test
    public void getEpicByIdTest() {
        manager.deleteAll();
        Epic epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их",
                100000002, TaskStatus.DONE, TaskType.EPIC);
        manager.createTask(epic2);
        assertEquals(manager.getEpicById(100000002), epic2);
    }

    @Test
    public void createTaskTest() {
        manager.deleteAll();
        Task task1 = new Task("Проверить manager.TaskManager", "Проверить основные методы класса manager.TaskManager", TaskType.TASK);
        manager.createTask(task1);
        Task epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их", TaskType.EPIC);
        manager.createTask(epic2);
        Task subTask1Epic2 = new SubTask("English", "London is the capital of Great Britain",
                TaskType.SUB_TASK, (Epic) epic2);
        manager.createTask(subTask1Epic2);
        assertTrue(manager.getTaskMap().containsValue(task1));
        assertTrue(manager.getEpicMap().containsValue(epic2));
        assertTrue(manager.getSubTaskMap().containsValue(subTask1Epic2));
    }

    @Test
    public void updateTaskTest() {
        manager.deleteAll();
        Task epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их", TaskType.EPIC);
        manager.createTask(epic2);
        Task subTask1Epic2 = new SubTask("English", "London is the capital of Great Britain",
                TaskType.SUB_TASK, (Epic) epic2);
        manager.createTask(subTask1Epic2);
        Task subTask2Epic2 = new SubTask("China", "Na**** English?",
                TaskType.SUB_TASK, (Epic) epic2);
        manager.createTask(subTask2Epic2);
        assertTrue(manager.getEpicMap().containsValue(epic2));
        assertTrue(manager.getSubTaskMap().containsValue(subTask1Epic2));
        assertTrue(manager.getSubTaskMap().containsValue(subTask2Epic2));

        subTask1Epic2.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateTask(subTask1Epic2);
        assertEquals(epic2.getStatus(), TaskStatus.IN_PROGRESS);

        subTask1Epic2.setStatus(TaskStatus.DONE);
        manager.updateTask(subTask1Epic2);
        subTask2Epic2.setStatus(TaskStatus.DONE);
        manager.updateTask(subTask2Epic2);
        assertEquals(epic2.getStatus(), TaskStatus.DONE);

        subTask1Epic2.setStatus(TaskStatus.NEW);
        manager.updateTask(subTask1Epic2);
        subTask2Epic2.setStatus(TaskStatus.NEW);
        manager.updateTask(subTask2Epic2);
        assertEquals(epic2.getStatus(), TaskStatus.NEW);

        subTask2Epic2.setStatus(TaskStatus.DONE);
        manager.updateTask(subTask2Epic2);
        assertEquals(epic2.getStatus(), TaskStatus.IN_PROGRESS);
    }

    @Test
    public void deleteAnyTaskByIdTest() {
        manager.deleteAll();
        Task task1 = new Task("Проверить manager.TaskManager",
                "Проверить основные методы класса manager.TaskManager", 100000001, TaskStatus.DONE, TaskType.TASK);
        manager.createTask(task1);
        Epic epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их",
                100000002, TaskStatus.DONE, TaskType.EPIC);
        manager.createTask(epic2);
        Task subTask1Epic2 = new SubTask("English", "London is the capital of Great Britain",
                100000003, TaskStatus.DONE, TaskType.SUB_TASK, epic2.getTaskId());
        manager.createTask(subTask1Epic2);
        manager.deleteAnyTaskById(100000001);
        assertTrue(manager.getTaskMap().isEmpty());
        manager.deleteAnyTaskById(100000002);
        assertTrue(manager.getSubTaskMap().isEmpty());
        assertTrue(manager.getEpicMap().isEmpty());
    }

    @Test
    public void getSubTaskListByEpicTest() {
        manager.deleteAll();
        Epic epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их",
                100000002, TaskStatus.DONE, TaskType.EPIC);
        manager.createTask(epic2);
        SubTask subTask1Epic2 = new SubTask("English", "London is the capital of Great Britain",
                100000003, TaskStatus.DONE, TaskType.SUB_TASK, epic2.getTaskId());
        manager.createTask(subTask1Epic2);
        assertTrue(manager.getSubTaskListByEpic(epic2).contains(subTask1Epic2));
    }
}