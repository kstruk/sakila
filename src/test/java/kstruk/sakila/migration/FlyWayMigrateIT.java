package kstruk.sakila.migration;

import kstruk.sakila.dao.DataSourceHolder;
import org.flywaydb.core.Flyway;
import org.junit.Test;

public class FlyWayMigrateIT {

    @Test
    public void flyway_migrate() {
        Flyway flyway = Flyway.configure()
            .dataSource(DataSourceHolder.get())
            .locations("classpath:kstruk/sakila/migration")
            .load();

        flyway.migrate();
    }

}
