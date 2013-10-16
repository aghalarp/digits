package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import views.formdata.ContactFormData;

/**
 * Provides a simple in-memory repository for Contact data.
 * @author Dave
 *
 */
public class ContactDB {
  
  private static Map<Long, Contact> contacts = new HashMap<>();
  
  /**
   * Update the repo with a new Contact if id = 0, or update a pre-existing contact if id != 0.
   * @param formData The contact data.
   * @return The newly created contact.
   */
  public static Contact addContact(ContactFormData formData) {
    long idVal = (formData.id == 0) ? contacts.size() + 1 : formData.id;
    Contact contact = new Contact(idVal, formData.firstName, formData.lastName, formData.telephone);
    
    contacts.put(idVal, contact);
    
    return contact;
  }
  
  /**
   * Returns a list containing all defined contacts.
   * @return A list of Contact instances.
   */
  public static List<Contact> getContacts() {
    return new ArrayList<>(contacts.values());
  }
  
  /**
   * Returns a Contact instance associated with the passed ID, or throws a RuntimeException if the ID is not found.
   * @param id The ID.
   * @return The retrieved ID.
   */
  public static Contact getContact(long id) {
    Contact contact = contacts.get(id);
    
    if (contact == null) {
      throw new RuntimeException("Passed a bogus id: " + id);
    }
    else {
      return contact;
    }
  }
  
  /**
   * Deletes a specified id from the contacts Map.
   * @param id The ID to delete.
   */
  public static void deleteContact(long id) {
    Contact contact = contacts.get(id);
    
    if (contact == null) {
      throw new RuntimeException("Passed a bogus id: " + id);
    }
    else {
      contacts.remove(id);
    }
  }
  
}
