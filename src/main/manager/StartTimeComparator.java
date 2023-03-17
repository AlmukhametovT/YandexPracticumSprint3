package main.manager;

import main.task.Task;

import java.util.Comparator;

public class StartTimeComparator implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        try {
            if (o1.getStartTime().isAfter(o2.getStartTime())) {
                return 1;
            } else if (o1.getStartTime().isBefore(o2.getStartTime())) {
                return -1;
            }
//        if (o1.getStartTime() == null) {
//            return 1;
//        } else if (o2.getStartTime() == null) {
//            return -1;
//        }
            if (o1.getTaskId() < o2.getTaskId()) {
                return 1;
            } else return -1;
        } catch (NullPointerException e) {
            return 1;
        }

//        this.taskTreeSet = new TreeSet<>(new StartTimeComparator());
//        Можно не создавать отдельный класс. Один из способов:
//        this.taskTreeSet = new TreeSet<>((Task o1, Task o2) -> {
//            if (o1.getStartTime().isAfter(o2.getStartTime())) {
//                return 1;
//            } else if (o1.getStartTime().isBefore(o2.getStartTime())) {
//                return -1;
//            } else {
//                if (o1.getTaskId() > o2.getTaskId())
//                    return 1;
//                else
//                    return -1;
//            }
//        });
    }
}