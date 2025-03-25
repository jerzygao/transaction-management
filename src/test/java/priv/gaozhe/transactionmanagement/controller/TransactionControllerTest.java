package priv.gaozhe.transactionmanagement.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import priv.gaozhe.transactionmanagement.model.Transaction;
import priv.gaozhe.transactionmanagement.repository.TransactionRepository;
import priv.gaozhe.transactionmanagement.service.TransactionService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static priv.gaozhe.transactionmanagement.model.TransactionType.DEPOSIT;
import static priv.gaozhe.transactionmanagement.model.TransactionType.TRANSFER;

@WebMvcTest(TransactionController.class)
@Import({TransactionService.class, TransactionRepository.class})
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    void getTransactionsPage() throws Exception {
        List<Transaction> mockList = Arrays.asList(
            new Transaction(TRANSFER,  100.0, "Test1", "123", "456"),
            new Transaction(DEPOSIT,  200.0, "Test2", null, "789")
        );

        given(transactionService.listByTime(0,10,false)).willReturn(mockList);
        given(transactionService.getTotalCount()).willReturn(mockList.size());

        mockMvc.perform(get("/api/transactions/page")
                .param("page", "0")
                .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.total").value(2))
            .andExpect(jsonPath("$.data[0].type").value("TRANSFER"));
    }

    // 新增根据ID查询测试
    @Test
    void testGetTransactionById() throws Exception {
        Transaction transaction =   new Transaction(TRANSFER,  100.0, "Test1", "123", "456");
        transaction.setId("mock-id");
        given(transactionService.findById("mock-id")).willReturn(Optional.of(transaction));

        mockMvc.perform(get("/api/transactions/mock-id"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("mock-id"));
    }

    // 增强创建测试的验证
    @Test
    void createTransaction() throws Exception {
        String jsonContent = "{\"type\":\"TRANSFER\",\"amount\":500.0,\"description\":\"Test\",\"sourceAccount\":\"123\",\"targetAccount\":\"456\"}";
        Transaction transaction =   new Transaction(TRANSFER,  500, "Test", "123", "456");
        given(transactionService.save(any(Transaction.class))).willReturn(transaction);
        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.type").value("TRANSFER"));

    }

    // 新增异常情况测试
    @Test
    void testGetTransactionNotFound() throws Exception {
        given(transactionService.findById("not-exist-id")).willReturn(Optional.empty());

        mockMvc.perform(get("/api/transactions/not-exist-id"))
            .andExpect(status().isNotFound());
    }

    // 修复更新测试的路径参数不匹配问题
    @Test
    void updateTransaction() throws Exception {
        String jsonContent = "{\"id\":\"mock-id\",\"type\":\"TRANSFER\",\"amount\":500.0,\"description\":\"Test\",\"sourceAccount\":\"123\",\"targetAccount\":\"456\"}";
        Transaction transaction =   new Transaction(TRANSFER,  500, "Test", "123", "456");
        transaction.setId("mock-id");
        given(transactionService.update(any(Transaction.class))).willReturn(transaction);
        // 保持路径ID与请求体ID一致
        mockMvc.perform(put("/api/transactions/mock-id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
            .andExpect(status().isOk());
    }
}