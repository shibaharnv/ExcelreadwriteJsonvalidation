import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ExcelWritewithComparison  {

    public  String pressurevalue;

    public ArrayList<String> Pressuevalueslist  = new ArrayList<String>();
    HashMap<Object,String> map= new HashMap<Object, String>();

    @Test
    public void parsingJsonCode(){

        String json="{\"message\":\"\",\"cod\":\"200\",\"city_id\":2643743,\"calctime\":0.0875,\"cnt\":3,\"list\":[{\"main\":{\"temp\":279.946,\"temp_min\":279.946,\"temp_max\":279.946,\"pressure\":1016.76,\"sea_level\":1024.45,\"grnd_level\":1016.76,\"humidity\":100},\"wind\":{\"speed\":4.59,\"deg\":163.001},\"clouds\":{\"all\":92},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"rain\":{\"3h\":2.69},\"dt\":1485717216},{\"main\":{\"temp\":282.597,\"temp_min\":282.597,\"temp_max\":282.597,\"pressure\":1012.12,\"sea_level\":1019.71,\"grnd_level\":1012.12,\"humidity\":98},\"wind\":{\"speed\":4.04,\"deg\":226},\"clouds\":{\"all\":92},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"rain\":{\"3h\":0.405},\"dt\":1485745061},{\"main\":{\"temp\":279.38,\"pressure\":1011,\"humidity\":93,\"temp_min\":278.15,\"temp_max\":280.15,\"sea_level\":0,\"grnd_level\":0},\"wind\":{\"speed\":2.6,\"deg\":30},\"clouds\":{\"all\":90},\"weather\":[{\"id\":701,\"main\":\"Mist\",\"description\":\"mist\",\"icon\":\"50d\"},{\"id\":741,\"main\":\"Fog\",\"description\":\"fog\",\"icon\":\"50d\"}],\"dt\":1485768552}]}\n";

        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

        String message = obj.get("message").getAsString();
        String cod = obj.get("cod").getAsString();
        String city_id = obj.get("city_id").getAsString();
        String calctime = obj.get("calctime").getAsString();
        String cnt = obj.get("cnt").getAsString();

        System.out.println("message " +message);
        System.out.println("cod " +cod);
        System.out.println("city_id " +city_id);
        System.out.println("calctime " +calctime);
        System.out.println("cnt " +cnt);



        JsonArray arr = obj.getAsJsonArray("list");
        for (int i = 0; i < arr.size(); i++) {


            String tempvalue =   arr.get(i).getAsJsonObject().getAsJsonObject("main").get("temp").getAsString();
            String tempminvalue =   arr.get(i).getAsJsonObject().getAsJsonObject("main").get("temp_min").getAsString();
            String tempMaxvalue =   arr.get(i).getAsJsonObject().getAsJsonObject("main").get("temp_max").getAsString();
            pressurevalue =   arr.get(i).getAsJsonObject().getAsJsonObject("main").get("pressure").getAsString();
            String  sealevelvalue = arr.get(i).getAsJsonObject().getAsJsonObject("main").get("sea_level").getAsString();
            String  grndlevelvalue = arr.get(i).getAsJsonObject().getAsJsonObject("main").get("grnd_level").getAsString();
            String humidityvalue =   arr.get(i).getAsJsonObject().getAsJsonObject("main").get("humidity").getAsString();
            String speedvalue =   arr.get(i).getAsJsonObject().getAsJsonObject("wind").get("speed").getAsString();
            String degvalue =   arr.get(i).getAsJsonObject().getAsJsonObject("wind").get("deg").getAsString();
            String allvalue =   arr.get(i).getAsJsonObject().getAsJsonObject("clouds").get("all").getAsString();
            String dtvalue =   arr.get(i).getAsJsonObject().get("dt").getAsString();
            map.put("temp",tempvalue);
            map.put("temp_min",tempminvalue);
            map.put("temp_max",tempMaxvalue);
            map.put("pressure",pressurevalue);
            map.put("sea_level",sealevelvalue);
            map.put("grnd_level",grndlevelvalue);
            map.put("humidity",humidityvalue);
            map.put("speed",speedvalue);
            map.put("deg",degvalue);
            map.put("all",allvalue);
            map.put("dt",dtvalue);


            Pressuevalueslist.add(pressurevalue);



        }


        System.out.println("Printing the map values " +map.entrySet());

        System.out.println("Pressuevalueslist" +Pressuevalueslist);
    }


    @Test(dependsOnMethods ="parsingJsonCode" )
    public void excelWritewithComparsiontest() throws IOException {


        ArrayList<String> values = new ArrayList<String>();
        //ArrayList <String> Actvalues = new ArrayList<String>();


        String excelReadFilePath=System.getProperty("user.dir")
                + "//src//test//excelfile//jsontoexcelconverted.xlsx";

        String excelWriteFilePath=System.getProperty("user.dir")
                + "//src//test//excelfile//ExcelWrite.xlsx";

        File myFile = new File(excelReadFilePath);
        FileInputStream fis = new FileInputStream(myFile);

        XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);

        for(int i=1;i<4;i++) {

            Row row = mySheet.getRow(i);
            Cell cell = row.getCell(3);
            values.add(cell.getStringCellValue()) ;
        }
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet hsheet = wb.createSheet("Results");

        XSSFRow rowhead = hsheet.createRow((short) 0);
        rowhead.createCell((short) 0).setCellValue("Expected Output");
        rowhead.createCell((short) 1).setCellValue("Actual Output");

        for(int k=0;k<values.size();k++) {

            rowhead = hsheet.createRow((short) k+1);


            String expvalue = values.get(k);
            String actvalue = Pressuevalueslist.get(k);

            rowhead.createCell((short) 0).setCellValue(expvalue);
            rowhead.createCell((short) 1).setCellValue(actvalue);
        }

        hsheet.autoSizeColumn(0);
        hsheet.autoSizeColumn(1);

        FileOutputStream fileOut = new FileOutputStream(excelWriteFilePath);
        wb.write(fileOut);
        fileOut.close();
        System.out.println("Able to write Data in Excel file Sucessfully.");
    }
    }