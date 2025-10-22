package com.imdb.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

public class BasePage {

    protected void openUrl(String url) {
        Selenide.open(url);
    }

    protected void typeText(SelenideElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    protected void pressEnter(SelenideElement element) {
        element.sendKeys(Keys.ENTER);
    }

    protected void clickElement(SelenideElement element) {
        element.click();
    }

    protected String getElementText(SelenideElement element) {
        return element.getText();
    }

    protected void waitForPageLoad() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted during page load wait", e);
        }
    }

    protected void safeClick(SelenideElement element) {
        try {
            element.click();
        } catch (Exception e) {
            System.out.println("Click failed, retrying: " + e.getMessage());
            element.click();
        }
    }

    protected void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted during wait", e);
        }
    }
}