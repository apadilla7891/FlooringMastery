package com.ap.flooringmastery.dao;

import com.ap.flooringmastery.model.Order;
import com.ap.flooringmastery.model.Taxes;
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
public class TaxesDaoFileImpl implements TaxesDao{
    
    //Tax file stuff
    public final String TAX_FILE;
    private Map<String,Taxes> taxList = new HashMap<>();
    public static final String DELIMITER = ",";
    
    public TaxesDaoFileImpl(){
        TAX_FILE= "Taxes.txt";
    }
    
    public TaxesDaoFileImpl(String taxesTextFile){
        TAX_FILE = taxesTextFile;
    }
    
        //get all taxes for state check
    @Override
    public List<String> getAllStates() throws FlooringPersistenceException, FileNotFoundException {
        loadTaxes();
        return new ArrayList<String>(taxList.keySet());
    }

    //adds valid state data to object
    @Override
    public Order stateDataEnter(Order order, String state) throws FlooringPersistenceException, FileNotFoundException{
        loadTaxes();
        String stateName = taxList.get(state).getStateAbbrv();
        order.setState(stateName);
        order.setTaxRate(taxList.get(state).getTaxRate());
        return order;
    }
    
    //Tax unmarshall/load below
     private Taxes unmarshallTax(String taxAsText){
    // taxAsText gets a line from the file
    // it is in the following format
    // stateAbbreviation,stateName,TaxRate
    //  [0]                 [1]       [2]     
        String[] taxTokens = taxAsText.split(DELIMITER);

    // state abbreviation is in index 0 of the array.
        String stateAbbreviation = taxTokens[0];

    // create a new tax object using the constructor and extracted title from file
        Taxes taxFromFile = new Taxes();

     //using setters, manually set the values.
     
    // Index 0 - stateAbbrevation
        taxFromFile.setStateAbbrv(stateAbbreviation);
    
    // Index 1 - state name
        taxFromFile.setStateName(taxTokens[1]);

    // Index 2 - taxrate
        Double unmarshallNum = Double.parseDouble(taxTokens[2]);
        BigDecimal taxRate = new BigDecimal(unmarshallNum).setScale(2, RoundingMode.HALF_UP);
        taxFromFile.setTaxRate(taxRate);

    // return created tax
        return taxFromFile;
    }
    
    //load taxes to list
    private void loadTaxes() throws FlooringPersistenceException, FileNotFoundException {
        Scanner scanner;

        try {
            // Creates Scanner for reading the file
            scanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            //catches the FileNotFoundException and translates it to the FlooringPersistenceException created
            throw new FlooringPersistenceException("Error State Tax List data could not be loaded into memory.", e);
        }
        //skips header
        scanner.nextLine();
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentTax holds the most recent order unmarshalled
        Taxes currentTax;
        // Goes through TAX_FILE line by line, decoding each line into a tax object by calling the unmarshallTax method.
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a tax
            currentTax = unmarshallTax(currentLine);

            // Foloowing what was done earlier we will us the tax abbreviation as the map key for the tax object.
            taxList.put(currentTax.getStateAbbrv(), currentTax);
        }
        // close scanner
        scanner.close();
    }  
}
