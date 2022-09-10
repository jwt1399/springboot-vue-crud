
<h2 align="center" >基于 SpringBoot-Vue 的 CRUD 整合 Demo</h2>

<h5 align="center">旨在帮您入门 SpringBoot 整合</h5>

## 0.项目简介

### ①.相关技术

- Bean开发——使用`Lombok`快速制作实体类
- Dao开发——整合`MyBatis-Plus`，制作数据层测试
- Service开发——基于`MyBatis-Plus`进行增量开发，制作业务层测试类
- Controller开发——基于`Restful`开发，使用`PostMan`测试接口功能
- 页面开发——基于`VUE+ElementUI`制作，前后端联调，页面数据处理，页面消息处理

### ②.项目展示

#### 主页面

![](https://img.jwt1399.top/img/202209080000584.png)

#### 增/改

![](https://img.jwt1399.top/img/202209080001120.png)

#### 删除

![](https://img.jwt1399.top/img/202209080001905.png)

#### 查询

![](https://img.jwt1399.top/img/202209080001485.png)

## 1.项目创建

创建新项目，加载要使用的技术对应的starter，修改配置文件格式为yml格式，并把web访问端口先设置成80。

**pom.xml**

```XML
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-test</artifactId>
  <scope>test</scope>
</dependency>

```

**application.yml**

```yaml
server:
  port: 80
```

## 2.实体层开发

**数据表**

```sql
-- ----------------------------
-- Table structure for tbl_book
-- ----------------------------
DROP TABLE IF EXISTS `tbl_book`;
CREATE TABLE `tbl_book`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_book
-- ----------------------------
INSERT INTO `tbl_book` VALUES (1, '计算机理论', 'Spring实战 第5版', 'Spring入门经典教程，深入理解Spring原理技术内幕');
INSERT INTO `tbl_book` VALUES (2, '计算机理论', 'Spring 5核心原理与30个类手写实战', '十年沉淀之作，手写Spring精华思想');
INSERT INTO `tbl_book` VALUES (3, '计算机理论', 'Spring 5 设计模式', '深入Spring源码剖析Spring源码中蕴含的10大设计模式');
INSERT INTO `tbl_book` VALUES (4, '计算机理论', 'Spring MVC+MyBatis开发从入门到项目实战', '全方位解析面向Web应用的轻量级框架，带你成为Spring MVC开发高手');
INSERT INTO `tbl_book` VALUES (5, '计算机理论', '轻量级Java Web企业应用实战', '源码级剖析Spring框架，适合已掌握Java基础的读者');
INSERT INTO `tbl_book` VALUES (6, '计算机理论', 'Java核心技术 卷I 基础知识（原书第11版）', 'Core Java 第11版，Jolt大奖获奖作品，针对Java SE9、10、11全面更新');
INSERT INTO `tbl_book` VALUES (7, '计算机理论', '深入理解Java虚拟机', '5个维度全面剖析JVM，大厂面试知识点全覆盖');
INSERT INTO `tbl_book` VALUES (8, '计算机理论', 'Java编程思想（第4版）', 'Java学习必读经典,殿堂级著作！赢得了全球程序员的广泛赞誉');
INSERT INTO `tbl_book` VALUES (9, '计算机理论', '零基础学Java（全彩版）', '零基础自学编程的入门图书，由浅入深，详解Java语言的编程思想和核心技术');
INSERT INTO `tbl_book` VALUES (10, '市场营销', '直播就该这么做：主播高效沟通实战指南', '李子柒、李佳琦、薇娅成长为网红的秘密都在书中');
INSERT INTO `tbl_book` VALUES (11, '市场营销', '直播销讲实战一本通', '和秋叶一起学系列网络营销书籍');
INSERT INTO `tbl_book` VALUES (12, '市场营销', '直播带货：淘宝、天猫直播从新手到高手', '一本教你如何玩转直播的书，10堂课轻松实现带货月入3W+');
```

**实体类**

```java
public class Book {
    private Integer id;
    private String type;
    private String name;
    private String description;
}
```

实体类的开发可以手工生成get/set方法，然后覆盖toString()方法。不过这一套操作书写很繁琐，可以使用 `Lombok`简化JavaBean开发。

引入 Lombok，用注解代替构造器、getter/setter、toString()等代码，新版 IDEA 已集成 Lombok 插件，无需下载

```xml
<dependency>
  <groupId>org.projectlombok</groupId>
  <artifactId>lombok</artifactId>
</dependency>
```

- @Data ： 注在类上，提供类的get、set、equals、hashCode、toString等方法

- @AllArgsConstructor ：注在类上，提供类的全参构造

- @NoArgsConstructor ：注在类上，提供类的无参构造

- @Setter ：注在属性上，提供 set 方法

- @Getter ：注在属性上，提供 get 方法

- @EqualsAndHashCode ：注在类上，提供对应的 equals 和 hashCode 方法

- @Log4j/@Slf4j ：注在类上，提供对应的 Logger 对象，变量名为 log

```java
//简化JavaBean开发
@NoArgsConstructor //无参构造
@AllArgsConstructor //有参构造
@Data
public class Book {
    private Integer id;
    private String type;
    private String name;
    private String description;
}
```

Lombok 还可以简化日志开发，例如下面代码

```java
@Slf4j
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String handle(@RequestParam("name") String name){
        log.info("请求进来了....");
        return "Hello, Spring Boot 2!"+"你好："+name;
    }
}
```

## 3.数据层开发

### ①基础CRUD

**步骤①**：导入MyBatisPlus与Druid对应的starter，当然mysql的驱动不能少

```xml
<dependency>
  <groupId>com.baomidou</groupId>
  <artifactId>mybatis-plus-boot-starter</artifactId>
  <version>3.4.3</version>
</dependency>
<dependency>
  <groupId>com.alibaba</groupId>
  <artifactId>druid-spring-boot-starter</artifactId>
  <version>1.2.6</version>
</dependency>
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <scope>runtime</scope>
</dependency>
```

**步骤②**：配置数据库连接相关的数据源配置

```yml
spring:
  datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/ssm_db?serverTimezone=Asia/Shanghai
      username: root
      password: root
```

**步骤③**：配置MP相关配置

- 表名通用前缀
- 主键生成策略
    - MP技术默认的主键生成策略为雪花算法，生成的主键ID长度较大，和目前的数据库设定规则不相符，需要配置一下使MP使用数据库的主键生成策略
- 配置MP日志
    - 设置日志输出方式为标准输出即可查阅SQL执行日志

```yml
mybatis-plus:
  global-config:
    db-config:
      table-prefix: tbl_		#设置表名通用前缀
      id-type: auto				#设置主键id字段的生成策略为参照数据库设定的策略，当前数据库设置id生成策略为自增
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl  #配置标准输出日志
```

**步骤④**：使用MP的标准通用接口BaseMapper加速开发，别忘了@Mapper和泛型的指定

```JAVA
@Mapper
public interface BookDao extends BaseMapper<Book> {
}
```

**步骤⑤**：制作测试类测试结果

```JAVA
package com.jianjian.dao;

@SpringBootTest
public class BookDaoTests {

    @Autowired
    private BookDao bookDao;

    @Test
    void testGetById(){
        System.out.println(bookDao.selectById(1));
    }

    @Test
    void testSave(){
        Book book = new Book();
        book.setType("测试数据123");
        book.setName("测试数据123");
        book.setDescription("测试数据123");
        bookDao.insert(book);
    }

    @Test
    void testUpdate(){
        Book book = new Book();
        book.setId(17);
        book.setType("测试数据abcdefg");
        book.setName("测试数据123");
        book.setDescription("测试数据123");
        bookDao.updateById(book);
    }

    @Test
    void testDelete(){
        bookDao.deleteById(16);
    }

    @Test
    void testGetAll(){
        bookDao.selectList(null);
    }
}
```

**总结**

1. 手工导入starter坐标（2个），mysql驱动（1个）
2. 配置数据源与MyBatisPlus对应的配置
3. 配置MP相关配置
4. 开发Dao接口（继承BaseMapper）
5. 制作测试类测试Dao功能是否有效

### ②分页功能

MyBatis-Plus自带分页插件，只要需要**定义MP拦截器并将其设置为Spring管控的bean**即可实现分页功能

```JAVA
@Configuration
public class MPConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        //创建MP的拦截器栈
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //初始化了分页拦截器，并添加到拦截器栈中
        //如果后期开发其他功能，需要添加全新的拦截器，按照此行的格式继续add进去新的拦截器就可以了。
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
```

MP提供的分页操作API如下

```JAVA
@Test
void testGetPage(){
    IPage page = new Page(2,5);
    bookDao.selectPage(page, null);
    System.out.println(page.getCurrent());		  //当前页码值
    System.out.println(page.getSize());			   //每页显示数
    System.out.println(page.getTotal());		  //数据总量
    System.out.println(page.getPages());		 //总页数
    System.out.println(page.getRecords());	//详细数据
}
```

其中`selectPage`方法需要传入一个封装分页数据的`IPage`对象，可以通过new的形式创建这个对象。创建此对象时就需要指定分页的两个基本数据

- 当前显示第几页
- 每页显示几条数据

可以通过创建Page对象时利用构造方法初始化这两个数据

```JAVA
IPage page = new Page(2,5);
```

将该对象传入到查询方法selectPage后，可以得到查询结果，但是我们会发现当前操作查询结果返回值仍然是一个IPage对象，这又是怎么回事？

```JAVA
IPage page = bookDao.selectPage(page, null);
```

原来这个IPage对象中封装了若干个数据，而查询的结果作为IPage对象封装的一个数据存在的，可以理解为查询结果得到后，又塞到了这个IPage对象中，其实还是为了高度的封装。

**总结**

1. 分页操作依赖MyBatisPlus分页拦截器实现功能
2. 使用IPage封装分页数据

### ③条件查询

以往我们写条件查询要自己动态拼写复杂的SQL语句，现在简单了，MP将这些操作都制作成API接口，调用一个又一个的方法就可以实现各种套件的拼装。

下面的操作就是执行一个模糊匹配对应的操作，由like条件书写变为了like方法的调用

```JAVA
@Test
void testGetBy(){
    //封装查询条件的对象
    QueryWrapper<Book> qw = new QueryWrapper<>();
    //查询条件
    qw.like("name","Spring");
    bookDao.selectList(qw);
}
```

由于属性字段名的书写存在着安全隐患，比如查询字段name，当前是以字符串的形态书写的，万一写错，编译器还没有办法发现，只能将问题抛到运行器通过异常堆栈告诉开发者，不太友好。

MP针对字段检查进行了功能升级，全面支持Lambda表达式。由QueryWrapper对象升级为LambdaQueryWrapper对象，这下就解决了上述问题的出现

```JAVA
@Test
void testGetBy2(){
    String name = "1";
    LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<Book>();
    lqw.like(Book::getName,"Spring");
    bookDao.selectList(lqw);
}
```

为了便于开发者动态拼写SQL，防止将null数据作为条件使用，MP还提供了动态拼装SQL的快捷书写方式

```JAVA
    @Test
    void testGetBy3(){
        String name = "Spring";
        LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<Book>();
        //if(name != null) lqw.like(Book::getName,name);		//方式一：JAVA代码控制
        lqw.like(name != null,Book::getName,name);				 //方式二：API接口提供控制开关
        bookDao.selectList(lqw);
    }
```

**总结**

1. 使用QueryWrapper对象封装查询条件

2. **推荐**使用LambdaQueryWrapper对象

3. 所有查询操作封装成方法调用

4. 查询条件支持动态条件拼装

## 4.业务层开发

### ①简介

初学者认为业务层就是调用数据层，更精准的说法应该是<font color="#ff0000"><b>组织业务逻辑功能，并根据业务需求，对数据持久层发起调用</b></font>。

一个常识性的知识普及一下，业务层的方法名定义一定要与业务有关，例如登录操作

```JAVA
login(String username,String password);
```

而数据层的方法名定义一定与业务无关，是一定，不是可能，也不是有可能，例如根据用户名密码查询

```JAVA
selectByUserNameAndPassword(String username,String password);
```

我们在开发的时候是可以根据完成的工作不同划分成不同职能的开发团队的。

比如开发数据层的团队，拿到的需求文档要求可能是这样的

```
接口：传入用户名与密码字段，查询出对应结果，结果是单条数据
接口：传入ID字段，查询出对应结果，结果是单条数据
接口：传入离职字段，查询出对应结果，结果是多条数据
```

但是开发业务层功能团队，拿到的需求文档要求差别就很大

```
接口：传入用户名与密码字段，对用户名字段做长度校验，4-15位，对密码字段做长度校验，8到24位，对喵喵喵字段做特殊字符校验，不允许存在空格，查询结果为对象。如果为null，返回BusinessException，封装消息码INFO_LOGON_USERNAME_PASSWORD_ERROR
```

所以说业务层方法定义与数据层方法定义差异化很大

### ②普通开发

**步骤①**：业务层接口定义如下：

```JAVA
public interface BookService {
    Boolean save(Book book);
    Boolean update(Book book);
    Boolean delete(Integer id);
    Book getById(Integer id);
    List<Book> getAll();
    IPage<Book> getPage(int currentPage,int pageSize);
}
```

**步骤②**：业务层实现类如下，转调数据层即可

```JAVA
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public Boolean save(Book book) {
        return bookDao.insert(book) > 0;
    }

    @Override
    public Boolean update(Book book) {
        return bookDao.updateById(book) > 0;
    }

    @Override
    public Boolean delete(Integer id) {
        return bookDao.deleteById(id) > 0;
    }

    @Override
    public Book getById(Integer id) {
        return bookDao.selectById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.selectList(null);
    }

    @Override
    public IPage<Book> getPage(int currentPage, int pageSize) {
        IPage page = new Page(currentPage,pageSize);
        return bookDao.selectPage(page,null);
    }
}
```

​	**步骤③**：对业务层接口进行测试

```JAVA
@SpringBootTest
public class BookServiceTests {
    @Autowired
    private BookService bookService;

    @Test
    void testGetById(){
        System.out.println(bookService.getById(4));
    }
    @Test
    void testSave(){
        Book book = new Book();
        book.setType("测试数据123");
        book.setName("测试数据123");
        book.setDescription("测试数据123");
        bookService.save(book);
    }
    @Test
    void testUpdate(){
        Book book = new Book();
        book.setId(17);
        book.setType("-----------------");
        book.setName("测试数据123");
        book.setDescription("测试数据123");
        bookService.update(book);
    }
    @Test
    void testDelete(){
        bookService.delete(18);
    }
    @Test
    void testGetAll(){
        bookService.getAll();
    }
    @Test
    void testGetPage(){
        IPage<Book> page = bookService.getPage(2, 5);
        System.out.println(page.getCurrent());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.getPages());
        System.out.println(page.getRecords());
    }
}
```

**总结**

1. Service接口名称定义成业务名称，并与Dao接口名称进行区分
2. 制作测试类测试Service功能是否有效

### ③快速开发

MP不仅提供了数据层快速开发方案，MP也给了业务层一个通用接口，**不推荐使用**，其实就是一个封装+继承的思想，实际开发慎用

**步骤①**：业务层接口快速开发

```JAVA
public interface IBookService extends IService<Book> {
    //添加非通用操作API接口
}
```

**步骤②**：业务层接口实现类快速开发，继承的类需要传入两个泛型，一个是数据层接口，另一个是实体类

```JAVA
@Service
public class BookServiceImpl extends ServiceImpl<BookDao, Book> implements IBookService {
    @Autowired
    private BookDao bookDao;
	//添加非通用操作API
}
```

如果感觉MP提供的功能不足以支撑你的使用需要，可以在原始接口基础上接着定义新的API接口就行了

**总结**

1. 使用通用接口（ISerivce\<T>）快速开发Service
2. 使用通用实现类（ServiceImpl<M,T>）快速开发ServiceImpl
3. 可以在通用接口基础上做功能重载或功能追加
4. 注意重载时不要覆盖原始操作，避免原始提供的功能丢失

## 5.控制层开发

### ①普通Restful开发

**步骤①**：控制层接口如下，基于Restful开发

```JAVA
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAll(){
        return bookService.list();
    }

    @PostMapping
    public Boolean save(@RequestBody Book book){
        return bookService.save(book);
    }

    @PutMapping
    public Boolean update(@RequestBody Book book){
        return bookService.modify(book);
    }

    @DeleteMapping("{id}")
    public Boolean delete(@PathVariable Integer id){
        return bookService.delete(id);
    }

    @GetMapping("{id}")
    public Book getById(@PathVariable Integer id){
        return bookService.getById(id);
    }

    @GetMapping("{currentPage}/{pageSize}")
    public IPage<Book> getPage(@PathVariable int currentPage,@PathVariable int pageSize){
        return bookService.getPage(currentPage,pageSize, null);
    }
}
```

**步骤②**：Postman测试，关注提交类型，对应上即可，不然就会报405的错误码了

- **普通GET请求**

![](https://img.jwt1399.top/img/202209100004375.png)

- **GET请求传递路径变量，后台使用@PathVariable接收数据**

![](https://img.jwt1399.top/img/202209100004293.png)

- **PUT请求传递json数据，后台使用@RequestBody接收数据**

![](https://img.jwt1399.top/img/202209100014797.png)

**总结**

1. 基于Restful制作表现层接口
    - 新增：@PostMapping
    - 删除：@DeleteMapping
    - 修改：@PutMapping
    - 查询：@GetMapping
2. 接收参数
    - 实体数据：@RequestBody
    - 路径变量：@PathVariable

### ②消息一致性开发

> 为何要返回统一格式。为方便使用已封装好，导入项目即可使用：https://jwt1399.lanzouv.com/iINu80bfh1sd

现在大多数web项目基本都是前后端分离模式，这种模式会涉及到一个前后端对接问题，所以一套完善且规范的接口是非常有必要的，不仅能够提高对接效率，也可以让我的代码看起来更加简洁优雅。本节将解决如何返回统一的标准格式以及处理全局异常。

如果SpringBoot不使用统一返回格式，默认会有如下三种返回情况。

- 返回字符串

```java
@GetMapping("/getUserName")
public String getUserName(){
    return "Hello";
}

// 返回结果：Hello
```

- 返回实体类对象

```java
@GetMapping("/getUserName")
public User getUserName(){
    return new User("Hello",18);
}

// 返回结果：
{
  "name": "Hello",
  "age": "18"
}
```

- 返回异常

```java
@GetMapping("/getUserName")
public static String getUserName(){
    HashMap hashMap = new HashMap();
    return hashMap.get(0).toString(); // 模拟一个空指针异常
}

// 返回结果：
{
    "timestamp": "2022-09-09T12:56:06.549+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "path": "/getUserName"
}
```

对于上面这几种情况，如果整个项目没有定义统一的返回格式，不同开发人员可能会定义不同的返回格式，这样会使前后端对接出现一些问题。

#### a. 定义返回标准

一个标准的返回格式至少包含3部分：

```
code： 状态码
message： 接口调用的提示信息  
data： 返回数据
```

**步骤①**：定义数据返回格式

```java
@Data
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;

    /**
     * 成功
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(ResultMsgEnum.SUCCESS.getCode());
        result.setMessage(ResultMsgEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 失败
     */
    public static <T> Result<T> fail(ResultMsgEnum resultMsgEnum) {
        Result<T> result = new Result<T>();
        result.setCode(resultMsgEnum.getCode());
        result.setMessage(resultMsgEnum.getMessage());
        return result;
    }
}
```

**步骤②**：定义状态码

```java
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResultMsgEnum {
    SUCCESS(0, "成功"),
    FAIL(-1, "失败"),
    TEST_ERROR(400, "发生错误啦!");
    private int code;
    private String message;
}
```

**步骤③**：使用

上面定义了数据返回格式和状态码，下面在接口中使用。

```java
//示例1
@GetMapping("/getUserName")
public Result getUserName(){
    return Result.success("Hello");
}

//示例2
@GetMapping("/getUserName")
public static Result getUserName(){
  HashMap hashMap = new HashMap();
  return Result.success(hashMap.get(0).toString()); // 模拟一个空指针异常
}
```

返回结果如下

```powershell
# 示例1结果
{
    "code": 0,
    "message": "成功",
    "data": "Hello"
}

# 示例2结果
{
    "timestamp": "2022-09-09T14:45:21.875+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "path": "/getUserName"
}
```

通过在Controller层调用Result.success()对返回结果进行包装后返回给前端，虽然能够满足日常需求，但是当有大量的接口时，每一个接口中都使用Result.success()来包装返回信息就会增加很多重复代码，而且遇到异常数据格式无法统一。

#### c .统一接口返回

前面步骤不够优雅，可以继续改进，用 **@RestControllerAdvice** 注解，拦截下后端返回的数据，实现 **ResponseBodyAdvice** 接口对数据做一层包装再返回给前端。

> `ResponseBodyAdvice`： 该接口是SpringMVC 4.1提供的，它允许在 执行@ResponseBody后自定义返回数据，用来封装统一数据格式返回；拦截Controller方法的返回值，统一处理返回值/响应体，一般用来统一返回格式，加解密，签名等
>
> `@RestControllerAdvice`： 该注解是Controller的增强版，可以全局捕获抛出的异常，全局数据绑定，全局数据预处理。

**步骤①**：新建ResponseAdvice类，该类用于统一封装controller中接口的返回结果。实现ResponseBodyAdvice接口，实现supports、beforeBodyWrite方法。

```java
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 是否开启功能 true:开启
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 处理返回结果
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //处理字符串类型数据
        if(o instanceof String){
            try {
                return objectMapper.writeValueAsString(Result.success(o));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        //返回类型是否已经封装
        if(o instanceof Result){
            return o;
        }
        return Result.success(o);
    }
}

```

**步骤②**：测试，无需转换格式，直接使用

```java
@GetMapping("/getUserName")
public static String getUserName(){
  HashMap hashMap = new HashMap();
  return hashMap.get(0).toString(); // 模拟一个空指针异常
}
```

返回结果如下，可以看到返回结果与在Result中定义的参数类型相同。

```powershell
{
    "code": 0,
    "message": "成功",
    "data": {
        "timestamp": "2022-09-09T14:37:19.902+00:00",
        "status": 500,
        "error": "Internal Server Error",
        "path": "/getUserName"
    }
}
```

#### d.全局异常处理

> 遇到异常时，第一时间想到的应该是try…catch，不过这种方式会导致大量代码重复，维护困难等问题，这里不用手写try…catch，由全局异常处理器统一捕获；对于自定义异常，只能通过全局异常处理器来处理，使用全局异常处理器最大的便利就是程序员在写代码时不再需要手写 try…catch了。

**步骤①**：首先新增一个类，增加`@RestControllerAdvice`注解，如果我们有想要拦截的异常类型，就新增一个方法，使用`@ExceptionHandler`注解修饰，注解参数为目标异常类型。

例如：controller中接口发生Exception异常时，就会进入到Execption方法中进行捕获，将杂乱的异常信息，转换成指定格式后交给ResponseAdvice方法进行统一格式封装并返回给前端。

```java
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public Result Execption(Exception e) {
        log.error("未知异常！", e);
        //e.printStackTrace();
        return Result.fail(ResultMsgEnum.TEST_ERROR);
    }
    //还可定义其它拦截异常方法
    //。。。。。。
}
```

**步骤②**：测试，再次调用接口getUserName查看返回结果

```java
@GetMapping("/getUserName")
public static String getUserName(){
    HashMap hashMap = new HashMap();
    return hashMap.get(0).toString(); // 模拟一个空指针异常
}
```

返回结果

```powershell
{
    "code": 400,
    "message": "发生错误啦!",
    "data": null
}
```

#### e.快速使用

> 前面实现了消息一致性，为了方便使用作如下封装，只需引用到项目中即可

**步骤①**：定义数据返回格式

```java
@Data
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;

    /**
     * 成功
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(ResultMsgEnum.SUCCESS.getCode());
        result.setMessage(ResultMsgEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 失败
     */
    public static <T> Result<T> fail(ResultMsgEnum resultMsgEnum) {
        Result<T> result = new Result<T>();
        result.setCode(resultMsgEnum.getCode());
        result.setMessage(resultMsgEnum.getMessage());
        return result;
    }
}
```

**步骤②**：定义状态码

```java
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResultMsgEnum {
    SUCCESS(0, "成功"),
    FAIL(-1, "失败"),
    TEST_ERROR(400, "发生错误啦!");
    private int code;
    private String message;
}
```

**步骤③：**统一接口

```java
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 是否开启功能 true:开启
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 处理返回结果
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //处理字符串类型数据
        if(o instanceof String){
            try {
                return objectMapper.writeValueAsString(Result.success(o));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        //返回类型是否已经封装
        if(o instanceof Result){
            return o;
        }
        return Result.success(o);
    }
}
```

**步骤④**：全局异常

```java
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public Result Execption(Exception e) {
        log.error("未知异常！", e);
        //e.printStackTrace();
        return Result.fail(ResultMsgEnum.TEST_ERROR);
    }
    //还可定义其它异常方法
    //。。。。。。
}
```

**总结**

1. 设计统一的返回值结果类型便于前端开发读取数据

2. 返回值结果类型可以根据需求自行设定，没有固定格式

3. 返回值结果模型类用于后端与前端进行数据格式统一，也称为前后端数据协议


**参考**

- [SpringBoot统一接口返回和全局异常处理](https://blog.csdn.net/qq_47183158/article/details/123440041)

- [统一接口返回和全局异常处理 ](https://juejin.cn/post/7032609987083894814#heading-6)

## 6.页面功能开发

### ⓪前端页面

前端页面下载：https://jwt1399.lanzouv.com/ibQNG0bet3yh

将页面保存到 resources/static目录中，建议执行maven的clean生命周期，避免缓存的问题出现。

如果成功访问：http://127.0.0.1/pages/books.html，即表明接入成功

在进行具体的功能开发之前，先做前后端联通性的测试，通过页面发送异步提交（axios）

```js
//列表
getAll() {
	axios.get("/books").then((res)=>{
		console.log(res.data);
	});
},
```

```js
//钩子函数，VUE对象初始化完成后自动执行
created() {
  this.getAll();
},
```

只要后台代码能够正常工作，前端能够在日志中接收到数据，就证明前后端是通的，即可进行下一步的功能开发了

**总结**

1. 单体项目中页面放置在resources/static目录下
2. created钩子函数用于初始化页面时发起调用
3. 页面使用axios发送异步请求获取数据后确认前后端是否联通

### ①列表功能

列表功能主要操作就是加载完数据，将数据展示到页面上，此处要利用VUE的数据模型绑定，发送请求得到数据，然后页面上读取指定数据即可

**a.页面数据模型定义**

```js
data:{
	dataList: [],//当前页要展示的列表数据
	...
},
```

**b.异步请求获取数据**

```JS
//列表
getAll() {
    axios.get("/books").then((res)=>{
        this.dataList = res.data.data;
    });
},
```

**c.钩子函数调用请求**

```js
//钩子函数，VUE对象初始化完成后自动执行
created() {
  this.getAll();
},
```

这样在页面加载时就可以获取到数据，并且由VUE将数据展示到页面上了

### ②添加功能

添加功能首先要进行数据收集窗口弹窗的展示，添加后隐藏弹窗即可。因为这个弹窗一直存在，因此当页面加载时首先设置这个弹窗为不可显示状态，需要展示时切换状态即可

**a.表单窗口默认状态**

```js
data:{
	dialogFormVisible: false,//添加表单是否可见
	...
},
```

**b.切换为显示状态**

```JS
//弹出添加窗口
handleCreate() {
	this.dialogFormVisible = true;
},
```

**c.定义清理数据操作**

由于每次添加数据都是使用同一个弹窗录入数据，所以每次操作的痕迹将在下一次操作时展示出来，需要在每次操作之前清理掉上次操作的痕迹

```js
//重置表单
resetForm() {
    this.formData = {};
},
```

**d.切换弹窗状态时清理数据**

```js
//弹出添加窗口
handleCreate() {
    this.dialogFormVisible = true;
    this.resetForm();
},
```

**e.添加操作**

1. 将要保存的数据传递到后台，通过post请求的第二个参数传递json数据到后台
2. 根据返回的操作结果决定下一步操作
    - 如果是状态码是成功就关闭添加窗口，显示添加成功的消息
    - 反之保留添加窗口，显示添加失败的消息
3. 无论添加是否成功，页面均进行刷新，动态加载数据（对getAll操作发起调用）

```js
//添加
handleAdd () {
    //发送异步请求
    axios.post("/books",this.formData).then((res)=>{
        //如果操作成功，关闭弹层，显示数据
        if(res.data.code == 0){
            this.dialogFormVisible = false;
            this.$message.success("添加成功");
        }else {
          	this.$message.error("添加失败");
        }
    }).finally(()=>{
        this.getAll();//重载数据，避免添加后数据不显示
    });
},
```

**f.取消添加操作**

```JS
//取消
cancel(){
    this.dialogFormVisible = false;
    this.$message.info("操作取消");
},
```

**总结**

1. 请求方式使用POST调用后台对应操作
2. 添加操作结束后动态刷新页面加载数据
3. 根据操作结果不同，显示对应的提示信息
4. 弹出添加表单时清除上次表单数据

### ③删除功能

模仿添加操作制作删除功能，差别之处在于删除操作仅传递一个待删除的数据id到后台即可

**a.删除操作**

```JS
// 删除
handleDelete(row) {
    axios.delete("/books/"+row.id).then((res)=>{
        if(res.data.flag){
            this.$message.success("删除成功");
        }else{
            this.$message.error("删除失败");
        }
    }).finally(()=>{
        this.getAll();
    });
},
```

**b.删除操作提示信息**

```JS
// 删除
handleDelete(row) {
    //1.弹出提示框
    this.$confirm("此操作永久删除当前数据，是否继续？","提示",{type:'info'}).then(()=>{
        //2.删除业务
        axios.delete("/books/"+row.id).then((res)=>{
          if(res.data.code == 0){
            	this.$message.success("删除成功");
          }else {
            	this.$message.error("删除失败");
          }
        }).finally(()=>{
             this.getAll();
        });
    }).catch(()=>{
        //3.取消删除
        this.$message.info("取消删除操作");
    });
}，	
```

**总结**

1. 请求方式使用Delete调用后台对应操作
2. 删除操作需要传递当前行数据对应的id值到后台
3. 删除操作结束后动态刷新页面加载数据
4. 根据操作结果不同，显示对应的提示信息
5. 删除操作前弹出提示框避免误操作

### ④修改功能

修改功能可以说是列表功能、删除功能与添加功能的合体。几个相似点如下：

1. 页面也需要有一个弹窗用来加载修改的数据，这一点与添加相同，都是要弹窗

2. 弹出窗口中要加载待修改的数据，而数据需要通过查询得到，这一点与查询相同，都是要查数据

3. 查询操作需要将要修改的数据id发送到后台，这一点与删除相同，都是传递id到后台

4. 查询得到数据后需要展示到弹窗中，这一点与查询相同，都是要通过数据模型绑定展示数据

5. 修改数据时需要将被修改的数据传递到后台，这一点与添加相同，都是要传递数据


**a.查询并展示数据**

```JS
//弹出编辑窗口
handleUpdate(row) {
    axios.get("/books/"+row.id).then((res)=>{
      if(res.data.code == 0){
          //展示弹层，加载数据
          this.formData = res.data.data;
          this.dialogFormVisibleEdit = true;
      }else {
        	this.$message.error("数据同步失败");
      }
    });
},
```

**b.修改操作**

```JS
//修改
handleEdit() {
    axios.put("/books",this.formData).then((res)=>{
        //如果操作成功，关闭弹层并刷新页面
      if(res.data.code == 0){
          this.dialogFormVisibleEdit = false;
          this.$message.success("修改成功");
      }else {
        	this.$message.error("修改失败");
      }
    }).finally(()=>{
       	 this.getAll();
    });
},
```

**c.取消修改操作**

```JS
//取消
cancel(){
    this.dialogFormVisible = false;
  	this.dialogFormVisibleEdit = false;
 	  this.$message.info("操作取消");
},
```

**总结**

1. 加载要修改数据通过传递当前行数据对应的id值到后台查询数据
2. 利用前端双向数据绑定将查询到的数据进行回显
3. 请求方式使用PUT调用后台对应操作
4. 修改操作结束后动态刷新页面加载数据
5. 根据操作结果不同，显示对应的提示信息

### ⑤分页功能

分页功能的制作用于替换前面的查询全部，

**a.ElementUI分页组件**

```html
<!--分页组件-->
<div class="pagination-container">
    <el-pagination
     class="pagiantion"
     @current-change="handleCurrentChange"
     @size-change="handleSizeChange"
     :current-page="pagination.currentPage"
     :page-size="pagination.pageSize"
     layout="total,sizes, prev, pager, next, jumper"
     :page-sizes="[5, 10, 15, 20]"
     :total="pagination.total">
    </el-pagination>
</div>
```

**b.配合分页组件，封装分页对应的数据模型**

```js
data:{
  //分页相关模型数据
	pagination: {	
		currentPage: 1,	//当前页码
		pageSize:10,	 //每页显示的记录数
		total:0,		  //总记录数
	}
},
```

**c.修改查询全部功能为分页查询，通过路径变量传递页码信息参数**

```JS
getAll() {
    axios.get("/books/"+this.pagination.currentPage+"/"+this.pagination.pageSize)
        .then((res)=>{
            console.log(res.data.data);
    	});
},

```

**d.页面根据分页操作结果读取对应数据，并进行数据模型绑定**

```JS
getAll() {
    axios.get("/books/"+this.pagination.currentPage+"/"+this.pagination.pageSize)
      	.then((res)=>{
            //console.log(res.data.data);
      			this.dataList = res.data.data.records;
            this.pagination.currentPage = res.data.data.current;
            this.pagination.pageSize = res.data.data.size;
            this.pagination.total = res.data.data.total;      
    	})
},
```

**f.对切换页码操作设置调用当前分页操作**

```JS
//切换页码
handleCurrentChange(currentPage) {
    this.pagination.currentPage = currentPage;
    this.getAll();
},
```

**g.对切换每页显示条数操作设置调用当前记录数操作**

```js
//切换每页条数
handleSizeChange(pageSize){
    this.pagination.pageSize = pageSize;
    this.getAll();
}
```

**总结**

1. 使用el分页组件
2. 定义分页组件绑定的数据模型
3. 异步调用获取分页数据
4. 分页数据页面回显

### ⑥删除功能维护

由于使用了分页功能，当最后一页只有一条数据时，删除操作就会出现BUG，最后一页无数据但是独立展示，对分页查询功能进行后台功能维护，如果当前页码值大于最大页码值，重新执行查询。其实这个问题解决方案很多，这里给出比较简单的一种处理方案

```JAVA
@GetMapping("{currentPage}/{pageSize}")
public IPage<Book> getPage(@PathVariable int currentPage, @PathVariable int pageSize){
    IPage<Book> page = bookService.getPage(currentPage, pageSize);
    //如果当前页码值大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
    if( currentPage > page.getPages()){
      	page = bookService.getPage((int)page.getPages(), pageSize);
    }
    return page;
}
```

删除一条数据之后，主键id不连续的问题解决

```sql
#删除id列
alter table tbl_book drop id; 
#新增id列,设为主键,并自增
ALTER TABLE tbl_book ADD id INT NOT NULL PRIMARY KEY AUTO_INCREMENT FIRST; 
```

### ⑦条件查询

条件查询可以理解为分页查询的时候除了携带分页数据再多带几个数据的查询。这些多带的数据就是查询条件。

页面发送请求时，两个分页数据仍然使用路径变量，其他条件采用动态拼装url参数的形式传递

**a.页面封装查询条件字段**

```js
pagination: {		
//分页相关模型数据
	currentPage: 1,		//当前页码
	pageSize:10,		//每页显示的记录数
	total:0,			//总记录数
	name: "",
	type: "",
	description: ""
},
```

**b.页面添加查询条件字段对应的数据模型绑定名称**

```HTML
<div class="filter-container">
    <el-input placeholder="图书类别" v-model="pagination.type" class="filter-item"/>
    <el-input placeholder="图书名称" v-model="pagination.name" class="filter-item"/>
    <el-input placeholder="图书描述" v-model="pagination.description" class="filter-item"/>
    <el-button @click="getAll()" class="dalfBut">查询</el-button>
    <el-button type="primary" class="butT" @click="handleCreate()">新建</el-button>
</div>
```

**c.将查询条件组织成url参数，添加到请求url地址中**

```JS
getAll() {
  //1.获取查询条件,拼接查询条件
  param = "?name="+this.pagination.name;
  param += "&type="+this.pagination.type;
  param += "&description="+this.pagination.description;
  //console.log(param);
  axios.get("/books/"+this.pagination.currentPage+"/"+this.pagination.pageSize+param)
    .then((res)=>{
    //console.log(res.data.data);
    this.pagination.currentPage = res.data.data.current;
    this.pagination.pageSize = res.data.data.size;
    this.pagination.total = res.data.data.total;
    this.dataList = res.data.data.records;
  })
},
```

**d.后台代码中定义实体类封查询条件**

```JAVA
@GetMapping("{currentPage}/{pageSize}")
public IPage<Book> getPage(@PathVariable int currentPage, @PathVariable int pageSize,Book book){
    System.out.println("参数=====>"+book);
    IPage<Book> page = bookService.getPage(currentPage, pageSize, book);
    //如果当前页码值大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
    if( currentPage > page.getPages()){
      	page = bookService.getPage((int)page.getPages(), pageSize, book);
    }
    return page;
}
```

**e.对应业务层接口与实现类进行修正**

```JAVA
public interface BookService {
    IPage<Book> getPage(int currentPage,int pageSize,Book book);
}
```

```JAVA
@Service
public class BookServiceImpl  implements BookService {
  @Override
  public IPage<Book> getPage(int currentPage, int pageSize, Book book) {
      IPage page = new Page(currentPage,pageSize);
      LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<Book>();
      lqw.like(Strings.isNotEmpty(book.getName()),Book::getName,book.getName());
      lqw.like(Strings.isNotEmpty(book.getType()),Book::getType,book.getType());
lqw.like(Strings.isNotEmpty(book.getDescription()),Book::getDescription,book.getDescription());
      return bookDao.selectPage(page,lqw);
    }
}
```
**总结**

1. 定义查询条件数据模型（当前封装到分页数据模型中）
2. 异步调用分页功能并通过请求参数传递数据到后台

