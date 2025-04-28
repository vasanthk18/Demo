// package com.ozone.smart;

// import java.time.Duration;

// import org.junit.jupiter.api.Test;
// import org.openqa.selenium.Alert;
// import org.openqa.selenium.By;
// import org.openqa.selenium.JavascriptExecutor;
// import org.openqa.selenium.Keys;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.interactions.Actions;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import com.ozone.smart.dto.CustomerDetailsDto;
// import com.ozone.smart.entity.CustomerDetails;
// import com.ozone.smart.repository.CustomerRepo;


// @SpringBootTest
// public class testupdatecustomerdetails {
	
// 	private String Surnamevalue;
// 	String LocalChairmanvalue="Smithllchman";
// 	String LCMobilenovalue="88888888";
// //	@Autowired
// //	private CustomerRepo customerRepo;
// //
// //	String flag="all";
// //
// //	String id="SMICOR101670";
// //	CustomerDetails customerDetails = customerRepo.findCustomerByFlag(id, flag);
// //	{
// //	if(customerDetails != null) {
// //		System.out.println(customerDetails.getId());
// //	
// //		String DOB = customerDetails.getDob();
// //		System.out.println("DOB ::"+DOB);
// //		String Firstname = customerDetails.getFirstname();
// //		String othername = customerDetails.getOthername();
// //		String surnmae = customerDetails.getSurname();
// ////		String remarks = customerDetails.getRemarks();
// //		String maritalstatus = customerDetails.getMaritalstatus();
// //		String sex = customerDetails.getSex();
// //		String mobileno = customerDetails.getMobileno();
// //		System.out.println("mobileno ::"+mobileno);
// //		String stage = customerDetails.getStage();
// //		String stagechairman = customerDetails.getStagechairman();
// //		String stagechmanmbno =  customerDetails.getStagechairmanno();
// //		String curntbikeregno = customerDetails.getCurrentbikeregno();
// //		String newbikeuse = customerDetails.getNewbikeuse();
// //		String district = customerDetails.getDistrict();
// //		String county = customerDetails.getCounty();
// //		String subcounty = customerDetails.getSubcounty();
// //		String parish = customerDetails.getParish();
// //		String village = customerDetails.getVillage();
// //		String nationalid = customerDetails.getNationalid();
// //		String yrsinvillage = customerDetails.getYearsinvillage();
// //		String nok = customerDetails.getNextofkin();
// //		String nokmbno = customerDetails.getNokmobileno();
// //		boolean nokagree = customerDetails.getNokagreeing();
// //		String nokrelationship = customerDetails.getNokrelationship();
// //		String drivingpermit = customerDetails.getDrivingpermit();
// //		String nationality = customerDetails.getNationality();
// //		String noofdepen = customerDetails.getNoofdependants();
// //		String ownhouseorrent = customerDetails.getOwnhouserented();
// //		String llname = customerDetails.getLandlordname();
// //		String llmobno = customerDetails.getLandlordmobileno();
// //		String rentpermnth = customerDetails.getRentpm();
// //		String otherincsrc = customerDetails.getOtherincomesource();
// //		String dwnpaymtsrc = customerDetails.getDownpaymentsource();
// //		String peradrss = customerDetails.getPermanentaddress();
// //		String fathername = customerDetails.getFathersname();
// //		String mothername = customerDetails.getMothersname();
// //		String npc = customerDetails.getNearbypolicestation();
// //		String locchmn = customerDetails.getLc();
// //		String lchmnmobno = customerDetails.getLcmobileno();	
// //	}
// //	}
// //	
	
// 	public WebDriver openUpdateCustomerDetails() throws InterruptedException {
// 		testmainpage page = new testmainpage();

// 		WebDriver driver = page.testdetaileddataentry();

// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
// 		WebElement childcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("childid-1-0")));
// 		childcategory.click();
// 		WebElement childpagetitle = driver.findElement(By.id("DDE {'>>'} Update Customer Details"));
// 		String childtitle = childpagetitle.getText();
// 		System.out.println(childtitle.equals("DDE {'>>'} Update Customer Details") ? "Childpage " + childtitle + " verified"
// 				: "child page " + childtitle + " failed");
		
// 		return driver;
// 	}
	
	
	
// 	public void testselectcustomer() throws InterruptedException {
		

// 	WebDriver driver = openUpdateCustomerDetails();
	
// 	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		
// 	///////selecting customer//////
// 			WebElement selectcustomer = wait.until(ExpectedConditions.elementToBeClickable(By.id(":r7:")));
// 			selectcustomer.click();

