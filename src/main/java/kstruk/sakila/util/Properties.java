package kstruk.sakila.util;

import java.util.Map;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Properties {

    private final Map<String, Object> values;

    public String getAppName() {
        return (String) values.get("app.name");
    }

    public String getAppVersion() {
        return (String) values.get("app.version");
    }

    public String getDataBaseVendor() {
        return (String) values.get("database.vendor");
    }

    public String getDataBaseDialect() {
        return (String) values.get("database.dialect");
    }

    public String getDataBaseUrl() {
        return (String) values.get("database.url");
    }

    public String getDataBaseUser() {
        return (String) values.get("database.user");
    }

    public String getDataBasePass() {
        return (String) values.get("database.pass");
    }

    public Map<String, Object> toMap() {
        return new TreeMap<>(values);
    }

}
