import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class simpleTestFerst {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.yandex.by/");

        WebElement inputField = driver.findElement(By.id("text"));
        inputField.sendKeys("Авиабилеты");

        String oldUrl = driver.getCurrentUrl();

        System.out.println(oldUrl);

        WebElement searchButton = driver.findElement(By.xpath("//button[@type='submit']"));
        searchButton.click();

        String newUrl = driver.getCurrentUrl();

        System.out.println(newUrl);

        WebElement fromInputField = driver.findElement(By.name("fromName"));
        System.out.println(fromInputField.getAttribute("value"));

        WebElement fromInputClearButton = driver.findElement(By.xpath("//span[input[@name='fromName']]/span"));
        //fromInputClearButton.click();
        System.out.println(fromInputClearButton.isDisplayed());

        WebElement toInputField = driver.findElement(By.name("toName"));





        driver.quit();
    }
}
