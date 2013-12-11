package tests;

import org.junit.Test;
import play.test.TestBrowser;
import play.libs.F.Callback;
import tests.pages.IndexPage;
import tests.pages.LoginPage;
import tests.pages.NewContactPage;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.testServer;
import static play.test.Helpers.running;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Integration tests running on an instance of the application.
 */
public class IntegrationTest {
  /** The port number for the integration test. */
  private static final int PORT = 3333;

  /**
   * Check to see that the two pages can be displayed.
   */
  @Test
  public void testBasicRetrieval() {
    running(testServer(PORT, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        browser.goTo("http://localhost:" + PORT);
        assertThat(browser.pageSource()).contains("digits");
      }
    });
  }
  
  /**
   * Test that the admin user can successfully login to the system and logout.
   */
  @Test
  public void testLogin() {
    running(testServer(PORT, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        IndexPage indexPage = new IndexPage(browser.getDriver(), PORT);
        browser.goTo(indexPage);
        indexPage.isAt();
        indexPage.goToLogin();
        
        LoginPage loginPage = new LoginPage(browser.getDriver(), PORT);
        loginPage.isAt();
        loginPage.login();
        assertThat(indexPage.isLoggedIn()).isTrue();
        
        indexPage.logout();
        assertThat(indexPage.isLoggedIn()).isFalse();
      }
    });
  }
  
  /**
   * Test that we can add a new contact.
   */
  @Test
  public void testNewContact() {
    running(testServer(PORT, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        IndexPage indexPage = new IndexPage(browser.getDriver(), PORT);
        browser.goTo(indexPage);
        indexPage.isAt();
        indexPage.goToLogin();
        
        LoginPage loginPage = new LoginPage(browser.getDriver(), PORT);
        loginPage.isAt();
        loginPage.login();
        assertThat(indexPage.isLoggedIn()).isTrue();
        
        indexPage.goToNewContact();
        NewContactPage newContactPage = new NewContactPage(browser.getDriver(), PORT);
        newContactPage.isAt();
        newContactPage.makeContact("Joe", "Montana", "123-456-7890", "Mobile");
        browser.goTo(indexPage);
        assertThat(browser.pageSource()).contains("Montana");
        
      }
    });
  }

}