// 			Thread.sleep(5000);
// 			WebElement from = driver.findElement(By.id(":r7:"));
// 			from.sendKeys("SMICOR101670");
// 			Thread.sleep(500);

// 			from.sendKeys(Keys.ARROW_DOWN);

// 			from.sendKeys(Keys.ENTER);
			
// 	//////////selecting customer//////
			
// //			WebElement applicationremarks = driver.findElement(By.id(""));
// //			const inputElement = document.getElementById(":rd:");
// 			Thread.sleep(1000);
//             WebElement nationalid = driver.findElement(By.id(":rd:"));
//             String nationalidvalue = nationalid.getAttribute("value");
//             if (nationalidvalue.equals("123456789")) {
//                 System.out.println("The nationalid values match!");
//             } else {
//                 System.out.println("The nationalid values do not match.");
//             }
            
//             Actions actions = new Actions(driver);
//             actions.scrollByAmount(0, 100).perform(); // Scroll down by 200 pixels

            
//             WebElement DOB = driver.findElement(By.id(":rf:"));
// //            WebElement DOB = driver.findElement(By.xpath("//*[@id=\":rb:\"]"));
//             ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", DOB);
//             String DOBvalue = DOB.getAttribute("value");
//             if (DOBvalue.equals("01/01/1990")) {
//                 System.out.println("The DOB values match!");
//             } else {
//                 System.out.println("The DOB values do not match.");
//             }

            
//             WebElement Firstname = driver.findElement(By.id(":rh:"));
//             String Firstnamevalue = Firstname.getAttribute("value");
//             if (Firstnamevalue.equals("John")) {
//                 System.out.println("The Firstname values match!");
//             } else {
//                 System.out.println("The Firstname values do not match.");
//             }
            
//             WebElement Othername = driver.findElement(By.id(":rj:"));
//             String Othernamevalue = Othername.getAttribute("value");
//             if (Othernamevalue.equals("Doe")) {
//                 System.out.println("The Othername values match!");
//             } else {
//                 System.out.println("The Othername values do not match.");
//             }
            
//             WebElement Surname = driver.findElement(By.id(":rl:"));
//             String Surnamevalue = Surname.getAttribute("value");
//             if (Surnamevalue.equals("Smith")) {
//                 System.out.println("The Surname values match!");
//             } else {
//                 System.out.println("The Surname values do not match.");
//             }
            
//           //*[@id="root"]/div/div[2]/div[3]/div/div[2]/div[8]/div/div
// //            WebElement Gender = driver.findElement(By.id(":rn:"));
//             WebElement Gender = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[2]/div[8]/div/div"));
//             String GENDERvalue = Gender.getText();
//             if (GENDERvalue.equals("Male")) {
//                 System.out.println("The Gender values match!");
//             } else {
//                 System.out.println("The Gender values do not match.");
//             }
            
//             WebElement MaritalStatus = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[2]/div[9]/div/div"));
//             String MaritalStatusvalue = MaritalStatus.getText();
//             if (MaritalStatusvalue.equals("single")) {
//                 System.out.println("The MaritalStatus values match!");
//             } else {
//                 System.out.println("The MaritalStatus values do not match.");
//             }
            
            
//             driver.findElement(By.id("btn-1")).click();
//             Thread.sleep(1000);
// //            WebElement MobileNo = driver.findElement(By.id(":rd:"));
//             WebElement MobileNo = driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div[3]/div/div[2]/div[1]/input"));
//             String MobileNovalue = MobileNo.getAttribute("value");
//             if (MobileNovalue.equals("256700123456") || MobileNovalue.equals("+256 700 123 456")) {
//                 System.out.println("The MobileNo values match!");
//             } else {
//                 System.out.println("The MobileNo values do not match.");
//             }
            
//             WebElement SelectBodaStage = driver.findElement(By.id(":rr:"));
//             String SelectBodaStagevalue = SelectBodaStage.getAttribute("value");
//             if (SelectBodaStagevalue.equals("CORPORATE")) {
//                 System.out.println("The SelectBodaStage values match!");
//             } else {
//                 System.out.println("The SelectBodaStage values do not match.");
//             }
            
//             WebElement StageChairmanName = driver.findElement(By.id(":rv:"));
//             String StageChairmanNamevalue = StageChairmanName.getAttribute("value");
//             if (StageChairmanNamevalue.equals("stagechrm")) {
//                 System.out.println("The StageChairmanName values match!");
//             } else {
//                 System.out.println("The StageChairmanName values do not match.");
//             }

