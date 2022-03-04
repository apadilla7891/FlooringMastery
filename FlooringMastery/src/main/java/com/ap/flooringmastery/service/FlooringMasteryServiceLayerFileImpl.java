package com.ap.flooringmastery.service;

import com.ap.flooringmastery.dao.FlooringMasteryDao;
import com.ap.flooringmastery.dao.ProductsDao;
import com.ap.flooringmastery.dao.TaxesDao;
import com.ap.flooringmastery.model.Order;
import com.ap.flooringmastery.model.Product;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public class FlooringMasteryServiceLayerFileImpl implements FlooringMasteryServiceLayer{
    FlooringMasteryDao dao;
    ProductsDao pDao;
    TaxesDao tDao;
    
    public FlooringMasteryServiceLayerFileImpl(FlooringMasteryDao dao, ProductsDao pDao, TaxesDao tDao){
        this.dao = dao;
        this.pDao = pDao;
        this.tDao = tDao;
    }
    
    //calculates all the costs based on info in order
    @Override
    public Order orderCalculations(Order order) {
        if(order != null){
            BigDecimal taxRate = order.getTaxRate();
            BigDecimal area = order.getArea();
            BigDecimal costPerSquareFoot = order.getCostPerSquareFoot();
            BigDecimal laborCostPerSquareFoot = order.getLaborCostPerSquareFoot();
        
            BigDecimal materialCost = area.multiply(costPerSquareFoot);
            materialCost = materialCost.setScale(2,RoundingMode.HALF_UP);
            order.setMaterialCost(materialCost);
        
            BigDecimal laborCost = area.multiply(laborCostPerSquareFoot);
            laborCost = laborCost.setScale(2,RoundingMode.HALF_UP);
            order.setLaborCost(laborCost);
        
            BigDecimal matLabCost = materialCost.add(laborCost);
            BigDecimal hundred = new BigDecimal("100");
            BigDecimal taxPart = taxRate.divide(hundred,2, RoundingMode.HALF_UP);
            BigDecimal tax = matLabCost.multiply(taxPart);
            tax = tax.setScale(2,RoundingMode.HALF_UP);
            order.setTax(tax);
        
            BigDecimal total = materialCost.add(laborCost.add(tax));
            total = total.setScale(2,RoundingMode.HALF_UP);
            order.setTotal(total);
        }
        return order;
    }
    
    //gets all orders for a given date
    @Override
    public List<Order> retrieveAllOrders(String orderText) throws FlooringPersistenceException, FileNotFoundException {
        List<Order> orders = dao.getAllOrders(orderText);
        return orders;
    }
    
    //gets all products
    @Override
    public List<Product> retrieveAllProducts() throws FlooringPersistenceException, FileNotFoundException {
        List<Product> products = pDao.getAllProducts();
        return products;
    }
    
    //gets all taxes/states
    @Override
    public List<String> retrieveAllStates() throws FlooringPersistenceException, FileNotFoundException {
        List<String> states = tDao.getAllStates();
        return states;
    }
    
    //gets all product names
    @Override
    public List<String> retrieveAllProductNames() throws FlooringPersistenceException, FileNotFoundException {
        List<String> products = pDao.getAllProductNames();
        return products;
    }
    
    //adds order to a given dates list
    @Override
    public Order addOrder(String orderText,int orderNumber, Order order) throws FlooringPersistenceException, FileNotFoundException{
        Order orderAdded = dao.addOrder(orderText,orderNumber, order);
        return orderAdded;
    }
    
    //gets a single order based on date and order number
    @Override
    public Order retrieveOrder(String orderText, int orderNumber)throws FlooringPersistenceException, FileNotFoundException{
        Order retrievedOrder = dao.getOrder(orderText, orderNumber);
        return retrievedOrder;
    }
    
    //assuming theyre accepts makes changes to an order
    @Override
    public Order updateOrder(String orderText, int orderNumber, Order order) throws FlooringPersistenceException, FileNotFoundException {
        Order updatedOrder = dao.editOrder(orderText, orderNumber, order);
        return updatedOrder;
    }
    
    //removes an order
    @Override
    public Order deleteOrder(String orderText, int orderNumber) throws FlooringPersistenceException, FileNotFoundException{
        Order removed = dao.removeOrder(orderText, orderNumber);
        return removed;
    }
    
    //gets state from tax file and enters data into order
    @Override
    public Order stateInfoEnter(Order order, String state) throws FlooringPersistenceException, FileNotFoundException{
        Order updatedState = new Order();
        if(order!=null){
            updatedState = tDao.stateDataEnter(order, state);
            return updatedState;
        }
        return null;
    }
    

    
    //enters product info into order
    @Override
    public Order productInfoEnter(Order order, String product) throws FlooringPersistenceException, FileNotFoundException{
        Order updatedProduct = new Order();
        if(order != null){    
            updatedProduct = pDao.productDataEnter(order, product);
            return updatedProduct;
        }
        return null;
    }
    
    //checks that order num entered is valid
    @Override
    public boolean orderNumCheck(String orderText,int orderNum) throws FlooringPersistenceException, FileNotFoundException{
        List<Integer> orderNums = dao.getOrderNumList(orderText);
        boolean check = orderNums.contains(orderNum);
        return check;
    }
    
    //converst a valid date into a order text line
    @Override
    public String makeOrderText(String orderDate){
        String orderText = "Orders_" + orderDate + ".txt";
        return orderText;
    }
    
    //checks that order file exists
    @Override
    public boolean fileCheck(String orderText){
        boolean check = dao.fileExist(orderText);
        return check;
    }
    
    //clears hashmap so its empty before each load
    @Override
    public void clearHashMap(){
        dao.clearHash();
    }
    
    //creates order file if one doesnt exist
    @Override
    public void fileMake(String orderText)throws FlooringPersistenceException, FileNotFoundException {
        dao.fileCreator(orderText);
    }
    
    //auto generate order number for add class
    public Order autoGenOrderNum(List<Order> orderList, Order currentOrder){
        int orderNum = 0;
        int maxNum = 0;
        if(orderList.size()==0){
            currentOrder.setOrderNumber(orderNum);
            return currentOrder;
        }else{
            for(int i = 0; i < orderList.size(); i++){
                if(orderNum < orderList.get(i).getOrderNumber()){
                    maxNum = orderList.get(i).getOrderNumber();
                }
            }
       }
        maxNum++;
        currentOrder.setOrderNumber(maxNum);
        return currentOrder;
    }
}
