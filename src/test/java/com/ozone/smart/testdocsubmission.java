// package com.ozone.smart;

// import java.time.Duration;

// import org.junit.jupiter.api.Test;
// import org.openqa.selenium.Alert;
// import org.openqa.selenium.By;
// import org.openqa.selenium.JavascriptExecutor;
// import org.openqa.selenium.Keys;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;


// public class testdocsubmission {
	
// 	String SelectCustomervalue="SMICOR101670";
// 	String Applicationformvalue="Application verified";
// 	String nidvalue="Nationalid verified";
// 	String Stagerecommendationlettervalue="stgrecletter verified";
// 	String Lc1recommendationlettervalue="lc1recletter verified";
// 	String Guarantor1idvalue="G1id verified";
// 	String Guarantor2idvalue="G2id verified";
// 	String Stageidvalue="stageid verified";
// 	String Additionalremarksvalue="Docsub verified";

	
	
// 	public WebDriver openDocumentSubmission() {
// 		testmainpage page = new testmainpage();

// 		WebDriver driver = page.titletest();

// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

// 		WebElement childcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("childid-0-2")));
// 		childcategory.click();

// 		WebElement childpagetitle = driver.findElement(By.id("QDE >> Document Submission"));
// 		String childtitle = childpagetitle.getText();
// 		System.out.println(childtitle.equals("QDE >> Document Submission") ? "Childpage " + childtitle + " verified"
// 				: "child page " + childtitle + " failed");
// 		return driver;
// 	}
	
// 	public WebDriver selectCustomer() throws InterruptedException {
// 		WebDriver driver = openDocumentSubmission();
		
// 		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
		
// 		WebElement selectcustomer = wait.until(ExpectedConditions.elementToBeClickable(By.id(":r7:")));
// 		selectcustomer.click();

// 		Thread.sleep(5000);
// 		WebElement from = driver.findElement(By.id(":r7:"));
// 		from.sendKeys(SelectCustomervalue);
// 		Thread.sleep(500);

// 		from.sendKeys(Keys.ARROW_DOWN);

// 		from.sendKeys(Keys.ENTER);
// 		return driver;
// 	}
	
	
// 	public WebDriver filling() throws InterruptedException{

//        WebDriver driver = selectCustomer();
			
// 			WebElement applicationremarks = driver.findElement(By.id(":rb:"));
// 			applicationremarks.click();
// 			applicationremarks.sendKeys("Application verified");
			
// 			 driver.findElement(By.id("ckbox-0")).click();

// 			WebElement nationalidremarks = driver.findElement(By.id(":rd:"));
// 			nationalidremarks.click();
// 			nationalidremarks.sendKeys("Nationalid verified");
			
// 			 driver.findElement(By.id("ckbox-1")).click();
			
// 			WebElement stgrecletter = driver.findElement(By.id(":rf:"));
// 			stgrecletter.click();
// 			stgrecletter.sendKeys("stgrecletter verified");
			
// 			 driver.findElement(By.id("ckbox-2")).click();
			
// 			WebElement lc1recletter = driver.findElement(By.id(":rh:"));
// 			lc1recletter.click();
// 			lc1recletter.sendKeys("lc1recletter verified");
			
// 			 driver.findElement(By.id("ckbox-3")).click();
			
// 			WebElement G1id = driver.findElement(By.id(":rj:"));
// 			G1id.click();
// 			G1id.sendKeys("G1id verified");
			
// 			 driver.findElement(By.id("ckbox-4")).click();
			
// 			WebElement G2id = driver.findElement(By.id(":rl:"));
// 			G2id.click();
// 			G2id.sendKeys("G2id verified");
			
// 			 driver.findElement(By.id("ckbox-5")).click();
			
// 			WebElement stageid = driver.findElement(By.id(":rn:"));
// 			stageid.click();
// 			stageid.sendKeys("stageid verified");
			
// 			 driver.findElement(By.id("ckbox-6")).click();
							 
// 			 WebElement additionalremarks = driver.findElement(By.id(":rp:"));
// 			 additionalremarks.click();
// 			 additionalremarks.sendKeys("Docsub verified");
// 				Thread.sleep(1000);
// 				return driver;
				
