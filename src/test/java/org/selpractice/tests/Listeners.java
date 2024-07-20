package org.selpractice.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import resources.ExtentReporterNG;

import java.io.IOException;

public class Listeners extends BaseTest implements ITestListener {
     ExtentTest test;
ExtentReports extent = ExtentReporterNG.getObjectReport();
ThreadLocal<ExtentTest> extentTest = new <ExtentTest> ThreadLocal();
    @Override
    public void onTestStart(ITestResult result) {
      test =  extent.createTest(result.getMethod().getMethodName());
      extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
      extentTest.get().log(Status.PASS , "Test Passed");

    }

    @Override
    public void onTestFailure(ITestResult result) {
        //Screenshot
        String path = null;
        String screenshot_file_name = result.getMethod().getMethodName();
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            path =  getScreenshot(screenshot_file_name, driver);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        extentTest.get().addScreenCaptureFromPath(path,screenshot_file_name);
        extentTest.get().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
     extent.flush();
    }
}
