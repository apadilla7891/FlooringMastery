package com.ap.flooringmastery.dao;

import com.ap.flooringmastery.model.Order;
import com.ap.flooringmastery.service.FlooringPersistenceException;
import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public interface TaxesDao {
        
    public List<String> getAllStates() throws FlooringPersistenceException, FileNotFoundException;
    
    public Order stateDataEnter(Order order, String state) throws FlooringPersistenceException, FileNotFoundException;
}
