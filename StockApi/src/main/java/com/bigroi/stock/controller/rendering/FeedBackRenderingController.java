package com.bigroi.stock.controller.rendering;

import com.bigroi.stock.controller.BaseRenderingController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("feedback")
public class FeedBackRenderingController extends BaseRenderingController {

    @RequestMapping("/Form")
    public ModelAndView getForm() {
        return createModelAndView("feedback");
    }

}
