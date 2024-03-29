package com.epam.preprod.voitenko.strategy.captcha;

import com.epam.preprod.voitenko.repository.CaptchaRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InputHiddenCaptchaStorage implements CaptchaStrategy {
    private int id = -1;

    @Override
    public void setIdCaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        id = CaptchaRepository.addCaptcha();
    }

    @Override
    public int getIdCaptcha(HttpServletRequest httpServletRequest) {
        return id;
    }

    @Override
    public String getTagContext(HttpServletRequest httpServletRequest) {
        StringBuilder code = new StringBuilder();
        code.append("<input type=\"hidden\" id=\"idCaptcha\" name=\"idCaptcha\" value=\"" + id + "\">");
        code.append(CaptchaStrategy.super.getTagContext(httpServletRequest));
        return code.toString();
    }
}