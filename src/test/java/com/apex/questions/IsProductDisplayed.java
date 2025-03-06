package com.apex.questions;

import com.apex.actions.Refresh;
import com.apex.actions.Scroll;
import com.apex.actions.WaitUntil;
import com.apex.pages.HomePage;
import org.testng.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.apex.actions.WaitUntil.textIsPresent;
import static com.apex.actions.WaitUntil.waitUntilTextChanges;

public class IsProductDisplayed extends HomePage {

    public IsProductDisplayed asCount(int productsCardSize){
        textIsPresent(lblResultCountProduct, productsCardSize +" Productos");
        Scroll.toElement(lblResultCountProduct);
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(lblResultCountProduct.getText());
        int resultProducts = matcher.find() ? Integer.parseInt(matcher.group()) : null;

        System.out.println("🔍 Verifying Products Found ...");
        System.out.println("✅ Expected: " + productsCardSize);
        System.out.println("🔢 Actual: " + resultProducts);

        Assert.assertEquals(resultProducts, productsCardSize, "❌ Product card size mismatch!");

        return this;
    }
}
