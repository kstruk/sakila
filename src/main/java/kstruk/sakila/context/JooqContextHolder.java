package kstruk.sakila.context;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import static org.jooq.SQLDialect.MYSQL;
import static org.jooq.conf.ParamType.NAMED;
import static org.jooq.conf.RenderKeywordCase.UPPER;
import static org.jooq.conf.RenderOptionalKeyword.ON;

public class JooqContextHolder {

    private static final AtomicReference<DSLContext> CURRENT;

    static {
        var dataSource = DataSourceHolder.get();
        var dialectValue = PropertiesHolder.get().getDataBaseDialect();
        var dialect = getDialect(dialectValue);

        Settings settings = new Settings()
            .withParamType(NAMED)
            .withRenderKeywordCase(UPPER)
            .withRenderOptionalInnerKeyword(ON)
            .withRenderOptionalAsKeywordForFieldAliases(ON)
            .withRenderOptionalAsKeywordForTableAliases(ON);

        DSLContext context = DSL.using(dataSource, dialect, settings);
        CURRENT = new AtomicReference<>(context);
    }

    public static DSLContext get() {
        return CURRENT.get();
    }

    private static SQLDialect getDialect(String dialect) {
        SQLDialect result = null;
        if ("mysql".equals(dialect)) {
            result = MYSQL;
        }

        return Objects.requireNonNull(result, () -> String.format(
            "Unknown database dialect provided: %s",
            dialect
        ));
    }

}
