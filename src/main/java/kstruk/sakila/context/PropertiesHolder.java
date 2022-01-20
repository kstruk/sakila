package kstruk.sakila.context;

import java.util.concurrent.atomic.AtomicReference;
import kstruk.sakila.util.Properties;
import kstruk.sakila.util.PropertiesLoader;

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

}
