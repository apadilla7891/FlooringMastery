package com.ap.flooringmastery.view;

import com.ap.flooringmastery.model.Order;
import com.ap.flooringmastery.model.Product;
import com.ap.flooringmastery.userio.UserIO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public class FlooringMasteryView {
    
    private UserIO io;
    public FlooringMasteryView(UserIO io){
        this.io = io;
    }
    
    public int printMenuAndGetSelection(){
        io.print("Main Menu");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Quit");
        
        return io.readInt("Please select from the above choices.",1,5);
    }
    
    //gets name and area from user
    public Order getAreaInfo(Order currentOrder){
        //checks that area is atleas 100 and converts it into a big decimal
        Double areaTemp = io.readDouble("Please enter the area (ust be atleast 100sq ft)", 100, 999999);
        String areaTemp2 = areaTemp.toString();
        BigDecimal area = new BigDecimal(areaTemp2);
            
        currentOrder.setArea(area);
        return currentOrder;
    }
    
    //get customer name for a new order
    public Order getCustomerName(){
        boolean isEmpty = true;
        String customerName="";
        String productType="";
        Order currentOrder = new Order();
        
        //customer name cannot be left blank
        while(isEmpty){
            customerName = io.readString("Please enter customer name");
            if(customerName.isBlank() == false){
                isEmpty = false;
            }else{
                System.out.println("Error customer name is blank please try again.");
            }
        }
        currentOrder.setCustomerName(customerName);
        return currentOrder;
    }
    
    //get state from user input
    public String getState(List<String> stateList){
        boolean stateBad = true;
        String state="";
        //currently checks to make sure state is two characters
        while(stateBad){
            state = io.readString("Please enter State Abbreviation (i.e. TX)");
            state = state.toUpperCase();
            
            if(state.length() != 2){
                io.print("Error state abrreviation is the wrong size, please try again");
            }else if(stateList.contains(state)==false){
                io.print("Sorry we do not sell to that state");
            }else{
                stateBad = false;
            }
        }
        return state;
    }
    
    //class to get state to edit
    public String editOrderState(Order order, List<String> stateList){
        boolean stateBad=true;
        String state = "";
        if(order !=null){    
            io.print("Current State: " + order.getState());
            while(stateBad){
                state = io.readString("Please enter State abrreviation:(Leave blank to keep same)");
                state = state.toUpperCase();
                if(state.length() > 2 || state.length()==1){
                    io.print("Error state abrreviation is the wrong size, please try again");
                }else if(state.isBlank() != true){
                    if(stateList.contains(state)==false){
                        io.print("Sorry we do not sell to that state");
                    }else if(stateList.contains(state)==true){
                        stateBad = false;
                    }    
                }else{
                    state = order.getState();
                    stateBad=false;
                }
            }
        }    
        return state;
    }
    //get product type from user input
    public String getProduct(List<String> productNames){
        boolean productBad = true;
        String product="";
        //currently checks to make sure product isnt empty and makes first letter uppercase and the rest lower case
        while(productBad){
            product = io.readString("Please enter product type");
            
            if(product.length() == 0){
                System.out.println("Error product type empty, please try again");
            }else{
                product = product.toLowerCase();
                product = product.substring(0, 1).toUpperCase() + product.substring(1);
                if(productNames.contains(product)==false){
                    io.print("Invalid input we do not sell that product");
                }else if(productNames.contains(product)==true){
                    productBad = false;
                }
            }
        }
        return product;
    }
    
    //class to get product to edit
    public String editOrderProduct(Order order,List<String> productNames){
        boolean productBad=true;
        String product = "";
        if(order !=null){
        io.print("Current Product Type: " + order.getProductType());
        while(productBad){
                product = io.readString("Please enter Product Type:(Leave blank to keep same");
                
                if(product.isBlank() != true){
                    product = product.toLowerCase();
                    product = product.substring(0, 1).toUpperCase() + product.substring(1);
                    if(productNames.contains(product)==false){
                        io.print("Invalid input we do not sell that product");
                    }else if(productNames.contains(product)==true){
                        productBad = false;
                }
                }else{
                    product = order.getProductType();
                    productBad=false;
                }
            }
        }
        return product;
        
    }
    //displays all orders from list passed through it using lambda stream
    public void displayOrderList(List<Order> orderList){
        
        //display orders assuming its good
        /*for(Order currentOrder: orderList){
            String orderInfo = String.format("#%d :Customer Name: %s State: %s Material: %s Total Cost:$ %s",
                    currentOrder.getOrderNumber(),
                    currentOrder.getCustomerName(),
                    currentOrder.getState(),
                    currentOrder.getProductType(),
                    currentOrder.getTotal().toString());
            io.print(orderInfo);
        }*/
        orderList.stream().forEach((p) -> System.out.println(p.getOrderNumber() + " Customer Name: " + p.getCustomerName() + " State: " + p.getState() + " Product Type: " + p.getProductType() + " Area: " + p.getArea().toString() + " Total:$" + p.getTotal().toString()));
        io.readString("Please hit enter to continue");
    }
    
    //display all products from a list using lambda stream
    public void displayProductList(List<Product> productList){
        
        //display products 
        /*for(Product currentProduct: productList){
            String productInfo = currentProduct.getProductType() + " Cost per sqft:$" + currentProduct.getCostPerSquareFoot().toString() + " Labor cost per sqft:$" + currentProduct.getLaborCostPerSquareFoot().toString();
            io.print(productInfo);
        }*/
        productList.stream().forEach((p) -> System.out.println(p.getProductType() + " Cost per sqft:$" + p.getCostPerSquareFoot().toString() + " Labor cost per sqft:$" + p.getLaborCostPerSquareFoot().toString()));
    }
    
    //allows user to put in info to change order info. If left blank then info remains the same
    public void editOrderCustomer(Order order) {
        if (order != null) {
            io.print("You are editing the information for " + order.getOrderNumber());
            io.print("Current Customer Name: " + order.getCustomerName());
            String customerName = io.readString("Please enter new customer name (leave blank to keep the same)");
            if(customerName.isBlank() != true){
                order.setCustomerName(customerName);
            }  
        } 
    }
    
    //allows user to edit order area
    public void editOrderArea(Order order){
        if(order!=null){    
            io.print("Current Area: " + order.getArea().toString());
            //do extra convert for area in order to make sure bigdecimal is fine
            Double areaTemp = io.readDouble("Please enter the area (must be atleast 100sq ft. If you would like to keep it the same please enter 99)", 99, 999999);
            String areaTemp2 = areaTemp.toString();
            if(areaTemp != 99){
                BigDecimal area = new BigDecimal(areaTemp2);
                order.setArea(area);
            }
        }    
    }
   
    public void orderToString(Order order){
        io.print(order.toString());
    }
    
    //get order number class for edit/remove
    public Integer getOrderNum(){
        int orderNum = io.readInt("Please enter Order Number");
        return orderNum;
    }

   
    
    //get a string containing orderDate
    public String getOrderDate(){
        String orderDate = "";
        boolean notValid = true;
        while(notValid){
            orderDate = io.readString("Please enter order date in the format of MMDDYYYY, it must be a future date");
            //counter used to check that all chars are nums
            int totalNum = 0;
            for(int i = 0; i < orderDate.length();i++){
                if(orderDate.charAt(i) >= '0' && orderDate.charAt(i) <='9'){
                    totalNum++;
                }
            }
            if(orderDate.length() != 8){
                io.print("Incorrect size please try again");
                continue;
            }
            if(totalNum !=8){
                io.print("Characters other than numbers detected please try again");
                continue;
            }
            if(totalNum == 8){
                int month = Integer.parseInt(orderDate.substring(0, 2));
                int day = Integer.parseInt(orderDate.substring(2, 4));
                if(month > 12 || month == 0 || day == 0){
                    io.print("Invalid input, there are only 12 months in a year");
                    continue;
                }else if((month == 1|| month == 3 || month == 5|| month == 7 || month == 8 || month == 10 || month ==12) && day >31){
                    io.print("Invalid input, there are only 31 days in this month");
                    continue;
                }else if(month ==2 && day >28){
                    io.print("Invalid input, there are only 28 days in this month");
                    continue;
                }else if((month == 4||month ==6 || month == 9 || month ==11) && day >30){
                    io.print("Invalid input, there are only 30 days in this month");
                    continue;
                }
            }
            LocalDate oDate;
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
            oDate = LocalDate.parse(orderDate, formatter);
            String formattedCurrent = currentDate.format(formatter);
            currentDate = LocalDate.parse(formattedCurrent, formatter);
            if(oDate.isBefore(currentDate)){
                io.print("Invalid input, date cannot be in the past");
            }else if(oDate.isEqual(currentDate)){
                io.print("Invalid input, date cannot be today");
            } else{
                    notValid = false;
            }
        }
        return orderDate;
    }
    
    //gets user approval before saving any changes
    public String getApproval(Order order){
        boolean notValid = true;
        String approval = "";
        if(order != null){
            //print out updated info
            io.print("Current information");
            io.print("Customer Name: " + order.getCustomerName());
            io.print("State: " + order.getState());
            io.print("Product Type: " + order.getProductType());
            io.print("Area: " + order.getArea().toString());
            io.print("Total Cost: $" + order.getTotal().toString());
            while(notValid){
                approval = io.readString("If you would like to confirm please enter yes or no");
                if(approval.equalsIgnoreCase("yes")||approval.equalsIgnoreCase("no")){
                    notValid = false;
                }else{
                    io.print("Invalid input please try again");
                }
            }
        }
        return approval;
    }
    
    //banners
    public void displayCreateOrderBanner(){
        io.print("Create New Order:");
    }
    
    public void displayCreateSuccessBanner(){
        io.readString("Order successfully created. Please hit enter to continue");
    }
    
    public void displayAllBanner(){
        io.print("Displaying all orders");
    }
    
    public void displayEditSuccessBanner(){
        io.readString("Order successfully edited. Please hit enter to continue");
    }
    
    public void noSuchOrder(){
        io.print("No such order.");
    }
    
    public void noOrdersForDate(){
        io.print("No orders for entered date");
    }
    
    public void displayRemovedOrderBanner(){
        io.print("Remove Order");
    }
    
    public void displayRemoveResult(Order removedOrder){
        if(removedOrder != null){
            io.print("Order successfully removed.");
        }else{
            io.print("No such order.");
        }
        io.readString("Please hit enter to continue");
    }
    
    public void displayExitBanner(){
        io.print("Good bye!");
    }
    
    public void displayUnknownCommandBanner(){
        io.print("Unknown Command");
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
    
    //outdated code from previous build
     //set order num to new order object -Outdated-
    /*public Order assignOrderNum(Order order, Integer orderNum){
        order.setOrderNumber(orderNum);
        return order;
    }*/
}
