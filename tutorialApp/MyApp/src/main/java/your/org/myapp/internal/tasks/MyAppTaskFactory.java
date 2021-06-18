package your.org.myapp.internal.tasks;

import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class MyAppTaskFactory extends AbstractTaskFactory {
    final CyNetworkManager networkManager;
    final CyNetworkFactory networkFactory;
    public MyAppTaskFactory(CyNetworkManager networkManager, CyNetworkFactory networkFactory) {
        super();
        this.networkFactory = networkFactory;
        this.networkManager = networkManager;
    }
    @Override
    public TaskIterator createTaskIterator() {
        return new TaskIterator( new myAppTask(networkManager,networkFactory));
    }

    @Override
    public boolean isReady() {
        return true;
    }
}
