package main.task;

import java.time.LocalDateTime;
import java.util.*;

public class Task {
    public static int lastTaskId = 1_000_000; // подготовленное значение для ID следующей задачи
    public final static boolean NO_MORE_THAN_ONE_TASK_AT_A_TIME = true; // задачи и подзадачи не пересекаются по времени выполнения
    // добавил это поле, т.к. считаю неправильным ТЗ, и при таком раскладе полезность приложения сводится к 0...
    // представим что одна задача - закончить курс по Java на Яндекс.Практикум, и длится она 10 месяцев.
    // вторая задача - сходить за покупками. Получается пока я не получу диплом, мне нельзя в магазин?..
    public static Map<Integer, Task> allTask = new HashMap<>(); // хранилище всех задач
    protected String title;
    protected String description;
    private final int taskId;
    protected TaskStatus status;
    protected TaskType type;
    protected int duration; // продолжительность задачи, оценка того, сколько времени она займёт в минутах (число)
    protected LocalDateTime startTime; // startTime — дата, когда предполагается приступить к выполнению задачи
    protected LocalDateTime endTime; // время завершения задачи, которое рассчитывается исходя из startTime и duration

    public Task(String title, String description, TaskType type) {
        this.title = title;
        this.description = description;
        this.taskId = checkTaskId(lastTaskId);
        lastTaskId++;
        this.status = TaskStatus.NEW;
        this.type = type;
        this.duration = 0;
        this.startTime = null;
        this.endTime = null;
        allTask.put(taskId, this);
    }

    public Task(String title, String description, int taskId, TaskStatus status, TaskType type) {
        this.title = title;
        this.description = description;
        this.taskId = checkTaskId(taskId);
        this.status = status;
        this.type = type;
        this.duration = 0;
        this.startTime = null;
        this.endTime = null;
        allTask.put(taskId, this);
    }

    public Task(String title, String description, int taskId, TaskStatus status, TaskType type, int duration, LocalDateTime startTime) {
        this.title = title;
        this.description = description;
        this.taskId = checkTaskId(taskId);
        this.status = status;
        this.type = type;
        this.duration = duration;
        boolean setStartTimeNull = false;
        if (startTime != null) {
            if (!checkTimeIntersection(duration, startTime) && NO_MORE_THAN_ONE_TASK_AT_A_TIME) {
                System.out.println("Выбран режим, при котором задачи не могут пересекаться по времени.");
                System.out.println("Интервалы задач пересекаются с новой задачей, установим startTime = null");
                setStartTimeNull = true;
            }
            if (!NO_MORE_THAN_ONE_TASK_AT_A_TIME) setStartTimeNull = false;
        } else setStartTimeNull = true;
        if (setStartTimeNull) {
            this.startTime = null;
            this.endTime = null;
        } else {
            this.startTime = startTime.minusNanos(startTime.getNano());
            this.endTime = startTime.plusMinutes(duration);
        }
        allTask.put(taskId, this);
    }

