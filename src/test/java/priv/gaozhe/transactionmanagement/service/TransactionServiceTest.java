package priv.gaozhe.transactionmanagement.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import priv.gaozhe.transactionmanagement.model.Transaction;
import priv.gaozhe.transactionmanagement.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static priv.gaozhe.transactionmanagement.model.TransactionType.DEPOSIT;
import static priv.gaozhe.transactionmanagement.model.TransactionType.TRANSFER;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;


    // 新增测试方法
    @Test
    void saveShouldReturnSavedTransaction() {
        Transaction newTrans = new Transaction(TRANSFER, 100.0, "Test", "123", "456");
        when(transactionRepository.save(any())).thenReturn(newTrans);

        Transaction result = transactionService.save(newTrans);
        
        assertNotNull(result);
        verify(transactionRepository).save(newTrans);
    }

    @Test
    void findByIdWhenExistsReturnTransaction() {
        Transaction mockTrans = new Transaction(TRANSFER, 500.0, "Test", "123", "456");
        mockTrans.setId("valid-id");
        when(transactionRepository.findById("valid-id")).thenReturn(Optional.of(mockTrans));

        Optional<Transaction> result = transactionService.findById("valid-id");
        
        assertTrue(result.isPresent());
        assertEquals(500.0, result.get().getAmount());
    }

    @Test
    void findByIdWhenNotExistsReturnEmpty() {
        when(transactionRepository.findById("invalid-id")).thenReturn(Optional.empty());
        
        Optional<Transaction> result = transactionService.findById("invalid-id");
        
        assertTrue(result.isEmpty());
    }

    @Test
    void listByTimeShouldReturnPagedResults() {
        List<Transaction> mockList = List.of(
            new Transaction(DEPOSIT, 200.0, "Test2", null, "789"),
            new Transaction(TRANSFER, 100.0, "Test1", "123", "456")
        );
        when(transactionRepository.listByTime(0,10,true)).thenReturn(mockList);

        List<Transaction> result = transactionService.listByTime(0, 10, true);
        
        assertEquals(2, result.size());
    }

    @Test
    void updateShouldModifyExistingRecord() {
        Transaction updated = new Transaction(DEPOSIT, 200.0, "New", null, "789");
        updated.setId("update-id");
        when(transactionRepository.update(any())).thenReturn(updated);

        Transaction result = transactionService.update(updated);
        
        assertEquals(DEPOSIT, result.getType());
        verify(transactionRepository).update(updated);
    }

    @Test
    void deleteShouldRemoveRecord() {
        doNothing().when(transactionRepository).deleteById("delete-id");
        
        transactionService.deleteById("delete-id");
        
        verify(transactionRepository).deleteById("delete-id");
    }
}