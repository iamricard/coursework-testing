package uk.org.adacollege.apprenticeship.testing;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

import static org.junit.Assert.assertTrue;

public class SolutionIT {
  private static WebDriver driver;
  private static WebDriverWait wait;
  private static String startUrl;
  private static String myWhipbirdsMenuId = "my-whipbirds-menu";
  private static String aboutMenuId = "about-menu";
  private static String logOutMenuId = "log-out-menu";
  private static String logInMenuId = "log-in-menu";
  private static String emailInputId = "email";
  private static String passwordInputId = "password";
  private static String validEmail = "ricard.casas@adacollege.org.uk";
  private static String invalidEmail = validEmail + ".nothing";
  private static String validPassword = "whipit";
  private static String invalidPassword = validPassword + "-invalid";
  private static String logInButtonId = "login-button";
  private static String logOutButtonId = "log-out-button";
  private static String popupMessageId = "global-snackbar";
  private static String footerRightId = "footer-right";

  // ========= UTILITY METHODS =========

  /**
   * Source & usage: https://stackoverflow.com/a/5709805
   */
  private static Function<WebDriver, WebElement> presenceOfElementLocated(final By locator) {
    return driver -> driver.findElement(locator);
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
      wait.until((ExpectedCondition<Boolean>) driver ->
        driver
        .getTitle()
        .equals("whipbird: my whipbirds")
      );
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
    assertTrue(wait.until((WebDriver driver) ->
      driver
      .getTitle()
      .equals(expectedTitle)
    ));
  }

  private static void assertUrlEquals(String expectedUrl) {
    assertTrue(wait.until((WebDriver driver) ->
      driver
      .getCurrentUrl()
      .equals(expectedUrl)
    ));
  }

  private static void assertElementTextEquals(By selector, String expectedText) {
    assertTrue(wait.until((WebDriver driver) ->
      driver
      .findElement(selector)
      .getText()
      .equals(expectedText)
    ));
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
    assertElementPresent(aboutMenuId);
    assertElementNotPresent(myWhipbirdsMenuId);
  }

  // Step 2
  @Test
  public void notLoggedIn_checkCurrentPage() {
    assertUrlEquals(startUrl + "#!/login");
    assertTitleEquals("whipbird: log in");
    assertElementTextEquals(By.cssSelector("h4"), "Log in");
    assertElementTextEquals(By.id(footerRightId), "");
  }

  // Step 3
  @Test
  public void notLoggedIn_clickAboutMenu() {
    wait.until(presenceOfElementLocated(By.id(aboutMenuId)));
    driver.findElement(By.id(aboutMenuId)).click();

    assertUrlEquals(startUrl + "#!/about");
    assertTitleEquals("whipbird: about");
    assertElementTextEquals(By.cssSelector("h4"), "About this app");
  }

  // Step 4
  @Test
  public void notLoggedIn_logInWithIncorrectCredentials() {
    logIn(false);
    assertElementPresent(logInMenuId);
    assertElementNotPresent(logOutMenuId);
    assertElementPresent(aboutMenuId);
    assertElementNotPresent(myWhipbirdsMenuId);

    assertUrlEquals(startUrl + "#!/login");
    assertTitleEquals("whipbird: log in");

    assertElementTextEquals(
      By.id(popupMessageId),
      "Username or password incorrect"
    );

    assertElementTextEquals(By.id(footerRightId), "");
  }

  // --------- WHEN LOGGED IN ---------

  // Step 5
  @Test
  public void loggedIn_checkMenus() {
    logIn(true);
    assertElementNotPresent(logInMenuId);
    assertElementPresent(logOutMenuId);
    assertElementPresent(aboutMenuId);
    assertElementPresent(myWhipbirdsMenuId);
  }

  // Step 6
  @Test
  public void loggedIn_checkCurrentPage() {
    logIn(true);
    assertUrlEquals(startUrl + "#!/my-whipbirds");
    assertTitleEquals("whipbird: my whipbirds");
    assertElementTextEquals(
      By.cssSelector("h4"),
      "Current whipbirds for Ricard Sole Casas"
    );
    assertElementTextEquals(
      By.id(footerRightId), "Ricard Sole Casas"
    );
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
