package com.projeto.ads.util;

import java.util.UUID;

public class Util {
    public static String generateToken() {
        UUID aux = UUID.randomUUID();
        String out = aux.toString().replaceAll("-", "").toLowerCase();
        return out;
    }
}
