package kstruk.sakila.util;

import java.util.concurrent.atomic.AtomicReference;

public class PropertiesHolder {

    private static final AtomicReference<Properties> CURRENT;

    static {
        var propertiesValues = new PropertiesLoader().load();
        var properties = new Properties(propertiesValues);
        CURRENT = new AtomicReference<>(properties);
    }

    public static Properties get() {
        return CURRENT.get();
    }

    public static void set(Properties properties) {
        CURRENT.set(properties);
    }

}
