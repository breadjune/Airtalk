package com.mobilepark.airtalk.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
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
        logger.info("["+board.getSeq()+"]["+board.getBcode()+"]["+board.getTitle()+"]["+board.getWriter()+"]["+board.getContents()+"]");
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
        logger.info("["+files.getName()+"]["+board.getSeq()+"]["+board.getBcode()+"]["+board.getTitle()+"]["+board.getWriter()+"]["+board.getContents()+"]");
        
        board = boardService.create(board);
        result = boardService.upload(board.getSeq(), files);
        
        map.put("result", result);
        return map;
    }

    // @RequestMapping(value="/modify",method = RequestMethod.POST)
    // public @ResponseBody Map<String, String> modify (Model model, @RequestParam(value = "files") MultipartFile files, Board board) throws Exception {
    //     Map<String, String> map = new HashMap<>();
    //     boardService.create(board);
    //     map.put("result", "sucess");
    //     return map;
    // }

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
    
    // @RequestMapping(value="/download",method = RequestMethod.POST)
    // public Map<String, String> upload (Model model, @RequestParam(value = "seq") int seq, 
    //                                                 @RequestParam(value = "files") MultipartFile files) throws IOException{
                                                        
    //     String result = boardService.upload(seq, files);  
    //     Map<String, String> map = new HashMap<>();       
    //     map.put("result", result);          

    // return map;        
    // }

    @RequestMapping(value="/download",method = RequestMethod.GET)
    @ResponseBody
    public void download (HttpServletRequest req, HttpServletResponse res , @RequestParam(value = "seq") int seq) throws IOException{
    
         String filePath = boardService.download(seq);
        String browser = boardService.getBrowser(req);
        File file = new File(filePath);
        Resource rs = new UrlResource(file.toURI());
        String disposition = boardService.getDisposition(rs.getFilename(), browser);

        ServletOutputStream sos = null;
        try(FileInputStream fis = new FileInputStream(file)) {
            res.setHeader("Content-Disposition", disposition);
            res.setContentType("application/octet-stream");
            res.setHeader("Content-Transfer-Encoding", "binary");

            sos = res.getOutputStream();

            byte[] b = new byte[1024];
            int data = 0;

            while ((data = (fis.read(b, 0, b.length))) != -1) {
                sos.write(b, 0, data);
            }
            sos.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if(sos != null) {
                try {
                    sos.close();
                }  catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }


        // String filePath = boardService.download(seq);

        // File file = new File(filePath);
        // Resource rs = null;

        // if(file.exists() && file.isFile()) {
        //     res.setContentType("application/octet-stream; charset=utf-8");
        //     String browser = boardService.getBrowser(req);

        //     rs = new UrlResource(file.toURI());

        //     String disposition = boardService.getDisposition(rs.getFilename(), browser);
        //     res.setHeader("Content-Disposition", disposition);
        //     res.setHeader("Content-Transfer-Encoding", "binary");
        //     OutputStream out = res.getOutputStream();
        //     FileInputStream fis = new FileInputStream(file);
        //     FileCopyUtils.copy(fis, out);

        //     if(fis != null) fis.close();
        //     out.flush();
        //     out.close();

        // }
    }
}
