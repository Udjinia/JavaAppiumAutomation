package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.Factories.ArticlePageObjectFactory;
import lib.ui.Factories.MyListPageObjectFactory;
import lib.ui.Factories.NavigationUIFactory;
import lib.ui.Factories.SearchPageObjectFactory;
import lib.ui.MyListPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListTests extends CoreTestCase
{
    private static String
            NAME_OF_FOLDER ="Learning programing";

    @Test

    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        MyListPageObject MyListPageObject = MyListPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        ArticlePageObject.waitForTitleElement();
        String article_title=ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(NAME_OF_FOLDER);
        } else {
                    ArticlePageObject.addArticleToMySaved();
                }

        ArticlePageObject.closeArticle();
        NavigationUI.clickMyList();

        if (Platform.getInstance().isAndroid()){
            MyListPageObject.openFolderByName(NAME_OF_FOLDER);
        }
        MyListPageObject.swipeByArticleToDelete(article_title);
    }
}
