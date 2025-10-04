package com.crunchtime.ctAutomation;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.MouseButton;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TestHeroku {

    public void executeTest(){
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(true));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.setDefaultTimeout(60000);

            System.out.println("Running the test");

            // Navigate to the herokuapp
            page.navigate("https://the-internet.herokuapp.com/");

            // Add and remove elements
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add/Remove Elements")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Element")).click();
            assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Delete"))).isVisible();
            page.navigate("https://the-internet.herokuapp.com/");

            // Dynamic controls
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Dynamic Controls")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove")).click();
            assertThat(page.locator("#message")).containsText("It's gone!");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
            page.locator("#checkbox").check();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Enable")).click();
            page.getByRole(AriaRole.TEXTBOX).click();
            page.getByRole(AriaRole.TEXTBOX).fill("Playwright");
            page.getByRole(AriaRole.TEXTBOX).click();
            page.getByRole(AriaRole.TEXTBOX).press("Enter");
            page.navigate("https://the-internet.herokuapp.com/");

            // Dynamic loading
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Dynamic Loading")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Example 1: Element on page")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Start")).click();
            assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Hello World!"))).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));
            page.navigate("https://the-internet.herokuapp.com/dynamic_loading");
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Example 2: Element rendered")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Start")).click();
            assertThat(page.locator("#finish")).containsText("Hello World!", new LocatorAssertions.ContainsTextOptions().setTimeout(10000));
            page.navigate("https://the-internet.herokuapp.com/");

            // Entry Add
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Entry Ad")).click();
            assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("This is a modal window"))).isVisible();
            page.getByText("Close", new Page.GetByTextOptions().setExact(true)).click();
            page.navigate("https://the-internet.herokuapp.com/");

            // jQuery menu
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JQuery UI Menus")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Enabled")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Downloads")).click();
            Download download = page.waitForDownload(() -> {
                page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("CSV")).click();
            });
            page.navigate("https://the-internet.herokuapp.com/");

            // Multiple windows
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Multiple Windows")).click();
            Page page1 = page.waitForPopup(() -> {
                page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Click Here")).click();
            });
            assertThat(page1.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("New Window"))).isVisible();
            page1.close();
            page.navigate("https://the-internet.herokuapp.com/");

            // Context menu
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Context Menu")).click();
            page.onceDialog(Dialog::dismiss);
            page.locator("#hot-spot").click(new Locator.ClickOptions()
                    .setButton(MouseButton.RIGHT));

            page.navigate("https://www.qaplayground.com/practice/select");
            page.getByRole(AriaRole.COMBOBOX).filter(new Locator.FilterOptions().setHasText("Select Fruit")).click();
            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Banana")).click();
            page.getByRole(AriaRole.LISTBOX).selectOption("aquaman");
            page.getByRole(AriaRole.COMBOBOX).filter(new Locator.FilterOptions().setHasText("Argentina")).click();
            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("UK")).click();

            page.navigate("https://www.qaplayground.com/practice/calendar");
            page.locator("div").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Enter todays date:$"))).getByRole(AriaRole.TEXTBOX).fill("2010-01-01");
            page.locator("div").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Enter your Birthday:$"))).getByRole(AriaRole.TEXTBOX).fill("2026-01-01");
        }

    }

    @BeforeSuite
    public void setup(){
        System.out.println("Running with suite:" + System.getProperty("threadCount") + " threads");
    }

    @Test(groups = {"shard-1"})
    public void herokuTest1(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest2(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest3(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest4(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest5(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest6(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest7(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest8(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest9(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest10(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest11(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest12(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest13(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest14(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest15(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest16(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest17(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest18(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest19(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest20(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest21(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest22(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest23(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest24(){
        executeTest();
    }

    @Test(groups = {"shard-1"})
    public void herokuTest25(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest26(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest27(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest28(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest29(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest30(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest31(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest32(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest33(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest34(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest35(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest36(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest37(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest38(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest39(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest40(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest41(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest42(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest43(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest44(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest45(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest46(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest47(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest48(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest49(){
        executeTest();
    }

    @Test(groups = {"shard-2"})
    public void herokuTest50(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest51(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest52(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest53(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest54(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest55(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest56(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest57(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest58(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest59(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest60(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest61(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest62(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest63(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest64(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest65(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest66(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest67(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest68(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest69(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest70(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest71(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest72(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest73(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest74(){
        executeTest();
    }

    @Test(groups = {"shard-3"})
    public void herokuTest75(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest76(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest77(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest78(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest79(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest80(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest81(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest82(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest83(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest84(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest85(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest86(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest87(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest88(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest89(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest90(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest91(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest92(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest93(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest94(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest95(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest96(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest97(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest98(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest99(){
        executeTest();
    }

    @Test(groups = {"shard-4"})
    public void herokuTest100(){
        executeTest();
    }
}
