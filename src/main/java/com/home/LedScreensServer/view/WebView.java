package com.home.LedScreensServer.view;

import com.home.LedScreensServer.model.ModelData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class WebView {
    private ModelData modelData;


    public WebView(ModelData modelData) {
        this.modelData = modelData;

    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model view) {
        //System.out.println(modelData.getReceivedCardList().get(0).getLedScreen().screenToBase64String());
        view.addAttribute("screen1", modelData.getLedScreen().screenToBase64String());
        view.addAttribute("screen2", modelData.getLedScreen().bigscreenToBase64String());
        return "index.html";
    }
}
//