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
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void testCreateTransaction() {
        Transaction transaction = new Transaction("Deposit", 1000.0);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction createdTransaction = transactionService.createTransaction(transaction);

        assertNotNull(createdTransaction);
        assertEquals(transaction, createdTransaction);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    public void testGetTransactionById() {
        String id = "123";
        Transaction transaction = new Transaction("Deposit", 1000.0);
        transaction.setId(id);
        when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));

        Optional<Transaction> result = transactionService.getTransactionById(id);

        assertTrue(result.isPresent());
        assertEquals(transaction, result.get());
        verify(transactionRepository, times(1)).findById(id);
    }

    @Test
    public void testGetAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("Deposit", 1000.0));
        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> result = transactionService.getAllTransactions();

        assertNotNull(result);
        assertEquals(transactions.size(), result.size());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteTransaction() {
        String id = "123";
        transactionService.deleteTransaction(id);
        verify(transactionRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateTransaction() {
        Transaction transaction = new Transaction("Deposit", 1000.0);
        when(transactionRepository.update(transaction)).thenReturn(transaction);

        Transaction updatedTransaction = transactionService.updateTransaction(transaction);

        assertNotNull(updatedTransaction);
        assertEquals(transaction, updatedTransaction);
        verify(transactionRepository, times(1)).update(transaction);
    }
}