package com.mobilepark.airtalk.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mobilepark.airtalk.data.Board;
import com.mobilepark.airtalk.service.BoardService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

    // @RequestMapping(value="/create.json",method = RequestMethod.POST)
    // public @ResponseBody Map<String, Object> create (Model model, MultipartHttpServletRequest req, Board board) {
    //     Map<String, Object> map = boardService.create(req, board);
    //     return map;

    // }

    // @RequestMapping(value="/update.json",method = RequestMethod.POST)
    // public @ResponseBody Map<String, Object> update (Model model, @RequestBody JSONObject form) {
    //     Map<String, Object> map = boardService.update(form);
    //     return map;
    // }

    // @RequestMapping(value="/delete.json",method = RequestMethod.POST)
    // public @ResponseBody Map<String, Object> delete (Model model, @RequestBody JSONObject form) {
    //     Map<String, Object> map = boardService.delete(form);
    //     return map;
    // }

}
