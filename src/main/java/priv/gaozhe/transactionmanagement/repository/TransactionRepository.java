package priv.gaozhe.transactionmanagement.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import priv.gaozhe.transactionmanagement.model.Transaction;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
public class TransactionRepository {
    // 改用更安全的并发有序集合
    private final Map<String, Transaction> idMap = new ConcurrentHashMap<>();
    private final NavigableSet<Transaction> timeSet = new ConcurrentSkipListSet<>( // 修改点1
            Comparator.comparing(Transaction::getTransactionTime, Comparator.nullsLast(Comparator.naturalOrder()))
                    .thenComparing(Transaction::getId, Comparator.nullsLast(Comparator.naturalOrder()))
    );
    // 替换为读写锁
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();

    public Transaction save(Transaction transaction) {
        writeLock.lock();
        try {
            if (idMap.containsKey(transaction.getId())) {
                throw new IllegalArgumentException("Duplicate ID: " + transaction.getId());
            }
            idMap.put(transaction.getId(), transaction); // 修正变量名错误
            timeSet.add(transaction);                    // 修正变量名错误
            return transaction;
        } finally {
            writeLock.unlock();
        }
    }

    public Optional<Transaction> findById(String id) {
        readLock.lock();
        try {
            return Optional.ofNullable(idMap.get(id));
        } finally {
            readLock.unlock();
        }
    }

    @Cacheable("transactions")
    public List<Transaction> findAll() {
        readLock.lock();
        try {
            return Collections.unmodifiableList(new ArrayList<>(idMap.values()));
        } finally {
            readLock.unlock();
        }
    }

    public void deleteById(String id) {
        writeLock.lock();
        try {
            Transaction transaction = idMap.remove(id);
            if (transaction != null) {
                timeSet.remove(transaction);
            }
        } finally {
            writeLock.unlock();
        }
    }

    public Transaction update(Transaction transaction) {
        writeLock.lock();
        try {
            if (idMap.containsKey(transaction.getId())) {
                // 先删除旧数据再插入更新后的数据
                timeSet.remove(idMap.get(transaction.getId()));
                idMap.put(transaction.getId(), transaction);
                timeSet.add(transaction);
                return transaction;
            } else {
                throw new IllegalArgumentException("ID not found: " + transaction.getId());
            }
        } finally {
            writeLock.unlock();
        }
    }


    public List<Transaction> listByTime(int page, int pageSize, boolean ascending) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must be >= 0");
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("Page size must be > 0");
        } ;

        readLock.lock();
        try {
            List<Transaction> result = new ArrayList<>();
            Iterator<Transaction> iterator = ascending ?
                    timeSet.iterator() : timeSet.descendingIterator();

            // 修复点4：修正缩进对齐
            int skip = page * pageSize;
            for (int i = 0; i < skip && iterator.hasNext(); i++) {
                iterator.next();
            }

            for (int i = 0; i < pageSize && iterator.hasNext(); i++) {
                result.add(iterator.next());
            }

            return Collections.unmodifiableList(result);
        } finally {
            readLock.unlock();
        }
    }

    // 获取总记录数（可选）
    public int getTotalCount() {
        return idMap.size();
    }
}