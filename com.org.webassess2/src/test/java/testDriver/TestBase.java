package testDriver;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import org.apache.log4j.xml.DOMConfigurator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import config.ActionKeywords;
import config.Constants;
import config.ExtentTestManage;
import config.userFunctions;
import testUtils.ExcelUtils;
import testUtils.Log;
import testUtils.Reports;

public class TestBase {

	public static Properties OR;
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static Method method[];
	public static WebDriver driver;
		
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static String sData;
    public static String sScenarioID;
    public static String bTakeScreenshot;
	public static boolean bResult;
	public static String sTestCaseName;
	public static String sStepName;
	
    public static String ResultsFolder;
	
    public static ExtentTest test;
    protected static ExtentReports extent;
    public static ExtentReports extent2;
    public ExtentTestManage extentTestManager;

    static ExtentReports myReport;
    static ExtentTest parent;
    
    public static ExtentTest CalculatorParentReport;
    public static ExtentTest loginTest;

    static private ExtentTest driverTest;
	
	public TestBase() throws NoSuchMethodException, SecurityException{
		 create_ResultsFolder(Constants.RESULTSFOLDER);
		 actionKeywords = new ActionKeywords();
		 method = actionKeywords.getClass().getMethods();	
		
		 extent2 = new ExtentReports(Constants.outputDirectory2, true);
	}
	
	 public static void setExtentReports(ExtentTest parentTest,ExtentTest loginTest, ExtentReports report){
	        parent = parentTest;
	        driverTest = loginTest;
	        myReport = report;

	    }
	 
	 public static Boolean getCurrentRunStatus() {
	        return bResult;
	    }

	    public static void setCurrentRunStatus(boolean value) {
	        bResult = value;
	    }

	    
	 @BeforeTest(alwaysRun=true)
	 public void initilize() throws Exception {
		 
    	ExcelUtils.setExcelFile(Constants.Path_TestData);
    	DOMConfigurator.configure("log4j.xml");
    	String Path_OR = Constants.Path_OR;
		FileInputStream fs = new FileInputStream(Path_OR);
		OR= new Properties(System.getProperties());
		OR.load(fs);
				
		 if (extent != null) {
	            extent.close();
	        }

	        System.out.println("Automation Completed.");
	 	}
	 
