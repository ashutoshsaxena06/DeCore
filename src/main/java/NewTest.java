import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class NewTest extends DETest {
	@BeforeClass
	public void beforeClass() {
		driver = Preconditions();
		driver.get("http://user.adv.diningedge.baitek.org/#/access/login");
	}

	@AfterClass
	public void afterClass() {

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
		Assert.assertEquals(driver.getCurrentUrl(), "http://user.adv.diningedge.baitek.org/#/app/home");
		System.out.println("login functionality working");
	}
	@Test(priority=3)
	public void SetLocation_Test() throws InterruptedException {
		try {
			driver.findElement(By.xpath("//div[6]/div/div/div[3]/button")).click();
			WaitForPageToLoad(30);
			Thread.sleep(5000);
			driver.findElement(By.id("dropdownMenu1")).click();
			driver.findElement(By.linkText("Ed1 copy3")).click();
			System.out.println("All locations checked");
		} catch (Exception e) {
			driver.findElement(By.id("dropdownMenu1")).click();
			driver.findElement(By.linkText("Ed1 copy3")).click();
			System.out.println("All locations checked");
			e.printStackTrace();
		}
	}
	
	@Test(priority=4)
	public void HomePage_AllLinks() throws InterruptedException {
		//Home
		WaitForPageToLoad(30);
		PageExist("DiningEdge");
		tesForLinks();
	}
	
	
	
}
