package com.shinkai.config;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class WebDriverProvider {

    public static void configure() {
        Configuration.baseUrl = WebDriver.config.getBaseUrl();
        Configuration.browserSize = WebDriver.config.getBrowserSize();
        String[] browserWithVersion = WebDriver.config.getBrowserAndVersion();
        Configuration.browser = browserWithVersion[0];
        Configuration.browserVersion = browserWithVersion[1];
        Configuration.pageLoadStrategy = "eager";

        if (WebDriver.config.getRemoteUrl() != null) {
            Configuration.remote = "https://" + Auth.config.usernameSelenoid() + ":"
                    + Auth.config.passwordSelenoid() + "@" + WebDriver.config.getRemoteUrl() + "/wd/hub";
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
    }
}
