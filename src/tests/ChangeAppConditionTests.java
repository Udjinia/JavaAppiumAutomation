package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase
{
    @Test
    public void testChangeScreenOrientationOnSearchResult()
    {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        String title_befor_rotation=ArticlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String title_after_rotation=ArticlePageObject.getArticleTitle();

        assertEquals(
                "Article title have been change after screen rotation",
                title_befor_rotation,
                title_after_rotation
        );

        this.rotateScreenPortrait();

        String title_after_second_rotation=ArticlePageObject.getArticleTitle();

        assertEquals(
                "Article title have been change after screen second rotation",
                title_befor_rotation,
                title_after_second_rotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroudApp(2);
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }
}
