package com.shinkai.pages;

import com.codeborne.selenide.ElementsCollection;
import com.shinkai.helpers.Attach;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class TestCasesListPage {
    private final ElementsCollection testCasesList = $$(".LoadableTree__view > li");

    @Step("Verify test cases table contains test case with name [{testCaseName}]")
    public void verifyTestCasesTableHasTestCase(String testCaseName) {
        testCasesList.find(text(testCaseName)).shouldBe(visible);
    }
}
