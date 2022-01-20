package kstruk.sakila.migration.v_0_1;

import java.time.LocalDateTime;
import kstruk.sakila.migration.SimpleJooqMigration;
import lombok.extern.slf4j.Slf4j;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.slf4j.Logger;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.table;
import static org.jooq.impl.SQLDataType.INTEGER;
import static org.jooq.impl.SQLDataType.LOCALDATETIME;
import static org.jooq.impl.SQLDataType.VARCHAR;

@Slf4j
public class V0_1_1017__InsertDataRating extends SimpleJooqMigration {

    private static final String TABLE_NAME = "rating";

    @Override
    protected Logger getLog() {
        return log;
    }

    @Override
    protected String getSql() {
        var updated = LocalDateTime.of(2021, 10, 1, 12, 0, 0, 0);

        return DSL.using(getDsl().dialect(), getDsl().settings())
            .insertInto(table(name(TABLE_NAME)))
            .columns(
                field(name("id"), INTEGER.identity(true)),
                field(name("name"), VARCHAR(64)),
                field(name("last_update"), LOCALDATETIME))
            .values(1, "G", updated)
            .values(2, "PG", updated)
            .values(3, "PG 13", updated)
            .values(4, "NC 17", updated)
            .values(5, "R", updated)
            .getSQL(ParamType.INLINED);
    }

}
