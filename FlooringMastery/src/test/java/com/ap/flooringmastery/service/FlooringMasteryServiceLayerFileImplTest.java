package com.ap.flooringmastery.service;

import com.ap.flooringmastery.model.Order;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Andy Padilla
 */
public class FlooringMasteryServiceLayerFileImplTest {
    private FlooringMasteryServiceLayer service;
    
    public FlooringMasteryServiceLayerFileImplTest() {
        //FlooringMasteryDao dao = new FlooringMasteryDaoStubImpl();
        //service = new FlooringMasteryServiceLayerFileImpl(dao);
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", FlooringMasteryServiceLayer.class);
    }
    

    @Test
    public void testCreateValidOrder() {
        Order order = new Order();
        order.setOrderNumber(0);
        order.setCustomerName("test");
        order.setState("KY");
        BigDecimal testArea = new BigDecimal("101.00");
        order.setArea(testArea);
        try{
            service.addOrder("", 0, order);
        }catch(FlooringPersistenceException| FileNotFoundException e){
            fail("order was valid no exception should have been thrown");
        }
    }
    
    @Test
    public void testGetAllStudents() throws Exception{
        Order order = new Order();
        order.setOrderNumber(0);
        order.setCustomerName("test");
        order.setState("KY");
        BigDecimal testArea = new BigDecimal("101.00");
        order.setArea(testArea);
        
        assertEquals(1, service.retrieveAllOrders("").size(), "should only have one order");
        
        assertTrue(service.retrieveAllOrders("").contains(order),"should contain order zero");
    }
    
    @Test
    public void testGetOrder() throws Exception{
        Order order = new Order();
        order.setOrderNumber(0);
        order.setCustomerName("test");
        order.setState("KY");
        BigDecimal testArea = new BigDecimal("101.00");
        order.setArea(testArea);
        
        Order shouldBeSame = service.retrieveOrder("", 0);
        assertNotNull(shouldBeSame, "order 0 should not be null");
        assertEquals(order, shouldBeSame, "both orders should be the same");
        
        Order shouldBeNull = service.retrieveOrder("", 4);
        assertNull(shouldBeNull, " order doesnt exist");
    }
    
    @Test
    public void testRemoveOrder() throws Exception{
        Order order = new Order();
        order.setOrderNumber(0);
        order.setCustomerName("test");
        order.setState("KY");
        BigDecimal testArea = new BigDecimal("101.00");
        order.setArea(testArea);
        
        Order shouldBeOrder = service.deleteOrder("", 0);
        assertNotNull(shouldBeOrder, " removing order 0 should not be null");
        assertEquals(order, shouldBeOrder,"both orders should be the same");
        
        Order shouldBeNull = service.deleteOrder("", 4);
        assertNull(shouldBeNull, " removing order 4 should be null");
    }
    
    @Test
    public void testCalculations() throws Exception{
        Order order = new Order();
        order.setOrderNumber(0);
        order.setCustomerName("test");
        order.setState("KY");
        BigDecimal testArea = new BigDecimal("100.00");
        order.setArea(testArea);
        
        BigDecimal testTaxRate = new BigDecimal("5.00");
        order.setTaxRate(testTaxRate);
        BigDecimal testCostPerSF = new BigDecimal("1.00");
        order.setCostPerSquareFoot(testCostPerSF);
        BigDecimal testLaborCostPerSF = new BigDecimal("4.00");
        order.setLaborCostPerSquareFoot(testLaborCostPerSF);
        
        //correct callculations
        BigDecimal materialCost = new BigDecimal("100.00");
        BigDecimal laborCost = new BigDecimal("400.00");
        BigDecimal Tax = new BigDecimal("25.00");
        BigDecimal Total = new BigDecimal("525.00");
        
        order = service.orderCalculations(order);
        
        
        assertEquals(order.getMaterialCost(),materialCost,"should be equal");
        assertEquals(order.getLaborCost(),laborCost,"should be equal");
        assertEquals(order.getTax(),Tax,"should be equal");
        assertEquals(order.getTotal(),Total,"should be equal");
    }
}
