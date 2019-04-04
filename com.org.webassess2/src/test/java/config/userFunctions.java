package config;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class userFunctions {

	 static ActionKeywords actionKeywords;
	    static String absoluteFileName;
	
	    //The method below will wait for ajax call to complete before interacting with elements
	    public static void waitForAjax(WebDriver driver) {
	        try {
	            if (driver instanceof JavascriptExecutor) {
	                JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
	                Object numberOfAjaxConnections = jsDriver.executeScript("return jQuery.active");
	               
	                if (numberOfAjaxConnections instanceof Long) {
	                    Long n = (Long) numberOfAjaxConnections;
	                    while (!(n.longValue() == 0L)) {
	                        System.out.println("Ajax wait......active calls-- " + n.longValue());
	                        numberOfAjaxConnections = jsDriver.executeScript("return jQuery.active");
	                        n = (Long) numberOfAjaxConnections;
	                    }

	                }

	            }
	        } catch (WebDriverException e) {
	            e.getMessage().toString().toUpperCase();
	        }

	    }
	    
	    public static void setScreenShotFileName(String value) {
	        absoluteFileName = value;
	    }

	    public static String getScreenShotFileName() {
	        return absoluteFileName;
	    }
	    public static void LogRunResults(ExtentReports testReport, ExtentTest testName, LogStatus status, String stepName, String stepDetails, Boolean captureScreenShot, WebDriver driver, Boolean appendTestToParent, ExtentTest parentTest) {

	        testName.log(status, stepName, stepDetails);

	        if (captureScreenShot) {
	            if (!(driver == null)) {
	                takeScreenShot(driver, Constants.screenShotsFolder);
	                testName.log(LogStatus.PASS, "Snapshot below for the above step: " + testName.addScreenCapture(getScreenShotFileName()));
	            } else {

	                testName.log(LogStatus.INFO, "Snapshot for the above step could not be taken because the driver was not instantiated.");
	            }

	        }

	        if (appendTestToParent) {
	            parentTest.appendChild(testName);
	        }

	      
	    }
	    
	    protected static void takeScreenShot(WebDriver driver, String path) {
	        try {

	            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
	            File scrFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
	            FileUtils.copyFile(scrFile, getDestinationFile(path));
	        } catch (IOException ioe) {
	            throw new RuntimeException(ioe);
	        }

	    }

	    public static File getDestinationFile(String userDirectory) {

	        String date = getDateTime();
	        String fileName = "test_screen_" + date + ".png";
	        //add date of today
	        String dateForDir = getDateTime();
	        setScreenShotFileName(userDirectory + "/" + dateForDir + "/" + fileName);

	        return new File(absoluteFileName);
	    }


	    public static String getDateTime() {
	        Date date = Calendar.getInstance().getTime();

	     
	        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
	        String today = formatter.format(date);
	        return today;
	    }
	
}
