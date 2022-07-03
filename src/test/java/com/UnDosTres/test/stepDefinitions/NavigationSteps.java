package com.UnDosTres.test.stepDefinitions;


import com.UnDosTres.test.framework.helper.UrlBuilder;
import com.UnDosTres.test.framework.testBase.WebDriverHelper;
import com.UnDosTres.test.framework.util.Props;
import cucumber.api.java.en.Given;


public class NavigationSteps {
    @Given("^I navigate to the \"(.*?)\" page$")
    public void I_navigate_to_the_page(String page){
        String siteUrl;
        if(page.equalsIgnoreCase("home")){
            UrlBuilder.startAtHomePage();
        }
        else {
            WebDriverHelper.getInstance().initializeBrowser();
            WebDriverHelper.getInstance().getWebDriver().get(page);
        }

    }
}
