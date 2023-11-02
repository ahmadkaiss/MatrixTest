package com.matrix.poms;

import com.matrix.driver.AbstractPOM;
import com.matrix.driver.PageDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPOM {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "username")
    private WebElement userNameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "log-in")
    private WebElement loginButton;

    private final By headerLoginPage = By.className("auth-header");

    public LoginPage setUserName(String userName) {
        PageDriver.waitForVisibilityOfElement(driver, userNameInput, 10);
        userNameInput.sendKeys(userName);
        return this;
    }

    public LoginPage setPassword(String password) {
        PageDriver.waitForVisibilityOfElement(driver, passwordInput, 10);
        passwordInput.sendKeys(password);
        return this;
    }

    public HomePage clickLoginButton() {
        PageDriver.waitUntilClickable(driver, loginButton);
        loginButton.click();
        return new HomePage(driver);
    }

    @Override
    public boolean isDisplayed() {
        PageDriver.waitForVisibility(driver, headerLoginPage, 15);
        WebElement header = driver.findElement(headerLoginPage);
        return header.isDisplayed() && header.getText().contains("Login Form");
    }
}
