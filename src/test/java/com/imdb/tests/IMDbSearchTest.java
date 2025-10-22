package com.imdb.tests;

import com.imdb.base.BaseTest;
import com.imdb.pages.HomePage;
import com.imdb.pages.ProfilePage;
import com.imdb.pages.TitlePage;
import com.imdb.utils.TestData;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class IMDbSearchTest extends BaseTest {

    private String savedTitleFromDropdown;
    private String thirdCastMemberName;

    @Test
    public void testIMDbSearchAndCastNavigation() {
        HomePage homePage = new HomePage();
        TitlePage titlePage = new TitlePage();
        ProfilePage profilePage = new ProfilePage();

        try {
            // Step 1: Open imdb.com and handle cookies
            homePage.openHomePage(TestData.BASE_URL);

            // Step 2: Click search bar area (dropdown list displays)
            homePage.clickSearchBar();

            // Step 3: Insert "QA" (dropdown list content updates)
            homePage.typeSearchQuery(TestData.SEARCH_QUERY);

            // Debug: Show what's available in search results
            homePage.printSearchResultsDebug();

            // Step 4: Save the name of the first title
            savedTitleFromDropdown = homePage.getFirstSearchResultTitle();

            // Verify we found a title
            assertNotNull(savedTitleFromDropdown, "First title from dropdown should not be null");
            assertFalse(savedTitleFromDropdown.isEmpty(), "First title from dropdown should not be empty");

            // Step 5: Click on the first title
            homePage.clickFirstSearchResult();

            // Wait for title page to load properly
            titlePage.waitForTitlePageToLoad();

            // Step 6: Verify that page title matches the one saved from the dropdown
            System.out.println(">>> Step 6: Verifying page title matches dropdown title");

            // Check if title page loaded
            boolean isTitlePageLoaded = titlePage.isTitlePageLoaded();
            System.out.println("   [INFO] Title page loaded: " + isTitlePageLoaded);

            assertTrue(isTitlePageLoaded, "Title page should be loaded");

            String actualPageTitle = titlePage.getPageTitle();
            System.out.println("   [OK] Page title: '" + actualPageTitle + "'");
            System.out.println("   [OK] Expected: '" + savedTitleFromDropdown + "'");

            // Use flexible matching for titles
            boolean titlesMatch = actualPageTitle.contains(savedTitleFromDropdown) ||
                    savedTitleFromDropdown.contains(actualPageTitle) ||
                    actualPageTitle.toLowerCase().contains(savedTitleFromDropdown.toLowerCase()) ||
                    savedTitleFromDropdown.toLowerCase().contains(actualPageTitle.toLowerCase());

            assertTrue(titlesMatch,
                    "Page title should match the title saved from dropdown. " +
                            "Dropdown: '" + savedTitleFromDropdown + "', " +
                            "Page: '" + actualPageTitle + "'");
            System.out.println("   [OK] Page title verification passed");

            // Step 7: Scroll down to "top cast section"
            titlePage.scrollToTopCastSection();

            // Step 8: Verify there are more than 3 members in the "top cast section"
            System.out.println(">>> Step 8: Verifying cast members count");
            int castCount = titlePage.getCastMembersCount();
            System.out.println("   [OK] Found " + castCount + " cast members");
            assertTrue(titlePage.hasMoreThanMinimumCastMembers(TestData.MIN_CAST_MEMBERS),
                    "Should have more than " + TestData.MIN_CAST_MEMBERS + " cast members. Actual: " + castCount);
            System.out.println("   [OK] Cast count verification passed (more than " + TestData.MIN_CAST_MEMBERS + ")");

            // Save the 3rd cast member name for verification
            thirdCastMemberName = titlePage.getCastMemberName(TestData.CAST_MEMBER_INDEX);
            assertNotNull(thirdCastMemberName, "Third cast member name should not be null");
            assertFalse(thirdCastMemberName.isEmpty(), "Third cast member name should not be empty");
            System.out.println("   [OK] Third cast member: '" + thirdCastMemberName + "'");

            // Step 9: Click on the 3rd profile in the "top cast section"
            titlePage.clickCastMemberByIndex(TestData.CAST_MEMBER_INDEX);

            // Step 10: Verify that correct profile have opened
            System.out.println(">>> Step 10: Verifying correct profile opened");
            assertTrue(profilePage.isProfilePageLoaded(), "Profile page should be loaded");
            String actualProfileName = profilePage.getProfileName();
            System.out.println("   [OK] Profile page opened: '" + actualProfileName + "'");

            // Verify profile name contains the cast member name
            assertTrue(actualProfileName.contains(thirdCastMemberName) ||
                            thirdCastMemberName.contains(actualProfileName),
                    "Profile name should match cast member name");
            System.out.println("   [OK] Profile verification passed");

            System.out.println(">>> ALL STEPS COMPLETED SUCCESSFULLY!");

        } catch (Exception e) {
            System.out.println(">>> TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            fail("Test failed with exception: " + e.getMessage());
        }
    }
}