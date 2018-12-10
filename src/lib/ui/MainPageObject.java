package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject (AppiumDriver driver)
    {
        this.driver=driver;
    }

    public WebElement waitForElementPresent (By by, String error_message, long timeoutInSecond )
    {
        WebDriverWait wait=new WebDriverWait(driver,timeoutInSecond);
        wait.withMessage(error_message+"\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public boolean waitForElementNotPresent (By by,String error_message, long timeoutInSecond)
    {
        WebDriverWait wait=new WebDriverWait(driver,timeoutInSecond);
        wait.withMessage(error_message+"\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClick(By by, String error_message, long timeoutInSecond)
    {
        WebElement element= waitForElementPresent(by,error_message,timeoutInSecond);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSecond)
    {
        WebElement element= waitForElementPresent(by ,error_message,timeoutInSecond);
        element.sendKeys(value);
        return element;
    }

    public WebElement waitForElementAndClear (By by, String error_message, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSecond);
        element.clear();
        return element;
    }
//-------Swipe

    public void swipeUp (int timeOfSwipe)
    {
        TouchAction action= new TouchAction(driver);

        Dimension size=driver.manage().window().getSize();
        int x=(int)(size.width/2);
        int start_y=(int)(size.height*0.85);
        int end_y=(int)(size.height*0.1);

        action.
                press(PointOption.point(x,start_y)).
                waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe))).
                moveTo(PointOption.point(x,end_y)).
                release().
                perform();
    }

    public  void swipUpQuick()
    {
        swipeUp(1000);
    }

    public  void swipUpToFindElement (By by, String error_message, int max_swipe)
    {
        int already_swipr=0;
        while (driver.findElements(by).size()==0){

            if (already_swipr > max_swipe){
                waitForElementPresent(by,"Can not find element by swipping up \n"+error_message,0);
                return;
            }
            swipUpQuick();
            ++already_swipr;
        }
    }

    public  void swipElementToLeft (By by,String error_message)
    {
        WebElement element=waitForElementPresent(by,error_message,10);

        int left_x=element.getLocation().getX();
        int right_x=left_x+element.getSize().getWidth();
        int upper_y=element.getLocation().getY();
        int lower_y=upper_y+element.getSize().getHeight();
        int middle_y=(upper_y+lower_y)/2;

        TouchAction action= new TouchAction(driver);
        action.
                press(PointOption.point(right_x,middle_y)).
                waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).
                moveTo(PointOption.point(left_x,middle_y)).
                release().
                perform();
    }
//-----------------

    public int getAmountOfElements(By by)
    {
        List elements=driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(By by,String error_message)
    {
        int amount_of_elements=getAmountOfElements(by);
        if (amount_of_elements > 0){
            String default_message="An element '"+by.toString()+"' supposed to be not present";
            throw new AssertionError(default_message+" "+error_message);
        }

    }

    public String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSecond)
    {
        WebElement element=waitForElementPresent(by,error_message,timeoutInSecond);
        return element.getAttribute(attribute);
    }

}
