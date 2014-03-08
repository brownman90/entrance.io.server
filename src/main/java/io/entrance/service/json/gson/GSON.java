
package io.entrance.service.json.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public enum GSON {
    INSTANCE;

    private Gson gson = null;
    private GsonBuilder gsonBuilder = null;

    public Gson gson() {
        if (gsonBuilder == null) {
            gsonBuilder = new GsonBuilder().setPrettyPrinting();
        }

        if (gson == null) {
            gson = gsonBuilder.create();
        }

        return gson;
    }

}
