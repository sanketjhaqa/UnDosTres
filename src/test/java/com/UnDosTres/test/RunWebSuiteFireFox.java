package com.UnDosTres.test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = "src/test/resources/features/", tags = {"@Regression"}, monochrome = true, plugin = {
        "pretty", "html:target/cucumber-report/runwebat",
        "json:target/cucumber-report/runwebat/cucumberFireFox.json",
        "rerun:target/cucumber-report/runwebat/rerun.txt"
        },
        glue = "com.UnDosTres.test")
public class RunWebSuiteFireFox extends AbstractTestNGCucumberTests {

}