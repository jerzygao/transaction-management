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

/**
 * rest api controller 提供交易相关增删改查api
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * 创建交易
     *
     * @param transaction 交易记录对像
     * @return
     */
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.save(transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    /**
     * 根据交易记录id查询
     *
     * @param id 交易记录id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String id) {
        Optional<Transaction> transaction = transactionService.findById(id);
        return transaction.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 获取全部交易记录
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAll();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * 根据交易记录id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable String id) {
        transactionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 根据交易记录id全量更新数据
     *
     * @param id
     * @param transaction
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable String id, @Valid @RequestBody Transaction transaction) {
        transaction.setId(id);
        Transaction updatedTransaction = transactionService.update(transaction);
        if (updatedTransaction != null) {
            return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * 分页查询交易记录，按照交易时间倒序
     *
     * @param page 页码 从0开始
     * @param size 每页数据条数
     * @return
     */
    // 分页查询接口
    @GetMapping("/page")
    public ResponseEntity<PageResult<Transaction>> getTransactionsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<Transaction> data = transactionService.listByTime(page, size, false);
        int total = transactionService.getTotalCount();
        return new ResponseEntity<>(new PageResult<>(data, total), HttpStatus.OK);
    }

}