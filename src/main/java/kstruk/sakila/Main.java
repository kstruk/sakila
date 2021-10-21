package kstruk.sakila;

import java.util.Map;
import java.util.stream.Stream;
import javax.sql.DataSource;
import kstruk.sakila.dao.DataSourceHolder;
import kstruk.sakila.util.Properties;
import kstruk.sakila.util.PropertiesHolder;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;

import static java.util.stream.Collectors.joining;

@Slf4j
public class Main {

    public static void main(String[] args) {
        var properties = PropertiesHolder.get();
        logStartup(properties);

        var dataSource = DataSourceHolder.get();
        migrateDataBase(dataSource);
    }

    private static void logStartup(Properties properties) {
        log.info("Starting app '{}' version {}",
            properties.getAppName(),
            properties.getAppVersion());

        var props = properties.toMap();

        log.info("App properties ({}) {}",
            props.size(),
            log.isDebugEnabled()
                ? "\n" + getListOfProperties(props)
                : "");
    }

    private static void migrateDataBase(DataSource dataSource) {
        var flyway = Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:kstruk/sakila/migration")
            .load();

        var migrations = flyway.info().all();

        log.info("Migrating database ({}) {}",
            migrations.length,
            log.isDebugEnabled()
                ? "\n" + getListOfMigrations(migrations)
                : "");

        log.info("Validate migrations started");
        flyway.validate();
        log.info("Validate migrations done");
    }

    private static String getListOfProperties(Map<String, ?> props) {
        return props.entrySet().stream()
            .filter(Main::isNotSensitiveData)
            .map(Main::mapProperty)
            .collect(joining("\n"));
    }

    private static boolean isNotSensitiveData(Map.Entry<String, ?> entry) {
        return isNotSensitiveData(entry.getKey());
    }

    private static boolean isNotSensitiveData(String key) {
        return !key.contains("password")
               && !key.contains("pass")
               && !key.contains("token");
    }

    private static String mapProperty(Map.Entry<String, ?> entry) {
        return String.format("%s: %s",
            entry.getKey(),
            entry.getValue() != null
                ? "'" + entry.getValue() + "'"
                : "null");
    }

    private static String getListOfMigrations(MigrationInfo[] migrations) {
        return Stream.of(migrations)
            .map(Main::mapMigration)
            .collect(joining("\n"));
    }

    private static String mapMigration(MigrationInfo migration) {
        return migration.getVersion().getVersion() + " " + migration.getDescription();
    }

}
