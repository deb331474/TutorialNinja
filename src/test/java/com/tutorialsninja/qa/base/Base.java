package com.tutorialsninja.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.tutorialsninja.qa.utils.Utilities;

public class Base {
	
    WebDriver driver;
    public Properties prop;
    public Properties dataProp;
		
    public Base() {
        prop = new Properties();
        File propFile = new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\tutorialsninja\\qa\\config\\config.properties");
        
        dataProp = new Properties();
        File dataPropFile = new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\tutorialsninja\\qa\\testdata\\testdata.properties");
        
        try {
            FileInputStream dataFis = new FileInputStream(dataPropFile);
            dataProp.load(dataFis);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        
        try {
            FileInputStream fis = new FileInputStream(propFile);
            prop.load(fis);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
	
    public WebDriver initializeBrowserAndOpenApplicationURL(String browserName) {
        if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized"); // Maximizes the browser window
            options.addArguments("--enable-automation"); // Allows automation (required for some Chrome policies)
            options.addArguments("--no-sandbox"); // Bypasses sandboxing for better compatibility on some systems
            options.addArguments("--disable-infobars"); // Hides the "Chrome is being controlled by automated test software" infobar
            options.addArguments("--incognito"); // Enables incognito mode (corrected from "incognito-mode")
            options.addArguments("--disable-dev-shm-usage"); // Overcomes limited resource problems in some Linux environments
            

            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("download.prompt_for_download", false); // Disables download prompt for automatic downloads
            prefs.put("download.default_directory", System.getProperty("user.dir") + "\\downloads"); // Sets default download directory
            options.setExperimentalOption("prefs", prefs);

            // Set the path to ChromeDriver if not in system PATH
            //System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
            driver = new ChromeDriver(options);

        } else if (browserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();

        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();

        } else if (browserName.equalsIgnoreCase("safari")) {
            driver = new SafariDriver();

        } else {
            throw new IllegalArgumentException("Browser name '" + browserName + "' is not supported");
        }

        driver.manage().window().maximize(); // Ensure window is maximized (redundant with --start-maximized, but kept for clarity)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Utilities.IMPLICIT_WAIT_TIME));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Utilities.PAGE_LOAD_TIME));
        driver.get(prop.getProperty("url"));

        return driver;
    }
}