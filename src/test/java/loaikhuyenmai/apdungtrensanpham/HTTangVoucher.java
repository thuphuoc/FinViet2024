package loaikhuyenmai.apdungtrensanpham;

import actions.common.BasePage;
import actions.helpers.ExcelHelper;
import org.testng.annotations.Test;

import java.util.List;

public class HTTangVoucher extends BasePage {
    BasePage bs= new BasePage();
    @Test
    public void tc() throws Exception {
        ExcelHelper excel = new ExcelHelper();
        String excelPath="src/main/resources/Data_KhuyenMai.xlsx";
        excel.setExcelFile(excelPath,"valid_data");
        excel.deleteColumnData("Mong đợi");
    }
}
