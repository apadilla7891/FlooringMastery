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
public class ProductsDaoFileImplTest {
    
    ProductsDao testDao;
    
    public ProductsDaoFileImplTest() {
    }
    
    
    @BeforeEach
    public void setUp() {
        testDao = new ProductsDaoFileImpl();
    }


    @Test
    public void testGetAllProductNames() throws FlooringPersistenceException, FileNotFoundException {
        List<String> testList = testDao.getAllProductNames();
        
        boolean contains = testList.contains("Wood");
        boolean notContained = testList.contains("soap");
        
        assertTrue(contains, "The list should contain wood");
        assertFalse(notContained, "the list should not contain soap");
    }
    
    @Test
    public void testProductDataEnter() throws FileNotFoundException, FlooringPersistenceException{
        Order orderOne = new Order();
        orderOne.setProductType("Wood");
        String temp = "5.15";
        BigDecimal costTemp = new BigDecimal(temp);
        costTemp = costTemp.setScale(2,RoundingMode.HALF_UP);
        orderOne.setCostPerSquareFoot(costTemp);
        temp = "4.75";
        costTemp = new BigDecimal(temp);
        costTemp = costTemp.setScale(2,RoundingMode.HALF_UP);
        orderOne.setLaborCostPerSquareFoot(costTemp);
        Order testOrder = new Order();
        
        testOrder = testDao.productDataEnter(testOrder, "Wood");
        
        assertEquals(testOrder.getProductType(),orderOne.getProductType(),"product types should be the same");
        assertEquals(testOrder.getCostPerSquareFoot(), orderOne.getCostPerSquareFoot(), "Cost per square foot should be the same");
        assertEquals(testOrder.getLaborCostPerSquareFoot(), orderOne.getLaborCostPerSquareFoot()," labor costs per square foot should be the same");
    }
}
