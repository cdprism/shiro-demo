package org.shancm.shirodemo.webapp.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.shancm.shirodemo.domain.entity.User;
import org.shancm.shirodemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EntryController {

    private static final String LOGIN = "login";
    private static final String INDEX = "index";

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(String username, String password, Model model) {
        if (StringUtils.isBlank(username)) {
            return LOGIN;
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            subject.login(token);
            System.out.println(subject.getPrincipals());
            String name = (String) subject.getPrincipal();
            System.out.println(userService.getUserByName(name));
            subject.getSession().setAttribute("user", userService.getUserByName(name));
            return INDEX;
        } catch (UnknownAccountException e) {
            model.addAttribute("msg","用户名错误");
            return LOGIN;
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误");
            return LOGIN;
        }
    }
}
