package com.ap.flooringmastery.controller;

import com.ap.flooringmastery.model.Order;
import com.ap.flooringmastery.model.Product;
import com.ap.flooringmastery.service.FlooringMasteryServiceLayer;
import com.ap.flooringmastery.service.FlooringPersistenceException;
import com.ap.flooringmastery.view.FlooringMasteryView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public class FlooringMasteryController {
    
    private FlooringMasteryView view;
    private FlooringMasteryServiceLayer service;
    
    public FlooringMasteryController(FlooringMasteryServiceLayer service, FlooringMasteryView view){
        this.service = service;
        this.view = view;
    }
    
    public void run() throws FileNotFoundException, IOException{
        boolean keepGoing = true;
        int menuSelection = 0;
        try{
            while(keepGoing){

                menuSelection = getMenuSelection();
            
                switch(menuSelection){
                    case 1:
                        listOrders();
                        break;
                    case 2:
                        createOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
        exitMessage();
        } catch(FlooringPersistenceException e){
            view.displayErrorMessage(e.getMessage());
        }
    }
    
    private int getMenuSelection(){
        return view.printMenuAndGetSelection();
    }
    
    //creates a new order and file if one doesnt exist
    private void createOrder()throws FlooringPersistenceException, FileNotFoundException, IOException{
        service.clearHashMap();
        view.displayCreateOrderBanner();

        //get orderDate from user
        String orderDate = view.getOrderDate();
        
        //makes order date to valid text file and creates new file if needed
        String orderFile = service.makeOrderText(orderDate);
        service.fileMake(orderFile);
        
        //get customer name from user
        Order newOrder = view.getCustomerName();
        
        //generates and suto sets order number to new order
        List<Order> orderList = service.retrieveAllOrders(orderFile);
        newOrder = service.autoGenOrderNum(orderList,newOrder);
        
        //get state from user and sets info to order
        List<String> stateList = service.retrieveAllStates();
        String state = view.getState(stateList);
        newOrder = service.stateInfoEnter(newOrder,state);
        
        //display all products to user
        List<Product> productList = service.retrieveAllProducts();
        view.displayProductList(productList);
        
        //get product from user and set infor into order
        List<String> productNames = service.retrieveAllProductNames();
        String product = view.getProduct(productNames);
        newOrder = service.productInfoEnter(newOrder, product);
        
        //get area from user
        view.getAreaInfo(newOrder);
        
        //calculate cost values
        service.orderCalculations(newOrder);
        
        //get user approval
        String approve = view.getApproval(newOrder);
        if(approve.equalsIgnoreCase("yes")){
            service.addOrder(orderFile,newOrder.getOrderNumber(), newOrder);
            view.displayCreateSuccessBanner();
        }    
    }
    
    //prints all orders based on a user entered date
    private void listOrders()throws FlooringPersistenceException, FileNotFoundException{
        service.clearHashMap();
        view.displayAllBanner();
        //get orderDate
        String orderDate = view.getOrderDate();
        String orderFile = service.makeOrderText(orderDate);
        
        //check if orders exist for that date and if they do display all orders for that date
        boolean check = service.fileCheck(orderFile);
        if(check == true){
            List<Order> orderList = service.retrieveAllOrders(orderFile);
            view.displayOrderList(orderList);
        }else{
            view.noOrdersForDate();
        }
    }
    
    //checks if an order exists based on date and order number and if it does it lets user edit some of the information
    private void editOrder()throws FlooringPersistenceException, FileNotFoundException{
        service.clearHashMap();
        //get orderDate
        String orderDate =view.getOrderDate();
        String orderFile = service.makeOrderText(orderDate);
        //check to make sure file exists and if so proceed
        boolean fileCheck = service.fileCheck(orderFile);
        if(fileCheck==true){
            
            int orderNum = view.getOrderNum();
            boolean validOrder = service.orderNumCheck(orderFile,orderNum);
            if(validOrder == false){
                view.noSuchOrder();
            }
            
            //if order exists retrive it for editing and let user input new name
            Order updatedOrder = service.retrieveOrder(orderFile, orderNum);
            view.editOrderCustomer(updatedOrder);
                
                
            //get state to potentially edit
            List<String> stateList = service.retrieveAllStates();
            String state = view.editOrderState(updatedOrder,stateList);
            updatedOrder = service.stateInfoEnter(updatedOrder,state);
                
            //display all products to user if order valid
            if(validOrder == true){
                List<Product> productList = service.retrieveAllProducts();
                view.displayProductList(productList);
            }
        
            //get product to potentially edit
            List<String> productNames = service.retrieveAllProductNames();
            String product = view.editOrderProduct(updatedOrder,productNames);
            updatedOrder = service.productInfoEnter(updatedOrder, product);
                
            //get user input for new area
            view.editOrderArea(updatedOrder);
                
            //calculate new costs with updated data
            service.orderCalculations(updatedOrder);
                
            //get approval and potentially finalize changes
            String approve = view.getApproval(updatedOrder);
            if(approve.equalsIgnoreCase("yes")){
                service.updateOrder(orderFile, updatedOrder.getOrderNumber(), updatedOrder);
                view.displayEditSuccessBanner();                        
            }
        }else{
            view.noOrdersForDate();
        }
    }
    
    //checks to see if order exists based on entered user date and order number and if so lets the user to remove the order
    private void removeOrder()throws FlooringPersistenceException, FileNotFoundException{
        service.clearHashMap();
        view.displayRemovedOrderBanner();
        //get orderDate
        String orderDate = view.getOrderDate();
        String orderFile = service.makeOrderText(orderDate);
        boolean check = service.fileCheck(orderFile);
        if(check ==true){
            //get order num and check if order exists
            int orderNum = view.getOrderNum();
            boolean validOrder = service.orderNumCheck(orderFile,orderNum);
            //print message if no order exists
            if(validOrder == false){
                view.noSuchOrder();
            }
            //get approval to finalize removal if order exists 
            Order removedOrder = service.retrieveOrder(orderFile, orderNum);
            String approve = view.getApproval(removedOrder);
            if(approve.equalsIgnoreCase("yes")){
                service.deleteOrder(orderFile, orderNum);
                view.displayRemoveResult(removedOrder);
            }     
        }else{
            view.noOrdersForDate();
        }
    }
    
    private void unknownCommand(){
        view.displayUnknownCommandBanner();
    }
    
    private void exitMessage(){
        view.displayExitBanner();
    }
}