    public boolean checkTimeIntersection(int duration, LocalDateTime startTime) {
        boolean noIntersection = true;
        if (startTime == null) return noIntersection;
        List<Task> tempList = getPrioritizedTasks();
        if (tempList.get(0).getStartTime() == null || tempList.get(0).getStartTime().isAfter(startTime.plusMinutes(duration)))
            return noIntersection;
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).getStartTime() == null) break;
            if (startTime.isAfter(tempList.get(i).getEndTime())) continue;
            if (startTime.plusMinutes(duration).isBefore(tempList.get(i).getStartTime())) break;
            noIntersection = false;
            break;
        }
        return noIntersection;
    }

    public int checkTaskId(int id) {
        while (true) {
            if (allTask.containsKey(id)) {
                System.out.println("данный id уже есть в системе, объект не создан, попробуем " + lastTaskId);
                id = lastTaskId;
                lastTaskId++;
                continue;
            }
            return id;
        }
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (NO_MORE_THAN_ONE_TASK_AT_A_TIME) {
            List<Task> tempList = getPrioritizedTasks();
            int thisIndex = tempList.indexOf(this);
            if ((thisIndex + 1) < tempList.size()) {
                if (tempList.get(thisIndex + 1).getStartTime() != null) {
                    if (this.getStartTime().plusMinutes(duration).isAfter(tempList.get(thisIndex + 1).getStartTime())) {
                        System.out.println("Выбран режим, при котором задачи не могут пересекаться по времени.");
                        System.out.println("Выбранная продолжительность не удовлетворяет данному требованию.");
                        System.out.println("Продолжительность останется прежней.");
                        return;
                    }
                }
            }
        }
        this.duration = duration;
        if (this.type.equals(TaskType.EPIC) || startTime == null) return;
        this.endTime = startTime.plusMinutes(duration);
        if (this.type.equals(TaskType.SUB_TASK)) {
            ((SubTask) this).updateEpicTimer((Epic) Task.allTask.get(((SubTask) this).getEpicId()));
        }
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        try {
            if (NO_MORE_THAN_ONE_TASK_AT_A_TIME) {
                LocalDateTime rememberStartTime = this.getStartTime();
                LocalDateTime rememberEndTime = this.getEndTime();
                this.startTime = null;
                this.endTime = null;
                if (startTime == null) {
                    return;
                }
                if (!checkTimeIntersection(this.duration, startTime)) {
                    System.out.println("Выбран режим, при котором задачи не могут пересекаться по времени.");
                    System.out.println("Выбранное начало выполнения задачи не удовлетворяет данному требованию.");
                    System.out.println("StartTime останется прежней.");
                    this.startTime = rememberStartTime;
                    this.endTime = rememberEndTime;
                    return;
                }
            }
        } catch (NullPointerException e) {
            return;
        }
        this.startTime = startTime.minusNanos(startTime.getNano());
        if (this.type.equals(TaskType.EPIC) || startTime == null) return;
        this.endTime = startTime.plusMinutes(duration);
        if (this.type.equals(TaskType.SUB_TASK)) {
            ((SubTask) this).updateEpicTimer((Epic) Task.allTask.get(((SubTask) this).getEpicId()));
        }
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        if (this.type.equals(TaskType.EPIC)) {
            this.endTime = endTime;
        } else System.out.println("endTime является расчетным параметром, изменять его вручную нельзя");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTaskId() {
        return taskId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
        if (this.type.equals(TaskType.SUB_TASK)) {
            if (status == TaskStatus.IN_PROGRESS) {
                Task.allTask.get(((SubTask) this).getEpicId()).setStatus(TaskStatus.IN_PROGRESS);
            }
            if (status == TaskStatus.DONE) {
                boolean checkAllSubTaskStatusDone = true;
                for (int subTaskId : ((Epic) Task.allTask.get(((SubTask) this).getEpicId())).getSubTaskIdSet()) {
                    if (Task.allTask.get(subTaskId).getStatus() == TaskStatus.DONE) continue;
                    checkAllSubTaskStatusDone = false;
                    break;
                }
                if (checkAllSubTaskStatusDone) {
                    Task.allTask.get(((SubTask) this).getEpicId()).setStatus(TaskStatus.DONE);
                } else Task.allTask.get(((SubTask) this).getEpicId()).setStatus(TaskStatus.IN_PROGRESS);
            }
            if (status == TaskStatus.NEW) {
                boolean checkAllSubTaskStatusNew = true;
                for (int subTaskId : ((Epic) Task.allTask.get(((SubTask) this).getEpicId())).getSubTaskIdSet()) {
                    if (Task.allTask.get(subTaskId).getStatus() == TaskStatus.NEW) continue;
                    checkAllSubTaskStatusNew = false;
                    break;
                }
                if (checkAllSubTaskStatusNew) {
                    Task.allTask.get(((SubTask) this).getEpicId()).setStatus(TaskStatus.NEW);
                } else Task.allTask.get(((SubTask) this).getEpicId()).setStatus(TaskStatus.IN_PROGRESS);
            }
        }
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("task {" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskId=" + taskId +
                ", status=" + status +
                ", type=" + type +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", endTime=" + endTime + '}');
        return String.valueOf(sb);
    }

    public static ArrayList<Task> getPrioritizedTasks() {
        SortedSet<Task> taskTreeSet =
//            new TreeSet<>(Comparator.comparing
//            (task->task.getStartTime() == null ? LocalDateTime.MAX : task.getStartTime()));
//            new TreeSet<>(new StartTimeComparator());
                new TreeSet<>(Comparator.comparing(Task::getStartTime,
                        Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(Task::getTaskId));
        taskTreeSet.addAll(allTask.values());
        return new ArrayList<>(taskTreeSet);
    }

    public static void printIdSetFromTreeSetPrioritizedTasks() {
        System.out.println("Печать сокращенной информации по задачам в порядке приоритетов начала выполнения:");
        List<Task> tempList = getPrioritizedTasks();
        for (int k = 0; k < tempList.size(); k++) {
            System.out.println(k + 1 + ". id: " + tempList.get(k).getTaskId() +
                    ", startTime: " + tempList.get(k).getStartTime() + ", duration: " + tempList.get(k).getDuration() +
                    ", endTime: " + tempList.get(k).getEndTime());
        }
    }
}