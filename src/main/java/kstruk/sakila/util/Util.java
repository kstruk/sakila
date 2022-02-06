package kstruk.sakila.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Util {

    /**
     * Return default {@link Object#toString} value for parameter
     */
    public static String toString(Object value) {
        return value.getClass().getName() + "@" + Integer.toHexString(value.hashCode());
    }

}
