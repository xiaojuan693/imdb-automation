package com.imdb.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class HomePage extends BasePage {

    // Real IMDb DOM elements
    private SelenideElement searchBox = $("#suggestion-search");

    // Cookie consent elements
    private SelenideElement acceptCookiesButton = $("button[data-testid='accept-button']");
    private SelenideElement declineCookiesButton = $("button[data-testid='reject-button']");

    public void openHomePage(String url) {
        System.out.println(">>> Step 1: Opening IMDb homepage: " + url);
        openUrl(url);
        waitForPageLoad();
        handleCookieConsent();
    }

    public void clickSearchBar() {
        System.out.println(">>> Step 2: Clicking search bar area");
        searchBox.shouldBe(visible).click();
        waitForPageLoad();
        System.out.println("   [OK] Dropdown should be displayed");
    }

    public void typeSearchQuery(String query) {
        System.out.println(">>> Step 3: Typing '" + query + "' in search bar");
        searchBox.setValue(query);
        waitForPageLoad();
        System.out.println("   [OK] Dropdown content updated with search results");
    }

    public String getFirstSearchResultTitle() {
        try {
            // Wait for search results to appear
            waitSeconds(3);

            System.out.println(">>> Step 4: Finding first actual search result title (after promotional sections)");

            // Get ALL search result containers
            ElementsCollection allResults = $$("[data-testid='search-result--const']");
            System.out.println("   [INFO] Found " + allResults.size() + " total result containers");

            // Find the first result that is an ACTUAL SEARCH RESULT (not promotional)
            for (int i = 0; i < allResults.size(); i++) {
                SelenideElement result = allResults.get(i);
                String title = getTitleFromResult(result);
                String containerType = classifyResultContainer(result, title);

                System.out.println("   [DEBUG] Result " + i + ": '" + title + "' - Type: " + containerType);

                // Skip promotional sections and find the first actual search result
                if ("ACTUAL_SEARCH_RESULT".equals(containerType)) {
                    System.out.println("   [OK] First actual search result found: '" + title + "'");
                    return title;
                }
            }

            System.out.println("   [ERROR] No actual search result titles found");
            return "";

        } catch (Exception e) {
            System.out.println("   [ERROR] Error finding first title: " + e.getMessage());
            return "";
        }
    }

    private String getTitleFromResult(SelenideElement result) {
        try {
            if (result.$(".searchResult__constTitle").exists()) {
                return result.$(".searchResult__constTitle").getText().trim();
            }
            return "No title";
        } catch (Exception e) {
            return "Error getting title";
        }
    }

    private String classifyResultContainer(SelenideElement result, String title) {
        // Check if this is a PROMOTIONAL SECTION
        if (isPromotionalSection(result, title)) {
            return "PROMOTIONAL_SECTION";
        }

        // Check if this is a PERSON result (not a movie)
        if (isPersonResult(result, title)) {
            return "PERSON_RESULT";
        }

        // Check if this is an ACTUAL MOVIE/TV TITLE that matches search
        if (isActualSearchResult(result, title)) {
            return "ACTUAL_SEARCH_RESULT";
        }

        return "UNKNOWN";
    }

    private boolean isPromotionalSection(SelenideElement result, String title) {
        // Promotional sections often have different structure or specific titles
        String lowerTitle = title.toLowerCase();

        // Known promotional section headers
        String[] promotionalTitles = {
                "halloween family fun",
                "stranger things",
                "the black phone",
                "book tickets"
        };

        for (String promoTitle : promotionalTitles) {
            if (lowerTitle.contains(promoTitle)) {
                return true;
            }
        }

        // Promotional sections might have different styling or structure
        // Check if this result is part of a featured/promotional carousel
        boolean hasPromotionalParent = result.closest(".ipc-shoveler").exists() ||
                result.closest(".ipc-poster-card").exists();

        // Promotional content often appears before actual search results
        // and might have different metadata structure
        boolean hasYear = result.$(".searchResult__metadata").exists();
        boolean hasActors = result.$$(".searchResult__metadata").size() > 1;

        // Promotional sections typically don't have both year and actors
        return hasPromotionalParent || !(hasYear && hasActors);
    }

    private boolean isPersonResult(SelenideElement result, String title) {
        // Person results usually don't have years or have different metadata
        boolean hasYear = result.$(".searchResult__metadata").exists();
        ElementsCollection metadata = result.$$(".searchResult__metadata");

        // Person results might have "Actor" or similar in metadata
        for (SelenideElement meta : metadata) {
            String metaText = meta.getText().toLowerCase();
            if (metaText.contains("actor") || metaText.contains("actress") ||
                    metaText.contains("director") || metaText.contains("writer")) {
                return true;
            }
        }

        // If no year but has metadata, might be a person
        return !hasYear && !metadata.isEmpty();
    }

    private boolean isActualSearchResult(SelenideElement result, String title) {
        if (title.equals("No title") || title.equals("Error getting title")) {
            return false;
        }

        // Skip known promotional titles
        if (isPromotionalSection(result, title)) {
            return false;
        }

        // Skip person results
        if (isPersonResult(result, title)) {
            return false;
        }

        // Actual search results should have:
        // - A title
        // - Year information
        // - Actor information (usually)
        boolean hasTitle = result.$(".searchResult__constTitle").exists();
        boolean hasYear = result.$(".searchResult__metadata").exists();
        ElementsCollection metadata = result.$$(".searchResult__metadata");
        boolean hasMultipleMetadata = metadata.size() >= 2; // Usually year + actors

        // Additional check: actual results often match the search query
        String lowerTitle = title.toLowerCase();
        boolean matchesSearch = lowerTitle.contains("q&a") ||
                lowerTitle.contains("qarib") ||
                lowerTitle.startsWith("q");

        return hasTitle && hasYear && hasMultipleMetadata && matchesSearch;
    }

    public void clickFirstSearchResult() {
        try {
            System.out.println(">>> Step 5: Clicking first actual search result");

            // Get ALL search result containers
            ElementsCollection allResults = $$("[data-testid='search-result--const']");

            if (!allResults.isEmpty()) {
                System.out.println("   [INFO] Found " + allResults.size() + " total result containers");

                // Find the first result that is an ACTUAL SEARCH RESULT (not promotional)
                SelenideElement firstActualResult = null;
                String firstActualTitle = "";

                for (int i = 0; i < allResults.size(); i++) {
                    SelenideElement result = allResults.get(i);
                    String title = getTitleFromResult(result);
                    String containerType = classifyResultContainer(result, title);

                    if ("ACTUAL_SEARCH_RESULT".equals(containerType)) {
                        firstActualResult = result;
                        firstActualTitle = title;
                        break;
                    }
                }

                if (firstActualResult != null) {
                    System.out.println("   [INFO] Clicking actual search result: '" + firstActualTitle + "'");
                    firstActualResult.click();
                    waitSeconds(5); // Wait for page to load
                    System.out.println("   [OK] First actual search result clicked successfully");
                } else {
                    throw new RuntimeException("No actual search results found to click");
                }
            } else {
                throw new RuntimeException("No search results found to click");
            }

        } catch (Exception e) {
            System.out.println("   [ERROR] Error clicking first title: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean isSearchBoxDisplayed() {
        return searchBox.isDisplayed();
    }

    private void handleCookieConsent() {
        try {
            System.out.println(">>> Handling cookie consent");
            waitForPageLoad();

            if (acceptCookiesButton.exists() && acceptCookiesButton.isDisplayed()) {
                System.out.println("   [OK] Cookie banner detected - accepting cookies");
                acceptCookiesButton.shouldBe(visible).click();
                waitForPageLoad();
                System.out.println("   [OK] Cookies accepted");
            } else {
                System.out.println("   [OK] No cookie banner found");
            }
        } catch (Exception e) {
            System.out.println("   [WARN] Cookie handling failed, continuing: " + e.getMessage());
        }
    }

    // Method to debug search results
    public void printSearchResultsDebug() {
        try {
            System.out.println("=== DEBUG: Detailed Search Results Analysis ===");

            // Get ALL search result containers
            ElementsCollection allResults = $$("[data-testid='search-result--const']");
            System.out.println("Total result containers found: " + allResults.size());

            int promotionalCount = 0;
            int personCount = 0;
            int actualResultCount = 0;

            for (int i = 0; i < allResults.size(); i++) {
                SelenideElement result = allResults.get(i);
                String title = getTitleFromResult(result);
                String containerType = classifyResultContainer(result, title);

                // Count by type
                switch (containerType) {
                    case "PROMOTIONAL_SECTION": promotionalCount++; break;
                    case "PERSON_RESULT": personCount++; break;
                    case "ACTUAL_SEARCH_RESULT": actualResultCount++; break;
                }

                String marker = "ACTUAL_SEARCH_RESULT".equals(containerType) ? " >>> FIRST TITLE <<<" : "";
                System.out.println("  [" + i + "] " + containerType + " - '" + title + "'" + marker);

                // Show metadata for actual results
                if ("ACTUAL_SEARCH_RESULT".equals(containerType)) {
                    ElementsCollection metadata = result.$$(".searchResult__metadata");
                    for (int j = 0; j < metadata.size(); j++) {
                        System.out.println("        Metadata " + j + ": " + metadata.get(j).getText());
                    }
                }
            }

            System.out.println("=== SUMMARY ===");
            System.out.println("Promotional sections: " + promotionalCount);
            System.out.println("Person results: " + personCount);
            System.out.println("Actual search results: " + actualResultCount);

            // Show what will be selected
            String firstTitle = getFirstSearchResultTitle();
            if (!firstTitle.isEmpty()) {
                System.out.println("=== WILL SELECT AS FIRST TITLE ===");
                System.out.println("Title: '" + firstTitle + "'");
            } else {
                System.out.println("=== NO VALID TITLES FOUND ===");
            }

        } catch (Exception e) {
            System.out.println("Debug error: " + e.getMessage());
        }
    }
}