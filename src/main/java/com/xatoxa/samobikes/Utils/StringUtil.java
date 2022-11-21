package com.xatoxa.samobikes.Utils;

public final class StringUtil {
    public static String reverseSortDir(String sortDir){
        return sortDir.equals("asc") ? "desc" : "asc";
    }
}
