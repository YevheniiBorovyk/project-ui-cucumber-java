package stepDefinitions;

import core.BasePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import ui.login.LogInPage;
import ui.login.MainPage;

public class LoginSteps extends BasePage {

    private final LogInPage logInPage = new LogInPage();
    private final MainPage mainPage = new MainPage();

    @Given("I access the stack overflow log in page")
    public void iAccessTheStackOverflowLogInPage() {
        logInPage.openPage();
    }

    @When("I enter a unique email {word}")
    public void iEnterAUniqueEmail(String email) {
        logInPage.fillEmail(email);
    }

    @And("I enter a unique password {}")
    public void iEnterAUniquePassword(String password) {
        logInPage.fillPassword(password);
    }

    @When("I enter a email {word}")
    public void iEnterAEmail(String email) {
        logInPage.fillEmail(email);
    }

    @And("I enter a password {}")
    public void iEnterAPassword(String password) {
        logInPage.fillPassword(password);
    }

    @And("I click on log in button")
    public void iClickOnLogInButton() {
        logInPage.clickLogInButton();
    }

    @Then("I should be presented with the unsuccessful login message {}")
    public void iShouldBePresentedWithTheUnsuccessfulLoginMessage(String logInvalidationMassage) {
        Assert.assertTrue(logInPage.getErrorMessage()
                .equals(logInvalidationMassage), "Error message assertion error");
    }

    @Then("I should be presented the icon home")
    public void iShouldBePresentedTheIconHome() {
        Assert.assertTrue(mainPage.isHomeIconVisible(), "Icon home is not visible");
    }
}
