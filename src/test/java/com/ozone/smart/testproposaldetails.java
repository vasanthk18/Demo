// package com.ozone.smart;

// import java.time.Duration;

// import org.junit.jupiter.api.Test;
// import org.openqa.selenium.Alert;
// import org.openqa.selenium.By;
// import org.openqa.selenium.Keys;
// //import org.openqa.selenium.edge.EdgeDriver;
// //import org.openqa.selenium.edge.EdgeOptions;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.interactions.Actions;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;




// public class testproposaldetails {
	
// 	String selectcustomervalue="SMICOR101670";
// 	String Selectvehiclevalue="Haojue | Express";
// 	String DownPaymentvalue="1000000";

	
// 	public WebDriver openProposaldetails() {
// 		testmainpage page = new testmainpage();

// 		WebDriver driver = page.titletest();

// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

// 		WebElement childcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("childid-0-1")));
// 		childcategory.click();

// 		WebElement childpagetitle = driver.findElement(By.id("QDE >> Proposal Creation"));
// 		String childtitle = childpagetitle.getText();
// 		System.out.println(childtitle.equals("QDE >> Proposal Creation") ? "Childpage " + childtitle + " verified"
// 				: "child page " + childtitle + " failed");
// 		return driver;
// 	}
	
// 	public WebDriver selectCustomerandProposal() throws InterruptedException {
// 		WebDriver driver = openProposaldetails();
		
// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		
// 	///////selecting customer//////
		
// 			WebElement selectcustomer = wait.until(ExpectedConditions.elementToBeClickable(By.id(":r7:")));
// 			selectcustomer.click();

// 			Thread.sleep(7000);
// 			WebElement from = driver.findElement(By.id(":r7:"));
// 			from.sendKeys(selectcustomervalue);
// 			Thread.sleep(2000);

// 			from.sendKeys(Keys.ARROW_DOWN);

// 			from.sendKeys(Keys.ENTER);
// 			Thread.sleep(1000);
			
// 	//////////selecting customer//////
			
// 	//////////selecting vehicle//////
// 			WebElement selectvehicle = driver.findElement(By.id(":rb:"));
// 			selectvehicle.click();
// 			Thread.sleep(2000);
// 			selectvehicle.sendKeys(Selectvehiclevalue);
// 			selectvehicle.sendKeys(Keys.ARROW_DOWN);
			
// 			selectvehicle.sendKeys(Keys.ENTER);
// 			Thread.sleep(2000);
// 	//////////selecting vehicle//////
// 			return driver;
// 	}

	
// 	public void positiveProposalDetails() throws InterruptedException {

//   WebDriver driver = selectCustomerandProposal();
  
// 	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

// //////////Entering Down payment//////
		
// 		WebElement enterdownpayment = driver.findElement(By.id(":rj:"));
// 		enterdownpayment.click();
// 		enterdownpayment.sendKeys(DownPaymentvalue);
// 		Thread.sleep(2000);
// //////////Entering Down payment//////
		
// //////////Clicking save button//////
// 		WebElement entersaveproposal = driver.findElement(By.id("saveproposal"));
// 		entersaveproposal.click();
// //////////Clicking save button//////	
		
// 		 try {
// 	            Alert alert = wait.until(ExpectedConditions.alertIsPresent()); // Wait for the alert to appear
// 	            System.out.println("Alert text: " + alert.getText()); // Print alert text for debugging
// 	            Thread.sleep(1000);
// 	            alert.accept(); // Click OK button
// 	            System.out.println("Alert handled successfully.");
// 	        } catch (Exception e) {
// 	            System.out.println("No alert was present.");
// 	        }
// 		 Thread.sleep(2000);
// //		 WebElement download =driver.findElement(By.xpath("//*[@id=\"myPrint\"]"));
//          WebElement downloadButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("myPrint")));

//          downloadButton.click();
         
//          boolean isDisabled = !downloadButton.isEnabled();
//          System.out.println(isDisabled ? "Button clicked and now disabled!" : "Button click not effective.");

//          Thread.sleep(2000);
// //         driver.navigate().back();
//          Actions actions = new Actions(driver);
//          actions.moveByOffset(10, 10).click().perform(); // Adjust the offset as needed
//          System.out.println("Clicked on the blank space to navigate back.");

//        driver.quit();
// 	}
	
// 	public void negativeSelectCustomer() throws InterruptedException {
		

// 		WebDriver driver = openProposaldetails();

		
// //////////selecting vehicle//////
// 		WebElement selectvehicle = driver.findElement(By.id(":rb:"));
// 		selectvehicle.click();
// 		Thread.sleep(2000);
// 		selectvehicle.sendKeys("Haojue | Express");
// 		selectvehicle.sendKeys(Keys.ARROW_DOWN);
		
// 		selectvehicle.sendKeys(Keys.ENTER);
// 		Thread.sleep(2000);
// //////////selecting vehicle//////

// //////////Entering Down payment//////
		
// 		WebElement enterdownpayment = driver.findElement(By.id(":rj:"));
// 		enterdownpayment.click();
// 		enterdownpayment.sendKeys("1000000");
// 		Thread.sleep(2000);
// //////////Entering Down payment//////
		
// //////////Clicking save button//////
// 		WebElement entersaveproposal = driver.findElement(By.id("saveproposal"));
// 		entersaveproposal.click();
// //////////Clicking save button//////	
		
//         WebElement errormessage = driver.findElement(By.id("errors"));
//         String actualMessage = errormessage.getText();

//         if (actualMessage.equals("please select a customer")) {
//             System.out.println("select customer Message matches: " + actualMessage);
//         } else {
//             System.out.println("Select customer Message does not match. Actual: " + actualMessage + ", Expected: " + "please select a customer");
//         }
//         driver.quit();
// 			}
	 
	
	
// 	public void negativeSelectvehile() throws InterruptedException {
		

// 		WebDriver driver = openProposaldetails();

// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

// ///////selecting customer//////
// 		WebElement selectcustomer = wait.until(ExpectedConditions.elementToBeClickable(By.id(":r7:")));
// 		selectcustomer.click();

// 		Thread.sleep(7000);
// 		WebElement from = driver.findElement(By.id(":r7:"));
// 		from.sendKeys("AKOFLE100416");
// 		Thread.sleep(2000);

// 		from.sendKeys(Keys.ARROW_DOWN);

// 		from.sendKeys(Keys.ENTER);
// 		Thread.sleep(1000);
		
// //////////selecting customer//////
		

// //////////Entering Down payment//////
		
// 		WebElement enterdownpayment = driver.findElement(By.id(":rj:"));
// 		enterdownpayment.click();
// 		enterdownpayment.sendKeys("1000000");
// 		Thread.sleep(2000);
// //////////Entering Down payment//////
		
// //////////Clicking save button//////
// 		WebElement entersaveproposal = driver.findElement(By.id("saveproposal"));
// 		entersaveproposal.click();
// //////////Clicking save button//////	
		
//         WebElement errormessage = driver.findElement(By.id("errors"));
//         String actualMessage = errormessage.getText();

//         if (actualMessage.equals("please select a vehicle")) {
//             System.out.println("vehicle Message matches: " + actualMessage);
//         } else {
//             System.out.println("vehicle Message does not match. Actual: " + actualMessage + ", Expected: " + "please select a vehicle");
//         }
//         driver.quit();
// 	}
	
	
// 	@Test
// 	public void runProposaldetails() throws InterruptedException {
// //		openProposaldetails();
// //		positiveProposalDetails();
// //		selectCustomerandProposal();
// //		negativeSelectCustomer();
// 		negativeSelectvehile();
// 	}
	
// }
