package duco.tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import duco.BaseTest;
import duco.RetryAnalyzer;
import duco.pages.HomePage;
import duco.pages.ResultsPage;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static org.assertj.core.api.Assertions.assertThat;

public class GoogleTests extends BaseTest {

    private static final String NOT_FOUND_MESSAGE = "Podana fraza - %s - nie została odnaleziona.";
    private static final String INVALID_QUERY = "gggzzzzhhhhhhyyzzqqq";

    @Test
    public void shouldReturnTenResults() {
        HomePage homePage = new HomePage();
        homePage.search(TEST);
        ResultsPage resultsPage = new ResultsPage();
        Set<String> urlsFromResults = new HashSet<>(resultsPage.results().texts());
        urlsFromResults.remove("");

        assertThat(urlsFromResults.size()).as("By default 10 results are returned").isEqualTo(10);
    }

    @Test
    public void shouldHaveTwelvePagesOfPaginationLinksOnResultsPage() {
        HomePage homePage = new HomePage();
        homePage.search(TEST);
        ResultsPage resultsPage = new ResultsPage();
        int size = resultsPage.paginationPages().size();

        assertThat(size).as("Should return 12 pagination pages").isEqualTo(12);
    }

    @Test
    public void shouldReturnDifferentLinksWhenPageIsChanged() {
        HomePage homePage = new HomePage();
        homePage.search(TEST);
        ResultsPage resultsPage = new ResultsPage();
        Set<String> urlsFromResults = new HashSet<>(resultsPage.results().texts());
        resultsPage.paginationPages().get(3).click();
        Set<String> urlsFromThirdPage = new HashSet<>(resultsPage.results().texts());

        assertThat(urlsFromResults).as("Should return different links").isNotEqualTo(urlsFromThirdPage);
    }

    @Test()//(retryAnalyzer = RetryAnalyzer.class)
    public void shouldRedirectToSiteFromResults() {
        HomePage homePage = new HomePage();
        homePage.search(TEST);
        String resultsUrl = WebDriverRunner.url();
        ResultsPage resultsPage = new ResultsPage();
        resultsPage.results().shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1));
        resultsPage.results().get(0).click();
        resultsPage.results().get(0).shouldNotBe(Condition.visible);
        String redirectedUrl = WebDriverRunner.url();

        assertThat(redirectedUrl).as("Should redirect to site from results").isNotEqualTo(resultsUrl);
    }

    @Test(description = "After clicking logo link to change language is not displayed", enabled = false)
    public void shouldReturnDifferentResultsWhenLanguageIsChangedNotWorking() {
        HomePage homePage = new HomePage();
        homePage.search(TEST);
        ResultsPage resultsPage = new ResultsPage();
        Set<String> urlsFromResults = new HashSet<>(resultsPage.results().texts());
        resultsPage.clickLogo();
        homePage.changeLanguage();
        homePage.search(TEST);
        Set<String> urlsWithDifferentLanguage = new HashSet<>(resultsPage.results().texts());

        assertThat(urlsFromResults).as("Results should be different if language was changed").isNotEqualTo(urlsWithDifferentLanguage);
    }

    @Test()
    public void shouldReturnDifferentResultsWhenLanguageIsChanged() {
        HomePage homePage = new HomePage();
        homePage.search(TEST);
        ResultsPage resultsPage = new ResultsPage();
        Set<String> urlsFromResults = new HashSet<>(resultsPage.results().texts());
        closeWebDriver();
        BaseTest.openPage();
        homePage.changeLanguage();
        homePage.search(TEST);
        Set<String> urlsWithDifferentLanguage = new HashSet<>(resultsPage.results().texts());

        assertThat(urlsFromResults).as("Results should be different if language was changed").isNotEqualTo(urlsWithDifferentLanguage);
    }

    @Test
    public void shouldNotReturnResultForMeaninglessQuery() {
        HomePage homePage = new HomePage();
        homePage.search(INVALID_QUERY);
        ResultsPage resultsPage = new ResultsPage();
        String message = resultsPage.notFoundMessage().text();

        assertThat(message).as("Results not found message should be displayed").isEqualTo(String.format(NOT_FOUND_MESSAGE, INVALID_QUERY));
    }
}
