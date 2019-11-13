//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package edu.udacity.java.nano;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

@SpringBootApplication
@RestController
public class WebSocketChatApplication {
    public WebSocketChatApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(WebSocketChatApplication.class, args);
    }

    @GetMapping({"/"})
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping({"/index"})
    public ModelAndView index(String username, HttpServletRequest request) throws UnknownHostException {
        if (username == null || username.isEmpty()) {
            username = "Anonymous";
        }

        ModelAndView mv = new ModelAndView("chat");
        mv.addObject("username", username);
        mv.addObject("webSocketUrl", "ws://" + InetAddress.getLocalHost().getHostAddress() + ":" + request.getServerPort() + request.getContextPath() + "/chat");
        return mv;
    }
}
