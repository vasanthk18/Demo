// package com.ozone.smart;

// import java.time.Duration;

// import org.junit.jupiter.api.Test;
// import org.openqa.selenium.By;
// import org.openqa.selenium.Keys;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;

// public class testcustomerqualitycheck {
	
// 	public WebDriver openCustomerQualityCheck() throws InterruptedException {
		 
// 		testmainpage page = new testmainpage();

// 		WebDriver driver = page.testCqc();

// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
// 		WebElement childcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("childid-2-0")));
// 		childcategory.click();

// 		return driver;
// 	}
	
// 	public void selectCustomerandProposal() throws InterruptedException {
// 		WebDriver driver = openCustomerQualityCheck();
		
// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

// 		WebElement selectcustomer = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
// 		selectcustomer.click();
// 		Thread.sleep(4000);
// 		selectcustomer.sendKeys("ABDCOR100362");
// 		selectcustomer.sendKeys(Keys.ARROW_DOWN);
// 		selectcustomer.sendKeys(Keys.ENTER);
// 		Thread.sleep(1500);
		
// 		WebElement selectAgreement = driver.findElement(By.xpath("//*[@id=\"customer\"]"));
// 		selectAgreement.click();
		
// 		 WebElement selectAgreementoption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[data-value='VBYB100662']")));
// 		 selectAgreementoption.click();
// 	}
	
// 	@Test
// 	public void runCqc() throws InterruptedException {
// //		openCustomerQualityCheck();
// 		selectCustomerandProposal();
// 	}

// }
