 package com.ozone.smart;

 import java.time.Duration;

 import org.junit.jupiter.api.Test;
 import org.openqa.selenium.By;
 import org.openqa.selenium.Keys;
 import org.openqa.selenium.WebDriver;
 import org.openqa.selenium.WebElement;
 import org.openqa.selenium.support.ui.ExpectedConditions;
 import org.openqa.selenium.support.ui.WebDriverWait;

 public class testdetailedguarantorentry {

 	String selectedcustomer = "GOOKAY101671";
 	String gidvalue = "777111";
 	String surnamevalue = "GoodmanTwosurnmae";
 	String firstnamevalue = "GoodmanTwofirstnmae";
 	String othernamevalue = "GoodmanTwoothername";
 	String moblienovalue = "2020202020";
// 	String adrssvalue = "GoodmanTwoaddress";
 	String yrsinaddrssvalue = "8";
 	String permantadrssvalue = "Twosameadrss";
 	String rentamtvalue = "14000";
 	String curntbikeregnovalue = "JRX6565";
 	String curntbikeownvalue = "GoodmanTwocurntbikeowner";
 	String incvlaue = "67000";
 	String otherincsrcvalue = "business";
 	String otherincvalue = "20000";
 	String llchmnvalue = "GoodmanTwollchmn";
 	String llchmnmobnovalue = "344455554565";
 	String stgorempnamevalue = "GoodmanTwoempname";
 	String stgadrssorempadrssvalue = "GoodmanTwoempaddrss";
 	String stgchmnormangernmaevalue = "GoodmanTwomanagername";
 	String stgchmnormangmbnovalue = "800000008";

 	String strdob = "21/07/1994";
 	String strllname = "GoodmanTwolllname";
 	String strllnumber = "7878787878";
 	String strllrentfeed = "GoodmanTworentfeed";
 	String stryakanum = "179";
 	String stryakanumname = "GoodmanTwoyakaname";
 	String strresiadrs = "GoodmanTworesiadrss";
 	String strnoofyrsinarea = "8";
 	String strlc1chmnrecfedd = "GoodmanTwochmnrecfeed";
 	String strnearlmarkresi = "GoodmanTwonearlmarkresi";
 	String stremmptyp = "GoodmanTwoemptype";
 	String strstagewrkadrssnearlmark = "GoodmanTwostgwrknearlmarkresi";
 	String strnoofyrsinstgbusi = "8";
 	String strmaritalsts = "GoodmanTwomaried";
 	String strspousename = "GoodmanTwospousenmae";
 	String strspousenumber = "77777799999";

 	public WebDriver enterFillingGuaOne() throws InterruptedException {
 		testmainpage page = new testmainpage();

 		WebDriver driver = page.testdetaileddataentry();

 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
 		WebElement childcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("childid-1-1")));
 		childcategory.click();
 		WebElement childpagetitle = driver.findElement(By.id("DDE {'>>'} Detailed Guarantor Entry"));
 		String childtitle = childpagetitle.getText();
 		System.out
 				.println(childtitle.equals("DDE >> Detailed Guarantor Entry") ? "Childpage " + childtitle + " verified"
 						: "child page " + childtitle + " failed");

 		/////// selecting customer//////
 		WebElement selectcustomer = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
 		selectcustomer.click();

 		Thread.sleep(12000);
 		WebElement from = driver.findElement(By.xpath("//*[@id=\":r7:\"]"));
 //		WebElement from = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
 		from.sendKeys(selectedcustomer);
 		Thread.sleep(500);

 		from.sendKeys(Keys.ARROW_DOWN);

 		from.sendKeys(Keys.ENTER);

 //////////selecting customer//////

 //////////getting the existing guarantor response//////
 		WebElement existsmsg = wait
 				.until(ExpectedConditions.visibilityOfElementLocated(By.id("guarantorexitsresponse")));
 //		WebElement existsmsg = driver.findElement(By.xpath("//*[@id=\"guarantorexitsresponse\"]"));
 		String existmessage = existsmsg.getText();
 		System.out.println("existmessage :: " + existmessage);
 //		if (existmessage.equals("Existing guarantor details Guarantor 2: 101982 GUARANTWO")) {
 			System.out.println("msg = Existing guarantor details Guarantor 2: 101982 GUARANTWO");

 			WebElement type = driver.findElement(By.xpath("//*[@id=\"1st G\"]/span[1]/input"));
 			type.click();

 			WebElement guanrantorid = driver.findElement(By.xpath("//*[@id=\":rd:\"]"));
 			guanrantorid.click();
 			guanrantorid.sendKeys(gidvalue);

 			WebElement surname = driver.findElement(By.xpath("//*[@id=\":rf:\"]"));
 			surname.click();
 			surname.sendKeys(surnamevalue);

 			WebElement firstname = driver.findElement(By.xpath("//*[@id=\":rh:\"]"));
 			firstname.click();
 			firstname.sendKeys(firstnamevalue);

 			WebElement othername = driver.findElement(By.xpath("//*[@id=\":rj:\"]"));
 			othername.click();
 			othername.sendKeys(othernamevalue);

 			WebElement mobileno = driver
 					.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[6]/input"));
 			mobileno.click();
 			mobileno.sendKeys(moblienovalue);

 			driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/button[1]")).click();

