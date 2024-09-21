package testBase;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
    public WebDriver driver;
    public Properties p;

    @Parameters({"os", "browser"})
    @BeforeClass
    public void setup(String os, String br) throws IOException {
        FileReader file = new FileReader("./src/test/resources/config.properties");
        p = new Properties();
        p.load(file);
        
        if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            if (os.equalsIgnoreCase("windows")) {
                capabilities.setPlatform(Platform.WIN11);
            } else if (os.equalsIgnoreCase("linux")) {
                capabilities.setPlatform(Platform.LINUX);
            } else {
                System.out.println("No matching OS");
                return;
            }
            switch (br.toLowerCase()) {
                case "chrome": 
                    capabilities.setBrowserName("chrome");
                    break;
                case "edge": 
                    capabilities.setBrowserName("edge"); // Corrected
                    break;
                case "firefox": 
                    capabilities.setBrowserName("firefox");
                    break;
                default: 
                    System.out.println("No matching browser");
                    return;
            }
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
            // Initialize remote WebDriver if needed
            // driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        }

        if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
            switch (br.toLowerCase()) {
                case "chrome": 
                    driver = new ChromeDriver();
                    break;
                case "edge":  // Removed extra space
                    driver = new EdgeDriver();
                    break;
                default: 
                    System.out.println("No matching browser");
                    return;
            }
        }
        
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://tutorialsninja.com/demo/");
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public String randomString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomNumber() {
        return RandomStringUtils.randomNumeric(10);
    }

    public String randomAlphaNumeric() {
        return RandomStringUtils.randomAlphabetic(5) + "#" + RandomStringUtils.randomNumeric(10);
    }
}
