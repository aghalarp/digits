package controllers;

import java.util.Map;
import models.ContactDB;
import models.UserInfo;
import models.UserInfoDB;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.formdata.ContactFormData;
import views.formdata.LoginFormData;
import views.formdata.TelephoneTypes;
import views.html.Index;
import views.html.Login;
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
    //UserInfo userInfo = UserInfoDB.getUser(request().username());
    //String user = userInfo.getEmail();
    //Boolean isLoggedIn = (userInfo != null);
    return ok(Index.render("Index", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), ContactDB.getContacts(Secured.getUser(ctx()))));
  }
  
  /**
   * Returns newcontact, a page containing a simple form.
   * @param id The id to retrieve, or create a new instance if ID is zero.
   * @return The NewContact page.
   */
  @Security.Authenticated(Secured.class)
  public static Result newContact(long id) {
    UserInfo userInfo = UserInfoDB.getUser(request().username());
    String user = userInfo.getEmail();
    Boolean isLoggedIn = (userInfo != null);
    
    ContactFormData data = (id == -1) ? new ContactFormData() : new ContactFormData(ContactDB.getContact(user, id));
    Form<ContactFormData> formData = Form.form(ContactFormData.class).fill(data);
    Map<String, Boolean> telephoneTypeMap = TelephoneTypes.getTypes(data.telephoneType);
    return ok(NewContact.render("NewContact", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), formData, telephoneTypeMap));
    
  }
  
  /**
   * Handles the posting of form data by the user.
   * @return The NewContact page with form data.
   */
  @Security.Authenticated(Secured.class)
  public static Result postContact() {
    UserInfo userInfo = UserInfoDB.getUser(request().username());
    String user = userInfo.getEmail();
    Boolean isLoggedIn = (userInfo != null);
    
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
      //Create empty telephoneTypeMap
      Map<String, Boolean> telephoneTypeMap = TelephoneTypes.getTypes();
      return badRequest(NewContact.render("NewContact", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), formData, telephoneTypeMap));
    }
    else {
      ContactFormData data = formData.get(); //Creates the object we made (ContactFormData) and fills with get data
      //Add to database
      ContactDB.addContact(user, data);
      Map<String, Boolean> telephoneTypeMap = TelephoneTypes.getTypes(data.telephoneType);
      return ok(NewContact.render("NewContact", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), formData, telephoneTypeMap));
    }
    
  }
  
  /**
   * Deletes corresponding ID from ContactDB.
   * @param id The id to delete.
   * @return Index page.
   */
  @Security.Authenticated(Secured.class)
  public static Result deleteContact(long id) {
    UserInfo userInfo = UserInfoDB.getUser(request().username());
    String user = userInfo.getEmail();
    Boolean isLoggedIn = (userInfo != null);
    ContactDB.deleteContact(user, id);
    
    return ok(Index.render("Index", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), ContactDB.getContacts(user)));
  }
  
  /**
   * Provides the Login page (only to unauthenticated users). 
   * @return The Login page. 
   */
  public static Result login() {
    Form<LoginFormData> formData = Form.form(LoginFormData.class);
    return ok(Login.render("Login", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), formData));
  }

  /**
   * Processes a login form submission from an unauthenticated user. 
   * First we bind the HTTP POST data to an instance of LoginFormData.
   * The binding process will invoke the LoginFormData.validate() method.
   * If errors are found, re-render the page, displaying the error data. 
   * If errors not found, render the page with the good data. 
   * @return The index page with the results of validation. 
   */
  public static Result postLogin() {

    // Get the submitted form data from the request object, and run validation.
    Form<LoginFormData> formData = Form.form(LoginFormData.class).bindFromRequest();

    if (formData.hasErrors()) {
      flash("error", "Login credentials not valid.");
      return badRequest(Login.render("Login", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), formData));
    }
    else {
      // email/password OK, so now we set the session variable and only go to authenticated pages.
      session().clear();
      session("email", formData.get().email);
      return redirect(routes.Application.index());
    }
  }
  
  /**
   * Logs out (only for authenticated users) and returns them to the Index page. 
   * @return A redirect to the Index page. 
   */
  @Security.Authenticated(Secured.class)
  public static Result logout() {
    session().clear();
    return redirect(routes.Application.index());
  }
}
