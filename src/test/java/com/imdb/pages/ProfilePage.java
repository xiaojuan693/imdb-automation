package com.imdb.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage extends BasePage {

    // Real IMDb DOM elements
    private SelenideElement profileName = $("[data-testid='hero__pageTitle']");
    private SelenideElement profileBio = $(".ipc-html-content-inner-div");

    public String getProfileName() {
        return profileName.shouldBe(visible).getText();
    }

    public boolean isProfilePageLoaded() {
        return profileName.isDisplayed() && profileBio.isDisplayed();
    }
}