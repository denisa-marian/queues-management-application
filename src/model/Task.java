package model;

public class Task {
    private final int id;
    private final int arrivalTime;
    private int serviceTime;
    private int waitingTime;

    public Task(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.waitingTime = 0;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void decreaseServiceTime() {
        if (serviceTime > 0) {
            serviceTime--;
        }
    }

    public void incrementWaitingTime() {
        waitingTime++;
    }

    @Override
    public String toString() {
        return "(" + id + ", A:" + arrivalTime + ", S:" + serviceTime + ", W:" + waitingTime + ")";
    }
}
