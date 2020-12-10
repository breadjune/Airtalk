package com.mobilepark.airtalk.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.airtalk.data.Board;
import com.mobilepark.airtalk.data.FileData;
import com.mobilepark.airtalk.service.BoardService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/restapi/board")
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
    
    @Autowired
    public BoardService boardService;

    @RequestMapping(value="/list",method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> list (Model model, @RequestBody JSONObject form) {
        Map<String, Object> map = boardService.list(form);
        return map;
    }

    @RequestMapping(value="/search",method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> search (Model model, @RequestBody JSONObject form) {
        Map<String, Object> map = boardService.search(form);
        return map;
    }

    @RequestMapping(value="/create",method = RequestMethod.POST)
    public @ResponseBody Map<String, String> create (Board board) throws Exception {
        
        Map<String, String> map = new HashMap<>();
        logger.info("["+board.getBcode()+"]["+board.getTitle()+"]["+board.getWriter()+"]["+board.getContents()+"]");
        board = boardService.create(board);
        map.put("seq", String.valueOf(board.getSeq()));
        return map;
    }

    @RequestMapping(value="/uploadCreate",method = RequestMethod.POST)
    public @ResponseBody Map<String, String> create (@RequestParam(value = "files") MultipartFile files, Board board) throws Exception {
        
        Map<String, String> map = new HashMap<>();
        String result = "";
        logger.info("files original name : " + files.getOriginalFilename());
        logger.info("files name : " + files.getName());
        logger.info("["+files.getName()+"]["+board.getBcode()+"]["+board.getTitle()+"]["+board.getWriter()+"]["+board.getContents()+"]");
        
        board = boardService.create(board);
        result = boardService.upload(board.getSeq(), board.getBcode(), files);
        
        map.put("result", result);
        return map;
    }


    // @RequestMapping(value="/create",method = RequestMethod.POST)
    // public @ResponseBody Map<String, String> create (Model model, @RequestParam(value = "bcode") String bcode,
    //                                                               @RequestParam(value = "files") MultipartFile files,
    //                                                               @RequestParam(value = "title") String title,
    //                                                               @RequestParam(value = "writer") String writer,
    //                                                               @RequestParam(value = "contents") String contents) throws Exception {
        
    //     // for(MultipartFile file : files) {
    //     //     logger.info("file original name : " + file.getOriginalFilename());
    //     //     logger.info("files name : " + file.getName());
    //     // }
    //     Board board = new Board();
    //     Map<String, String> map = new HashMap<>();
    //     String result = "";
    //     logger.info("files original name : " + files.getOriginalFilename());
    //     logger.info("files name : " + files.getName());
    //     logger.info("["+files.getName()+"]["+bcode+"]["+title+"]["+writer+"]["+contents+"]");

    //     board.setBcode(bcode);
    //     board.setTitle(title);
    //     board.setWriter(writer);
    //     board.setContents(contents);
        
    //     if(files.isEmpty()) boardService.create(board);
    //     else {
    //         board = boardService.create(board);
    //         result = boardService.upload(board.getSeq(), board.getBcode(), files);
    //     }
    //     // Map<String, String> map = new HashMap<>();
    //     map.put("result", result);
    //     return map;
    // }

    @RequestMapping(value="/modify",method = RequestMethod.POST)
    public @ResponseBody Map<String, String> modify (Model model, @RequestParam(value = "files") MultipartFile files, Board board) throws Exception {
        Map<String, String> map = new HashMap<>();
        boardService.create(board);
        map.put("result", "sucess");
        return map;
    }

    @RequestMapping(value="/delete",method = RequestMethod.POST)
    public @ResponseBody Map<String, String> delete (Model model, Board board) {
        Map<String, String> map = boardService.delete(board);
        return map;
    }

    @RequestMapping(value="/fileNameInfo",method = RequestMethod.POST)
    public @ResponseBody Map<String, String> getfileName (Model model, FileData fildata) {
        Map<String, String> map = boardService.getfileName(fildata);
        return map;
    }
    
    @RequestMapping(value="/download",method = RequestMethod.POST)
    public Map<String, String> upload (Model model, @RequestParam(value = "seq") int seq, 
                                                    @RequestParam(value = "bcode") String bcode,
                                                    @RequestParam(value = "files") MultipartFile files) throws IOException{
                                                        
        String result = boardService.upload(seq, bcode, files);  
        Map<String, String> map = new HashMap<>();       
        map.put("result", "sucess");                                      
    return map;
                                                    
    }
    @RequestMapping(value="/download",method = RequestMethod.GET)
    public ResponseEntity<Resource> download (Model model, @RequestParam(value = "seq") int seq) throws IOException{
    
        Path path = Paths.get(boardService.download(seq));
        String contentType = Files.probeContentType(path);

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path.getFileName().toString());

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
