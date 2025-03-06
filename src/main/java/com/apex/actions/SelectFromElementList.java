package com.apex.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SelectFromElementList {

    /**
     * Selects an item from a list based on its text.
     */
    public static void byText(List<WebElement> elements, String text) {
        if (!WaitUntil.allElementsExist(elements)) return; // Wait until all elements are present

        // Print all elements' text for debugging
        /*System.out.println("🔍 Available elements:");
        elements.forEach(el -> System.out.println("➡️ " + el.getText().trim()));*/

        elements.stream()
                .filter(el -> el.getText().trim().equalsIgnoreCase(text))
                .findFirst()
                .ifPresent(el -> {
                    Click.on(el);
                });
    }

    /**
     * Selects an item from a list based on its text.
     */
    public static void byTextJS(List<WebElement> elements, String text) {
        if (!WaitUntil.allElementsExist(elements)) return; // Wait until all elements are present

        // Print all elements' text for debugging
        System.out.println("🔍 Available elements:");
        elements.forEach(el -> System.out.println("➡️ " + el.getText().trim()));

        elements.stream()
                .filter(el -> el.getText().trim().equalsIgnoreCase(text))
                .findFirst()
                .ifPresent(el -> {
                    Click.forceClick(el);
                    WaitUntil.allElementsNotExist(elements); // Wait until elements disappear
                });
    }
}
