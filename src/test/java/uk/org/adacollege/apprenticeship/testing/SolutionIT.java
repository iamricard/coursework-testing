package uk.org.adacollege.apprenticeship.testing;

import static org.junit.Assert.assertTrue;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

public class SolutionIT {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static String startUrl;
    private static String myWhipbirdsMenuId = "TODO";
    private static String aboutMenuId = "TODO";
    private static String logOutMenuId = "TODO";
    private static String logInMenuId = "TODO";
    private static String emailInputId = "TODO";
    private static String passwordInputId = "TODO";
    private static String validEmail = "TODO";
    private static String invalidEmail = validEmail + ".nothing";
    private static String validPassword = "TODO";
    private static String invalidPassword = validPassword + "-invalid";
    private static String logInButtonId = "TODO";
    private static String logOutButtonId = "TODO";
    private static String popupMessageId = "TODO";

    // ========= UTILITY METHODS =========

    /**
     * Source & usage: https://stackoverflow.com/a/5709805
     */
    private static Function<WebDriver, WebElement> presenceOfElementLocated(final By locator) {
        return new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        };
    }

    private static void logIn(Boolean withValidCredentials) {
        String email = withValidCredentials ? validEmail : invalidEmail;
        String password = withValidCredentials ? validPassword : invalidPassword;

        wait.until(presenceOfElementLocated(By.id(logInMenuId)));
        driver.findElement(By.id(logInMenuId)).click();

        wait.until(presenceOfElementLocated(By.id(emailInputId)));
        driver.findElement(By.id(emailInputId)).sendKeys(email);

        wait.until(presenceOfElementLocated(By.id(passwordInputId)));
        driver.findElement(By.id(passwordInputId)).sendKeys(password);

        wait.until(presenceOfElementLocated(By.id(logInButtonId)));
        driver.findElement(By.id(logInButtonId)).click();

        if (withValidCredentials) {
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return driver.getTitle().equals("whipbird: my whipbirds");
                }
            });
        }
    }

    private static void logOut() {
        Boolean isLoggedIn = (driver.findElements(By.id(logOutMenuId)).size() > 0);

        if (isLoggedIn) {
            wait.until(presenceOfElementLocated(By.id(logOutMenuId)));
            driver.findElement(By.id(logOutMenuId)).click();

            wait.until(presenceOfElementLocated(By.id(logOutButtonId)));
            driver.findElement(By.id(logOutButtonId)).click();
        }
    }

    private static void assertElementPresent(String elementId) {
        wait.until(presenceOfElementLocated(By.id(elementId)));
        assertTrue(driver.findElements(By.id(elementId)).size() == 1);
    }

    private static void assertElementNotPresent(String elementId) {
        assertTrue(driver.findElements(By.id(elementId)).size() == 0);
    }

    private static void assertTitleEquals(String expectedTitle) {
        Boolean result = wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return driver.getTitle().equals(expectedTitle);
            }
        });
        assertTrue(result);
    }

    private static void assertUrlEquals(String expectedUrl) {
        // TODO: implement this method
        // - use assertTitleEquals() as an example pattern to follow
        // - search the web for how to find the current URL with Selenium
    }

    private static void assertElementTextEquals(By selector, String expectedText) {
        // TODO: implement this method
        // - use assertTitleEquals() as an example pattern to follow
        // - but instead of return driver.getTitle().equals(expectedTitle)
        //   call driver.findElement() with the selector provided
        //   and then get the text from that element
        //   and then check that it equals the expected text
    }

    // ========= SCAFFOLDING =========

    @BeforeClass
    public static void beforeAll() {
        startUrl = "http://whipbird.mattcalthrop.com/";
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
    }

    @AfterClass
    public static void afterAll() {
        driver.close();
        driver.quit();
    }

    @Before
    public void beforeEach() {
        driver.get(startUrl);
    }

    @After
    public void afterEach() {
        logOut();
    }

    // ========= TESTS =========

    // --------- WHEN NOT LOGGED IN ---------

    // Step 1
    @Test
    public void notLoggedIn_checkMenus() {
        assertElementPresent(logInMenuId);
        assertElementNotPresent(logOutMenuId);
        // TODO: complete for all other menus
    }

    // Step 2
    @Test
    public void notLoggedIn_checkCurrentPage() {
        // TODO
    }

    // Step 3
    @Test
    public void notLoggedIn_clickAboutMenu() {
        // TODO
    }

    // Step 4
    @Test
    public void notLoggedIn_logInWithIncorrectCredentials() {
        // TODO
    }

    // --------- WHEN LOGGED IN ---------

    // Step 5
    @Test
    public void loggedIn_checkMenus() {
        logIn(true);
        // TODO
    }

    // Step 6
    @Test
    public void loggedIn_checkCurrentPage() {
        // TODO
    }

    // Step 7
    @Test
    public void loggedIn_clickLogOutMenu() {
        // TODO
    }

    // Step 8
    @Test
    public void loggedIn_addNewWhipbird() {
        // TODO
    }

    // Step 9
    @Test
    public void loggedIn_addNewWhipbirdThenDeleteIt() {
        // TODO
    }
}
