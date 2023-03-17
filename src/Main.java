import manager.*;
import task.*;

public class Main {
    public static void main(String[] args) {

        Epic epic1 = new Epic("Работать в удовольствие", "Стать Senior Java-разработчиком", TaskType.EPIC);
        Task subTask1Epic1 = new SubTask("Middle разработчик", "После джуна вырасти до Мидла",
                TaskType.SUB_TASK, epic1);
        Task subTask2Epic1 = new SubTask("Senior разработчик", "После Мидла вырасти до Сеньора",
                TaskType.SUB_TASK, epic1);
        Epic epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их", TaskType.EPIC);
        Task subTask1Epic2 = new SubTask("English", "London is the capital of Great Britain",
                TaskType.SUB_TASK, epic2);

        TaskManager taskManager = Managers.getDefault();

        taskManager.createTask(epic1);
        taskManager.createTask(subTask1Epic1);
        taskManager.createTask(subTask2Epic1);
        taskManager.createTask(epic2);
        taskManager.createTask(subTask1Epic2);

        subTask1Epic1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(subTask1Epic1);
        subTask2Epic1.setStatus(TaskStatus.DONE);
        taskManager.updateTask(subTask2Epic1);

        subTask1Epic2.setStatus(TaskStatus.DONE);
        taskManager.updateTask(subTask1Epic2);
        subTask1Epic1.setStatus(TaskStatus.DONE);
        taskManager.updateTask(subTask1Epic1);

        Task subTask3Epic1 = new SubTask("Стать крутым программером", "Выучить Basic, Кумир, Pascal...",
                TaskType.SUB_TASK, epic1);
        taskManager.createTask(subTask3Epic1);

        Task task1 = new Task("Проверить manager.TaskManager", "Проверить основные методы класса manager.TaskManager", TaskType.TASK);
        taskManager.createTask(task1);

        Epic epic3 = new Epic("Эпик без подзадач", "Зачем мне подзадачи? я сам все могу!", TaskType.EPIC);
        taskManager.createTask(epic3);

        taskManager.getAllTaskList().forEach(System.out::println);

        //•	запросите созданные задачи несколько раз в разном порядке;
        //•	после каждого запроса выведите историю и убедитесь, что в ней нет повторов;
        taskManager.getEpicById(1000003);
        taskManager.getHistoryManager().printHistory();
        taskManager.getById(1000003);
        taskManager.getHistoryManager().printHistory();
        taskManager.getById(1000000);
        taskManager.getHistoryManager().printHistory();
        taskManager.getTaskById(1000006);
        taskManager.getHistoryManager().printHistory();
        taskManager.getSubTaskById(1000001);
        taskManager.getHistoryManager().printHistory();
        taskManager.getEpicById(1000003);
        taskManager.getHistoryManager().printHistory();
        taskManager.getSubTaskById(1000005);
        taskManager.getHistoryManager().printHistory();
        taskManager.getSubTaskById(1000004);
        taskManager.getHistoryManager().printHistory();
        taskManager.getById(1000000);
        taskManager.getHistoryManager().printHistory();
        taskManager.getTaskById(1000006);
        taskManager.getHistoryManager().printHistory();
        taskManager.getSubTaskById(1000002);
        taskManager.getHistoryManager().printHistory();
        taskManager.getEpicById(1000000);
        taskManager.getHistoryManager().printHistory();
        taskManager.getEpicById(1000003);
        taskManager.getHistoryManager().printHistory();
        taskManager.getById(1000007);
        taskManager.getHistoryManager().printHistory();

        //•	удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться;
        taskManager.deleteAnyTaskById(1000004);
        taskManager.getHistoryManager().printHistory();

        //•	удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи.
        taskManager.deleteAnyTaskById(1000000);
        taskManager.getHistoryManager().printHistory();
    }
}