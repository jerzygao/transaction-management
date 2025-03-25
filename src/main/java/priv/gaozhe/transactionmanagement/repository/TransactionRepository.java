package priv.gaozhe.transactionmanagement.repository;

import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Repository;
import priv.gaozhe.transactionmanagement.model.Transaction;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 交易数据存储实现类
 * 特性：
 * 1. 基于内存的并发安全存储
 * 2. 双重存储结构（ID哈希表+时间有序集合）
 * 3. 读写锁控制并发访问
 * 4. Spring Cache缓存集成
 */
@Repository
@CacheConfig(cacheNames = "transactions")
public class TransactionRepository {
    private final Map<String, Transaction> idMap = new ConcurrentHashMap<>();
    private final NavigableSet<Transaction> timeSet = new ConcurrentSkipListSet<>( 
            Comparator.comparing(Transaction::getTransactionTime, Comparator.nullsLast(Comparator.naturalOrder()))
                    .thenComparing(Transaction::getId, Comparator.nullsLast(Comparator.naturalOrder()))
    );
     // 使用读写锁保证并发安全（写操作独占，读操作共享）
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();

     /**
     * 保存交易记录
     * @param transaction 交易对象（必须包含唯一ID）
     * @return 已保存的交易对象
     * @throws IllegalArgumentException 当ID重复时抛出
     */
    @Caching(
        put = @CachePut(key = "#transaction.id"),
        evict = @CacheEvict(value = "transactionLists", allEntries = true)
    )
    public Transaction save(Transaction transaction) {
        writeLock.lock();
        try {
            if (idMap.containsKey(transaction.getId())) {
                throw new IllegalArgumentException("Duplicate ID: " + transaction.getId());
            }
            idMap.put(transaction.getId(), transaction);
            timeSet.add(transaction);
            return transaction;
        } finally {
            writeLock.unlock();
        }
    }

    @Cacheable(key = "#id")
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

    @CacheEvict(value = {"transactions", "transactionLists"}, allEntries = true)
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

    @Caching(
        put = @CachePut(key = "#transaction.id"),
        evict = @CacheEvict(value = "transactionLists", allEntries = true)
    )
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

 /**
     * 分页查询交易记录
     * @param page 页码（从0开始）
     * @param pageSize 每页记录数
     * @param ascending 是否按时间升序排列
     * @return 不可修改的交易列表
     * @throws IllegalArgumentException 当分页参数非法时抛出
     */
    @Cacheable(value = "transactionLists", keyGenerator = "pageKeyGenerator")
    public List<Transaction> listByTime(int page, int pageSize, boolean ascending) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must be >= 0");
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("Page size must be > 0");
        } 

        readLock.lock();
        try {
            List<Transaction> result = new ArrayList<>();
            Iterator<Transaction> iterator = ascending ?
                    timeSet.iterator() : timeSet.descendingIterator();

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

    public int getTotalCount() {
        return idMap.size();
    }
}