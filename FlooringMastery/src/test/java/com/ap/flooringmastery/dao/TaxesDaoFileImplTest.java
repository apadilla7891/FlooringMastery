package com.ap.flooringmastery.dao;

import com.ap.flooringmastery.model.Order;
import com.ap.flooringmastery.service.FlooringPersistenceException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Andy Padilla
 */
public class TaxesDaoFileImplTest {
    TaxesDao testDao;
    
    public TaxesDaoFileImplTest() {
    }
   
    @BeforeEach
    public void setUp() {
        testDao = new TaxesDaoFileImpl();
    }
 

    @Test
    public void testGetAllStateNames() throws FlooringPersistenceException, FileNotFoundException {
        List<String> testList = testDao.getAllStates();
        
        boolean contains = testList.contains("KY");
        boolean notContained = testList.contains("soap");
        
        assertTrue(contains, "The list should contain KY");
        assertFalse(notContained, "the list should not contain soap");
    }
    
    @Test
    public void testStateDataEnter() throws FileNotFoundException, FlooringPersistenceException{
        Order orderOne = new Order();
        orderOne.setState("TX");
        String temp = "4.45";
        BigDecimal tempTax = new BigDecimal(temp);
        tempTax = tempTax.setScale(2,RoundingMode.HALF_UP);
        orderOne.setTaxRate(tempTax);
        Order testOrder = new Order();
        
        testOrder = testDao.stateDataEnter(testOrder, "TX");
        
        assertEquals(testOrder.getState(),orderOne.getState(),"states should both be TX");
        assertEquals(testOrder.getTaxRate(), orderOne.getTaxRate(), "tax rate should both be 4.45");
    }
}
