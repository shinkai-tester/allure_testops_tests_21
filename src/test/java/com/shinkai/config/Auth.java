package com.shinkai.config;

import org.aeonbits.owner.ConfigFactory;

public class Auth {
    public static AuthConfig config = ConfigFactory.create(AuthConfig.class, System.getProperties());
}
