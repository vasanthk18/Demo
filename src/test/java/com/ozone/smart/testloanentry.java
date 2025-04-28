// package com.ozone.smart;

// import java.time.Duration;

// import org.junit.jupiter.api.Test;
// import org.openqa.selenium.By;
// import org.openqa.selenium.Keys;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;

// public class testloanentry {

// 	String selectedproposalvalue="VBYB101385";
// 	String NoofInstallmentsvalue="52";
// 	String InterestRatevalue="35";
// 	String cusidvalue ="AKOFLE100416";
// 	String loanamtvalue="4,850,000";
// 	String MarMoneyvalue="1000000";
	
	
	
// 	public void openandsaveloan() throws InterruptedException{
// 		testmainpage page = new testmainpage();

// 		WebDriver driver = page.testdetaileddataentry();

// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
// 		WebElement childcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("childid-1-2")));
// 		childcategory.click();
// 		WebElement childpagetitle = driver.findElement(By.id("DDE {'>>'} Loan Entry"));
// 		String childtitle = childpagetitle.getText();
// 		System.out.println(childtitle.equals("DDE >> Loan Entry") ? "Childpage " + childtitle + " verified"
// 				: "child page " + childtitle + " failed");
	
// 	WebElement selectproposal = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
// 	selectproposal.click();

// 	Thread.sleep(4000);
// 	WebElement from = driver.findElement(By.xpath("//*[@id=\":r7:\"]"));
// //	WebElement from = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
// 	from.sendKeys(selectedproposalvalue);
// 	Thread.sleep(500);

// 	from.sendKeys(Keys.ARROW_DOWN);

// 	from.sendKeys(Keys.ENTER);
	
// 	  WebElement CustomerId = driver.findElement(By.xpath("//*[@id=\":rb:\"]"));
//       String CustomerIdvalue = CustomerId.getAttribute("value");
//       if (CustomerIdvalue.equals(cusidvalue)) {
//           System.out.println("The CustomerId values match!");
//       } else {
//           System.out.println("The CustomerId values do not match.");
//       }
      
//       WebElement LoanAmount = driver.findElement(By.xpath("//*[@id=\":rd:\"]"));
//       String LoanAmountvalue = LoanAmount.getAttribute("value");
//       if (LoanAmountvalue.equals(loanamtvalue)) {
//           System.out.println("The LoanAmount values match!");
//       } else {
//           System.out.println("The LoanAmount values do not match.");
//       }
      
//       WebElement MarginMoney = driver.findElement(By.xpath("//*[@id=\":rf:\"]"));
//       String MarginMoneyvalue = MarginMoney.getAttribute("value");
//       if (MarginMoneyvalue.equals(MarMoneyvalue)) {
//           System.out.println("The MarginMoney values match!");
//       } else {
//           System.out.println("The MarginMoney values do not match.");
//       }
      
//       WebElement PaymentMode = driver.findElement(By.xpath("//*[@id=\"paymodedropdown\"]"));
//       PaymentMode.click();
//       Thread.sleep(1000);
//       WebElement weeklyOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@role='option' and @tabindex='0']")));
//       weeklyOption.click();
      
//       WebElement NoofInstallments = driver.findElement(By.xpath("//*[@id=\":rj:\"]"));
//       NoofInstallments.click();
//       NoofInstallments.sendKeys(NoofInstallmentsvalue);
      
//       WebElement InterestRate = driver.findElement(By.xpath("//*[@id=\":rl:\"]"));
//       InterestRate.click();
//       InterestRate.sendKeys(InterestRatevalue);
      
//       driver.findElement(By.xpath("//*[@id=\"calcIns\"]")).click();
      
//       Thread.sleep(1500);
       
//       WebElement trackerfeevisible = driver.findElement(By.xpath("//*[@id=\":rn:\"]"));
//       String trackerfeevisiblevalue = trackerfeevisible.getAttribute("value");
      
//       if (trackerfeevisiblevalue != null && !trackerfeevisiblevalue.isEmpty()) {
//     	  System.out.println("save button");
// //    	  driver.findElement(By.xpath("//*[@id=\"saveLoan\"]")).click();
//       }else {
//     	  System.out.println("nullvalue");
//       }
      
