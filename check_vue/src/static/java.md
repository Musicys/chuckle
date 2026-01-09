Mapper层

extends baseMapper<实体类>

    .selectList(条件||null)查询通过返回list集合
    .insert(实体类 )单数据添加
    .deleByid(long id)
    .deletebyMap({age:23}   map)
    .deleteBathids(arr)
    .updateByid(实例类);

    .seletctByid(1L)；单个数据
    .selectBatchId(arrid)返回list
    .selectMap(new map[{age:200},{"nmae":"张三"}])
    .selectList(条件构造器||null)

    page分页功能

Service

实现类 extends ServiceImp<UserMappr,user> implenment UserService()

    cout 查询总记录数
    save 添加数据

注解

@TbaleName("表名")

作用在类上

@TableId（value="数据库主键字段"，type=idType.auto）主键自增

指定属性是主键

@TableFeild(value="")

指定属性 丰主键

@TableLogic 逻辑删除

条件构造器

QueryWrapper

eq 等于 例：eq(“name”,“张子”) ===> name = ‘张子’

ne 不等于 例：ne(“name”,“张子”) ===> name <> ‘张子’

gt 大于 例：gt(“age”,“18”) ===> age>18

lt 小于 例：lt(“age”,“18”) ===> age<18

between 在值1到值2之间 例：between(“age”,18,30) ===> 18<age<30’

like 模糊查询 例：like(“name”,“张”) ===> name like ‘%张%’

isNull 字段为NULL 例：isNull(“name”) ===> name is null

EQ equal 等于  
 NE not equal 不等于
GT greater than 大于  
 LT less than 小于  
 GE greater than or equal 大于等于
LE less than or equal 小于等于

插件

    @Configuration

    public class MybatisPlusConfig {
        @Bean
        public MybatisPlusInterceptor mybatisPlusInterceptor(){

            MybatisPlusInterceptor interceptor=new MybatisPlusInterceptor();
            //分页插件
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
            //乐观锁插件
            interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
            return  interceptor;
        }
    }

分页插件

     Page<Employee> page= new Page<>(1,3);
    employyeService.page(page, null);
     System.out.println(page);

乐观锁

通过版本号比较，来进行判断是否更新

@Version

版本号注解

通用枚举

代码生成器

快速搭建模板
