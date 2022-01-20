package kstruk.sakila.migration;

import java.sql.Statement;
import kstruk.sakila.context.JooqContextHolder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.jooq.DSLContext;

@RequiredArgsConstructor
public abstract class BaseJooqMigration extends BaseJavaMigration {

    @Getter(AccessLevel.PROTECTED)
    private final DSLContext dsl;

    public BaseJooqMigration() {
        this(JooqContextHolder.get());
    }

    @SneakyThrows
    protected Statement getStatement(Context context) {
        return context.getConnection().createStatement();
    }

}
