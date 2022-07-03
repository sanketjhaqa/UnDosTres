package com.UnDosTres.test.framework.testBase;


import com.UnDosTres.test.framework.helper.UrlBuilder;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class WebDriverHelper {

    private static Dimension MOBILE_WINDOW_SIZE= new Dimension(411, 731);
    private static WebDriverHelper instance = null;
    private static Logger log = LogManager.getLogger(WebDriverHelper.class);
    public static WebDriverWait wait;
    private static long WAIT_TIME = 10;
    public static Actions actions;
    private static ThreadLocal<RemoteWebDriver> threadLocal = new ThreadLocal<>();

    private WebDriverHelper() {
    }

    public static WebDriverHelper getInstance() {
        if (instance == null) {
            instance = new WebDriverHelper();
        }
        return instance;
    }

    public WebDriver getWebDriver() {
        return threadLocal.get();
    }

    public WebDriver initializeBrowser() {
        RemoteWebDriver driver = null;

        if (UrlBuilder.isLocalFireFox()) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = getFireFoxOptions();
            driver = new FirefoxDriver(options);
            if (UrlBuilder.isMobile()) {
                driver.manage().window().setSize(MOBILE_WINDOW_SIZE);
            }else {
                driver.manage().window().maximize();
            }
        }
        else if (UrlBuilder.isLocalChrome()) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = getChromeOptions();
            driver = new ChromeDriver(options);
            if (UrlBuilder.isMobile()) {
                driver.manage().window().setSize(MOBILE_WINDOW_SIZE);
            }else {
                driver.manage().window().maximize();
            }
        } else {
            throw new IllegalArgumentException(
                    "Browser type not supported: " + System.getProperty("browserName"));
        }
        log.info("WebDriver start up finished.");
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        wait = new WebDriverWait(driver, WAIT_TIME);
        actions = new Actions(driver);
        threadLocal.set(driver);
        log.info("Browser has been opened successfully");
        return getWebDriver();
}



    private static ChromeOptions getChromeOptions() {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.DRIVER, Level.ALL);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-web-security");
        chromeOptions.addArguments("--test-type");
        chromeOptions.addArguments("allow-running-insecure-content");
        chromeOptions.setExperimentalOption("w3c", false);
        chromeOptions.addArguments("--ignore-ssl-errors=yes");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--incognito");
        return chromeOptions;
    }

    private static FirefoxOptions getFireFoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(true);
        profile.setPreference("network.cookie.cookieBehavior", 1);
        profile.setPreference("startup.homepage_welcome_url.additional", "");
        profile.setPreference("network.proxy.type", 0);
        options.setCapability(FirefoxDriver.PROFILE, profile);
        options.setCapability("marionette", true);
        options.setCapability("platform", "WINDOWS");
        options.setCapability("disable-restore-session-state", true);
        options.setCapability("acceptInsecureCerts", true);
        return options;
    }

    public String getScreenShot(String testName, WebDriver driver) {
        log.info("Going to take screenshot for test case->" + testName);

        TakesScreenshot sc = (TakesScreenshot) driver;
        log.info("Screenshot has been taken");
        File source = sc.getScreenshotAs(OutputType.FILE);
        String destination = "./" + "AutomationReport/ScreenShots/" + testName + ".png";

        try {
            FileUtils.copyFile(source, new File(destination));
            log.info("Screenshot has been stored in screenshot folder");
        } catch (IOException e) {
            log.error("Unable to take screenshot error has been occured", e.fillInStackTrace());
        }
        return destination;
    }

    public void recursiveDelete(File file) {
        //to end the recursive loop
        if (!file.exists())
            return;

        //if directory, go inside and call recursively
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                //call recursively
                recursiveDelete(f);
            }
        }
        //call delete to delete files and empty directory
        file.delete();
        System.out.println("Deleted file/folder: " + file.getAbsolutePath());
    }

    public void changeWebDriverWaitTime(long seconds) {
        WAIT_TIME = seconds;
    }
}
