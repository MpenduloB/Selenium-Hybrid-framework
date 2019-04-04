package config;

public class Constants {

	//System Variables
	public static final String URL = "http://www.way2automation.com/angularjs-protractor/webtables/";
	public static final String Path_TestData = System.getProperty("user.dir") + "//src//test//java//dataFiles//WebTestCase.xlsx";
		
	public static final String Path_OR = System.getProperty("user.dir") + "//src//test//java//config//OR.txt";
	public static final String File_TestData = "WebTestCase.xlsx";
	
	public static final String KEYWORD_FAIL = "FAIL";
	public static final String KEYWORD_PASS = "PASS";
	public static final String RESULTSFOLDER = System.getProperty("user.dir") + "//src//test//java//Results//Mpendulo_CIBAssessment";
	
	protected static final String outputDirectory = "MpenduloK_CIB_QA_Assessment_Automation_Report.html";
	protected static final String fileDownloadDir = "C:\\selenium_downloads";
	
	protected static final String screenShotsFolder = System.getProperty("user.dir") + "//src//test//java//screenShot";
		public static final String outputDirectory2 =  System.getProperty("user.dir") + "//src//test//java//OIReport.html";
	
	//Data Sheet Column Numbers
	public static final int Col_TestCaseID = 0;	
	public static final int Col_TestScenarioID =1 ;
	public static final int Col_PageObject =4 ;
	public static final int Col_ActionKeyword =5 ;
	public static final int Col_RunMode =2 ;
	public static final int Col_Result =3 ;
	public static final int Col_DataSet =6 ;
	public static final int Col_TakeScreenshot =7 ;
	public static final int Col_TestStepResult =8 ;
	public static final int Col_ErrorMessages=10;
		
	// Data Engine Excel sheets
	public static final String Sheet_TestSteps = "Test Steps";
	public static final String Sheet_TestCases = "Test Cases";
	public static final String Sheet_Environment = "Environment";
	
}
