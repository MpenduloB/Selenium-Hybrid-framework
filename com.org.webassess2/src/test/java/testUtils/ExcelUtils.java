package testUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;

import config.Constants;
import testDriver.TestBase;

public class ExcelUtils {


	private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static org.apache.poi.ss.usermodel.Cell Cell;
    private static XSSFRow Row;
    //private static XSSFRow Row;

public static void setExcelFile(String Path) throws Exception {
	try {
        FileInputStream ExcelFile = new FileInputStream(Path);
        ExcelWBook = new XSSFWorkbook(ExcelFile);
	} catch (Exception e){
		Log.error("Class Utils | Method setExcelFile | Exception desc : "+e.getMessage());
		TestBase.bResult = false;
    	}
	}

public static String getCellData(int RowNum, int ColNum, String SheetName ) throws Exception{
    try{
    	ExcelWSheet = ExcelWBook.getSheet(SheetName);
       	Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
        String CellData = Cell.getStringCellValue();
        return CellData;
     }catch (Exception e){
         Log.error("Class Utils | Method getCellData | Exception desc : "+e.getMessage());
         TestBase.bResult = false;
         return"";
         }
     }
@SuppressWarnings("deprecation")
public static void setResultLink(String Result, int RowNum, int ColNum, String SheetName, String LinkPath) throws Exception {
    try {

    	
        ExcelWSheet = ExcelWBook.getSheet(SheetName);
        ExcelWSheet = ExcelWBook.getSheet(SheetName);

        //Format Hyperlin in Excel to be blue and underlined
        CellStyle hlink_style = ExcelWBook.createCellStyle();
        Font hlink_font = ExcelWBook.createFont();
        hlink_font.setUnderline(Font.U_SINGLE);
        hlink_font.setColor(IndexedColors.BLUE.getIndex());
        hlink_style.setFont(hlink_font);

        CreationHelper createHelper = ExcelWBook.getCreationHelper();
        //Hyperlink file_link = createHelper.createHyperlink(HSSFHyperlink.LINK_FILE);
        Hyperlink link = createHelper.createHyperlink(HyperlinkType.FILE);
        link.setAddress((URLEncoder.encode(LinkPath)).replace("\\", "/").replace("+", "%20"));
        Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
        Cell.setHyperlink(link);
        Cell.setCellStyle(hlink_style);


    } catch (Exception e) {
        Log.error("Class Utils | Method setExcelFile | Exception desc : " + e.getMessage());
        Font hlink_font = ExcelWBook.createFont();
        hlink_font.setColor(IndexedColors.RED.getIndex());
       // hlink_font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        TestBase.bResult = false;
    }
}

public static int getRowCount(String SheetName){
	int iNumber=0;
	try {
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		iNumber=ExcelWSheet.getLastRowNum()+1;
	} catch (Exception e){
		Log.error("Class Utils | Method getRowCount | Exception desc : "+e.getMessage());
		TestBase.bResult = false;
		}
	return iNumber;
	}

public static int getRowContains(String sTestCaseName, int colNum,String SheetName) throws Exception{
	int iRowNum=0;	
	try {
	    //ExcelWSheet = ExcelWBook.getSheet(SheetName);
		int rowCount = ExcelUtils.getRowCount(SheetName);
		for (; iRowNum<rowCount; iRowNum++){
			if  (ExcelUtils.getCellData(iRowNum,colNum,SheetName).equalsIgnoreCase(sTestCaseName)){
				break;
			}
		}       			
	} catch (Exception e){
		Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
		TestBase.bResult = false;
		}
	return iRowNum;
	}

public static int getTestStepsCount(String SheetName, String sTestCaseID, int iTestCaseStart) throws Exception{
	try {
		for(int i=iTestCaseStart;i<=ExcelUtils.getRowCount(SheetName);i++){
			if(!sTestCaseID.equals(ExcelUtils.getCellData(i, Constants.Col_TestCaseID, SheetName))){
				int number = i;
				return number;      				
				}
			}
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		int number=ExcelWSheet.getLastRowNum()+1;
		return number;
	} catch (Exception e){
		Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
		TestBase.bResult = false;
		return 0;
    }
}

//@SuppressWarnings("static-access")
public static void setCellData(String Result,  int RowNum, int ColNum, String SheetName) throws Exception    {
       try{
    	   
    	   ExcelWSheet = ExcelWBook.getSheet(SheetName);
           Row  = ExcelWSheet.getRow(RowNum);
           //Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
           Cell = Row.getCell(ColNum);
           if (Cell == null) {
        	   Cell = Row.createCell(ColNum);
        	   Cell.setCellValue(Result);
            } else {
                Cell.setCellValue(Result);
            }
             FileOutputStream fileOut = new FileOutputStream(Constants.Path_TestData);
             ExcelWBook.write(fileOut);
             //fileOut.flush();
             fileOut.close();
             ExcelWBook = new XSSFWorkbook(new FileInputStream(Constants.Path_TestData));
         }catch(Exception e){
        	 TestBase.bResult = false;
  
         }
    }
	
}
