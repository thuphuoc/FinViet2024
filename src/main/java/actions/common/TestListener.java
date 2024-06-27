package actions.common;

import actions.helpers.PushTelgram;
import actions.utils.LogUtils;
import org.testng.*;
import org.testng.internal.Utils;
import java.util.List;
import java.util.Map;

public class TestListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

	static int count_totalTCs;
	static int count_passedTCs;
	static int count_skippedTCs;
	static int count_failedTCs;
	static String detatilTCFail = "";
	static String testName = "";

	public String getTestName(ITestResult result) {
		return result.getTestName() != null ? result.getTestName() : result.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		testName += getTestName(testResult);
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult result) {
		LogUtils.info("Run " + method.getTestMethod().getMethodName());

		Reporter.setCurrentTestResult(result);
		if (method.isTestMethod()) {
			VerificationFailures allFailures = VerificationFailures.getFailures();
			if (result.getThrowable() != null) {
				allFailures.addFailureForTest(result, result.getThrowable());
			}

			List<Throwable> failures = allFailures.getFailuresForTest(result);
			int size = failures.size() - 1;
			if (size > 0) {
				result.setStatus(ITestResult.FAILURE);
//				result.setThrowable(failures.get(size-1));
				if(size > 1) {
					StringBuffer message = new StringBuffer("Multiple failures (").append(size).append("):\n");
					for (int failure = 0; failure < size; failure++) {
						message.append("Failure ").append(failure + 1).append(" of ").append(size).append("\n");
						message.append(Utils.shortStackTrace(failures.get(failure), false)).append("\n");
					}
				}
			}
		}
	}

	@Override
	public void onStart(ISuite iSuite) {
		LogUtils.info("Starting Suite: " + iSuite.getName());
	}

	@Override
	public void onFinish(ISuite iSuite) {
		String suiteName = iSuite.getName();
//		FrameworkConstants.XOA_REPORT_ALLURE = suiteName;
		Map<String, String> suiteParameters = iSuite.getXmlSuite().getParameters();
		suiteName += " - " + suiteParameters;
		LogUtils.info("End Suite: " + suiteName);
//		if(FrameworkConstants.SEND_REPORT_TO_TELEGRAM.equals("yes")) {
			String message = getReportTel(iSuite);
			if(!message.equals("")) {
//				PushTelgram.sendMessageText("-4125801019", "7425188497:AAHe_lbuHxmhZsLFDIrMFQjEOb5Mx246JYY", message);
				PushTelgram.sendMessageText("-4117417249", "7425188497:AAHe_lbuHxmhZsLFDIrMFQjEOb5Mx246JYY", message);
			}

	}


	@Override
	public void onTestStart(ITestResult iTestResult) {
		count_totalTCs = count_totalTCs + 1;

	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		count_passedTCs = count_passedTCs + 1;
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		count_failedTCs = count_failedTCs + 1;
		LogUtils.error(iTestResult.getThrowable());


	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		count_skippedTCs = count_skippedTCs + 1;
		LogUtils.error(iTestResult.getThrowable());
	}


	private String getReportTel(ISuite iSuite) {
		String messager="";
//		if(count_failedTCs>0 || count_skippedTCs >0 ) {
			Map<String, ISuiteResult> r = iSuite.getResults();
			String testName = "";
			for (ISuiteResult r2 : r.values()) {
				ITestContext testContext = r2.getTestContext();
				testName += testContext.getName();
				System.out.println("testName: " + testName);
			}

			String className = iSuite.getName() + " || "+ testName;

			messager = "<b>[Auto Report]</b> - "+className+"\n \n "+
					"                TC Passed | TC Failed | TC Skiped | TC Total \n" +
					"                 ----- "+count_passedTCs+" -----  | ----- "+count_failedTCs+" ----- | ------ "+
					count_skippedTCs +" ------ | ----- "+count_totalTCs+" ----- \n";
			messager += "\n ********************************************************";
//					+ "\n <b>Descriptions TCs Failed</b> \n" + detatilTCFail + "\n" ;

	//	}
		return messager;
	}

}
