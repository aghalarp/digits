package models;

import java.util.List;
import views.formdata.ContactFormData;

/**
 * Provides a simple in-memory repository for Contact data.
 * @author Dave
 *
 */
public class ContactDB {
  
  /**
   * Update the repo with a new Contact if id = -1, or update a pre-existing contact if id != -1.
   * @param user The user.
   * @param formData The contact data.
   */
  public static void addContact(String user, ContactFormData formData) {
    boolean isNewContact = (formData.id == -1);
    
    if (isNewContact) {
      //Create new Contact entity object
      Contact contact = new Contact(formData.firstName, formData.lastName, formData.telephone, formData.telephoneType);
      //Find and create UserInfo entity object
      UserInfo userInfo = UserInfo.find().where().eq("email", user).findUnique();
      if (userInfo == null) {
        throw new RuntimeException("Could not find user: " + user);
      }
      
      //Now connect these entities by setting their fields
      userInfo.addContact(contact); //Note how the word "add" inherently indicates the OneToMany relationship.
      contact.setUserInfo(userInfo); //While "set" indicates the OneToOne relationship.
      
      //The save method comes from ebeans (when we extended the Model class in the Contact and UserInfo classes)
      contact.save();
      userInfo.save();
    }
    else { //Otherwise, Contact already exists
      //Retrieve Contact from the database.
      Contact contact = Contact.find().byId(formData.id);
      
      //Then update fields.
      //Unfortunately we don't know which of these form fields were changed, so we do them all.
      contact.setFirstName(formData.firstName);
      contact.setLastName(formData.lastName);
      contact.setTelephone(formData.telephone);
      contact.setTelephoneType(formData.telephoneType);
      
      //Note: This eBeans save method is smart enough to know that we're updating an existing Contact
      //rather than creating a new Contact.
      contact.save();
      
    }
  }
  
  /**
   * Returns a list containing all defined contacts.
   * In other words, returns all Contacts associated with the given user.
   * @param user The user.
   * @return A list of Contact instances or null if user not defined.
   */
  public static List<Contact> getContacts(String user) {
    //Grab the UserInfo object associated with the user.
    UserInfo userInfo = UserInfo.find().where().eq("email", user).findUnique();
    
    if (userInfo == null) {
      return null;
    }
    else {
      return userInfo.getContacts();
    }
  }
  
  /**
   * Returns true if the user is defined in the contacts DB.
   * @param user The user.
   * @return True if the user is defined.
   */
  public static boolean isUser(String user) {
    //So this returns True if user is found. False otherwise.
    return (UserInfo.find().where().eq("email", user).findUnique() != null);
  }
  
  /**
   * Returns a Contact instance associated with the passed ID, or throws a RuntimeException if the ID is not found 
   * or the user is not found.
   * @param user The user.
   * @param id The ID.
   * @return The retrieved ID.
   */
  public static Contact getContact(String user, long id) {
    Contact contact = Contact.find().byId(id);
    
    if (contact == null) {
      throw new RuntimeException("Contact ID not found: " + id);
    }
    
    UserInfo userInfo = contact.getUserInfo();
    if (!user.equals(userInfo.getEmail())) {
      throw new RuntimeException("User not the same one stored with the contact.");
    }
    
    return contact;
  }
  
  /**
   * Deletes a specified id from the contacts Map.
   * @param user The user.
   * @param id The ID to delete.
   */
  public static void deleteContact(String user, long id) {
    
    Contact contact = Contact.find().byId(id);
    
    if (contact == null) {
      throw new RuntimeException("Contact ID not found: " + id);
    }
    
    UserInfo userInfo = contact.getUserInfo();
    if (!user.equals(userInfo.getEmail())) {
      throw new RuntimeException("User not the same one stored with the contact.");
    }
    
    //eBeans delete method. This should delete the specified contact from the DB.
    contact.delete();
  }
  
}
