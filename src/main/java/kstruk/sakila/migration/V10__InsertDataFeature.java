package kstruk.sakila.migration;

import java.time.LocalDateTime;
import kstruk.sakila.dao.JooqContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.jooq.DSLContext;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.table;
import static org.jooq.impl.SQLDataType.INTEGER;
import static org.jooq.impl.SQLDataType.LOCALDATETIME;
import static org.jooq.impl.SQLDataType.VARCHAR;

@Slf4j
public class V10__InsertDataFeature extends BaseJavaMigration {

    private static final String TABLE_NAME = "feature";

    private final DSLContext dsl = JooqContextHolder.get();

    @Override
    public void migrate(Context context) throws Exception {
        try (var statement = context.getConnection().createStatement()) {
            String sql = DSL.using(dsl.dialect(), dsl.settings())
                .insertInto(table(name(TABLE_NAME)))
                .columns(
                    field(name("id"), INTEGER.identity(true)),
                    field(name("name"), VARCHAR(64)),
                    field(name("last_update"), LOCALDATETIME))
                .values(1, "Trailers", LocalDateTime.now())
                .values(2, "Commentaries", LocalDateTime.now())
                .values(3, "Deleted scenes", LocalDateTime.now())
                .values(4, "Behind the scenes", LocalDateTime.now())
                .getSQL(ParamType.INLINED);

            log.debug("Insert '{}' data: {}", TABLE_NAME, sql);
            statement.execute(sql);
        }
    }

}
