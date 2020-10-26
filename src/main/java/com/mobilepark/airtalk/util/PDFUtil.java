package com.mobilepark.airtalk.util;

import com.mobilepark.airtalk.data.Vacation;
import com.mobilepark.airtalk.service.VacationService;
import org.apache.fontbox.ttf.TrueTypeCollection;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PDFUtil {

    @Autowired
    ServletContext context;

    private static final Logger logger = LoggerFactory.getLogger(VacationService.class);

    public static String pdfCreate(String writerName, HttpServletResponse response, Vacation vacation, String type, String rank, String location) throws Exception, IOException {

        File path = new File(location);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date currentDate = new Date();
        String date = dateFormat.format(currentDate);

        String fileName = writerName + "_" + date;

        PDDocument pdfDocument = new PDDocument();

        File pdfFont = new File("src/main/resources/font/gulim.ttc");

        PDType0Font fontGulim = PDType0Font.load(pdfDocument, new TrueTypeCollection(pdfFont).getFontByName("Gulim"), true);

        File pdfFileName = new File(location + File.separator + fileName + ".pdf");
        File originalPDFfile = new File(location + File.separator + fileName + "old.pdf");

        try {
            // 해당 경로에 디렉토리가 없을 경우 생성
            if (!path.exists()) {
                path.mkdir();
            }

            // 같은 이름의 파일 명 있는지 확인 있을 경우 기존 파일 명 변경

            if (pdfFileName.exists()) {
                pdfFileName.renameTo(originalPDFfile);
            }

            // 페이지 추가
            PDPage page = new PDPage(PDRectangle.A4);
            pdfDocument.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);

            drawTable(contentStream, fontGulim, vacation, pdfDocument, type, rank);

            contentStream.close();

            pdfDocument.save(pdfFileName);
            pdfDocument.close();

        } catch (IOException io) {
            logger.error("PDF download IOException : " + io);
        } catch (Exception e) {
            logger.error("PDF download Exception : " + e);
        }

        return fileName;
    }

    private static void drawText(String text, PDFont font, int fontSize, float left, float bottom, PDPageContentStream pdpContentStream) throws Exception {
        pdpContentStream.beginText();
        pdpContentStream.setFont(font, fontSize);
        pdpContentStream.newLineAtOffset(left, bottom);
        pdpContentStream.showText(text);
        pdpContentStream.endText();
    }

    private static void drawLine (PDPageContentStream pdpContentStream, float xStart, float yStart, float xEnd, float yEnd) throws IOException {
        pdpContentStream.moveTo(xStart,yStart);
        pdpContentStream.lineTo(xEnd,yEnd);
        pdpContentStream.stroke();
    }

    private static void drawTable (PDPageContentStream pdpContentStream, PDType0Font fontGulim, Vacation vacation, PDDocument pdfDocument, String type, String rank) throws Exception {

        String startDate = vacation.getStartDate();
        String sYear = startDate.substring(0, 4);
        String sMonth = startDate.substring(5, 7);
        String sDay = startDate.substring(8, 10);

        String endDate = vacation.getEndDate();
        String eYear = endDate.substring(0, 4);
        String eMonth = endDate.substring(5, 7);
        String eDay = endDate.substring(8, 10);

        String pNum = vacation.getPhoneNum();
        String fNum = "";
        String sNum = "";
        String tNum = "";

        if (pNum.length() < 11) {
            fNum = pNum.substring(0, 3);
            sNum = pNum.substring(3, 6);
            tNum = pNum.substring(6, 10);
        }
        else {
            fNum = pNum.substring(0, 3);
            sNum = pNum.substring(3, 7);
            tNum = pNum.substring(7, 11);
        }

        String regDate = vacation.getRegDate().toString();
        String modDate = vacation.getModDate().toString();

        // 회사 logo
        PDImageXObject pdImage = PDImageXObject.createFromFile("src/main/resources/image/mobilepark_logo.png", pdfDocument);
        pdpContentStream.drawImage(pdImage, 30, 715);

        // 행
        drawLine(pdpContentStream, 30, 80, 570, 80);      // 테이블 틀
        drawLine(pdpContentStream, 30, 710, 570, 710);      // 테이블 틀
        drawText("휴 가 신 청 서", fontGulim, 30, 205, 650, pdpContentStream);
        drawLine(pdpContentStream, 30, 620, 570, 620);

        // 결재
        drawLine(pdpContentStream, 30, 520, 570, 520);
        drawLine(pdpContentStream, 320, 620, 320, 520);     // 결재 칸
        drawText("결", fontGulim, 15, 337, 587, pdpContentStream);
        drawText("재", fontGulim, 15, 337, 542, pdpContentStream);
        drawLine(pdpContentStream, 370, 620, 370, 520);     // 결재 칸
        drawText("담   당", fontGulim, 15, 383, 599, pdpContentStream);
        drawLine(pdpContentStream, 438, 620, 438, 520);     // 담당, 팀장, 대표 칸 구분 선
        drawText("팀   장", fontGulim, 15, 450, 599, pdpContentStream);
        drawLine(pdpContentStream, 505, 620, 505, 520);     // 담당, 팀장, 대표 칸 구분 선
        drawText("대   표", fontGulim, 15, 515, 599, pdpContentStream);
        drawLine(pdpContentStream, 370, 590, 570, 590);     // 담당, 팀장, 대표 칸

        // 신청내역
        drawText("신 청 내 역", fontGulim, 15, 263, 500, pdpContentStream);
        drawLine(pdpContentStream, 30, 490, 570, 490);

        // 소속, 직급
        drawText("소    속", fontGulim, 15, 58, 470, pdpContentStream);
        drawLine(pdpContentStream, 135, 490, 135, 370);
        drawText("모바일파크", fontGulim, 15, 190, 470, pdpContentStream);
        drawLine(pdpContentStream, 320, 490, 320, 430);
        drawText("직위·직급", fontGulim, 15, 334, 470, pdpContentStream);
        drawLine(pdpContentStream, 415, 490, 415, 430);
        drawText(rank, fontGulim, 15, 479, 470, pdpContentStream);
        drawLine(pdpContentStream, 30, 460, 570, 460);


        // 휴가중 연락처, 성명
        drawLine(pdpContentStream, 30, 430, 570, 430);
        drawText("휴가중 연락처", fontGulim, 15, 35, 440, pdpContentStream);
        drawText(fNum + "-" + sNum + "-" + tNum, fontGulim, 15, 172, 440, pdpContentStream);
        drawText("성    명", fontGulim, 15, 342, 440, pdpContentStream);
        drawText(vacation.getAdmin().getAdminName(), fontGulim, 15, 473, 440, pdpContentStream);
        drawLine(pdpContentStream, 30, 400, 570, 400);



        // 휴가 구분
        drawText("휴 가 구 분", fontGulim, 15, 45, 410, pdpContentStream);
        drawText(type, fontGulim, 15, 190, 410, pdpContentStream);


        // 일시
        drawLine(pdpContentStream, 30, 370, 570, 370);
        drawText("일    시", fontGulim, 15, 58, 380, pdpContentStream);
        drawText(sYear + "년 " + sMonth + "월 " + sDay + "일부터", fontGulim, 15, 150, 380, pdpContentStream);
        drawText(eYear + "년 " + eMonth + "월 " + eDay + "일까지", fontGulim, 15, 320, 380, pdpContentStream);
        drawText("총  " + vacation.getPeriod() + "일간", fontGulim, 15, 490, 380, pdpContentStream);


        drawText("위와 같이 휴가를 신청하오니 허가하여 주시기 바랍니다.", fontGulim, 15, 120, 280, pdpContentStream);

        if (!("".equals(modDate)) || (modDate != null)) {
            String mYear = modDate.substring(0, 4);
            String mMonth = modDate.substring(5, 7);
            String mDay = modDate.substring(8, 10);
            drawText(mYear + "년 " + mMonth + "월 " + mDay + "일", fontGulim, 15, 240, 230, pdpContentStream);
        }
        else {
            String rYear = regDate.substring(0, 4);
            String rMonth = regDate.substring(5, 7);
            String rDay = regDate.substring(8, 10);
            drawText(rYear + "년 " + rMonth + "월 " + rDay + "일", fontGulim, 15, 240, 230, pdpContentStream);
        }

        drawText("신청자 성명 : ", fontGulim, 15, 233, 180, pdpContentStream);
        drawText(vacation.getAdmin().getAdminName(), fontGulim, 15, 328, 180, pdpContentStream);


        // 열
        drawLine(pdpContentStream, 30, 80, 30, 710);       // 테이블 틀
        drawLine(pdpContentStream, 570, 80, 570, 710);     // 테이블 틀

        drawText("주)1. 전결 사항    반차 및 1일 이하 = 팀장     //     2일 이상 = 대표", fontGulim, 15, 30, 65, pdpContentStream);
        drawText("주)2. 모든 문서는 이메일로 처리", fontGulim, 15, 30, 48, pdpContentStream);
    }
}