//       driver.quit();
// 	}
	
	
// 	public void negativeselectproposal() throws InterruptedException {
// 		testmainpage page = new testmainpage();

// 		WebDriver driver = page.testdetaileddataentry();

// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
// 		WebElement childcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("childid-1-2")));
// 		childcategory.click();
// 		WebElement childpagetitle = driver.findElement(By.id("DDE {'>>'} Loan Entry"));
// 		String childtitle = childpagetitle.getText();
// 		System.out.println(childtitle.equals("DDE >> Loan Entry") ? "Childpage " + childtitle + " verified"
// 				: "child page " + childtitle + " failed");
	

	
// 	  WebElement CustomerId = driver.findElement(By.xpath("//*[@id=\":rb:\"]"));
//       String CustomerIdvalue = CustomerId.getAttribute("value");
//       if (CustomerIdvalue.equals(cusidvalue)) {
//           System.out.println("The CustomerId values match!");
//       } else {
//           System.out.println("The CustomerId values do not match.");
//       }
      
//       WebElement LoanAmount = driver.findElement(By.xpath("//*[@id=\":rd:\"]"));
//       String LoanAmountvalue = LoanAmount.getAttribute("value");
//       if (LoanAmountvalue.equals(loanamtvalue)) {
//           System.out.println("The LoanAmount values match!");
//       } else {
//           System.out.println("The LoanAmount values do not match.");
//       }
      
//       WebElement MarginMoney = driver.findElement(By.xpath("//*[@id=\":rf:\"]"));
//       String MarginMoneyvalue = MarginMoney.getAttribute("value");
//       if (MarginMoneyvalue.equals(MarMoneyvalue)) {
//           System.out.println("The MarginMoney values match!");
//       } else {
//           System.out.println("The MarginMoney values do not match.");
//       }
      
//       WebElement PaymentMode = driver.findElement(By.xpath("//*[@id=\"paymodedropdown\"]"));
//       PaymentMode.click();
//       Thread.sleep(1000);
//       WebElement weeklyOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@role='option' and @tabindex='0']")));
//       weeklyOption.click();
      
//       WebElement NoofInstallments = driver.findElement(By.xpath("//*[@id=\":rj:\"]"));
//       NoofInstallments.click();
//       NoofInstallments.sendKeys(NoofInstallmentsvalue);
      
//       WebElement InterestRate = driver.findElement(By.xpath("//*[@id=\":rl:\"]"));
//       InterestRate.click();
//       InterestRate.sendKeys(InterestRatevalue);
      
//       driver.findElement(By.xpath("//*[@id=\"calcIns\"]")).click();
      
//       Thread.sleep(1500);
      
//       WebElement errorresponse = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/p"));
//       String errorresponsevalue = errorresponse.getText();
//       if (errorresponsevalue.equals("Please Select Proposal")) {
//           System.out.println("The errorresponse values match! : "+errorresponsevalue);
//       } else {
//           System.out.println("The errorresponse values do not match.");
//       }
       
     
//       driver.quit();
// 	}
	
// 	public void negativepaymode() throws InterruptedException{
// 		testmainpage page = new testmainpage();

// 		WebDriver driver = page.testdetaileddataentry();

// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
// 		WebElement childcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("childid-1-2")));
// 		childcategory.click();
// 		WebElement childpagetitle = driver.findElement(By.id("DDE {'>>'} Loan Entry"));
// 		String childtitle = childpagetitle.getText();
// 		System.out.println(childtitle.equals("DDE >> Loan Entry") ? "Childpage " + childtitle + " verified"
// 				: "child page " + childtitle + " failed");
	
// 	WebElement selectproposal = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
// 	selectproposal.click();

// 	Thread.sleep(4000);
// 	WebElement from = driver.findElement(By.xpath("//*[@id=\":r7:\"]"));
// //	WebElement from = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
// 	from.sendKeys(selectedproposalvalue);
// 	Thread.sleep(500);

// 	from.sendKeys(Keys.ARROW_DOWN);

