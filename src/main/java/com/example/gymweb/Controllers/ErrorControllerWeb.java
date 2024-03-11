package com.example.gymweb.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorControllerWeb implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        int statusCode = (int) request.getAttribute("javax.servlet.error.status_code");
        switch (statusCode) {
            case 400:
                mav.setViewName("error400");
                break;
            case 401:
                mav.setViewName("error401");
                break;
            case 404:
                mav.setViewName("error404");
                break;
            case 503:
                mav.setViewName("error503");
                break;
            default:
                mav.setViewName("error");
        }
        return mav;
    }
}
