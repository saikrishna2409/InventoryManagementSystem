package com.cts.netflixzuulapigatewayserver;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	@RequestMapping("/user")
    public String loginPage(){
        return "Hi Devi";
    }

}
