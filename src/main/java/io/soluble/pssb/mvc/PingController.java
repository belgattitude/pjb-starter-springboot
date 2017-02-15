package io.soluble.pssb.mvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import java.util.HashMap;


@Controller
public class PingController {
    private Log LOG = LogFactory.getLog(PingController.class);

    @RequestMapping(value = "/ping.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody
    String pingAction() {

        Gson gson = new Gson();
        HashMap map = new HashMap<String,String>();
        map.put("success", true);
        map.put("message", "PHPJavaBridge running");
        map.put("date", System.currentTimeMillis());

        return gson.toJson(map);
    }

}