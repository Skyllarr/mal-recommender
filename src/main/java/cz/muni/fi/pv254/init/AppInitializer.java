package cz.muni.fi.pv254.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.Console;

/**
 * Initializes application without web.xml file.
 */
@WebListener
class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {

        System.out.println("test");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
