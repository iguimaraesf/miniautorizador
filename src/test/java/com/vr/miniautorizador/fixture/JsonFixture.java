package com.vr.miniautorizador.fixture;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonFixture {
    private static final Gson GSON = new Gson();
    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }
}
