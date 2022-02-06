package kstruk.sakila.migration;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;
import javax.sql.DataSource;
import kstruk.sakila.context.DataSourceHolder;
import kstruk.sakila.context.FlywayHolder;
import kstruk.sakila.context.GsonHolder;
import kstruk.sakila.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.output.InfoOutput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Slf4j
public class FlyWayMigrateIT {

    private static final Gson gson = GsonHolder.get();

    private DataSource originalDc;

    private DataSource temporalDc;

    @Before
    public void setUp() {
        var props = getTempDbProps();
        temporalDc = DataSourceHolder.createH2DataSource(props);

        originalDc = DataSourceHolder.get();
        DataSourceHolder.set(originalDc, temporalDc);
    }

    @After
    public void tearDown() {
        DataSourceHolder.set(temporalDc, originalDc);
    }

    @Test
    public void flyway_migrate() {
        var flyway = FlywayHolder.get();

        var migrate = flyway.migrate();
        log.debug(
            "Migration result: {}",
            gson.toJson(migrate));

        assertTrue(String.format(
            "Must be zero warnings: %s",
            String.join(", ", migrate.warnings)
        ), migrate.warnings.isEmpty());

        var migrations = flyway.info()
            .getInfoResult()
            .migrations;

        assertFalse(
            "List of migrations is empty",
            migrations.isEmpty());

        var unsuccessfulMigrations = migrations.stream()
            .filter(m -> !m.state.equals("Success"))
            .map(this::format)
            .collect(toList());

        assertTrue(String.format(
            "Where are unsuccessful migrations: %s",
            String.join(", ", unsuccessfulMigrations)
        ), unsuccessfulMigrations.isEmpty());
    }

    private String format(InfoOutput output) {
        return String.format(
            "(%s, '%s', %s)",
            output.version,
            output.description,
            output.state);
    }

    private static Properties getTempDbProps() {
        return new Properties(Map.of(
            "database.url", getTempDbUrl(),
            "database.user", "sakila",
            "database.pass", "sakila"));
    }

    private static String getTempDbUrl() {
        try {
            var tempFile = File.createTempFile("sakila", "");
            log.debug(
                "Using temp database: {}",
                tempFile.getAbsolutePath());

            var url = String.format(
                "jdbc:h2:file:%s;" +
                "MODE=MySQL;" +
                "DATABASE_TO_LOWER=TRUE;" +
                "CASE_INSENSITIVE_IDENTIFIERS=TRUE;",
                tempFile);

            return url;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

}
