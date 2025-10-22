package com.imdb.base;

import com.imdb.config.TestConfig;
import com.imdb.utils.AllureTestListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import static com.codeborne.selenide.Selenide.*;

@Listeners(AllureTestListener.class)
public class BaseTest {

    @BeforeMethod
    public void setUp() {
        TestConfig.setup();
    }

    @AfterMethod
    public void tearDown() {
        closeWebDriver();
    }
}