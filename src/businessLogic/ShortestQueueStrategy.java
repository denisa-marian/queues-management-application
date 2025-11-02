package businessLogic;

import model.Server;
import model.Task;
import java.util.List;

public class ShortestQueueStrategy extends Strategy {
    @Override
    public void addTask(List<Server> servers, Task task) {
        //serverul cu cea mai scurtă coadă
        Server best = servers.get(0);
        for (Server s : servers) {
            if (s.getTasks().size() < best.getTasks().size()) {
                best = s;
            }
        }
        best.addTask(task);
    }
}
