package com.ap.flooringmastery.dao;

import com.ap.flooringmastery.model.Order;
import com.ap.flooringmastery.model.Product;
import com.ap.flooringmastery.service.FlooringPersistenceException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
public class ProductsDaoFileImpl implements ProductsDao{
    
    //product type file stuff
    public final String PRODUCT_FILE;
    private Map<String,Product> productList = new HashMap<>();
    public static final String DELIMITER = ",";
    
    public ProductsDaoFileImpl(){
        PRODUCT_FILE = "Products.txt";
    }
    
    public ProductsDaoFileImpl(String productsTextFile){
        PRODUCT_FILE = productsTextFile;
    }
    
    //gets all product types to print to user
    @Override
    public List<Product> getAllProducts() throws FlooringPersistenceException, FileNotFoundException {
        loadProducts();
        return new ArrayList<Product>(productList.values());
    }
    
    //gets all product names 
    @Override
    public List<String> getAllProductNames() throws FlooringPersistenceException, FileNotFoundException {
        loadProducts();
        return new ArrayList<String>(productList.keySet());
    }
    
    
    //adds valid product data to object
    @Override
    public Order productDataEnter(Order order, String product) throws FlooringPersistenceException, FileNotFoundException{
        loadProducts();
        String productType = productList.get(product).getProductType();
        order.setProductType(productType);
        order.setCostPerSquareFoot(productList.get(product).getCostPerSquareFoot());
        order.setLaborCostPerSquareFoot(productList.get(product).getLaborCostPerSquareFoot());
        return order;
    }
    
    
        //Product unmarshall/load below
     private Product unmarshallProduct(String productAsText){
    // productAsText gets a line from the file
    // it is in the following format
    // product type,costPerSquareFoot,laborCostPerSquareFoot
    //  [0]                 [1]         [2]     
        String[] productTokens = productAsText.split(DELIMITER);

    // productType is in index 0 of the array.
        String productType = productTokens[0];

    // create a new product object using the constructor and extracted title from file
        Product productFromFile = new Product();

     //using setters, manually set the values.
     
    // Index 0 - productType
        productFromFile.setProductType(productType);
    
    // Index 1 - costPerSquareFoot
        Double unmarshallNum = Double.parseDouble(productTokens[1]);
        BigDecimal tempNum = new BigDecimal(unmarshallNum).setScale(2, RoundingMode.HALF_UP);
        productFromFile.setCostPerSquareFoot(tempNum);

    // Index 2 - laborCostPerSquareFoot
        unmarshallNum = Double.parseDouble(productTokens[2]);
        tempNum = new BigDecimal(unmarshallNum).setScale(2, RoundingMode.HALF_UP);
        productFromFile.setLaborCostPerSquareFoot(tempNum);

    // return created product
        return productFromFile;
    }
     
    //load products to list
    private void loadProducts() throws FlooringPersistenceException, FileNotFoundException {
        Scanner scanner;

        try {
            // Creates Scanner for reading the file
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            //catches the FileNotFoundException and translates it to the FlooringPersistenceException created
            throw new FlooringPersistenceException("Error Product type List data could not be loaded into memory.", e);
        }
        //skips header
        scanner.nextLine();
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentProduct holds the most recent order unmarshalled
        Product currentProduct;
        // Goes through PRODUCT_FILE line by line, decoding each line into a product object by calling the unmarshallProduct method.
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a product
            currentProduct = unmarshallProduct(currentLine);

            // Following what was done earlier we will us the Product type as the map key for the product object.
            productList.put(currentProduct.getProductType(), currentProduct);
        }
        // close scanner
        scanner.close();
    }

}
