package com.shinkai.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.shinkai.generators.TestCaseDataGenerator;
import com.shinkai.helpers.Attach;
import com.shinkai.pages.TestCasePage;
import com.shinkai.pages.TestCasesListPage;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import com.shinkai.config.WebDriverProvider;

public class TestBase {

    TestCasePage testCasePage = new TestCasePage();
    TestCasesListPage testCasesListPage = new TestCasesListPage();
    TestCaseDataGenerator testDataGenerate = new TestCaseDataGenerator();

    @BeforeAll
    static void setUp() {
        WebDriverProvider.configure();
        RestAssured.baseURI = "https://allure.autotests.cloud";
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.addVideo();
        Attach.pageSource();
        Attach.browserConsoleLogs();
    }
}
