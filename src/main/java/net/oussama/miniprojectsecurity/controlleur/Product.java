package net.oussama.miniprojectsecurity.controlleur;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/product")
public class Product {
    private Logger logger = LoggerFactory.getLogger(Product.class);
    @PostMapping("/add")
    public ModelAndView addProduct(@RequestParam String name){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main.html");
        logger.info("add product"+name);
        return modelAndView;
    }
}
