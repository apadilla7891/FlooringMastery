package com.ap.flooringmastery.dao;

import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.ap.flooringmastery.model.Order;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public class FlooringMasteryDaoFileImplTest {
    
    FlooringMasteryDao testDao;
    
    public FlooringMasteryDaoFileImplTest() {
    }
    
    
    @BeforeEach
    public void setUp() throws IOException {
        String OLTest = "testOrder.txt";
        new FileWriter(OLTest);
        testDao = new FlooringMasteryDaoFileImpl();
    }
    

    @Test
    public void testAddGetOrder() throws Exception{
        Order testOrder = new Order();
        testOrder.setOrderNumber(0);
        testOrder.setCustomerName("qwerty");
        testOrder.setState("CA");
        BigDecimal testArea = new BigDecimal("101.00");
        testOrder.setArea(testArea);
        String OLTest = "testOrder.txt";
        
        testDao.addOrder(OLTest, testOrder.getOrderNumber(), testOrder);
        Order retrievedOrder = testDao.getOrder(OLTest, testOrder.getOrderNumber());
        
        assertEquals(testOrder.getOrderNumber(), retrievedOrder.getOrderNumber(), "Checking order num");
        assertEquals(testOrder.getCustomerName(), retrievedOrder.getCustomerName(),"Checking customer name");
        assertEquals(testOrder.getState(), retrievedOrder.getState(),"Checking state");
        assertEquals(testOrder.getArea(),retrievedOrder.getArea(),"Checking area");
        
    }
    
    @Test
    public void testAddGetAllOrders() throws Exception{
        Order testOrder = new Order();
        testOrder.setOrderNumber(0);
        testOrder.setCustomerName("qwerty");
        testOrder.setState("CA");
        BigDecimal testArea = new BigDecimal("101.00");
        testOrder.setArea(testArea);
        
        Order testOrder2 = new Order();
        testOrder2.setOrderNumber(1);
        testOrder2.setCustomerName("wasd");
        testOrder2.setState("TX");
        BigDecimal testArea2 = new BigDecimal("254.00");
        testOrder2.setArea(testArea2);
        String OLTest = "testOrder.txt";
        
        testDao.addOrder(OLTest, testOrder.getOrderNumber(), testOrder);
        testDao.addOrder(OLTest, testOrder2.getOrderNumber(), testOrder2);
        
        List<Order> allOrders = testDao.getAllOrders(OLTest);
        
        assertNotNull(allOrders,"The list of orders must not be null");
        assertEquals(2,allOrders.size(),"The list of orders should have 2 orders");
        
        
        assertTrue(allOrders.contains(testOrder),"The list should contain order 0");
        assertTrue(testDao.getAllOrders(OLTest).contains(testOrder2),"The list should contain order 1");
    }
    
    @Test
    public void testRemoveOrder() throws Exception{
        Order testOrder = new Order();
        testOrder.setOrderNumber(0);
        testOrder.setCustomerName("qwerty");
        testOrder.setState("CA");
        BigDecimal testArea = new BigDecimal("101.00");
        testOrder.setArea(testArea);
        
        Order testOrder2 = new Order();
        testOrder2.setOrderNumber(1);
        testOrder2.setCustomerName("wasd");
        testOrder2.setState("TX");
        BigDecimal testArea2 = new BigDecimal("254.00");
        testOrder2.setArea(testArea2);
        String OLTest = "testOrder.txt";
        
        testDao.addOrder(OLTest, testOrder.getOrderNumber(), testOrder);
        testDao.addOrder(OLTest, testOrder2.getOrderNumber(), testOrder2);
        //remove first order
        Order removedOrder = testDao.removeOrder(OLTest, testOrder.getOrderNumber());
        
        assertEquals(removedOrder, testOrder, " The removed order should be order 0");
        
        List<Order> allOrders = testDao.getAllOrders(OLTest);
        
        assertNotNull(allOrders,"The list of orders must not be null");
        assertEquals(1,allOrders.size(),"The list of orders should have 1 orders");
        
        assertFalse(allOrders.contains(testOrder),"The list shouldnt contain order 0");
        assertTrue(testDao.getAllOrders(OLTest).contains(testOrder2),"The list should contain order 1");
        
        //remove order2
        removedOrder = testDao.removeOrder(OLTest, testOrder2.getOrderNumber());
        
        assertEquals(removedOrder, testOrder2, " The removed order should be order 1");
        
        allOrders = testDao.getAllOrders(OLTest);
        assertTrue(allOrders.isEmpty()," The retrieved list should now be empty");
        
        //makesure both orders no longer exists
        removedOrder = testDao.removeOrder(OLTest, testOrder.getOrderNumber());
        assertNull(removedOrder," should now be mull since order already removed");
        removedOrder = testDao.removeOrder(OLTest, testOrder2.getOrderNumber());
        assertNull(removedOrder," should now be mull since order already removed");
    }
}
