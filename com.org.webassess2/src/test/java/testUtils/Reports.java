package testUtils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;
import com.relevantcodes.extentreports.ReporterType;

import config.Constants;
import testDriver.TestBase;

import java.io.File;
import java.net.UnknownHostException;

import org.testng.annotations.BeforeSuite;

public class Reports extends Constants {

	
	
   // @SuppressWarnings("deprecation")
	@BeforeSuite
	public static ExtentReports Instance() {
        ExtentReports extent;
        String Path = TestBase.ResultsFolder + "\\Report.html";
        System.out.println(Path);
        extent = new ExtentReports(Path, false);
        extent.config()
                .documentTitle("Mpendulo K CIB Assess QA Automation Report")
                .reportName("Mpendulo CIB Assessment ");


        return extent;
    }


    private static ExtentReports extent;

    protected static final String extentConfigFileLocation = "src/test/java/ExtentConfig.xml";

    public synchronized static ExtentReports getReporter(String filePath) {
        filePath = filePath + "\\" + outputDirectory;
        if (extent == null) {
            extent = new ExtentReports(filePath, true, NetworkMode.ONLINE);
        	//extent = new ExtentReports(filePath,true);
            extent.startReporter(ReporterType.DB, (new File(filePath)).getParent() + File.separator + "extent.db");


            //Load Config File for Report

            extent.config()
                    .documentTitle("Mpendulo K CIB Assess QA Automation Report")
                    .reportName("Mpendulo CIB Assessment");


            com.sun.security.auth.module.NTSystem NTSystem = new com.sun.security.auth.module.NTSystem();

            java.net.InetAddress localMachine = null;
            try {
                localMachine = java.net.InetAddress.getLocalHost();
            } catch (RuntimeException e) {
                e.getMessage();
            } catch (UnknownHostException e) {
                e.getMessage();
            }

            if (!(localMachine == null)) {
                extent
                        .addSystemInfo("Host Name", localMachine.getHostName())
                        .addSystemInfo("Test Environment", "Omni Channel UAT");
            } else {
                extent
                        .addSystemInfo("Host Name", "localhost_Mpendulo-PC")
                        .addSystemInfo("Test Environment", "Local Machine");
            }


        }

        return extent;
    }

    public synchronized static ExtentReports getReporter() {
        return extent;
    }
    
    public void close() {
        if (extent != null) {
            extent.close();
        }
    }

}

