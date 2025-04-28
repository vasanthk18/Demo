// package com.ozone.smart;

// import java.time.Duration;

// import org.junit.jupiter.api.Test;
// import org.openqa.selenium.By;
// import org.openqa.selenium.Keys;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;

// public class testuploaddocument {
	

// 	public WebDriver openuploaddoc() throws InterruptedException {
// 		testmainpage page = new testmainpage();

// 		WebDriver driver = page.testdetaileddataentry();

// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
// 		WebElement childcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("childid-1-3")));
// 		childcategory.click();
// 		WebElement childpagetitle = driver.findElement(By.id("DDE {'>>'} Upload Document"));
// 		String childtitle = childpagetitle.getText();
// 		System.out.println(childtitle.equals("DDE >> Upload Document") ? "Childpage " + childtitle + " verified"
// 				: "child page " + childtitle + " failed");
// 		return driver;
// 	}
	
// 	public void positiveUploadDocument() throws InterruptedException {
// 		WebDriver driver= openuploaddoc();
		
// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

// 		WebElement selectcustomer = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
// 		selectcustomer.click();
// 		Thread.sleep(4000);
// 		selectcustomer.sendKeys("ABUCHE100626");
// 		selectcustomer.sendKeys(Keys.ARROW_DOWN);
// 		selectcustomer.sendKeys(Keys.ENTER);
		
// 		Thread.sleep(1500);
		
// 		 WebElement CustomerID = driver.findElement(By.xpath("//*[@id=\":rb:\"]"));
// 	      String CustomerIDvalue = CustomerID.getAttribute("value");
	      
// 	      if (CustomerIDvalue != null && !CustomerIDvalue.isEmpty()) {
// //	    	  System.out.println("save button");
// 	    	  driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/form/div[2]/label")).click();
// 	      }else {
// 	    	  System.out.println("nullvalue");
// 	      }
		
// 	}
	
// 	public void negativeselectcustomer() throws InterruptedException {
		
// 		WebDriver driver= openuploaddoc();
//   	  driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/form/div[3]/div/div[1]/button")).click();

//   	 WebElement errormsg = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/form/div[3]/p"));
//   	String errormsgvalue = errormsg.getText();
//   	if(errormsgvalue.equals("please select a customer")) {
//         System.out.println("The errorresponse values match! : "+errormsgvalue);
//   	}
//   	else {
//         System.out.println("The errorresponse values do not match.");
//   	}
//   	driver.quit();
// 	}
	
// public void negativeChooseFile() throws InterruptedException {
		
// 		WebDriver driver= openuploaddoc();
// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

// 		WebElement selectcustomer = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
// 		selectcustomer.click();
// 		Thread.sleep(4000);
// 		selectcustomer.sendKeys("ABUCHE100626");
// 		selectcustomer.sendKeys(Keys.ARROW_DOWN);
// 		selectcustomer.sendKeys(Keys.ENTER);
		
// 		Thread.sleep(1500);
		
// 		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/form/div[3]/div/div[1]/button")).click();
		
//   	 WebElement errormsg = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/form/div[3]/p"));
//   	String errormsgvalue = errormsg.getText();
//   	if(errormsgvalue.equals("Please select a file to upload.")) {
//         System.out.println("The errorresponse values match! : "+errormsgvalue);
//   	}
//   	else {
//         System.out.println("The errorresponse values do not match.");
//   	}
//   	driver.quit();
// 	}
	
	
// 	@Test
// 	public void runUploadDocument() throws InterruptedException {
// //		openuploaddoc();
// //		positiveUploadDocument();
// //		negativeselectcustomer();
// 		negativeChooseFile();
// 		}
// }


