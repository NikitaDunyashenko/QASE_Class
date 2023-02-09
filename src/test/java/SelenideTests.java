
import com.codeborne.selenide.Configuration;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTests {

    private final static String USER_NAME = "nik123@mailinator.com";
    private final static String PASSWORD = "Password@_1";
    private final String DROPDOWN_LABEL = "//label[text()='%s']/parent::div/div/button";
    private final static String DROPDOWN_OPTION = "//div[text()='%s']";
    private final static String STEP_INPUT = "//div[@title='%s']//parent::div/parent::div//following-sibling::div//p[@data-placeholder='%s']";
    private final static String ADD_STEP_BUTTON = "//*[text()=' Add step']/parent::button";
    private final static String TOAST_MESSAGE = ".OL6rtE";
    String stepAction = "Step Action";
    String data = "Data";
    String expectedResult = "Expected result";
    int[] arrayNumber = {2, 3, 4};
    String[] arrayDropdownLabel = {"Priority", "Type", "Severity"};
    String[] arrayDropdownOption = {"High", "Smoke", "Blocker"};

    @BeforeClass
    public void initialize() {
        Configuration.browser = "chrome";
        Configuration.baseUrl = "https://app.qase.io";
        Configuration.startMaximized = true;
        Configuration.timeout = 10000;

    }

    @Test
    public void LoginTestPositive() {
        open("/login");
        $("#inputEmail").shouldBe(enabled).setValue(USER_NAME);
        $("#inputPassword").shouldBe(enabled).setValue(PASSWORD);
        $("#btnLogin").shouldBe(enabled).click();
        Assert.assertTrue($(".text-center.project-icon").isDisplayed());
    }

    @Test
    public void createNewProjectTest() {
        open("/login");
        $("#inputEmail").shouldBe(enabled).setValue(USER_NAME);
        $("#inputPassword").shouldBe(enabled).setValue(PASSWORD);
        $("#btnLogin").shouldBe(enabled).click();

        $("#createButton").shouldBe(enabled).click();

        $("#project-name").setValue("QASE");
        $("#project-code").clear();
        $("#project-code").setValue("QA");
        $("#description-area").setValue("Project is designed to set up test-cases and defects " +
                "for SauceDemo website");
        $("[type=submit]").click();

        String projectCode = $(".VqrSGU").shouldBe(visible).text();
        String projectName = $(".sqabXr").shouldBe(visible).text();

        Assert.assertEquals(projectCode,  "QA repository");
        Assert.assertEquals(projectName, "QASE");
    }

    @Test
    public void createTestCase() {
        open("/login");
        $("#inputEmail").shouldBe(enabled).setValue(USER_NAME);
        $("#inputPassword").shouldBe(enabled).setValue(PASSWORD);
        $("#btnLogin").shouldBe(enabled).click();

        $(By.xpath("//a[text()='QASE']")).shouldBe(enabled).click();
        $("#create-case-button").shouldBe(enabled).click();
        String pageName = $(".TNRMCJ").shouldBe(visible).text();

        Assert.assertEquals(pageName, "Create test case");

        $("#title").shouldBe(enabled).setValue("Logging in to Qase website");
        $(By.xpath(String.format(DROPDOWN_LABEL, arrayDropdownLabel[0]))).click();
        $(By.xpath(String.format(DROPDOWN_OPTION, arrayDropdownOption[0]))).click();
        $(By.xpath(String.format(DROPDOWN_LABEL, arrayDropdownLabel[1]))).click();
        $(By.xpath(String.format(DROPDOWN_OPTION, arrayDropdownOption[1]))).click();
        $(By.xpath(String.format(DROPDOWN_LABEL, arrayDropdownLabel[2]))).click();
        $(By.xpath(String.format(DROPDOWN_OPTION, arrayDropdownOption[2]))).click();
        $(By.xpath(ADD_STEP_BUTTON)).scrollIntoView(true).click();
        $(By.xpath("//*[@class='iFHUaT']//p[@data-placeholder='Step Action']"))
                .setValue("go to the website qase.io");
        $(By.xpath(ADD_STEP_BUTTON)).click();
        $(By.xpath(String.format(STEP_INPUT, arrayNumber[0], stepAction)))
                .setValue("entering the user name");
        $(By.xpath(String.format(STEP_INPUT, arrayNumber[0], data)))
                .setValue(USER_NAME);
        $(By.xpath(ADD_STEP_BUTTON)).click();
        $(By.xpath(String.format(STEP_INPUT, arrayNumber[1], stepAction)))
                .setValue("entering the password");
        $(By.xpath(String.format(STEP_INPUT, arrayNumber[1], data)))
                .setValue(PASSWORD);
        $(By.xpath(ADD_STEP_BUTTON)).click();
        $(By.xpath(String.format(STEP_INPUT, arrayNumber[2], stepAction)))
                .setValue("clicking login button");
        $(By.xpath(String.format(STEP_INPUT, arrayNumber[2], expectedResult)))
                .setValue("The user should be logged in and transferred to the home page");

        $("#save-case").click();

        Assert.assertTrue($(TOAST_MESSAGE).should(appear).isDisplayed());
    }

    @Test
    public void createNewDefect() {
        open("/login");
        $("#inputEmail").shouldBe(enabled).setValue(USER_NAME);
        $("#inputPassword").shouldBe(enabled).setValue(PASSWORD);
        $("#btnLogin").shouldBe(enabled).click();

        $(By.xpath("//a[text()='QASE']")).shouldBe(enabled).click();
        $(By.xpath("//span[text()='Defects']")).shouldBe(enabled).click();
        $(".btn-primary").shouldBe(enabled).click();

        $("#title").shouldBe(enabled).setValue("Creating a test case skipping required fields");
        $("p[class=Q9IhIQ]").shouldBe(enabled).setValue("It's possible to create a new test case skipping the required field Severity");
        $("[type=submit]").shouldBe(enabled).click();

        Assert.assertTrue($(TOAST_MESSAGE).should(appear).isDisplayed());

    }

}
