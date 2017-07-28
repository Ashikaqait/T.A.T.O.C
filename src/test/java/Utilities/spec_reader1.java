package Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class spec_reader1 {
	static String str;
	static String[] words;
	static WebElement element;
	static Scanner sc = new Scanner(System.in);

	static JavascriptExecutor js;
	
	public static WebElement locate(WebDriver driver, String str) throws IOException {

		js = (JavascriptExecutor) driver;
		File file = new File("D:\\test\\tatoc\\src\\main\\resources\\locators.spec");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;

		outer:while ((line = br.readLine()) != null) {

			if (line.contains(str)) {

				words = line.split(":");
			
				
				String str1 = "id";
				String str2 = "xpath";
				String str3 = "css";
				if (words[1].trim().equals(str1)) {

					element= (WebElement) js.executeScript("return document.getElementById('"+words[2].trim()+"');");
					break outer;
				}
				if (words[1].trim().equals(str2)) {

				
					element= (WebElement) js.executeScript("return document.evaluate( \""+words[2].trim()+"\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
					
					break outer;

				}
				if (words[1].trim().equals(str3)) {
				
					element= (WebElement) js.executeScript("return document.executeQuery('.words[2].trim()');");
					
					break outer;
				}
			}
		}
	
		return element;

	}
}
