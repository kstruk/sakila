package kstruk.sakila.migration.v_0_1;

import kstruk.sakila.migration.SimpleJooqMigration;
import lombok.extern.slf4j.Slf4j;
import org.jooq.impl.DSL;
import org.slf4j.Logger;

import static org.jooq.impl.DSL.name;
import static org.jooq.impl.SQLDataType.INTEGER;
import static org.jooq.impl.SQLDataType.LOCALDATETIME;

@Slf4j
public class V0_1_1018_CreateTableRental extends SimpleJooqMigration {

    private static final String TABLE_NAME = "rental";

    @Override
    protected Logger getLog() {
        return log;
    }

    @Override
    protected String getSql() {
        return DSL.using(getDsl().dialect(), getDsl().settings())
            .createTableIfNotExists(name(TABLE_NAME))
            .column(name("id"), INTEGER.identity(true))
            .column(name("stuff_id"), INTEGER.notNull())
            .column(name("customer_id"), INTEGER.notNull())
            .column(name("inventory_id"), INTEGER.notNull())
            .column(name("rental_date"), LOCALDATETIME.notNull())
            .column(name("return_date"), LOCALDATETIME.notNull())
            .column(name("last_update"), LOCALDATETIME.notNull())
            .primaryKey(name("id"))
            .getSQL();
    }

}
