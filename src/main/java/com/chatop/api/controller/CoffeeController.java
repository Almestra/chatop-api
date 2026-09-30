package com.chatop.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.exception.TeapotException;

/**
 * Coffee endpoint of the API.
 */
@RestController
@RequestMapping("/api/coffee")
public class CoffeeController {

    /**
     * Brews a coffee.
     *
     * @throws TeapotException always, since this server is a teapot (RFC 2324)
     */
    @PostMapping
    public void brewCoffee() {
        throw new TeapotException("I'm a teapot");
    }

}
