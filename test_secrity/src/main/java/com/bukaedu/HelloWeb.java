package com.bukaedu;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName HelloWeb
 * @date 2022/8/17 11:02
 */
@RestController
@RequestMapping("/hello")
public class HelloWeb {

    @RequestMapping("/h1")
    @PreAuthorize("hasAuthority('add')")
    public String helli(){

        return "okok";
    }
}
