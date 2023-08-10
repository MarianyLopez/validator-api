package com.example.prosecution.services;


import com.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProsecutionService {

    public String  readFile(String filePath){
        if (filePath.contains("csv")){
            return readCSV(filePath);
        }else
            return readExcel(filePath);
    }

    private String readCSV(String filePath) {
        try(CSVReader csvReader =new CSVReader(new FileReader(filePath))) {
            List<String[]> lineas = csvReader.readAll();
            int valid=0;
            int invalid=0;
            for (int i = 0; i < lineas.size(); i++) {
                if (i>0) {
                    String[] linea = {lineas.get(i)[5], lineas.get(i)[7], lineas.get(i)[8]};
                    //String[] strings = {linea[5], linea[7], linea[8]};

                    String url ="http://localhost:8081/api/v1/validation";

                    ResponseEntity<Boolean> response = new RestTemplate().postForEntity(url,linea, Boolean.class);

                    if(Boolean.TRUE.equals(response.getBody()))
                        valid++;
                    else
                        invalid++;

                }
            }
            return "Numero de lineas validas: " + valid + ". Numero de lineas invalidas: " + invalid;

        }catch (Exception e){
            return e.toString();
        }
    }

    private String readExcel(String filePath){
        try (InputStream inputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            List<String[]> dataBase = new ArrayList<>();
            String[] data = new String[14];
            int valid=0;
            int invalid=0;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                int cont = 0;
                for (Cell cell : row) {
                    DataFormatter formatter = new DataFormatter();
                    data[cont] = formatter.formatCellValue(cell);
                    cont++;
                }
                dataBase.add(data);
                String [] strings = {data[1],data[7]};

                String url ="http://localhost:8081/api/v1/validation";

                ResponseEntity <Boolean> response = new RestTemplate().postForEntity(url,strings, Boolean.class);

                if(Boolean.TRUE.equals(response.getBody()))
                    valid++;
                else
                    invalid++;
            }
           return "Numero de lineas validas: " + valid + ". Numero de lineas invalidas: " + invalid;

        } catch (Exception e) {
            return e.toString();
        }
    }

}
