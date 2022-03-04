package com.ap.flooringmastery.service;

import com.ap.flooringmastery.dao.FlooringMasteryDao;
import com.ap.flooringmastery.model.Order;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public class FlooringMasteryDaoStubImpl implements FlooringMasteryDao{
    
    public Order onlyOrder;
    
    public FlooringMasteryDaoStubImpl(){
        onlyOrder = new Order();
        onlyOrder.setOrderNumber(0);
        onlyOrder.setCustomerName("test");
        onlyOrder.setState("KY");
        BigDecimal testArea = new BigDecimal("101.00");
        onlyOrder.setArea(testArea);
    }
    
    public FlooringMasteryDaoStubImpl(Order testOrder){
        this.onlyOrder = testOrder;
    }
    
    @Override
    public List<Order> getAllOrders(String orderText) throws FlooringPersistenceException, FileNotFoundException {
        List<Order> orderList = new ArrayList<>();
        orderList.add(onlyOrder);
        return orderList;
    }

    @Override
    public Order addOrder(String orderText, int orderNumber, Order order) throws FlooringPersistenceException, FileNotFoundException {
        if(orderNumber == onlyOrder.getOrderNumber()){
            return onlyOrder;
        }else{
            return null;
        }
    }

    @Override
    public Order getOrder(String orderText, int orderNumber) throws FlooringPersistenceException, FileNotFoundException {
        if(orderNumber == onlyOrder.getOrderNumber()){
            return onlyOrder;
        }else{
            return null;
        }
    }

    @Override
    public Order editOrder(String orderText, int orderNumber, Order order) throws FlooringPersistenceException, FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order removeOrder(String orderText, int orderNumber) throws FlooringPersistenceException, FileNotFoundException {
        if(orderNumber == onlyOrder.getOrderNumber()){
            return onlyOrder;
        }else{
            return null;
        }
    }
    @Override
    public boolean fileExist(String orderText) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clearHash() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fileCreator(String orderText) throws FlooringPersistenceException, FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Integer> getOrderNumList(String orderText) throws FlooringPersistenceException, FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