// 	from.sendKeys(Keys.ENTER);
	
// 	  WebElement CustomerId = driver.findElement(By.xpath("//*[@id=\":rb:\"]"));
//       String CustomerIdvalue = CustomerId.getAttribute("value");
//       if (CustomerIdvalue.equals(cusidvalue)) {
//           System.out.println("The CustomerId values match!");
//       } else {
//           System.out.println("The CustomerId values do not match.");
//       }
      
//       WebElement LoanAmount = driver.findElement(By.xpath("//*[@id=\":rd:\"]"));
//       String LoanAmountvalue = LoanAmount.getAttribute("value");
//       if (LoanAmountvalue.equals(loanamtvalue)) {
//           System.out.println("The LoanAmount values match!");
//       } else {
//           System.out.println("The LoanAmount values do not match.");
//       }
      
//       WebElement MarginMoney = driver.findElement(By.xpath("//*[@id=\":rf:\"]"));
//       String MarginMoneyvalue = MarginMoney.getAttribute("value");
//       if (MarginMoneyvalue.equals(MarMoneyvalue)) {
//           System.out.println("The MarginMoney values match!");
//       } else {
//           System.out.println("The MarginMoney values do not match.");
//       }
      
      
//       WebElement NoofInstallments = driver.findElement(By.xpath("//*[@id=\":rj:\"]"));
//       NoofInstallments.click();
//       NoofInstallments.sendKeys(NoofInstallmentsvalue);
      
//       WebElement InterestRate = driver.findElement(By.xpath("//*[@id=\":rl:\"]"));
//       InterestRate.click();
//       InterestRate.sendKeys(InterestRatevalue);
      
//       driver.findElement(By.xpath("//*[@id=\"calcIns\"]")).click();
      
//       Thread.sleep(1500);
       
//       WebElement errorresponse = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/p"));
//       String errorresponsevalue = errorresponse.getText();
//       if (errorresponsevalue.equals("Please Select Payment Mode")) {
//           System.out.println("The errorresponse values match! : "+errorresponsevalue);
//       } else {
//           System.out.println("The errorresponse values do not match.");
//       }
      
//       driver.quit();
// 	}
	
// 	public void negativenoofinstallments() throws InterruptedException{
// 		testmainpage page = new testmainpage();

// 		WebDriver driver = page.testdetaileddataentry();

// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
// 		WebElement childcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("childid-1-2")));
// 		childcategory.click();
// 		WebElement childpagetitle = driver.findElement(By.id("DDE {'>>'} Loan Entry"));
// 		String childtitle = childpagetitle.getText();
// 		System.out.println(childtitle.equals("DDE >> Loan Entry") ? "Childpage " + childtitle + " verified"
// 				: "child page " + childtitle + " failed");
	
// 	WebElement selectproposal = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
// 	selectproposal.click();

// 	Thread.sleep(4000);
// 	WebElement from = driver.findElement(By.xpath("//*[@id=\":r7:\"]"));
// //	WebElement from = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
// 	from.sendKeys(selectedproposalvalue);
// 	Thread.sleep(500);

// 	from.sendKeys(Keys.ARROW_DOWN);

// 	from.sendKeys(Keys.ENTER);
	
// 	  WebElement CustomerId = driver.findElement(By.xpath("//*[@id=\":rb:\"]"));
//       String CustomerIdvalue = CustomerId.getAttribute("value");
//       if (CustomerIdvalue.equals(cusidvalue)) {
//           System.out.println("The CustomerId values match!");
//       } else {
//           System.out.println("The CustomerId values do not match.");
//       }
      
//       WebElement LoanAmount = driver.findElement(By.xpath("//*[@id=\":rd:\"]"));
//       String LoanAmountvalue = LoanAmount.getAttribute("value");
//       if (LoanAmountvalue.equals(loanamtvalue)) {
//           System.out.println("The LoanAmount values match!");
//       } else {
//           System.out.println("The LoanAmount values do not match.");
//       }
      
