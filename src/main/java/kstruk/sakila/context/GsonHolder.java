package kstruk.sakila.context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.atomic.AtomicReference;

public class GsonHolder {

    private static final AtomicReference<Gson> CURRENT;

    static {
        var gson = new GsonBuilder()
            .create();
        CURRENT = new AtomicReference<>(gson);
    }

    public static Gson get() {
        return CURRENT.get();
    }

}
