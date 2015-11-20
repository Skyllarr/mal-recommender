package cz.muni.fi.pv254.init;

import cz.muni.fi.pv254.entity.MalUser;
import cz.muni.fi.pv254.repository.MalUserRepository;
import cz.muni.fi.pv254.utils.DomParser;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

/*
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Initializes application without web.xml file.
 */
@WebListener
class AppInitializer implements ServletContextListener {


    @Inject
    MalUserRepository malUserRepository;

    public void contextInitialized(ServletContextEvent sce) {


        //List<MalUser> list = malUserRepository.findAll();
        /*MalUser user = new MalUser();
        user.setName("User" + list.size());
        user.setBirthday(LocalDate.of(2000,1,1) );
        user.setLastSeen(OffsetDateTime.now());
        try {
            malUserRepository.create(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        list.stream().forEach((x) -> {
            System.out.println(x.getName());
            System.out.println(x.getBirthday());
            System.out.println(x.getLastSeen());
        });*/

        //create an instance
        //DomParser dpe = new DomParser();

        //call run example
        //dpe.run();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
