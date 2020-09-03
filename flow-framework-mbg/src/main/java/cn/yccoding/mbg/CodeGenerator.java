package cn.yccoding.mbg;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

public class CodeGenerator {
    public static final String AUTHOR = "yc";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/flow_single?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "1234";
    public static final String PACKAGE_PARENT = "cn.yccoding";
    public static final String MODULE = "member";
    public static final String INCLUDE[] = {"ums_member"};
    public static final String DB_PREFIX = "ums_";

    public static void main(String[] args) {
        genCode();
        System.out.println("over");
    }

    public static void genCode() {

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/flow-framework-mbg/src/main/java");
        gc.setAuthor(AUTHOR);
        //生成后是否打开资源管理器
        gc.setOpen(false);
        //重新生成时文件是否覆盖
        gc.setFileOverride(false);
        //去掉Service接口的首字母I
        gc.setServiceName("%sService");
        //主键策略
        gc.setIdType(IdType.ASSIGN_ID);
        //定义生成的实体类中日期类型
        gc.setDateType(DateType.ONLY_DATE);
        //开启Swagger2模式
        gc.setSwagger2(false);
        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(DB_URL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(DB_USERNAME);
        dsc.setPassword(DB_PASSWORD);
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        //模块名
        pc.setModuleName("domain");
        String parent = PACKAGE_PARENT+"."+MODULE;
        pc.setParent(parent);
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        //数据库表映射到实体的命名策略,驼峰
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 生成的表
        if (INCLUDE.length > 0) {
            strategy.setInclude(INCLUDE);
        }
        //设置表前缀不生成
        strategy.setTablePrefix(DB_PREFIX);
        //数据库表字段映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // lombok 模型 @Accessors(chain = true) setter链式操作
        strategy.setEntityLombokModel(true);
        //逻辑删除字段名
        strategy.setLogicDeleteFieldName("is_deleted");
        //去掉布尔值的is_前缀
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);

        //自动填充
        TableFill gmtCreate = new TableFill("create_time", FieldFill.INSERT);
        TableFill gmtModified = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        strategy.setTableFillList(tableFills);

        //restful api风格控制器
        strategy.setRestControllerStyle(true);
        //url中驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);

        //设置BaseEntity
        strategy.setSuperEntityClass("cn.yccoding.common.base.BaseEntity");
        // 填写BaseEntity中的公共字段
        strategy.setSuperEntityColumns("id", "create_time", "update_time");

        // 6、执行
        mpg.execute();
    }
}
