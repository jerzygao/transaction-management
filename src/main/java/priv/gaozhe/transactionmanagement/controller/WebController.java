package priv.gaozhe.transactionmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    
    @GetMapping("/transactions")
    public String transactionPage() {
        return "transaction-ui";
    }
}