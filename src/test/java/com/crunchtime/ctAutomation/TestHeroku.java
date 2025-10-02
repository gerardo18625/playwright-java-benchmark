package com.crunchtime.ctAutomation;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.MouseButton;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TestHeroku {


    @Test(invocationCount = 20, threadPoolSize = 1)
    public void herokuTest(){
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(true));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.setDefaultTimeout(60000);

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
            page.onceDialog(dialog -> {
                System.out.println(String.format("Dialog message: %s", dialog.message()));
                dialog.dismiss();
            });
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
}
