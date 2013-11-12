import models.ContactDB;
import play.Application;
import play.GlobalSettings;
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
    ContactDB.addContact(new ContactFormData("David", "Aghalarpour", "123-456-7890", "Home"));
    ContactDB.addContact(new ContactFormData("John", "Smith", "123-456-7890", "Work"));
    ContactDB.addContact(new ContactFormData("Jane", "Doe", "123-456-7890", "Mobile"));
    ContactDB.addContact(new ContactFormData("Michael", "Fassbender", "123-456-7890", "Work"));
  } 
}
