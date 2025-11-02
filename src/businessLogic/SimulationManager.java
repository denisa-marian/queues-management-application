package businessLogic;

import gui.SimulationFrame;
import model.Server;
import model.Task;
import java.util.*;

public class SimulationManager implements Runnable {
    private int timeLimit;
    private int numberOfServers;
    private int numberOfTasks;
    private int minArrival, maxArrival;
    private int minService, maxService;
    private Scheduler scheduler;
    private List<Task> generatedTasks;
    private SimulationFrame simulationFrame;


    public SimulationManager(int timeLimit, int numberOfServers, int numberOfTasks,
                             int minArrival, int maxArrival, int minService, int maxService,
                             SimulationFrame simulationFrame) {
        this.timeLimit = timeLimit;
        this.numberOfServers = numberOfServers;
        this.numberOfTasks = numberOfTasks;
        this.minArrival = minArrival;
        this.maxArrival = maxArrival;
        this.minService = minService;
        this.maxService = maxService;
        this.simulationFrame = simulationFrame;
        this.generatedTasks = new ArrayList<>();

    }

    private void generateTasks() {
        Random rand = new Random();
        for (int i = 1; i <= numberOfTasks; i++) {
            int arrival = minArrival + rand.nextInt(maxArrival - minArrival + 1);
            int service = minService + rand.nextInt(maxService - minService + 1);
            generatedTasks.add(new Task(i, arrival, service));
        }
        generatedTasks.sort(Comparator.comparingInt(Task::getArrivalTime));
    }

    private void runSimulation() {
        List<Server> servers = new ArrayList<>();
        for (int i = 0; i < numberOfServers; i++) {
            servers.add(new Server());
        }

        scheduler = new Scheduler(servers);
        long startTime = System.currentTimeMillis();
        int currentTime = 0;
        int totalWaitingTime = 0;  // Total waiting time for all clients, including unsaved
        int servedClientsCount = 0; // Count of served clients

        while ((System.currentTimeMillis() - startTime) < timeLimit * 1000 &&
                (!generatedTasks.isEmpty() || servers.stream().anyMatch(q -> !q.isEmpty()))) {

            print("Time " + currentTime + "s");

            List<Task> arrivedNow = new ArrayList<>();
            for (Task task : generatedTasks) {
                if (task.getArrivalTime() <= currentTime) {
                    scheduler.dispatchTask(task);
                    arrivedNow.add(task);
                }
            }
            generatedTasks.removeAll(arrivedNow);
            print("Waiting tasks: " + generatedTasks);

            List<Server> queues = scheduler.getQueues();
            for (int i = 0; i < queues.size(); i++) {
                print("Queue " + (i + 1) + ": " + queues.get(i));
                queues.get(i).processOneTimeUnit();
            }
            print("");
            currentTime++;

            try {
                Thread.sleep(1000); // 1 sec = 1 unit de timp
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        print("Simulation finished.");

        double totalWaitTime = 0;
        int totalClients = 0;

        // Calculăm media timpilor de așteptare pentru clienții care au fost serviți
        for (Server server : scheduler.getQueues()) {
            for (Task task : server.getCompletedTasks()) {
                totalWaitTime += task.getWaitingTime();
                totalClients++;
            }
        }

        // Incluzând și clienții neserviți în calculul mediei
        double avgWaitingTime = (totalClients + servedClientsCount == 0) ? 0 : (totalWaitTime + totalWaitingTime) / (totalClients + servedClientsCount);
        print("Average waiting time: " + avgWaitingTime);

        String avgWaiting = String.format("%.2f", avgWaitingTime);
        simulationFrame.saveSimulationToFile("simulation.txt", avgWaiting);
    }


    private void print(String msg) {
        simulationFrame.appendLog(msg + "\n");
    }

    @Override
    public void run() {
        generateTasks();
        runSimulation();
    }
}
