package com.ap.flooringmastery.service;

import com.ap.flooringmastery.dao.TaxesDao;
import com.ap.flooringmastery.model.Order;
import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public class TaxesDaoStubFileImpl implements TaxesDao{

    @Override
    public List<String> getAllStates() throws FlooringPersistenceException, FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order stateDataEnter(Order order, String state) throws FlooringPersistenceException, FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
