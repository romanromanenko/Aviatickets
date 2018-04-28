package base;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestBase {
    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    @Parameters("Browser")
    public void start(@Optional String browserName){
        if (browserName == null) {
            browserName = "chrome";
        }

        if (browserName.contentEquals("chrome")) {
            driver = new ChromeDriver();
        } else if (browserName.contentEquals("headless")){
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");

            driver = new ChromeDriver(chromeOptions);
        } else if (browserName.contentEquals("grid_chrome")) {
            try {
                String nodeURL = "http://192.168.100.8:4444/wd/hub";
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                capabilities.setBrowserName("chrome");
                capabilities.setPlatform(Platform.MAC);
                driver = new RemoteWebDriver(new URL(nodeURL), capabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
        else  if (browserName.contentEquals("mobile")) {
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


        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

    @AfterMethod
    public void finish(){
        driver.quit();
    }

}
