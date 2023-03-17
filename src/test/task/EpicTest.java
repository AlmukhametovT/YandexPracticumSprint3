package test.task;

import main.task.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class EpicTest {

    private static long suiteStartTime;
    private long testStartTime;

    @BeforeAll
    public static void initSuite() {
        System.out.println("Running All Tests");
        suiteStartTime = System.nanoTime();
    }

    @AfterAll
    public static void completeSuite() {
        System.out.println("All Tests completed in time: " + (System.nanoTime() - suiteStartTime));
    }

    @BeforeEach
    public void initTest() {
        System.out.println("Starting new test");
        testStartTime = System.nanoTime();
    }

    @AfterEach
    public void finalizeTest() {
        System.out.println("Test complete in time: " + (System.nanoTime() - testStartTime));
    }

    public Epic createTasks(int option) {
        Epic epic1 = new Epic("Работать в удовольствие", "Стать Senior Java-разработчиком", TaskType.EPIC);

        Task subTask1Epic1 = new SubTask("Middle разработчик", "После джуна вырасти до Мидла",
                TaskType.SUB_TASK, epic1);
        Task subTask2Epic1 = new SubTask("Senior разработчик", "После Мидла вырасти до Сеньора",
                TaskType.SUB_TASK, epic1);
        Task subTask3Epic1 = new SubTask("Стать крутым программером", "Выучить Basic & Кумир & Pascal...",
                TaskType.SUB_TASK, epic1);

        switch (option) {
            case 1:
                subTask1Epic1.setStatus(TaskStatus.NEW);
                subTask2Epic1.setStatus(TaskStatus.NEW);
                subTask3Epic1.setStatus(TaskStatus.NEW);
                break;
            case 2:
                subTask1Epic1.setStatus(TaskStatus.DONE);
                subTask2Epic1.setStatus(TaskStatus.DONE);
                subTask3Epic1.setStatus(TaskStatus.DONE);
                break;
            case 3:
                subTask1Epic1.setStatus(TaskStatus.NEW);
                subTask2Epic1.setStatus(TaskStatus.DONE);
                subTask3Epic1.setStatus(TaskStatus.NEW);
                break;
            case 4:
                subTask1Epic1.setStatus(TaskStatus.IN_PROGRESS);
                subTask2Epic1.setStatus(TaskStatus.IN_PROGRESS);
                subTask3Epic1.setStatus(TaskStatus.IN_PROGRESS);
                break;
            default:
                epic1.getSubTaskIdSet().clear();
        }

        return epic1;
    }

    @Test
    public void emptySubTaskListTest() {
        System.out.println("Тест расчёта статуса Epic, пустой список подзадач:");
        Epic epic = createTasks(0);
        System.out.println(epic);
        assertNotEquals(epic.getStatus(), TaskStatus.DONE);
        assertTrue(epic.getSubTaskIdSet().isEmpty());
        assertFalse(epic.getStatus().equals(TaskStatus.IN_PROGRESS));
    }

    @Test
    public void newSubTaskListTest() {
        System.out.println("Тест расчёта статуса Epic, все подзадачи со статусом NEW:");
        Epic epic = createTasks(1);
        System.out.println(epic);
        assertEquals(TaskStatus.NEW, epic.getStatus(), "Некорректный расчет статуса EPIC, " +
                "Ожидаем \"NEW\", получен \"" + epic.getStatus() + "\"");
    }

    @Test
    public void doneSubTaskListTest() {
        System.out.println("Тест расчёта статуса Epic, все подзадачи со статусом DONE:");
        Epic epic = createTasks(2);
        System.out.println(epic);
        assertEquals(TaskStatus.DONE, epic.getStatus(), "Некорректный расчет статуса EPIC, " +
                "Ожидаем \"DONE\", получен \"" + epic.getStatus() + "\"");
    }

    @Test
    public void newAndDoneSubTaskListTest() {
        System.out.println("Тест расчёта статуса Epic, подзадачи со статусами NEW и DONE:");
        Epic epic = createTasks(3);
        System.out.println(epic);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus(), "Некорректный расчет статуса EPIC, " +
                "Ожидаем \"IN_PROGRESS\", получен \"" + epic.getStatus() + "\"");
    }

    @Test
    public void inProgressSubTaskListTest() {
        System.out.println("Тест расчёта статуса Epic, все подзадачи со статусом IN_PROGRESS:");
        Epic epic = createTasks(4);
        System.out.println(epic);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus(), "Некорректный расчет статуса EPIC, " +
                "Ожидаем \"IN_PROGRESS\", получен \"" + epic.getStatus() + "\"");
    }
}