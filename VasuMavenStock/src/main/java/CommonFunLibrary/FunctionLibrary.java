package CommonFunLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.PropertyFileUtil;


public class FunctionLibrary
{
	WebDriver driver;
//startBrowser
	public static WebDriver startBrowser(WebDriver driver) throws Throwable
	{
		
		if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "D:\\Vasu8AM\\VasuMavenStock\\ExecutableFiles\\geckodriver.exe");
			driver=new FirefoxDriver();
		}else
			if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome"))
			{
				System.setProperty("webdriver.chrome.driver", "D:\\Vasu8AM\\VasuMavenStock\\ExecutableFiles\\chromedriver.exe");
				driver=new ChromeDriver();
			}else
				if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("ie"))
				{
					System.setProperty("webdriver.ie.driver", "D:\\Vasu8AM\\VasuMavenStock\\ExecutableFiles\\IEDriverServer.exe");
					driver=new InternetExplorerDriver();
				}
		return driver;
	}
	//openAppplication
	
	public static void openApplication(WebDriver driver) throws Throwable
	{
		driver.get(PropertyFileUtil.getValueForKey("URL"));
		driver.manage().window().maximize();
	}
	
	//typeAction
	public static void typeAction(WebDriver driver,String locatorType,String locatorValue,String data)
	{
		if(locatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorValue)).clear();
			driver.findElement(By.id(locatorValue)).sendKeys(data);
			
		}else
			if(locatorType.equalsIgnoreCase("name"))
			{
				driver.findElement(By.name(locatorValue)).clear();
				driver.findElement(By.name(locatorValue)).sendKeys(data);
			}else
				if(locatorType.equalsIgnoreCase("xpath"))
				{
					driver.findElement(By.xpath(locatorValue)).clear();
					driver.findElement(By.xpath(locatorValue)).sendKeys(data);
				}
	}
	//clickAction
	
	public static void clickAction(WebDriver driver,String locatorType,String locatorValue)
	{
		if(locatorType.equalsIgnoreCase("id"))
		{
			
			driver.findElement(By.id(locatorValue)).click();
		}else
			if(locatorType.equalsIgnoreCase("name"))
			{
				
				driver.findElement(By.name(locatorValue)).click();
			}else
				if(locatorType.equalsIgnoreCase("xpath"))
				{
					
					driver.findElement(By.xpath(locatorValue)).click();
				}
	}
	//closeBrowser
	public static void closeBrowser(WebDriver driver)
	{
		driver.close();
	}
	
	//waitForElement
	
	public static void waitForElement(WebDriver driver,String locatorType,String locatorValue,String waittime)
	{
		WebDriverWait myWait=new WebDriverWait(driver, Integer.parseInt(waittime));
		
		if(locatorType.equalsIgnoreCase("id"))
		{
			
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
		}else
			if(locatorType.equalsIgnoreCase("name"))
			{
				
				myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
			}else
				if(locatorType.equalsIgnoreCase("xpath"))
				{
					
					myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
				}
		
	}
	//scroll down page
	
			public static void pageDown(WebDriver driver)
			{
				Actions action=new Actions(driver);
				
				action.sendKeys(Keys.PAGE_DOWN).build().perform();
			}
			//Capture Supplier Number
			public static void captureData(WebDriver driver,String locatorType,String locatorValue) throws Throwable
			{
				String data="";
				if (locatorType.equalsIgnoreCase("id"))
				{
				data=driver.findElement(By.id(locatorValue)).getAttribute("value");
				}else
					if(locatorType.equalsIgnoreCase("name"))
					{
						data=driver.findElement(By.name(locatorValue)).getAttribute("value");
					}else
						if(locatorType.equalsIgnoreCase("xpath"))
						{
							data=driver.findElement(By.xpath(locatorValue)).getAttribute("value");	
						}
				FileWriter fw=new FileWriter("D:\\Vasu8AM\\VasuMavenStock\\CaptureData\\Data.txt");
				BufferedWriter bw=new BufferedWriter(fw);
				bw.write(data);
				bw.close();
				
			}
			
	//SupplierCreation Table Validation
			
		public static void tableValidation(WebDriver driver,String column) throws Throwable
		{
			//Reading the Supplier Number  from  text file 
			
			FileReader fr=new FileReader("D:\\Vasu8AM\\VasuMavenStock\\CaptureData\\Data.txt");
			BufferedReader br=new BufferedReader(fr);
			String exp_data=br.readLine();
			//Converting String value into integer
			int columNum=Integer.parseInt(column);
			
			if (driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.Panel"))).isDisplayed()) 
			{
				driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.Panel"))).click();
				
				driver.findElement(By.id(PropertyFileUtil.getValueForKey("Search.Box"))).clear();
				driver.findElement(By.id(PropertyFileUtil.getValueForKey("Search.Box"))).sendKeys(exp_data);
				driver.findElement(By.id(PropertyFileUtil.getValueForKey("Search.Button"))).click();
				
			}else
			{
				driver.findElement(By.id(PropertyFileUtil.getValueForKey("Search.Box"))).clear();
				driver.findElement(By.id(PropertyFileUtil.getValueForKey("Search.Box"))).sendKeys(exp_data);
				driver.findElement(By.id(PropertyFileUtil.getValueForKey("Search.Button"))).click();
			}
			//identifying Suppliers details WebTable
		WebElement webTable=driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("webtable")));
		//row count
		List<WebElement> rows=webTable.findElements(By.tagName("tr"));
		for (int i =1; i <=rows.size(); i++) 
		{
			
		String act_data=driver.findElement(By.xpath("//*[@id='ewContentColumn']/div[3]/form/div//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+columNum+"]/div/span")).getText();	
	System.out.println(act_data);
	//validation
		Assert.assertEquals(exp_data, act_data);
		break;
		}
		
		}
		
		//Generate Date
			
		public static String generateDate()
		{
			Date date=new Date();
			
			SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_dd_ss");
			
			return sdf.format(date);
		}
		//Sample method
		
		public static void vasu()
		{
			System.out.println("Selenium OJT Training");
		}
			
}
