package kstruk.sakila.migration;

import java.util.Set;
import org.junit.Test;

public class MigrationsCheckerTest {

    @Test
    public void db_version_same_as_app() {
        MigrationsLoader loader = () -> Set.of(
            "V0_1_1001__in_app_and_db");

        var checker = new MigrationsChecker("migrations", loader);

        Set<String> dbMigrations = Set.of(
            "V0_1_1001__in_app_and_db");

        checker.checkAllMigrationsApplied(dbMigrations);
    }

    @Test(expected = IllegalStateException.class)
    public void db_version_is_old() {
        MigrationsLoader loader = () -> Set.of(
            "V0_1_1001__in_app_and_db",
            "V0_1_1002__only_in_app");

        var checker = new MigrationsChecker("migrations", loader);

        Set<String> dbMigrations = Set.of(
            "V0_1_1001__in_app_and_db");

        checker.checkAllMigrationsApplied(dbMigrations);
    }

    @Test(expected = IllegalStateException.class)
    public void app_version_is_old() {
        MigrationsLoader loader = () -> Set.of(
            "V0_1_1001__in_app_and_db");

        var checker = new MigrationsChecker("migrations", loader);

        Set<String> dbMigrations = Set.of(
            "V0_1_1001__in_app_and_db",
            "V0_1_1002__only_in_db");

        checker.checkAllMigrationsApplied(dbMigrations);
    }

}
