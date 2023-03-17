package test.task;

import main.task.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class SubTaskTest {

    @Test
    public void subTaskWithEpicNullTest() {
        System.out.println("Тест подзадачи с null вместо Эпика:");
        Epic epic = null;
        assertThrows(NullPointerException.class, () ->(new SubTask("subTask", "subTask", TaskType.SUB_TASK, epic)).getEpicId());
    }
}