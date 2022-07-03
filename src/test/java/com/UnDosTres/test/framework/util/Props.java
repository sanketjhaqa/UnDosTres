package com.UnDosTres.test.framework.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import static java.lang.System.out;

public class Props {
    private static Properties config;
    private static Properties environmentProps;
    private static Properties messages,devices;

    private static final Logger log = LoggerFactory.getLogger(Props.class);

    static {
        loadRunConfigProps("/environment.properties");
    }

    public static void loadRunConfigProps(String configPropertyFileLocation) {
        environmentProps = new Properties();
        try (InputStream inputStream = Props.class.getResourceAsStream(configPropertyFileLocation)) {
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            environmentProps.load(reader);
            environmentProps.list(System.out);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        //Read Config file
        config = new Properties();
        try (InputStream inputStream = Props.class.getResourceAsStream(environmentProps.getProperty("profile.path"))) {
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            config.load(reader);
            config.list(System.out);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        //Read message file
        messages=new Properties();
        try (InputStream inputStream = Props.class.getResourceAsStream(environmentProps.getProperty("profile.messages"))){
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            messages.load(reader);
            messages.list(System.out);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        //Read Device file
        String devicesFile = environmentProps.getProperty("profile.device");
        devices = new Properties();
        try (InputStream inputStream = Props.class.getResourceAsStream(devicesFile)) {
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            devices.load(reader);
            devices.list(out);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

    /**
     * Gets the key from devices
     **/
    public static String getdevice(String key) {
        if ((key == null) || key.isEmpty()) {
            return "";
        } else {
            return devices.getProperty(key);
        }
    }

    /**
     * Gets the key from config.properties related to chosen profile
     **/
    public static String getExecutionEnv() {
        return environmentProps.getProperty("profile.executionEnv");
    }

    public static String getEnvProperty(String propertyName) {
        return environmentProps.getProperty(propertyName);
    }


    public static boolean setProp(String key, String value) {
        boolean flag = false;
        if (key == null || key.isEmpty()) {
            log.error("Key is Either Empty or Null please check");
        } else {
            config.setProperty(key, value);
            flag = true;
        }
        return flag;
    }

    public static String getProp(String key) {
        boolean flag = false;
        if (key == null || key.isEmpty()) {
            log.error("Key is Either Empty or Null please check");
            return "";
        } else {
            return config.getProperty(key);
        }
    }

    public static String getMessage(String key) {
        boolean flag = false;
        if (key == null || key.isEmpty()) {
            log.error("Key is Either Empty or Null please check");
            return "";
        } else {
            return messages.getProperty(key);
        }
    }


}