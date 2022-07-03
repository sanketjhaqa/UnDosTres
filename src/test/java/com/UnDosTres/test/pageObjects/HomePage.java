package com.UnDosTres.test.pageObjects;


import com.UnDosTres.test.framework.util.PageObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomePage extends PageObject {

    private By operator = By.xpath("//div[@to-do=\"mobile\"]/div/div[@class=\"form\"] //input[@name=\"operator\"]");

    private By operatorList = By.xpath("//input[@id=\"suggested\"]/../div/ul/li");

    private By mobile = By.xpath("//div[@to-do=\"mobile\"]/div/div[@class=\"form\"] //input[@name=\"mobile\"]");

    private By amount = By.xpath("//div[@to-do=\"mobile\"]/div/div[@class=\"form\"] //input[@name=\"amount\"]");

    private By performPaymentButton = By.xpath("//div[@to-do=\"mobile\"]/div //button");

    private By textRecargaCelular = By.cssSelector("div[to-do=\"mobile\"] h1");

    private String rechargeType = "//div[contains(@class,'category-tab')]/div[text()='xxx']";

    private static final Logger log = LogManager.getLogger("UnDosTres");


    public boolean setOperator(String operator) {

        boolean status = false;
        try {
            click(driver.findElement(this.operator));
            log.info("Click on the operator field to open the operator list");


            if (wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(operatorList)).stream().
                    filter(s -> s.getText().trim().equalsIgnoreCase(operator)).count() == 1) {

                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(operatorList)).stream().
                        filter(s -> s.getText().trim().equalsIgnoreCase(operator)).findFirst().get().click();

                log.info("Oprator list has been found, and clicked sucessfully");
                status = true;
            } else {
                throw new RuntimeException("With this locator--->>" + this.operator + " more than one element is showing ");
            }
        } catch (Exception e) {
            log.error("Unable to select operator-> " + operator, e.fillInStackTrace());
        }
        return status;
    }

    public boolean setMobileNumber(String mobileNumber) {
        boolean status = false;

        try {
            setText(driver.findElement(mobile), mobileNumber);
            log.info("Mobile Number->" + mobileNumber + " has been entered successfully");
            status = true;
        } catch (Exception e) {
            System.out.println("Unable to Set Mobile Number");
            log.error("Unable to enter mobile number", e.fillInStackTrace());
        }
        return status;
    }

    public boolean setRechargeTypeAndAmount(String RechargeType, String amount) {
        boolean status = false;
        int i = 0, k = 0;
        click(driver.findElement(this.amount));
        try {
            log.info("Getting recharge type web element " + RechargeType);
            String xpath = this.rechargeType.replace("xxx",RechargeType);
            WebElement rechargeType = driver.findElement(By.xpath(xpath));
            log.info("Recharge type webelement has been found successfully");

            click(rechargeType);
            log.info("Click over Recharge type->" + RechargeType);

            List<WebElement> list = driver.findElements(By.xpath("//div[@class=\"category-tab \"]/div[contains(@class,'category')]"));

            while (!getText(list.get(i)).contains(RechargeType)) {
                i++;
            }
            List<WebElement> list1 = list.get(i).findElements(By.xpath("../following-sibling::ul[" + (++i) + "]/li"));
            for (WebElement e : list1) {
                if (e.findElement(By.xpath("a/div/b")).getText().contains(amount)) {
                    e.click();
                    log.info("Clicked on the amount " + amount);
                    status = true;
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Unable to set recharge type and amount ", e.fillInStackTrace());
        }
        return status;
    }

    public PaymentDetailPage clickProceedToPayment() {
        PaymentDetailPage obj = null;
        try {
            log.info("Going to click on submit button to move towards payment page");
            click(driver.findElement(performPaymentButton));
            log.info("Clicked on perform payment button");
            obj = new PaymentDetailPage();
        } catch (Exception e) {
            log.error("Unable to click on click to Proceed button please check ");
            e.printStackTrace();
        }
        return obj;
    }

    public String getTextRecargaCelular() {
        log.info("Getting text Recarga Celular");
        return getText(driver.findElement(textRecargaCelular));
    }
}
