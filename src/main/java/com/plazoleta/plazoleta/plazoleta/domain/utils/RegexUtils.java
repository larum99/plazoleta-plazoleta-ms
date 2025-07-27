package com.plazoleta.plazoleta.plazoleta.domain.utils;

import java.util.regex.Pattern;

public class RegexUtils {

    private RegexUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static final Pattern DOCUMENT_PATTERN = Pattern.compile("^[0-9]+$");
    public static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{1,13}$");
    public static final Pattern RESTAURANT_NAME_PATTERN = Pattern.compile("^(?!\\d+$)[\\w\\s]+$");
    public static final Pattern URL_PATTERN = Pattern.compile("^(https?|ftp)://[^\\s/$.?#].[^\\s]*$");
}
