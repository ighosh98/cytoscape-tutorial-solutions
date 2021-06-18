package your.org.myapp.internal.tasks;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class MyAppTaskFactory extends AbstractTaskFactory {
    public MyAppTaskFactory() {
        super();
    }
    @Override
    public TaskIterator createTaskIterator() {
        return new TaskIterator();
    }

    @Override
    public boolean isReady() {
        return true;
    }
}
