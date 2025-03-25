package priv.gaozhe.transactionmanagement.exception;

import java.io.Serial;

/**
 * 自定义业务异常
 * 用于处理业务逻辑错误（如数据重复、非法参数等）
 */
public class BusinessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -6711179955280043215L;
    private final String errorCode;

    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}