//             WebElement StageChairmanMobileNo = driver.findElement(By.id(":r11:"));
//             String StageChairmanMobileNovalue = StageChairmanMobileNo.getAttribute("value");
//             if (StageChairmanMobileNovalue.equals("7678646763")) {
//                 System.out.println("The StageChairmanMobileNo values match!");
//             } else {
//                 System.out.println("The StageChairmanMobileNo values do not match.");
//             }
            
//             WebElement CurrentBikeRegno = driver.findElement(By.id(":r13:"));
//             String CurrentBikeRegnovalue = CurrentBikeRegno.getAttribute("value");
//             if (CurrentBikeRegnovalue.equals("NA")) {
//                 System.out.println("The CurrentBikeRegno values match!");
//             } else {
//                 System.out.println("The CurrentBikeRegno values do not match.");
//             }
            
//             WebElement UsefornewBike = driver.findElement(By.id(":r15:"));
//             String UsefornewBikevalue = UsefornewBike.getAttribute("value");
//             if (UsefornewBikevalue.equals("personal")) {
//                 System.out.println("The UsefornewBike values match!");
//             } else {
//                 System.out.println("The UsefornewBike values do not match.");
//             }
            
//             WebElement SelectDistrict = driver.findElement(By.id(":r17:"));
//             String SelectDistrictvalue = SelectDistrict.getAttribute("value");
//             if (SelectDistrictvalue.equals("Mubende")) {
//                 System.out.println("The SelectDistrict values match!");
//             } else {
//                 System.out.println("The SelectDistrict values do not match.");
//             }
            
//             WebElement SelectCounty = driver.findElement(By.id(":r1b:"));
//             String SelectCountyvalue = SelectCounty.getAttribute("value");
//             if (SelectCountyvalue.equals("Buwekula County")) {
//                 System.out.println("The SelectCounty values match!");
//             } else {
//                 System.out.println("The SelectCounty values do not match.");
//             }
            
//             WebElement SelectSubCounty = driver.findElement(By.id(":r1f:"));
            
//             String SelectSubCountyvalue = SelectSubCounty.getAttribute("value");
//             if (SelectSubCountyvalue.equals("KITENGA")) {
//                 System.out.println("The SelectSubCounty values match!");
//             } else {
//                 System.out.println("The SelectSubCounty values do not match.");
//             }
            
//             WebElement ParishName = driver.findElement(By.id(":r1j:"));
//             String ParishNamevalue = ParishName.getAttribute("value");
//             if (ParishNamevalue.equals("testparishname")) {
//                 System.out.println("The ParishName values match!");
//             } else {
//                 System.out.println("The ParishName values do not match.");
//             }
            
//             driver.findElement(By.id("btn-2")).click();
//             Thread.sleep(1000);
            
//             WebElement Village = driver.findElement(By.id(":r1l:"));
//             String Villagevalue = Village.getAttribute("value");
//             if (Villagevalue.equals("testvillage")) {
//                 System.out.println("The Village values match!");
//             } else {
//                 System.out.println("The Village values do not match.");
//             }
            
//             WebElement YearsinVillage = driver.findElement(By.id(":r1n:"));
//             String YearsinVillagevalue = YearsinVillage.getAttribute("value");
//             if (YearsinVillagevalue.equals("5")) {
//                 System.out.println("The YearsinVillage values match!");
//             } else {
//                 System.out.println("The YearsinVillage values do not match.");
//             }
            
//             WebElement Nextofkin = driver.findElement(By.id(":r1p:"));
//             String Nextofkinvalue = Nextofkin.getAttribute("value");
//             if (Nextofkinvalue.equals("testnok")) {
//                 System.out.println("The Nextofkin values match!");
//             } else {
//                 System.out.println("The Nextofkin values do not match.");
//             }

// //            WebElement Nokinmbno = driver.findElement(By.id(":rd:"));
//             WebElement Nokinmbno = driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div[3]/div/div[2]/div[4]/input"));
//             String Nokinmbnovalue = Nokinmbno.getAttribute("value");
//             if (Nokinmbnovalue.equals("256700123457")||Nokinmbnovalue.equals("+256 700 123 457")) {
//                 System.out.println("The Nokinmbno values match!");
//             } else {
//                 System.out.println("The Nokinmbno values do not match.");
//             }
            
