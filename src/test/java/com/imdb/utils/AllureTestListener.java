package com.imdb.utils;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class AllureTestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        captureScreenshot("Screenshot on Failure");
        System.out.println("Test failed: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        captureScreenshot("Screenshot on Success");
        System.out.println("Test passed: " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        captureScreenshot("Screenshot on Skipped");
        System.out.println("Test skipped: " + result.getName());
    }

    private void captureScreenshot(String attachmentName) {
        try {
            WebDriver driver = WebDriverRunner.getWebDriver();
            if (driver instanceof TakesScreenshot) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment(attachmentName,
                        "image/png", new ByteArrayInputStream(screenshot), ".png");
            }
        } catch (Exception e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}