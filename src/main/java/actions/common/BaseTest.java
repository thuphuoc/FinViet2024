package actions.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import actions.utils.LogUtils;
import com.google.gson.Gson;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;

import static io.restassured.RestAssured.given;

public class BaseTest {

    Gson gson = new Gson();
    Response rsp;

    public WebDriver GetDriverInstance() {
        return this.driver;
    }

    private WebDriver driver;
    String projectPath = System.getProperty("user.dir");

    protected WebDriver getBrowserDriver(String browserName) {
        BROWSERLIST browser = BROWSERLIST.valueOf(browserName.toUpperCase());
        if (browser.equals(BROWSERLIST.CHROME)) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            driver = new ChromeDriver(options);
        } else if (browser.equals(BROWSERLIST.FIREFOX)) {
            driver = new FirefoxDriver();
        } else if (browser.equals(BROWSERLIST.EDGE)) {
            driver = new EdgeDriver();
        } else if (browser.equals(BROWSERLIST.COCCOC)) {
//            WebDriverManager.chromedriver().driverVersion("97.0.4692").setup();
            ChromeOptions options = new ChromeOptions();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
            options.setBinary("C:\\Users\\phuoc\\AppData\\Local\\CocCoc\\Browser\\Application\\browser.exe");
            driver = new ChromeDriver(options);
        } else {
            throw new RuntimeException("Browser Name Invalid");
        }
        return driver;
    }


//
//    protected String getEnv(String envName) {
//        String url=null;
//        if (envName.equals("dev")) {
//            url=PageBaseUI.URL_DEV;
//        } else if (envName.equals("release")) {
//            url=PageBaseUI.URL_RELEASE;
//        } else if (envName.equals("live")) {
//            url=PageBaseUI.URL_LIVE;
//        } else {
//            throw new RuntimeException("Enviroment Name Invalid");
//        }
//        return url;
//    }

    protected boolean verifyTrue(boolean condition, String mess) {
        boolean pass = true;
        try {
            Assert.assertTrue(condition, mess);
            LogUtils.info(mess + " | PASSED");
            Allure.step(mess+ " | PASSED", Status.PASSED);
//            LogUtils.info(" -------------------------- PASSED -------------------------- ");
        } catch (Throwable e) {
            LogUtils.info(mess +" | FAILED ");
//            LogUtils.info(" -------------------------- FAILED -------------------------- ");
//			Allure.step(mess+ " | FAILED", Status.FAILED);
//            if(DriverManager.getDriver() == null) {
//                Allure.step(mess+ " | FAILED", Status.FAILED);
//            }else {
                Allure.step(mess, () -> {
                    String uuid = startStepAndGetUuid();
                    try {
                        Allure.getLifecycle().updateStep(uuid, stepResult -> {stepResult.setStatus(Status.FAILED);});
                        captureScreenshot();
                        Allure.getLifecycle().stopStep(uuid);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
//            }
            pass = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyFalse(boolean condition, String mess) {
        boolean pass = true;
        try {
            Assert.assertFalse(condition, mess);
            LogUtils.info(mess+ " | PASSED");
            Allure.step(mess+ " | PASSED", Status.PASSED);
//            LogUtils.info(" -------------------------- PASSED -------------------------- ");
        } catch (Throwable e) {
            LogUtils.info(mess);
//			Allure.step(mess+ " | FAILED", Status.FAILED);
//            if(DriverManager.getDriver() == null) {
//                Allure.step(mess+ " | FAILED", Status.FAILED);
//            }else {
                Allure.step(mess, () -> {
                    String uuid = startStepAndGetUuid();
                    try {
                        Allure.getLifecycle().updateStep(uuid, stepResult -> {stepResult.setStatus(Status.FAILED);});
                        captureScreenshot();
                        Allure.getLifecycle().stopStep(uuid);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
//            }
            pass = false;
            LogUtils.info(mess+ " | FAILED");
//            LogUtils.info(" -------------------------- FAILED -------------------------- ");
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyEquals(Object actual, Object expected, String mess) {
        boolean pass = true;
        try {
            Assert.assertEquals(actual, expected, mess);
            LogUtils.info("actual: " + actual + " == expected: " + expected +  " | PASSED");
            Allure.step(mess+ " | PASSED", Status.PASSED);
//            LogUtils.info(" -------------------------- PASSED -------------------------- ");
        } catch (Throwable e) {
            pass = false;
            LogUtils.info("actual: " + actual + " != expected: " + expected +  " | FAILED");
//			Allure.getLifecycle().startStep(uuid, new StepResult().setName(mess).setStatus(Status.PASSED));
//			Allure.step(mess+ " | FAILED", Status.FAILED);
//            if(DriverManager.getDriver() == null) {
//                Allure.step(mess+ " | FAILED", Status.FAILED);
//            }else {
                Allure.step(mess, () -> {
                    String uuid = startStepAndGetUuid();
                    try {
                        Allure.getLifecycle().updateStep(uuid, stepResult -> {stepResult.setStatus(Status.FAILED);});
                        captureScreenshot();
                        Allure.getLifecycle().stopStep(uuid);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
//            }
//            LogUtils.info(" -------------------------- FAILED -------------------------- ");
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    private String startStepAndGetUuid() {
        return Allure.getLifecycle().getCurrentTestCaseOrStep().get();
    }

    private void captureScreenshot() {
        String pathAllureReport = GlobalConstants.PROJECT_PATH;
        if (driver != null) {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            String base64Screenshot = screenshot.getScreenshotAs(OutputType.BASE64);
            try {
                byte[] decodedScreenshot = org.apache.commons.codec.binary.Base64.decodeBase64(base64Screenshot);
                File screenshotFile = new File(pathAllureReport + "/allure-results/" + getTimestamp() + ".png");
                Files.write(screenshotFile.toPath(), decodedScreenshot);
                Allure.addAttachment("Screenshot", new ByteArrayInputStream(decodedScreenshot));
                System.out.println("Screenshot captured: " + screenshotFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }

    public void threadSecond(long second) {
        try {
            Thread.sleep(1000 * second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void step(String step) {
        Allure.step(step);
    }

    public Response postRequestJson(Object bodyData, String url) {
        Allure.step("Call Api: " + url);
        Allure.step("Body data: " + gson.toJson(bodyData));
//        System.out.println("Body: "+ gson.toJson(bodyData));
        RequestSpecification req = given();
        rsp = req.baseUri(GlobalConstants.URL)
                .contentType("application/json")
                .header("x-app-id", "ca5e7185-9b34-4321-b51e-019f0a5ff63f")
                .body(gson.toJson(bodyData)).when().post(url);
//        Allure.step("Response: " + rsp.prettyPrint());
        return rsp;
    }

    public String getReponse(Response rsp, String jsonPath) {
        return rsp.getBody().path(jsonPath).toString();
    }

    public void deleteAllFilesInReportAllure() {
        System.out.println("---------- START delete file in folder ----------");
        deleteAllFileInFolder();
        System.out.println("---------- END delete file in folder ----------");
    }

    public void deleteAllFileInFolder() {
        try {
            String pathFolderDownload = GlobalConstants.PROJECT_PATH + "/allure-results";
            File file = new File(pathFolderDownload);
            File[] listOfFiles = file.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println(listOfFiles[i].getName());
                    new File(listOfFiles[i].toString()).delete();
                }
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }


}