package duco.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Getter
@Accessors(fluent = true)
public class HomePage {

    private SelenideElement queryTextField = $(By.name("q"));
    private SelenideElement searchButton = $(By.name("btnK"));
    private SelenideElement luckyGuessButton = $(By.name("btnI"));
    private ElementsCollection hints = $$(By.xpath("(//div[@role='presentation'])[1]//li"));
    private SelenideElement language = $(By.xpath("//*[@id='SIvCob']//a"));
    private static SelenideElement cookieAccept = $$(By.xpath("//button")).get(5);


    public void search(String text){
        setTextForSearch(text);
        queryTextField.sendKeys(Keys.ENTER);
    }

    public void setTextForSearch(String text) {
        queryTextField.setValue(text);
    }

    public boolean isSearchButtonDisplayed(){
        return isButtonDisplayed(searchButton);
    }

    private boolean isButtonDisplayed(SelenideElement button){
        return Boolean.valueOf(button.getAttribute("displayed"));
    }

    public void changeLanguage(){
        language.click();
    }

    public static void acceptCookies(){
        cookieAccept.click();
    }

}
