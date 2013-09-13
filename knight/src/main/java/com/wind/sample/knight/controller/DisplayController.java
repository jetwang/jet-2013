package com.wind.sample.knight.controller;

import com.wind.sample.domain.Knight;
import com.wind.sample.knight.dao.KnightDao;
import com.wind.sample.knight.manager.KnightManager;
import jetwang.framework.db.Page;
import jetwang.framework.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Jet
 */
@Controller
public class DisplayController {
    @Autowired
    private KnightDao knightRepository;
    @Autowired
    private KnightManager knightService;
    @Autowired
    private Log log;

    @RequestMapping
    public void list(@RequestParam(value = "keyword", required = false) String keyword, @ModelAttribute Page page, Model model) {
        List<Knight> knights = knightRepository.findKnights(keyword, page);
        model.addAttribute("knights", knights);
    }

    @RequestMapping
    public String delete(@RequestParam("number") long number) {
        knightRepository.deleteById(number);
        return ("redirect:display-list");
    }
}