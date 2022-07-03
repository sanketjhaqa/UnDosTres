package com.UnDosTres.test.framework.helper;

import com.UnDosTres.test.framework.testBase.WebDriverHelper;
import com.UnDosTres.test.framework.util.Props;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.net.MalformedURLException;
import java.net.URL;

public class UrlBuilder {

    private static URL basePath;
    private static Logger log = LogManager.getLogger(UrlBuilder.class);

    static {
        try {
            if(Props.getExecutionEnv().equalsIgnoreCase("QA"))
                basePath = new URL(Props.getProp("site.qa.url"));
            else if(Props.getExecutionEnv().equalsIgnoreCase("UAT"))
                basePath = new URL(Props.getProp("site.uat.url"));
            else basePath = new URL(Props.getProp("site.url"));

        } catch (MalformedURLException e) {
            log.error(e.getMessage());
        }

    }

    public static void startAtHomePage() {
        WebDriverHelper.getInstance().initializeBrowser();
        WebDriverHelper.getInstance().getWebDriver().navigate().to((basePath));
    }

    public static void navigateToTheBaseUrl(){
        WebDriverHelper.getInstance().getWebDriver().navigate().to(basePath);
    }

    public static void navigateToGivenUrl(String url){
        WebDriverHelper.getInstance().getWebDriver().navigate().to(url);
    }


    public static URL createUrl(String path) {
        try {
            return new URL(basePath.getProtocol(), basePath.getHost(), basePath.getPort(), path);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    // Check whether device is Mobile
    public  static boolean isMobile(){
        boolean flag=Props.getdevice("application.run.mode").equalsIgnoreCase("Mobile");
        return flag;
    }

    // Check whether device is Desktop
    public  static boolean isDesktop(){
        boolean flag=Props.getdevice("application.run.mode").equalsIgnoreCase("Desktop");
        return flag;
    }

    // Check whether device is Iphone
    public  static boolean isIphone(){
        boolean flag=Props.getdevice("execution.on.device").equalsIgnoreCase("Iphone");
        return flag;
    }

    // Check whether device is Nexus
    public  static boolean isNexus(){
        boolean flag=Props.getdevice("execution.on.device").equalsIgnoreCase("Nexus");
        return flag;
    }

    // Check whether device is LocalChrome
    public  static boolean isLocalChrome(){
        boolean flag=Props.getdevice("execution.on.device").equalsIgnoreCase("LocalChrome");
        return flag;
    }

    // Check whether device is localFireFox
    public  static boolean isLocalFireFox(){
        boolean flag=Props.getdevice("execution.on.device").equalsIgnoreCase("localFirefox");
        return flag;
    }
}
