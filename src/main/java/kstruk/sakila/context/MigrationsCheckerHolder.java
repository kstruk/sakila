package kstruk.sakila.context;

import java.util.concurrent.atomic.AtomicReference;
import kstruk.sakila.migration.FileMigrationsLoader;
import kstruk.sakila.migration.MigrationsChecker;

public class MigrationsCheckerHolder {

    private static final AtomicReference<MigrationsChecker> CURRENT;

    static {
        var file = "migrations.list";
        var loader = new FileMigrationsLoader(file);
        var checker = new MigrationsChecker("", loader);
        CURRENT = new AtomicReference<>(checker);
    }

    public static MigrationsChecker get() {
        return CURRENT.get();
    }

}
