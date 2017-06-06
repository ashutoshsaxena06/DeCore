import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.apache.log4j.Logger;
import org.apache.xpath.operations.Or;

public class DETest {

	public WebDriver driver;
	private static final Logger logger = Logger.getLogger(DETest.class);

	public WebDriver Preconditions() {

		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Edge\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}

	public WebDriver getDriver() {
		// driver = Preconditions();
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
				logger.info(element.getAttribute("title") + "  - URL: " + element.getAttribute("href")
						+ " :: returned -> " + isLinkBroken(new URL(element.getAttribute("href"))));
				// logger.info("URL: " +
				// element.getAttribute("outerhtml")+ " returned " +
				// isLinkBroken(new URL(element.getAttribute("href"))));
			} catch (Exception exp) {
				logger.info("At " + element.getAttribute("innerHTML") + " Exception occured -&gt; " + exp.getMessage());
			}
		}
	}

	public void SampleOrder() throws InterruptedException {
		{
			driver.findElement(By.cssSelector("i.graphic-icon-orderedge.graphic-icon")).click();
			logger.info("Order Grid page");
			WaitForPageToLoad(30);
			Thread.sleep(5000);

			// Pop up
			handleModal_Continue();

			WaitForPageToLoad(30);
			Thread.sleep(5000);
			// List of purveyors
			List<WebElement> OrderGrid_Purveyors = driver.findElements(By.xpath("//table/*/tr[1]/th"));
			logger.info("Number of purveyors on OrderGrid - " + (OrderGrid_Purveyors.size() - 1));
			int i = 0;
			for (WebElement webElement : OrderGrid_Purveyors) {
				logger.info(i + " " + webElement.getText());
				i++;
			}

			// Search for Item - Cheese
			// 100164006
			WebElement searchBar = driver.findElement(By.id("searchmix_name_value"));
			searchBar.click();
			searchBar.clear();
			Thread.sleep(5000);
			searchBar.sendKeys("100164006");
			driver.findElement(By.id("searchmix_name_value")).sendKeys(Keys.RETURN);
			logger.info("Searching for item in Grid ....");
			Thread.sleep(5000);

			//
			List<WebElement> Ordergrid_Items = driver.findElements(By.xpath("//tr/td[1]/following-sibling::*/*"));
			int n = Ordergrid_Items.size() + 1;
			for (WebElement element : Ordergrid_Items) {
				Select se = new Select(driver.findElement(By.xpath("//tr/td[" + n
						+ "]/*/*/*/div[@class='qty-info']/*/*[@class='quantity-box bordered-box helper-qty']")));
				se.selectByVisibleText(Integer.toString(n));
				logger.info("Item selected from Grid with quantity -" + n);
				n--;
			}
			Thread.sleep(5000);
			WaitForPageToLoad(30);
			driver.findElement(By.linkText("Checkout:")).click();
			logger.info("Items Selected from grid. Proceed to checkout");

			Thread.sleep(5000);
			WaitForPageToLoad(30);
			driver.findElement(By.linkText("Checkout")).click();
			logger.info("Checkout complete. Proceed to submit order");

			Thread.sleep(5000);
			WaitForPageToLoad(30);
			try {
				Wait(40).until(ExpectedConditions
						.elementToBeClickable(driver.findElement(By.xpath("//div[@class='cart-actions']/a[3]"))));
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
			logger.info("Order Submitted !!!");
		}
	}

	public void handleModal_Continue() {
		try {
			driver.switchTo().frame("modal-dialog");
			driver.findElement(By
					.xpath("//*[@class='modal-dialog']/*/div[@class='modal-footer ng-scope']/button[contains(.,'Create New Order')]"))
					.click();
			driver.switchTo().activeElement();
			logger.info("Pop-up Handled !");
		} catch (Exception e) {
			logger.info("No Pop-Up !");
		}
	}

}
