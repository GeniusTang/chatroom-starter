package edu.udacity.java.nano;


import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebSocketChatApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WebSocketChatApplicationTest {

    WebDriver webDriver;
    @Test
    public void loginAndJoin() throws Exception {
        testName("Kujin");
        testName("");
    }


    private void testName(String inputName) {
        String outputName = inputName.isEmpty() ? "Anonymous" : inputName;

        webDriver = new HtmlUnitDriver(true);
        webDriver.get("http://localhost:8080");

        System.out.println("Visiting: http://localhost:8080");

        WebElement username = webDriver.findElement(By.name("username"));
        username.sendKeys(inputName);

        WebElement login_form = webDriver.findElement(By.className("submit"));
        login_form.click();

        (new WebDriverWait(webDriver, 1)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement username1 = webDriver.findElement(By.id("username"));
                System.out.println("username: " + username1.getText());
                return username1.getText().equals(outputName);
            }
        });
    }

    @Test
    public void chat() throws Exception {
        testChat("Kujin", "Hello");
        testChat("", "How are you");
    }

    private void testChat(String inputName, String message) {
        String outputName = inputName.isEmpty() ? "Anonymous" : inputName;

        webDriver = new HtmlUnitDriver(true);
        webDriver.get("http://localhost:8080" + "/index?username=" + inputName);

        //Check join notification
        (new WebDriverWait(webDriver, 5)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                List<WebElement> messages = webDriver.findElements(By.className("message-content"));
                if (messages.size() == 0) {
                    return false;
                }
                System.out.println(messages.get(0).getText());
                return messages.get(0).getText().equals(outputName + " has joined chat.");
            }
        });

        //Send message
        WebElement sendBox = webDriver.findElement(By.id("msg"));
        sendBox.sendKeys(message);
        WebElement sendButton = webDriver.findElement(By.tagName("button"));

        sendButton.click();

        //Check message notification
        (new WebDriverWait(webDriver, 5)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                List<WebElement> messages = webDriver.findElements(By.className("message-content"));
                if (messages.size() == 0) {
                    return false;
                }
                System.out.println(messages.get(messages.size() - 1).getText());
                return messages.get(messages.size() - 1).getText().equals(outputName + ": " + message);
            }
        });
    }

    @Test
    public void leave() throws Exception {
        webDriver = new HtmlUnitDriver(true);
        webDriver.get("http://localhost:8080" + "/index?username=Kujin");

        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        WebElement leaveButton = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("exit")));

        leaveButton.click();

        assertEquals("http://localhost:8080/", webDriver.getCurrentUrl());
    }

    @After
    public void close() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}