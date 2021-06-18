package your.org.myapp.internal.tasks;

import org.cytoscape.model.*;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

public class myAppTask extends AbstractTask {
    final CyNetworkManager networkManager;
    final CyNetworkFactory networkFactory;

    public myAppTask(CyNetworkManager networkManager, CyNetworkFactory networkFactory) {
        super();
        this.networkFactory = networkFactory;
        this.networkManager = networkManager;
    }
    public void run(TaskMonitor monitor){
        monitor.setProgress(-1.0);
        CyNetwork myNetwork = networkFactory.createNetwork();
        CyNode node1 = myNetwork.addNode();;
        CyNode node2 = myNetwork.addNode();;
        CyEdge edge1 = myNetwork.addEdge(node1,node2,false);
        myNetwork.getRow(myNetwork).set(CyNetwork.NAME,"My Network");
        networkManager.addNetwork(myNetwork);
    }
    public String getTitle(){
        return "MyApp Task";
    }
}
