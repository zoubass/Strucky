package cz.zoubelu.config.web;

import cz.zoubelu.config.*;
import cz.zoubelu.config.ds.DataSourceProd;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Created by t922274 on 19.9.2016.
 */
public class WebInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) {
		// Create the 'root' Spring application context
		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		//FIXME: Replace graphtest for graphconfig
		appContext.register(ApplicationConfig.class,GraphConfig.class,SchedulerConfig.class, DataSourceProd.class);

		// Manage the lifecycle of the root application context
		servletContext.addListener(new ContextLoaderListener(appContext));
		servletContext.setInitParameter("spring.profiles.active", "production");

		// Create the dispatcher servlet's Spring application context
		AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
		dispatcherServlet.register(Dispatcher.class);

		// Register and map the dispatcher servlet
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherServlet));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

	}

}