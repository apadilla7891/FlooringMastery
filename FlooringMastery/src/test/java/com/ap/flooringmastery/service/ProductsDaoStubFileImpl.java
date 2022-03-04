package com.ap.flooringmastery.service;

import com.ap.flooringmastery.dao.ProductsDao;
import com.ap.flooringmastery.model.Order;
import com.ap.flooringmastery.model.Product;
import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public class ProductsDaoStubFileImpl implements ProductsDao{

    @Override
    public List<Product> getAllProducts() throws FlooringPersistenceException, FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getAllProductNames() throws FlooringPersistenceException, FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order productDataEnter(Order order, String product) throws FlooringPersistenceException, FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
