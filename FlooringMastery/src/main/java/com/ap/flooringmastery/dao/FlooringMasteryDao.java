package com.ap.flooringmastery.dao;

import com.ap.flooringmastery.model.Order;
import com.ap.flooringmastery.service.FlooringPersistenceException;
import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public interface FlooringMasteryDao {
    List<Order> getAllOrders(String orderText)throws FlooringPersistenceException, FileNotFoundException ;
    
    Order addOrder(String orderText,int orderNumber, Order order)throws FlooringPersistenceException, FileNotFoundException ;
    
    Order getOrder(String orderText, int orderNumber)throws FlooringPersistenceException, FileNotFoundException ;
    
    Order editOrder(String orderText, int orderNumber, Order order)throws FlooringPersistenceException, FileNotFoundException ;
    
    Order removeOrder(String orderText, int orderNumber)throws FlooringPersistenceException, FileNotFoundException ; 
    
    public List<Integer> getOrderNumList(String orderText) throws FlooringPersistenceException, FileNotFoundException;

    public boolean fileExist(String orderText);
    
    public void clearHash();
    
    public void fileCreator(String orderText)throws FlooringPersistenceException, FileNotFoundException;
}
