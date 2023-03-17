import exceptions.ManagerSaveException;
import manager.*;
import task.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ManagerSaveException {

        Task epic1 = new Epic("Работать в удовольствие", "Стать Senior Java-разработчиком", TaskType.EPIC);
        Task subTask1Epic1 = new SubTask("Middle разработчик", "После джуна вырасти до Мидла",
                TaskType.SUB_TASK, (Epic) epic1);
        Task subTask2Epic1 = new SubTask("Senior разработчик", "После Мидла вырасти до Сеньора",
                TaskType.SUB_TASK, (Epic) epic1);
        Task epic2 = new Epic("Выучить английский", "Записаться на курсы английского и завершить их", TaskType.EPIC);
        Task subTask1Epic2 = new SubTask("English", "London is the capital of Great Britain",
                TaskType.SUB_TASK, (Epic) epic2);

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
        taskManager.createTask(subTask3Epic1);

        Task task1 = new Task("Проверить manager.TaskManager", "Проверить основные методы класса manager.TaskManager", TaskType.TASK);
        taskManager.createTask(task1);

        Task epic3 = new Epic("Эпик без подзадач", "Зачем мне подзадачи? я сам все могу!", TaskType.EPIC);
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

//        //•	удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться;
//        taskManager.deleteAnyTaskById(1000004);
//        taskManager.getHistoryManager().printHistory();
//
//        //•	удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи.
//        taskManager.deleteAnyTaskById(1000000);
//        taskManager.getHistoryManager().printHistory();

        String sss = FileBackedTasksManager.historyToString(taskManager.getHistoryManager());
        System.out.println("Получаем строку из истории: " + sss);

        System.out.println("Получаем лист истории из строки: " + FileBackedTasksManager.historyFromString(sss));

        List<Task> taskList = new ArrayList<>(taskManager.getAllTaskList());
        List<Integer> historyList = FileBackedTasksManager.historyFromString(sss);

        System.out.println(taskList);
        System.out.println(historyList);

        // создаем менеджер задач с возможностью сохранения из Task`ов, которые есть в системе
        // поэтому не создаются новые таски, и id у них остается такой же
        TaskManager fileBackedTasksManager = FileBackedTasksManager.restoreTaskManagerFromLists(taskList, historyList);
        fileBackedTasksManager.getHistoryManager().printHistory();

        // создаем менеджер задач с возможностью сохранения из Task`ов, которые считали из файла
        // поэтому создаются новые аналогичные таски, а т.к. id у них уже используется, то назначется новый id
        TaskManager fileBackedTasksManager2 = FileBackedTasksManager.loadFromDefaultFile();
        // убрал передачу null в аргументах метода, сделал дефолтный загрузчик и старый стал универсальным.
        // путь изменил до рекомендуемых. есть идея заводить отдельные файлы для каждого объекта FileBackedTasksManager
        // (завести счетчик и плюсовать его к имени очередного менеджера FileBackedTasksManager)
        // что бы сохранялась история каждого из них, например у меня fileBackedTasksManager работает первым (в нем
        // восстановление из стрингов, которые получаем из объекта класса InMemoryHistoryManager,
        // потом создается fileBackedTasksManager2 для восстановления данных из файла, а т.к. путь один, то первый
        // менеджер остается без данных для восстановления, их затер менеджер 2 своими данными :)

        System.out.println();
        System.out.println("распечатаем историю и проверим что она аналогична, отличаются только id");
        System.out.println("в случае внештатного перезапуска программы и восстановления данных");
        System.out.println("проблем с новыми id не будет, т.к. старые сотрутся и картина будет");
        System.out.println("полностью соответствовать до внештатной ситуации...");
        if (fileBackedTasksManager2 != null) {
            fileBackedTasksManager2.getHistoryManager().printHistory();
        }
    }
}