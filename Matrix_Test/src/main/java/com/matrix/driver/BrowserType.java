package com.matrix.driver;

import java.io.IOException;

public enum BrowserType {
    CH("Chrome") {
        @Override
        public void killBrowser() throws IOException {
            System.out.println("kill chrome driver");
            Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
        }
    };

//    FF("Firefox") {..

    private String browserName;

    BrowserType(String name) {
        this.browserName = name;
    }

    public abstract void killBrowser() throws IOException;

    public String toString() {
        return this.browserName;
    }
}
