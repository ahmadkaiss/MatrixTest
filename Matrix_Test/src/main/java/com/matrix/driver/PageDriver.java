package com.matrix.driver;

import com.matrix.utils.ActionWrapper;
import com.matrix.utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PageDriver {
    private BrowserType browserType;
    private WebDriver driver;
    private JsExecutor js;
    private long implicitWait = 15;

    public PageDriver(BrowserType browserType) {
        this.browserType = browserType;
        boolean isPass = Utils.tryIter(new ActionWrapper("init web driver", null, 10000) {
            @Override
            public boolean invoke() throws Exception {
                try {
                    switch (browserType) {
                        case CH:
                            DesiredCapabilities cap = initChromeProp();
                            driver = new ChromeDriver(cap);
                            break;
                        default:
                            System.out.println("Unknown browser type: " + browserType);
                            break;
                    }

                    driver.manage().window().maximize();
                    driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
                    js = new JsExecutor(driver);
                    return true;
                } catch (Throwable e) {
                    System.out.println("init web driver Error: " + e.getMessage());
                }
                return false;
            }


            private DesiredCapabilities initChromeProp() {
                HashMap<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("profile.default_content_settings.popups", 0);//pop-up windows should be blocked or not allowed to appear
                chromePrefs.put("credentials_enable_service", false); //disable save password popup
                chromePrefs.put("profile.password_manager_enabled", false);//disables the browser's built-in password manager
                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("prefs", chromePrefs);//preferences

                DesiredCapabilities cap = DesiredCapabilities.chrome();
                cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                cap.setCapability(ChromeOptions.CAPABILITY, options);
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");////C:\AutomationProjects\Matrix_Test\drivers\chromedriver.exe
                return cap;
            }

        }, 5);

        System.out.println("The init web driver " + isPass);
    }



    public void waitPageLoad(Integer timeOutMs) {
        boolean isPass = Utils.tryUntil(new ActionWrapper("Wait for page loading", timeOutMs, 2000) {
            @Override
            public boolean invoke() {
                String res = js().executeScript("document.readyState");
                System.out.println("Page loading result = " + res);
                return res.equals("complete");
            }
        });
        if (!isPass)
            System.out.println("Page still loading..");
    }

    public void navigate(String to) {
        try {
            driver.get(to);
        } catch (Exception e) {
            Utils.sleep(1000);
            driver.navigate().to(to);
        }
        waitPageLoad(40000);
    }


    public static WebElement waitUntilClickable(WebDriver driver, By by) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
        return element;
    }

    public static WebElement waitUntilClickable(WebDriver driver, WebElement elm) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebElement element = wait.until(elementToBeClickableByWebElement(elm));
        return element;
    }

    public static ExpectedCondition<WebElement> elementToBeClickableByWebElement(final WebElement elm) {
        return new ExpectedCondition<WebElement>() {
            public ExpectedCondition<WebElement> visibilityOfElementLocated = ExpectedConditions.visibilityOf(elm);

            @Override
            public WebElement apply(WebDriver driver) {
                WebElement element = visibilityOfElementLocated.apply(driver);
                try {
                    if (element != null && element.isEnabled()) {
                        return element;
                    } else {
                        return null;
                    }
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return "element to be clickable: " + elm.toString();
            }
        };
    }

    public static WebElement waitForVisibilityOfElement(WebDriver driver, WebElement elm, int waitTime) {
        Wait<WebDriver> wait = new WebDriverWait(driver, waitTime);
        WebElement divElement = wait.until(visibilityOfWebElement(elm));
        return divElement;
    }

    public static ExpectedCondition<WebElement> visibilityOfWebElement(final WebElement elm) {
        return new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                if (elm.isDisplayed()) {
                    return elm;
                }
                return null;
            }
        };
    }

    public static WebElement waitForVisibility(WebDriver driver, By by, int waitTime) {
        Wait<WebDriver> wait = new WebDriverWait(driver, waitTime);
        WebElement divElement = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return divElement;
    }

    public WebDriver get() {
        return driver;
    }

    /* close browser include kill process  */
    public void quit() {
        driver.quit();
    }

    public JsExecutor js() {
        return this.js;
    }
}
