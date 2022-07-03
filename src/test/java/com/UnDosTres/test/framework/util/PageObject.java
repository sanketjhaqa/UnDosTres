package com.UnDosTres.test.framework.util;


import com.UnDosTres.test.framework.testBase.WebDriverHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;


public abstract class PageObject
{
    protected WebDriver driver;
    protected static WebDriverWait wait;
    protected Actions actions;
    private static Logger log= LogManager.getLogger(PageObject.class.getName());

    public PageObject()
    {
        this.driver= WebDriverHelper.getInstance().getWebDriver();
        wait = new WebDriverWait(driver,10);
        actions = new Actions(driver);
    }

    public void moveToElementAndClick(WebElement element)
    {
        try{

            actions.moveToElement(element).click().build().perform();
            log.info("Successfully Move to the element and clicked-->"+element.toString());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public  boolean setText(WebElement element,String data)
    {
        isPageReady(driver);

        boolean status=false;

        try
        {
            log.info("Waiting until field is clickable->"+element.toString());
            wait.until(ExpectedConditions.visibilityOf(element));
            actions.moveToElement(element).build().perform();
            log.info("Moving to the element-->"+element.toString());
            WebElement element1=wait.until(ExpectedConditions.elementToBeClickable(element));
            element1.click();
            element1.clear();
            log.info("Clear data from the field if present-->"+element.toString());
        }catch (Exception e)
        {
            log.error("Element->>"+element.toString()+" is not clickable",e.fillInStackTrace());
        }
        int count=0;

        do
            {
               element.sendKeys(data);
                log.info("Field has been populated with data-->>"+data);

               if(element.getText().trim().equalsIgnoreCase(data))
               {
                   log.info("Validated that Field has been populated with data-->>"+data);
                   //System.out.println("Value of Element "+element+" is "+data+" entered successfully");
                   status=true;
                   break;
               }
               else
                   {
                       if(element.getAttribute("value").trim().equalsIgnoreCase(data))
                       {
                           log.info("Validate with attribute value that field populated with data-->>"+data);
                           status=true;
                           break;
                       }
                       else
                       {
                           count++;
                       }
                   }
            }while (count!=4);

        if(status!=true)
        {
            //System.out.println("Unable to check if data is successfully entered for "+element);
            log.warn("Unable to validate that data has been entered successfully-->>"+data);
        }
        return status;
    }

    public boolean click(WebElement element)
    {
        isPageReady(driver);

        boolean status=false;

        try
        {
            log.info("Waiting for the element to be clickable-->>"+element.toString());
            wait.until(ExpectedConditions.elementToBeClickable(element));

            actions.moveToElement(element).click().build().perform();
            log.info("Successfully moved to the element and clicked-->>"+element.toString());

            status=true;
        }catch (Exception e)
        {
            //System.out.println("Unable to click over element "+element);
            log.error("Unable to click over click over element-->>"+element.toString()+"\n",e.fillInStackTrace());
        }
        return status;
    }

    public  boolean hover(WebElement element)
    {

        isPageReady(driver);

        boolean status=false;
        try
        {
            log.info("Waiting for the visibility of the element in DOM-->>"+element.toString());
            wait.until(ExpectedConditions.visibilityOf(element));

            actions.moveToElement(element).build().perform();
            log.info("Successfully move to the element ");
            status=true;
        }catch (Exception e)
        {
            //System.out.println("Unable to click over element "+element);
            log.error("Unable to hover over the element",e.fillInStackTrace());
        }
        return status;
    }

    public String getText(WebElement element)
    {
        isPageReady(driver);
        String text="Dummy";
        try
        {
            log.info("Getting text with the help of getText method");
            if(element.getText()!=null || !element.getText().isEmpty())
          {
              text=element.getText().trim();
              log.info("Text has been found with getText method which is-->>"+text);
          }

          else
              {
                  log.info("Getting text with the help of attribute value");
                  if(element.getAttribute("value")!=null || !element.getAttribute("value").isEmpty())
                  {
                      text=element.getAttribute("value").trim();
                      log.info("Text has been found with Attribute value method which is-->>"+text);
                  }
                  else
                      {
                          //System.out.println("Unable to extract text from element "+element);
                          log.error("Unable to extract text with getText and getAttribute value-->>"+element);
                      }
              }

        }catch (Exception e)
        {
           // System.out.println("Element "+element+ "is not visible properly");
            log.error("Unable to extract text from the element-->>"+element.toString()+"\n",e.fillInStackTrace());
        }

        return text;
    }

    public  boolean setAutoSuggestiveDropDown(By by, String optionToSelect)
    {
        isPageReady(driver);
        boolean status=false;

        List<WebElement> list=null;

        try
        {
            list=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        }catch (Exception e)
        {
            System.out.println("Unable to fetch List");
            e.printStackTrace();
        }
        if(list!=null || !list.isEmpty())
        {
            for (WebElement e:list)
            {
                String text=e.getText().trim().replace("\n"," ");

                System.out.println(text);

                if(text.contains(optionToSelect))
                {
                    e.click();
                    status=true;
                    break;
                }
            }
        }
        return status;
    }

    public static void isPageReady(WebDriver driver)
    {
        wait.withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(2)).ignoring(Exception.class);
        try{
            log.info("Getting the readiness of the page");
            wait.until(driver1 -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
            log.info("Page is ready");
        }catch (Exception e)
        {
            //System.out.println("Page is not getting stable within 20 sec");
            log.error("Page is not getting stable within 20 sec",e.fillInStackTrace());
        }
    }

    public static boolean isSpinnerThereAndLoaded(WebDriver driver){
        boolean flag=false;
        try {
            FluentWait<WebDriver> wait = new FluentWait<>(driver);
            WebElement spinner= wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#loader-spinner-section div.spinner")));
            wait.withTimeout(Duration.ofSeconds(5));
            wait.pollingEvery(Duration.ofSeconds(1));
            wait.ignoring(TimeoutException.class);
            wait.until(ExpectedConditions.invisibilityOf(spinner));
            flag = true;

        }catch (NoSuchElementException e){
            log.info("Try to find the spinner but it is not there No Such Element");
        }catch (Exception e1){
            log.error("Spinner is there page might be taking more time to load please check");
            e1.printStackTrace();
        }
        return flag;
    }

    public boolean urlContains(String fraction){
        boolean flag = false;
        try {
            flag = wait.until(ExpectedConditions.urlContains(fraction));
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public void clickElementByJS(WebElement element){
        log.info("Going to click webelement through JS-->>"+element);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        log.info("Clicked through JS completed");
    }
}
