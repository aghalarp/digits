package views.formdata;

import java.util.ArrayList;
import java.util.List;
import models.Contact;
import play.data.validation.ValidationError;

/**
 * The backing class for form data.
 * @author Dave
 */
public class ContactFormData {
  
  private static final int NUM_TELEPHONE_CHARS = 12;
  
  /** The ID field. */
  public long id;
  /** The first name form field. */
  public String firstName = "";
  /** The last name form field. */
  public String lastName = "";
  /** The telephone form field. */
  public String telephone = "";
  /** The telephone type. */
  public String telephoneType = "";
  
  /**
   * The default constructor, required by Play.
   */
  public ContactFormData() {
    // default no-arg constructor.
  }
  
  /**
   * Create a ContactFormData object based upon a Contact.
   * @param contact The contact.
   */
  public ContactFormData(Contact contact) {
    this.id = contact.getId();
    this.firstName = contact.getFirstName();
    this.lastName = contact.getLastName();
    this.telephone = contact.getTelephone();
    this.telephoneType = contact.getTelephoneType();
  }
  
  
  /**
   * Validates the form input by the user.
   * All fields must be non-empty.
   * Telephone field must contain 12 characters.
   * 
   * @return null if no errors, list of ValidationErrors if errors.
   */
  
  public List<ValidationError> validate() {
    
    List<ValidationError> errors = new ArrayList<>();
    
    if (firstName == null || firstName.length() == 0) {
      errors.add(new ValidationError("firstName", "First name is required"));
    }
    
    if (lastName == null || lastName.length() == 0) {
      errors.add(new ValidationError("lastName", "Last name is required"));
    }
    
    if (telephone == null || telephone.length() == 0) {
      errors.add(new ValidationError("telephone", "Telephone is required"));
    }
    
    if (telephone.length() != NUM_TELEPHONE_CHARS) {
      errors.add(new ValidationError("telephone", "Telephone must be in the form of xxx-xxx-xxxx"));
    }
    
    if (!TelephoneTypes.isType(telephoneType)) {
      errors.add(new ValidationError("telephoneType", "Telephone type is invalid."));
    }
    
    return errors.isEmpty() ? null : errors;
  }
  
}
