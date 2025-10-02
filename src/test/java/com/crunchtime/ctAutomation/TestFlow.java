package com.crunchtime.ctAutomation;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class TestFlow {


    @Test(invocationCount = 100, threadPoolSize = 8, enabled = false)
    public void testFlow(){
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(true));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://www.saucedemo.com/");
            page.locator("[data-test=\"username\"]").click();
            page.locator("[data-test=\"username\"]").fill("standard_user");
            page.locator("[data-test=\"password\"]").click();
            page.locator("[data-test=\"password\"]").fill("secret_sauce");
            page.locator("[data-test=\"login-button\"]").click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Open Menu")).click();
            page.locator("[data-test=\"about-sidebar-link\"]").click();
            page.goBack();
            page.locator("[data-test=\"product-sort-container\"]").selectOption("lohi");
            page.locator("[data-test=\"add-to-cart-sauce-labs-onesie\"]").click();
            page.locator("[data-test=\"add-to-cart-sauce-labs-bike-light\"]").click();
            page.locator("[data-test=\"add-to-cart-sauce-labs-bolt-t-shirt\"]").click();
            page.locator("[data-test=\"add-to-cart-test.allthethings()-t-shirt-(red)\"]").click();
            page.locator("[data-test=\"shopping-cart-link\"]").click();
            page.locator("[data-test=\"remove-test.allthethings()-t-shirt-(red)\"]").click();
            page.locator("[data-test=\"checkout\"]").click();
            page.locator("[data-test=\"firstName\"]").click();
            page.locator("[data-test=\"firstName\"]").fill("Test");
            page.locator("[data-test=\"lastName\"]").click();
            page.locator("[data-test=\"lastName\"]").fill("User");
            page.locator("[data-test=\"postalCode\"]").click();
            page.locator("[data-test=\"postalCode\"]").fill("90210");
            page.locator("[data-test=\"continue\"]").click();
            page.locator("[data-test=\"finish\"]").click();
            page.locator("[data-test=\"back-to-products\"]").click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Open Menu")).click();
            page.locator("[data-test=\"logout-sidebar-link\"]").click();
            page.navigate("https://bstackdemo.com/");
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign In")).click();
            page.locator("#username svg").click();
            page.getByText("demouser", new Page.GetByTextOptions().setExact(true)).click();
            page.locator("#password svg").click();
            page.getByText("testingisfun99", new Page.GetByTextOptions().setExact(true)).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log In")).click();
            page.locator("[id=\"1\"]").getByText("Add to cart").click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("+")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("+")).click();
            page.getByText("X", new Page.GetByTextOptions().setExact(true)).click();
            page.locator("[id=\"2\"]").getByText("Add to cart").click();
            page.locator(".shelf-item__del").nth(1).click();
            page.getByText("X", new Page.GetByTextOptions().setExact(true)).click();
            page.locator("[id=\"3\"]").getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("delete")).click();
            page.locator("span").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^3$"))).first().click();
            page.getByText("Checkout").click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name")).fill("Test");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name")).fill("User");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Address")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Address")).fill("test address");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("State/Province")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("State/Province")).fill("Test");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Postal Code")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Postal Code")).fill("90210");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue Shopping »")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Logout")).click();
        }
    }

}