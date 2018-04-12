package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {

    private By inputField = By.name("text");
    private By searchButton = By.xpath("//button[@type='submit']");

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void search(String searchPharse){
        driver.findElement(inputField).sendKeys(searchPharse);
        driver.findElement(searchButton).click();
    }

}
