package com.UnDosTres.test.framework.hooks;

import com.UnDosTres.test.framework.testBase.WebDriverHelper;
import com.UnDosTres.test.framework.util.PageObject;
import com.UnDosTres.test.framework.util.Props;
import cucumber.api.Scenario;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ScenarioHook {
    protected static String scenarioName;

    public static String getScenarioName(){return scenarioName;}

    @Before
    public void scenarioInfo(Scenario scenario){
        this.scenarioName=scenario.getName();
    }

    @AfterStep
    public void attachScreenShot(Scenario scenario){
        PageObject.isSpinnerThereAndLoaded(WebDriverHelper.getInstance().getWebDriver());
        scenario.write(WebDriverHelper.getInstance().getWebDriver().getCurrentUrl());
        scenario.write("Browser is "+ Props.getdevice("execution.on.device") +"\n");
        byte[] shot=((TakesScreenshot) WebDriverHelper.getInstance().getWebDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.embed(shot,"image/png");
    }

}
