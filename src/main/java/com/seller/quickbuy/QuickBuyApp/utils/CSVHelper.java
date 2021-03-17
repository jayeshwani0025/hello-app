package com.seller.quickbuy.QuickBuyApp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.seller.quickbuy.QuickBuyApp.repository.Importable;

public class CSVHelper {

	public static String TYPE = "text/csv";

	public static boolean hasCSVFormat(MultipartFile file) {
		if (TYPE.equals(file.getContentType()) || file.getContentType().equals("application/vnd.ms-excel")) {
			return true;
		}

		return false;
	}

	public static List<Importable> csvImport(MultipartFile file, Class entityClass, int id) {

//		  if(!(entityClass.isInstance(Importable.class))) {
//			 throw new RuntimeException("Provided class is not importable");
//		  }
		if (isFileEmpty(file)) {
			return null;
		}

		List<Importable> importlist = null;
		try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

			ColumnPositionMappingStrategy<Importable> beanStrategy = new ColumnPositionMappingStrategy<Importable>();
			beanStrategy.setType(entityClass);

			if (id == 1) {

				beanStrategy.setColumnMapping(new String[] { "categoryId", "productDescription", "productIcon",
						"productName", "createdBy", "lastUpdatedBy", "lastUpdatedLogin" });
			}

			if (id == 2) {

				beanStrategy.setColumnMapping(new String[] { "sellerName", "sellerAddress", "sellerPointOfContact",
						"sellerContactNumber", "sellerEmailAddress", "city", "state", "country", "zipCode", "createdBy",
						"lastUpdatedBy", "lastUpdatedLogin" });

			}

			if (id == 3) {

				beanStrategy.setColumnMapping(new String[] { "createdBy", "lastUpdatedBy", "lastUpdatedLogin","sellerPrice",
						                                     "quantity", "productName" ,"location","sellerName"});
				
				
			}

			CsvToBean<Importable> csvToBean = new CsvToBeanBuilder(reader).withSkipLines(1).withType(entityClass).withSeparator(',')
					.withIgnoreLeadingWhiteSpace(true).withMappingStrategy(beanStrategy).build();

			importlist = csvToBean.parse();
		}

		catch (IOException e) {

			e.printStackTrace();
		}

		return importlist;
	}

	public static boolean isFileEmpty(MultipartFile file) {
		BufferedReader reader = null;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			String line = null;

			String ls = System.getProperty("line.separator");
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line.trim());

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return stringBuilder.toString().trim().length() == 0;
	}
}