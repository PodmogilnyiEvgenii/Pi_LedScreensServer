package com.home.LedScreensServer.view;

import com.home.LedScreensServer.model.ModelData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebView {
    private ModelData modelData;

    public WebView(ModelData modelData) {
        this.modelData = modelData;
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model view) {

        view.addAttribute("thresholdTemp", "model.getThresholdTemp()");
//        view.addAttribute("currentTemperature", modelData.getCurrentTemperature());
//        view.addAttribute("teplovisorOnline", modelData.isTeplovisorOnline());
//        view.addAttribute("indicatorOnline", modelData.isIndicatorOnline());
//        view.addAttribute("temperatureAlarm", modelData.isTemperatureAlarm());
//        view.addAttribute("colour", modelData.getStringColour());
//        view.addAttribute("blinking", modelData.getStringBlinking());
//        view.addAttribute("brightness", modelData.getBrightness());
//        view.addAttribute("thresholdTemp", modelData.getThresholdTemp());


        return "index";
    }
}
