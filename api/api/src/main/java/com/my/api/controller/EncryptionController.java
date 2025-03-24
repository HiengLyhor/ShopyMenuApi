package com.my.api.controller;

import com.my.api.config.Encryption;
import com.my.api.dto.encryption.EncryptionRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/enc/")
public class EncryptionController {

    @PostMapping("encrypt")
    String encryptString(@RequestBody EncryptionRequest data) throws Exception {
        if (data.getAdminRight().equals("TRUEADMIN")) {
            return Encryption.encrypt(data.getKey());
        }
        return "Invalid Admin Right";
    }

    @PostMapping("decrypt")
    String decryptString(@RequestBody EncryptionRequest data) throws Exception {
        if (data.getAdminRight().equals("TRUEADMIN")) {
            return Encryption.decrypt(data.getKey());
        }
        return "Invalid Admin Right";
    }

}
