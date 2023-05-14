package com.shinkai.pages;

import com.codeborne.selenide.SelenideElement;
import com.shinkai.helpers.Attach;
import io.qameta.allure.Step;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class TestCasePage {
    private final SelenideElement testCaseOverview = $(".TestCaseOverview");
    private final SelenideElement menuTrigger = $(".Menu__trigger");
    private final SelenideElement menuItem = $(".tippy-content");
    private final SelenideElement newTestCaseNameInput = $("input[placeholder='Enter name']");
    private final SelenideElement enterNameInput = $("input[placeholder='Enter name']");
    private final SelenideElement submitEditTestName = $(".Modal__content").find(byName("submit"));
    private final SelenideElement testCaseTitle = $(".TestCaseLayout__name");
    private final SelenideElement descriptionArea = $(byText("Description")).ancestor(".PaneSection");
    private final SelenideElement scenario = $(byText("Scenario")).ancestor(".PaneSection");
    private final SelenideElement editStep = $(".TestCaseScenarioStepEdit__textarea");
    private final SelenideElement stepTriggerEdit = $(".TestCaseStepRow__trigger_edit");
    private final SelenideElement menuItemFile = $("[data-testid='menu_item__file']");
    private final SelenideElement uploadArea = $(".DropZone__drop-area [type='file']");

    @Step("Open test case by id [{testCaseId}] on project by id [{projectId}]")
    public void openPage(Integer projectId, Integer testCaseId) {
        open("/project/" + projectId + "/test-cases/" + testCaseId);
        testCaseOverview.should(appear);
    }

    @Step("Click 'Test case actions'")
    public void clickTestCaseActions() {
        menuTrigger.shouldNotHave(attribute("disabled")).click();
    }

    @Step("Edit test case name to [{newName}]")
    public void renameTestCase(String newName) {
        menuItem.find(byText("Rename test case")).click();
        clearOldTestCaseName();
        enterNameInput.sendKeys(newName);
        submitEditTestName.click();
    }

    private void clearOldTestCaseName() {
        executeJavaScript("arguments[0].value = '';", newTestCaseNameInput);
    }

    @Step("Verify test case name is [{name}] and test case id is [{testId}]")
    public void verifyTestCaseNameAndId(String name, Integer testId) {
        testCaseTitle.shouldHave(exactText("#" + testId + " " + name));
    }

    @Step("Edit test case description to [{newDescription}]")
    public void editTestCaseDescription(String newDescription) {
        descriptionArea.find("button").click();
        $(byName("description")).setValue(newDescription);
        descriptionArea.find(byName("submit")).click();
    }

    @Step("Verify test case description is [{description}]")
    public void verifyTestCaseDescription(String description) {
        descriptionArea.shouldHave(text(description));
        Attach.screenshotAs("Screenshot: test case is changed", descriptionArea.find(byText(description)));
    }

    @Step("Add step to test case: [{stepName}]")
    public void addStepToTestCase(String stepName) {
        scenario.find("button").click();
        editStep.sendKeys(stepName);
    }

    @Step("Add next step to test case: [{stepName}]")
    public void addNextStepToTestCase(String stepName) {
        editStep.pressEnter();
        editStep.sendKeys(stepName);
        Attach.screenshotAs("Screenshot of test case steps", scenario.find(byName("submit")));
    }

    public void submitAddingScenario() {
        scenario.find(byName("submit")).click();
    }

    @Step("Verify test case scenario has step: [{step}]")
    public void verifyScenarioHasStep(String step) {
        scenario.shouldHave(text(step));
    }

    @Step("Click 'Step edit actions'")
    public void openStepEditMenu() {
        stepTriggerEdit.shouldBe(interactable);
        stepTriggerEdit.click();
    }

    @Step("Attach file to step")
    public void attachFileToStep(File image) {
        menuItem.shouldHave(text("Attach files"));
        menuItemFile.click();
        uploadArea.uploadFile(image);
    }

    @Step("Verify filename and its extension before submit attach: [{fileName}], [{contentType}]")
    public void verifyAddedFileBeforeSubmit(String fileName, String fileType) {
        $(".DropZone__list-top__length").shouldHave(exactText("Total: 1 files"));
        $(byName("fileName")).shouldHave(value(fileName));
        $(byName("contentType")).shouldHave(value(fileType));
    }

    @Step("Submit adding file to step")
    public void submitAddingOfFile() {
        $(".DropZone__edit-view").find(byName("submit")).click();
        scenario.find(byName("submit")).click();
    }

    @Step("Verify filename and its extension after submit attach: [{fileName}], [{contentType}]")
    public void verifyAddedFileAfterSubmit(String fileName, String fileType) {
        $(".AttachmentRow__name").shouldHave(text(fileName));
        $(".AttachmentRow__type").shouldHave(exactText(fileType));
        Attach.screenshotAs("Screenshot of the attached file", $(".FileContent__media").shouldHave(attributeMatching("src", ".*/api/rs/testcase/attachment/.*")));
    }
}
