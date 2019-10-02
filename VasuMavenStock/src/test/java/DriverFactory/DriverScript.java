package DriverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunLibrary.FunctionLibrary;
import Utilities.ExcelFileUtil;

public class DriverScript
{
WebDriver driver;
ExtentReports report;
ExtentTest logger;
	public void startTest() throws Throwable
	{
		//Create Object reference for reusing Excel Methods
		ExcelFileUtil excel=new ExcelFileUtil();
		
		//Working with "MasterTestCases" Sheet
		for (int i = 1; i <=excel.rowCount("MasterTestCases"); i++)
		{
			
			String ModuleStatus="";
			System.out.println(excel.getData("MasterTestCases", i, 2));
			if(excel.getData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
			{
				//Define Module(Test Cases) Name
				String TCModule=excel.getData("MasterTestCases", i, 1);
				
report=new ExtentReports("D:\\Vasu8AM\\VasuMavenStock\\Reports\\"+TCModule+FunctionLibrary.generateDate()+".html");
		logger=report.startTest(TCModule);
				// Working with "TCModule(Test Cases Steps)" Sheet
				int rowcount=excel.rowCount(TCModule);
				for (int j = 1; j<=rowcount; j++) 
				{
					String Description=excel.getData(TCModule, j, 0);
					String Object_Type=excel.getData(TCModule, j, 1);
					String Locator_Type=excel.getData(TCModule, j, 2);
					String Locator_Value=excel.getData(TCModule, j, 3);
					String Test_Data=excel.getData(TCModule, j, 4);
					try
					{
					if (Object_Type.equalsIgnoreCase("startBrowser"))
					{
						driver=FunctionLibrary.startBrowser(driver);
						logger.log(LogStatus.INFO, Description);
					}
					if (Object_Type.equalsIgnoreCase("openApplication")) 
					{
						FunctionLibrary.openApplication(driver);
						logger.log(LogStatus.INFO, Description);
					}
					if (Object_Type.equalsIgnoreCase("typeAction")) 
					{
						FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if (Object_Type.equalsIgnoreCase("clickAction"))
					{
						FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if (Object_Type.equalsIgnoreCase("waitForElement")) 
					{
						FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if (Object_Type.equalsIgnoreCase("closeBrowser")) 
					{
						FunctionLibrary.closeBrowser(driver);
						logger.log(LogStatus.INFO, Description);
					}
					if (Object_Type.equalsIgnoreCase("pageDown"))
					{
						FunctionLibrary.pageDown(driver);
						logger.log(LogStatus.INFO, Description);
					}
					if (Object_Type.equalsIgnoreCase("captureData"))
					{
						FunctionLibrary.captureData(driver, Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if (Object_Type.equalsIgnoreCase("tableValidation"))
					{
						FunctionLibrary.tableValidation(driver, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					excel.setData(TCModule, j, 5, "PASS");
					logger.log(LogStatus.PASS, Description);
					ModuleStatus="true";
					}catch(Exception e)
					{
					excel.setData(TCModule, j, 5, "FAIL");
					logger.log(LogStatus.FAIL, Description);
					ModuleStatus="false";
					
				File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(srcFile,new File("D:\\Vasu8AM\\VasuMavenStock\\ScreenShots\\"+TCModule+FunctionLibrary.generateDate()+".png"));
				break;
					}
					
				}
				if(ModuleStatus.equalsIgnoreCase("true"))
				{
					excel.setData("MasterTestCases",i,3,"PASS");
				}else
					if(ModuleStatus.equalsIgnoreCase("false"))
					{
						excel.setData("MasterTestCases",i,3,"FAIL");
					}
				report.endTest(logger);
				report.flush();
			}
			
			else
			{
				excel.setData("MasterTestCases",i,3,"Not Executed");
			}
		}
		
		
	}
}
