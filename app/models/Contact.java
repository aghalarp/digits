package models;

/**
 * Contact class.
 *
 */
public class Contact {
  
  private long id;
  private String firstName;
  private String lastName;
  private String telephone;
  private String telephoneType;
  
  /**
   * Creates a new contact.
   * @param id The ID.
   * @param firstName The first name.
   * @param lastName The last name.
   * @param telephone The telephone number.
   * @param telephoneType The telephone type.
   */
  public Contact(long id, String firstName, String lastName, String telephone, String telephoneType) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.telephone = telephone;
    this.telephoneType = telephoneType;
  }
  
  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }
  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }
  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  /**
   * @return the telephone
   */
  public String getTelephone() {
    return telephone;
  }
  /**
   * @param telephone the telephone to set
   */
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  /**
   * @return the telephoneType
   */
  public String getTelephoneType() {
    return telephoneType;
  }

  /**
   * @param telephoneType the telephoneType to set
   */
  public void setTelephoneType(String telephoneType) {
    this.telephoneType = telephoneType;
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

}
