package com.shinkai.tests;

import com.shinkai.helpers.PrepareTestDataExtension;
import com.shinkai.helpers.WithLogin;
import com.shinkai.helpers.WithTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Allure TestOps test cases update")
public class EditTestCasesTests extends TestBase {
    private final static int PROJECT_ID = 2242;

    @Test
    @WithTestData
    @WithLogin
    @DisplayName("Update test case name and description")
    void editTestNameAndDescription() {
        Integer testCaseId = PrepareTestDataExtension.getTestCaseId();
        String testCaseName = PrepareTestDataExtension.getTestCaseName();

        String newName = testCaseName + " " + testDataGenerate.getRandomDigitWithLength(5);
        String newDescription = testDataGenerate.getTestDescription();

        testCasePage.openPage(PROJECT_ID, testCaseId);
        testCasesListPage.verifyTestCasesTableHasTestCase(testCaseName);
        testCasePage.clickTestCaseActions().
                renameTestCase(newName)
                .editTestCaseDescription(newDescription)
                .verifyTestCaseNameAndId(newName, testCaseId)
                .verifyTestCaseDescription(newDescription);
    }

    @Test
    @WithTestData
    @WithLogin
    @DisplayName("Add steps to test case")
    void addStepsToTestCase() {
        Integer testCaseId = PrepareTestDataExtension.getTestCaseId();

        String stepName1 = testDataGenerate.getTestStepName();
        String rndNum = testDataGenerate.getRandomDigitWithLength(5);
        String stepName2 = rndNum + " " + stepName1;

        testCasePage.openPage(PROJECT_ID, testCaseId)
                .addStepToTestCase(stepName1)
                .addNextStepToTestCase(stepName2)
                .submitAddingScenario()
                .verifyScenarioHasStep(stepName1)
                .verifyScenarioHasStep(stepName2);
    }

    @CsvSource(value = {
            "Umaru.jpg, image/jpeg",
            "qa_bug.png, image/png",
    })
    @ParameterizedTest(name = "Add {1} to step in test case")
    @WithTestData
    @WithLogin
    void attachImageToStep(String fileName, String fileType) {
        Integer testCaseId = PrepareTestDataExtension.getTestCaseId();
        String stepName = testDataGenerate.getTestStepName();

        testCasePage.openPage(PROJECT_ID, testCaseId)
                .addStepToTestCase(stepName)
                .openStepEditMenu()
                .attachFileToStep(testDataGenerate.getFile(fileName))
                .verifyAddedFileBeforeSubmit(fileName, fileType)
                .submitAddingOfFile()
                .verifyAddedFileAfterSubmit(fileName, fileType);
    }
}