// 			WebElement Address = driver.findElement(By.xpath("//*[@id=\":rl:\"]"));
// 			Address.click();
// 			Address.sendKeys(adrssvalue);

 			WebElement yrsinAddress = driver.findElement(By.xpath("//*[@id=\":rn:\"]"));
 			yrsinAddress.click();
 			yrsinAddress.sendKeys(yrsinaddrssvalue);

 			WebElement Permanentaddress = driver.findElement(By.xpath("//*[@id=\":rp:\"]"));
 			Permanentaddress.click();
 			Permanentaddress.sendKeys(permantadrssvalue);

 			WebElement ownhouseorrenttype = driver.findElement(By
 					.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[4]/div/label[1]/span[1]/input"));
 			ownhouseorrenttype.click();

 			WebElement rentamt = driver.findElement(By.xpath("//*[@id=\":rt:\"]"));
 			rentamt.click();
 			rentamt.sendKeys(rentamtvalue);

 			WebElement crntbikeregno = driver.findElement(By.xpath("//*[@id=\":rv:\"]"));
 			crntbikeregno.click();
 			crntbikeregno.sendKeys(curntbikeregnovalue);

 			driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[7]/div[2]/button"))
 					.click();

 			WebElement Currentbikeowner = driver.findElement(By.xpath("//*[@id=\":r11:\"]"));
 			Currentbikeowner.click();
 			Currentbikeowner.sendKeys(curntbikeownvalue);

 			WebElement salariedtype = driver.findElement(By
 					.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[2]/div/label[1]/span[1]/input"));
 			salariedtype.click();

 			WebElement Income = driver.findElement(By.xpath("//*[@id=\":r15:\"]"));
 			Income.click();
 			Income.sendKeys(incvlaue);

 			WebElement otherincsrc = driver.findElement(By.xpath("//*[@id=\":r17:\"]"));
 			otherincsrc.click();
 			otherincsrc.sendKeys(otherincsrcvalue);

 			WebElement otherinc = driver.findElement(By.xpath("//*[@id=\":r19:\"]"));
 			otherinc.click();
 			otherinc.sendKeys(otherincvalue);

 			driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[6]/div[2]/button"))
 					.click();

 			WebElement Localchairman = driver.findElement(By.xpath("//*[@id=\":r1b:\"]"));
 			Localchairman.click();
 			Localchairman.sendKeys(llchmnvalue);

 			WebElement Localchairmanmobile = driver
 					.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[2]/input"));
 			Localchairmanmobile.click();
 			Localchairmanmobile.sendKeys(llchmnmobnovalue);

 			WebElement stgnameorempname = driver.findElement(By.xpath("//*[@id=\":r1d:\"]"));
 			stgnameorempname.click();
 			stgnameorempname.sendKeys(stgorempnamevalue);

 			WebElement stgadrssorempadrss = driver.findElement(By.xpath("//*[@id=\":r1f:\"]"));
 			stgadrssorempadrss.click();
 			stgadrssorempadrss.sendKeys(stgadrssorempadrssvalue);

 			WebElement stgchmanormanagername = driver.findElement(By.xpath("//*[@id=\":r1h:\"]"));
 			stgchmanormanagername.click();
 			stgchmanormanagername.sendKeys(stgchmnormangernmaevalue);

 			WebElement stgchmnormanagermbno = driver
 					.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[6]/div/input"));
 			stgchmnormanagermbno.click();
 			stgchmnormanagermbno.sendKeys(stgchmnormangmbnovalue);

 			driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[8]/div[2]/button"))
 					.click();

 			WebElement dob = driver.findElement(By.xpath("//*[@id=\":r1l:\"]"));
 			dob.click();
 			dob.sendKeys(strdob);

 			WebElement llname = driver.findElement(By.xpath("//*[@id=\":r1n:\"]"));
 			llname.click();
 			llname.sendKeys(strllname);

 			WebElement llno = driver.findElement(By.xpath("//*[@id=\":r1p:\"]"));
 			llno.click();
 			llno.sendKeys(strllnumber);

 			WebElement llrentfeed = driver.findElement(By.xpath("//*[@id=\":r1r:\"]"));
 			llrentfeed.click();
 			llrentfeed.sendKeys(strllrentfeed);

 			WebElement yaka = driver.findElement(By.xpath("//*[@id=\":r1t:\"]"));
 			yaka.click();
 			yaka.sendKeys(stryakanum);

 			WebElement yakanum = driver.findElement(By.xpath("//*[@id=\":r1v:\"]"));
 			yakanum.click();
 			yakanum.sendKeys(stryakanumname);

 			WebElement resiaddrss = driver.findElement(By.xpath("//*[@id=\":r21:\"]"));
 			resiaddrss.click();
 			resiaddrss.sendKeys(strresiadrs);

 			WebElement noofyrs = driver.findElement(By.xpath("//*[@id=\":r23:\"]"));
 			noofyrs.click();
 			noofyrs.sendKeys(strnoofyrsinarea);

 			driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[9]/div[2]/button"))
 					.click();

 			WebElement lc1chmrecfeed = driver.findElement(By.xpath("//*[@id=\":r25:\"]"));
 			lc1chmrecfeed.click();
 			lc1chmrecfeed.sendKeys(strlc1chmnrecfedd);

 			WebElement nearlmarkresi = driver.findElement(By.xpath("//*[@id=\":r27:\"]"));
 			nearlmarkresi.click();
 			nearlmarkresi.sendKeys(strnearlmarkresi);

 			WebElement emptype = driver.findElement(By.xpath("//*[@id=\":r29:\"]"));
 			emptype.click();
 			emptype.sendKeys(stremmptyp);

 			WebElement stageorwrkaddrsswithnearlmark = driver.findElement(By.xpath("//*[@id=\":r2b:\"]"));
 			stageorwrkaddrsswithnearlmark.click();
 			stageorwrkaddrsswithnearlmark.sendKeys(strstagewrkadrssnearlmark);

 			driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[5]/div[2]/button"))
 					.click();

 			WebElement noofyrsinstgorbusi = driver.findElement(By.xpath("//*[@id=\":r2d:\"]"));
 			noofyrsinstgorbusi.click();
 			noofyrsinstgorbusi.sendKeys(strnoofyrsinstgbusi);

 			WebElement maritalsts = driver.findElement(By.xpath("//*[@id=\":r2f:\"]"));
 			maritalsts.click();
 			maritalsts.sendKeys(strmaritalsts);

 			WebElement spousename = driver.findElement(By.xpath("//*[@id=\":r2h:\"]"));
 			spousename.click();
 			spousename.sendKeys(strspousename);

 			WebElement spousenumber = driver.findElement(By.xpath("//*[@id=\":r2j:\"]"));
 			spousenumber.click();
 			spousenumber.sendKeys(strspousenumber);

		
 //		else if(existmessage.contains("Existing guarantor details Guarantor 1")) {
 //			WebElement type = driver.findElement(By.xpath("//*[@id=\"2nd G\"]/span[1]/input"));
 //			type.click();
 //		}
 		return driver;

 	}

 	public WebDriver enterFillingsGuaTwo() throws InterruptedException {
 		testmainpage page = new testmainpage();

 		WebDriver driver = page.testdetaileddataentry();

 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
 		WebElement childcategory = wait.until(ExpectedConditions.elementToBeClickable(By.id("childid-1-1")));
 		childcategory.click();
 		WebElement childpagetitle = driver.findElement(By.id("DDE {'>>'} Detailed Guarantor Entry"));
 		String childtitle = childpagetitle.getText();
 		System.out
 				.println(childtitle.equals("DDE >> Detailed Guarantor Entry") ? "Childpage " + childtitle + " verified"
 						: "child page " + childtitle + " failed");

 		/////// selecting customer//////
 		WebElement selectcustomer = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
 		selectcustomer.click();

 		Thread.sleep(12000);
 		WebElement from = driver.findElement(By.xpath("//*[@id=\":r7:\"]"));
 //		WebElement from = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r7:\"]")));
 		from.sendKeys(selectedcustomer);
 		Thread.sleep(500);

 		from.sendKeys(Keys.ARROW_DOWN);

 		from.sendKeys(Keys.ENTER);

 //////////selecting customer//////

 //////////getting the existing guarantor response//////
 		WebElement existsmsg = wait
 				.until(ExpectedConditions.visibilityOfElementLocated(By.id("guarantorexitsresponse")));
 //		WebElement existsmsg = driver.findElement(By.xpath("//*[@id=\"guarantorexitsresponse\"]"));
 		String existmessage = existsmsg.getText();
 		System.out.println("existmessage :: " + existmessage);

 		WebElement type = driver.findElement(By.xpath("//*[@id=\"2nd G\"]/span[1]/input"));
 		type.click();

 		WebElement guanrantorid = driver.findElement(By.xpath("//*[@id=\":rd:\"]"));
 		guanrantorid.click();
 		guanrantorid.sendKeys(gidvalue);

 		WebElement surname = driver.findElement(By.xpath("//*[@id=\":rf:\"]"));
 		surname.click();
 		surname.sendKeys(surnamevalue);

 		WebElement firstname = driver.findElement(By.xpath("//*[@id=\":rh:\"]"));
 		firstname.click();
 		firstname.sendKeys(firstnamevalue);

 		WebElement othername = driver.findElement(By.xpath("//*[@id=\":rj:\"]"));
 		othername.click();
 		othername.sendKeys(othernamevalue);

 		WebElement mobileno = driver
 				.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[6]/input"));
 		mobileno.click();
 		mobileno.sendKeys(moblienovalue);

 		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/button[1]")).click();

// 		WebElement Address = driver.findElement(By.xpath("//*[@id=\":rl:\"]"));
// 		Address.click();
// 		Address.sendKeys(adrssvalue);

 		WebElement yrsinAddress = driver.findElement(By.xpath("//*[@id=\":rl:\"]"));
 		yrsinAddress.click();
 		yrsinAddress.sendKeys(yrsinaddrssvalue);

 		WebElement Permanentaddress = driver.findElement(By.xpath("//*[@id=\":r1b:\"]"));
 		Permanentaddress.click();
 		Permanentaddress.sendKeys(permantadrssvalue);

 		WebElement ownhouseorrenttype = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[3]/div/label[1]/span[1]"));
 		ownhouseorrenttype.click();

 		WebElement rentamt = driver.findElement(By.xpath("//*[@id=\":r1f:\"]"));
 		rentamt.click();
 		rentamt.sendKeys(rentamtvalue);

 		WebElement crntbikeregno = driver.findElement(By.xpath("//*[@id=\":r1h:\"]"));
 		crntbikeregno.click();
 		crntbikeregno.sendKeys(curntbikeregnovalue);

 		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[6]/div[2]/button"))
 				.click();

 		WebElement Currentbikeowner = driver.findElement(By.xpath("//*[@id=\":r1j:\"]"));
 		Currentbikeowner.click();
 		Currentbikeowner.sendKeys(curntbikeownvalue);

 		WebElement salariedtype = driver.findElement(
 				By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[2]/div/label[1]/span[1]"));
 		salariedtype.click();

 		WebElement Income = driver.findElement(By.xpath("//*[@id=\":r1n:\"]"));
 		Income.click();
 		Income.sendKeys(incvlaue);

 		WebElement otherincsrc = driver.findElement(By.xpath("//*[@id=\":r1p:\"]"));
 		otherincsrc.click();
 		otherincsrc.sendKeys(otherincsrcvalue);

 		WebElement otherinc = driver.findElement(By.xpath("//*[@id=\":r1r:\"]"));
 		otherinc.click();
 		otherinc.sendKeys(otherincvalue);

 		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[6]/div[2]/button"))
 				.click();

 		WebElement Localchairman = driver.findElement(By.xpath("//*[@id=\":r1t:\"]"));
 		Localchairman.click();
 		Localchairman.sendKeys(llchmnvalue);

 		WebElement Localchairmanmobile = driver
 				.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[2]/input"));
 		Localchairmanmobile.click();
 		Localchairmanmobile.sendKeys(llchmnmobnovalue);

 		WebElement stgnameorempname = driver.findElement(By.xpath("//*[@id=\":r1v:\"]"));
 		stgnameorempname.click();
 		stgnameorempname.sendKeys(stgorempnamevalue);

 		WebElement stgadrssorempadrss = driver.findElement(By.xpath("//*[@id=\":r21:\"]"));
 		stgadrssorempadrss.click();
 		stgadrssorempadrss.sendKeys(stgadrssorempadrssvalue);

 		WebElement stgchmanormanagername = driver.findElement(By.xpath("//*[@id=\":r23:\"]"));
 		stgchmanormanagername.click();
 		stgchmanormanagername.sendKeys(stgchmnormangernmaevalue);

 		WebElement stgchmnormanagermbno = driver
 				.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[6]/div/input"));
 		stgchmnormanagermbno.click();
 		stgchmnormanagermbno.sendKeys(stgchmnormangmbnovalue);

 		WebElement stgchmnormngconfirmationtype = driver.findElement(
 				By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[7]/div/label[1]/span[1]"));
 		stgchmnormngconfirmationtype.click();
		

 		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[8]/div[2]/button")).click();

 		WebElement dob = driver.findElement(By.xpath("//*[@id=\":r27:\"]"));
 		dob.click();
 		dob.sendKeys(strdob);

 		WebElement llname = driver.findElement(By.xpath("//*[@id=\":r29:\"]"));
 		llname.click();
 		llname.sendKeys(strllname);

 		WebElement llno = driver.findElement(By.xpath("//*[@id=\":r2b:\"]"));
 		llno.click();
 		llno.sendKeys(strllnumber);

 		WebElement llrentfeed = driver.findElement(By.xpath("//*[@id=\":r2d:\"]"));
 		llrentfeed.click();
 		llrentfeed.sendKeys(strllrentfeed);

 		WebElement yaka = driver.findElement(By.xpath("//*[@id=\":r2f:\"]"));
 		yaka.click();
 		yaka.sendKeys(stryakanum);

 		WebElement yakanum = driver.findElement(By.xpath("//*[@id=\":r2h:\"]"));
 		yakanum.click();
 		yakanum.sendKeys(stryakanumname);

 		WebElement resiaddrss = driver.findElement(By.xpath("//*[@id=\":r2j:\"]"));
 		resiaddrss.click();
 		resiaddrss.sendKeys(strresiadrs);

 		WebElement noofyrs = driver.findElement(By.xpath("//*[@id=\":r2l:\"]"));
 		noofyrs.click();
 		noofyrs.sendKeys(strnoofyrsinarea);

 		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[9]/div[2]/button"))
 				.click();

 		WebElement lc1chmrecfeed = driver.findElement(By.xpath("//*[@id=\":r2n:\"]"));
 		lc1chmrecfeed.click();
 		lc1chmrecfeed.sendKeys(strlc1chmnrecfedd);

 		WebElement nearlmarkresi = driver.findElement(By.xpath("//*[@id=\":r2p:\"]"));
 		nearlmarkresi.click();
 		nearlmarkresi.sendKeys(strnearlmarkresi);

 		WebElement emptype = driver.findElement(By.xpath("//*[@id=\":r2r:\"]"));
 		emptype.click();
 		emptype.sendKeys(stremmptyp);

 		WebElement stageorwrkaddrsswithnearlmark = driver.findElement(By.xpath("//*[@id=\":r2t:\"]"));
 		stageorwrkaddrsswithnearlmark.click();
 		stageorwrkaddrsswithnearlmark.sendKeys(strstagewrkadrssnearlmark);

 		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[5]/div[2]/button"))
 				.click();

 		WebElement noofyrsinstgorbusi = driver.findElement(By.xpath("//*[@id=\":r2v:\"]"));
 		noofyrsinstgorbusi.click();
 		noofyrsinstgorbusi.sendKeys(strnoofyrsinstgbusi);

 		WebElement maritalsts = driver.findElement(By.xpath("//*[@id=\":r31:\"]"));
 		maritalsts.click();
 		maritalsts.sendKeys(strmaritalsts);

 		WebElement spousename = driver.findElement(By.xpath("//*[@id=\":r33:\"]"));
 		spousename.click();
 		spousename.sendKeys(strspousename);

 		WebElement spousenumber = driver.findElement(By.xpath("//*[@id=\":r35:\"]"));
 		spousenumber.click();
 		spousenumber.sendKeys(strspousenumber);
 		
 		
		
 		return driver;
 	}

 	public void saveGuarantor() throws InterruptedException {
 //		WebDriver driver= enterFillingGuaOne();
 		WebDriver driver = enterFillingsGuaTwo();
 		WebElement Save = driver
 				.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/div/div[5]/div/div/div[8]/div[2]/button"));
 		Save.click();

 	}

 	@Test
 	public void runDge() throws InterruptedException {
// 		enterFillingGuaOne();
 		enterFillingsGuaTwo();
 //		saveGuarantor();
 	}
 }
