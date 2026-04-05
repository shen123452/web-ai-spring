# Tlias Web

一个基于 `Spring Boot 3 + MyBatis + MySQL + JWT` 的后端练习项目，当前已经覆盖登录鉴权、部门管理、员工管理、班级管理、学员管理、报表查询、操作日志和 OSS 文件上传等基础能力。

## 项目定位

这个项目适合作为：

- Spring Boot / MyBatis / MySQL 入门到进阶的练手项目
- JWT 登录鉴权、事务、分页查询、文件上传的综合示例
- 后续继续扩展 Redis、Docker、接口文档、参数校验的基础骨架

## 当前功能

- 登录认证
  - 用户名密码登录
  - JWT 鉴权拦截
  - 支持 `Authorization` 或 `token` 请求头
- 部门管理
  - 部门列表、详情、新增、修改、删除
- 员工管理
  - 员工分页查询
  - 员工详情、新增、修改、删除
  - 工作经历批量保存
- 班级管理
  - 班级分页、列表、详情、新增、修改、删除
- 学员管理
  - 学员分页、详情、新增、修改、删除
  - 违纪扣分
- 报表统计
  - 员工职位分布
  - 员工性别分布
  - 班级学员人数统计
  - 学员学历分布
- 文件上传
  - 上传文件到阿里云 OSS
- 日志与追踪
  - 请求级 `requestId`
  - 操作日志分页查询
  - 本地文件日志输出

## 技术栈

- Java 21
- Spring Boot 3.5.x
- MyBatis 3.0.x
- MySQL 8.x
- Maven 3.9+
- PageHelper
- JJWT
- Aliyun OSS SDK
- JUnit 5 + MockMvc

## 目录结构

```text
Tlias-web
├─ src/main/java/com/hhh
│  ├─ config         # WebMvc、JWT 配置
│  ├─ controller     # REST 接口层
│  ├─ exception      # 全局异常处理
│  ├─ filter         # 请求过滤器
│  ├─ interceptor    # JWT 拦截器
│  ├─ mapper         # MyBatis Mapper 接口
│  ├─ pojo           # 实体、DTO、VO、统一返回对象
│  ├─ service        # 业务接口与实现
│  └─ utils          # JWT、OSS、上下文等工具类
├─ src/main/resources
│  ├─ application.yml
│  ├─ logback.xml
│  └─ com/hhh/mapper # MyBatis XML
└─ src/test/java     # 基础控制器与工具类测试
```

## 运行环境

- JDK 21+
- Maven 3.9+
- MySQL 8+

## 快速启动

### 1. 准备数据库

创建数据库：

```sql
CREATE DATABASE tlias DEFAULT CHARACTER SET utf8mb4;
```

注意：

- 当前仓库**没有内置 SQL 初始化脚本**
- 你需要自行准备 `tlias` 库中的表结构和基础数据

### 2. 修改配置

编辑 [src/main/resources/application.yml](./src/main/resources/application.yml)：

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `jwt.secret-key`
- `aliyun.oss.endpoint`
- `aliyun.oss.bucket-name`
- `aliyun.oss.region`

当前默认端口：

```text
8080
```

### 3. 配置阿里云 OSS 凭证

`AliyunOSSOperator` 使用的是环境变量凭证提供者。  
如果你需要测试上传接口，请在系统环境变量中配置阿里云 OSS 的 AccessKey 信息。

### 4. 启动项目

在 [Tlias-web](.) 目录下执行：

```bash
mvn spring-boot:run
```

启动后访问：

```text
http://localhost:8080
```

## 认证说明

除登录接口和静态资源外，其余接口默认需要携带 JWT。

可使用以下任一种请求头：

```http
Authorization: Bearer <token>
```

或：

```http
token: <token>
```

登录接口：

```http
POST /login
Content-Type: application/json
```

请求体示例：

```json
{
  "username": "admin",
  "password": "123456"
}
```

## 主要接口

### 登录与上传

- `POST /login`
- `POST /upload`

### 部门管理

- `GET /depts`
- `GET /depts/{id}`
- `POST /depts`
- `PUT /depts`
- `PUT /depts/{id}`
- `DELETE /depts?id={id}`
- `DELETE /depts/{id}`

### 员工管理

- `GET /emps`
- `GET /emps/list`
- `GET /emps/{id}`
- `POST /emps`
- `PUT /emps`
- `PUT /emps/{id}`
- `DELETE /emps/{ids}`
- `DELETE /emps?id={id}`
- `DELETE /emps?ids={id1,id2,...}`

### 班级管理

- `GET /clazzs`
- `GET /clazzs/list`
- `GET /clazzs/{id}`
- `POST /clazzs`
- `PUT /clazzs`
- `DELETE /clazzs/{id}`

### 学员管理

- `GET /students`
- `GET /students/{id}`
- `POST /students`
- `PUT /students`
- `DELETE /students/{ids}`
- `PUT /students/violation/{id}/{score}`

### 报表接口

- `GET /report/empJobData`
- `GET /report/empGenderData`
- `GET /report/studentCountData`
- `GET /report/studentDegreeData`

### 操作日志

- `GET /log/page?page=1&pageSize=10`

## 测试

执行测试：

```bash
mvn test
```

当前已包含：

- 登录控制器测试
- 员工控制器测试
- JWT 工具类测试

## 日志

日志由 [src/main/resources/logback.xml](./src/main/resources/logback.xml) 管理，当前配置包括：

- 控制台日志
- 按日期和大小滚动的文件日志
- 基于 `requestId` 的请求链路标识

如果你在其他机器上运行，建议根据自己的环境调整日志输出目录。

## 已知说明

- 当前配置文件中的数据库账号、密码和 JWT 密钥属于开发环境写法，部署前建议改成环境变量或不同 profile 管理
- 当前仓库还没有接入 Redis、Docker Compose、OpenAPI/Swagger、参数校验注解等能力
- 如果要把这个项目作为简历项目，建议继续补充环境隔离、密码加密、参数校验、接口文档和部署方案

## 后续改造建议

- 接入 Redis，补缓存、登录态、验证码或排行榜场景
- 增加 `springdoc-openapi` 生成接口文档
- 增加 `docker-compose.yml`，统一启动 MySQL 和应用
- 增加 `application-dev.yml`、`application-prod.yml`
- 引入 `@Valid` / `@Validated` 做参数校验
- 对密码做加密存储，不再使用明文密码
