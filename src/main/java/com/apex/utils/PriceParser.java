package com.apex.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceParser {

    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d+");

    /**
     * Parses a price range string like "$20,000 - $30,000 MXN"
     * to extract the minimum and maximum numeric values (e.g. "20000", "30000").
     *
     * @param priceRange e.g. "$20,000 - $30,000 MXN"
     * @return a String[] of length 2: [minPrice, maxPrice]
     */
    public static String[] parsePriceRange(String priceRange) {
        Matcher matcher = DIGIT_PATTERN.matcher(priceRange);
        String[] priceParts = new String[2];
        int index = 0;

        // Find all sequences of digits in the string
        while (matcher.find() && index < 2) {
            priceParts[index++] = matcher.group();
        }

        // If the string did not have two numbers, fill them with "0"
        if (priceParts[0] == null) priceParts[0] = "0";
        if (priceParts[1] == null) priceParts[1] = "0";

        return priceParts;
    }
}