//       WebElement MarginMoney = driver.findElement(By.xpath("//*[@id=\":rf:\"]"));
//       String MarginMoneyvalue = MarginMoney.getAttribute("value");
//       if (MarginMoneyvalue.equals(MarMoneyvalue)) {
//           System.out.println("The MarginMoney values match!");
//       } else {
//           System.out.println("The MarginMoney values do not match.");
//       }
      
//       WebElement PaymentMode = driver.findElement(By.xpath("//*[@id=\"paymodedropdown\"]"));
//       PaymentMode.click();
//       Thread.sleep(1000);
//       WebElement weeklyOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@role='option' and @tabindex='0']")));
//       weeklyOption.click();

      
//       WebElement InterestRate = driver.findElement(By.xpath("//*[@id=\":rl:\"]"));
//       InterestRate.click();
//       InterestRate.sendKeys(InterestRatevalue);
      
//       driver.findElement(By.xpath("//*[@id=\"calcIns\"]")).click();
      
//       Thread.sleep(1500);
       
//       WebElement errorresponse = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/p"));
//       String errorresponsevalue = errorresponse.getText();
//       if (errorresponsevalue.equals("Please Enter No of Installments")) {
//           System.out.println("The errorresponse values match! : "+errorresponsevalue);
//       } else {
//           System.out.println("The errorresponse values do not match.");
//       }
      
//       driver.quit();
// 	}
	
// 	public void negativeInterestRate() throws InterruptedException{
// 		testmainpage page = new testmainpage();

// 		WebDriver driver = page.testdetaileddataentry();

// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
// 		WebElement childcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("childid-1-2")));
// 		childcategory.click();
// 		WebElement childpagetitle = driver.findElement(By.id("DDE {'>>'} Loan Entry"));
// 		String childtitle = childpagetitle.getText();
// 		System.out.println(childtitle.equals("DDE >> Loan Entry") ? "Childpage " + childtitle + " verified"
// 				: "child page " + childtitle + " failed");
	
// 	WebElement selectproposal = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
// 	selectproposal.click();

// 	Thread.sleep(4000);
// 	WebElement from = driver.findElement(By.xpath("//*[@id=\":r7:\"]"));
// //	WebElement from = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
// 	from.sendKeys(selectedproposalvalue);
// 	Thread.sleep(500);

// 	from.sendKeys(Keys.ARROW_DOWN);

// 	from.sendKeys(Keys.ENTER);
	
// 	  WebElement CustomerId = driver.findElement(By.xpath("//*[@id=\":rb:\"]"));
//       String CustomerIdvalue = CustomerId.getAttribute("value");
//       if (CustomerIdvalue.equals(cusidvalue)) {
//           System.out.println("The CustomerId values match!");
//       } else {
//           System.out.println("The CustomerId values do not match.");
//       }
      
//       WebElement LoanAmount = driver.findElement(By.xpath("//*[@id=\":rd:\"]"));
//       String LoanAmountvalue = LoanAmount.getAttribute("value");
//       if (LoanAmountvalue.equals(loanamtvalue)) {
//           System.out.println("The LoanAmount values match!");
//       } else {
//           System.out.println("The LoanAmount values do not match.");
//       }
      
//       WebElement MarginMoney = driver.findElement(By.xpath("//*[@id=\":rf:\"]"));
//       String MarginMoneyvalue = MarginMoney.getAttribute("value");
//       if (MarginMoneyvalue.equals(MarMoneyvalue)) {
//           System.out.println("The MarginMoney values match!");
//       } else {
//           System.out.println("The MarginMoney values do not match.");
//       }
      
//       WebElement PaymentMode = driver.findElement(By.xpath("//*[@id=\"paymodedropdown\"]"));
//       PaymentMode.click();
//       Thread.sleep(1000);
//       WebElement weeklyOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@role='option' and @tabindex='0']")));
//       weeklyOption.click();

//       WebElement NoofInstallments = driver.findElement(By.xpath("//*[@id=\":rj:\"]"));
//       NoofInstallments.click();
//       NoofInstallments.sendKeys(NoofInstallmentsvalue);

//       driver.findElement(By.xpath("//*[@id=\"calcIns\"]")).click();
      
