package com.ap.flooringmastery.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Andy Padilla
 */
public class Order {
    //auto generated
    private int orderNumber;
    //assigned/editable
    private String customerName;
    //assigned/editable
    private String state;
    //retrieved based on state
    private BigDecimal taxRate = new BigDecimal("0");
    //assigned/editable
    private String productType;
    //assigned/editable
    private BigDecimal area = new BigDecimal("0");
    //retrieved based on product type
    private BigDecimal costPerSquareFoot = new BigDecimal("0");
    //retrieved based on product type
    private BigDecimal laborCostPerSquareFoot = new BigDecimal("0");
    //calculated by area * costPerSquareFoot
    private BigDecimal materialCost = new BigDecimal("0");
    //calculated by area * laborCostPerSquareFoot
    private BigDecimal laborCost = new BigDecimal("0");
    //calculated by (materialCost + LaborCost) *(taxRate/100)
    private BigDecimal tax = new BigDecimal("0");
    
    //calculated by (materialCost + laborCost+ tax)
    private BigDecimal total = new BigDecimal("0");

    
    
    //special contructor that takes in order number which in this program is auto generated
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
    
    //getters/setters for each variable
    
    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    
    @Override
    public String toString(){
        return "Order Number: " + orderNumber + " Customer Name: " + customerName + " State: " + state + " Area: " + area + "sqft";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.orderNumber;
        hash = 17 * hash + Objects.hashCode(this.customerName);
        hash = 17 * hash + Objects.hashCode(this.state);
        hash = 17 * hash + Objects.hashCode(this.area);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        return true;
    }

    
    
}
