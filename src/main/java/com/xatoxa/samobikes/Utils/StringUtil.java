package com.xatoxa.samobikes.Utils;

import com.xatoxa.samobikes.entities.Bike;

public final class StringUtil {
    public static String reverseSortDir(String sortDir){
        return sortDir.equals("asc") ? "desc" : "asc";
    }

    public static String makeHistoryType(Bike bike, String msg){
        StringBuilder type = new StringBuilder();
        type
                .append("Велосипед ")
                .append(bike.getNumber())
                .append(" | ")
                .append(bike.getQrNumber())
                .append(" | ")
                .append(bike.getVIN())
                .append(msg);

        return type.toString();
    }

    public static String makeHistoryType(String username, String msg){
        StringBuilder type = new StringBuilder();
        type
                .append("Пользователь ")
                .append(username)
                .append(msg);

        return type.toString();
    }

    public static String createBikePageRedirectLink(int id, String currentPage, String sortField,
                                              String sortDir, String commentSortDir, String keyword){
        StringBuilder redirectLink = new StringBuilder();

        redirectLink
                .append("redirect:/bikes/show/")
                .append(id)
                .append("?currentPage=")
                .append(currentPage)
                .append("&sortField=")
                .append(sortField)
                .append("&sortDir=")
                .append(sortDir)
                .append("&commentSortField=commentedAt&commentSortDir=")
                .append(commentSortDir)
                .append(keyword != null ? "&keyword=" + keyword : "");

        return redirectLink.toString();
    }
}
