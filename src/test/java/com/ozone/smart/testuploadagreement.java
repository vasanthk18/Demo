// package com.ozone.smart;

// import java.time.Duration;

// import org.junit.jupiter.api.Test;
// import org.openqa.selenium.By;
// import org.openqa.selenium.Keys;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;

// public class testuploadagreement {

// 	public WebDriver openUploadAgreement() throws InterruptedException {
// 		testmainpage page = new testmainpage();

// 		WebDriver driver = page.testdetaileddataentry();

// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
// 		WebElement childcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("childid-1-4")));
// 		childcategory.click();
// 		WebElement childpagetitle = driver.findElement(By.id("DDE {'>>'} Upload Agreement"));
// 		String childtitle = childpagetitle.getText();
// 		System.out.println(childtitle.equals("DDE >> Upload Agreement") ? "Childpage " + childtitle + " verified"
// 				: "child page " + childtitle + " failed");
// 		return driver;
// 	}
	
// 	public void positiveUploadAgreement() throws InterruptedException {
// 		WebDriver driver = openUploadAgreement();
		
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
// //		
		
// //         wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".MuiBackdrop-root")));
         
// 		 WebElement selectAgreementoption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[data-value='VBYB100907::AN100690']")));
// 		 selectAgreementoption.click();
		
// 		//chosefile
// 		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/form/div[1]/label")).click();
// 		//chosefile
		
		
// 	}
	
// 	public void NegativeSelectCustomer() throws InterruptedException {
// 		WebDriver driver = openUploadAgreement();
				
// 		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/form/div[2]/div/div[1]/button")).click();
		
// 		  WebElement errormessage = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/form/p"));
// 	        String actualMessage = errormessage.getText();

// 	        if (actualMessage.equals("please select a customer")) {
// 	            System.out.println("select customer Message matches: " + actualMessage);
// 	        } else {
// 	            System.out.println("Select customer Message does not match. Actual: " + actualMessage + ", Expected: " + "Please Select Customer");
// 	        }
	        
// 	        driver.quit();
		
// 	}
	
// 	public void NegativeSelectAgreement() throws InterruptedException {
// 		WebDriver driver = openUploadAgreement();
		
// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		
// 		WebElement selectcustomer = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
// 		selectcustomer.click();
// 		Thread.sleep(4000);
// 		selectcustomer.sendKeys("ABUCHE100626");
// 		selectcustomer.sendKeys(Keys.ARROW_DOWN);
// 		selectcustomer.sendKeys(Keys.ENTER);
				
// 		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/form/div[2]/div/div[1]/button")).click();
		
// 		  WebElement errormessage = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/form/p"));
// 	        String actualMessage = errormessage.getText();

// 	        if (actualMessage.equals("please select Agreement")) {
// 	            System.out.println("select Agreement Message matches: " + actualMessage);
// 	        } else {
// 	            System.out.println("Select Agreement Message does not match. Actual: " + actualMessage + ", Expected: " + "Please Select Customer");
// 	        }
	        
// 	        driver.quit();
		
// 	}
	
// 	public void NegativeChooseFile() throws InterruptedException {
// 		WebDriver driver = openUploadAgreement();
		
// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		
// 		WebElement selectcustomer = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
// 		selectcustomer.click();
// 		Thread.sleep(4000);
// 		selectcustomer.sendKeys("ABUCHE100626");
// 		selectcustomer.sendKeys(Keys.ARROW_DOWN);
// 		selectcustomer.sendKeys(Keys.ENTER);
		
// 		WebElement selectAgreement = driver.findElement(By.xpath("//*[@id=\"customer\"]"));
// 		selectAgreement.click();
		
// 		 WebElement selectAgreementoption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[data-value='VBYB100907::AN100690']")));
// 		 selectAgreementoption.click();
				
// 		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/form/div[2]/div/div[1]/button")).click();
		
// 		  WebElement errormessage = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/form/p"));
// 	        String actualMessage = errormessage.getText();

// 	        if (actualMessage.equals("Please select a file to upload.")) {
// 	            System.out.println("select Message matches: " + actualMessage);
// 	        } else {
// 	            System.out.println("Select Message does not match. Actual: " + actualMessage + ", Expected: " + "Please Select Customer");
// 	        }
	        
// 	        driver.quit();
		
// 	}
	
// 	@Test
// 	public void runUploadAgreement() throws InterruptedException {
// //		openUploadAgreement();
// //		positiveUploadAgreement();
// //		NegativeSelectCustomer();
// //		NegativeSelectAgreement();
// 		NegativeChooseFile();
// 	}
// }
