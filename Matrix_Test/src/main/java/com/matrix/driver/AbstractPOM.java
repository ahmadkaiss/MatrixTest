package com.matrix.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.concurrent.TimeUnit;

public abstract class AbstractPOM {
    protected WebDriver driver;
    protected Wait<WebDriver> wait;
    protected JsExecutor js;

    public AbstractPOM(WebDriver driver) {
        this.driver = driver;
        wait = initializePOM(driver, this);
        js = new JsExecutor(driver);
    }

    public static Wait<WebDriver> initializePOM(WebDriver driver, Object pom) {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 4), pom);
        return new FluentWait<>(driver).withTimeout(50, TimeUnit.SECONDS).pollingEvery(900, TimeUnit.MILLISECONDS);
    }

    public abstract boolean isDisplayed();
}
