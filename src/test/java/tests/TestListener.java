package tests;

import lombok.extern.log4j.Log4j2;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.AllureUtils;

import java.util.concurrent.TimeUnit;

@Log4j2
public class TestListener implements ITestListener {
    //log вместо sout
    public void onTestStart(ITestResult iTestResult) {
        log.info((String.format("======================================== STARTING TEST %s ========================================", iTestResult.getName())));
    }


    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println(String.format("======================================== FINISHED TEST %s Duration: %ss ========================================", iTestResult.getName(),
                getExecutionTime(iTestResult)));
        takeScreenshot();
    }


    public void onTestFailure(ITestResult iTestResult) {
        System.out.println(String.format("======================================== FAILED TEST %s Duration: %ss ========================================", iTestResult.getName(),
                getExecutionTime(iTestResult)));
        //takeScreenshot(iTestResult);
    }


    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println(String.format("======================================== SKIPPING TEST %s ========================================", iTestResult.getName()));

    }

    private byte[] takeScreenshot() {
        return AllureUtils.takeScreenshot();
    }


    private long getExecutionTime(ITestResult iTestResult) {
        return TimeUnit.MILLISECONDS.toSeconds(iTestResult.getEndMillis() - iTestResult.getStartMillis());
    }


}
