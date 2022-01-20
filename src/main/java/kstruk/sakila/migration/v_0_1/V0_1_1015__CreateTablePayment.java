package kstruk.sakila.migration.v_0_1;

import kstruk.sakila.migration.SimpleJooqMigration;
import lombok.extern.slf4j.Slf4j;
import org.jooq.impl.DSL;
import org.slf4j.Logger;

import static org.jooq.impl.DSL.name;
import static org.jooq.impl.SQLDataType.DECIMAL;
import static org.jooq.impl.SQLDataType.INTEGER;
import static org.jooq.impl.SQLDataType.LOCALDATETIME;

@Slf4j
public class V0_1_1015__CreateTablePayment extends SimpleJooqMigration {

    private static final String TABLE_NAME = "payment";

    @Override
    protected Logger getLog() {
        return log;
    }

    @Override
    protected String getSql() {
        return DSL.using(getDsl().dialect(), getDsl().settings())
            .createTableIfNotExists(name(TABLE_NAME))
            .column(name("id"), INTEGER.identity(true))
            .column(name("customer_id"), INTEGER.notNull())
            .column(name("stuff_id"), INTEGER.notNull())
            .column(name("amount"), DECIMAL(20, 4).notNull())
            .column(name("payment_update"), LOCALDATETIME.notNull())
            .column(name("last_update"), LOCALDATETIME.notNull())
            .primaryKey(name("id"))
            .getSQL();
    }

}
