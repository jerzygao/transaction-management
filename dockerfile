# 使用官方的OpenJDK 21基础镜像
FROM openjdk:21-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制项目的JAR文件到容器中
COPY target/transaction-management-1.0-SNAPSHOT.jar app.jar

# 暴露应用程序运行的端口
EXPOSE 8080

# 定义容器启动时执行的命令
CMD ["java", "-jar", "app.jar"]