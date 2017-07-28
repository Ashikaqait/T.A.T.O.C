package Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

/*import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class config_reader {

	Properties prop = new Properties();
	FileInputStream input = null;

	public Object browser(String optionKey) throws IOException {

		ClassLoader classLoader = getClass().getClassLoader();
		java.net.URL resource = classLoader.getResource("config.properties");
		prop.load(new FileReader(new File(resource.getFile())));
		
		
		return prop.getProperty("browserName");
	
public static void main(String[] args)
{
	System.out.println(prop.getProperty("browser"));
}
}
}*/

 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
 

public class config_reader {
	
	
	
	public static  String getPropValues() throws IOException {
		
		 InputStream inputStream;
		  
			
		  Properties prop = new Properties();
			
			File file = new File("D:\\test\\tatoc\\src\\test\\resources\\config1.properties");
			FileReader fr = new FileReader(file);
	
			prop.load(fr);
 
		
			String browserName = prop.getProperty("browserName");
			
 
			System.out.println(browserName);
	
		return browserName;
	}

	
}
