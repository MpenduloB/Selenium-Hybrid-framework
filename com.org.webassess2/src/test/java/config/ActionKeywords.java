package config;

import static testDriver.TestBase.OR;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import testUtils.Log;
import testDriver.TestBase;


public class ActionKeywords extends userFunctions{
	
	public static WebDriver driver;
	int implicitWaitTime;
    ExtentReports reports;
    static ExtentTest parent;
    static ExtentTest browserLoginTest;
    static ExtentTest browserLogoutTest;
    static ExtentReports myReport;
    public static boolean takeScreenShot;

	 

    public static void setExtentReports(ExtentTest parentTest, ExtentTest loginTest, ExtentReports report, String bTakeScreenshot) {
        parent = parentTest;
        browserLoginTest = loginTest;
        myReport = report;
        if (bTakeScreenshot.equalsIgnoreCase("yes")) {
            takeScreenShot = true;
        } else {
            takeScreenShot = false;
        }
    }
	
		
	
	public static void openBrowser(String object,String data){		
		Log.info("Opening Browser");
		 boolean browserInvoked = false;
		try{				
			 if(data.equals("IE")){
				driver=new InternetExplorerDriver();
				Log.info("IE browser started");
				}
			else if(data.equals("Chrome")){
				
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "//jars//chromedriver_win32//chromedriver.exe");
				driver = new ChromeDriver();
			
				Log.info("Chrome browser started");
				}
			
			int implicitWaitTime=(30);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
			
			userFunctions.LogRunResults(myReport, browserLoginTest, LogStatus.PASS, "WebDriver instantiated using the driver class" + driver.getClass().toString(), "OK", false, driver, false, parent);
            userFunctions.LogRunResults(myReport, browserLoginTest, LogStatus.PASS, "Test execution is starting for scenario name " + parent.getTest().getName().toString().toUpperCase(), "OK", takeScreenShot, driver, false, parent);
            browserInvoked = true;
            TestBase.setCurrentRunStatus(true);
			
		}catch (Exception e){
			Log.info("Not able to open the Browser --- " + e.getMessage());
			TestBase.setCurrentRunStatus(false);
			browserInvoked = true;
			LogRunResults(myReport, browserLoginTest, LogStatus.FAIL, "Failed to instantiate WebDriver", "The exception received is....... " + e, true, driver, true, parent);
		}
		}
	
		public static void navigate(String object, String data) {
        try {
            Log.info("Navigating to URL");
            driver.get(Constants.URL);
            String driverClass = driver.getClass().toString();

            if ((!driverClass.contains("InternetExplorerDriver") && !driverClass.contains("ChromeDriver"))) {
                driver.get("javascript:document.getElementById('overridelink').click();");
            }
            userFunctions.LogRunResults(myReport, browserLoginTest, LogStatus.PASS, "Navigating to URL " + Constants.URL, "OK", takeScreenShot, driver, false, parent);
            
            TestBase.setCurrentRunStatus(true);
        } catch (Exception e) {
            Log.info("Not able to navigate --- " + e.getMessage());
            userFunctions.LogRunResults(myReport, browserLoginTest, LogStatus.FAIL, "Not able to navigate to URL--- " + e.getMessage(), "OK", true, driver, false, parent);
            TestBase.setCurrentRunStatus(false);
        	}
        }
	
    public static void clear(String object, String data) {
        try {
            Log.info("Clearing the text in " + object);
            driver.findElement(By.xpath(OR.getProperty(object))).clear();
            TestBase.setCurrentRunStatus(true);
        } catch (Exception e) {
            Log.error("Not able to Clear text  --- " + e.getMessage());
            TestBase.setCurrentRunStatus(false);

        }
    }
	
	public static void click(String object, String data){
		try{
			Log.info("Clicking on Webelement "+ object);
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			TestBase.setCurrentRunStatus(true);
            userFunctions.LogRunResults(myReport, browserLoginTest, LogStatus.PASS, "Clicking the element", "OK", takeScreenShot, driver, false, parent);
            
		 }catch(Exception e){
 			Log.error("Not able to click --- " + e.getMessage());
 			userFunctions.LogRunResults(myReport, browserLoginTest, LogStatus.FAIL, "Not able to click --- " + e.getMessage(), "OK", false, driver, false, parent);
 			TestBase.setCurrentRunStatus(false);
         	}
		}
	

