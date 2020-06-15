package com.bigroi.stock.controller;

import com.bigroi.stock.service.LabelService;
import com.bigroi.stock.util.LabelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;


public abstract class BaseRenderingController extends BaseController {

    private static final boolean DEV_ENV = true;

    @Autowired
    protected LabelService labelService;

    protected final ModelAndView createModelAndView(String pageName) {
        Object user = SecurityContextHolder.getContext() != null
                && SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getPrincipal()
                : null;
        return new ModelAndView(pageName)
                .addObject("label", new LabelMap())
                .addObject("user", user)
                .addObject("build_number", "")
                .addObject("languages", LabelUtil.getPassibleLanguages(getLanguage()))
                .addObject("dev_env", DEV_ENV)
                .addObject("page_title", labelService.getLabel("pageNames", pageName, getLanguage()));
    }

    private class LabelMap extends HashMap<String, Object> {

        private static final long serialVersionUID = -4893325882571654741L;

        private String category;

        public LabelMap() {
        }

        public LabelMap(String category) {
            this.category = category;
        }

        @Override
        public Object get(Object key) {
            if (category == null) {
                return new LabelMap(key.toString());
            } else {
                return labelService.getLabel(category, key.toString(), getLanguage());
            }
        }

        @Override
        public boolean equals(Object arg0) {
            return super.equals(arg0);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }

}
