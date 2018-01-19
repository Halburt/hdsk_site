package com.houdask.site.common.web;

import org.springframework.web.bind.annotation.RestController;


//@RestController
public class ErrorController {

    public String error(){
        return "{'msg':'error','code':100}";
    }
}
