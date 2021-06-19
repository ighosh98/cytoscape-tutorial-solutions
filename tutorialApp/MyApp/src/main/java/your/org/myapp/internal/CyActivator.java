package your.org.myapp.internal;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.work.ServiceProperties;
import org.cytoscape.work.TaskFactory;
import org.osgi.framework.BundleContext;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyleFactory;

import java.util.Properties;

import your.org.myapp.internal.tasks.MyAppTaskFactory;

/**
 * {@code CyActivator} is a class that is a starting point for OSGi bundles.
 *
 * A quick overview of OSGi: The common currency of OSGi is the <i>service</i>.
 * A service is merely a Java interface, along with objects that implement the
 * interface. OSGi establishes a system of <i>bundles</i>. Most bundles import
 * services. Some bundles export services. Some do both. When a bundle exports a
 * service, it provides an implementation to the service's interface. Bundles
 * import a service by asking OSGi for an implementation. The implementation is
 * provided by some other bundle.
 *
 * When OSGi starts your bundle, it will invoke {@CyActivator}'s
 * {@code start} method. So, the {@code start} method is where
 * you put in all your code that sets up your app. This is where you import and
 * export services.
 *
 * Your bundle's {@code Bundle-Activator} manifest entry has a fully-qualified
 * path to this class. It's not necessary to inherit from
 * {@code AbstractCyActivator}. However, we provide this class as a convenience
 * to make it easier to work with OSGi.
 *
 * Note: AbstractCyActivator already provides its own {@code stop} method, which
 * {@code unget}s any services we fetch using getService().
 */
public class CyActivator extends AbstractCyActivator {
	/**
	 * This is the {@code start} method, which sets up your app. The
	 * {@code BundleContext} object allows you to communicate with the OSGi
	 * environment. You use {@code BundleContext} to import services or ask OSGi
	 * about the status of some service.
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		CyApplicationManager applicationManager = getService(context, CyApplicationManager.class);
		CyNetworkFactory networkFactory = getService(context,CyNetworkFactory.class);
		CyNetworkManager networkManager = getService(context,CyNetworkManager.class);
		CyNetworkViewFactory viewFactory = getService(context,CyNetworkViewFactory.class);
		CyNetworkViewManager viewManager = getService(context,CyNetworkViewManager.class);
		VisualMappingManager vmm = getService(context,VisualMappingManager.class);
		VisualStyleFactory visualStyleFactory = getService(context,VisualStyleFactory.class);
		//continuous mapping strategy -> Refer slides
		VisualMappingFunctionFactory continuousMappingFactory = getService(context,VisualMappingFunctionFactory.class,"(mapping.type=continuous)");
		Properties props = new Properties();
		props.put(ServiceProperties.PREFERRED_MENU,"Apps");
		props.put(ServiceProperties.TITLE,"Hello World!");
		TaskFactory factory = new MyAppTaskFactory(networkManager,networkFactory,viewManager,viewFactory,vmm,visualStyleFactory,continuousMappingFactory);

		//registers service with osgi
		registerService(context,factory,TaskFactory.class,props);
	}
}
