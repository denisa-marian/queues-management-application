package businessLogic;

import model.Server;
import model.Task;
import java.util.*;

public class Scheduler {
    private List<Server> queues;

    public Scheduler(List<Server> queues) {
        this.queues = queues;
    }

    public void dispatchTask(Task task) {
        Server shortest = queues.get(0);
        for (Server server : queues) {
            if (server.getTasks().size() < shortest.getTasks().size()) {
                shortest = server;
            }
        }
        shortest.addTask(task);
    }

    public List<Server> getQueues() {
        return queues;
    }
}
