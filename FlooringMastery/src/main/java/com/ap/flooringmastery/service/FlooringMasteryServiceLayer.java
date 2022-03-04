package com.ap.flooringmastery.service;

import com.ap.flooringmastery.model.Order;
import com.ap.flooringmastery.model.Product;
import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public interface FlooringMasteryServiceLayer {
    public Order orderCalculations(Order order);
   
    public List<Order> retrieveAllOrders(String orderText) throws FlooringPersistenceException, FileNotFoundException;
    
    public List<Product> retrieveAllProducts() throws FlooringPersistenceException, FileNotFoundException;
    
    public Order addOrder(String orderText,int orderNumber, Order order) throws FlooringPersistenceException, FileNotFoundException;
    
    public Order retrieveOrder(String orderText, int orderNumber)throws FlooringPersistenceException, FileNotFoundException;
    
    public Order updateOrder(String orderText, int orderNumber, Order order) throws FlooringPersistenceException, FileNotFoundException;
    
    public Order deleteOrder(String orderText, int orderNumber) throws FlooringPersistenceException, FileNotFoundException;
    
    public Order stateInfoEnter(Order order, String state) throws FlooringPersistenceException, FileNotFoundException;
    
    public Order productInfoEnter(Order order, String product) throws FlooringPersistenceException, FileNotFoundException;
    
    public boolean orderNumCheck(String orderText,int orderNum) throws FlooringPersistenceException, FileNotFoundException;
    
    public String makeOrderText(String orderDate);
    
    public boolean fileCheck(String orderText);
    
    public void fileMake(String orderText)throws FlooringPersistenceException, FileNotFoundException ;
    
    public void clearHashMap();
    
    public Order autoGenOrderNum(List<Order> orderList, Order currentOrder);
    
    public List<String> retrieveAllStates() throws FlooringPersistenceException, FileNotFoundException;
    
     public List<String> retrieveAllProductNames() throws FlooringPersistenceException, FileNotFoundException ;

}
