package kstruk.sakila.migration;

import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.junit.Test;
import org.reflections.Reflections;

import static java.util.function.Predicate.not;
import static kstruk.sakila.util.Conditions.checkState;

@Slf4j
public class MigrationsListIT {

    private static final String PACKAGE_NAME = "kstruk.sakila.migration";

    private static final String MIGRATIONS_LIST_FILE = "migrations.list";

    private static final Predicate<String> IS_MIGRATION_CLASS_NAME;

    private static final Predicate<String> IS_KNOWN_NON_MIGRATION_CLASS_NAME;

    static {
        IS_MIGRATION_CLASS_NAME = Pattern
            .compile("^[UVR][0-9_]*(__)[a-zA-Z0-9]*$")
            .asMatchPredicate();

        IS_KNOWN_NON_MIGRATION_CLASS_NAME = Set.of(
            "BaseJooqMigration",
            "SimpleJooqMigration"
        )::contains;
    }

    @Test
    public void check_all_migrations_are_listed() {
        var classes = findClasses(PACKAGE_NAME);
        var migrations = findMigrationsClasses(classes);
        checkState(migrations.isEmpty(),
            "Can't find any migrations classes");

        var loader = new FileMigrationsLoader(MIGRATIONS_LIST_FILE);
        var checker = new MigrationsChecker(MIGRATIONS_LIST_FILE, loader);

        checker.checkAllMigrationsListed(migrations);
    }

    @Test
    public void check_non_migrations_files() {
        var classes = findClasses(PACKAGE_NAME);

        String nonMigrations = classes.stream()
            .map(Class::getSimpleName)
            .filter(not(IS_MIGRATION_CLASS_NAME))
            .filter(not(IS_KNOWN_NON_MIGRATION_CLASS_NAME))
            .collect(Collectors.joining(", "));

        checkState(!nonMigrations.isEmpty(),
            "Non-migrations files found: %s",
            nonMigrations);

    }

    private Set<Class<? extends BaseJavaMigration>> findClasses(String packageName) {
        return new Reflections(packageName)
            .getSubTypesOf(BaseJavaMigration.class);
    }

    private Set<String> findMigrationsClasses(Set<Class<? extends BaseJavaMigration>> migrations) {
        return migrations.stream()
            .map(Class::getSimpleName)
            .filter(IS_MIGRATION_CLASS_NAME)
            .collect(Collectors.toSet());
    }

}
