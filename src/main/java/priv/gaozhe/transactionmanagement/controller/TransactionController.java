package priv.gaozhe.transactionmanagement.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import priv.gaozhe.transactionmanagement.model.PageResult;
import priv.gaozhe.transactionmanagement.model.Transaction;
import priv.gaozhe.transactionmanagement.service.TransactionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        return transaction.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable String id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable String id, @Valid @RequestBody Transaction transaction) {
        transaction.setId(id);
        Transaction updatedTransaction = transactionService.updateTransaction(transaction);
        if (updatedTransaction != null) {
            return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 新增分页查询接口
    @GetMapping("/page")
    public ResponseEntity<PageResult<Transaction>> getTransactionsByPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        
        List<Transaction> data = transactionService.listByTime(page, size,true);
        int total = transactionService.getTotalCount();
        return new ResponseEntity<>(new PageResult<>(data, total), HttpStatus.OK);
    }

}