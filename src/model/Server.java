package model;

import java.util.*;

public class Server {
    private Queue<Task> taskQueue;
    private List<Task> completedTasks;

    public Server() {
        taskQueue = new LinkedList<>();
        completedTasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        taskQueue.offer(task);
    }

    public void processOneTimeUnit() {
        incrementWaitingTimes();

        Task currentTask = taskQueue.peek();
        if (currentTask != null) {
            currentTask.decreaseServiceTime();
            if (currentTask.getServiceTime() <= 0) {
                completedTasks.add(taskQueue.poll());
                System.out.println("Task " + currentTask.getId() + " completed. Waiting Time: " + currentTask.getWaitingTime());
            }
        }
    }

    public void incrementWaitingTimes() {
        Iterator<Task> it = taskQueue.iterator();
        if (it.hasNext()) {
            it.next();
        }
        while (it.hasNext()) {
            it.next().incrementWaitingTime();
        }
    }

    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }

    public List<Task> getTasks() {
        return new ArrayList<>(taskQueue);
    }

    public List<Task> getCompletedTasks() {
        return completedTasks;
    }

    @Override
    public String toString() {
        return taskQueue.toString();
    }

    public int getWaitingTime() {
        int totalWaitingTime = 0;
        for (Task task : taskQueue) {
            totalWaitingTime += task.getWaitingTime();
        }
        return totalWaitingTime;
    }
}
