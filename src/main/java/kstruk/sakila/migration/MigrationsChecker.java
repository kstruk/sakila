package kstruk.sakila.migration;

import java.util.Set;
import java.util.TreeSet;
import lombok.extern.slf4j.Slf4j;

import static java.lang.String.join;
import static java.util.Comparator.naturalOrder;
import static kstruk.sakila.util.Conditions.checkNotNull;
import static kstruk.sakila.util.Conditions.checkState;

@Slf4j
public class MigrationsChecker {

    private final MigrationsLoader loader;

    private final String migrationsListPath;

    public static MigrationsChecker of(String migrationsFile) {
        var loader = new FileMigrationsLoader(migrationsFile);
        return new MigrationsChecker(migrationsFile, loader);
    }

    public MigrationsChecker(String migrationsFile, MigrationsLoader loader) {
        this.migrationsListPath = checkNotNull(migrationsFile, "migrationsFile");
        this.loader = checkNotNull(loader, "loader");
    }

    public void checkAllMigrationsApplied(Set<String> migrations) {
        var dbMigration = checkNotNull(migrations, "migrations");
        checkState(dbMigration.isEmpty(), "Migrations to check is empty");

        var appMigrations = loader.load();
        checkState(appMigrations.isEmpty(), "Migrations list is empty");

        var missingInDatabase = diff(dbMigration, appMigrations);
        if (missingInDatabase.size() > 0) {
            log.error("Missing migrations must be applied to database:\n{}",
                join("\n", missingInDatabase));

            String maxDbVersion = dbMigration.stream()
                .max(naturalOrder())
                .orElse("");

            String maxAppVersion = appMigrations.stream()
                .max(naturalOrder())
                .orElse("");

            checkState(!missingInDatabase.isEmpty(),
                "Database version is too old and should be upgraded:\n" +
                "Database    %s\n" +
                "Application %s\n",
                maxDbVersion, maxAppVersion);
        }

        var missingInApp = diff(appMigrations, dbMigration);
        if (missingInApp.size() > 0) {
            log.error("Missing migrations in application:\n{}",
                join("\n", missingInApp));

            String maxDbVersion = dbMigration.stream()
                .max(naturalOrder())
                .orElse("");

            String maxAppVersion = appMigrations.stream()
                .max(naturalOrder())
                .orElse("");

            checkState(!missingInApp.isEmpty(),
                "Application version is too old for this database:\n" +
                "Database    %s\n" +
                "Application %s\n",
                maxDbVersion, maxAppVersion);
        }
    }

    void checkAllMigrationsListed(Set<String> migrations) {
        var migrationFromClasses = checkNotNull(migrations, "migrations");
        checkState(migrationFromClasses.isEmpty(), "Migrations to check is empty");

        var migrationsFromList = loader.load();
        checkState(migrationsFromList.isEmpty(), "Migrations list is empty");

        var missingInList = diff(migrationsFromList, migrationFromClasses);

        if (missingInList.size() > 0) {
            log.error("Missing migrations must be added to '{}':\n{}",
                migrationsListPath, join("\n", missingInList));

            checkState(!missingInList.isEmpty(),
                "All migrations must be present in '%s'",
                migrationsListPath);
        }
    }

    private Set<String> diff(Set<String> base, Set<String> check) {
        var result = new TreeSet<String>();
        for (var it : check) {
            if (!base.contains(it)) {
                result.add(it);
            }
        }
        return result;
    }

}