public static void verifyURL(String object, String data){
		try{
			Log.info("verifying URL" );
			String URL = driver.getCurrentUrl();
			//Assert.assertEquals(URL, "http://www.way2automation.com/angularjs-protractor/webtables/" );
			Assert.assertEquals(URL, data );
		 }catch(Exception e){
			 Log.error("Not able to Enter UserName --- " + e.getMessage());
			 TestBase.bResult = false;
		 	}
		}
	
	 public static void getLabelText(String object, String data) {
	        try {
	            Log.info("Validate the text " + object);
	            driver.findElement(By.xpath(OR.getProperty(object))).getText().equals(data);
	            TestBase.setCurrentRunStatus(true);
	        } catch (Exception e) {
	            Log.error("Not able to Select an item --- " + e.getMessage());
	            TestBase.setCurrentRunStatus(false);

	        }
	    }
	
	public static void input(String object, String data){
		try{
			Log.info("Entering the text in " + object);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.chord(Keys.CONTROL, "a"), data, Keys.TAB);
			   userFunctions.LogRunResults(myReport, browserLoginTest, LogStatus.PASS, "Populating the field with '" + data.toUpperCase() + "'", "OK", takeScreenShot, driver, false, parent);
			   TestBase.setCurrentRunStatus(true);
		}catch(Exception e){
			 Log.error("Not able to Enter --- " + e.getMessage());
			 userFunctions.LogRunResults(myReport, browserLoginTest, LogStatus.FAIL, "Not able to populate the field --- " + e.getMessage(), "FAIL", true, driver, false, parent);
			 TestBase.setCurrentRunStatus(false);
		 	}
		}
	
	 public static void waitFor(String object, String data) throws Exception {
	        try {
	            Log.info("Wait for a few seconds");
	            userFunctions.waitForAjax(driver);
	            Thread.sleep(Long.parseLong(data));
	          
	            TestBase.setCurrentRunStatus(true);
	           if (takeScreenShot) {
	                try {
	                    if (driver instanceof JavascriptExecutor) {
	                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(OR.getProperty(object))));
	                    }
	                } catch (WebDriverException e) {
	                    e.getMessage().toString().toUpperCase();
	                }
	           }
	           
	           userFunctions.LogRunResults(myReport, browserLoginTest, LogStatus.PASS, "The wait has been completed for the object.", "OK", takeScreenShot, driver, false, parent);

	        } catch (Exception e) {
	            Log.error("Not able to Wait --- " + e.getMessage());
	            userFunctions.LogRunResults(myReport, browserLoginTest, LogStatus.FATAL, "Exception encountered while waiting for the object. --- " + e.getMessage(), "FAIL", true, driver, false, parent);
	            TestBase.setCurrentRunStatus(false);
	            

	        }
	    }
	 
	 public static void selectRadio(String object, String data) {
	        try {
	            Log.info("Select item " + object);

	            //driver.findElement(By.xpath(OR.getProperty(object))).click();
	            WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
	            Actions actions = new Actions(driver);
	            actions.moveToElement(element).click().perform();  
	            userFunctions.waitForAjax(driver);
	           TestBase.setCurrentRunStatus(true);
	        } catch (Exception e) {
	            Log.error("Not able to Select an item --- " + e.getMessage());
	            TestBase.setCurrentRunStatus(false);

	        }
	    }
	 
	
	    public static void select(String object, String data) {
	        try {
	            Log.info("Selecting item in Combo " + object);

	            WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
	            Actions actions = new Actions(driver);
	            actions.moveToElement(element).click().perform();  
	            userFunctions.waitForAjax(driver);
	            final Select selectBox = new Select(driver.findElement(By.xpath(OR.getProperty(object))));

	            selectBox.selectByVisibleText(data);
	            TestBase.setCurrentRunStatus(true);
	        } catch (Exception e) {
	            Log.error("Not able to Select an item --- " + e.getMessage());
	            TestBase.setCurrentRunStatus(false);

	        }
	    }
	    
	 public static void WaitUntilVisible(String object, String data) {
	        try {
	            Log.info("Wait for the object to be visible " + object);
	            userFunctions.waitForAjax(driver);

	            WebDriverWait wait = new WebDriverWait(driver, 20);
	            WebElement element = wait.until(
	             ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
	            System.out.println(element.getTagName());
	            TestBase.setCurrentRunStatus(true);
	           if (takeScreenShot) {
	                try {
	                    if (driver instanceof JavascriptExecutor) {
	                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(OR.getProperty(object))));
	                    }
	                } catch (WebDriverException e) {
	                    e.getMessage().toString().toUpperCase();
	                }
	           }
	           userFunctions.LogRunResults(myReport, browserLoginTest, LogStatus.PASS, "The test object is displayed.", "OK", takeScreenShot, driver, false, parent);
	        
	        } catch (Exception e) {
	            Log.error("Not able to view item --- " + e.getMessage());
	            userFunctions.LogRunResults(myReport, browserLoginTest, LogStatus.FATAL, "Exception encountered while trying to check the whether the object is visible. --- " + e.getMessage(), "FAIL", true, driver, false, parent);
	            TestBase.setCurrentRunStatus(false);
	            }
	    }
	 
	 
	 public static void ValidateText(String object, String data) {
	        try {
	            Log.info("Validate the text " + object);

	            String fieldData = driver.findElement(By.xpath(OR.getProperty(object))).getText();

	            if (fieldData.isEmpty() || fieldData.equals("")) {
	                fieldData = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");

	            }

	            if (fieldData == null) {
	                fieldData = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("title");

	            }

	            TestBase.setCurrentRunStatus(fieldData.trim().replaceAll("\\s", "").equalsIgnoreCase(data.trim().replaceAll("\\s", "")));

	            if (fieldData.trim().replaceAll("\\s", "").equalsIgnoreCase(data.trim().replaceAll("\\s", ""))) {
	                if (takeScreenShot) {
	                    try {
	                        if (driver instanceof JavascriptExecutor) {
	                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(OR.getProperty(object))));
	                        }
	                    } catch (WebDriverException e) {
	                        e.getMessage().toString().toUpperCase();
	                    }
	                }
	                userFunctions.LogRunResults(myReport, browserLoginTest, LogStatus.PASS, "The text '" + data.toUpperCase() + "' was verified successfully.", "OK", takeScreenShot, driver, false, parent);
	            } else {
	                userFunctions.LogRunResults(myReport, browserLoginTest, LogStatus.FAIL, "The text '" + data.toUpperCase() + "' was not verified. The actual data displayed on the field is '" + fieldData.trim().toUpperCase() + "'.", "FAIL", true, driver, false, parent);

	            }

	        } catch (Exception e) {
	            Log.error("Not able to Select an item --- " + e.getMessage());
	            userFunctions.LogRunResults(myReport, browserLoginTest, LogStatus.FATAL, "Exception encountered while trying to validate text--- " + e.getMessage(), "FAIL", true, driver, false, parent);
	            TestBase.setCurrentRunStatus(false);

	        }
	    }

	 
	 public static void TakeScreenShots(String TestID, String ResultsPath) {
	        try {
	        	
	            Log.info("Take a screenshot ");
	             File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	             FileUtils.copyFile(scrFile, new File(ResultsPath + "\\" + TestID + ".png"));
	        
	        } catch (Exception e) {
	            Log.error("Not able to take a screenshot --- " + e.getMessage());
	         
	        }
	    }
	    
	 
	public static void closeBrowser(String object, String data){
		try{
		
			 if (!(driver == null)) {
	                Log.info("Closing the browser");
	                System.out.println("Closing the browser");
	                userFunctions.LogRunResults(myReport, browserLoginTest, LogStatus.PASS, "Closing the " + driver.getClass().toString() + " driver", "OK", takeScreenShot, driver, false, parent);
	                driver.quit();
	            }
		
		 }catch(Exception e){
			 Log.error("Not able to Close the Browser --- " + e.getMessage());
			 userFunctions.LogRunResults(myReport, browserLoginTest, LogStatus.INFO, "Not able to Close the Browser --- " + e.getMessage(), "FAIL", false, driver, false, parent);
			 TestBase.setCurrentRunStatus(false);
         	}
		}
	
	
	
	
}
