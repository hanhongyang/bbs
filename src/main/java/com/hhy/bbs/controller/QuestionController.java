package com.hhy.bbs.controller;

import com.hhy.bbs.dto.QuestionDTO;
import com.hhy.bbs.mapper.QuestionMapper;
import com.hhy.bbs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

   // @Autowired
   // private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id, Model model) {
//        Long questionId = null;
//        try {
//            questionId = Long.parseLong(id);
//        } catch (NumberFormatException e) {
//            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
//        }
       QuestionDTO questionDTO = questionService.getById(id);
//        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
//        List<CommentDTO> comments = commentService.listByTargetId(questionId, CommentTypeEnum.QUESTION);
        //累加阅读数
        questionService.incView(id);

        model.addAttribute("question", questionDTO);
//        model.addAttribute("comments", comments);
//        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }
}
