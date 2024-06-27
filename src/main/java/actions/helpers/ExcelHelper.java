package actions.helpers;

import java.awt.Color;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;

public class ExcelHelper {

    private FileInputStream fis;
    private FileOutputStream fileOut;
    private Workbook wb;
    private Sheet sh;
    private Cell cell;
    private Row row;
    private CellStyle cellstyle;
    private Color mycolor;
    private String excelFilePath;
    private Map<String, Integer> columns = new HashMap<>();

    public void setExcelFile(String ExcelPath, String SheetName) {
        try {
            File f = new File(ExcelPath);

            if (!f.exists()) {
                f.createNewFile();
                System.out.println("File doesn't exist, so created!");
            }

            fis = new FileInputStream(ExcelPath);
            wb = WorkbookFactory.create(fis);
            sh = wb.getSheet(SheetName);
            //sh = wb.getSheetAt(0); //0 - index of 1st sheet
            if (sh == null) {
                sh = wb.createSheet(SheetName);
            }

            this.excelFilePath = ExcelPath;

            //adding all the column header names to the map 'columns'
            sh.getRow(0).forEach(cell -> {
                columns.put(cell.getStringCellValue(), cell.getColumnIndex());
            });

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getCellData(int rownum, int colnum) {
        try {
            cell = sh.getRow(rownum).getCell(colnum);
            String CellData = null;
            switch (cell.getCellType()) {
                case STRING:
                    CellData = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        CellData = String.valueOf(cell.getDateCellValue());
                    } else {
                        CellData = String.valueOf((long) cell.getNumericCellValue());
                    }
                    break;
                case BOOLEAN:
                    CellData = Boolean.toString(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    CellData = "";
                    break;
            }
            return CellData;
        } catch (Exception e) {
            return "";
        }
    }

    public String getCellData(String columnName, int rownum) {
        return getCellData(rownum, columns.get(columnName));
    }

    public void setCellData(String text, int rownum, String columnName) {
        try {
            row = sh.getRow(rownum);
            if (row == null) {
                row = sh.createRow(rownum);
            }

            int colnum = columns.get(columnName);
            cell = row.getCell(colnum);
            if (cell == null) {
                cell = row.createCell(colnum);
            }

            cell.setCellValue(text);
            FileOutputStream fileOut = new FileOutputStream(excelFilePath);
            wb.write(fileOut);
            // Không nên đóng luồng fileOut tại đây
            fileOut.flush(); // Nếu cần
        } catch (Exception e) {
            System.err.println("Error write data excel with row " + row + " column " + columnName + " error:" + e.getMessage());
        }
    }


    // Phương thức để đếm số ô chứa dữ liệu từ cột startColumn đến endColumn
    public int countCellsInRange(int startColumn, int endColumn, int rowIndex) {
        int count = 0;
        Row row = sh.getRow(rowIndex);
        if (row != null) {
            for (int i = startColumn; i <= endColumn; i++) {
                Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    count++;
                }
            }
        }
        return count;
    }


    public int indexColumByText(String columnName) {
        Row firstRow = sh.getRow(0); // Lấy dòng đầu tiên (chứa tên cột)

        if (firstRow != null) {
            for (int i = 0; i < firstRow.getLastCellNum(); i++) {
                Cell cell = firstRow.getCell(i);

                if (cell != null && cell.getStringCellValue().equals(columnName)) {
                    return cell.getColumnIndex();
                }
            }
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }


    public boolean isCellHasData(String columnName, int row) {
        try {
            return !(getCellData(columnName, row).isEmpty());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int countRowsHasData() {
        int rowCount = 0;
        for (Row row : sh) {
            boolean rowHasData = false;
            for (Cell cell : row) {
                if (cell.getCellType() != CellType.BLANK) {
                    rowHasData = true;
                    break;
                }
            }
            if (rowHasData) {
                rowCount++;
            }
        }
        return rowCount;
    }

    public void closeExecl() {
        try {
            wb.close();
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void copyFile(String source_FilePath, String target_FilePath) {
        try {
            Files.copy(Paths.get(source_FilePath), Paths.get(target_FilePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteColumnData(String columnName) {
        int columnIndex = indexColumByText(columnName);
        try{
        for (int i = 1; i <= sh.getLastRowNum(); i++) { // Bắt đầu từ dòng thứ hai (dòng index 1)
            Row row = sh.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(columnIndex);
                if (cell != null) {
                    row.removeCell(cell); // Xóa cell trong dòng
                }
            }
            FileOutputStream fileOut = new FileOutputStream(excelFilePath);
            wb.write(fileOut);
            fileOut.flush();
        }
    }catch(Exception e){
            System.err.println("Error delete data excel with row " + row + " column " + columnName + " error:" + e.getMessage());
        }
}

public int countRowsHasTextWithColumName(int targetValue, String columName) {
    int columnIndex = indexColumByText(columName);
    int count = 0;
    for (Row row : sh) {
        Cell cell = row.getCell(columnIndex);
        if (cell != null && cell.getCellType() == CellType.NUMERIC && cell.getNumericCellValue() == targetValue) {
            count++;
        }
    }
    return count;
}

public List<Integer> findPositions(int columnIndex, int targetValue) {
    List<Integer> positions = new ArrayList<>();
    boolean inSequence = false;
    int start = -1;

    for (int i = 0; i <= sh.getLastRowNum(); i++) {
        Row row = sh.getRow(i);
        if (row != null) {
            Cell cell = row.getCell(columnIndex);
            if (cell != null && cell.getCellType() == CellType.NUMERIC && cell.getNumericCellValue() == targetValue) {
                if (!inSequence) {
                    start = i;
                    inSequence = true;
                }
            } else {
                if (inSequence) {
                    positions.add(start);
                    positions.add(i - 1);
                    inSequence = false;
                }
            }
        }
    }

    // Kiểm tra nếu chuỗi số 1 kết thúc ở hàng cuối cùng
    if (inSequence) {
        positions.add(start);
        positions.add(sh.getLastRowNum());
    }

    return positions;
}

}