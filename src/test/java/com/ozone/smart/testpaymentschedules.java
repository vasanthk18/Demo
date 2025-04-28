// package com.ozone.smart;

// import java.time.Duration;

// import org.junit.jupiter.api.Test;
// import org.openqa.selenium.By;
// import org.openqa.selenium.Keys;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;

// public class testpaymentschedules {

// 	public WebDriver openPaymentSchedules() throws InterruptedException {
		
// 		testmainpage page = new testmainpage();

// 		WebDriver driver = page.testdetaileddataentry();

// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
// 		WebElement childcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("childid-1-5")));
// 		childcategory.click();
// 		WebElement childpagetitle = driver.findElement(By.id("DDE {'>>'} Payment Schedule"));
// 		String childtitle = childpagetitle.getText();
// 		System.out.println(childtitle.equals("DDE >> Payment Schedule") ? "Childpage " + childtitle + " verified"
// 				: "child page " + childtitle + " failed");
// 		return driver;
// 	}
	
// 	public void positivePaymentschedules() throws InterruptedException {
		
// 		WebDriver driver= openPaymentSchedules();
		
// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

// 		WebElement selectcustomer = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
// 		selectcustomer.click();
// 		Thread.sleep(4000);
// 		selectcustomer.sendKeys("ABUCHE100626");
// 		selectcustomer.sendKeys(Keys.ARROW_DOWN);
// 		selectcustomer.sendKeys(Keys.ENTER);
// 		Thread.sleep(1500);
		
// 		WebElement selectAgreement = driver.findElement(By.xpath("//*[@id=\"customer\"]"));
// 		selectAgreement.click();
		
// 		 WebElement selectAgreementoption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[data-value='VBYB100907::AN100690']")));
// 		 selectAgreementoption.click();
		 
// 		 driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/form/div[2]/div/div[1]/button")).click();
		 
		 
// 	}
	
// public void negativeSelectCustomer() throws InterruptedException {
		
// 		WebDriver driver= openPaymentSchedules();
		
		 
// 		 driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/form/div[2]/div/div[1]/button")).click();
		 
// 		 WebElement errormessage = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/form/div[2]/p"));
// 	        String actualMessage = errormessage.getText();

// 	        if (actualMessage.equals("Please select Customer")) {
// 	            System.out.println("select customer Message matches: " + actualMessage);
// 	        } else {
// 	            System.out.println("Select customer Message does not match. Actual: " + actualMessage + ", Expected: " + "Please Select Customer");
// 	        }
	        
// 	        driver.quit();
// 	}
	
// public void negatiiveSelectAgreement() throws InterruptedException {
	
// 	WebDriver driver= openPaymentSchedules();
	
// 	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

// 	WebElement selectcustomer = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
// 	selectcustomer.click();
// 	Thread.sleep(4000);
// 	selectcustomer.sendKeys("ABUCHE100626");
// 	selectcustomer.sendKeys(Keys.ARROW_DOWN);
// 	selectcustomer.sendKeys(Keys.ENTER);
// 	Thread.sleep(1500);

	 
// 	 driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/form/div[2]/div/div[1]/button")).click();
	 
// 	 WebElement errormessage = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/form/div[2]/p"));
//      String actualMessage = errormessage.getText();

//      if (actualMessage.equals("Please Select Agreement")) {
//          System.out.println("select customer Message matches: " + actualMessage);
//      } else {
//          System.out.println("Select customer Message does not match. Actual: " + actualMessage + ", Expected: " + "Please Select Customer");
//      }
     
//      driver.quit();
	 
// }

// 	@Test
// 	public void runPaymentSchedules() throws InterruptedException {
// //		openPaymentSchedules();
// //		positivePaymentschedules();
// //		negativeSelectCustomer();
// 		negatiiveSelectAgreement();
// 	}
	
// }
