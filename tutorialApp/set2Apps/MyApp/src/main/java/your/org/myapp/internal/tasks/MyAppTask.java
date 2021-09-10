package your.org.myapp.internal.tasks;

import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.work.*;
import org.cytoscape.command.AvailableCommands;
import org.cytoscape.command.CommandExecutorTaskFactory;

import java.util.HashMap;
import java.util.Map;

public class MyAppTask extends AbstractTask implements TaskObserver {
    final CyServiceRegistrar registrar;
    SynchronousTaskManager taskManager = null;
    CommandExecutorTaskFactory taskFactory = null;
    public MyAppTask(final CyServiceRegistrar registrar){
        this.registrar = registrar;
    }
    public void run(TaskMonitor monitor){
        //execute commands
        /**
         * Get all services
         */
        if(taskManager==null)
            taskManager = this.registrar.getService(SynchronousTaskManager.class);
        if(taskFactory==null)
            taskFactory = this.registrar.getService(CommandExecutorTaskFactory.class);
        // Set our tunables
        Map<String, Object> args = new HashMap<>();
        args.put("nodeShape", "Diamond");
        args.put("lowerColor", "cyan");
        args.put("upperColor", "yellow");
        TaskIterator ti = taskFactory.createTaskIterator("MyApp", "HelloWorld", args, null);
        taskManager.execute(ti);
    }

    @Override
    public void taskFinished(ObservableTask observableTask) {

    }

    @Override
    public void allFinished(FinishStatus finishStatus) {

    }
}
