package com.ap.flooringmastery.dao;

import com.ap.flooringmastery.model.Order;
import com.ap.flooringmastery.model.Product;
import com.ap.flooringmastery.service.FlooringPersistenceException;
import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public interface ProductsDao {
    
    public List<Product> getAllProducts() throws FlooringPersistenceException, FileNotFoundException;
   
    public List<String> getAllProductNames() throws FlooringPersistenceException, FileNotFoundException ;
    
    public Order productDataEnter(Order order, String product) throws FlooringPersistenceException, FileNotFoundException;
}
