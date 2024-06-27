package delete_report;
import actions.common.BaseTest;
import org.testng.annotations.Test;

public class DeleteFileReportAllure extends BaseTest {
	@Test(priority = 1)
	public void TC01_DeleteFileReportAllure_Not_Care() {
		step("Run delete file in report allure");
		deleteAllFilesInReportAllure();
	}
}
