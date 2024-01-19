package stepDefinitions.base;

import core.drivers.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.sql.Timestamp;

import static core.drivers.DriverFactory.getDriver;
import static data.TestData.LOGGER_INDENT;

@Log4j
public class Hooks {

    @AfterStep
    public void captureExceptionImage(Scenario scenario) {
        if (scenario.isFailed()) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String timeMilliseconds = Long.toString(timestamp.getTime());

            byte[] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", timeMilliseconds);
        }
    }

    @After
    public void closeDriverSession() {
        log.info("After method steps".toUpperCase() + LOGGER_INDENT);
        DriverFactory.cleanUpDriver();
    }
}
