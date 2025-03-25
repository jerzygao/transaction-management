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

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> findById(String id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public void deleteById(String id) {
        transactionRepository.deleteById(id);
    }

    public Transaction update(Transaction transaction) {
        return transactionRepository.update(transaction);
    }

    public List<Transaction> listByTime(int page, int pageSize, boolean ascending) {
        return transactionRepository.listByTime(page, pageSize, ascending);
    }

    public int getTotalCount() {
        return transactionRepository.getTotalCount();
    }
}