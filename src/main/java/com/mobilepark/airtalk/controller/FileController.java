package com.mobilepark.airtalk.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.mobilepark.airtalk.data.BoardFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest/file")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    
    @RequestMapping(value="/search.json",method = RequestMethod.POST)
    public @ResponseBody List<BoardFile> search (Model model, @RequestBody String form) {
        List<BoardFile> list = new ArrayList<>();
        for(int i=0; i <= 4; i++) {
            BoardFile boardFile = new BoardFile();

            boardFile.setSeq(i);
            boardFile.setTitle("파일게시판 테스트"+i);
            // boardFile.setContents("콘텐츠"+i);
            boardFile.setWriter("작성자"+i);
            // boardFile.setFileName("File Name"+i);
            // boardFile.setFileRealName("File Read Name"+i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            boardFile.setRegDate(sdf.format(new Date()));
            
            list.add(boardFile);
            logger.info(boardFile.toString());
        }

        return list;

    }

}
