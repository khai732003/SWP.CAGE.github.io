package com.swp.cageshop.service.feedbacksService;

import com.swp.cageshop.entity.Feedbacks;
import java.util.List;

public interface IFeedbacksService {

  public List<Feedbacks> listFeedbacks();
  public Feedbacks addFeedBack(Feedbacks feedbacks, Long UserId);

  public boolean deleteFeedBack(Long id);

  public Feedbacks updateFeedbacks(Long id, Feedbacks feedbacks);
}
