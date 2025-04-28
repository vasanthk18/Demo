 package com.ozone.smart;

 //import org.openqa.selenium.chrome.ChromeDriver;
 import org.openqa.selenium.edge.EdgeDriver;
 import org.openqa.selenium.edge.EdgeOptions;
 import org.openqa.selenium.support.ui.ExpectedConditions;
 import org.openqa.selenium.support.ui.WebDriverWait;

 import java.time.Duration;

 import org.junit.jupiter.api.Test;
 import org.openqa.selenium.By;
 import org.openqa.selenium.WebDriver;
 import org.openqa.selenium.WebElement;


 public class testmainpage {
 	@Test
      public WebDriver titletest() {
		
 		 EdgeOptions options = new EdgeOptions();
 	        options.setAcceptInsecureCerts(true);
	        
 	 WebDriver driver = new EdgeDriver(options);
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

 //	 ChromeDriver driver = new ChromeDriver();
 //	 EdgeDriver driver =new EdgeDriver();

 	 driver.get("http://localhost:5173/");
      driver.manage().window().maximize();

 	 String titlename = driver.getTitle();
	  
 	 if(titlename.equals("Vuga Boda Yo")) 
 	 {
 		 System.out.println("TEST CASE PASSED");
 	}
 	 else {
 		 System.out.println("TEST CASE Failed");
 	 }
	 
 //	 driver.quit();
	 
 	 driver.findElement(By.id(":r1:")).sendKeys("Admin");
 	 driver.findElement(By.id(":r3:")).sendKeys("Intellion@71725");
 	 driver.findElement(By.id("loginButton")).click();
 	 WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(15));
 WebElement parentcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("accordion-summary-0")));
 parentcategory.click();

 //WebElement childcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("childid")));
 //childcategory.click();
 //
 //WebElement childPageTitle = driver.findElement(By.id("QDE >> Customer Detail"));
 //String childtitle=childPageTitle.getText();
 //if (childtitle.equals("QDE >> Customer Detail")) {
 //    System.out.println("Child Page TEST CASE PASSED "+childtitle);
 //} else {
 //    System.out.println("Child Page TEST CASE Failed , fomud title is "+childtitle);
 //}
 return driver;
  }
	
 	public WebDriver testdetaileddataentry() throws InterruptedException {
 		 EdgeOptions options = new EdgeOptions();
 	        options.setAcceptInsecureCerts(true);
	        
 	 WebDriver driver = new EdgeDriver(options);
      driver.manage().window().maximize();

  driver.get("http://localhost:5173/");
	 
 	 String titlename = driver.getTitle();
	  
 	 if(titlename.equals("Vuga Boda Yo")) 
 	 {
 		 System.out.println("TEST CASE PASSED");
 	}
 	 else {
 		 System.out.println("TEST CASE Failed");
 	 }
	 	 
 	 driver.findElement(By.id(":r1:")).sendKeys("Admin");
 	 driver.findElement(By.id(":r3:")).sendKeys("Intellion@71725");
 	 driver.findElement(By.id("loginButton")).click();
 	 WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(15));
 WebElement parentcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("accordion-summary-1")));
 parentcategory.click();
 Thread.sleep(1000);
 return driver;
 	}
	
 	public WebDriver testCqc() throws InterruptedException {
 		 EdgeOptions options = new EdgeOptions();
 	        options.setAcceptInsecureCerts(true);
	        
 	 WebDriver driver = new EdgeDriver(options);
     driver.manage().window().maximize();

 driver.get("http://localhost:5173/");
	 
 	 String titlename = driver.getTitle();
	  
 	 if(titlename.equals("Vuga Boda Yo")) 
 	 {
 		 System.out.println("TEST CASE PASSED");
 	}
 	 else {
 		 System.out.println("TEST CASE Failed");
 	 }
	 	 
 	 driver.findElement(By.id(":r1:")).sendKeys("Admin");
 	 driver.findElement(By.id(":r3:")).sendKeys("Intellion@71725");
 	 driver.findElement(By.id("loginButton")).click();
 	 WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(15));
 WebElement parentcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("accordion-summary-2")));
 parentcategory.click();
 Thread.sleep(1000);
 return driver;
 	}
 }
