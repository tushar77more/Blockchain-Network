package services;

import java.util.Date;

public class FraudValueImpl {

	private String SSUID;
	private String description;
	private String frauddate; 
	private String reportingFIID; //Fraud reported by FI
	private String originFIID; //Fraudlet belong to this FI
	

	/**
	 * getSSUID
	 * @return
	 */
	public String getSSUID() {
		return SSUID;
	}


	/**
	 * getSSUID
	 * @return
	 */
	public void setSSUID(String sSUID) {
		SSUID = sSUID;
	}

	/**
	 * getSSUID
	 * @return
	 */

	public String getDescription() {
		return description;
	}

	/**
	 * getSSUID
	 * @return
	 */

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * getSSUID
	 * @return
	 */

	public String getFrauddate() {
		return frauddate;
	}

	/**
	 * getSSUID
	 * @return
	 */

	public void setFrauddate(String frauddate) {
		this.frauddate = frauddate;
	}

	/**
	 * getSSUID
	 * @return
	 */

	public String getReportingFIID() {
		return reportingFIID;
	}

	/**
	 * setting reporting FI unique ID
	 * @return
	 */

	public void setReportingFIID(String reportingFIID) {
		this.reportingFIID = reportingFIID;
	}

	/**
	 * getting FI unique ID
	 * @return
	 */

	public String getOriginFIID() {
		return originFIID;
	}

	/**
	 * setting FI unique ID
	 * @return
	 */

	public void setOriginFIID(String originFIID) {
		this.originFIID = originFIID;
	}

	/**
	 * String representation of value object
	 * @return
	 */

	public String toString()
	{
		return "{SSUID:"+SSUID+",Description:"+description+",Fraud Date:"+frauddate+",Reporting FI ID:"+reportingFIID+",Originator FI ID:"+originFIID+"}";
		
	}
}
