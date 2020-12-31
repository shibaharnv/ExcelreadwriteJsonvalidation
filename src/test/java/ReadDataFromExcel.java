import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadDataFromExcel {

    @Test
    public void readDataFromExcelTest() throws IOException {

        String excelFilePath=System.getProperty("user.dir")
                + "//src//test//excelfile//jsontoexcelconverted.xlsx";

        File myFile = new File(excelFilePath);


        FileInputStream fis = new FileInputStream(myFile);

        XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);

        int rownum = mySheet.getLastRowNum();

        System.out.println("Reading the Data from Excel and print the values");

        String value=" Null";

        for(int i=0;i<=rownum;i++) {

            for(int j=0;j<=4;j++) {

                Row row = mySheet.getRow(i);
                Cell cell = row.getCell(j);
                value = cell.getStringCellValue();

                System.out.print(value);
            }

            System.out.println();
        }

    }


}