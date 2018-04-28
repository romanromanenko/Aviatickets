package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage {

    @FindBy(name = "text")
    WebElement inputField;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement searchButton;

    public void search(String searchPharse){

        inputField.sendKeys(searchPharse);
        searchButton.click();
    }

}
