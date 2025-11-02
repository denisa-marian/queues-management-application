package businessLogic;

import model.Server;
import model.Task;
import java.util.List;

public abstract class Strategy {
    public abstract void addTask(List<Server> servers, Task task);
}