	 /*
	 @BeforeTest(alwaysRun=true)
	 @Parameters({"URL","browser"})
	 public void initilize(String URL_,String browser_) throws Exception {
		 		 
    	ExcelUtils.setExcelFile(Constants.Path_TestData);
    	DOMConfigurator.configure("log4j.xml");
    	String Path_OR = Constants.Path_OR;
		FileInputStream fs = new FileInputStream(Path_OR);
		OR= new Properties(System.getProperties());
		OR.load(fs);
		
		 if(browser_.equals("IE")){
				driver=new InternetExplorerDriver();
				Log.info("IE browser started");
				}
			else if(browser_.equals("Chrome")){

				System.setProperty("webdriver.chrome.driver","C:\\Users\\Mpendulo\\ProtonWorkspace\\WebAssessment2\\com.org.webassess2\\jars\\chromedriver_win32\\chromedriver.exe");
				driver = new ChromeDriver();
				Log.info("Chrome browser started");
				int implicitWaitTime=(30);
				driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
				 driver.get(URL_);
				
				}
		
		 if (extent != null) {
	            extent.close();
	        }

	        System.out.println("Automation Completed.");
    } */
	 
	 
	@Test
	private void execute_TestCase() throws Exception {
	    	
		int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
	    	
			for(int iTestcase=1; iTestcase<iTotalTestCases; iTestcase++){
				bResult = true;
				System.out.println("Test case #" + iTestcase);
				
				sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
				sTestCaseName = ExcelUtils.getCellData(iTestcase, 1, Constants.Sheet_TestCases);
				
				sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
				
				if (sRunMode.equals("Yes")){
					Log.startTestCase(sTestCaseID);
					
					iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
					iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
					
					bResult=true;
					
	                System.out.println(sTestCaseName);

	                CalculatorParentReport = ExtentTestManage.startTest(sTestCaseName,sTestCaseID);
	                
					for (;iTestStep<iTestLastStep;iTestStep++){
						
			    		sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,Constants.Sheet_TestSteps);
			    		sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
			    		sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
			    		
			    		bTakeScreenshot = ExcelUtils.getCellData(iTestStep, Constants.Col_TakeScreenshot, Constants.Sheet_TestSteps);
	                    sScenarioID = ExcelUtils.getCellData(iTestStep, Constants.Col_TestScenarioID, Constants.Sheet_TestSteps);
	                    sStepName = ExcelUtils.getCellData(iTestStep, 2, Constants.Sheet_TestSteps);
	                    System.out.println(sStepName);

	                    loginTest = extent2.startTest(sStepName);
	                    setExtentReports(CalculatorParentReport,loginTest, extent2);
	                    userFunctions.LogRunResults(myReport, driverTest, LogStatus.INFO, "Execution of '"+sStepName+"'.", "OK", false, ActionKeywords.driver, true, CalculatorParentReport);

			    		for(int i=0;i<method.length;i++){
	            			
	            			if(method[i].getName().equals(sActionKeyword)){
	            				
	            				ActionKeywords.setExtentReports(CalculatorParentReport,loginTest, extent2,bTakeScreenshot);
	            				 
	            				method[i].invoke(actionKeywords,sPageObject, sData);
	            				if(method[i].getName().equals("closeBrowser")){
	                                setCurrentRunStatus(true);
	                            }
	            							
	            				if(getCurrentRunStatus()==true){
	            					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
	            					ActionKeywords.TakeScreenShots(sScenarioID, ResultsFolder);
	                                ExcelUtils.setResultLink(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps, ResultsFolder + "\\" + sScenarioID + ".png");
	                                     					
	            					break;
	            					
	            				}else{
	            					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
	            					ExcelUtils.setResultLink(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps, ResultsFolder + "\\" + sScenarioID + ".png");
	            					
	            					ActionKeywords.closeBrowser("","");
	            					break;
	            					}
	            				}
	            			}
	                 
	                    
						if(getCurrentRunStatus()==false){
							ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
							
							loginTest = extent2.startTest(sTestCaseName);
	                        setExtentReports(CalculatorParentReport,loginTest, extent2);
	                        userFunctions.LogRunResults(myReport, driverTest, LogStatus.INFO, "Execution of "+sTestCaseName+" has failed", "FAIL", false, ActionKeywords.driver, true, CalculatorParentReport);
							
							Log.endTestCase(sTestCaseID);
							break;
							}						
						}
					if(getCurrentRunStatus()==true){
					ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
					
					loginTest = extent2.startTest(sTestCaseName);
                    setExtentReports(CalculatorParentReport,loginTest, extent2);
                    userFunctions.LogRunResults(myReport, driverTest, LogStatus.INFO, "Execution of test "+sTestCaseName+" has passed", "PASS", false, ActionKeywords.driver, true, CalculatorParentReport);
					
                	}	
				} else {
	                Log.startTestCase(sTestCaseID);
	                CalculatorParentReport = ExtentTestManage.startTest(sTestCaseName,sTestCaseID);
	                //CalculatorParentReport = extentTestManager.startTest(sTestCaseName,sTestCaseID);
	                loginTest = extent2.startTest(sTestCaseName);
	                setExtentReports(CalculatorParentReport,loginTest, extent2);
	                userFunctions.LogRunResults(myReport, driverTest, LogStatus.SKIP, "Execution of "+sTestCaseName+" was skipped", "PASS", false, driver, true, CalculatorParentReport);
	            }
				Reports.getReporter().endTest(ExtentTestManage.getTest());
	            Reports.getReporter().flush();
				}
		}
    
		
     public static void create_ResultsFolder(String sResultsFolder) {
         ResultsFolder = sResultsFolder
                 + new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(
                 Calendar.getInstance().getTime()).toString();

         new File(ResultsFolder).mkdir();

     }
}