// 	}
	
	
// 	public void positivedocsubmission() throws InterruptedException {
		
// 		WebDriver driver = filling();
				
// 			driver.findElement(By.id("Submitbtn")).click();
// 			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
// 			 Alert alert = wait.until(ExpectedConditions.alertIsPresent()); 
// 			 String responsemsg= alert.getText();
// 			 if (responsemsg.equals("DC submission for : "+SelectCustomervalue+" saved successfully.")) {
// 		            System.out.println("Message matches: " + responsemsg);
// 		        } else {
// 		            System.out.println(" Message does not match. Actual: " + responsemsg + ", Expected: " + "DC submission for : "+SelectCustomervalue+" saved successfully.");
// 		        }

// 		driver.quit();
// 	} 
	
	
// 	public void negativeselectcustomer() throws InterruptedException {

// 		WebDriver driver = openDocumentSubmission();

// 	///////selecting customer//////
// //			WebElement selectcustomer = wait.until(ExpectedConditions.elementToBeClickable(By.id(":r7:")));
// //			selectcustomer.click();
// //
// //			Thread.sleep(5000);
// //			WebElement from = driver.findElement(By.id(":r7:"));
// //			from.sendKeys("BUSYOU101502");
// //			Thread.sleep(2000);
// //
// //			from.sendKeys(Keys.ARROW_DOWN);
// //
// //			from.sendKeys(Keys.ENTER);
// //			Thread.sleep(500);
			
// 	//////////selecting customer//////
			
// 			WebElement applicationremarks = driver.findElement(By.id(":rb:"));
// 			applicationremarks.click();
// 			applicationremarks.sendKeys("Application verified");

// 			driver.findElement(By.id("ckbox-0")).click();

			
// 			WebElement nationalidremarks = driver.findElement(By.id(":rd:"));
// 			nationalidremarks.click();
// 			nationalidremarks.sendKeys("Nationalid verified");
// 			 driver.findElement(By.id("ckbox-1")).click();

			
// 			WebElement stgrecletter = driver.findElement(By.id(":rf:"));
// 			stgrecletter.click();
// 			stgrecletter.sendKeys("stgrecletter verified");
// 			 driver.findElement(By.id("ckbox-2")).click();

			
// 			WebElement lc1recletter = driver.findElement(By.id(":rh:"));
// 			lc1recletter.click();
// 			lc1recletter.sendKeys("lc1recletter verified");
// 			 driver.findElement(By.id("ckbox-3")).click();

			
// 			WebElement G1id = driver.findElement(By.id(":rj:"));
// 			G1id.click();
// 			G1id.sendKeys("G1id verified");
// 			 driver.findElement(By.id("ckbox-4")).click();

			
// 			WebElement G2id = driver.findElement(By.id(":rl:"));
// 			G2id.click();
// 			G2id.sendKeys("G2id verified");

// 			 driver.findElement(By.id("ckbox-5")).click();

			
// 			WebElement stageid = driver.findElement(By.id(":rn:"));
// 			stageid.click();
// 			stageid.sendKeys("stageid verified");

// 			 driver.findElement(By.id("ckbox-6")).click();
				 
// 			 WebElement additionalremarks = driver.findElement(By.id(":rp:"));
// 			 additionalremarks.click();
// 			 additionalremarks.sendKeys("Docsub verified");
// 				Thread.sleep(1000);
				
// 			driver.findElement(By.id("Submitbtn")).click();
// 			Thread.sleep(1000);

			
// 			   WebElement errormessage = driver.findElement(By.id("errors"));
// 		        String actualMessage = errormessage.getText();

// 		        if (actualMessage.equals("Please Select Customer")) {
// 		            System.out.println("select customer Message matches: " + actualMessage);
// 		        } else {
// 		            System.out.println("Select customer Message does not match. Actual: " + actualMessage + ", Expected: " + "Please Select Customer");
// 		        }

// 		driver.quit();
// 	}
	
	

