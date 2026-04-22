package net.oussama.miniprojectsecurity.controlleur;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfProtection {
    @PostMapping("/test1")
    public String test1() {
        return "test1";
    }

    @PostMapping("/test2")
    public String test2() {
        return "test2";
    }
}
