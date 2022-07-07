package com.ic.taskmanager.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/check")
    public boolean checkAuth() {
        return true;
    }
}
