package com.mobilepark.airtalk.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobilepark.airtalk.data.Board;
import com.mobilepark.airtalk.data.FileData;
import com.mobilepark.airtalk.service.BoardService;
import com.mobilepark.airtalk.service.CommentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/restapi/board")
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
    
    @Autowired
    public BoardService boardService;

    @Autowired
    public CommentService commentService;

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
    }

    @RequestMapping(value="/comment/search", method=RequestMethod.POST)
    public @ResponseBody Map<String, Object> commentSearch(@RequestBody JSONObject form) {

        logger.info("seq : " + form.get("seq"));
        Map<String, Object> map = commentService.list(Integer.parseInt(form.get("seq").toString()));
        return map;
    }

    @RequestMapping(value="/comment/create", method=RequestMethod.POST)
    public @ResponseBody Map<String, Object> commentCreate(@RequestBody JSONObject form) {

        logger.info("seq : ["+form.get("seq")+"] adminId : ["+form.get("adminId")+"] comment : ["+form.get("comment")+"]");
        Map<String, Object> map = commentService.create(form);
        return map;
    }
    
}
