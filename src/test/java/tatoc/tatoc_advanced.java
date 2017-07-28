package tatoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;

import Utilities.spec_reader;

import net.minidev.json.parser.ParseException;

public class tatoc_advanced {

	private static WebDriver driver;
	static JavascriptExecutor js = (JavascriptExecutor) driver;
	 spec_reader y1 = new spec_reader();

	@Test(priority = 0)
	public void HomePage() throws IOException {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\ashikasrivastava\\Downloads\\chromedriver_win32 (2)\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get("http://10.0.1.86/tatoc");
		y1.locate(driver, "advanced").click();

	}

	@Test(priority = 1)
	public void Hover() throws IOException {

		Actions action = new Actions(driver);

		WebElement element = y1.locate(driver, "menu");

		WebElement element1 = y1.locate(driver, "next");
		action.moveToElement(element).moveToElement(element1).click().build().perform();

	}

	@Test(priority = 2)
	public void JDBC()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {
		String id = null, name = null, passkey = null;
		String dbClass = "com.mysql.jdbc.Driver";
		// String query = "select * from credentials;";
		// String query1 = "select * from identity;";
		// String query2= "desc credentials;";

		String symbol = y1.locate(driver, "symbol").getText().toLowerCase();
		// System.out.println(symbol);

		String query3 = "Select  id  from identity where symbol='" + symbol + "';";
		Class.forName(dbClass).newInstance();

		Connection con = DriverManager.getConnection("jdbc:mysql://10.0.1.86:3306/tatoc", "tatocuser", "tatoc01");
		// System.out.println(con);
		con.createStatement();
		Statement st = con.createStatement();

		ResultSet rs = st.executeQuery(query3);

		while (rs.next()) {
			id = rs.getString(1);
			// System.out.println("cred:"+rs.getString(1));
			// System.out.println("cred:"+rs.getString(2));
			// System.out.println("cred:"+rs.getString(3));

		}

		// con.close();
		String query4 = "Select  name , passkey  from credentials where id='" + id + "';";
		Class.forName(dbClass).newInstance();

		// Connection
		// con1=DriverManager.getConnection("jdbc:mysql://10.0.1.86:3306/tatoc","tatocuser","tatoc01");
		con.createStatement();
		Statement st1 = con.createStatement();

		ResultSet rs1 = st1.executeQuery(query4);

		while (rs1.next()) {
			name = rs1.getString(1);
			passkey = rs1.getString(2);
			// System.out.println("cred:"+rs1.getString(1));

		}

		con.close();

		y1.locate(driver, "name").sendKeys(name);
		y1.locate(driver, "password").sendKeys(passkey);
		y1.locate(driver, "submit").click();

	}

	@Test(priority = 3)
	public void Restful() throws IOException, ParseException {

		driver.get("http://10.0.1.86/tatoc/advanced/rest/#");
		String id = driver.findElement(By.id("session_id")).getText().substring(12);
		System.out.println(id);
		JsonPath res = RestAssured.given().get("http://10.0.1.86/tatoc/advanced/rest/service/token/" + id).jsonPath();

		String resp = res.getString("token");
		System.out.println(resp);

		// System.out.println(orderedJson);

		String body = "id=[Session ID], signature=[Token], allow_access=1";
		RestAssured.given().parameters("id", id, "signature", resp, "allow_access", "1")
				.post("http://10.0.1.86/tatoc/advanced/rest/service/register");

		y1.locate(driver, "proceed").click();

	}

	@Test(priority = 4)
	public void File() throws IOException, ParseException, InterruptedException {

		String sign = " ";

		driver.findElement(By.xpath(".//a[contains(.,'Download File')]")).click();

		Thread.sleep(1000);

		BufferedReader br = null;
		FileReader fr = null;

		fr = new FileReader("C:\\Users\\ashikasrivastava\\Downloads\\file_handle_test.dat");
		br = new BufferedReader(fr);

		String sCurrentLine;

		while ((sCurrentLine = br.readLine()) != null) {
			System.out.println(sCurrentLine);
			String[] words = sCurrentLine.split(":");
			if (words[0].equals("Signature")) {
				sign = words[1].trim();
				break;
			}
		}

		driver.findElement(By.id("signature")).sendKeys(sign);
		driver.findElement(By.xpath("//input[@value='Proceed']")).click();
	}

	@AfterTest
	public void close() {
		 driver.close();
	}

}