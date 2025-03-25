package priv.gaozhe.transactionmanagement.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import priv.gaozhe.transactionmanagement.model.Transaction;
import priv.gaozhe.transactionmanagement.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

//    @Test
//    void deleteTransactionSuccess() {
//        // 准备
//        Long existingId = 1L;
//        given(transactionRepository.existsById(existingId)).willReturn(true);
//
//        // 执行
//        transactionService.deleteTransaction(existingId);
//
//        // 验证
//        verify(transactionRepository).deleteById(existingId);
//    }
//
//    @Test
//    void deleteTransactionNotFound() {
//        Long nonExistingId = 999L;
//        given(transactionRepository.existsById(nonExistingId)).willReturn(false);
//
//        assertThrows(EntityNotFoundException.class, () -> {
//            transactionService.deleteTransaction(nonExistingId);
//        });
//    }
}