package com.makotojava.learn.blockchain.chaincode;

import org.hyperledger.java.shim.ChaincodeStub;

public class testCode {

	  public static final String CONTRACT_ID = "ChaincodeLogSmartContract";
	  public static final String KEY_PREFIX = CONTRACT_ID + "-CLSC-";
	  
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	 private boolean toCheckNode(String[] args) {
		  boolean check=false;
		  
		    for (String key : args) {
		      String logKey = KEY_PREFIX + key;
	System.out.println(" logKey"+logKey);
		     // String value = stub.getState(logKey);
		      System.out.println(" logKey"+logKey);
		      /*if(value!=null && value.length()>0){
		    	  check= true;
		      }else{
		    	  check= false;
		      }*/
		     
		    }
		    
		 return check;
	  }
	  

}
