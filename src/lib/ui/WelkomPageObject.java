package lib.ui;

import io.appium.java_client.AppiumDriver;

public class WelkomPageObject extends MainPageObject
{
    private static final String
        STEP_LEARN_MORE_LINK="id:Learn more about Wikipedia",
        STEP_NEW_WAY_TO_EXPLORE_TEXT="id:New ways to explore",
        STEP_ADD_OR_EDIT_PREFERRED_LANGUAGES="id:Add or edit preferred languages",
        STEP_LEARN_MORE_ABOUT_DATA_COLLECED_TEXT="id:Learn more about data collected",
        NEXT_LINK="id:Next",
        GET_STARTED_BUTTON="id:Get started",
        SKIP="id:Skip";

    public WelkomPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    public void waitForLearnMoreLink()
    {
        this.waitForElementPresent(STEP_LEARN_MORE_LINK,"Cannot find 'Learn more about Wikipedia'",10);
    }

    public void waitForNewWayToExploreText()
    {
        this.waitForElementPresent(STEP_NEW_WAY_TO_EXPLORE_TEXT,"Cannot find 'New ways to explore'",10);
    }

    public void waitForAddOrEditPreferredLanguagesText()
    {
        this.waitForElementPresent(STEP_ADD_OR_EDIT_PREFERRED_LANGUAGES,"Cannot find 'Add or edit preferred languages'",10);
    }

    public void waitForLearnMoreAboutDataCollectedText()
    {
        this.waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECED_TEXT,"Cannot find 'Learn more about data collected'",10);
    }


    public void ClickNextButton()
    {
        this.waitForElementAndClick(NEXT_LINK,"Cannot find and click the Next link",10);
    }

    public void clickGetStartedButton()
    {
        this.waitForElementAndClick(GET_STARTED_BUTTON,"Cannot find and click the 'Get started' button",10);
    }

    public void clickSkip()
    {
        this.waitForElementAndClick(SKIP,"Cannot find and click the Skip button",15);
    }


}
