package com.imdb.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TitlePage extends BasePage {

    // Real IMDb DOM elements - try multiple selectors for title
    private SelenideElement pageTitle = $("[data-testid='hero__pageTitle']");
    private SelenideElement altPageTitle = $("h1[data-testid='hero__pageTitle']");
    private SelenideElement legacyPageTitle = $("h1");

    private SelenideElement castSection = $("section[data-testid='title-cast']");
    private SelenideElement altCastSection = $(".ipc-sub-grid"); // Alternative cast section selector
    private ElementsCollection castMembers = $$("a[data-testid='title-cast-item__actor']");
    private ElementsCollection altCastMembers = $$(".ipc-metadata-list__item:has(.ipc-metadata-list-item__label:contains(Cast)) .ipc-metadata-list-item__content-container");

    public String getPageTitle() {
        // Try multiple selectors for the page title
        if (pageTitle.exists()) {
            return pageTitle.shouldBe(visible).getText();
        } else if (altPageTitle.exists()) {
            return altPageTitle.shouldBe(visible).getText();
        } else {
            return legacyPageTitle.shouldBe(visible).getText();
        }
    }

    public boolean isTitlePageLoaded() {
        System.out.println(">>> Checking if title page is loaded");

        // Try multiple ways to verify the page loaded
        boolean titleExists = pageTitle.exists() || altPageTitle.exists();
        boolean titleDisplayed = (pageTitle.exists() && pageTitle.isDisplayed()) ||
                (altPageTitle.exists() && altPageTitle.isDisplayed());

        System.out.println("   [INFO] Title exists: " + titleExists);
        System.out.println("   [INFO] Title displayed: " + titleDisplayed);

        if (titleExists) {
            try {
                String currentTitle = getPageTitle();
                System.out.println("   [INFO] Current page title: '" + currentTitle + "'");
                return !currentTitle.isEmpty();
            } catch (Exception e) {
                System.out.println("   [ERROR] Error getting page title: " + e.getMessage());
                return false;
            }
        }

        return false;
    }

    public void waitForTitlePageToLoad() {
        try {
            System.out.println(">>> Waiting for title page to load...");
            waitSeconds(5); // Wait 5 seconds for page to load

            // Check if we're still on a search page or actually on a title page
            boolean isSearchPage = $(".findHeader").exists();
            boolean isTitlePage = isTitlePageLoaded();

            System.out.println("   [INFO] Is search page: " + isSearchPage);
            System.out.println("   [INFO] Is title page: " + isTitlePage);

            if (!isTitlePage && isSearchPage) {
                System.out.println("   [WARN] Still on search page, might need to handle redirect");
            }

        } catch (Exception e) {
            System.out.println("   [ERROR] Error waiting for page load: " + e.getMessage());
        }
    }

    public void scrollToTopCastSection() {
        System.out.println(">>> Step 7: Scrolling to top cast section");

        // Try multiple selectors for cast section
        if (castSection.exists()) {
            castSection.scrollIntoView(true);
        } else if (altCastSection.exists()) {
            altCastSection.scrollIntoView(true);
        } else {
            System.out.println("   [WARN] Cast section not found with standard selectors");
            // Scroll down a bit as fallback
            scrollDown();
        }

        waitForPageLoad();
        System.out.println("   [OK] Scrolled to top cast section");
    }

    private void scrollDown() {
        try {
            // Simple scroll down as fallback
            $$("body").first().scrollIntoView(false);
        } catch (Exception e) {
            System.out.println("   [WARN] Scroll failed: " + e.getMessage());
        }
    }

    public int getCastMembersCount() {
        // Try multiple selectors for cast members
        if (!castMembers.isEmpty()) {
            return castMembers.shouldHave(CollectionCondition.sizeGreaterThan(0)).size();
        } else if (!altCastMembers.isEmpty()) {
            return altCastMembers.shouldHave(CollectionCondition.sizeGreaterThan(0)).size();
        }
        return 0;
    }

    public void clickCastMemberByIndex(int index) {
        System.out.println(">>> Step 9: Clicking 3rd profile in top cast section");

        // Try multiple selectors for cast members
        if (!castMembers.isEmpty() && castMembers.size() > index) {
            castMembers.get(index).click();
        } else if (!altCastMembers.isEmpty() && altCastMembers.size() > index) {
            altCastMembers.get(index).click();
        } else {
            throw new RuntimeException("No cast members found to click at index: " + index);
        }

        waitForPageLoad();
        System.out.println("   [OK] Clicked cast member at index: " + index);
    }

    public boolean hasMoreThanMinimumCastMembers(int minimum) {
        return getCastMembersCount() > minimum;
    }

    public String getCastMemberName(int index) {
        if (!castMembers.isEmpty() && castMembers.size() > index) {
            return castMembers.get(index).getText();
        } else if (!altCastMembers.isEmpty() && altCastMembers.size() > index) {
            return altCastMembers.get(index).getText();
        }
        return "";
    }
}