package your.org.myapp.internal.tasks;

import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class MyAppTaskFactory extends AbstractTaskFactory {
    final CyServiceRegistrar registrar;
    public MyAppTaskFactory(final CyServiceRegistrar registrar) {
        super();
        this.registrar = registrar;
    }

    public TaskIterator createTaskIterator () {
        return new TaskIterator(new MyAppTask(registrar));
    }

    public boolean isReady() { return true; }
}
