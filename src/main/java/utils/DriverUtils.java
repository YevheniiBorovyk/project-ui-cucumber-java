package utils;

import core.drivers.DriverFactory;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.or;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementValue;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

@Log4j
public class DriverUtils {

    private static final long DEFAULT_WAIT = 15L;

    private DriverUtils() {
    }

    private static WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    private static WebDriverWait getDefaultWait() {
        return new WebDriverWait(getDriver(), ofSeconds(DEFAULT_WAIT));
    }

    private static WebDriverWait getCustomWait(long waitInSeconds) {
        return getCustomWait(waitInSeconds, 500L);
    }

    private static WebDriverWait getCustomWait(long waitInSeconds, long pollingWaitInMillis) {
        return new WebDriverWait(getDriver(), ofSeconds(waitInSeconds), ofMillis(pollingWaitInMillis));
    }

    @Step
    public static WebElement findElement(WebElement webElement) {
        return getDefaultWait().until(visibilityOf(webElement));
    }

    @Step
    public static boolean isElementVisible(WebElement webElement) {
        try {
            findElement(webElement);
            return true;
        } catch (TimeoutException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Step
    public static boolean isElementVisible(WebElement webElement, long timeOutInSeconds, long pollingWaitInMillis) {
        try {
            getCustomWait(timeOutInSeconds, pollingWaitInMillis).until(visibilityOf(webElement));
            return true;
        } catch (TimeoutException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Step
    public static boolean isElementNotVisible(WebElement element) {
        try {
            getDefaultWait().until(invisibilityOf(element));
            return true;
        } catch (TimeoutException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Step
    public static boolean isElementNotVisible(WebElement webElement, long timeOutInSeconds, long pollingWaitInMillis) {
        try {
            getCustomWait(timeOutInSeconds, pollingWaitInMillis).until(invisibilityOf(webElement));
            return true;
        } catch (TimeoutException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Step
    public static void getURL(String url) {
        log.info("Getting URL [" + url + "]");
        getDriver().get(url);
    }

    @Step
    public static String getCurrentURL() {
        String currentURL = getDriver().getCurrentUrl();
        log.info("Getting current URL [" + currentURL + "]");
        return currentURL;
    }

    @Step
    public static void click(WebElement element) {
        getDefaultWait().until(elementToBeClickable(element));
        getDefaultWait().until(CustomExpectedConditions.click(element));
    }

    @Step
    public static void click(By locator) {
        getDefaultWait().until(elementToBeClickable(locator));
        getDefaultWait().until(CustomExpectedConditions.click(locator));
    }

    @Step
    public static void type(WebElement webElement, String text) {
        findElement(webElement).sendKeys(text);
    }

    @Step
    public static void typeWithCheckText(WebElement webElement, String text) {
        findElement(webElement).sendKeys(text);
        getDefaultWait().until(
                or(textToBePresentInElementValue(webElement, text), textToBePresentInElement(webElement, text)));
    }

    @Step
    public static String getText(WebElement element) {
        return getDefaultWait().until(CustomExpectedConditions.getText(element));
    }

    @Step
    public static void waitUntilElementsDisappearIfVisible(WebElement... webElements) {
        waitUntilElementsDisappearIfVisible(2L, webElements);
    }

    @Step
    public static void waitUntilElementsDisappearIfVisible(long waitForVisibilityTimeoutInSeconds,
            WebElement... webElements) {
        long time = 120L;
        log.info("Waiting [" + time + "] sec until element disappears");
        for (WebElement webElement : webElements) {
            if (isElementVisible(webElement, waitForVisibilityTimeoutInSeconds, 250L)) {
                isElementNotVisible(webElement, time, 500L);
            }
        }
    }

    @Step
    public static void waitUntilPageLoaded() {
        getCustomWait(120, 50).withMessage("Page is not loaded: " + getCurrentURL())
                .until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState")
                        .toString()
                        .equalsIgnoreCase("complete"));
    }

}
