package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class LoadXMLFile {

	public static void main(String[] args) {
		// XML파일 읽어오기 => FileInputStream, Properties
		
		try {
			Properties prop = new Properties();	
			// driver.xml파일을 읽기 위한 스트림
			FileInputStream fis = new FileInputStream("driver.xml");

			// 스트림에 연결된 파일명에 해당하는 파일을 모두 읽어온 후 P
			// Properties타입 객체에 str,str 형태로 저장
		
			prop.loadFromXML(fis);
			// prop.getProperty("key") => 해당하는 String타입의 value값을 내어줌
			// 값 getProperty(키)
			
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String userName = prop.getProperty("userName");
			String password = prop.getProperty("password");			
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, userName, password);
			
			System.out.println(conn);
			
		} 
		
		catch (Exception e) {
			e.printStackTrace();
		}
		

		
	}

}
