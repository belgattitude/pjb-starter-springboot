package io.soluble.pssb.mvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class IndexController {
    private Log LOG = LogFactory.getLog(IndexController.class);



    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {

        model.addAttribute("menu_title", "PHPJavaBridge");
        model.addAttribute("headline", "Bridge Running !");
        return "index";
    }



}