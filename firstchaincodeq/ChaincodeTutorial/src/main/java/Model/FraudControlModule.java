package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import services.FraudValueImpl;

public class FraudControlModule {


	public ArrayList readFile() {

		BufferedReader br = null;
		FraudValueImpl record = null;
		ArrayList<FraudValueImpl> recordList=new ArrayList<FraudValueImpl>();
		String effectiveDate = null;

		try {
			br = new BufferedReader(new FileReader("C:\\IAD\\firstchaincode\\ChaincodeTutorial\\src\\main\\java\\resources\\testchain.txt"));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(";");
				record = new FraudValueImpl();
				//Do necessary work with the values, here we just print them out
				if(values != null)
				{
					for (String str : values) {


						if(!isBlank(values[0]))
						{
							record.setSSUID(values[0]);
						}
						if(!isBlank(values[1]))
						{
							record.setDescription(values[1]);
						}
						if(!isBlank(values[2]))
						{effectiveDate = values[2];
						record.setFrauddate(effectiveDate);
						}
						if(!isBlank(values[3]))
						{
							record.setReportingFIID(values[3]);
						}
						if(!isBlank(values[4]))
						{
							record.setOriginFIID(values[4]);
						}
					}
					recordList.add(record);
					//System.out.println("record.toString()---"+record.toString());
				}
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return recordList;
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	} 
}
