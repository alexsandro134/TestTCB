package commons;

import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.code.com.sun.mail.imap.IMAPFolder;
import com.google.code.javax.mail.Folder;
import com.google.code.javax.mail.Message;
import com.google.code.javax.mail.Session;
import com.google.code.javax.mail.Store;
import com.google.code.javax.mail.search.GmailRawSearchTerm;

public class CommonVerify {

	Properties prop;
	Session session;
	Store store;
	IMAPFolder inbox;
	GmailRawSearchTerm rawTerm;

	public boolean isElementDisplayed(WebDriver driver, String xpathName) {
		WebElement element = driver.findElement(By.xpath(xpathName));
		if (element.isDisplayed()) {
			System.out.println("Element " + xpathName + " is displayed");
			return true;
		} else {
			System.out.println("Element " + xpathName + " is not displayed");
			return false;
		}
	}

	public boolean isElementEnabled(WebDriver driver, String xpathName) {
		WebElement element = driver.findElement(By.xpath(xpathName));
		if (element.isEnabled()) {
			System.out.println("Element " + xpathName + " is enabled");
			return true;
		} else {
			System.out.println("Element " + xpathName + " is disabled");
			return false;
		}
	}

	public boolean isElementSelected(WebDriver driver, String xpathName) {
		WebElement element = driver.findElement(By.xpath(xpathName));
		if (element.isSelected()) {
			System.out.println("Element " + xpathName + " is selected");
			return true;
		} else {
			System.out.println("Element " + xpathName + " is not selected");
			return false;
		}
	}

	// =========================================
	// Working with Element

	public void sendTextToElement(WebDriver driver, String xpath, String text) {
		WebElement element = driver.findElement(By.xpath(xpath));
		element.clear();
		element.sendKeys(text);
	}

	public void clickToElement(WebDriver driver, String xpath) {
		WebElement element = driver.findElement(By.xpath(xpath));
		if (element.isEnabled()) {
			element.click();
		}
	}

	public String getTextElement(WebDriver driver, String xpath) {
		WebElement element = driver.findElement(By.xpath(xpath));
		return element.getText();
	}

	// =========================================
	// Working with WindowHandles

	public void switchToChildWindow(WebDriver driver, String parent) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String windowHandle : allWindows) {
			if (!windowHandle.equals(parent)) {
				driver.switchTo().window(windowHandle);
				break;
			}
		}
	}

	public void switchToMultipleWindow(WebDriver driver, String expectedTitle) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String windowHandle : allWindows) {
			driver.switchTo().window(windowHandle);
			if (driver.getTitle().equals(expectedTitle)) {
				break;
			}
		}
	}

	public void closeOtherWindow(WebDriver driver, String parentWindow) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String windowHandle : allWindows) {
			if (!windowHandle.equals(parentWindow)) {
				driver.switchTo().window(windowHandle);
				driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
	}

	// =========================================
	// Working with Element by JS

	public Object workingWithBrowserByJS(WebDriver driver, String js) {
		try {
			return ((JavascriptExecutor) driver).executeScript(js);
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public Object clickToElementByJs(WebDriver driver, String xpath) {
		try {
			WebElement element = driver.findElement(By.xpath(xpath));
			return ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public Object removeAttributeInDOM(WebDriver driver, String xpath, String attributeName) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			return js.executeScript("arguments[0].removeAttribute('" + attributeName + "');", driver.findElement(By.xpath(xpath)));
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public Object scrollToBottomPage(WebDriver driver) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			return js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public void highlightElement(WebDriver driver, WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].style.border='2px groove green';", element);
	}

	public Object scrollToElement(WebDriver driver, WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			return js.executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public boolean checkAnyImageLoaded(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (boolean) js.executeScript("return arguments[0].complete && " + "typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", element);
	}

	// get html5 validation msg
	public String getHTML5ValidationMessage(WebDriver driver, WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", element);
	}

	// =========================================
	// Verify email was sent by IMAP
	public boolean verifyEmailWasSent(String search_keyword, String recipientEmail, String passwordRecipient) throws Exception {
		try {
			prop = System.getProperties();
			prop.setProperty("mail.store.protocol", "imaps");
			session = Session.getDefaultInstance(prop, null);
			store = session.getStore("imaps");

			store.connect("imap.gmail.com", recipientEmail, passwordRecipient);
			System.out.println("Connection established !!!!");

			inbox = (IMAPFolder) store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);
			System.out.println("Total emails : " + inbox.getMessageCount());

			rawTerm = new GmailRawSearchTerm(search_keyword);
			Message messagesFound[] = inbox.search(rawTerm);
			System.out.println("Number emails match with keywords : " + messagesFound.length);
			if (messagesFound.length == 0) {
				System.out.println("Dont have any email with this subject!");
				return false;
			} else if (messagesFound.length == 1) {
				System.out.println("***********Print subjects ***********************");
				System.out.println(messagesFound[0].getSubject());
				if (messagesFound[0].getSubject().equals(search_keyword)) {
					return true;
				} else
					return false;
			}
			return false;
		} finally {
			if (inbox.isOpen()) {
				inbox.close(true);
			}
			store.close();
			System.out.println("Connection Closed !!!");
		}
	}
}
