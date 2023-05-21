package com.shinkai.helpers;

import com.shinkai.api.testcase.CreateTestCaseRequestDto;
import com.shinkai.api.testcase.TestCaseApi;
import com.shinkai.api.testcase.TestCaseDetailsResponseDto;
import com.shinkai.generators.TestCaseDataGenerator;
import io.qameta.allure.Step;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class PrepareTestDataExtension implements BeforeEachCallback, AfterEachCallback {
    private final TestCaseDataGenerator testDataGenerate = new TestCaseDataGenerator();
    static String testCaseName;
    static String testCaseDescription;
    static Integer testCaseId;

    private final TestCaseApi testCaseApi = new TestCaseApi();
    private final static int PROJECT_ID = 2242;

    @Override
    @Step("Create test case for updating it")
    public void beforeEach(ExtensionContext context) {
        testCaseDescription = testDataGenerate.getTestDescription();
        testCaseName = testDataGenerate.getTestName();

        CreateTestCaseRequestDto testCase = CreateTestCaseRequestDto.builder()
                .name(testCaseName)
                .description(testCaseDescription)
                .projectId(PROJECT_ID)
                .build();

        TestCaseDetailsResponseDto testCaseResponseData = testCaseApi.createTestCase(testCase);

        testCaseId = testCaseResponseData.getId();
    }

    @Override
    @Step("Cleanup test case")
    public void afterEach(ExtensionContext context) {
        testCaseApi.deleteTestCase(testCaseId);
    }

    public static Integer getTestCaseId() {
        return testCaseId;
    }

    public static String getTestCaseName() {
        return testCaseName;
    }
}
