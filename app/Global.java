import models.ContactDB;
import models.UserInfoDB;
import play.Application;
import play.GlobalSettings;
import play.Play;
import views.formdata.ContactFormData;

/**
 * Provide initialization code for the digits application.
 * @author Dave
 *
 */
public class Global extends GlobalSettings {
  
  /**
   * Initialize the system with some sample contacts.
   * @param app The application.
   */
  public void onStart(Application app) {
    
    String adminEmail = Play.application().configuration().getString("digits.admin.email");
    String adminPassword = Play.application().configuration().getString("digits.admin.password");
    
    UserInfoDB.defineAdmin("Administrator", adminEmail, adminPassword);
    
    if (UserInfoDB.adminDefined()) {
      ContactDB.addContact(adminEmail, new ContactFormData("David", "Smith", "123-456-7890", "Home"));
      ContactDB.addContact(adminEmail, new ContactFormData("John", "Smith", "123-456-7890", "Work"));
    }
  }
}
