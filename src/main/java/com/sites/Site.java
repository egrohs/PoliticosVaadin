package com.sites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.modelo.JavaBeanToCsv;
import com.modelo.Politico;

public abstract class Site {
	protected WebDriver driver;
	// private Document doc;
	protected String siteURL;
	protected List<Politico> politicos = new ArrayList<Politico>();

	public abstract String getUrl();

	public abstract List<Politico> getData(Document doc, List<Politico> politicos) throws IOException;

	public Site(boolean needDriver) throws Exception {
		if (needDriver) {
			// FirefoxProfile profile = new FirefoxProfile();
			System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
			// WebDriver driver = new FirefoxDriver();
			// final WebDriver driver = new HtmlUnitDriver();
			// ((HtmlUnitDriver)driver).setJavascriptEnabled(true);
			driver = new ChromeDriver();
			// driver.manage().timeouts().setScriptTimeout(10,
			// TimeUnit.SECONDS);
		}
		List<Politico> politicos = new ArrayList<Politico>();
		// politicos = JavaBeanToCsv.read();// le existentes
		politicos = getData(navega(getUrl()), politicos);
		// JavaBeanToCsv.toCSV(politicos);// salva novos
		if (driver != null) {
			driver.close();
		}
	}

	protected void clica(WebElement elem) {
		elem.click();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 35);
			final JavascriptExecutor javascript = (JavascriptExecutor) (driver instanceof JavascriptExecutor ? driver
					: null);

			wait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					boolean outcome = Boolean
							.parseBoolean(javascript.executeScript("return jQuery.active == 0").toString());
					return outcome;
				}
			});
		} catch (TimeoutException ex) {
			throw new TimeoutException("Timed out after " + 35 + " seconds while waiting for Ajax to complete.");
		} catch (WebDriverException e) {
			System.out.println(
					"JQuery libraries are not present on page " + driver.getCurrentUrl() + " - " + driver.getTitle());
		}
	}

	protected Document navega(String url) {
		if (driver != null) {
			driver.get(url);
		}
		return lePaginaSemAjax();
	}

	protected Document lePaginaSemAjax(/*String url*/) {
		/*if (url == null || "".equals(url)) {
			url = getUrl();
		}*/
		if (driver != null) {
			return Jsoup.parse(driver.getPageSource());
		}
		try {
			return Jsoup.connect(getUrl())
					.userAgent(
							"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
					.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}