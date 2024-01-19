package core.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

@Log4j
public class DriverFactory {

    private static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public static synchronized WebDriver getDriver() {
        if (webDriver.get() == null) {
            webDriver.set(createDriver());
        }
        return webDriver.get();
    }

    private static synchronized WebDriver createDriver() {
        WebDriver driver = null;

        switch (getBrowserType()) {
            case "chrome":
                WebDriverManager.chromedriver()
                        .setup();
                HashMap<String, Object> chromePreferences = new HashMap<>();
                chromePreferences.put("profile.default_content_settings.popups", 0);
                chromePreferences.put("profile.default_content_setting_values.automatic_downloads", 1);
                chromePreferences.put("profile.default_content_setting_values.notifications",
                        2); // disable notifications
                chromePreferences.put("profile.password_manager_enabled", false); //no asking to save login details
                chromePreferences.put("plugin.state.flash", 0);

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--test-type");
                chromeOptions.addArguments("disable-infobars");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-site-isolation-trials");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-plugins");
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                LoggingPreferences logPrefs = new LoggingPreferences();
                logPrefs.enable(LogType.BROWSER, Level.ALL);
                logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
                chromeOptions.setCapability("goog:loggingPrefs", logPrefs);
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver()
                        .setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driver = new FirefoxDriver(firefoxOptions);
                break;
        }
        return driver;
    }

    private static String getBrowserType() {
        String browserType = null;
        try {
            Properties properties = new Properties();
            FileInputStream file =
                    new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config.properties");
            properties.load(file);
            browserType = properties.getProperty("browser")
                    .toLowerCase()
                    .trim();
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return browserType;
    }

    public static synchronized void cleanUpDriver() {
        log.info("Cleaning up the driver");
        webDriver.get()
                .quit();
        webDriver.remove();
    }
}
