package com.mobilepark.airtalk.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class DownUtil {

    private static final Logger logger = LoggerFactory.getLogger(DownUtil.class);
    private Environment env;

    private static final String CHROME = "chrome";
    private static final String MSIE = "MSIE";
    private static final String OPERA = "Opera";
    private static final String FIREFOX = "Firefox";
    private static final String UTF_8 = "UTF-8";


    public void download(Map<String, Object> model, HttpServletRequest req, HttpServletResponse res, String location) throws IOException {

        String fileName = model.get("fileName").toString();

        logger.info(">>>>>>>properteisLocation : {}", location);

        if (model.get("type").equals("pdf")) {

            String defaultDownloadFileName = "vacation.pdf";

            try {
                if (StringUtils.isBlank(fileName)) {
                    fileName = defaultDownloadFileName;
                }

                if (fileName.indexOf(".pdf") == -1) {
                    fileName = fileName + ".pdf";
                }
                String temp = fileName;

                String userBrowser = req.getHeader("User-Agent");

                boolean ie = false;
                if (userBrowser.indexOf(MSIE) > -1 || userBrowser.indexOf("Edge") > -1 || userBrowser.indexOf("Trident") > -1) {
                    ie = true;
                }

                if (ie) {
                    fileName = URLEncoder.encode(fileName, UTF_8).replaceAll("\\+", "%20");
                } else {
                    fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                }// end if;

                res.setContentType("application/download");
                res.setHeader("Content-Disposition", "attachment; filename=" + fileName);

                String serverPath = location + File.separator + temp;
                File serverCheck = new File(location + File.separator + temp);

                FileInputStream fis = new FileInputStream(serverPath);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ServletOutputStream so = res.getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(so);

                byte[] data = new byte[2048];
                int input = 0;

                while ((input = bis.read(data)) != -1) {
                    bos.write(data, 0, input);
                    bos.flush();
                }

                if (bos != null) bos.close();
                if (bis != null) bis.close();
                if (so != null) so.close();
                if (fis != null) fis.close();

                if (serverCheck.exists()) {
                    serverCheck.delete();
                }
            } catch (IOException io) {
                logger.info("pdfTest IOException : {}", io);
            } catch (Exception e) {
                logger.info("pdfTest Exception : {}", e);
            }

        }

        if (model.get("type").equals("excel")) {

            String browser = getBrowser(req);
            logger.info("browser : {}", browser);

            String disposition = getDisposition(fileName, browser);

            Workbook wb = null;

            try {
                wb = (Workbook) model.get("workbook");

                res.setHeader("Content-Disposition", disposition);
                res.setContentType("application/octet-stream");

                wb.write(res.getOutputStream());

            } catch (Exception e) {

                logger.error("error : ", e);

            } //try-catch

        } //if
        if (model.get("type").equals("file")) { //이미지 다운로드 및 zip 다운로드 이용

            String browser = getBrowser(req);
            logger.info("browserName : {}", browser);

            String disposition = getDisposition(fileName, browser);

            File file = new File(location);

            ServletOutputStream servletOutputStream = null;

            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                res.setHeader("Content-Disposition", disposition);
                res.setContentType("application/octer-stream");
                res.setHeader("Content-Transfer-Encoding", "binary;");


                servletOutputStream = res.getOutputStream();

                byte[] b = new byte[1024];
                int data = 0;

                while ((data = (fileInputStream.read(b, 0, b.length))) != -1) {
                    servletOutputStream.write(b, 0, data);
                }
                servletOutputStream.flush();//출력

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                if (servletOutputStream != null) {
                    try {
                        servletOutputStream.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        } //if
    } //download

    private String getBrowser(HttpServletRequest req) {
        String header = req.getHeader("User-Agent");
        if (header.indexOf(MSIE) > -1 || header.indexOf("Trident") > -1) return MSIE;
        else if (header.indexOf(CHROME) > -1) return CHROME;
        else if (header.indexOf(OPERA) > -1) return OPERA;
        return FIREFOX;

    } //browser

    private String getDisposition(String filename, String browser) throws UnsupportedEncodingException {
        String dispositionPrefix = "attachment; filename=";
        String encodedFilename = null;
        if (browser.equals(MSIE)) {
            encodedFilename = URLEncoder.encode(filename, UTF_8).replaceAll("\\+", "%20");
        } else if (browser.equals(FIREFOX)) {
            encodedFilename = "\"" + new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\"";
        } else if (browser.equals(OPERA)) {
            encodedFilename = "\"" + new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\"";
        } else if (browser.equals(CHROME)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < filename.length(); i++) {
                char c = filename.charAt(i);
                if (c > '~') {
                    sb.append(URLEncoder.encode("" + c, UTF_8));
                } else {
                    sb.append(c);
                } //else
            } //for
            encodedFilename = sb.toString();

        } //else-if
        return dispositionPrefix + encodedFilename;

    } //getDisposition
}
