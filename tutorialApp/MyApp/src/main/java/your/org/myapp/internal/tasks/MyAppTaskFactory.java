package your.org.myapp.internal.tasks;

import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class MyAppTaskFactory extends AbstractTaskFactory {
    final CyNetworkManager netManager;
    final CyNetworkFactory netFactory;
    final CyNetworkViewManager viewManager;
    final CyNetworkViewFactory viewFactory;
    final	VisualMappingManager vmm;
    final	VisualMappingFunctionFactory cFactory;
    final	VisualStyleFactory vFactory;
    public MyAppTaskFactory(CyNetworkManager networkManager, CyNetworkFactory networkFactory,
                            CyNetworkViewManager viewManager, CyNetworkViewFactory viewFactory,
                            VisualMappingManager vmm, VisualStyleFactory vFactory,
                            VisualMappingFunctionFactory cFactory) {
        super();
        netManager = networkManager;
        netFactory = networkFactory;
        this.viewManager = viewManager;
        this.viewFactory = viewFactory;
        this.vmm = vmm;
        this.cFactory = cFactory;
        this.vFactory = vFactory;
    }

    public TaskIterator createTaskIterator () {
        return new TaskIterator(new MyAppTask(netManager, netFactory, viewManager, viewFactory, vmm, vFactory, cFactory));
    }

    public boolean isReady() { return true; }
}
