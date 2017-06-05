

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.apache.log4j.Logger;

public class DETest {

	public WebDriver driver;
	private static final Logger logger = Logger.getLogger(DETest.class);

	public WebDriver Preconditions() {

		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\ashsaxen\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}

	public WebDriver getDriver() {
	//	driver = Preconditions();
		return this.driver;
	}

	public void WaitForPageToLoad(int... waitTime) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};

		if (waitTime.length > 0) {
			Wait(waitTime).until(expectation);
		} else {
			Wait(30).until(expectation);
		}

	}

	public Wait<WebDriver> Wait(int... waitTime) {
		int waitTimeInSeconds;
		if (waitTime.length > 0) {
			waitTimeInSeconds = waitTime[0];
		} else {
			waitTimeInSeconds = 5;
		}
		return new FluentWait<WebDriver>(getDriver()).withTimeout(waitTimeInSeconds, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).ignoring(WebDriverException.class);
	}

	public boolean PageExist(String expectedTitle) throws InterruptedException {

		try {
			for (int i = 0; i < 3; i++) {
				String act = getDriver().getTitle();
				if (act.equals(expectedTitle)) {
					logger.info(">> Current page - " + expectedTitle);
					return true;
				} else {
					Thread.sleep(2000);
					logger.info("waiting for page.. ");
				}
			}
			logger.info("Not reached page - " + expectedTitle);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public List<WebElement> findAllLinks() {
		List<WebElement> linksOnPage = getDriver().findElements(By.tagName("a"));
		linksOnPage.addAll(driver.findElements(By.tagName("img")));
		logger.info("Total links and Images on current Page :- " + linksOnPage.size());
		List<WebElement> finalList = new ArrayList<WebElement>();
		for (WebElement link : linksOnPage) {
			if (link.getAttribute("href") != null) {
				finalList.add(link);
			}
		}
		return finalList;
	}

	public String isLinkBroken(URL url) throws Exception {
		// url
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try {
			connection.connect();
			String response = connection.getResponseMessage();
			connection.disconnect();
			return response;
		} catch (Exception exp) {
			return exp.getMessage();
		}
	}

	public void tesForLinks() {
		List<WebElement> allImages = findAllLinks();
		logger.info("Total number of elements found " + allImages.size());
		for (WebElement element : allImages) {
			try {
				logger.info(element.getAttribute("title") + "  - URL: " + element.getAttribute("href") + " :: returned -> "
						+ isLinkBroken(new URL(element.getAttribute("href"))));
				// logger.info("URL: " +
				// element.getAttribute("outerhtml")+ " returned " +
				// isLinkBroken(new URL(element.getAttribute("href"))));
			} catch (Exception exp) {
				logger.info(
						"At " + element.getAttribute("innerHTML") + " Exception occured -&gt; " + exp.getMessage());
			}
		}
	}
	
	public void SampleOrder(){
		{
		    driver.findElement(By.cssSelector("i.graphic-icon-orderedge.graphic-icon")).click();
		    driver.findElement(By.id("searchmix_name_value")).click();
		    driver.findElement(By.id("searchmix_name_value")).clear();
		    driver.findElement(By.id("searchmix_name_value")).sendKeys("cheese");
		    driver.findElement(By.xpath("//div[@id='item-wrapper-2089436']/div[4]/div/div[3]/div/select")).click();
		    new Select(driver.findElement(By.xpath("//div[@id='item-wrapper-2089436']/div[4]/div/div[3]/div/select"))).selectByVisibleText("1");
		    driver.findElement(By.cssSelector("option[value=\"1\"]")).click();
		    new Select(driver.findElement(By.xpath("//div[@id='item-wrapper-2015133']/div[4]/div/div[3]/div/select"))).selectByVisibleText("1");
		    driver.findElement(By.xpath("(//option[@value='1'])[2]")).click();
		    new Select(driver.findElement(By.xpath("//div[@id='item-wrapper-2015139']/div[4]/div/div[3]/div/select"))).selectByVisibleText("10");
		    driver.findElement(By.xpath("(//option[@value='10'])[6]")).click();
		    new Select(driver.findElement(By.xpath("//div[@id='item-wrapper-2015792']/div[4]/div/div[3]/div/select"))).selectByVisibleText("5");
		    driver.findElement(By.xpath("(//option[@value='5'])[11]")).click();
		    driver.findElement(By.xpath("//div[6]/div/div/div/div/button")).click();
		    driver.findElement(By.xpath("(//option[@value='20'])[17]")).click();
		    new Select(driver.findElement(By.xpath("//div[@id='item-wrapper-2012135']/div[5]/div/div[3]/div/select"))).selectByVisibleText("5");
		    driver.findElement(By.xpath("(//option[@value='5'])[17]")).click();
		    driver.findElement(By.linkText("Checkout:")).click();
		    driver.findElement(By.linkText("Checkout")).click();
		    driver.findElement(By.linkText("Submit Order")).click();
		   // assertEquals(closeAlertAndGetItsText(), "Error: Connection to diningedge.smtp.com:80 Timed Out");
		  //  driver.findElement(By.cssSelector("i.logo-image")).click();
		  //  driver.findElement(By.xpath("//div[3]/button")).click();
		  }
	}
	

}
