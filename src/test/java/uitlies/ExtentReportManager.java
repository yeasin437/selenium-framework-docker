package uitlies;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter;//UI of the report
	public ExtentReports extent; //populate common info on the report
	public ExtentTest test; //create test case entries in the report and update status of the test methods
	String repName;
	public void onStart(ITestContext testContext) {
//		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
//		Date dt = new Date();
//		String currentdatetimestamp = df.format(dt);
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.mm.ss").format(new Date());
		repName = "Test-Report" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName);
		
		sparkReporter.config().setDocumentTitle("Automation Report"); //Title of the report
		sparkReporter.config().setReportName("Functional Testing");//name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "openCart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
	    String browser = testContext.getCurrentXmlTest().getParameter("browser");
	    extent.setSystemInfo("Browser", browser);
	    
	    List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
	    if(!includedGroups.isEmpty()) {
	    	extent.setSystemInfo("Groups", includedGroups.toString());
	    }
	}
	public void onTestSuccess(ITestResult result)
	{
		test = extent.createTest(result.getTestClass().getName());//create a new entry in the report
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS,"Test case PASSED is:"+result.getName()+"got successfully executed");///update status p/f/s
	}
	public void onTestFailure(ITestResult result)
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL,result.getName()+" got failed");
		test.log(Status.INFO,result.getThrowable().getMessage());
		
		
	}
	public void onTestSkipped(ITestResult result)
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP,result.getName()+" got skipped");
		test.log(Status.INFO,result.getThrowable().getMessage());
	}
	public void onFinish(ITestContext context)
	{
		extent.flush();
		
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		
		try
		{
			Desktop.getDesktop().browse(extentReport.toURI());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		/*
		try {
		    // Construct the file URL
		    String filePath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + repName;
		    URL url = new URL("file:///" + filePath);
		    
		    // Create the email message
		    ImageHtmlEmail email = new ImageHtmlEmail();
		    email.setDataSourceResolver(new DataSourceUrlResolver(url));
		    email.setHostName("smtp.gmail.com"); // Corrected hostname
		    email.setSmtpPort(465);
		    email.setAuthenticator(new DefaultAuthenticator("mdyeasin560@gmail.com", "bangladesh437"));
		    email.setSSLOnConnect(true);
		    email.setFrom("mdyeasin560@gmail.com");
		    email.setSubject("Test Results");
		    email.setMsg("Please find the attached report....");
		    email.addTo("career3517@gmail.com");
		    email.attach(url, "extent report", "Please check the report....");
		    email.send();
		} catch (Exception e) {
		    e.printStackTrace(); // Log the exception for debugging
		}
		*/
		
	}

}
