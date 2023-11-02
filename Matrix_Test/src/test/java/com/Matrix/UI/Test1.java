package com.Matrix.UI;

import com.matrix.driver.BrowserType;
import com.matrix.driver.PageDriver;
import com.matrix.poms.HomePage;
import com.matrix.poms.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.ITestResult;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Test1 {
    private static WebDriver driver;
    static Properties properties;
    static PageDriver pageDriver;

    @BeforeClass
    public void beforeClassInit() {
        final String pathFile = "C:\\AutomationProjects\\Matrix_Test\\TestProps.properties";
        // Load the properties file
        properties = new Properties();
        try (FileInputStream file = new FileInputStream(pathFile)) {
            properties.load(file);
        } catch (IOException e) {
            System.out.println("An error occurred while loading the properties file:");
            e.printStackTrace();
        }
        pageDriver = new PageDriver(BrowserType.CH);
        driver = pageDriver.get();
        String url = properties.getProperty("Url");
        if (url != null) {
            pageDriver.navigate(url);
        } else {
            System.out.println("The 'Url' property is not specified in the properties file.");
        }
        pageDriver.navigate(url);
    }


    @Test
    public void test() {
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isDisplayed(), "The LogIn page not open as expected");
        loginPage.setUserName(properties.getProperty("username"));
        loginPage.setPassword(properties.getProperty("password"));
        loginPage.clickLoginButton();
    }

    @Test(dependsOnMethods = "test")
    public void test2() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isDisplayed(), "The Home Page not open as expected");
        int expectedSuccessTransactions = 2;
        int currentSuccessTransactions = homePage.numberStatusesCompleted();
        Assert.assertEquals(currentSuccessTransactions, expectedSuccessTransactions, "There is miss "
                + (expectedSuccessTransactions - currentSuccessTransactions) + "transactions that not appear in Recent transactions");
    }

    @AfterMethod
    public void handleTestResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            // Call tearDown only if the test status is FAILURE
            tearDown();
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
