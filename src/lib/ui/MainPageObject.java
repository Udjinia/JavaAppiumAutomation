package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import lib.Platform;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject (AppiumDriver driver)
    {
        this.driver=driver;
    }

    public WebElement waitForElementPresent (String locator, String error_message, long timeoutInSecond )
    {
        By by=this.getLocatorByString(locator);
        WebDriverWait wait=new WebDriverWait(driver,timeoutInSecond);
        wait.withMessage(error_message+"\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public boolean waitForElementNotPresent (String locator,String error_message, long timeoutInSecond)
    {
        By by=this.getLocatorByString(locator);
        WebDriverWait wait=new WebDriverWait(driver,timeoutInSecond);
        wait.withMessage(error_message+"\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSecond)
    {
        WebElement element= waitForElementPresent(locator,error_message,timeoutInSecond);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSecond)
    {
        WebElement element= waitForElementPresent(locator ,error_message,timeoutInSecond);
        element.sendKeys(value);
        return element;
    }

    public WebElement waitForElementAndClear (String locator, String error_message, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSecond);
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

    public  void swipUpToFindElement (String locator, String error_message, int max_swipe)
    {
        By by=this.getLocatorByString(locator);
        int already_swipr=0;
        while (driver.findElements(by).size()==0){

            if (already_swipr > max_swipe){
                waitForElementPresent(locator,"Can not find element by swipping up \n"+error_message,0);
                return;
            }
            swipUpQuick();
            ++already_swipr;
        }
    }

    public void swipUpTillElementApeare (String locator, String error_message, int max_swipe)
    {
        int already_swipr=0;
        while (!this.isElementLocatedOnTheScreen(locator))
        {
            if(already_swipr>max_swipe)
            {
                Assert.assertTrue(error_message,this.isElementLocatedOnTheScreen(locator));
            }
            swipUpQuick();
            ++already_swipr;
        }
    }

    public boolean isElementLocatedOnTheScreen (String locator)
    {
        int element_locator_by_y= this.waitForElementPresent(locator,"Cannot find element by locator on the screen",10).getLocation().getY();
        int screen_size_by_y= driver.manage().window().getSize().getHeight();
        return element_locator_by_y < screen_size_by_y;

    }

    public void clickElementToTheRightUpperCorner(String locator, String error_message)
    {
        WebElement element=this.waitForElementPresent(locator +"/.." ,error_message,10);
        int right_x = element.getLocation().getX();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y +lower_y)/2;
        int width = element.getSize().getWidth();

        int point_to_click_x = (right_x +width) - 3;
        int point_to_click_y=middle_y;

        TouchAction action= new TouchAction(driver);
        action.tap(PointOption.point(point_to_click_x,point_to_click_y)).perform();
    }

    public  void swipElementToLeft (String locator,String error_message)
    {
        WebElement element=waitForElementPresent(locator,error_message,10);

        int left_x=element.getLocation().getX();
        int right_x=left_x+element.getSize().getWidth();
        int upper_y=element.getLocation().getY();
        int lower_y=upper_y+element.getSize().getHeight();
        int middle_y=(upper_y+lower_y)/2;

        TouchAction action= new TouchAction(driver);

        action.press(PointOption.point(right_x,middle_y));
        action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)));


        if (Platform.getInstance().isAndroid()) {
        action.moveTo(PointOption.point(left_x, middle_y));
        }
        else {
            int offset_x=(-1 * element.getSize().getWidth());
            action.moveTo(PointOption.point(offset_x, 0));
        }
        action.release();
        action.perform();
    }
//-----------------

    public int getAmountOfElements(String locator)
    {
        By by=this.getLocatorByString(locator);
        List elements=driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(String locator,String error_message)
    {
        int amount_of_elements=getAmountOfElements(locator);
        if (amount_of_elements > 0){
            String default_message="An element '"+locator+"' supposed to be not present";
            throw new AssertionError(default_message+" "+error_message);
        }

    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String error_message, long timeoutInSecond)
    {
        WebElement element=waitForElementPresent(locator,error_message,timeoutInSecond);
        return element.getAttribute(attribute);
    }

    private By getLocatorByString(String locator_with_type)
    {
        String[] explored_locator=locator_with_type.split(Pattern.quote(":"),2);
        String by_type =explored_locator[0];
        String locator =explored_locator[1];
        if (by_type.equals("xpath"))
        {
            return By.xpath(locator);
        }
        else
            if (by_type.equals("id"))
            {
                return  By.id(locator);
            }
            throw new IllegalArgumentException("Cannot get type of locator. Locator="+locator_with_type);

    }
}
