package com.matrix.driver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JsExecutor {
    private WebDriver driver;

    public JsExecutor(WebDriver driver) {
        this.driver = driver;
    }

    public JavascriptExecutor getJsExecutor() {
        return (JavascriptExecutor) this.driver;
    }

    public  String executeScript(String command) {
        Object execute = null;
        try {
            System.out.println("Execute script:" + command);
            execute = getJsExecutor().executeScript("return " + command);
        } catch (Exception e) {
            System.out.println("Failed to execute script " + e.getMessage());
        }
        if (execute == null) {
            return "null";
        }
        return execute.toString();
    }

    public String executeScript(String command, WebElement element) {
        Object execute = null;
        try {
            System.out.println("Execute script:" + command);
            execute = getJsExecutor().executeScript(command, element);
        } catch (Exception e) {
            System.out.println("Failed to execute script " + e.getMessage());
        }

        if (execute == null)
            return "null";

        return execute.toString();
    }

    public void scrollTo(WebElement element) {
        executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
