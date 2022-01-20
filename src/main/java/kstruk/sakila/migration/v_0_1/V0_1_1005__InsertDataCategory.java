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
public class V0_1_1005__InsertDataCategory extends SimpleJooqMigration {

    private static final String TABLE_NAME = "category";

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
            .values(1, "Action", updated)
            .values(2, "Animation", updated)
            .values(3, "Children", updated)
            .values(4, "Classics", updated)
            .values(5, "Comedy", updated)
            .values(6, "Documentary", updated)
            .values(7, "Drama", updated)
            .values(8, "Family", updated)
            .values(9, "Foreign", updated)
            .values(10, "Games", updated)
            .values(11, "Horror", updated)
            .values(12, "Music", updated)
            .values(13, "New", updated)
            .values(14, "Sci-Fi", updated)
            .values(15, "Sports", updated)
            .values(16, "Travel", updated)
            .getSQL(ParamType.INLINED);
    }

}
