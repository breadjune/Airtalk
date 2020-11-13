package com.mobilepark.airtalk.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class UploadUtil {

    public Map<String, List<String>> upload(MultipartHttpServletRequest req) {
        
        String path;
        String newFileName;
        String fileName;
        List<String> realFiles = new ArrayList<>();
        List<String> newFiles = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        path = System.getProperty ("user.home")+"/upload/"+sdf.format(new Date())+"/" ;

        System.out.println("파일 업로드 경로 : " + path );

        File dir = new File(path);
        if(!dir.isDirectory()) dir.mkdir();

        Iterator<String> iterator = req.getFileNames();

        while(iterator.hasNext()) {
            MultipartFile mFile = req.getFile(iterator.next());
            fileName = mFile.getOriginalFilename();
            System.out.println("실제 파일 이름 : " + fileName );
            realFiles.add(fileName);

            if(fileName.isEmpty()) {
                newFileName = System.currentTimeMillis() + "." + fileName.substring(fileName.lastIndexOf('.') + 1);
                System.out.println("서버에 저장될 파일 이름 : " + newFileName );
                newFiles.add(newFileName);
                try {
                    mFile.transferTo(new File(path + newFileName));
                    // mreq.setAttribute("file_name", newFileName);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                newFiles.add("null");
            }
        }
        map.put("real", realFiles);
        map.put("new", newFiles);

        return map;

    }

}