//             WebElement NokRelationship = driver.findElement(By.id(":r1r:"));
//             String NokRelationshipvalue = NokRelationship.getAttribute("value");
//             if (NokRelationshipvalue.equals("testnokrlshp")) {
//                 System.out.println("The NokRelationship values match!");
//             } else {
//                 System.out.println("The NokRelationship values do not match.");
//             }
            
//             WebElement Nokagree = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[2]/div[6]/div/div"));
//             String Nokagreevalue = Nokagree.getText();
//             if (Nokagreevalue.equals("Yes")) {
//                 System.out.println("The Nokagree values match!");
//             } else {
//                 System.out.println("The Nokagree values do not match.");
//             }
            
//             WebElement Drivingpermit = driver.findElement(By.id(":r1v:"));
//             String Drivingpermitvalue = Drivingpermit.getAttribute("value");
//             if (Drivingpermitvalue.equals("Yes")) {
//                 System.out.println("The Drivingpermit values match!");
//             } else {
//                 System.out.println("The Drivingpermit values do not match.");
//             }
            
//             WebElement Nationality = driver.findElement(By.id(":r21:"));
//             String Nationalityvalue = Nationality.getAttribute("value");
//             if (Nationalityvalue.equals("testUganda")) {
//                 System.out.println("The Nationality values match!");
//             } else {
//                 System.out.println("The Nationality values do not match.");
//             }
            
//             driver.findElement(By.id("btn-3")).click();
//             Thread.sleep(1000);
            
//             WebElement NoofDependants = driver.findElement(By.xpath("//*[@id=\":r23:\"]"));
//             String NoofDependantsvalue = NoofDependants.getAttribute("value");
//             if (NoofDependantsvalue.equals("4")) {
//                 System.out.println("The NoofDependants values match!");
//             } else {
//                 System.out.println("The NoofDependants values do not match.");
//             }
            
//             WebElement OwnHouseorRent = driver.findElement(By.xpath("//*[@id=\":r25:\"]"));
//             String OwnHouseorRentvalue = OwnHouseorRent.getAttribute("value");
//             if (OwnHouseorRentvalue.equals("rented")) {
//                 System.out.println("The OwnHouseorRent values match!");
//             } else {
//                 System.out.println("The OwnHouseorRent values do not match.");
//             }
            
//             WebElement LlordName = driver.findElement(By.xpath("//*[@id=\":r27:\"]"));
//             String LlordNamevalue = LlordName.getAttribute("value");
//             if (LlordNamevalue.equals("testllname")) {
//                 System.out.println("The LlordName values match!");
//             } else {
//                 System.out.println("The LlordName values do not match.");
//             }
            
// //            WebElement Llordmbno = driver.findElement(By.id(":rd:"));
//             WebElement Llordmbno = driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div[3]/div/div[2]/div[4]/input"));
//             String Llordmbnovalue = Llordmbno.getAttribute("value");
//             if (Llordmbnovalue.equals("256700123458") || Llordmbnovalue.equals("+256 700 123 458")) {
//                 System.out.println("The Llordmbno values match!");
//             } else {
//                 System.out.println("The Llordmbno values do not match.");
//             }
            
//             WebElement RentAmtPerMnth = driver.findElement(By.xpath("//*[@id=\":r29:\"]"));
//             String RentAmtPerMnthvalue = RentAmtPerMnth.getAttribute("value");
//             if (RentAmtPerMnthvalue.equals("20000")) {
//                 System.out.println("The RentAmtPerMnth values match!");
//             } else {
//                 System.out.println("The RentAmtPerMnth values do not match.");
//             }
            
//             WebElement OtherIncomeSrc = driver.findElement(By.xpath("//*[@id=\":r2b:\"]"));
//             String OtherIncomeSrcvalue = OtherIncomeSrc.getAttribute("value");
//             if (OtherIncomeSrcvalue.equals("NA")) {
//                 System.out.println("The OtherIncomeSrc values match!");
//             } else {
//                 System.out.println("The OtherIncomeSrc values do not match.");
//             }
            
//             WebElement DownPaySrc = driver.findElement(By.xpath("//*[@id=\":r2d:\"]"));
//             String DownPaySrcvalue = DownPaySrc.getAttribute("value");
//             if (DownPaySrcvalue.equals("Savings")) {
//                 System.out.println("The DownPaySrc values match!");
//             } else {
//                 System.out.println("The DownPaySrc values do not match.");
//             }
            
