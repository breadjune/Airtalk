package com.mobilepark.airtalk.util;

import com.mobilepark.airtalk.data.Book;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public HSSFWorkbook excelDownload(List<Book> list) throws IllegalAccessException  {

        //워크북 생성
        HSSFWorkbook workBook = new HSSFWorkbook();

        //VO list로 변환
        List<String> voKeys = new ArrayList<>();
        List<List<String>> voValueList = new ArrayList<>();

        //VO 컬럼 추출
        objFormet(list, voKeys, voValueList);

        //시트 생성
        HSSFSheet sheet = workBook.createSheet("BookList");
        HSSFCellStyle style = workBook.createCellStyle();

        //테이블 테두리 생성
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);

        style.setVerticalAlignment(CellStyle.ALIGN_CENTER);

        //시트 열 너비 설정
        for(int i=0; i<voKeys.size(); i++) {

            int valueLeng = 0;
            int temp = 0;

            for(int j=0; j<voValueList.size(); j++) {

                valueLeng = voKeys.get(i).length();

                temp = voValueList.get(j).get(i).length();

                if(temp > valueLeng) valueLeng = temp;

            }

            sheet.setColumnWidth(i, valueLeng*500);

        }

        //헤더 row 생성
        HSSFRow headerRow = sheet.createRow(0);

        //헤더열의 셀 생성
        for(int i=0; i<voKeys.size(); i++) {

            HSSFCell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(voKeys.get(i));
            headerCell.setCellStyle(style);

        }

        //body row&Cell 선언
        HSSFRow bodyRow = null;
        HSSFCell bodyCell = null;

        for(int i=0; i<list.size(); i++) {
            List<String> booklist = voValueList.get(i);

            bodyRow = sheet.createRow(i+1);

            for(int j=0; j<booklist.size(); j++) {

                bodyCell = bodyRow.createCell(j);

                bodyCell.setCellValue(booklist.get(j));

                bodyCell.setCellStyle(style);
            }

        } //end for

        //제목 폰트
        HSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short)9);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("맑은고딕");

        //cell 테두리 적용

        logger.info("workbook create successed! : {}", workBook);

        return workBook;

    }

    public static void objFormet(List<Book> list, List<String> voKeys, List<List<String>> voValueList) throws IllegalAccessException {

        //row 데이터 개수 만큼 반복 진행
        for(int i=0; i<list.size(); i++) {

            Class<?> getCls = list.get(i).getClass();
            Class<?> getSuperCls = getCls.getSuperclass();
            List<String> valueList = new ArrayList<>();

            forEach(getCls, list, voKeys, valueList, i);
            forEach(getSuperCls, list, voKeys, valueList, i);

            logger.info("voKeys : {}", voKeys);
            voValueList.add(valueList);

        }

        logger.info("volist size : {}", voKeys);
    }

    public static void forEach(Class<?> clazz,
                               List<Book> list,
                               List<String> voKeys,
                               List<String> valueList,
                               int count) throws IllegalAccessException{

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);

        for(Field data : clazz.getDeclaredFields()) {

            data.setAccessible(true);

            Object stringValue = null;

            try {

                if (data.getName().equals("serialVersionUID")) continue;

                else if (count == 0) {
                    String key = data.getName();
                    voKeys.add(key);
                }

                stringValue = data.get(list.get(count));

                if (stringValue == null) stringValue = "";
                else if (data.getName().equals("regDate")) stringValue = dateFormat.format((Date) stringValue);
                else if (data.getName().equals("modDate")) stringValue = dateFormat.format((Date) stringValue);

                valueList.add(stringValue.toString());
                logger.info("valueList : {}", stringValue);

            } catch (IllegalArgumentException e) {
                logger.error("IllegalArgumentException");
            } catch (IllegalAccessException e) {
                logger.error("IllegalAccessException");
            }

        }
    }

} //end class
