package priv.gaozhe.transactionmanagement.service;

import cn.hutool.core.util.StrUtil;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import priv.gaozhe.transactionmanagement.exception.BusinessException;
import priv.gaozhe.transactionmanagement.model.Transaction;
import priv.gaozhe.transactionmanagement.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

import static priv.gaozhe.transactionmanagement.model.TransactionType.*;

/**
 * 交易记录服务
 */
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction save(@NotNull Transaction transaction) {
        this.validateParam(transaction);
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> findById(@NotNull String id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public void deleteById(@NotNull String id) {
        transactionRepository.deleteById(id);
    }

    public Transaction update(@NotNull Transaction transaction) {
        this.validateParam(transaction);
        return transactionRepository.update(transaction);
    }

    public List<Transaction> listByTime(int page, int pageSize, boolean ascending) {
        return transactionRepository.listByTime(page, pageSize, ascending);
    }

    public int getTotalCount() {
        return transactionRepository.getTotalCount();
    }

    private void validateParam(Transaction transaction) {
        if ((TRANSFER == transaction.getType() || PAYMENT == transaction.getType())
                && (StrUtil.isBlank(transaction.getSourceAccount()) || StrUtil.isBlank(transaction.getTargetAccount()))) {
            throw new BusinessException("1001", "收付款账户信息不能为空");
        }
        if (DEPOSIT == transaction.getType() && StrUtil.isBlank(transaction.getTargetAccount())) {
            throw new BusinessException("1001", "存入账户信息不能为空");
        }
        if (WITHDRAWAL == transaction.getType() && StrUtil.isBlank(transaction.getSourceAccount())) {
            throw new BusinessException("1001", "取出账户信息不能为空");
        }
    }
}