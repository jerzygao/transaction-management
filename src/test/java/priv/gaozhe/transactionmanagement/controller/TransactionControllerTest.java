package priv.gaozhe.transactionmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import priv.gaozhe.transactionmanagement.model.Transaction;
import priv.gaozhe.transactionmanagement.service.TransactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    public void testCreateTransaction() throws Exception {
//        Transaction transaction = new Transaction("Deposit", 1000.0);
//        when(transactionService.createTransaction(transaction)).thenReturn(transaction);
//
//        mockMvc.perform(post("/api/transactions")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(transaction)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.type").value("Deposit"))
//                .andExpect(jsonPath("$.amount").value(1000.0));
//    }

//    @Test
//    public void testGetTransactionById() throws Exception {
//        String id = "123";
//        Transaction transaction = new Transaction("Deposit", 1000.0);
//        transaction.setId(id);
//        when(transactionService.getTransactionById(id)).thenReturn(Optional.of(transaction));
//
//        mockMvc.perform(get("/api/transactions/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.type").value("Deposit"))
//                .andExpect(jsonPath("$.amount").value(1000.0));
//    }
//
//    @Test
//    public void testGetAllTransactions() throws Exception {
//        List<Transaction> transactions = new ArrayList<>();
//        transactions.add(new Transaction("Deposit", 1000.0));
//        when(transactionService.getAllTransactions()).thenReturn(transactions);
//
//        mockMvc.perform(get("/api/transactions"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].type").value("Deposit"))
//                .andExpect(jsonPath("$[0].amount").value(1000.0));
//    }
//
//    @Test
//    public void testDeleteTransaction() throws Exception {
//        String id = "123";
//        mockMvc.perform(delete("/api/transactions/{id}", id))
//                .andExpect(status().isNoContent());
//        verify(transactionService, times(1)).deleteTransaction(id);
//    }
//
//    @Test
//    public void testUpdateTransaction() throws Exception {
//        String id = "123";
//        Transaction transaction = new Transaction("Deposit", 1000.0);
//        transaction.setId(id);
//        when(transactionService.updateTransaction(transaction)).thenReturn(transaction);
//
//        mockMvc.perform(put("/api/transactions/{id}", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(transaction)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.type").value("Deposit"))
//                .andExpect(jsonPath("$.amount").value(1000.0));
//    }
}