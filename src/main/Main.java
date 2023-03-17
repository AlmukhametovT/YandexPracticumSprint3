package main;

import main.exceptions.ManagerSaveException;
import main.manager.*;
import main.task.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ManagerSaveException {
        LocalDateTime time = LocalDateTime.of(1986, 02, 01, 22, 00, 22);

        Task epic1 = new Epic("Работать в удовольствие", "Стать Senior Java-разработчиком", TaskType.EPIC);
        epic1.setDuration(100);
        epic1.setStartTime(time.plusDays(20));

        Task subTask1Epic1 = new SubTask("Middle разработчик", "После джуна вырасти до Мидла",
                TaskType.SUB_TASK, (Epic) epic1);
        subTask1Epic1.setDuration(200);
        subTask1Epic1.setStartTime(time.plusWeeks(44));

        Task subTask2Epic1 = new SubTask("Senior разработчик", "После Мидла вырасти до Сеньора",
                TaskType.SUB_TASK, (Epic) epic1);
        subTask2Epic1.setDuration(123);
//        subTask2Epic1.setStartTime(time.minusYears(4));

        Task epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их", TaskType.EPIC);
        Task subTask1Epic2 = new SubTask("English", "London is the capital of Great Britain",
                TaskType.SUB_TASK, (Epic) epic2);
        subTask1Epic2.setDuration(999);
        subTask1Epic2.setStartTime(time.minusHours(25));

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

        Task subTask3Epic1 = new SubTask("Стать крутым программером", "Выучить Basic & Кумир & Pascal...",
                TaskType.SUB_TASK, (Epic) epic1);
        subTask3Epic1.setDuration(1);
        subTask3Epic1.setStartTime(time.plusYears(5));
        taskManager.createTask(subTask3Epic1);

        Task task1 = new Task("Проверить manager.TaskManager", "Проверить основные методы класса manager.TaskManager", TaskType.TASK);
        task1.setDuration(120);
        task1.setStartTime(time.plusYears(50));
        taskManager.createTask(task1);

        Task epic3 = new Epic("Эпик без подзадач", "Зачем мне подзадачи? я сам все могу!", TaskType.EPIC);
        epic3.setDuration(200);
        epic3.setStartTime(time.plusYears(20));
        taskManager.createTask(epic3);

        taskManager.getAllTaskList().forEach(System.out::println);

        // заполним историю запросами тасков
        taskManager.getEpicById(1000003);
        taskManager.getById(1000003);
        taskManager.getById(1000000);
        taskManager.getTaskById(1000006);
        taskManager.getSubTaskById(1000001);
        taskManager.getEpicById(1000003);
        taskManager.getSubTaskById(1000005);
        taskManager.getSubTaskById(1000004);
        taskManager.getById(1000000);
        taskManager.getTaskById(1000006);
        taskManager.getSubTaskById(1000002);
        taskManager.getEpicById(1000000);
        taskManager.getEpicById(1000003);
        taskManager.getById(1000007);
        taskManager.getById(1000006);
        taskManager.getById(1000000);
        taskManager.getHistoryManager().printHistory();

        List<Task> taskList = new ArrayList<>(taskManager.getAllTaskList());
        List<Integer> historyList = FileBackedTasksManager.historyFromString(FileBackedTasksManager.historyToString(taskManager.getHistoryManager()));

        // создаем менеджер задач с возможностью сохранения из Task`ов, которые есть в системе
        // поэтому не создаются новые таски, и id у них остается такой же
        TaskManager fileBackedTasksManager = FileBackedTasksManager.restoreTaskManagerFromLists(taskList, historyList);
        fileBackedTasksManager.getHistoryManager().printHistory();

        System.out.println(Task.getPrioritizedTasks());
        Task.getPrioritizedTasks().forEach(System.out::println);
        Task.printIdSetFromTreeSetPrioritizedTasks();
    }
}