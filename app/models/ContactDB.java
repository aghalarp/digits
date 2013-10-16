package models;

import java.util.ArrayList;
import java.util.List;
import views.formdata.ContactFormData;

/**
 * Provides a simple in-memory repository for Contact data.
 * @author Dave
 *
 */
public class ContactDB {
  
  private static List<Contact> contacts = new ArrayList<>();
  
  /**
   * Creates and returns a new Contact, storing it in the in-memory repository.
   * @param formData The contact data.
   * @return The newly created contact.
   */
  public static Contact addContact(ContactFormData formData) {
    //Create new contact object with formData info
    Contact contact = new Contact(formData.firstName, formData.lastName, formData.telephone);
    
    //Add contact object to List
    contacts.add(contact);
    
    return contact;
  }
  
  /**
   * Returns a list containing all defined contacts.
   * @return A list of Contact instances.
   */
  public static List<Contact> getContacts() {
    return contacts;
  }
  
  
}
