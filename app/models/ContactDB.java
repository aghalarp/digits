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
  
  private static Map<String, Map<Long, Contact>> contacts = new HashMap<String, Map<Long, Contact>>();
  
  /**
   * Update the repo with a new Contact if id = 0, or update a pre-existing contact if id != 0.
   * @param user The user.
   * @param formData The contact data.
   * @return The newly created contact.
   */
  public static Contact addContact(String user, ContactFormData formData) {
    long idVal = (formData.id == 0) ? contacts.size() + 1 : formData.id;
    Contact contact = new Contact(idVal, formData.firstName, formData.lastName, formData.telephone, 
        formData.telephoneType);
    
    if (!isUser(user)) {
      contacts.put(user, new HashMap<Long, Contact>());
    }
    contacts.get(user).put(idVal, contact);
    return contact;
  }
  
  /**
   * Returns a list containing all defined contacts.
   * @param user The user.
   * @return A list of Contact instances or null if user not defined.
   */
  public static List<Contact> getContacts(String user) {
    if (!isUser(user)) {
      return null; //Or maybe return empty new ArrayList?
    }
    return new ArrayList<>(contacts.get(user).values());
  }
  
  /**
   * Returns true if the user is defined in the contacts DB.
   * @param user The user.
   * @return True if the user is defined.
   */
  public static boolean isUser(String user) {
    return contacts.containsKey(user);
  }
  
  /**
   * Returns a Contact instance associated with the passed ID, or throws a RuntimeException if the ID is not found 
   * or the user is not found.
   * @param user The user.
   * @param id The ID.
   * @return The retrieved ID.
   */
  public static Contact getContact(String user, long id) {
    if (!isUser(user)) {
      throw new RuntimeException("Passed a bogus user: " + user);
    }
    
    Contact contact = contacts.get(user).get(id);
    
    if (contact == null) {
      throw new RuntimeException("Passed a bogus id: " + id);
    }
    else {
      return contact;
    }
  }
  
  /**
   * Deletes a specified id from the contacts Map.
   * @param user The user.
   * @param id The ID to delete.
   */
  public static void deleteContact(String user, long id) {
    if (!isUser(user)) {
      throw new RuntimeException("Passed a bogus user: " + user);
    }
    
    Contact contact = contacts.get(user).get(id);
    
    if (contact == null) {
      throw new RuntimeException("Passed a bogus id: " + id);
    }
    else {
      contacts.get(user).remove(id);
    }
  }
  
}
