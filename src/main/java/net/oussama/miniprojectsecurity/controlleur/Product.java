package net.oussama.miniprojectsecurity.controlleur;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    @PostMapping("/test")
    @ResponseBody
    public String test() {
        logger.info("Test method called");
        return "HELLO";
    }

    @GetMapping("/gettoken")
    public String getToken(HttpServletRequest request){
        CsrfToken token  = (CsrfToken) request.getAttribute("_csrf");
        return token.getToken();
    }
    @PostMapping("/test3")
    //@CrossOrigin("http://localhost:8089")
    @ResponseBody
    public String test3() {
        System.out.println("test3");
        logger.info("Test method called");
        return "HELLO";
    }
}
