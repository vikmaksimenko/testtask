package automation.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import realword.automation.BaseTest;
import realword.automation.HomePage;

import static org.testng.Assert.assertTrue;

public class TestFiltrationByTags extends BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;

    @BeforeTest(description = "Initialize driver")
    public void init() {
        this.driver = getDriver();
        this.homePage = new HomePage(driver);
        this.homePage.go();
    }

    @Test(description = "Check if any article review is found by tag filtering.")
    public void checkFirstArticlePreviewContainsSelectedTag() {
        String tagName = "as";
        homePage.selectTag(tagName);

        assertTrue(homePage.getTagsForFirstArticlePreview().stream()
                .filter(tagElement -> tagElement.getText().trim().equalsIgnoreCase(tagName)).count() == 1);
    }

    @Test(description = "Check all article reviews (10 per 1 page) contain selected tag.")
    public void checkSelectedTagAppears10Times() {
        String tagName = "butt";
        homePage.selectTag(tagName);

        assertTrue(homePage.getAllFoundTagsOnPage().stream()
                .filter(tagElement -> tagElement.getText().trim().equalsIgnoreCase(tagName)).count() == 10);
    }

    @Test(description = "Check tag with current name is absent in filtering.")
    public void checkTagIsAbsentInFiltering() {
        String tagName = "absenttag";

        assertTrue(homePage.findTagInFiltering(tagName).isEmpty());
    }

    @AfterTest(description = " Close browser")
    public void tearDown() {
        if (null != this.driver) {
            this.driver.quit();
        }
    }
}
