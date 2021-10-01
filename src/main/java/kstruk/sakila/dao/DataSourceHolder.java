package kstruk.sakila.dao;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import javax.sql.DataSource;
import kstruk.sakila.util.Properties;
import kstruk.sakila.util.PropertiesHolder;
import org.h2.jdbcx.JdbcDataSource;

public class DataSourceHolder {

    private static final AtomicReference<DataSource> CURRENT;

    private static final Map<String, Function<Properties, DataSource>> PROVIDERS_MAPPING;

    static {
        PROVIDERS_MAPPING = new HashMap<>();
        PROVIDERS_MAPPING.put("mysql", DataSourceHolder::createMysqlDataSource);
        PROVIDERS_MAPPING.put("h2", DataSourceHolder::createH2DataSource);

        var properties = PropertiesHolder.get();
        var vendor = properties.getDataBaseVendor();
        var producer = PROVIDERS_MAPPING.get(vendor);
        var dataSource = producer.apply(properties);

        CURRENT = new AtomicReference<>(dataSource);
    }

    public static DataSource get() {
        return CURRENT.get();
    }

    public static void set(DataSource dataSource) {
        CURRENT.set(dataSource);
    }

    private static DataSource createMysqlDataSource(Properties properties) {
        var dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setUrl(properties.getDataBaseUrl());
        dataSource.setUser(properties.getDataBaseUser());
        dataSource.setPassword(properties.getDataBasePass());
        return dataSource;
    }

    private static DataSource createH2DataSource(Properties properties) {
        var dataSource = new JdbcDataSource();
        dataSource.setUrl(properties.getDataBaseUrl());
        dataSource.setUser(properties.getDataBaseUser());
        dataSource.setPassword(properties.getDataBasePass());
        return dataSource;
    }

}
