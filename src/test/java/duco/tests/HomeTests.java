package duco.tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.WebDriverRunner;
import duco.BaseTest;
import duco.RetryAnalyzer;
import duco.pages.HomePage;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeTests extends BaseTest {

    @Test
    public void searchForEmptyString() {
        HomePage homePage = new HomePage();
        String initialUrl = WebDriverRunner.url();
        homePage.search("");
        String afterSearch = WebDriverRunner.url();

        assertThat(afterSearch).as("Searching for empty string should not redirect to results website").isEqualTo(initialUrl);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void shouldDisplayHintsInQueryTextField() {
        HomePage homePage = new HomePage();
        int initialHintsSize = homePage.hints().size();
        homePage.setTextForSearch(TEST);
        homePage.hints().shouldHave(CollectionCondition.sizeGreaterThan(0));
        int hintsSize = homePage.hints().size();
        List<String> hintsList = homePage.hints().texts();

        SoftAssertions s = new SoftAssertions();
        s.assertThat(hintsSize).as("Hints size when text is provided should be greater than initial").isGreaterThan(initialHintsSize);
        for (String hint : hintsList) {
            s.assertThat(hint).as(String.format("Hint should starts with '%s'", TEST)).startsWith(TEST);
        }
        s.assertAll();
    }
}
