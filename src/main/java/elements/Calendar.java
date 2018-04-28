package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Calendar {

    private WebElement calendarElement;

    private By calendarTitle = By.xpath("//div[@class='calendar__name']");
    private By goToNextMonth = By.xpath("//div[contains(@class,'calendar__arrow_direction_right')]");
    private By goToPreviouseMonth = By.xpath("//div[contains(@class,'calendar__arrow_direction_left')]");


    public Calendar(WebElement calendarElement) {
        this.calendarElement = calendarElement;
    }

    public String getCalendarTitle(){

        return calendarElement.findElement(calendarTitle).getText();
    }

    public void goToNextMonth (){
        calendarElement.findElement(goToNextMonth).click();
    }

    public void goToPreviouseMontht (){
        calendarElement.findElement(goToPreviouseMonth).click();
    }
}
