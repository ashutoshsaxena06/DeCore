import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class New_Test extends DETest {
	private static final Logger logger = Logger.getLogger(New_Test.class);

	@BeforeClass
	public void beforeClass() {
		driver = Preconditions();
		driver.get("https://user.diningedge.com/#/access/login");
	}

	@AfterClass
	public void afterClass() {
	//	driver.close();
	}

	@Test(priority=1)
	public void LoginPage_AllLinks() throws InterruptedException {
		WaitForPageToLoad(30);
		PageExist("DiningEdge");
		tesForLinks();
	}

	@Test(priority=2)
	public void LoginFunctionality() throws InterruptedException {
		WaitForPageToLoad(30);
		PageExist("DiningEdge");
		// login
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("ed");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("123890ff");
		driver.findElement(By.id("login_toggle")).click();
		
		Thread.sleep(5000);
		//validate
		Assert.assertEquals(driver.getCurrentUrl(), "https://user.diningedge.com/#/app/home");
		logger.info("login functionality working");
	}
	@Test(priority=3)
	public void SetLocation_Test() throws InterruptedException {
		try {
			driver.findElement(By.xpath("//div[6]/div/div/div[3]/button")).click();
			WaitForPageToLoad(30);
			Thread.sleep(5000);
		/*	WebElement ddl_SelectLocation = );
			Actions act = new Actions(driver);
			act.moveToElement(ddl_SelectLocation).doubleClick();*/
			driver.findElement(By.id("dropdownMenu1")).click();
			driver.findElement(By.id("dropdownMenu1")).click();
			driver.findElement(By.xpath("//button[@id='dropdownMenu1']/following-sibling::*/li[1]/a")).click();
			logger.info("Location selected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=4)
	public void HomePage_AllLinks() throws InterruptedException {
		//Home
		WaitForPageToLoad(30);
		PageExist("DiningEdge");
		//tesForLinks();
	}
	
	@Test(priority=5)
	public void SampleOrder_Test() throws InterruptedException {
		//Home
		WaitForPageToLoad(30);
		PageExist("DiningEdge");
		//stesForLinks();
		SampleOrder();
		
	}
	
	@Test(priority=6)
	public void LogOut_Function() throws InterruptedException {
		Thread.sleep(3000);
	//	driver.findElement(By.xpath("//a/i[@title='Log Out']")).click();
		logger.info("LogOut Success");

	}
	
	
}
