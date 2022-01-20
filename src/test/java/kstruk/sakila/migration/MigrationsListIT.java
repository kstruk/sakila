package kstruk.sakila.migration;

import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.junit.Test;
import org.reflections.Reflections;

import static kstruk.sakila.util.Conditions.checkState;

@Slf4j
public class MigrationsListIT {

    private static final Predicate<String> IS_MIGRATION_CLASS_NAME;

    static {
        IS_MIGRATION_CLASS_NAME = Pattern
            .compile("^[UVR][0-9_]*(__)[a-zA-Z0-9]*$")
            .asMatchPredicate();
    }

    @Test
    public void check_all_migrations_are_listed() {
        var migrationFromClasses = findMigrationsClasses("kstruk.sakila.migration");
        checkState(migrationFromClasses.isEmpty(), "Can't find any migrations classes");

        var loader = new FileMigrationsLoader("migrations.list");
        var checker = new MigrationsChecker("migrations.list", loader);

        checker.checkAllMigrationsListed(migrationFromClasses);
    }

    private Set<String> findMigrationsClasses(String packageName) {
        var allTypes = new Reflections(packageName)
            .getSubTypesOf(BaseJavaMigration.class);

        return allTypes.stream()
            .map(Class::getSimpleName)
            .filter(IS_MIGRATION_CLASS_NAME)
            .collect(Collectors.toSet());
    }

}