//             WebElement PermanentAddrs = driver.findElement(By.xpath("//*[@id=\":r2f:\"]"));
//             String PermanentAddrsvalue = PermanentAddrs.getAttribute("value");
//             if (PermanentAddrsvalue.equals("1/195 bengam street")) {
//                 System.out.println("The PermanentAddrs values match!");
//             } else {
//                 System.out.println("The PermanentAddrs values do not match.");
//             }
            
//             driver.findElement(By.id("btn-4")).click();
//             Thread.sleep(1000);
            
//             WebElement FatherName = driver.findElement(By.xpath("//*[@id=\":r2h:\"]"));
//             String FatherNamevalue = FatherName.getAttribute("value");
//             if (FatherNamevalue.equals("testfname")) {
//                 System.out.println("The FatherName values match!");
//             } else {
//                 System.out.println("The FatherName values do not match.");
//             }
            
//             WebElement MotherName = driver.findElement(By.xpath("//*[@id=\":r2j:\"]"));
//             String MotherNamevalue = MotherName.getAttribute("value");
//             if (MotherNamevalue.equals("testmname")) {
//                 System.out.println("The MotherName values match!");
//             } else {
//                 System.out.println("The MotherName values do not match.");
//             }
            
//             WebElement NbyPlcStation = driver.findElement(By.xpath("//*[@id=\":r2l:\"]"));
//             String NbyPlcStationvalue = NbyPlcStation.getAttribute("value");
//             if (NbyPlcStationvalue.equals("testnearby ps")) {
//                 System.out.println("The NbyPlcStation values match!");
//             } else {
//                 System.out.println("The NbyPlcStation values do not match.");
//             }
            
//             WebElement LocalChman = driver.findElement(By.xpath("//*[@id=\":r2n:\"]"));
//             String LocalChmanvalue = LocalChman.getAttribute("value");
//             if (LocalChmanvalue.equals("")) {
//                 System.out.println("The LocalChman values match!");
//             } else {
//                 System.out.println("The LocalChman values do not match.");
//             }
            
// //            WebElement LCMobno = driver.findElement(By.id(":rd:"));
//             WebElement LCMobno = driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div[3]/div/div[2]/div[5]/div/input"));
//             String LCMobnovalue = LCMobno.getText();
//             if (LCMobnovalue.equals("")) {
//                 System.out.println("The LCMobno values match!");
//             } else {
//                 System.out.println("The LCMobno values do not match.");
//             }
            
//            driver.quit();
// 	}
	
	
	
// 	public WebDriver fillUpdateCustomerDetails() throws InterruptedException {
// 		WebDriver driver = openUpdateCustomerDetails();
		
// 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		
// 		///////selecting customer//////
// 				WebElement selectcustomer = wait.until(ExpectedConditions.elementToBeClickable(By.id(":r7:")));
// 				selectcustomer.click();

// 				Thread.sleep(5000);
// 				WebElement from = driver.findElement(By.id(":r7:"));
// 				from.sendKeys("SMICOR101670");
// 				Thread.sleep(500);

// 				from.sendKeys(Keys.ARROW_DOWN);

// 				from.sendKeys(Keys.ENTER);
				
// 		//////////selecting customer//////
				
// //				WebElement applicationremarks = driver.findElement(By.id(""));
// //				const inputElement = document.getElementById(":rd:");
// 				Thread.sleep(1000);
// 	            WebElement nationalid = driver.findElement(By.id(":rd:"));
// 	            String nationalidvalue = nationalid.getAttribute("value");
// 		       if (nationalidvalue != null && !nationalidvalue.isEmpty()) {

// 	                System.out.println("The nationalid values present!");
	            
// 	            WebElement DOB = driver.findElement(By.id(":rf:"));
// 	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", DOB);
// 	            String DOBvalue = DOB.getAttribute("value");
// 	 		       if (DOBvalue != null && !DOBvalue.isEmpty()) {
// 	                System.out.println("The DOB values present!");
	            
// 	            WebElement Firstname = driver.findElement(By.id(":rh:"));
// 	            String Firstnamevalue = Firstname.getAttribute("value");
// 	 		       if (Firstnamevalue != null && !Firstnamevalue.isEmpty()) {
// 	                System.out.println("The Firstname values match!");
	            
