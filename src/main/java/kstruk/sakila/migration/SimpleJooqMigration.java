package kstruk.sakila.migration;

import lombok.SneakyThrows;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;

public abstract class SimpleJooqMigration extends BaseJooqMigration {

    protected abstract Logger getLog();

    protected abstract String getSql();

    @Override
    public Integer getChecksum() {
        return getSql().hashCode();
    }

    @Override
    @SneakyThrows
    public void migrate(Context context) {
        try (var statement = getStatement(context)) {
            String sql = getSql();
            getLog().info(sql);
            statement.execute(sql);
        }
    }

}
