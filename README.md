# 银行系统交易管理应用程序

## 简介
这是一个简易的银行系统交易管理应用程序，允许用户记录、查看和管理财务交易。

## 技术栈
- Java 21
- Spring Boot
- 内存存储（无需持久化）

## 非JDK标准库说明
- spring-boot-starter-web ：用于构建RESTful API。
- spring-boot-starter-cache ：用于应用缓存机制。
- spring-boot-starter-validation ：用于参数验证。
- spring-boot-starter-test ：用于单元测试。
- mockito-core ：用于模拟对象。

## API文档
- 创建交易 ： POST /api/transactions
- 获取交易 ： GET /api/transactions/{id}
- 获取所有交易 ： GET /api/transactions
- 删除交易 ： DELETE /api/transactions/{id}
- 更新交易 ： PUT /api/transactions/{id}

## 运行项目
1. 克隆项目到本地：
```bash
git clone https://github.com/your-repo/transaction-management.git
cd transaction-management
```
2. 使用Maven构建项目：
```bash
mvn clean package
 ```

3. 运行项目：
```bash
java -jar target/transaction-management-1.0-SNAPSHOT.jar
 ```


## 单元测试
运行以下命令执行单元测试：

```bash
mvn test
 ```

## 压力测试
使用Gatling进行压力测试，确保API性能稳定。

## 容器化部署
使用Dockerfile构建Docker镜像并运行容器：

```bash
docker build -t transaction-management .
docker run -p 8080:8080 transaction-management
 ```