package com.saha.slnarch.report;

import java.io.File;
import java.io.IOException;
import org.junit.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ExtentJunitListener extends TestWatcher {

  ReportManager reportManager = ReportManager.getInstance();


  WebDriver driver;

  public ExtentJunitListener(WebDriver driver) {
    this.driver = driver;
  }

  @Override
  public Statement apply(Statement base, Description description) {
    return super.apply(base, description);
  }

  @Override
  protected void succeeded(Description description) {
    super.succeeded(description);
    reportManager.getExtentTest()
        .info(String.format("Success Test=%s", description.getMethodName()));
  }

  @Override
  protected void failed(Throwable e, Description description) {
    super.failed(e, description);
    reportManager.getExtentTest()
        .fail(String.format("Fail Test=%s clause=%s", description.getMethodName(), e.getMessage()));
    addScreenShoot();
  }

  @Override
  protected void skipped(AssumptionViolatedException e, Description description) {
    super.skipped(e, description);
    reportManager.getExtentTest()
        .skip(String.format("Skip Test=%s clause=%s", description.getMethodName(), e.getMessage()));
    addScreenShoot();
  }

  @Override
  protected void starting(Description description) {
    super.starting(description);
    reportManager.createNewExtentTest(description.getMethodName());
    reportManager.getExtentTest()
        .info(String.format("Starting Test=%s", description.getMethodName()));
    reportManager.setAuthor(description);
    reportManager.setCategory(description);
  }

  @Override
  protected void finished(Description description) {
    super.finished(description);
    driver.quit();
    reportManager.getExtentTest()
        .info(String.format("Finished Test=%s", description.getMethodName()));
  }

  private void addScreenShoot() {
    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    try {
      reportManager.addScreenShot(scrFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
