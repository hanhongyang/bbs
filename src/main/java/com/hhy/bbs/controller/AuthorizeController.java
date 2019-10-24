package com.hhy.bbs.controller;

import com.hhy.bbs.dto.AccessTokenDTO;
import com.hhy.bbs.dto.GithubUser;
import com.hhy.bbs.mapper.UserMapper;
import com.hhy.bbs.model.User;
import com.hhy.bbs.provider.GithubProvider;
import com.hhy.bbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
@Autowired
private GithubProvider githubProvider;
@Autowired
private UserService userService;
@Value("${github.client.id}")
   private String clientId;
@Value("${github.client.secret}")
   private String clientSerect;
@Value("${github.redirect.uri}")
   private String redirectUri ;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSerect);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken=githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser=githubProvider.getUser(accessToken);
        if(githubUser != null && githubUser.getId() != null){
            User user=new User();
            String token=UUID.randomUUID().toString();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
            //登录成功
        }else{
            return "redirect:/";
            //登陆失败
        }

    }
}
