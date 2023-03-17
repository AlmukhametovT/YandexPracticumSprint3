package main.exceptions;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(String value) {
        super(value);
        System.out.println("Проблемы с сохранением образа менеджера задач!");
    }
}