//       Thread.sleep(1500);
       
//       WebElement errorresponse = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/p"));
//       String errorresponsevalue = errorresponse.getText();
//       if (errorresponsevalue.equals("Please Enter Interest Rate")) {
//           System.out.println("The errorresponse values match! : "+errorresponsevalue);
//       } else {
//           System.out.println("The errorresponse values do not match.");
//       }
      
//       driver.quit();
// 	}
	
// 	public void negativecalculateinsurance() throws InterruptedException{
// 		testmainpage page = new testmainpage();

// 		WebDriver driver = page.testdetaileddataentry();

// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
// 		WebElement childcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("childid-1-2")));
// 		childcategory.click();
// 		WebElement childpagetitle = driver.findElement(By.id("DDE {'>>'} Loan Entry"));
// 		String childtitle = childpagetitle.getText();
// 		System.out.println(childtitle.equals("DDE >> Loan Entry") ? "Childpage " + childtitle + " verified"
// 				: "child page " + childtitle + " failed");
	
// 	WebElement selectproposal = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
// 	selectproposal.click();

// 	Thread.sleep(4000);
// 	WebElement from = driver.findElement(By.xpath("//*[@id=\":r7:\"]"));
// //	WebElement from = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
// 	from.sendKeys(selectedproposalvalue);
// 	Thread.sleep(500);

// 	from.sendKeys(Keys.ARROW_DOWN);

// 	from.sendKeys(Keys.ENTER);
	
// 	  WebElement CustomerId = driver.findElement(By.xpath("//*[@id=\":rb:\"]"));
//       String CustomerIdvalue = CustomerId.getAttribute("value");
//       if (CustomerIdvalue.equals(cusidvalue)) {
//           System.out.println("The CustomerId values match!");
//       } else {
//           System.out.println("The CustomerId values do not match.");
//       }
      
//       WebElement LoanAmount = driver.findElement(By.xpath("//*[@id=\":rd:\"]"));
//       String LoanAmountvalue = LoanAmount.getAttribute("value");
//       if (LoanAmountvalue.equals(loanamtvalue)) {
//           System.out.println("The LoanAmount values match!");
//       } else {
//           System.out.println("The LoanAmount values do not match.");
//       }
      
//       WebElement MarginMoney = driver.findElement(By.xpath("//*[@id=\":rf:\"]"));
//       String MarginMoneyvalue = MarginMoney.getAttribute("value");
//       if (MarginMoneyvalue.equals(MarMoneyvalue)) {
//           System.out.println("The MarginMoney values match!");
//       } else {
//           System.out.println("The MarginMoney values do not match.");
//       }
      
//       WebElement PaymentMode = driver.findElement(By.xpath("//*[@id=\"paymodedropdown\"]"));
//       PaymentMode.click();
//       Thread.sleep(1000);
//       WebElement weeklyOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@role='option' and @tabindex='0']")));
//       weeklyOption.click();

//       WebElement NoofInstallments = driver.findElement(By.xpath("//*[@id=\":rj:\"]"));
//       NoofInstallments.click();
//       NoofInstallments.sendKeys(NoofInstallmentsvalue);
      
//       WebElement InterestRate = driver.findElement(By.xpath("//*[@id=\":rl:\"]"));
//       InterestRate.click();
//       InterestRate.sendKeys(InterestRatevalue);
   
//       Thread.sleep(1500);
       
// 	  driver.findElement(By.xpath("//*[@id=\"saveLoan\"]")).click();

      
//       WebElement errorresponse = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/p"));
//       String errorresponsevalue = errorresponse.getText();
//       if (errorresponsevalue.equals("Please Calculate the Insurance Fee")) {
//           System.out.println("The errorresponse values match! : "+errorresponsevalue);
//       } else {
//           System.out.println("The errorresponse values do not match.");
//       }
      
//       driver.quit();
// 	}
	
// 	@Test
// 	public void runmethod() throws InterruptedException {
// //		openandsaveloan();
// //		negativepaymode();
// //		negativenoofinstallments();
// //		negativeInterestRate();
// 		negativecalculateinsurance();
// 	}
// }
