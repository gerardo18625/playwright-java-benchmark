package com.crunchtime.ctAutomation;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.Date;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TestDemoQA {

    long starttime;
    long endtime;

    @BeforeSuite
    public void announceStartTime(){
        Date date = new Date();
        starttime = date.getTime();
    }

    @AfterSuite
    public void announceEndTime(){
        endtime = new Date().getTime();
        System.out.print("*** TEST TIME: ");
        System.out.print((endtime - starttime)/1000);
    }

    @Test(invocationCount = 1, threadPoolSize = 1)
    public void demoQaTest(){
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(true));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            //Navigate to the demo QA site
            page.navigate("https://demoqa.com/automation-practice-form");

            //Fill the text input fields
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name")).fill("Test");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name")).fill("User");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("name@example.com")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("name@example.com")).fill("test@test.com");

            //Select the gender
            page.getByText("Male", new Page.GetByTextOptions().setExact(true)).click();

            //Fill the mobile number
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Mobile Number")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Mobile Number")).fill("3322445566");
//            page.locator("#dateOfBirthInput").click();
//            page.locator("div").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^JanuaryFebruaryMarchAprilMayJuneJulyAugustSeptemberOctoberNovemberDecember$"))).getByRole(AriaRole.COMBOBOX).selectOption("2010");
//            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Choose Friday, January 1st,")).selectOption("0");
//            page.getByRole(AriaRole.COMBOBOX).nth(1).click();
//            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Choose Friday, January 1st,")).click();

            //Fill the auto-complete input
            page.locator(".subjects-auto-complete__value-container").click();
            page.locator("#subjectsInput").fill("Ma");
            page.getByText("Maths", new Page.GetByTextOptions().setExact(true)).click();

            //Check all the checkboxes
            page.getByText("Sports").click();
            page.getByText("Reading").click();
            page.getByText("Music").click();

            // Set the file to upload
            //page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select picture")).setInputFiles(Paths.get("/Users/gerardohernandez/workspace/POC_PLAYWRIGHT/PLAYWRIGHT_FOR_CI_JAVA/src/test/java/com/crunchtime/ctAutomation/reportPortal.jpg"));

            // Fill the address
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Current Address")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Current Address")).fill("Test Address");

            // Select the state
            page.locator("div").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Select State$"))).nth(3).click();
            page.getByText("NCR", new Page.GetByTextOptions().setExact(true)).click();
            page.getByText("Select City").click();
            page.getByText("Delhi", new Page.GetByTextOptions().setExact(true)).click();

            //Submit the form
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();
            assertThat(page.locator("#example-modal-sizes-title-lg")).containsText("Thanks for submitting the form");
            page.getByRole(AriaRole.DIALOG, new Page.GetByRoleOptions().setName("Thanks for submitting the form")).press("Escape");

            //CLick on Alters frames and windows
            page.getByText("Alerts, Frame & Windows").click();
            page.getByText("Browser Windows").click();
            Page page1 = page.waitForPopup(() -> {
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("New Tab")).click();
            });
            page1.navigate("https://www.google.com/?zx=1759365742803&no_sw_cr=1");
            //assertThat(page1.locator("#SIvCob")).containsText("Google disponible en: English");
            page1.close();
            Page page2 = page.waitForPopup(() -> {
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("New Window").setExact(true)).click();
            });
            assertThat(page2.locator("#sampleHeading")).containsText("This is a sample page");
            page2.close();
            Page page3 = page.waitForPopup(() -> {
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("New Window Message")).click();
            });
            page.navigate("https://demoqa.com/browser-windows#google_vignette");
            assertThat(page3.locator("body")).containsText("Knowledge increases by sharing but not by saving. Please share this website with your friends and in your organization.");
            page3.close();

            //Click in modal and dialogs
            page.getByText("Modal Dialogs").click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Small modal")).click();
            assertThat(page.locator("#example-modal-sizes-title-sm")).containsText("Small Modal");
            page.locator("#closeSmallModal").click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Large modal")).click();
            assertThat(page.locator("#example-modal-sizes-title-lg")).containsText("Large Modal");
            page.locator("#closeLargeModal").click();
            page.getByText("Widgets").click();
            page.getByText("Slider").click();
            page.getByRole(AriaRole.SLIDER).fill("76");

        }
    }
}
