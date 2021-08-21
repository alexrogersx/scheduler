package logic.entities;

import db.dao.util.DataTransferObject;


/**
 * The type Country.
 */
public class Country implements DataTransferObject {
    private int countryID;
    private String country;

    /**
     * Instantiates a new Country.
     *
     * @param countryID the country id
     * @param country   the country
     */
    public Country(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    /**
     * Instantiates a new Country.
     */
    public Country(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        return countryID == country.countryID;
    }

    @Override
    public int hashCode() {
        return countryID;
    }

    @Override
    public String toString() {
        return country;
    }

    @Override
    public int getID() {
        return countryID;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }


    /**
     * Sets country id.
     *
     * @param countryID the country id
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

}
