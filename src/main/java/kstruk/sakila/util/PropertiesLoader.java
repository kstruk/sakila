package kstruk.sakila.util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PropertiesLoader {

    private final String path;

    public PropertiesLoader() {
        this("application.properties");
    }

    public Map<String, Object> load() {
        var resource = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(path);

        var appProps = new Properties();
        try {
            appProps.load(resource);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }

        var result = new LinkedHashMap<String, Object>();
        appProps.forEach((key, value) -> result.put((String) key, value));

        return result;
    }

}
