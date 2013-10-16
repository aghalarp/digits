package controllers;

import models.ContactDB;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.formdata.ContactFormData;
import views.html.Index;
import views.html.NewContact;

/**
 * Implements the controllers for this application.
 */
public class Application extends Controller {

  /**
   * Returns the home page. 
   * @return The resulting home page. 
   */
  public static Result index() {
    return ok(Index.render(ContactDB.getContacts()));
  }
  
  /**
   * Returns newcontact, a page containing a simple form.
   * @return The NewContact page.
   */
  public static Result newContact() {
    Form<ContactFormData> formData = Form.form(ContactFormData.class);
    return ok(NewContact.render(formData));
    
  }
  
  /**
   * Handles the posting of form data by the user.
   * @return The NewContact page with form data.
   */
  public static Result postContact() {
    Form<ContactFormData> formData = Form.form(ContactFormData.class).bindFromRequest();
    
    /* Important to understand: Whenever we invoke bindFromRequest(), if there is a validation() method in the
     * associated object's class (in this case, ContactFormData) that implements the form, it will call that validation
     * method and annotate that object with information about any found errors (We see that the validate() method
     * returns either null or a list of ValidationErrors). We can then check if the formData object contains any errors
     * by calling the hasErrors() method, as seen below.
     * 
     * Also remember: We cannot call the get() method if there are errors in the formData object, so we put it in the
     * else clause.
     */
    if (formData.hasErrors()) {
      return badRequest(NewContact.render(formData));
    }
    else {
      ContactFormData data = formData.get(); //Creates the object we made (ContactFormData) and fills with get data
      //Add to database
      ContactDB.addContact(data);
      return ok(NewContact.render(formData));
    }
    
  }
}
