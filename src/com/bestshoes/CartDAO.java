package com.bestshoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bestshoes.DBConnector;

/**
 * --------------------------------------------- 
 * @author JIEUN KWON (991447941)
 *	
 * TASK : Assignment 3 
 * MVC Modeling - Shoe Product Ordering System
 * 
 * created Date : Nov 25, 2018 
 * modified Date : Nov 25, 2018
 * --------------------------------------------- 
 *
 * Page Task	: CartDAO 
 * 				  all tasks related to access database 'Cart' 
 *
 * references db structure : 
 *
 */ 

public class CartDAO {

	// connection 
	Connection con = null;
	PreparedStatement pst;  
	ResultSet rs = null;
	
 
	// ---------------------------------------------------------
	// 		METHOD addRow()
	// ---------------------------------------------------------
	
	public void addRow( int itemId, String itemName, String customerId, int quantity, double price)throws Exception {
 
		
		// make a query
        String insertQuery = "Insert into cart (itemId,itemName,customerId,quantity,price) "
        		+ "values (?,?,?,?,?)";
        
        
        // db connect
        try{
        	
        	// get connection
		    con = DBConnector.getConnection();
		    pst = con.prepareStatement(insertQuery);
		      
			// set
			pst.setInt(1,itemId);
			pst.setString(2,itemName);
			pst.setString(3,customerId);
			pst.setInt(4,quantity);
			pst.setDouble(5,price);
			
			// execute
            pst.executeUpdate();

            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        	DBConnector.closeConnectionAll(con,pst,null);
        }
    }
	
	
	// ---------------------------------------------------------
	// 		METHOD listCart()
	// ---------------------------------------------------------
	
	public ArrayList<Cart> listCart(String customerId) throws Exception{
		
		// make a query
		String selectQuery = "select cartNo, itemId, itemName, quantity, price from Cart where customerId ='" + customerId + "' ";
		selectQuery = selectQuery + "order by cartNo asc";
        
		// result
		ArrayList<Cart> cartList = new ArrayList<Cart>();
		
		// db connection
		try{
           
		    con = DBConnector.getConnection();
		    pst = con.prepareStatement(selectQuery);
		    
			try {
				rs = pst.executeQuery();
				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				System.out.println(pst.toString());
				e.printStackTrace();
			}
			
			// save all list to ArrayList
			try {
				  
		         while(rs.next())
		  		 {
		        	 
		        	 Cart cart = new Cart();
			 	       
		        	 // store information	
		        	 cart.setCartNo(rs.getInt(1));
		        	 cart.setItemId(rs.getInt(2)); 
		        	 cart.setItemName(rs.getString(3));
		        	 cart.setQuantity(rs.getInt(4));
		        	 cart.setPrice(rs.getDouble(5));
		        	 
		        		 
	        		// add to arraylist 
	        		cartList.add(cart); 
		 	   		 
		  		 }
		         
				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			 
		  
        }catch(Exception e){
                e.printStackTrace();
        }finally{
              
        	DBConnector.closeConnectionAll(con,pst,rs);
        }
		
		// return 
		return cartList;
	}

	
	// ---------------------------------------------------------
	// 		METHOD delRow()
	// ---------------------------------------------------------
	
	public void delRow( int itemId, String customerId)throws Exception {
 
		
		// make a query
        String insertQuery = "delete cart where itemId=? and customerId=?";
        
        
        // db connect
        try{
        	
        	// get connection
		    con = DBConnector.getConnection();
		    pst = con.prepareStatement(insertQuery);
		      
			// set
			pst.setInt(1,itemId); 
			pst.setString(2,customerId);
		 
			// execute
            pst.executeUpdate();

            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        	DBConnector.closeConnectionAll(con,pst,null);
        }
    }
}