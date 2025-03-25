package priv.gaozhe.transactionmanagement.config;

import cn.hutool.core.util.RandomUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import priv.gaozhe.transactionmanagement.model.Transaction;
import priv.gaozhe.transactionmanagement.model.TransactionType;
import priv.gaozhe.transactionmanagement.repository.TransactionRepository;

import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static priv.gaozhe.transactionmanagement.model.TransactionType.*;

@Component
public class DataInitializer implements CommandLineRunner {
    private final TransactionRepository repository;
    private final Random random = new Random();
    private static final List<TransactionType> TYPES = Arrays.asList(TransactionType.values());

    public DataInitializer(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        // 仅当数据库为空时初始化
        if (repository.getTotalCount() == 0) {
            for (int i = 0; i < 100; i++) {
                repository.save(createRandomTransaction());
            }
        }
    }

    private Transaction createRandomTransaction() {
        TransactionType type = TYPES.get(random.nextInt(TYPES.size()));
        double amount = RandomUtil.randomDouble(1000, 2, RoundingMode.HALF_UP);
        String sourceAccount = "MyAccount";
        String targetAccount = RandomUtil.randomNumbers(20);
        Transaction initData = new Transaction();
        if (WITHDRAWAL == type || PAYMENT == type) {
            amount = 0 - amount;
        }
        if (DEPOSIT == type) {
            sourceAccount = "";
            targetAccount = "MyAccount";
        }
        if (WITHDRAWAL == type) {
            targetAccount = "";
        }

        return new Transaction(type, amount, type.getName(), sourceAccount, targetAccount);
    }
}