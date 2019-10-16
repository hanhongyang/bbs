package com.hhy.bbs.controller;

import com.hhy.bbs.dto.AccessTokenDTO;
import com.hhy.bbs.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
@Autowired
private GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDTO.setState(state);
        githubProvider.getAccessToken(accessTokenDTO);

        return "index";
    }
}
