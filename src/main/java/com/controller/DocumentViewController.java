package com.controller;

import com.google.common.collect.Maps;
import com.pdf.PdfView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DocumentViewController {

    @GetMapping("/doc")
    public ModelAndView document(ModelAndView model) {
        return new ModelAndView(new PdfView(), Maps.newHashMap());
    }

}
