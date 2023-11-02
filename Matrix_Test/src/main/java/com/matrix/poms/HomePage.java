package com.matrix.poms;

import com.matrix.driver.AbstractPOM;
import com.matrix.driver.PageDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage extends AbstractPOM {
    final private By tableTransactions = By.className("table-responsive");
    final private By allStatuses = By.cssSelector("div[class=\"table-responsive\"] > table > tbody > tr > td:nth-child(1) > span:nth-child(2)");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public int numberStatusesCompleted() {
        PageDriver.waitForVisibility(driver, allStatuses, 20);
        int successCounter = 0;
        List<WebElement> tranStatuses;
        tranStatuses = driver.findElements(allStatuses);
        for (WebElement status : tranStatuses) {
            if (status.getText().contains("Complete")) {
                successCounter++;
            }
        }
        return successCounter;
    }


    @Override
    public boolean isDisplayed() {
        PageDriver.waitForVisibility(driver, tableTransactions, 15);
        WebElement tableView = driver.findElement(tableTransactions);
        js.scrollTo(tableView);
        return driver.findElement(tableTransactions).isDisplayed();
    }
}
