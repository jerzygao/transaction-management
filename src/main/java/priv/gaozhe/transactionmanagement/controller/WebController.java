package priv.gaozhe.transactionmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 交易记录管理页面控制器
 */
@Controller
public class WebController {
    
    @GetMapping("/transactions")
    public String transactionPage() {
        return "transaction-ui";
    }
}