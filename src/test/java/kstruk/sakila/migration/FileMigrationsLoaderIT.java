package kstruk.sakila.migration;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class FileMigrationsLoaderIT {

    private static final String MIGRATIONS_LIST = "migrations.list";

    @Test
    public void load_project_migrations() {
        var loader = new FileMigrationsLoader(MIGRATIONS_LIST);
        var migrations = loader.load();

        assertFalse(
            "Migrations names must be present in " + MIGRATIONS_LIST,
            migrations.isEmpty());

        boolean commentsPresent = migrations.stream()
            .anyMatch(s -> s.startsWith("#"));

        assertFalse(
            "Migrations names must not contains commented lines",
            commentsPresent);
    }

}
