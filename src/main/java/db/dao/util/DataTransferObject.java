package db.dao.util;

/**
 * Interface to ensure compatibility of all entity objects that DAOs will be created for
 * Implements a getID() function to allow the DAO to retrieve ID from entity.
 */
public interface DataTransferObject {
    int getID();
}