// 	            WebElement Othername = driver.findElement(By.id(":rj:"));
// 	            String Othernamevalue = Othername.getAttribute("value");
// 	 		       if (Othernamevalue != null && !Othernamevalue.isEmpty()) {
// 	                System.out.println("The Othername values match!");
	           
	            
// 	            WebElement Surname = driver.findElement(By.id(":rl:"));
// 	            String Surnamevalue = Surname.getAttribute("value");
// 	 		       if (Surnamevalue != null && !Surnamevalue.isEmpty()) {
// 	                System.out.println("The Surname values match!");
	          
// 	            WebElement Gender = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[2]/div[8]/div/div"));
// 	            String GENDERvalue = Gender.getText();
// 	 		       if (GENDERvalue != null && !GENDERvalue.isEmpty()) {
// 	                System.out.println("The Gender values match!");
	          
// 	            WebElement MaritalStatus = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[2]/div[9]/div/div"));
// 	            String MaritalStatusvalue = MaritalStatus.getText();
// 	 		       if (MaritalStatusvalue != null && !MaritalStatusvalue.isEmpty()) {
// 	                System.out.println("The MaritalStatus values match!");
	           
	            
// 	            driver.findElement(By.id("btn-1")).click();
// 	            Thread.sleep(1000);
// //	            WebElement MobileNo = driver.findElement(By.id(":rd:"));
// 	            WebElement MobileNo = driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div[3]/div/div[2]/div[1]/input"));
// 	            String MobileNovalue = MobileNo.getAttribute("value");
// 	 		       if (MobileNovalue != null && !MobileNovalue.isEmpty()) {
// 	                System.out.println("The MobileNo values match!");
	           
	            
// 	            WebElement SelectBodaStage = driver.findElement(By.id(":rr:"));
// 	            String SelectBodaStagevalue = SelectBodaStage.getAttribute("value");
// 	 		       if (SelectBodaStagevalue != null && !SelectBodaStagevalue.isEmpty()) {
// 	                System.out.println("The SelectBodaStage values match!");
	          
	            
// 	            WebElement StageChairmanName = driver.findElement(By.id(":rv:"));
// 	            String StageChairmanNamevalue = StageChairmanName.getAttribute("value");
// 	 		       if (StageChairmanNamevalue != null && !StageChairmanNamevalue.isEmpty()) {
// 	                System.out.println("The StageChairmanName values match!");
	           

// 	            WebElement StageChairmanMobileNo = driver.findElement(By.id(":r11:"));
// 	            String StageChairmanMobileNovalue = StageChairmanMobileNo.getAttribute("value");
// 	 		       if (StageChairmanMobileNovalue != null && !StageChairmanMobileNovalue.isEmpty()) {
// 	                System.out.println("The StageChairmanMobileNo values match!");
	           
	            
// 	            WebElement CurrentBikeRegno = driver.findElement(By.id(":r13:"));
// 	            String CurrentBikeRegnovalue = CurrentBikeRegno.getAttribute("value");
// 	 		       if (CurrentBikeRegnovalue != null && !CurrentBikeRegnovalue.isEmpty()) {
// 	                System.out.println("The CurrentBikeRegno values match!");
	           
	            
// 	            WebElement UsefornewBike = driver.findElement(By.id(":r15:"));
// 	            String UsefornewBikevalue = UsefornewBike.getAttribute("value");
// 	 		       if (UsefornewBikevalue != null && !UsefornewBikevalue.isEmpty()) {
// 	                System.out.println("The UsefornewBike values match!");
	           
	            
// 	            WebElement SelectDistrict = driver.findElement(By.id(":r17:"));
// 	            String SelectDistrictvalue = SelectDistrict.getAttribute("value");
// 	 		       if (SelectDistrictvalue != null && !SelectDistrictvalue.isEmpty()) {
// 	                System.out.println("The SelectDistrict values match!");
	           
	            
// 	            WebElement SelectCounty = driver.findElement(By.id(":r1b:"));
// 	            String SelectCountyvalue = SelectCounty.getAttribute("value");
// 	 		       if (SelectCountyvalue != null && !SelectCountyvalue.isEmpty()) {
// 	                System.out.println("The SelectCounty values match!");
	            
	            
// 	            WebElement SelectSubCounty = driver.findElement(By.id(":r1f:"));
	            
// 	            String SelectSubCountyvalue = SelectSubCounty.getAttribute("value");
// 	 		       if (SelectSubCountyvalue != null && !SelectSubCountyvalue.isEmpty()) {
// 	                System.out.println("The SelectSubCounty values match!");
	          
	            
// 	            WebElement ParishName = driver.findElement(By.id(":r1j:"));
// 	            String ParishNamevalue = ParishName.getAttribute("value");
// 	 		       if (ParishNamevalue != null && !ParishNamevalue.isEmpty()) {
// 	                System.out.println("The ParishName values match!");
	           
	            
// 	            driver.findElement(By.id("btn-2")).click();
// 	            Thread.sleep(1000);
	            
