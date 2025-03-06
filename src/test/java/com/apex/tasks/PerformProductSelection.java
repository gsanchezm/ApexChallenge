package com.apex.tasks;

import com.apex.actions.*;
import com.apex.factory.WebDriverFactory;
import com.apex.pages.FiltersPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import static com.apex.actions.WaitUntil.elementExists;
import static com.apex.actions.WaitUntil.elementNotDisplayed;

public class PerformProductSelection extends FiltersPage {

    public PerformProductSelection andShowMore() {
        lnkViewMoreBrand.stream()
                .filter(el -> el.getText().contains("Ver más"))
                .forEach(Click::butScrollFirst);
        return this;
    }

    public PerformProductSelection cleanFilters(){
        if(elementExists(lnkCleanFilters)){
            Click.butScrollFirst(lnkCleanFilters);
            elementNotDisplayed(lnkCleanFilters);
        }
        return this;
    }

    public Integer filterAs(String id, String filter) {
        Pattern pattern = Pattern.compile("\\((\\d+)\\)"); // Regex to extract number inside ()

        return findElementByText(chkAllFilters, filter)
                .map(el -> {
                    Scroll.toElement(el);
                    WebElement brandCheckbox = WebDriverFactory.getInstance()
                            .getWebDriver()
                            .findElement(By.id(id + filter));

                    if (!CheckBox.isSelected(brandCheckbox)) {
                        Scroll.toElement(brandCheckbox);
                        CheckBox.select(brandCheckbox);
                    }

                    // Extract and store the product number
                    Matcher matcher = pattern.matcher(el.getText());

                    return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
                })
                .orElse(0); // Return 0 if no match is found
    }

    public PerformProductSelection filterPriceAs(String min, String max) {
        Enter.text(txtMinimum, min);
        Enter.text(txtMaximum, max);

        Click.on(spnNext);
        return  this;
    }

    public PerformProductSelection filterColorAs(String color){
        Click.on(color.equalsIgnoreCase("Blanco")?rdbWhite:rdbBlack);
        return this;
    }

    private Optional<WebElement> findElementByText(Iterable<WebElement> elements, String text) {
        return Optional.ofNullable(elements)
                .flatMap(list -> StreamSupport.stream(list.spliterator(), false)
                        .filter(el -> Obtain.text(el).toLowerCase().contains(text.toLowerCase()))
                        .findFirst());
    }
}