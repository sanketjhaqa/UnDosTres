package com.UnDosTres.test.pageObjects;

import com.UnDosTres.test.framework.util.PageObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;


public class PaymentDetailPage extends PageObject {
    private static Logger log = LogManager.getLogger(PaymentDetailPage.class.getName());

    private By summaryMessage = By.cssSelector("span.summary-message");

    private String tarjeta = "//p[text()=\"xxx\"]/..";

    private By cardHolderName = By.xpath("//div[@class=\"card-info-box\"] //input[@name=\"cardname\"]");

    private By cardNumber = By.id("cardnumberunique");

    private By expiryMonth = By.xpath("//td//input[@name=\"expmonth\"]");

    private By expiryYear = By.xpath("//td//input[@name=\"expyear\"]");

    private By ccvNumber = By.xpath("//td//input[@name=\"cvvno\"]");

    private By emailId = By.xpath("//td//input[@name=\"txtEmail\"]");

    private By pagarConTarjeta = By.xpath("//button[@name=\"formsubmit\" and @id=\"paylimit\"]");

    private By popUpEmailId = By.cssSelector("#usrname");

    private By popUpPassword = By.cssSelector("#psw");

    private By popUpIamNotRobotCheckBox = By.cssSelector("#recaptcha-anchor");

    private By popUpSubmitButton = By.cssSelector("button#loginBtn");

    private String selectRadioButton = "//span[text()='xxx']/../parent::label";

    private By popUpHeaderText = By.cssSelector("div.loginToProceed");

    private By rechargeSuccessMessage = By.cssSelector("div.visual-message");

    public boolean selectRadioButtonHavingText(String text) {
        boolean flag = false;
        try {
            String xpath = selectRadioButton.replace("xxx", text);
            WebElement element = driver.findElement(By.xpath(xpath));
            click(element.findElement(By.xpath("a")));
            if (element.findElement(By.xpath("preceding-sibling::input")).isSelected()) {
                int i = 0;
                while (i < 4) {
                    if (element.findElement(By.xpath("ancestor::tr")).getAttribute("aria-expanded").
                            equalsIgnoreCase("true")) {
                        flag = true;
                        break;
                    } else {
                        click(element.findElement(By.xpath("a")));
                        i++;
                    }
                }
                if (!flag && i == 3) {
                    log.error("clicked on the radio button but area to fill details is not expanded");
                }
            }
        } catch (Exception e) {
            log.error("Unable to select radio button against-->>" + text);
            e.printStackTrace();
        }
        return flag;
    }


    public String getSummaryMessage() {
        String text = "Dummy";

        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver).
                withTimeout(Duration.ofSeconds(15)).
                pollingEvery(Duration.ofSeconds(3)).
                ignoring(ElementNotVisibleException.class).
                ignoring(NotFoundException.class);
        try {
            WebElement element = fluentWait.until(ExpectedConditions.visibilityOfElementLocated(summaryMessage));
            text = element.getText();
        } catch (Exception e) {
            System.out.println("Page Is taking more time than expected to navigate to payment");
            log.error("Unable to navigate to payment page ", e.fillInStackTrace());
        }
        return text;
    }

    public boolean selectOptionTarjeta(String option) {
        boolean flag = false;
        isPageReady(driver);
        String xpath = tarjeta.replace("xxx", option);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        int i = 0;
        while (i < 4) {
            if (element.findElement(By.xpath("parent::a")).getAttribute("aria-expanded") != null &&
                    element.findElement(By.xpath("parent::a")).getAttribute("aria-expanded").equalsIgnoreCase("true")) {
                flag = true;
                break;
            } else {
                click(element);
                i++;
            }
        }
        return flag;
    }


    public boolean enterCardHolderName(String name) {
        return setText(driver.findElement(cardHolderName), name);
    }

    public boolean enterCardNumber(String number) {
        return setText(driver.findElement(cardNumber), number);
    }

    public boolean enterExpiryMonth(String month) {
        return setText(driver.findElement(expiryMonth), month);
    }

    public boolean enterExpiryYear(String year) {
        return setText(driver.findElement(expiryYear), year);
    }

    public boolean enterCCVNumber(String ccv) {
        return setText(driver.findElement(ccvNumber), ccv);
    }

    public boolean clickpagarConTarjetaButton() {
        return click(driver.findElement(pagarConTarjeta));
    }

    public boolean enterEmailId(String email) {
        return setText(driver.findElement(emailId), email);
    }

    public boolean fillPopUpEmailIdAndPassword(String emailId, String pswrd) {
        setText(driver.findElement(popUpEmailId), emailId);
        setText(driver.findElement(popUpPassword), pswrd);
        return true;
    }

    public String getTextFromPopUpHeader() {
        return getText(wait.until(ExpectedConditions.visibilityOfElementLocated(popUpHeaderText)));
    }

    public boolean clickRobotCheckBoxAndClickSubmit() {
        boolean status = false;
        //---switching to iframe-----//
        try {
            List<WebElement> iframes= wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//iframe[@title='reCAPTCHA']")));
            log.info("Number of iframes are " + iframes.size());
            for (int i = 0; i < iframes.size()-1; i++) {
                driver.switchTo().frame(iframes.get(i));
                if (driver.findElements(popUpIamNotRobotCheckBox).size()!= 0) {
                    if(!click(driver.findElement(popUpIamNotRobotCheckBox))){
                        clickElementByJS(driver.findElement(popUpIamNotRobotCheckBox));
                    }
                    driver.switchTo().defaultContent();
                    break;
                }
                driver.switchTo().defaultContent();
            }
            WebElement popSubmitButton = driver.findElement(popUpSubmitButton);
            click(popSubmitButton);
            log.info("Waiting for to be successfully submitted ");
            FluentWait<WebDriver> fluentWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(TimeoutException.class);
            fluentWait.until(ExpectedConditions.invisibilityOfElementLocated(popUpSubmitButton));
            status = true;
        } catch (Exception e) {
            System.out.println("Pop Up Is Still Visible");
            log.error("Pop Up Is Still Visbile Unable to check if recharge is successfull ", e.fillInStackTrace());
        } finally {
            driver.switchTo().defaultContent();
        }
        return status;
    }

    public String getRechargeSuccessMessage() {
        String text = "Not Found";
        isPageReady(driver);
        isSpinnerThereAndLoaded(driver);
        wait.withTimeout(Duration.ofSeconds(10));
        wait.pollingEvery(Duration.ofSeconds(1));
        wait.ignoring(Exception.class);
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(rechargeSuccessMessage));
            text = getText(element);
        } catch (Exception e) {
            log.error("Recharge might not be successful please logs and screenshot");
            e.printStackTrace();
        }
        return text;
    }
}
