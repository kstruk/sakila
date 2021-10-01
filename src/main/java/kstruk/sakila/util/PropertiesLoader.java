package kstruk.sakila.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
            .getResource(path);

        var propertiesPath = Optional.ofNullable(resource)
            .map(URL::getPath)
            .orElseThrow(() -> new IllegalStateException(String.format(
                "Missing properties resource: %s", path
            )));

        var appProps = new Properties();
        try {
            appProps.load(new FileInputStream(propertiesPath));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }

        var result = new HashMap<String, Object>();
        appProps.forEach((key, value) -> result.put((String) key, value));

        return result;
    }

}
