package logic.entities;

import db.dao.util.DataTransferObject;

/**
 * The type Division.
 */
public class Division implements DataTransferObject {
    private int divisionID;
    private String division;
    private Country country;


    @Override
    public String toString() {
        return division;
    }

    public int getID() {
        return divisionID;
    }

    /**
     * Gets division.
     *
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Sets division id.
     *
     * @param divisionID the division id
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Sets division.
     *
     * @param division the division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(Country country) {
        this.country = country;
    }
}
