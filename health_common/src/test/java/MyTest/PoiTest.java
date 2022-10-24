package MyTest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName MyTest.PoiTest
 * @date 2022/8/13 13:57
 */

public class PoiTest {
    public static void main(String[] args) throws IOException {
//        XSSFWorkbook workbook = new XSSFWorkbook("/Users/lihongyuan/Desktop/who am i.xlsx");
//        XSSFSheet rows = workbook.getSheetAt(0);
//
//        for (Row row : rows) {
//            for (Cell cell : row) {
//                String value = cell.getStringCellValue();
//                System.out.println(value);
//            }
//        }


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("姓名");

        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("1");
        row1.createCell(1).setCellValue("lili");

        XSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("2");
        row2.createCell(1).setCellValue("kkk");

        FileOutputStream outputStream = new FileOutputStream("/Users/lihongyuan/Desktop/小李.xlsx");
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }
}
