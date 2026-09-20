package com.example.orangehrm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class OrangeHRMLoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String BASE_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();

        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get(BASE_URL);
    }

    @Test
    void validLoginTest() {

        // NAME locator
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("username")))
                .sendKeys("Admin");

        // NAME locator
        driver.findElement(By.name("password"))
                .sendKeys("admin123");

        // CSS Selector
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[type='submit']")))
                .click();

        // XPath locator
        boolean dashboardVisible = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h6[normalize-space()='Dashboard']")))
                .isDisplayed();

        assertTrue(
                dashboardVisible,
                "Dashboard should be visible after valid login"
        );
    }

    @Test
    void invalidLoginTest() {

        // NAME locator
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("username")))
                .sendKeys("InvalidUser");

        // NAME locator
        driver.findElement(By.name("password"))
                .sendKeys("WrongPassword123");

        // CSS Selector
        driver.findElement(
                By.cssSelector("button[type='submit']"))
                .click();

        // XPath locator
        boolean errorVisible = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                "//p[contains(@class,'oxd-alert-content-text')]")))
                .isDisplayed();

        assertTrue(
                errorVisible,
                "Invalid login message should be displayed"
        );
    }

    @AfterEach
    void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}