package ui.login;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.DriverUtils.isElementVisible;

public class MainPage extends BasePage {

    private @FindBy(xpath = "//*[contains(@class,'iconHome')]")
    WebElement iconHome;

    public MainPage() {
        super();
    }

    @Step
    public boolean isHomeIconVisible() {
        return isElementVisible(iconHome);
    }
}
