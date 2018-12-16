package tests.IOS;

import lib.IOSTestCase;
import lib.ui.WelkomPageObject;
import org.junit.Test;


public class GetStartedTest extends IOSTestCase
{
   @Test
    public void testPassThroughWelcom()
   {
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
