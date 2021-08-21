package logic.entities;

import db.dao.util.DataTransferObject;

/**
 * The type Contact.
 */
public class Contact implements DataTransferObject {
    private int contactID;
    private String contactName;
    private String email;

    @Override
    public String toString() {
        return contactName;
    }

    @Override
    public int getID() {
        return contactID;
    }

    /**
     * Gets contact name.
     *
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (contactID != contact.contactID) return false;
        if (!contactName.equals(contact.contactName)) return false;
        return email.equals(contact.email);
    }

    @Override
    public int hashCode() {
        int result = contactID;
        result = 31 * result + contactName.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    /**
     * Sets contact id.
     *
     * @param contactID the contact id
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Sets contact name.
     *
     * @param contactName the contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
