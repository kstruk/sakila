package kstruk.sakila.migration;

import java.util.Set;

public interface MigrationsLoader {

    public Set<String> load();

}
