package com.saha.slnarch.core.page;

import com.saha.slnarch.core.browser.BrowserFactory;
import com.saha.slnarch.core.helper.ConfigurationHelper;
import com.saha.slnarch.core.model.Configuration;
import java.net.MalformedURLException;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PageTestImpl implements PageTest {

  protected final Logger logger = LoggerFactory.getLogger(getClass());
  private WebDriver driver;
  private Configuration configuration;

  protected PageTestImpl() {
    initWebDriver();
    if (useEventDriver()) {
      driver = createEventDriver();
    }
  }

  private void initWebDriver() {

    configuration = ConfigurationHelper.INSTANCE.getConfiguration();
    try {
      driver = new BrowserFactory().getWebDriver(getCapabilities(), getProxy(), configuration);
    } catch (MalformedURLException e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }

  }


  protected WebDriver getDriver() {
    return driver;
  }

  @Override
  public Proxy getProxy() {
    return null;
  }

  @Override
  public Capabilities getCapabilities() {
    return null;
  }

  @Override
  public WebDriver createEventDriver() {
    return new EventFiringWebDriver(driver);
  }


  @Override
  public boolean useEventDriver() {
    return true;
  }

  protected Configuration getConfiguration() {
    return configuration;
  }

}