// 	public void negativeapplicationremarks() throws InterruptedException {
// 		WebDriver driver = filling();
// 		JavascriptExecutor js = (JavascriptExecutor) driver;
// 		js.executeScript("window.scrollTo(0, 100);");
// 		WebElement applicationremarks = driver.findElement(By.id(":rb:"));
// 		applicationremarks.click();
// 		Thread.sleep(1000);
// 		applicationremarks.sendKeys(Keys.chord(Keys.CONTROL, "a")); // Select all text
// 		applicationremarks.sendKeys(Keys.DELETE);
		
// 		driver.findElement(By.id("Submitbtn")).click();
// 		Thread.sleep(1000);

// 		   WebElement errormessage = driver.findElement(By.id("errors"));
// 	        String actualMessage = errormessage.getText();

// 	        if (actualMessage.equals("Please Enter Remarks for Application form")) {
// 	            System.out.println("select customer Message matches: " + actualMessage);
// 	        } else {
// 	            System.out.println("Select customer Message does not match. Actual: " + actualMessage + ", Expected: " + "Please Enter Remarks for Application form");
// 	        }

// 	driver.quit();
// 	} 
	
	
	
// 	public void negativenationalid() throws InterruptedException {

// 		WebDriver driver = filling();
// 		JavascriptExecutor js = (JavascriptExecutor) driver;
// 		js.executeScript("window.scrollTo(0, 100);");
// 		WebElement nationalidremarks = driver.findElement(By.id(":rd:"));
// 		nationalidremarks.click();
// 		Thread.sleep(1000);
// 		nationalidremarks.sendKeys(Keys.chord(Keys.CONTROL, "a")); // Select all text
// 		nationalidremarks.sendKeys(Keys.DELETE);
		
// 		driver.findElement(By.id("Submitbtn")).click();
// 		Thread.sleep(1000);

// 		   WebElement errormessage = driver.findElement(By.id("errors"));
// 	        String actualMessage = errormessage.getText();

// 	        if (actualMessage.equals("Please Enter Remarks for National id")) {
// 	            System.out.println("select customer Message matches: " + actualMessage);
// 	        } else {
// 	            System.out.println("Select customer Message does not match. Actual: " + actualMessage + ", Expected: " + "Please Enter Remarks for National id");
// 	        }

// 	driver.quit();
// 	} 
	

// 	public void negativestgrecomletter() throws InterruptedException {	
		
// 		WebDriver driver = filling();
// 		JavascriptExecutor js = (JavascriptExecutor) driver;
// 		js.executeScript("window.scrollTo(0, 100);");
// 		WebElement stgrecletter = driver.findElement(By.id(":rf:"));
// 		stgrecletter.click();
// 		Thread.sleep(1000);
// 		stgrecletter.sendKeys(Keys.chord(Keys.CONTROL, "a")); // Select all text
// 		stgrecletter.sendKeys(Keys.DELETE);
		
// 		driver.findElement(By.id("Submitbtn")).click();
// 		Thread.sleep(1000);

// 		   WebElement errormessage = driver.findElement(By.id("errors"));
// 	        String actualMessage = errormessage.getText();

// 	        if (actualMessage.equals("Please Enter Remarks for Stage recommendation letter")) {
// 	            System.out.println("select customer Message matches: " + actualMessage);
// 	        } else {
// 	            System.out.println("Select customer Message does not match. Actual: " + actualMessage + ", Expected: " + "Please Enter Remarks for Stage recommendation letter");
// 	        }

// 	driver.quit();
// 	} 
	
	
// 	public void negativelc1recomletter() throws InterruptedException {	
		
// 		WebDriver driver = filling();
// 		JavascriptExecutor js = (JavascriptExecutor) driver;
// 		js.executeScript("window.scrollTo(0, 120);");
// 		WebElement lc1recletter = driver.findElement(By.id(":rh:"));
// 		lc1recletter.click();
// 		lc1recletter.sendKeys(Keys.chord(Keys.CONTROL, "a")); // Select all text
// 		lc1recletter.sendKeys(Keys.DELETE);
		
// 		driver.findElement(By.id("Submitbtn")).click();
// 		Thread.sleep(1000);

// 		   WebElement errormessage = driver.findElement(By.id("errors"));
// 	        String actualMessage = errormessage.getText();

