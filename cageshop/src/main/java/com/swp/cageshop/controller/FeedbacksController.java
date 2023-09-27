package com.swp.cageshop.controller;

import com.swp.cageshop.entity.Feedbacks;
import com.swp.cageshop.service.feedbacksService.IFeedbacksService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cageshop")
public class FeedbacksController {

  @Autowired
  private IFeedbacksService iFeedbacksService;

  @GetMapping("/feedback/list")
  public List<Feedbacks> listFeedBack(){
    return iFeedbacksService.listFeedbacks();
  }

  @PostMapping("/feedback/add/{id}")
  public Feedbacks addFeedback(@RequestBody Feedbacks feedbacks, @PathVariable Long id){
    return iFeedbacksService.addFeedBack(feedbacks, id);
  }
}
