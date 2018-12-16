package lib.ui;

import io.appium.java_client.AppiumDriver;

public class MyListPageObject extends MainPageObject
{
    private static final String
        FOLDRR_BY_NAME_TPL="xpath://*[@text='{FOLDER_NAME}']",
        ARTICLE_BY_TITLE_TPL="xpath://*[@text='{TITLE}']";

    public MyListPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    /*TEMPLATE METHODS*/
    private static String gerFolderXpathByName(String name_of_folder)
    {
        return FOLDRR_BY_NAME_TPL.replace("{FOLDER_NAME}",name_of_folder);
    }

    private static String gerSavedArticleXpathByTitle(String article_title)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}",article_title);
    }
    /*TEMPLATE METHODS*/

    public void openFolderByName(String name_of_folder)
    {
        String folder_name_xpath=gerFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Created folder is not found by name " +name_of_folder,
                15
        );
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        String article_xpath=gerFolderXpathByName(article_title);
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by title "+article_title,
                5
        );
    }


    public void waitForArticleToDisappearByTitle(String article_title)
    {
        String article_xpath=gerFolderXpathByName(article_title);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present with title "+article_title,
                5
        );
    }

    public void swipeByArticleToDelete(String article_title)
    {

        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath=gerFolderXpathByName(article_title);
        this.swipElementToLeft(
                article_xpath,
                "Cannot find saved article"
        );

        this.waitForArticleToDisappearByTitle(article_title);
    }
}
