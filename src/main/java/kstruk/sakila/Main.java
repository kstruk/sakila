package kstruk.sakila;

import com.google.gson.Gson;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import kstruk.sakila.context.FlywayHolder;
import kstruk.sakila.context.GsonHolder;
import kstruk.sakila.context.MigrationsCheckerHolder;
import kstruk.sakila.context.PropertiesHolder;
import kstruk.sakila.migration.MigrationsChecker;
import kstruk.sakila.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;

@Slf4j
public class Main {

    private static final Gson gson = GsonHolder.get();

    public static void main(String[] args) {
        var properties = PropertiesHolder.get();
        logStartup(properties);

        var flyway = FlywayHolder.get();
        var checker = MigrationsCheckerHolder.get();
        validateDataBase(flyway, checker);
    }

    private static void logStartup(Properties properties) {
        log.info("Starting app '{}' version {}",
            properties.getAppName(),
            properties.getAppVersion());

        var props = properties.toMap();
        log.info("App properties ({}){}",
            props.size(),
            getPropertiesList(props));
    }

    private static String getPropertiesList(Map<String, Object> props) {
        if (!log.isDebugEnabled()) {
            return "";
        }
        var filteredProps = props.entrySet().stream()
            .filter(Main::isNotSensitiveData)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
            ));
        return ": " + gson.toJson(filteredProps);
    }

    private static void validateDataBase(Flyway flyway, MigrationsChecker checker) {
        var migrations = flyway.info().all();
        log.info("Database migrations: {}", migrations.length);

        var migrationsNames = Arrays.stream(migrations)
            .map(MigrationInfo::getScript)
            .filter(n -> n.lastIndexOf(".") < n.length())
            .map(n -> n.substring(n.lastIndexOf(".") + 1))
            .collect(toCollection(TreeSet::new));

        checker.checkAllMigrationsApplied(migrationsNames);

        var maxMigration = migrationsNames.stream()
            .max(Comparator.naturalOrder())
            .orElseThrow();

        log.info("Current database version: {}", maxMigration);
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

}
