package cz.muni.fi.pv254.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/*
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Initializes application without web.xml file.
 */
@WebListener
class AppInitializer implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("test");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
