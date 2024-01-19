package ui.login;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static data.URLs.getLoginPageURL;
import static utils.DriverUtils.click;
import static utils.DriverUtils.getText;
import static utils.DriverUtils.getURL;
import static utils.DriverUtils.type;
import static utils.DriverUtils.typeWithCheckText;
import static utils.DriverUtils.waitUntilElementsDisappearIfVisible;
import static utils.DriverUtils.waitUntilPageLoaded;

public class LogInPage extends BasePage {

    private @FindBy(id = "email")
    WebElement inputEmail;

    private @FindBy(id = "password")
    WebElement inputPassword;

    private @FindBy(id = "submit-button")
    WebElement buttonLogIn;

    private @FindBy(xpath = "//p[contains(@class,'error-details')]")
    WebElement errorMessage;

    private @FindBy(xpath = "//body[@data-gr-ext-installed]")
    WebElement dataInstalled;

    public LogInPage() {
        super();
    }

    @Step
    public void openPage() {
        getURL(getLoginPageURL());
        waitUntilPageLoaded();
        waitUntilElementsDisappearIfVisible(dataInstalled);
    }

    @Step
    public void fillEmail(String email) {
        typeWithCheckText(inputEmail, email);
    }

    @Step
    public void fillPassword(String password) {
        type(inputPassword, password);
    }

    @Step
    public void clickLogInButton() {
        click(buttonLogIn);
    }

    @Step
    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
