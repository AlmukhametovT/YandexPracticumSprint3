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

        TaskManager taskManager = new TaskManager();
        taskManager.createTask(epic1);
        taskManager.createTask(subTask1Epic1);
        taskManager.createTask(subTask2Epic1);
        taskManager.createTask(epic2);
        taskManager.createTask(subTask1Epic2);

        System.out.println("Список всех задач:");
        System.out.println(taskManager.getAllTaskList());
        System.out.println("Список задач типа Epic:");
        System.out.println(taskManager.getEpicList());
        System.out.println("Список задач типа SubTask:");
        System.out.println(taskManager.getSubTaskList());
        System.out.println("Список задач типа Task:");
        System.out.println(taskManager.getTaskList());

        subTask1Epic1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(subTask1Epic1);
        subTask2Epic1.setStatus(TaskStatus.DONE);
        taskManager.updateTask(subTask2Epic1);
        System.out.println("Изменили статус подзадач первой задачи типа Epic");
        System.out.println(subTask1Epic1);
        System.out.println(subTask2Epic1);
        System.out.println(epic1);

        subTask1Epic2.setStatus(TaskStatus.DONE);
        taskManager.updateTask(subTask1Epic2);
        System.out.println("Изменили статус подзадачи второй задачи типа Epic");
        System.out.println(subTask1Epic2);
        System.out.println(epic2);

        taskManager.deleteTaskById(subTask1Epic2.getTaskId());
        taskManager.deleteTaskById(epic2.getTaskId());
        System.out.println("Удалили подзадачу и Epic задачу с изучением английского, вроде статус стал \"Done!\"");
        System.out.println("Список всех оставшихся задач:");
        System.out.println(taskManager.getAllTaskList());

        subTask1Epic1.setStatus(TaskStatus.DONE);
        taskManager.updateTask(subTask1Epic1);
        System.out.println("Все сабтаски Эпика1 стали Done, проверяем какой статус у Эпика:");
        taskManager.getAllTaskList().forEach(System.out::println);

        Task subTask3Epic1 = new SubTask("Стать крутым программером", "Выучить Basic, Кумир, Pascal...",
                TaskType.SUB_TASK, epic1);
        taskManager.createTask(subTask3Epic1);
        System.out.println("Эпик1 был Done, добавили новый сабтаск к нему и проверяем статус Эпика:");
        taskManager.getAllTaskList().forEach(System.out::println);

        System.out.println("удалили новый сабтаск, проверим Эпик (у оставшихся сабтасков статус Done):");
        taskManager.deleteTaskById(1000005);
        taskManager.getAllTaskList().forEach(System.out::println);

        taskManager.deleteAllSubTask();
        System.out.println("Удалили все сабтаски, проверим что стало с оставшимся одним Эпиком:");
        taskManager.getAllTaskList().forEach(System.out::println);

        taskManager.deleteAllEpic();
        System.out.println("Что осталось, когда удалили все Эпики:");
        taskManager.getAllTaskList().forEach(System.out::println);
        System.out.println(Task.allTask);

        Task task1 = new Task("Проверить TaskManager", "Проверить основные методы класса TaskManager", TaskType.TASK);
        taskManager.createTask(task1);
        System.out.println("Проверим самое простое, что происходит когда создаем обычные таски:");
        taskManager.getAllTaskList().forEach(System.out::println);

        taskManager.deleteAll();
        System.out.println("Проверяем что останется, если удалить всё:");
        taskManager.getAllTaskList().forEach(System.out::println);
        System.out.println(Task.allTask);
    }
}