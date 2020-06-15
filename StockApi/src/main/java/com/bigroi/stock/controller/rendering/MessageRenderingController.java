package com.bigroi.stock.controller.rendering;

import com.bigroi.stock.controller.BaseRenderingController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("Message")
public class MessageRenderingController extends BaseRenderingController {

    private static final String MESSAGE_PAGE = "message";

    @RequestMapping
    public ModelAndView getMessage(
            @RequestParam(MESSAGE_PAGE) String message,
            @RequestParam("type") String type
    ) {
        var modelAndView = createModelAndView(MESSAGE_PAGE);
        var tmp = message.split("\\.");
        modelAndView.addObject(MESSAGE_PAGE, labelService.getLabel(tmp[1], tmp[2], getLanguage()));
        modelAndView.addObject("title", labelService.getLabel(MESSAGE_PAGE, type, getLanguage()));
        return modelAndView;
    }

}
