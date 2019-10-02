package CommonFunLibrary;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class StockAccounting
{
	WebDriver driver;
String res;
	//appLaunch
	
	public String appLaunch(String url)
	{
		System.setProperty("webdriver.chrome.driver", "D:\\Vasu8AM\\StockAccounting\\CommonJarFiles\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		//validation
		if (driver.findElement(By.id("username")).isDisplayed()) 
		{
			
			res="Pass";
		}else
		{
			
			res="Fail";
		}
		
		return res;
		
	}
	
	//appLogin
	public String appLogin(String username,String password)
	{
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).clear();
		
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("btnsubmit")).click();
		//validation
		if(driver.findElement(By.id("logout")).isDisplayed())
		{
			
			res="Pass";
		}else
		{
			
			res="Fail";
		}
		
		return res;
		
	}
	
	//appLogout
	public String appLogout() throws Throwable
	{
		Thread.sleep(2000);
		driver.findElement(By.id("logout")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[text()='OK!']")).click();
		Thread.sleep(2000);
		//validation
		if (driver.findElement(By.id("username")).isDisplayed()) 
		{
			
			res="Pass";
		}else
		{
			
			res="Fail";
		}
		
		return res;
	}
	//appClose
	public void appClose()
	{
		driver.close();
	}
	//SupplierCreation
	public String supplierCreation(String sName,String add,String city,String country,String cPerson,String pNumber,String email,String mNumber,String notes) throws InterruptedException
	{
		driver.findElement(By.id("mi_a_suppliers")).click();
		driver.findElement(By.xpath("//*[@id='ewContentColumn']/div[3]/div[1]/div[1]/div[1]/div/a")).click();
		String exp_data=driver.findElement(By.id("x_Supplier_Number")).getAttribute("value");
		System.out.println(exp_data);
		driver.findElement(By.id("x_Supplier_Name")).sendKeys(sName);
		driver.findElement(By.id("x_Address")).sendKeys(add);
		driver.findElement(By.id("x_City")).sendKeys(city);
		driver.findElement(By.id("x_Country")).sendKeys(country);
		driver.findElement(By.id("x_Contact_Person")).sendKeys(cPerson);
		driver.findElement(By.id("x_Phone_Number")).sendKeys(pNumber);
		driver.findElement(By.id("x__Email")).sendKeys(email);
		driver.findElement(By.id("x_Mobile_Number")).sendKeys(mNumber);
		driver.findElement(By.id("x_Notes")).sendKeys(notes);
		/*//scroll down the page
		Actions action=new Actions(driver);
		action.sendKeys(Keys.PAGE_DOWN).build().perform();
		*/
		driver.findElement(By.id("btnAction")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[text()='OK!']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//*[text()='OK'])[6]")).click();
		//validation
		
		if(driver.findElement(By.xpath("//*[@id='ewContentColumn']/div[2]/div[2]/div/button/span")).isDisplayed())
		{
			driver.findElement(By.xpath("//*[@id='ewContentColumn']/div[2]/div[2]/div/button/span")).click();
			Thread.sleep(2000);
			driver.findElement(By.id("psearch")).clear();
			driver.findElement(By.id("psearch")).sendKeys(exp_data);
			Thread.sleep(2000);
			driver.findElement(By.id("btnsubmit")).click();
		}else
		{
			Thread.sleep(2000);
			driver.findElement(By.id("psearch")).clear();
			driver.findElement(By.id("psearch")).sendKeys(exp_data);
			Thread.sleep(2000);
			driver.findElement(By.id("btnsubmit")).click();
		}
		Thread.sleep(2000);
		String act_data=driver.findElement(By.xpath("//*[@id='el1_a_suppliers_Supplier_Number']/span")).getText();
		System.out.println(act_data);
		
		if (exp_data.equals(act_data))
		{
			res="pass";
		}else
		{
			res="Fail";
		}
		driver.findElement(By.xpath("//*[@id='ewContentColumn']/div[2]/div[2]/div/button/span")).click();
		return res;
		
	}
	public static void main(String[] args) throws Throwable
	{
		StockAccounting app=new StockAccounting();
		
		
		System.out.println(app.appLaunch("http://webapp.qedge.com"));
		System.out.println(app.appLogin("admin", "master"));
		app.supplierCreation("Nokia", "Ameerpet", "Hyderabad", "India", "Vasudeva", "22222", "vasu@qedgetech.com", "55555", "Completed");
		
	}
}
