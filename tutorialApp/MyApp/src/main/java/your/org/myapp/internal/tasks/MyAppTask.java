package your.org.myapp.internal.tasks;

import org.cytoscape.model.*;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MyAppTask extends AbstractTask {
    final CyNetworkManager networkManager;
    final CyNetworkFactory networkFactory;
    final CyNetworkViewFactory viewFactory ;
    final CyNetworkViewManager viewManager;
    final VisualMappingManager vmm ;
    final VisualMappingFunctionFactory continuousMappingFactory;
    final VisualStyleFactory visualStyleFactory;

    public MyAppTask(CyNetworkManager networkManager, CyNetworkFactory networkFactory, CyNetworkViewManager viewManager, CyNetworkViewFactory viewFactory, VisualMappingManager vmm, VisualStyleFactory visualStyleFactory, VisualMappingFunctionFactory continuousMappingFactory) {
        super();
        this.networkFactory = networkFactory;
        this.networkManager = networkManager;
        this.viewManager= viewManager;
        this.viewFactory = viewFactory;
        this.vmm = vmm;
        this.visualStyleFactory = visualStyleFactory;
        this.continuousMappingFactory = continuousMappingFactory;
    }
    public void run(TaskMonitor monitor){
        monitor.setProgress(-1.0);
        CyNetwork myNetwork = networkFactory.createNetwork();
        CyNode node1 = myNetwork.addNode();
        CyNode node2 = myNetwork.addNode();
        CyEdge edge1 = myNetwork.addEdge(node1,node2,false);

        myNetwork.getRow(node1).set(CyNetwork.NAME,"Node1");
        myNetwork.getRow(node1).set(CyNetwork.NAME,"Node2");
        myNetwork.getRow(edge1).set(CyNetwork.NAME,"Edge1");
        myNetwork.getRow(myNetwork).set(CyNetwork.NAME,"My Network");

        // Create new columns
        CyTable nodeTable = myNetwork.getDefaultNodeTable();
        if (nodeTable.getColumn("Hello") == null) {
            nodeTable.createListColumn("Hello", String.class, false);
        }
        if (nodeTable.getColumn("World") == null) {
            nodeTable.createColumn("World", Double.class, false);
        }

        myNetwork.getRow(node1).set("Hello", Arrays.asList("One", "Two","Three"));
        myNetwork.getRow(node2).set("Hello",Arrays.asList("Four", "Five","Six"));

        myNetwork.getRow(node1).set("World",1.0);
        myNetwork.getRow(node2).set("World",2.0);
        networkManager.addNetwork(myNetwork);
        monitor.setStatusMessage("Network Created");

        CyNetworkView view = viewFactory.createNetworkView(myNetwork);
        viewManager.addNetworkView(view);
        //setting the node shapes
        View<CyNode> nodeView1 = view.getNodeView(node1);
        View<CyNode> nodeView2 = view.getNodeView(node2);
        nodeView1.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.DIAMOND);
        nodeView2.setLockedValue(BasicVisualLexicon.NODE_SHAPE,NodeShapeVisualProperty.ELLIPSE);

        List<String> node1Hello = myNetwork.getRow(node1).getList("Hello", String.class);
        List<String> node2Hello = myNetwork.getRow(node2).getList("Hello", String.class);
        nodeView1.setLockedValue(BasicVisualLexicon.NODE_LABEL, node1Hello.get(0));
        nodeView2.setLockedValue(BasicVisualLexicon.NODE_LABEL, node2Hello.get(0));

        //visual style
        VisualStyle currStyle = vmm.getVisualStyle(view);
        VisualStyle temp = visualStyleFactory.createVisualStyle(currStyle);
        ContinuousMapping mapping = (ContinuousMapping) continuousMappingFactory.createVisualMappingFunction("World",
                Double.class,BasicVisualLexicon.NODE_FILL_COLOR);
        Double val1 = 2d;
        BoundaryRangeValues<Paint> brv1 =
                new BoundaryRangeValues<Paint>(Color.RED, Color.GREEN, Color.GREEN);
        Double val2 = 12d;
        BoundaryRangeValues<Paint> brv2 =
                new BoundaryRangeValues<Paint>(Color.YELLOW, Color.YELLOW, Color.BLACK);
        // Set the points
        mapping.addPoint(val1, brv1);
        mapping.addPoint(val2, brv2);
        // add the mapping to visual style
        temp.addVisualMappingFunction(mapping);
        vmm.addVisualStyle(temp);
        vmm.setVisualStyle(temp,view);
        monitor.setProgress(1.0);
    }
    public String getTitle(){
        return "MyApp Task 3";
    }
}
