# Selenium Hybrid Framework  
Keyword and Data Driven Framework using Selenium + Java + TestNG + Maven
Reporting feature using Extent Reports.
Taking Screenshots on Failed test cases and also upon specifying on Test Step. 

This is a demonstration of how one can create a Hybrid Framework using Selenium and Java and TestNG annotations. 

#How to run
1. Open WebTestcase.xlsx and perform below steps
	- Test Cases sheet - set Yes or NO on run mode per test case
	- Test Steps sheet - select page object and how you want to interact with each one under Action keywords
		     - select at which test step you want to take screenshot under screenshot column
	- Test Data sheet - enter test data that is then loaded into Test Steps Data Set column
	- Save and close excel spreedsheet
2. Open TestBase class in testDriver package and run as TestNG application 
3. Once program stops executing refresh project and then open Results folder --> latest Mpendulo_CIBAssessment folder then    MpenduloK_CIB_QA_Assessment_Automation_Report.html to view extent report
4. Open screenShots\Screens folder to view screenshots as per your selection on excell sheet

#Components
1. TestBase class - Main class that initilizes and executes test cases

2. WebTestcase.xlsx that the program is driven from 	
   
3. Object repository (OR) of all web element locators on website that are used to execute testcase 
    
3. Keywords class - A keyword library that contains all the methods to be executed against web elements stored in OR. 

4. Contants - used to declare all the contant variables that cannot be changed using final keyword

5. ExcelUtils - handles all the excel related functions from Apache POI library used in TestBase to execute excel test cases

6. Reports - configures and creates Extent reports instance used to create html reports

    
Thank you,
Mpendulo Khumalo