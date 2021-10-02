package kstruk.sakila.migration;

import kstruk.sakila.dao.JooqContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import static org.jooq.impl.DSL.name;
import static org.jooq.impl.SQLDataType.BOOLEAN;
import static org.jooq.impl.SQLDataType.INTEGER;
import static org.jooq.impl.SQLDataType.LOCALDATETIME;
import static org.jooq.impl.SQLDataType.VARCHAR;

@Slf4j
public class V8__CreateTableCustomer extends BaseJavaMigration {

    private static final String TABLE_NAME = "customer";

    private final DSLContext dsl = JooqContextHolder.get();

    @Override
    public void migrate(Context context) throws Exception {
        try (var statement = context.getConnection().createStatement()) {
            String sql = DSL.using(dsl.dialect(), dsl.settings())
                .createTableIfNotExists(name(TABLE_NAME))
                .column(name("id"), INTEGER.identity(true))
                .column(name("address_id"), INTEGER.notNull())
                .column(name("store_id"), INTEGER.notNull())
                .column(name("first_name"), VARCHAR(64).notNull())
                .column(name("last_name"), VARCHAR(64).notNull())
                .column(name("email"), VARCHAR(64).notNull())
                .column(name("active"), BOOLEAN.notNull())
                .column(name("last_update"), LOCALDATETIME.notNull())
                .primaryKey(name("id"))
                .getSQL();

            log.debug("Create '{}' table sql: {}", TABLE_NAME, sql);
            statement.execute(sql);
        }
    }

}
