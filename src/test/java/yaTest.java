import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;
import org.testng.annotations.Optional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class yaTest {
    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    @Parameters("Browser")
    public void start(@Optional String browserName){
        if (browserName == null) {
            browserName = "chrome";
        }

        if (browserName.contentEquals("chrome")) {
            driver = new ChromeDriver();
        } else  if (browserName.contentEquals("mobile")) {
            Map<String, String> mobileEmulation = new HashMap<String, String>();
            mobileEmulation.put("deviceName", "Galaxy S5");

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

            driver = new ChromeDriver(chromeOptions);
        } else if (browserName.contentEquals("ff")) {
            driver = new FirefoxDriver();
        } else if (browserName.contentEquals("ie")) {
            driver = new InternetExplorerDriver();
        } else {
            throw new IllegalArgumentException("Unknown browser");
        }

        driver.get("https://www.yandex.by/");

        WebElement inputField = driver.findElement(By.name("text"));
        inputField.sendKeys("Авиабилеты");

        WebElement searchButton = driver.findElement(By.xpath("//button[@type='submit']"));
        searchButton.click();
    }

    @AfterMethod
    public void finish(){
        driver.quit();
    }


    @Test
    public void verifySearchInAddress() {
        String currentURL = driver.getCurrentUrl();

        Assert.assertTrue(currentURL.contains("search"), "Search result page should be opened");
    }

    @Test
    public void defaultLocationInFromField(){
        WebElement regionChange = driver.findElement(By.xpath("//div[@class='region-change']/a"));
        String currentRegion = regionChange.getText();

        WebElement fromInputField = driver.findElement(By.name("fromName"));
        String fromValue = fromInputField.getAttribute("value");

        Assert.assertEquals(fromValue, currentRegion, "Values should be the same ");

    }

    @Test(description = "Check placeholder")
    public void toPlaceholderVerifying() {
        WebElement toField = driver.findElement(By.name("toName"));
        String toValue = toField.getAttribute("value");

        Assert.assertTrue(toValue.isEmpty(), "Placeholder is visible");
    }

    @Test
    public void whenDefaultDateCheck(){
        WebElement whenField = driver.findElement(By.name("when"));
        String defaultDate = whenField.getAttribute("value");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 3);
        Date date = cal.getTime();

        Assert.assertEquals(defaultDate, dateFormat.format(date), "Default date should be correct");
    }

    @Test(groups = {"search", "regression"})
    public void whenHasClearButton() {
        WebElement fromInputClearButton = driver.findElement(By.xpath("//div[input[@name='when']]/span"));

        Assert.assertTrue(fromInputClearButton.isDisplayed(), "When input should have clear button");

        WebElement whenDateHolder = driver.findElement(By.xpath("//div[input[@name='when']]/input"));

        Assert.assertFalse(whenDateHolder.getAttribute("value").isEmpty(), "When input shouldn't have calendar icon");
    }

    @Test(groups = {"search", "smoke"})
    public void searchTicketButtonIsPresent() {
        WebElement searchTicketButton = driver.findElement(By.xpath("//a[contains(@class, 'find-offers')]"));

        Assert.assertTrue(searchTicketButton.isDisplayed());
    }

    @Test
    public void switchButtonIsDisplayed() {
        if (!isDesktop()) {
            throw new SkipException("Doesn't work for mobile");
        }
        WebElement switchButton = driver.findElement(By.xpath("//div[@class='geo-route__switcher']"));
        Assert.assertTrue(switchButton.isDisplayed(), "Switch button should be displayed");
    }

    @Test
    public void clearFromByBackSpace(){
        WebElement fromInputField = driver.findElement(By.name("fromName"));
        String fromValue = fromInputField.getAttribute("value");

        int numberOfLetters = fromValue.length();

        for (int i = 0; i < numberOfLetters ; i++) {
            fromInputField.sendKeys(Keys.BACK_SPACE);
        }

        fromValue = fromInputField.getAttribute("value");

        Assert.assertTrue(fromValue.isEmpty(), "Value should be empty");
    }

    public boolean isDesktop() {
        List<WebElement> moreLabels = driver.findElements(By.xpath("//div[contains(@class, 'navigation__more-label')]"));

        return moreLabels.size() > 0;

    }




}
