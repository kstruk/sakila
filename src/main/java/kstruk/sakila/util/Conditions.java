package kstruk.sakila.util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.experimental.UtilityClass;

/**
 * Utility helper for conditions and state check.
 */
@UtilityClass
public class Conditions {

    /**
     * Shortcut to {@link Optional#ofNullable}
     */
    public static <Type> Optional<Type> nullable(Type value) {
        return Optional.ofNullable(value);
    }

    /**
     * Shortcut to {@link Objects#isNull}
     */
    public static boolean isNull(Object value) {
        return Objects.isNull(value);
    }

    /**
     * Shortcut to {@link Objects#nonNull}
     */
    public static boolean isNotNull(Object value) {
        return Objects.nonNull(value);
    }

    /**
     * @throws NullPointerException when value is null
     */
    public static <Type> Type checkNotNull(Type value) {
        return Objects.requireNonNull(value);
    }

    /**
     * @throws NullPointerException when value is null
     */
    public static <Type> Type checkNotNull(Type value, String name) {
        return Objects.requireNonNull(value, () -> name + " must be not null");
    }

    /**
     * @throws NullPointerException when value is null
     */
    public static <Type> Type checkNotNull(Type value, Supplier<String> message) {
        return Objects.requireNonNull(value, message);
    }

    /**
     * @throws IllegalArgumentException when condition is true
     */
    public static <Type> Type checkArg(Type value, boolean condition, String message) {
        if (condition) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    /**
     * @throws IllegalArgumentException when condition is true
     */
    public static <Type> Type checkArg(Type value, boolean condition, String format, Object... args) {
        if (condition) {
            String message = String.format(format, args);
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    /**
     * @throws IllegalArgumentException when condition is true
     */
    public static <Type> Type checkArg(Type value, boolean condition, Supplier<String> message) {
        if (condition) {
            throw new IllegalArgumentException(message.get());
        }
        return value;
    }

    /**
     * @throws IllegalArgumentException when condition is true
     */
    public static <Type> Type checkArg(Type value, Predicate<Type> condition, Supplier<String> message) {
        if (condition.test(value)) {
            throw new IllegalArgumentException(message.get());
        }
        return value;
    }

    /**
     * @throws IllegalStateException when condition is true
     */
    public static void checkState(boolean condition, String message) {
        if (condition) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * @throws IllegalStateException when condition is true
     */
    public static void checkState(boolean condition, String format, Object... args) {
        if (condition) {
            String message = String.format(format, args);
            throw new IllegalStateException(message);
        }
    }

    /**
     * @throws IllegalStateException when condition is true
     */
    public static void checkState(boolean condition, Supplier<String> message) {
        if (condition) {
            throw new IllegalStateException(message.get());
        }
    }

    /**
     * @throws IllegalStateException when condition is true
     */
    public static void checkState(Supplier<Boolean> condition, Supplier<String> message) {
        if (condition.get()) {
            throw new IllegalStateException(message.get());
        }
    }

    public static String checkArgNotEmpty(String value, String name) {
        return checkArg(value, notExistsOrEmpty(value), () -> name + " must be not empty");
    }

    public static <Key, Value> Map<Key, Value> checkArgNotEmpty(Map<Key, Value> value, String name) {
        return checkArg(value, notExistsOrEmpty(value), () -> name + " must be not empty");
    }

    public static <Value, Type extends Collection<Value>> Type checkArgNotEmpty(Type value, String name) {
        return checkArg(value, notExistsOrEmpty(value), () -> name + " must be not empty");
    }

    public static <Type> Type[] checkArgNotEmpty(Type[] value, String name) {
        return checkArg(value, notExistsOrEmpty(value), () -> name + " must be not empty");
    }

    public static void checkStateNotEmpty(String value, String name) {
        checkState(notExistsOrEmpty(value), () -> name + " must be not empty");
    }

    public static <Key, Value> void checkStateNotEmpty(Map<Key, Value> value, String name) {
        checkState(notExistsOrEmpty(value), () -> name + " must be not empty");
    }

    public static <Value, Type extends Collection<Value>> void checkStateNotEmpty(Type value, String name) {
        checkState(notExistsOrEmpty(value), () -> name + " must be not empty");
    }

    public static <Type> void checkStateNotEmpty(Type[] value, String name) {
        checkState(notExistsOrEmpty(value), () -> name + " must be not empty");
    }

    /**
     * Check that variable is not null but empty
     */
    public static boolean existsAndEmpty(String value) {
        return value != null && value.trim().length() == 0;
    }

    /**
     * Check that variable is not null but empty
     */
    public static boolean existsAndEmpty(Map<?, ?> value) {
        return value != null && value.isEmpty();
    }

    /**
     * Check that variable is not null but empty
     */
    public static boolean existsAndEmpty(Collection<?> value) {
        return value != null && value.isEmpty();
    }

    /**
     * Check that variable is not null but empty
     */
    public static boolean existsAndEmpty(Object[] value) {
        return value != null && value.length == 0;
    }

    /**
     * Check that variable is not null and not empty
     */
    public static boolean existsNotEmpty(String value) {
        return value != null && value.trim().length() > 0;
    }

    /**
     * Check that variable is not null and not empty
     */
    public static boolean existsNotEmpty(Number value) {
        return value != null && value.doubleValue() > 0;
    }

    /**
     * Check that variable is not null and not empty
     */
    public static boolean existsNotEmpty(Map<?, ?> value) {
        return value != null && !value.isEmpty();
    }

    /**
     * Check that variable is not null and not empty
     */
    public static boolean existsNotEmpty(Collection<?> value) {
        return value != null && !value.isEmpty();
    }

    /**
     * Check that variable is not null and not empty
     */
    public static boolean existsNotEmpty(Object[] value) {
        return value != null && value.length > 0;
    }

    /**
     * Check that variable is null or empty
     */
    public static boolean notExistsOrEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    /**
     * Check that variable is null or empty
     */
    public static boolean notExistsOrEmpty(Number value) {
        return value == null || value.doubleValue() == 0;
    }

    /**
     * Check that variable is null or empty
     */
    public static boolean notExistsOrEmpty(Map<?, ?> value) {
        return value == null || value.isEmpty();
    }

    /**
     * Check that variable is null or empty
     */
    public static boolean notExistsOrEmpty(Collection<?> value) {
        return value == null || value.isEmpty();
    }

    /**
     * Check that variable is null or empty
     */
    public static boolean notExistsOrEmpty(Object[] value) {
        return value == null || value.length == 0;
    }

    /**
     * Check that variable is not null and equals some value
     */
    public static boolean existsAndEqual(String value, String expect) {
        return value != null && value.trim().equals(expect);
    }

    /**
     * Check that variable is not null and equals some value
     */
    public static boolean existsAndEqual(Number value, Number expect) {
        return value != null && value.equals(expect);
    }

    /**
     * Check that variable is not null and not equals some value
     */
    public static boolean existsNotEqual(String value, String expect) {
        return value != null && !value.trim().equals(expect);
    }

    /**
     * Check that variable is not null and not equals some value
     */
    public static boolean existsNotEqual(Number value, Number expect) {
        return value != null && !value.equals(expect);
    }

}

