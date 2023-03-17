package test.manager;

import main.manager.InMemoryTaskManager;
import main.task.Task;
import main.task.TaskType;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void initTest() {
        super.manager = new InMemoryTaskManager();
    }

    @Test
    public void getTaskListFromMapTest() {
        manager.deleteAll();
        Task task1 = new Task("Проверить manager.TaskManager", "Проверить основные методы класса manager.TaskManager", TaskType.TASK);
        manager.createTask(task1);
        assertEquals(manager.getTaskMap().size(), manager.getTaskListFromMap(manager.getTaskMap()).size());
    }
}