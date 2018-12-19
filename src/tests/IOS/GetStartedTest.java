package tests.IOS;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelkomPageObject;
import org.junit.Test;


public class GetStartedTest extends CoreTestCase
{
   @Test
    public void testPassThroughWelcom()
   {
       if (Platform.getInstance().isAndroid()){
           return;
       }
       WelkomPageObject WelkomPage= new WelkomPageObject(driver);

       WelkomPage.waitForLearnMoreLink();
       WelkomPage.ClickNextButton();

       WelkomPage.waitForNewWayToExploreText();
       WelkomPage.ClickNextButton();

       WelkomPage.waitForAddOrEditPreferredLanguagesText();
       WelkomPage.ClickNextButton();

       WelkomPage.waitForLearnMoreAboutDataCollectedText();
       WelkomPage.clickGetStartedButton();
   }
}
