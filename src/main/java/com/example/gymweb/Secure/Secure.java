package com.example.gymweb.Secure;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class Secure {
    public static String deleteTags (String text) {
        return Jsoup.clean(text, Safelist.none());
    }

    public static String deleteDangerous (String text) {
        return Jsoup.clean(text, Safelist.basic().addAttributes("h1", "h2", "h3", "h4", "h5", "h6"));
    }
}
