package priv.gaozhe.transactionmanagement.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import priv.gaozhe.transactionmanagement.model.Transaction;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransactionRepository {
    private final Map<String, Transaction> transactions = new ConcurrentHashMap<>();

    public Transaction save(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
        return transaction;
    }

    public Optional<Transaction> findById(String id) {
        return Optional.ofNullable(transactions.get(id));
    }

    @Cacheable("transactions")
    public List<Transaction> findAll() {
        return new ArrayList<>(transactions.values());
    }

    public void deleteById(String id) {
        transactions.remove(id);
    }

    public Transaction update(Transaction transaction) {
        if (transactions.containsKey(transaction.getId())) {
            transactions.put(transaction.getId(), transaction);
            return transaction;
        }
        return null;
    }
}