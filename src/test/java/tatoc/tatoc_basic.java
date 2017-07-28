package tatoc;

import org.openqa.selenium.Cookie;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Utilities.config_reader;
import Utilities.spec_reader;

public class tatoc_basic {

	private static WebDriver driver;

	static spec_reader y1 = new spec_reader();
	 config_reader y2 = new config_reader();
	 String title;

	@BeforeTest
	public void launch() throws IOException, InterruptedException {
		Reporter.log("<---------------T.A.T.O.C Begins!!!-------------->");
		String browser = y2.getPropValues();
		if (browser.equals("chrome")) {

			System.setProperty("webdriver.chrome.driver",
					"C:/Users/ashikasrivastava/Desktop/chromeDriver/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get("http://10.0.1.86/tatoc");
			Thread.sleep(5000);
	
			//System.out.println("Print page title: " + title);
			
		}

	}

	@Test(priority = 0)
	public void HomePage() throws IOException {
		Reporter.log("<---------------T.A.T.O.C Basic-------------->");
		spec_reader.locate(driver, "basic").click();


	}

	@Test(priority = 1)
	public void GreenBox() throws InterruptedException, IOException {
		Reporter.log("<---------------T.A.T.O.C Step1-------------->");
		spec_reader.locate(driver, "greenbox").click();

	}

	@Test(priority = 2)
	public void iframe() throws InterruptedException, IOException {
		Reporter.log("<---------------T.A.T.O.C Step2-------------->");
		String cls2 = " ";
		driver.switchTo().frame("main");
		WebElement element1 = spec_reader.locate(driver, "1box");
			String cls = element1.getAttribute("class").toString();


		while (!(cls.equals(cls2))) {

			driver.switchTo().frame("child");
			WebElement element2 = spec_reader.locate(driver, "2box");
			cls2 = element2.getAttribute("class").toString();
		
			if (cls.equals(cls2)) {
				driver.switchTo().defaultContent();
				driver.switchTo().frame("main");
				spec_reader.locate(driver, "proceed").click();

			} else {
				driver.switchTo().defaultContent();
				driver.switchTo().frame("main");

				spec_reader.locate(driver, "repaint").click();
			}

		}
	}

	@Test(priority = 3)
	public void drag_n_drop() throws InterruptedException, IOException {
		Reporter.log("<---------------T.A.T.O.C Step3-------------->");

		WebElement From = spec_reader.locate(driver, "drag");

		WebElement To = spec_reader.locate(driver, "drop");

		Actions builder = new Actions(driver);

		Action dragAndDrop = builder.clickAndHold(From)

				.moveToElement(To)

				.release(To)

				.build();

		dragAndDrop.perform();

		spec_reader.locate(driver, "proceed").click();

	}

	@Test(priority = 4)
	public void window_handler() throws InterruptedException, IOException {
		Reporter.log("<---------------T.A.T.O.C Step4-------------->");
		String parentWindowHandler = driver.getWindowHandle(); // Store your
																// parent window

		spec_reader.locate(driver, "launch").click();
		String subWindowHandler = null;

		Set<String> handles = driver.getWindowHandles(); // get all window
															// handles
		Iterator<String> iterator = handles.iterator();
		while (iterator.hasNext()) {
			subWindowHandler = iterator.next();
		}

		driver.switchTo().window(subWindowHandler); // switch to popup window
		Reporter.log("<---------------T.A.T.O.C user moved to pop up page-------------->");
		WebElement email_id = spec_reader.locate(driver, "email");
		email_id.sendKeys("Ashika Srivastava");

		spec_reader.locate(driver, "submit").click();

		driver.switchTo().window(parentWindowHandler);

		spec_reader.locate(driver, "proceed").click();

	}

	@Test(priority = 5)
	public void cookie() throws InterruptedException, IOException {
		Reporter.log("<---------------T.A.T.O.C Step5-------------->");

		spec_reader.locate(driver, "generate").click();
		String text = spec_reader.locate(driver, "token").getText();
		String[] token = text.split(":");
		// System.out.println(token[1]);
		Cookie ck = new Cookie("Token", token[1].trim());
		driver.manage().addCookie(ck);

		Reporter.log("<---------------T.A.T.O.C cookie generated-------------->");
		spec_reader.locate(driver, "proceed").click();
	}

	@AfterTest
	public void session_close() {
		Reporter.log("<---------------T.A.T.O.C Session Close-------------->");
		driver.close();

	}

}
