

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class DETest {

	public WebDriver driver;

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
					System.out.println("current page - " + expectedTitle);
					return true;
				} else {
					Thread.sleep(2000);
					System.out.println("waiting for page.. ");
				}
			}
			System.out.println("Not reached page - " + expectedTitle);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public List findAllLinks() {
		List<WebElement> linksOnPage = getDriver().findElements(By.tagName("a"));
		linksOnPage.addAll(driver.findElements(By.tagName("img")));
		System.out.println("Total links and Images on current Page :- " + linksOnPage.size());
		List finalList = new ArrayList();
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
		System.out.println("Total number of elements found " + allImages.size());
		for (WebElement element : allImages) {
			try {
				System.out.println("URL: " + element.getAttribute("href") + " :: returned -> "
						+ isLinkBroken(new URL(element.getAttribute("href"))));
				// System.out.println("URL: " +
				// element.getAttribute("outerhtml")+ " returned " +
				// isLinkBroken(new URL(element.getAttribute("href"))));
			} catch (Exception exp) {
				System.out.println(
						"At " + element.getAttribute("innerHTML") + " Exception occured -&gt; " + exp.getMessage());
			}
		}
	}

}
