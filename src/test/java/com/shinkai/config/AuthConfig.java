package com.shinkai.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:auth.properties"
})
public interface AuthConfig extends Config {
    @Key("usernameSelenoid")
    String usernameSelenoid();

    @Key("passwordSelenoid")
    String passwordSelenoid();

    @Key("apiToken")
    String apiToken();

    @Key("usernameAllure")
    String usernameAllure();

    @Key("passwordAllure")
    String passwordAllure();
}
