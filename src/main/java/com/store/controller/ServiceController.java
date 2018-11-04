package com.store.controller;

import com.store.service.ProductStore;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ServiceController {

    @Autowired
    private ProductStore productStore;

    private static Gson gson = new Gson();

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String healthCheck(){
        return "Application is running";
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String searchPrefix(@RequestParam("keyword") String keyword){
        return  gson.toJson(productStore.search(keyword));
    }

}
