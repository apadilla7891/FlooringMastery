package com.ap.flooringmastery.dao;

import com.ap.flooringmastery.model.Order;
import com.ap.flooringmastery.service.FlooringPersistenceException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Andy Padilla
 */
public class FlooringMasteryDaoFileImpl implements FlooringMasteryDao{
    //make a class that gets a file based on order date and then loads the map using that file data for use/ if said file doesnt exist make a new one
    //make class that gets order number and returns is
    
    private Map<Integer, Order> orders = new HashMap<>();
    
    public static final String DELIMITER = ",";
    //temp for only one order list need to update to be able to use multiple files
    public static final String ORDER_FILE="order.txt";
    

    
    @Override
    public List<Order> getAllOrders(String orderText) throws FlooringPersistenceException, FileNotFoundException {
        loadOrders(orderText);
        return new ArrayList<Order>(orders.values());
    }
    
    @Override
    public Order addOrder(String orderText,int orderNumber, Order order) throws FlooringPersistenceException, FileNotFoundException  {
        loadOrders(orderText);
        Order prevOrder = orders.put(orderNumber, order);
        writeOrder(orderText);
        return prevOrder;
    }
    
    @Override
    public Order getOrder(String orderText, int orderNumber)throws FlooringPersistenceException, FileNotFoundException {
        loadOrders(orderText);
        return orders.get(orderNumber);
    }
    
    @Override
    public Order editOrder(String orderText, int orderNumber, Order order) throws FlooringPersistenceException, FileNotFoundException {
        loadOrders(orderText);
        orders.replace(orderNumber, order);
        writeOrder(orderText);
        return orders.get(orderNumber);
    }

    @Override
    public Order removeOrder(String orderText, int orderNumber) throws FlooringPersistenceException, FileNotFoundException  {
        loadOrders(orderText);
        Order removedOrder = orders.remove(orderNumber);
        writeOrder(orderText);
        return removedOrder;
    }

    //checks if order number is valid for edit/remove
    @Override
    public List<Integer> getOrderNumList(String orderText) throws FlooringPersistenceException, FileNotFoundException{
        loadOrders(orderText);
        return new ArrayList<Integer>(orders.keySet());
    }
    
    @Override
    public void clearHash(){
        orders.clear();
    }
    
    private Order unmarshallOrder(String orderAsText){
    // orderAsText gets a line from the file
    // it is in the following format
    // orderNum,customerName,state,tax rate,product type,area,cost per square foot, labor cost per square foot, material cost, labor cost, tax, total
    //  [0]         [1]       [2]     [3]     [4]        [5]        [6]                     [7]                     [8]             [9]    [10]   [11]
        String[] orderTokens = orderAsText.split(DELIMITER);

    // OrderNum is in index 0 of the array.
        String orderNumber = orderTokens[0];

    // create a new order object using the constructor and extracted title from file
        Order orderFromFile = new Order();

     //using setters, manually set the values.
     
    // Index 0 - orderNum
        orderFromFile.setOrderNumber(Integer.parseInt(orderTokens[0]));
    
    // Index 1 - customer name
        orderFromFile.setCustomerName(orderTokens[1]);

    // Index 2 - state
        orderFromFile.setState(orderTokens[2]);

    // Index 3 - tax rate
        double unmarshallNum = Double.parseDouble(orderTokens[3]);
        BigDecimal tempUnmarshall = new BigDecimal(unmarshallNum).setScale(2, RoundingMode.HALF_UP);
        orderFromFile.setTaxRate(tempUnmarshall);
        
    // Index 4 - product type
        orderFromFile.setProductType(orderTokens[4]);

    // Index 5 - area
        unmarshallNum = Double.parseDouble(orderTokens[5]);
        tempUnmarshall = new BigDecimal(unmarshallNum).setScale(2, RoundingMode.HALF_UP);
        orderFromFile.setArea(tempUnmarshall);
    
    // Index 6 - costPerSquareFoot
        unmarshallNum = Double.parseDouble(orderTokens[6]);
        tempUnmarshall = new BigDecimal(unmarshallNum).setScale(2, RoundingMode.HALF_UP);
        orderFromFile.setCostPerSquareFoot(tempUnmarshall);
    
    // Index 7 - laborCostPerSquareFoot
        unmarshallNum = Double.parseDouble(orderTokens[7]);
        tempUnmarshall = new BigDecimal(unmarshallNum).setScale(2, RoundingMode.HALF_UP);
        orderFromFile.setLaborCostPerSquareFoot(tempUnmarshall);
    
    // Index 8 - materialCost
        unmarshallNum = Double.parseDouble(orderTokens[8]);
        tempUnmarshall = new BigDecimal(unmarshallNum).setScale(2, RoundingMode.HALF_UP);
        orderFromFile.setMaterialCost(tempUnmarshall);
    
    // Index 9 - laborCost
        unmarshallNum = Double.parseDouble(orderTokens[9]);
        tempUnmarshall = new BigDecimal(unmarshallNum).setScale(2, RoundingMode.HALF_UP);
        orderFromFile.setLaborCost(tempUnmarshall);
        
    // Index 10 - Tax
        unmarshallNum = Double.parseDouble(orderTokens[10]);
        tempUnmarshall = new BigDecimal(unmarshallNum).setScale(2, RoundingMode.HALF_UP);
        orderFromFile.setTax(tempUnmarshall);
        
    // Index 11 - total
        unmarshallNum = Double.parseDouble(orderTokens[11]);
        tempUnmarshall = new BigDecimal(unmarshallNum).setScale(2, RoundingMode.HALF_UP);
        orderFromFile.setTotal(tempUnmarshall);
    
    // return created order
        return orderFromFile;
    }
    @Override
    public boolean fileExist(String orderText){
        File temp = new File(orderText);
        boolean exists = temp.exists();
        return exists;
    }
    