// 	            WebElement Village = driver.findElement(By.id(":r1l:"));
// 	            String Villagevalue = Village.getAttribute("value");
// 	 		       if (Villagevalue != null && !Villagevalue.isEmpty()) {
// 	                System.out.println("The Village values match!");
	          
// 	            WebElement YearsinVillage = driver.findElement(By.id(":r1n:"));
// 	            String YearsinVillagevalue = YearsinVillage.getAttribute("value");
// 	 		       if (YearsinVillagevalue != null && !YearsinVillagevalue.isEmpty()) {
// 	                System.out.println("The YearsinVillage values match!");
	           
	            
// 	            WebElement Nextofkin = driver.findElement(By.id(":r1p:"));
// 	            String Nextofkinvalue = Nextofkin.getAttribute("value");
// 	 		       if (Nextofkinvalue != null && !Nextofkinvalue.isEmpty()) {
// 	                System.out.println("The Nextofkin values match!");
	          

// //	            WebElement Nokinmbno = driver.findElement(By.id(":rd:"));
// 	            WebElement Nokinmbno = driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div[3]/div/div[2]/div[4]/input"));
// 	            String Nokinmbnovalue = Nokinmbno.getAttribute("value");
// 	 		       if (Nokinmbnovalue != null && !Nokinmbnovalue.isEmpty()) {
// 	                System.out.println("The Nokinmbno values match!");
	           
// 	            WebElement NokRelationship = driver.findElement(By.id(":r1r:"));
// 	            String NokRelationshipvalue = NokRelationship.getAttribute("value");
// 	 		       if (NokRelationshipvalue != null && !NokRelationshipvalue.isEmpty()) {
// 	                System.out.println("The NokRelationship values match!");
	          
	            
// 	            WebElement Nokagree = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[2]/div[6]/div/div"));
// 	            String Nokagreevalue = Nokagree.getText();
// 	 		       if (Nokagreevalue != null && !Nokagreevalue.isEmpty()) {
// 	                System.out.println("The Nokagree values match!");
	          
// 	            WebElement Drivingpermit = driver.findElement(By.id(":r1v:"));
// 	            String Drivingpermitvalue = Drivingpermit.getAttribute("value");
// 	 		       if (Drivingpermitvalue != null && !Drivingpermitvalue.isEmpty()) {
// 	                System.out.println("The Drivingpermit values match!");
	          
	            
// 	            WebElement Nationality = driver.findElement(By.id(":r21:"));
// 	            String Nationalityvalue = Nationality.getAttribute("value");
// 	 		       if (Nationalityvalue != null && !Nationalityvalue.isEmpty()) {
// 	                System.out.println("The Nationality values match!");
	          
	            
// 	            driver.findElement(By.id("btn-3")).click();
// 	            Thread.sleep(1000);
	            
// 	            WebElement NoofDependants = driver.findElement(By.xpath("//*[@id=\":r23:\"]"));
// 	            String NoofDependantsvalue = NoofDependants.getAttribute("value");
// 	 		       if (NoofDependantsvalue != null && !NoofDependantsvalue.isEmpty()) {
// 	                System.out.println("The NoofDependants values match!");
	           
	            
// 	            WebElement OwnHouseorRent = driver.findElement(By.xpath("//*[@id=\":r25:\"]"));
// 	            String OwnHouseorRentvalue = OwnHouseorRent.getAttribute("value");
// 	 		       if (OwnHouseorRentvalue != null && !OwnHouseorRentvalue.isEmpty()) {
// 	                System.out.println("The OwnHouseorRent values match!");
	          
// 	            WebElement LlordName = driver.findElement(By.xpath("//*[@id=\":r27:\"]"));
// 	            String LlordNamevalue = LlordName.getAttribute("value");
// 	 		       if (LlordNamevalue != null && !LlordNamevalue.isEmpty()) {
// 	                System.out.println("The LlordName values match!");
	          
// 	            WebElement Llordmbno = driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div[3]/div/div[2]/div[4]/input"));
// 	            String Llordmbnovalue = Llordmbno.getAttribute("value");
// 	 		       if (Llordmbnovalue != null && !Llordmbnovalue.isEmpty()) {
// 	                System.out.println("The Llordmbno values match!");
	           
