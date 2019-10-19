package com.hhy.bbs.controller;

import com.hhy.bbs.dto.AccessTokenDTO;
import com.hhy.bbs.dto.GithubUser;
import com.hhy.bbs.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {
@Autowired
private GithubProvider githubProvider;
@Value("${github.client.id}")
   private String clientId;
@Value("${github.client.secret}")
   private String clientSerect;
@Value("${github.redirect.uri}")
   private String redirectUri ;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSerect);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken=githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user=githubProvider.getUser(accessToken);
        if(user!=null){
            request.getSession().setAttribute("user",user);
            return "redirect:/";
            //登录成功
        }else{
            return "redirect:/";
            //登陆失败
        }

    }
}
