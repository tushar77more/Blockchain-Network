package com.javadb.example;
 
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.s1.arch.utilities.S1Date;
 
public class ChainTest {
 
    public void readFile() {
 
        BufferedReader br = null;
        FraudRecord record = null;
        String effectiveDate = null;
 
        try {
            br = new BufferedReader(new FileReader("C://file//testchain.txt"));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                record = new FraudRecord();
                //Do necessary work with the values, here we just print them out
                if(values != null)
                {
                for (String str : values) {
                	
                   // System.out.println(str);
                	if(!StringUtils.isBlank(values[0]))
                    record.setSSUID(values[0]);
                	if(!StringUtils.isBlank(values[1]))
                    record.setDescription(values[1]);
                	if(!StringUtils.isBlank(values[2]))
                    effectiveDate = values[2];
                    record.setFrauddate(prepareDate(effectiveDate));
                    if(!StringUtils.isBlank(values[3]))
                    record.setReportingFIID(values[3]);
                    if(!StringUtils.isBlank(values[4]))
                    record.setOriginFIID(values[4]);
                }
                System.out.println("record.toString()---"+record.toString());
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
    }
 
    public static void main(String[] args) {
    	ChainTest test = new ChainTest();
        test.readFile();
    }
    
    public S1Date prepareDate(String effectiveDate)
    {
    	S1Date effDate = null;
    	if (StringUtils.isNumeric(effectiveDate)){
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
				df.setLenient(false);
				effDate = new S1Date(df.parse(effectiveDate));
			} catch (Exception e) {
				//throw new UploadParserException("Effective Date is invalid  (Record 1, Positions 2-9)", e);
				// per Kelley if the effective date is invalid then use today
				effDate = null;
			}
		}
		
		if (effDate != null) {
			//check that effective date is valid
			S1Date today = new S1Date();
			if (effDate.before(today)) {
				// before today bad
				effDate = null;
			} else {
				GregorianCalendar futureDate = today.getCalendar();
				futureDate.add(GregorianCalendar.DATE, 180);
				S1Date future = new S1Date(futureDate.getTime());
				if (effDate.after(future)) {
					// it can't be more than 180 days in the future
					effDate = null;
				}
			}
		}
		if (effDate == null) {
			effDate = new S1Date();
		}
		
		return effDate;
    }
}