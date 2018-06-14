package selenium_api;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import commons.CommonVerify;

public class Test_TCB extends CommonVerify {
	WebDriver driver;

	String workingDir = System.getProperty("user.dir");
	String filePath = workingDir + "\\testdata\\DavosaCoupon.png";
	String fileName = "DavosaCoupon.png";

	String usernameTxt = "//input[@id='identifierId']";
	String btnNextUsername = "//div[@id='identifierNext']";
	String passwordTxt = "//input[@type='password']";
	String btnNextPwd = "//div[@id='passwordNext']";
	String composeBtn = "//div[@role='button' and text()='COMPOSE']";
	String dialogEmail = "//div[@role='dialog']";
	String recipientsTxt = "//div[text()='Recipients']";
	String toTextArea = "//textarea[@aria-label='To']";
	String subjectTxt = "//input[@name='subjectbox']";
	String textboxArea = "//div[@role='textbox']";
	String uploadBtn = "//input[@type='file']";
	String imageSelected = "//div[text()='" + fileName + "']";
	String sendBtn = "//div[@role='button' and text()='Send']";
	String sentMsg = "//div[contains(text(),'Your message has been sent.')]";

	String userName = "TaiLe.13041991";
	String passWord = "Hanoi@0!%";
	String recipientsEmail = "taile.01319@gmail.com";
	String subjectEmail = "Test automation " + randomGenerate();
	String contentEmail = "I'm writing to test automation for gmail";

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\driver\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://gmail.com");
	}

	@Test
	public void TC_01_LoginToGmail() {
		List<WebElement> signInElement = driver.findElements(By.xpath("//a[text()='Sign In']"));
		if (signInElement.size() > 0)
			clickToElement(driver, "//a[text()='Sign In']");
		sendTextToElement(driver, usernameTxt, userName);
		clickToElementByJs(driver, btnNextUsername);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(passwordTxt)));
		sendTextToElement(driver, passwordTxt, passWord);
		clickToElement(driver, btnNextPwd);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(composeBtn)));
		isElementDisplayed(driver, composeBtn);
	}

	@Test
	public void TC_02_SendEmailWithAttachment() throws Exception {
		clickToElement(driver, composeBtn);
		isElementDisplayed(driver, dialogEmail);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(recipientsTxt)));
		sendTextToElement(driver, toTextArea, recipientsEmail);
		System.out.println("Email subject: " + subjectEmail);
		sendTextToElement(driver, subjectTxt, subjectEmail);
		sendTextToElement(driver, textboxArea, contentEmail);
		StringSelection select = new StringSelection(filePath);

		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, null);

		WebElement uploadElement = driver.findElement(By.xpath("//div[@command='Files']"));
		uploadElement.click();

		Robot robot = new Robot();

		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);

		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		Thread.sleep(1000);

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(1000);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(imageSelected)));
		isElementDisplayed(driver, imageSelected);
		clickToElement(driver, sendBtn);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(sentMsg)));
		isElementDisplayed(driver, sentMsg);
	}

	@Test
	public void TC_03_VerifyEmailSent() throws Exception {
		Assert.assertTrue(verifyEmailWasSent(subjectEmail, recipientsEmail, passWord));
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public int randomGenerate() {
		Random rand = new Random();
		return rand.nextInt(99999);
	}

}