// 	            WebElement RentAmtPerMnth = driver.findElement(By.xpath("//*[@id=\":r29:\"]"));
// 	            String RentAmtPerMnthvalue = RentAmtPerMnth.getAttribute("value");
// 	 		       if (RentAmtPerMnthvalue != null && !RentAmtPerMnthvalue.isEmpty()) {
// 	                System.out.println("The RentAmtPerMnth values match!");
	           
// 	            WebElement OtherIncomeSrc = driver.findElement(By.xpath("//*[@id=\":r2b:\"]"));
// 	            String OtherIncomeSrcvalue = OtherIncomeSrc.getAttribute("value");
// 	 		       if (OtherIncomeSrcvalue != null && !OtherIncomeSrcvalue.isEmpty()) {
// 	                System.out.println("The OtherIncomeSrc values match!");
	           
// 	            WebElement DownPaySrc = driver.findElement(By.xpath("//*[@id=\":r2d:\"]"));
// 	            String DownPaySrcvalue = DownPaySrc.getAttribute("value");
// 	 		       if (DownPaySrcvalue != null && !DownPaySrcvalue.isEmpty()) {
// 	                System.out.println("The DownPaySrc values match!");
	           
	            
// 	            WebElement PermanentAddrs = driver.findElement(By.xpath("//*[@id=\":r2f:\"]"));
// 	            String PermanentAddrsvalue = PermanentAddrs.getAttribute("value");
// 	 		       if (PermanentAddrsvalue != null && !PermanentAddrsvalue.isEmpty()) {
// 	                System.out.println("The PermanentAddrs values match!");
	           
// 	            driver.findElement(By.id("btn-4")).click();
// 	            Thread.sleep(1000);
	            
// 	            WebElement FatherName = driver.findElement(By.xpath("//*[@id=\":r2h:\"]"));
// 	            String FatherNamevalue = FatherName.getAttribute("value");
// 	 		       if (FatherNamevalue != null && !FatherNamevalue.isEmpty()) {
// 	                System.out.println("The FatherName values match!");
	         
// 	            WebElement MotherName = driver.findElement(By.xpath("//*[@id=\":r2j:\"]"));
// 	            String MotherNamevalue = MotherName.getAttribute("value");
// 	 		       if (MotherNamevalue != null && !MotherNamevalue.isEmpty()) {
// 	                System.out.println("The MotherName values match!");
	          
// 	            WebElement NbyPlcStation = driver.findElement(By.xpath("//*[@id=\":r2l:\"]"));
// 	            String NbyPlcStationvalue = NbyPlcStation.getAttribute("value");
// 	 		       if (NbyPlcStationvalue != null && !NbyPlcStationvalue.isEmpty()) {
// 	                System.out.println("The NbyPlcStation values match!");
	           
// 	            WebElement LocalChman = driver.findElement(By.xpath("//*[@id=\":r2n:\"]"));
// 	            LocalChman.click();
// 	            LocalChman.sendKeys(LocalChairmanvalue);
	            
// 	            WebElement LCMobno = driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div[3]/div/div[2]/div[5]/div/input"));
// 	            LCMobno.click();
// 	            LCMobno.sendKeys(LCMobilenovalue);
// 	 		       }}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}
// 			return driver;
	            
// //	           driver.quit();
	 		       
// 	}
	
// 	public void positiveUpdateCustomerDetails() throws InterruptedException {
		
		
		
// 			WebDriver driver = fillUpdateCustomerDetails();
// 			WebElement save =driver.findElement(By.xpath("//*[@id=\"updateButton\"]"));
// 			save.click();
			
// 			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
// 			 Alert alert = wait.until(ExpectedConditions.alertIsPresent()); 
// 			 String responsemsg= alert.getText();
// 			 if (responsemsg.equals("Customer"+ Surnamevalue+"Successfully Added")) {
// 		            System.out.println("Message matches: " + responsemsg);
		            
// 		            driver.quit();
// 		        } else {
// 		            System.out.println(" Message does not match. Actual: " + responsemsg + ", Expected: " + "DC submission for : "+Surnamevalue+" saved successfully.");
// 		        }

// 	}
	
// 	@Test
// 	public void RunUpdateCustomerDetails() throws InterruptedException {
// //		fillUpdateCustomerDetails();
// 		positiveUpdateCustomerDetails();
// 	}

// }
