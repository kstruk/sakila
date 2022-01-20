package kstruk.sakila.migration;

import kstruk.sakila.context.FlywayHolder;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.junit.Test;

@Slf4j
public class FlyWayMigrateIT {

    @Test
    public void flyway_migrate() {
        Flyway flyway = FlywayHolder.get();

        var migrateResult = flyway.migrate();
        var current = flyway.info().current();
    }

}
