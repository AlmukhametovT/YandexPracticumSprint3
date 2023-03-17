package main.manager;

import main.task.Task;

public class Node {
    private Task currentTask;
    private Node previousNode;
    private Node nextNode;

    public Node(Task currentTask, Node previousNode, Node nextNode) {
        this.currentTask = currentTask;
        this.previousNode = previousNode;
        this.nextNode = nextNode;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }
}