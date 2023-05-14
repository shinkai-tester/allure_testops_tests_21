package com.shinkai.tests;

import com.shinkai.api.testcase.CreateTestCaseRequestDto;
import com.shinkai.api.testcase.TestCaseApi;
import com.shinkai.api.testcase.TestCaseDetailsResponseDto;
import com.shinkai.helpers.Attach;
import com.shinkai.helpers.WithLogin;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Allure TestOps test cases update")
public class EditTestCasesTests extends TestBase {
    String testCaseName;
    String testCaseDescription;
    Integer testCaseId;

    private final TestCaseApi testCaseApi = new TestCaseApi();
    private final static int PROJECT_ID = 2242;

    @BeforeEach
    @Step("Create test case for updating it")
    public void createTestCase() {
        this.testCaseName = testDataGenerate.getTestName();
        this.testCaseDescription = testDataGenerate.getTestDescription();

        CreateTestCaseRequestDto testCase = CreateTestCaseRequestDto.builder()
                .name(testCaseName)
                .description(testCaseDescription)
                .projectId(PROJECT_ID)
                .build();

        TestCaseDetailsResponseDto testCaseResponseData = testCaseApi.createTestCase(testCase);

        assertThat(testCaseResponseData.getId()).isNotNull();

        this.testCaseId = testCaseResponseData.getId();
    }

    @AfterEach
    @Step("Cleanup test case")
    public void deleteTestCase() {
        testCaseApi.deleteTestCase(testCaseId);
    }

    @Test
    @WithLogin
    @DisplayName("Update test case name and description")
    void editTestNameAndDescription() {
        String newName = testCaseName + " " + testDataGenerate.getRandomDigitWithLength(5);
        String newDescription = testDataGenerate.getTestDescription();

        testCasePage.openPage(PROJECT_ID, testCaseId);
        testCasesListPage.verifyTestCasesTableHasTestCase(testCaseName);
        Attach.screenshotAs("Screenshot: test case before change");
        testCasePage.clickTestCaseActions();
        testCasePage.renameTestCase(newName);
        testCasePage.editTestCaseDescription(newDescription);
        Attach.screenshotAs("Screenshot: test case is changed");
        testCasePage.verifyTestCaseNameAndId(newName, testCaseId);
        testCasePage.verifyTestCaseDescription(newDescription);
    }

    @Test
    @WithLogin
    @DisplayName("Add steps to test case")
    void addStepsToTestCase() {
        String stepName1 = testDataGenerate.getTestStepName();
        String rndNum = testDataGenerate.getRandomDigitWithLength(5);
        String stepName2 = rndNum + " " + stepName1;

        testCasePage.openPage(PROJECT_ID, testCaseId);
        testCasePage.addStepToTestCase(stepName1);
        testCasePage.addNextStepToTestCase(stepName2);
        testCasePage.submitAddingScenario();
        Attach.screenshotAs("Screenshot: steps are added to test case");
        testCasePage.verifyScenarioHasStep(stepName1);
        testCasePage.verifyScenarioHasStep(stepName2);
    }

    @CsvSource(value = {
            "Umaru.jpg, image/jpeg",
            "qa_bug.png, image/png",
    })
    @ParameterizedTest(name = "Add {1} to step in test case")
    @WithLogin
    void attachImageToStep(String fileName, String fileType) {
        String stepName = testDataGenerate.getTestStepName();

        testCasePage.openPage(PROJECT_ID, testCaseId);
        testCasePage.addStepToTestCase(stepName);
        testCasePage.openStepEditMenu();
        testCasePage.attachFileToStep(testDataGenerate.getFile(fileName));
        testCasePage.verifyAddedFileBeforeSubmit(fileName, fileType);
        testCasePage.submitAddingOfFile();
        Attach.screenshotAs("Screenshot of the attached file");
        testCasePage.verifyAddedFileAfterSubmit(fileName, fileType);
    }
}
