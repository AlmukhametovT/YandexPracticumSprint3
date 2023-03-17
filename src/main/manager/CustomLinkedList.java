package main.manager;

import main.task.Task;

import java.util.ArrayList;
import java.util.List;

public class CustomLinkedList {
    private Node first;
    private Node last;
    private int counter;

    public Node getFirst() {
        return first;
    }

    public Node getLast() {
        return last;
    }

    public int getCounter() {
        return counter;
    }

    public CustomLinkedList() {
        first = null;
        last = null;
        counter = -1;
    }

    public void linkLast(Task task) {
        counter++;
        if (last == null) {
            Node firstNode = new Node(task, null, null);
            last = firstNode;
            return;
        }
        if (first == null) {
            first = last;
            Node secondNode = new Node(task, first, null);
            last = secondNode;
            first.setNextNode(last);
            return;
        }
        Node node = new Node(task, last, null);
        last.setNextNode(node);
        last = node;
    }

    public List<Task> getTasks() {
        List<Task> history = new ArrayList<>();
        if (last == null) return null;
        Node enumeration = last;
        while (true) {
            history.add(enumeration.getCurrentTask());
            enumeration = enumeration.getPreviousNode();
            if (enumeration == null) break;
        }
        return history;
    }

    public void removeNode(Node node) {
        counter--;
        if (node.getNextNode() == null && node.getPreviousNode() == null) {
            first = null;
            last = null;
            return;
        }
        if (node.getNextNode() == null) {
            last = node.getPreviousNode();
            last.setNextNode(null);
            return;
        }
        if (node.getPreviousNode() == null) {
            first = node.getNextNode();
            first.setPreviousNode(null);
            return;
        }
        node.getPreviousNode().setNextNode(node.getNextNode());
        node.getNextNode().setPreviousNode(node.getPreviousNode());
    }
}