package kstruk.sakila.context;

import java.util.concurrent.atomic.AtomicReference;
import org.flywaydb.core.Flyway;

public class FlywayHolder {

    private static final AtomicReference<Flyway> CURRENT;

    static {
        var flyway = Flyway.configure()
            .dataSource(DataSourceHolder.get())
            .locations("classpath:kstruk/sakila/migration")
            .load();
        CURRENT = new AtomicReference<>(flyway);
    }

    public static Flyway get() {
        return CURRENT.get();
    }

}
