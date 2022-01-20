package kstruk.sakila.migration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Set;
import java.util.stream.Collectors;

import static kstruk.sakila.util.Conditions.checkNotNull;
import static kstruk.sakila.util.Conditions.checkState;

public class FileMigrationsLoader implements MigrationsLoader {

    private final String path;

    public FileMigrationsLoader(String path) {
        this.path = checkNotNull(path, "path");
    }

    public Set<String> load() {
        var is = ClassLoader.getSystemResourceAsStream(path);
        checkState(is == null, "Can't find 'migrations.list'");

        try (var isr = new InputStreamReader(is);
             var reader = new BufferedReader(isr)) {

            return reader.lines()
                .map(String::trim)
                .filter(line -> !line.isBlank())
                .filter(line -> !line.startsWith("#"))
                .collect(Collectors.toSet());

        } catch (IOException ex) {
            throw new UncheckedIOException("Can't read 'migrations.list'", ex);
        }
    }

}
