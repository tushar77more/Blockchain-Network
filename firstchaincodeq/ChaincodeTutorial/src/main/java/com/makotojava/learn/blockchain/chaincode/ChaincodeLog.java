/*
 * Copyright 2017 Makoto Consulting Group, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.makotojava.learn.blockchain.chaincode;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.java.shim.ChaincodeStub;

import Model.FraudControlModule;
import services.FraudValueImpl;

/**
 * 
 * @author jstevenperry
 *
 */
public class ChaincodeLog extends AbstractChaincode {

  private static final Log log = LogFactory.getLog(ChaincodeLog.class);

  public static final String CONTRACT_ID = "ChaincodeLogSmartContract";

  public static final String FUNCTION_LOG = "log";

  public static final String KEY_PREFIX = CONTRACT_ID + "-CLSC-";

  /**
   * The driver method. Every chaincode program must have one.
   * This is invoked to start the chaincode running, and register
   * it with the Fabric.
   * 
   * @param args
   */
  public static void main(String[] args) {
    new ChaincodeLog().start(args);
  }

  /**
   * Returns the unique chaincode ID for this chaincode program.
   */
  @Override
  public String getChaincodeID() {
    return CONTRACT_ID;
  }

  /**
   * Handles initializing this chaincode program.
   * <br/>
   * Caller expects this method to:
   * <ol>
   * <li>Use args[0] as the key for logging.</li>
   * <li>Use args[1] as the log message.</li>
   * <li>Return the logged message.</li>
   * </ol>
   */
  @Override
  protected String handleInit(ChaincodeStub stub, String[] args) {
	  String ret;
	    //
	    // Log the init invocation to the ledger
	    ret = creatingGenesisBlock(stub, args);
	    return ret;
  }
  
  private String creatingGenesisBlock(ChaincodeStub stub, String[] args) {
	  String ret = null;
	  //
	  // Store the log message
	  String logKey = args[0];
	  String logMessage = args[1];
	  log.info("*** Creating Genesis Block  message (K,V) -> (" + KEY_PREFIX + logKey + "," + logMessage + ") ***");
	  stub.putState(KEY_PREFIX + logKey, logMessage.toString());
	  //
	  ret = logMessage;

	  return ret;
  }

  private String handleLog(ChaincodeStub stub, String[] args) {
	    String ret = null;
	    boolean blockExist=false;
	    blockExist=toCheckNode(stub, args);
	    if(!blockExist){
	    	FraudControlModule module = new FraudControlModule();
	    	ArrayList<FraudValueImpl> recordList=module.readFile();
	    	//
	    	// Store the log message
	    	String logKey = args[0];
	    	String logMessage = args[1];
	    	log.info("*** Storing Block in Chain message (K,V) -> (" + KEY_PREFIX + logKey + "," + logMessage + ") ***");
	    	stub.putState(KEY_PREFIX + logKey, recordList.toString());
	    	//
	    	ret = logMessage;
	    }else{
	    	log.info("*** Block message (K,V) -> (" + KEY_PREFIX + args[0] + ") Already exist in Chain***");
	    }
	    return ret;
	  }
  
 private boolean toCheckNode(ChaincodeStub stub, String[] args) {
	  boolean check=false;

	      String logKey = KEY_PREFIX + args[0];
	      String value = stub.getState(logKey);
	      if(value!=null && value.length()>0){
	    	  check= true;
	      }else{
	    	  check= false;
	      }
	    
	 return check;
  }
  
  /**
   * Handles querying the ledger.
   * <br/>
   * Caller expects this method to:
   * <ol>
   * <li>Use args[0] as the key for ledger query.</li>
   * <li>Return the ledger value matching the specified key
   * (which should be the message that was logged using that key).</li>
   * </ol>
   */
  @Override
  protected String handleQuery(ChaincodeStub stub, String[] args) {
	  StringBuilder sb = new StringBuilder();
	    int aa = 0;
	    for (String key : args) {
	      String logKey = KEY_PREFIX + key;
	      if (aa++ > 0) {
	        sb.append(",");
	      }
	      String value = stub.getState(logKey);
	      log.info("*** Query: For Block '" + logKey + ", value is '" + value + "' ***");
	      sb.append(value);
	    }
	    //System.out.println("testing handleInit method");
	    return sb.toString();
  }

  /**
   * Handles other methods applied to the ledger.
   * Currently, that functionality is limited to these functions:
   * <ul>
   * <li>log</li>
   * </ul>
   * <br/>
   * Caller expects this method to:
   * <ol>
   * <li>Use args[0] as the key for logging.</li>
   * <li>Use args[1] as the log message.</li>
   * <li>Return the logged message.</li>
   * </ol>
   */
  @Override
  protected String handleOther(ChaincodeStub stub, String function, String[] args) {
	  String ret;
	    switch (function) {
	    case FUNCTION_LOG:
	      ret = handleLog(stub, args);
	      break;
	    default:
	      ret = "NO HANDLER FOUND FOR FUNCTION '" + function + "'";
	    }
	    return ret;
  }

}
