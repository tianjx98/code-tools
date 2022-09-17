package cn.tianjx98.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class NotifyController {

    @GetMapping("/1MeAwgqqT4aBvuT10PjRJA")
    public void sendEmail() {
        System.out.println("123");
    }
}
