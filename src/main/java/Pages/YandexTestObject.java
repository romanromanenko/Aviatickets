import Pages.MainPage;
import Pages.SearchResultPage;
import base.TestBase;
import com.google.common.io.Files;
import helpers.helper;

import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class YandexTestObject  extends TestBase {

    private MainPage mainPage;
    private SearchResultPage searchResultPage;

    @BeforeMethod(alwaysRun = true)
    @Parameters("Browser")
    public void start(@Optional String browserName){

        super.start(browserName);

 //       mainPage = new MainPage(driver);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        searchResultPage = new SearchResultPage(driver);

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        driver.get("https://www.yandex.by/");

        mainPage.search("Авиабилеты");
    }

    @Test
    public void verifySearchInAddress() {
        String currentURL = driver.getCurrentUrl();

        Assert.assertTrue(currentURL.contains("search"), "Search result page should be opened");
    }

    @Test
    public void defaultLocationInFromField(){
        Assert.assertEquals(searchResultPage.getFromText(),
                searchResultPage.getCurrentRegion(), "Values should be the same");
    }

    @Test(description = "Check placeholder")
    public void toPlaceholderVerifying() {
        Assert.assertTrue(searchResultPage.getToPlaceholder().isEmpty(), "Placeholder is visible");
    }

    @Test
    public void whenDefaultDateCheck(){
        String defaultDate = searchResultPage.getWhenDate();
        String expectedDate = helper.getCurrentDate("yyyy-MM-dd", 3);

        Assert.assertEquals(defaultDate, expectedDate, "Default date should be correct");
    }

    @Test(groups = {"search", "regression"})
    public void whenHasClearButton() {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(searchResultPage.isFromInputClearButtonDisplayed(), "When input should have clear button");
        softAssert.assertFalse(searchResultPage.getWhenDateHolder().isEmpty(), "When input shouldn't have calendar icon");

        softAssert.assertAll();
    }

    @Test(groups = {"search", "smoke"})
    public void searchTicketButtonIsPresent() {
        Assert.assertTrue(searchResultPage.isSearchTicketButtonDisplayed(),"Search ticket button should be displayed");
    }

    @Test
    public void switchButtonIsDisplayed() {
        if (!isDesktop()) {
            throw new SkipException("Doesn't work for mobile");
        }
        Assert.assertTrue(searchResultPage.isSwitchButtonDisplayed(), "Switch button should be displayed");
    }

    @Test
    public void clearFromByBackSpace(){
        searchResultPage.clearFromInputByBackspace();
        Assert.assertTrue(searchResultPage.getFromText().isEmpty(), "Value should be empty");
    }

    @Test
    public void clearFromInputByClearButton(){
        searchResultPage.clickClearButton();
        Assert.assertTrue(searchResultPage.getFromText().isEmpty(), "Value should be empty");
    }

    @Test
    public void fromDropDownIsAppear() throws InterruptedException {
        searchResultPage.clickFromField();
        String ddText = searchResultPage.getDropDownTest();
        Assert.assertTrue(ddText.split("\n").length > 0, "Drop down list should contain ");

    }

    @Test
    public void fromDropDownIsAppwaerByTab(){
        searchResultPage.clickToInput();
        searchResultPage.sendKeys(Keys.SHIFT.toString() + Keys.TAB.toString());
    }

    @Test
    public void fromDropDwnWalidation() throws InterruptedException {

        searchResultPage.clickClearButton();
        searchResultPage.fillFromField("М");
        searchResultPage.scrollToFromField();
//        try {
//            takeScreenShot();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Thread.sleep(500);
//        try {
//            takeScreenShot();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        searchResultPage.fillFromField("и");
        Thread.sleep(500);
        searchResultPage.fillFromField("н");
        Thread.sleep(500);

        String ddText = searchResultPage.getDropDownTest();

        String[] cities = ddText.split("\n");
        boolean allCitiesAreOK = true;

        for (int i = 1; i <cities.length ; i++) {
            allCitiesAreOK = allCitiesAreOK && cities[i].startsWith("Мин");
        }
        Assert.assertTrue(allCitiesAreOK, "All cities should start with 'Мин'");
    }

    @Test
    public void inputString(){
        searchResultPage.clickClearButton();
        searchResultPage.inputStrinpInField("\\\\??***№№!..\\\\\\");
    }

    @Test
    public void clearFieldFrom(){
        searchResultPage.clickClearButton();
        searchResultPage.inputNameCity("Москва");
        searchResultPage.clearFromInputByBackspace();
    }

    @Test
    public void clearFieldWhereByButton(){
        searchResultPage.clearFieldWhereByButton("Москва");
    }

    @Test
    public void checkListDropeDown(){
        searchResultPage.inputNoCity();
    }

    @Test
    public void inputNameOfCity(){
        searchResultPage.inputName();
    }

//    public void takeScreenShot()  throws IOException {
//        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//        Date date = new Date();
//        long time =  date.getTime();
//        String pathToFile = "screenshots//screenshot_%s.png";
//        Files.copy(scrFile, new File(String.format(pathToFile, time)));
//
//
//    }

    @Test(dataProvider = "getcityName")
    public void fromDropDwnWalidationDataProvider(String city) throws InterruptedException {
        searchResultPage.clickClearButton();

        String[] cityLetters = city.split("");

        for (String letter:cityLetters) {
            searchResultPage.fillFromField(letter);
            Thread.sleep(500);
        }

        int a = 1;

//        try {
//            takeScreenShot();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Thread.sleep(500);
//        try {
//            takeScreenShot();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        String ddText = searchResultPage.getDropDownTest();

        String[] cities = ddText.split("\n");
        boolean allCitiesAreOK = true;

        for (int i = 1; i <cities.length ; i++) {
            allCitiesAreOK = allCitiesAreOK && cities[i].startsWith(city);
        }

        String infoMessage = "All cities should start with %s";
        Assert.assertTrue(allCitiesAreOK, infoMessage.replace("%s", city));
    }

    @Test
    public void workingWithWhenCalendar(){
        searchResultPage.clicWhenButton();
        searchResultPage.worcingWithCalendar();
    }

    @Test
    public void workingWithWhenReturnCalendar(){
        searchResultPage.clicreturnButton();
        searchResultPage.worcingWithCalendar();
    }

    public boolean isDesktop() {
        return searchResultPage.isMoreLabelDisplayed();
    }

    @DataProvider
    public Object[][] getcityName(){
        return new Object[][]{
                {"Мин"}, {"Мос"},
                {"сршы"}, {"Караг"},
                {"Min"}, {"Vui"}
        };
    }



}