    //must update to make sure its getting from a specific date
    private void loadOrders(String orderText) throws FlooringPersistenceException, FileNotFoundException {
        Scanner scanner;
        File file = new File(orderText);
        //check if file exists and make if it doesnt
        /*try{
            File orderFile = new File(orderText);
            orderFile.createNewFile();
        }catch (IOException e) {
            //catches the FileNotFoundException and translates it to the FlooringPersistenceException created
            throw new FlooringPersistenceException("Error Order List could not be created.", e);
        }*/
        
        
        try {
            // Creates Scanner for reading the file
            scanner = new Scanner(new BufferedReader(new FileReader(orderText)));
        } catch (FileNotFoundException e) {
            //catches the FileNotFoundException and translates it to the FlooringPersistenceException created
            throw new FlooringPersistenceException("Error Order List data could not be loaded into memory.", e);
        }
        //skips header
        if(file.length()!=0){
            scanner.nextLine();
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentOrder holds the most recent order unmarshalled
        Order currentOrder;
        // Goes through ORDER_FILE line by line, decoding each line into a order object by calling the unmarshallOrder method.
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a order
            currentOrder = unmarshallOrder(currentLine);

            // Foloowing what was done earlier we will us the order number as the map key for the order object.
            orders.put(currentOrder.getOrderNumber(), currentOrder);
        }
        // close scanner
        scanner.close();
    }
    
    private String marshallOrder(Order aOrder){
        // Turns a order object into a line of text for our file.
        // it will be in the following format 
        //orderNum,customerName,state,tax rate,product type,area,cost per square foot, labor cost per square foot, material cost, labor cost, tax, total
        // Start with the ordernumber and add delimiter to crate space between each value
        String orderAsText = aOrder.getOrderNumber() + DELIMITER;
        // CustomerName
        orderAsText += aOrder.getCustomerName() + DELIMITER;
        // state
        orderAsText += aOrder.getState() + DELIMITER;
        // tax rate
        orderAsText += aOrder.getTaxRate().toString() + DELIMITER;
        // product type
        orderAsText += aOrder.getProductType() + DELIMITER;
        // area
        orderAsText += aOrder.getArea().toString() + DELIMITER;
        // costPerSquareFoot
        orderAsText += aOrder.getCostPerSquareFoot().toString() + DELIMITER;
        // laborCostPerSquareFoot
        orderAsText += aOrder.getLaborCostPerSquareFoot().toString() + DELIMITER;
        // materialcost
        orderAsText += aOrder.getMaterialCost().toString() + DELIMITER;
        // laborCost
        orderAsText += aOrder.getLaborCost().toString() + DELIMITER;
        // tax
        orderAsText += aOrder.getTax().toString() + DELIMITER;
        // user note, since this is the last value skip adding delimiter
        orderAsText += aOrder.getTotal().toString();
        // return the new line of text
        return orderAsText;
    }
    
    //Writes all order in the library out to a ORDER_FILE must update to add a date later.  
    private void writeOrder(String orderText) throws FlooringPersistenceException, FileNotFoundException {
        PrintWriter out;
        
        //catches and translates IOExceptions to FlooringPersistenceExceptions
        try {
            out = new PrintWriter(new FileWriter(orderText));
        } catch (IOException e) {
            throw new FlooringPersistenceException( "Could not save order data.", e);
        }
        // Write out the order objects to the orders file.
        //Reuses a previously created method to do this
        String orderAsText;
        List<Order> orderList = this.getAllOrders(orderText);
        out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
        for (Order currentOrder : orderList) {
            // turn a order into a String
            orderAsText = marshallOrder(currentOrder);
            // write the order object to the file
            out.println(orderAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up and close printwriter
        out.close();
    }
    
    @Override
    public void fileCreator(String orderText)throws FlooringPersistenceException, FileNotFoundException {
        File temp = new File(orderText);
        boolean exists = temp.exists();
        if(exists == false){
            try{
                File orderFile = new File(orderText);
                orderFile.createNewFile();
            }catch (IOException e) {
                //catches the FileNotFoundException and translates it to the FlooringPersistenceException created
                throw new FlooringPersistenceException("Error Order List could not be created.", e);
            }
        }
    }
   
}
