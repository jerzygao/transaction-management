package priv.gaozhe.transactionmanagement.service;

import org.springframework.stereotype.Service;
import priv.gaozhe.transactionmanagement.model.Transaction;
import priv.gaozhe.transactionmanagement.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> getTransactionById(String id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public void deleteTransaction(String id) {
        transactionRepository.deleteById(id);
    }

    public Transaction updateTransaction(Transaction transaction) {
        return transactionRepository.update(transaction);
    }

    public List<Transaction> listByTime(int page, int pageSize, boolean ascending) {
        return transactionRepository.listByTime(page, pageSize, ascending);
    }

    public int getTotalCount() {
        return transactionRepository.getTotalCount();
    }
}