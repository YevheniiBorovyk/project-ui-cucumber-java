package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static core.drivers.DriverFactory.getDriver;

public class BasePage {

    protected WebDriver driver;

    public BasePage() {
        this.driver = getDriver();
        PageFactory.initElements(driver, this);
    }
}