// 	        if (actualMessage.equals("Please Enter Remarks for LC1 recommendation letter")) {
// 	            System.out.println("select customer Message matches: " + actualMessage);
// 	        } else {
// 	            System.out.println("Select customer Message does not match. Actual: " + actualMessage + ", Expected: " + "Please Enter Remarks for LC1 recommendation letter");
// 	        }

// 	driver.quit();
// 	} 
	
	
// 	public void negativeG1id() throws InterruptedException {	
		
// 		WebDriver driver = filling();
// 		JavascriptExecutor js = (JavascriptExecutor) driver;
// 		js.executeScript("window.scrollTo(0, 120);");
// 		WebElement G1id = driver.findElement(By.id(":rj:"));
// 		G1id.click();
// 		G1id.sendKeys(Keys.chord(Keys.CONTROL, "a")); // Select all text
// 		G1id.sendKeys(Keys.DELETE);
		
// 		driver.findElement(By.id("Submitbtn")).click();
// 		Thread.sleep(1000);

// 		   WebElement errormessage = driver.findElement(By.id("errors"));
// 	        String actualMessage = errormessage.getText();

// 	        if (actualMessage.equals("Please Enter Remarks for Guarantor 1 id")) {
// 	            System.out.println("select customer Message matches: " + actualMessage);
// 	        } else {
// 	            System.out.println("Select customer Message does not match. Actual: " + actualMessage + ", Expected: " + "Please Enter Remarks for Guarantor 1 id");
// 	        }

// 	driver.quit();
// 	} 
	

// 	public void negativeG2id() throws InterruptedException {	
		
// 		WebDriver driver = filling();
// 		JavascriptExecutor js = (JavascriptExecutor) driver;
// 		js.executeScript("window.scrollTo(0, 120);");
// 		WebElement G2id = driver.findElement(By.id(":rl:"));
// 		G2id.click();
// 		G2id.sendKeys(Keys.chord(Keys.CONTROL, "a")); // Select all text
// 		G2id.sendKeys(Keys.DELETE);
		
// 		driver.findElement(By.id("Submitbtn")).click();
// 		Thread.sleep(1000);

// 		   WebElement errormessage = driver.findElement(By.id("errors"));
// 	        String actualMessage = errormessage.getText();

// 	        if (actualMessage.equals("Please Enter Remarks for Guarantor 2 id")) {
// 	            System.out.println("select customer Message matches: " + actualMessage);
// 	        } else {
// 	            System.out.println("Select customer Message does not match. Actual: " + actualMessage + ", Expected: " + "Please Enter Remarks for Guarantor 2 id");
// 	        }

// 	driver.quit();
// 	} 
	

// 	public void negativestageid() throws InterruptedException {	
		
// 		WebDriver driver = filling();
// 		JavascriptExecutor js = (JavascriptExecutor) driver;
// 		js.executeScript("window.scrollTo(0, 120);");
// 		WebElement stageid = driver.findElement(By.id(":rn:"));
// 		stageid.click();
// 		stageid.sendKeys(Keys.chord(Keys.CONTROL, "a")); // Select all text
// 		stageid.sendKeys(Keys.DELETE);
		
// 		driver.findElement(By.id("Submitbtn")).click();
// 		Thread.sleep(1000);

// 		   WebElement errormessage = driver.findElement(By.id("errors"));
// 	        String actualMessage = errormessage.getText();

// 	        if (actualMessage.equals("Please Enter Remarks for Stage id")) {
// 	            System.out.println("select customer Message matches: " + actualMessage);
// 	        } else {
// 	            System.out.println("Select customer Message does not match. Actual: " + actualMessage + ", Expected: " + "Please Enter Remarks for Stage id");
// 	        }

// 	driver.quit();
// 	}
	
	
// 	@Test
// 	public void runDocSubmision() throws InterruptedException {
// //		negativestageid();
// //		negativeapplicationremarks();
// //		negativeG1id();
// //		negativeG2id();
// //		negativelc1recomletter();
// //		negativenationalid();
// //		negativeselectcustomer();
// //		negativestageid();
// //		negativestgrecomletter();
// //		openDocumentSubmission();
// 		positivedocsubmission();
// 	}
// }
