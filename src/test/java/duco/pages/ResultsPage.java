package duco.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Getter
@Accessors(fluent = true)
public class ResultsPage {

    private ElementsCollection results = $$(By.xpath("//div[@id='search']//div//*//cite"));
    private ElementsCollection paginationPages =  $$(By.xpath("//tbody/tr/td"));
    private SelenideElement logo = $(By.xpath("//div[@class='logo']//a"));
    private SelenideElement notFoundMessage = $(By.xpath("(//*[@id='topstuff']/div//p)[1]"));

    public void clickLogo(){
        logo.click();
    }

}
