package com.seller.quickbuy.QuickBuyApp.config;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Properties;
import javax.crypto.Cipher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.seller.quickbuy.QuickBuyApp.utils.QuickBuyUtils;
import oracle.jdbc.datasource.OracleDataSource;

@Configuration
public class CloudConnectionConfig {


	  public static void fail(String msg){
	    System.err.println(String.join("", Collections.nCopies(20, "*")));
	    System.err.println(msg);
	    System.err.println(String.join("", Collections.nCopies(20, "*")));
	    System.exit(1);
	  }
	  
	  @Bean
	  @Primary
	  public OracleDataSource dataSource(){
	    System.out.println("JDBC Driver Version:" + oracle.jdbc.OracleDriver.getDriverVersion());
	    if (!oracle.jdbc.OracleDriver.getDriverVersion().startsWith("19.")) {
	      fail(" DRIVER TOOO OLD!!!");
	    }
	    int maxKeySize = 0;
	    try {
	      maxKeySize = Cipher.getMaxAllowedKeyLength("AES");
	    } catch (NoSuchAlgorithmException e) {
	    }
	    if (maxKeySize < 129 ) {
	      fail(" JCE Policy not unlimited!!!");      
	    }
//	    String cp = System.getProperty("java.class.path");
//	    String[] cpFiles = {"ojdbc8.jar","oraclepki.jar","osdt_cert.jar","osdt_core.jar"};
//	    for (String file:cpFiles){
//	      if ( cp.indexOf(file) == -1 ){
//	        fail("CLASSPATH Missing:" + file);
//	      }
//	    }
//	    String unzippedWalletLocation = "D:\\jar\\Himanshu\\Wallet_DB202009020830";
//	    String conString = "jdbc:oracle:thin:@db202009020830_tp";
	    Properties props = new Properties();
	    props.setProperty("oracle.net.wallet_location", QuickBuyUtils.getPropertyValue("COM.UTIL.UNZIP.LOCATION"));
	    props.setProperty("oracle.net.tns_admin", QuickBuyUtils.getPropertyValue("COM.UTIL.UNZIP.LOCATION"));
	    props.setProperty("OracleConnection.CONNECTION_PROPERTY_TNS_ADMIN", QuickBuyUtils.getPropertyValue("COM.UTIL.UNZIP.LOCATION"));
	    props.setProperty("javax.net.ssl.trustStore","truststore.jks");
	    props.setProperty("javax.net.ssl.trustStorePassword","<password>");
	    props.setProperty("javax.net.ssl.keyStore","keystore.jks");
	    props.setProperty("javax.net.ssl.keyStorePassword","<password>");
	    props.setProperty("oracle.net.ssl_server_dn_match","true");    
	    props.setProperty("oracle.net.ssl_version","1.2");
	    props.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
	    System.out.println(props.getProperty("oracle.net.tns_admin"));
	    System.out.println(props.getProperty("javax.net.ssl.trustStore"));
	    System.out.println(props.getProperty("javax.net.ssl.trustStorePassword"));
	    System.out.println(props.getProperty("javax.net.ssl.keyStore"));
	    System.out.println(props.getProperty("oracle.net.ssl_server_dn_match"));
	    System.out.println(props.getProperty("hibernate.dialect"));
	    props.setProperty("user", "QUICKBUY_UAT");
	    props.setProperty("password", "Oracle123!@#");
	    
	    OracleDataSource dataSource = null;
		try {
			dataSource = new oracle.jdbc.pool.OracleDataSource();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    try { 
	          	dataSource.setConnectionProperties(props);
	    	    dataSource.setURL(QuickBuyUtils.getPropertyValue("COM.CONNECTION.UTIL"));
	    	    

	    } catch (Exception e){
	      e.printStackTrace();
	      fail(e.getLocalizedMessage());
	    }
		return dataSource;
	  }

}
