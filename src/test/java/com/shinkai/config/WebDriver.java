package com.shinkai.config;

import org.aeonbits.owner.ConfigFactory;

public class WebDriver {
    public static WebDriverConfig config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());
}
