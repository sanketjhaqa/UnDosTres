package com.UnDosTres.test.framework.screenshot_helper;

import com.UnDosTres.test.framework.testBase.WebDriverHelper;
import com.UnDosTres.test.framework.util.Props;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ScreenshotHook {
    private static final Logger log= LoggerFactory.getLogger(ScreenshotHook.class);
    @After
    public void embedScreenshot(Scenario scenario){
        if(scenario.isFailed() && !scenario.getSourceTagNames().contains("@Api")){
            scenario.write(WebDriverHelper.getInstance().getWebDriver().getCurrentUrl()+":"+Props.getdevice("execution.on.device"));
            byte[] shot=((TakesScreenshot) WebDriverHelper.getInstance().getWebDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.embed(shot,"image/png");
            log.info("Screenshot taken successfully ");
        }
        if(WebDriverHelper.getInstance().getWebDriver()!=null){
            WebDriverHelper.getInstance().getWebDriver().quit();
        }
    }
}
