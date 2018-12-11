package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject
{
    private static final String
            SEARCH_INIT_ELEMENT="//*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT="//*[contains(@text,'Search…')]",
            SEARCH_CANCEL_BUTTON="org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL ="//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT="//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT="//*[@text='No results found']";

    public SearchPageObject (AppiumDriver driver)
    {
        super(driver);
    }

    /*TEMPLATE METHODS*/
    private static String gerResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}",substring);
    }
    /*TEMPLATE METHODS*/

    public void initSearchInput()
    {
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT),"Cannot find 'Search Wikipedia' input",15);
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT),"Cannot find 'Search Wikipedia' input after clicking init element",5);
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT),search_line,"Cannot find and type into search input",5);
    }

    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = gerResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath),"Cannot find search result with substring "+ search_result_xpath ,5);
    }

    public  void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON),"Cannot find X to cancel search",15);
    }

    public  void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON),"X is still present on the page",5);
    }

    public void clickCancelButton()
    {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON),"Cannot find and click search cancel button",5);
    }

    public void clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = gerResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath),"Cannot find and click search result with substring "+ search_result_xpath ,10);
    }

    public int GetAmountOfFoundArticle()
    {

        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by request",
                15
        );

        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));

    }

    public void waitForEmptyResultLabel()
    {
        this.waitForElementPresent(By.xpath(SEARCH_EMPTY_RESULT),"Cannot find empty result element",15);

    }

    public void assertThereIsNoResultOfSearch()
    {
        this.waitForElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT),"We supposed not ti find any result",15);
    }

}
