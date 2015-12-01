package cz.muni.fi.pv254.init;

import liquibase.integration.cdi.CDILiquibaseConfig;
import liquibase.integration.cdi.annotations.LiquibaseType;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

/**
 * Created by suomiy on 11/7/15.
 */
@ApplicationScoped
public class Setup {

    /**
     * Constructs SLF4J Logger based on the class into which it is injected.
     *
     * @return constructed logger.
     */
    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    /**
     * Provides entity manager.
     */
    @Produces
    @PersistenceContext(unitName = "PostgreSQLDS")
    private EntityManager em;


    @Resource(mappedName = "java:jboss/datasources/PostgreSQLDS")
    @Produces
    @LiquibaseType
    private DataSource dataSource;

    // null == All Entries
    public static final Long userMaxResultCount = null;
    public static final Long animeMaxResultCount = null;
    public static final boolean forbidEntitiesUpdates = false;

    @Produces
    @LiquibaseType
    public CDILiquibaseConfig createConfig() {
        CDILiquibaseConfig config = new CDILiquibaseConfig();
        config.setChangeLog("META-INF/db_changelog.xml");
        return config;
    }

    @Produces
    @LiquibaseType
    public ResourceAccessor create() {
        return new ClassLoaderResourceAccessor(getClass().getClassLoader());
    }
}
