# 设计模式--创建型

## 说明

### 面向对象的设计原则（--GOF）

- 对接口编程而不是对实现编程
- 优先使用对象组合而不是继承

### 创建型模式

该类型设计模式提供了一种在创建对象的同时隐藏创建逻辑的方式，而不是使用 new 运算符直接实例化对象。这使得程序在判断针对某个给定实例需要创建哪些对象时更加灵活。

## 工厂模式

### 介绍

- 目的：提供一个统一的类，可以直接直接通过类名获取对象实例（单个工厂时，也成简单工厂）
- 关键：定义一个创建对象的接口，让子类决定实例化哪一个工厂类，使类的创建过程延迟到子类进行
- 场景：1. 运行时才能确定对象的类型；2. 数据库切换，动态配置不同数据库类型的连接
- 优点：1. 将具体产品和创建者解耦；2. 符合单一职责原则；3. 扩展性高，如果想增加一个产品，只要扩展一个工厂类就可以
- 缺点：1. 每次增加一个产品时，都需要增加一个具体类和对象实现工厂，使得系统中类的个数成倍增加

### 源码应用

Calendar.getInstance()
java.text.NumberFormat.getInstance()
java.util.ResourceBundle.getBundle()

java.net.URLStreamHandlerFactory
javax.xml.bind.JAXBContext.createMarshaller

### Demo实现

#### 实现方式

1. 提供一个AD，AP两个英雄工厂，传入英雄名即可返回对应英雄实例
2. 所有英雄都继承同一个接口；
3. 利用多态返回实例

#### 图例

![img](https://notes-yc-01.oss-cn-shenzhen.aliyuncs.com/blog/2020/12/05/fc45d942aee241948cc49d6715436047.png)

### 步骤

1. 创建一个接口 

   ```java
   public interface Legend {
   
       /**
        * 大招
        */
       String ult();
   }
   ```

2. 创建接口实现类 

   ```java
   public class Garen implements Legend {
       private String garenParam;
   
       @Override
       public String ult() {
           return "Demacian Justice（德玛西亚正义）";
       }
   
       public void setGarenMethod(String garenParam) {
           System.out.println("盖伦自有方法调用...");
           this.garenParam = garenParam;
       }
   }
   ```

   ```java
   public class XinZhao implements Legend{
       @Override
       public String ult() {
           return "Crescent Sweep（新月横扫）";
       }
   }
   ```

   ```java
   public class Teemo implements Legend{
       @Override
       public String ult() {
           return "Noxious Trap（种蘑菇）";
       }
   }
   ```

3. 创建一个Ad,Ap工厂类，根据指定输入参数，生成对应的实例对象 

   ```java
   public class AdLegendFactory {
   
       /**
        * 获取英雄的类
        */
       public Legend getLegend(String name){
   
           if ("garen".equalsIgnoreCase(name)) {
               Garen garen = new Garen();
               garen.setGarenMethod("德玛西亚");
               return garen;
           } else if ("xinzhao".equalsIgnoreCase(name)) {
               return new Teemo();
           }
           return null;
       }
   }
   ```

   ```java
   public class ApLegendFactory {
   
       public Legend getLegend(String name) {
           if ("teemo".equalsIgnoreCase(name)) {
               return new Teemo();
           }
           return null;
       }
   }
   ```

4. 使用上一步的工厂类，传如类型信息，获取对象，调用方法

   ```java
   public class FactoryPatternDemo {
   
       public static void main(String[] args) {
           AdLegendFactory adFactory = new AdLegendFactory();
           ApLegendFactory apFactory = new ApLegendFactory();
           String garenUlt = adFactory.getLegend("garen").ult();
           String teemoUlt = apFactory.getLegend("teemo").ult();
           System.out.println(garenUlt);
           System.out.println(teemoUlt);
       }
   }
   ```

5. 执行流程

   ```
   盖伦自有方法调用...
   Demacian Justice（德玛西亚正义）
   Noxious Trap（种蘑菇）
   ```

## 抽象工厂模式

### 介绍

- 目的：生产不同**产品族**的工厂
- 关键：提供一个创建一系列相关或相互依赖对象的接口，而无需指定它们具体的类。又称其他工厂定的工厂
- 场景：1. 程序需要处理不同系列的相关产品，但是您不希望它依赖于这些产品的具体类时
- 优点：1. 可以确信你从工厂得到的产品彼此是兼容的；2. 符合单一职责原则；3. 符合开闭原则
- 缺点：1. 扩展性不好; 2. 抽象工厂类中加入了其他工厂类的方法

### 源码应用

java.sql.Connection 
java.sql.Driver

### Demo实现

#### 实现方式

1. 创建一个英雄和装备接口，分别实现该接口（不同产品族）。实现类即为具体的英雄或装备；
2. 分别定义两个接口的工厂类Factory，功能：传入英雄或装备名称，即可以返回指定英雄或装备的实例对象；
3. 创建抽象工厂类，上述两个工厂类，实现该抽象工厂类
4. 创建工厂生产者，提供抽象工厂类

#### 图例

![img](https://notes-yc-01.oss-cn-shenzhen.aliyuncs.com/blog/2020/11/22/39c85311ec384d26b1b03ad3ddad262a.png)

#### 步骤

1. 创建一个英雄接口 

   ```java
   public interface Legend {
   
       /**
        * 大招
        */
       String ult();
   }
   ```

   ```java
       /**
        * 装备属性
        *
        * @return
        */
       String stat();
   }
   ```

2. 创建抽象工厂类

   ```java
   public abstract class AbstractFactory {
       public abstract Legend getLegend(String legend);
       public abstract Item getItem(String item);
   }
   ```

3. 创建接口实现类 

   ```java
   public class Garen implements Legend {
       private String garenParam;
   
       @Override
       public String ult() {
           return "Demacian Justice（德玛西亚正义）";
       }
   
       public void setGarenMethod(String garenParam) {
           System.out.println("盖伦自有方法调用...");
           this.garenParam = garenParam;
       }
   }
   ```

   ```java
   public class XinZhao implements Legend {
       @Override
       public String ult() {
           return "Crescent Sweep（新月横扫）";
       }
   }
   ```

   ```java
   public class Teemo implements Legend {
       @Override
       public String ult() {
           return "Noxious Trap（种蘑菇）";
       }
   }
   ```

4. 创建一个装备接口 

   ```java
   public class SunFire implements Item {
       @Override
       public String stat() {
           return "ar(护甲)";
       }
   }
   ```

   ```java
   public class TriForce implements Item {
       @Override
       public String stat() {
           return "AS,Crit,AD(攻速，暴击，攻击力)";
       }
   }
   ```

   ```java
   public class ZhonYa implements Item {
       @Override
       public String stat() {
           return "AP,AR(法强，护甲)";
       }
   }
   ```

5. 创建接口实现类 

   ```java
   public class LegendFactory extends AbstractFactory{
       @Override
       public Legend getLegend(String legend) {
           if ("garen".equalsIgnoreCase(legend)) {
               Garen garen = new Garen();
               garen.setGarenMethod("德玛西亚");
               return garen;
           } else if ("xinzhao".equalsIgnoreCase(legend)) {
               return new XinZhao();
           } else if ("teemo".equalsIgnoreCase(legend)) {
               return new Teemo();
           }
           return null;
       }
   
       @Override
       public Item getItem(String item) {
           return null;
       }
   }
   ```

   ```java
   public class ItemFactory extends AbstractFactory {
       @Override
       public Legend getLegend(String legend) {
           return null;
       }
   
       @Override
       public Item getItem(String item) {
           if (item == null) {
               return null;
           }
           if (item.equalsIgnoreCase("sunfire")) {
               return new SunFire();
           } else if (item.equalsIgnoreCase("triforce")) {
               return new TriForce();
           } else if (item.equalsIgnoreCase("zhonya")) {
               return new ZhonYa();
           }
           return null;
       }
   }
   ```

6. 创建一个英雄工厂类，继承抽象工厂类，重写获取实例方法。根据指定输入参数，生成对应的实例对象

    ```java
    public class LegendFactory extends AbstractFactory{
        @Override
        public Legend getLegend(String legend) {
            if ("garen".equalsIgnoreCase(legend)) {
                Garen garen = new Garen();
                garen.setGarenMethod("德玛西亚");
                return garen;
            } else if ("xinzhao".equalsIgnoreCase(legend)) {
                return new XinZhao();
            } else if ("teemo".equalsIgnoreCase(legend)) {
                return new Teemo();
            }
            return null;
        }
    
        @Override
        public Item getItem(String item) {
            return null;
        }
    }
    ```

7. 创建一个装备工厂类，继承抽象工厂类，重写获取实例方法。根据指定输入参数，生成对应的实例对象

    ```java
    public class ItemFactory extends AbstractFactory {
        @Override
        public Legend getLegend(String legend) {
            return null;
        }
    
        @Override
        public Item getItem(String item) {
            if (item.equalsIgnoreCase("sunfire")) {
                return new SunFire();
            } else if (item.equalsIgnoreCase("triforce")) {
                return new TriForce();
            } else if (item.equalsIgnoreCase("zhonya")) {
                return new ZhonYa();
            }
            return null;
        }
    }
    ```

8. 创建一个生产者类，或者抽象工厂实例

   ```java
   public class FactoryProducer {
   
       public static AbstractFactory getFactory(String factory) {
           if ("legend".equalsIgnoreCase(factory)) {
               return new LegendFactory();
           } else if ("item".equalsIgnoreCase(factory)) {
               return new ItemFactory();
           }
           return null;
       }
   }
   ```

9. 执行流程

    ```java
    public class AbstractFactoryPatternDemo {
    
        public static void main(String[] args) {
            AbstractFactory itemFactory = FactoryProducer.getFactory("item");
            AbstractFactory legendFactory = FactoryProducer.getFactory("LEGEND");
    
            Item triforce = itemFactory.getItem("triforce");
            System.out.println(triforce.stat());
    
            Legend timo = legendFactory.getLegend("teemo");
            System.out.println(timo.ult());
        }
    }
    ```

10. 终端打印

    ```
    AS,Crit,AD(攻速，暴击，攻击力)
    Noxious Trap（种蘑菇）
    ```

## 单例模式

### 介绍

- 目的：保证一个类全局只有一个实例对象
- 关键：构造器私有
- 场景：1. 全局频繁使用的对象；2. 重量级对象，不需要多个对象，数据库连接，线程池
- 优点：1. 将具体产品和创建者解耦；2. 符合单一职责原则；3. 扩展性高，如果想增加一个产品，只要扩展一个工厂类就可以
- 缺点：1. 没有接口，不能继承；2. 与单一职责冲突，一个类只关心内部逻辑，不关注外部如何实例化

### 源码应用

Spring & JDK 

 java.lang.Runtime org.springframework.aop.framework.ProxyFactoryBean org.springframework.beans.factory.support.DefaultSingletonBeanRegistry org.springframework.core.ReactiveAdapterRegistry

### Demo实现

#### 实现方式

> 以下设计模式都考虑了线程安全

#### 饿汉模式

- 特点：类加载的初始化阶段就完成了实例的初始化。

- 设计：本质上就是借助于jvm 类加载机制，保证实例的唯一性（初始化过程只会执行一次）及线程安全（JVM以同步的形式来完成类加载的整个过程）

  ```java
  public class HungrySingleton {
  
      /**
       * 类加载时就给静态变量赋值初始化
       */
      private static final HungrySingleton instance = new HungrySingleton();
  
      /**
       * 构造器私有
       */
      private HungrySingleton() {
      }
  
      public static HungrySingleton getInstance() {
          return instance;
      }
  }
  ```

#### 懒汉模式

- 特点：延迟加载， 只有在真正使用的时候，才开始实例化

- 设计：DCL双端校验锁优化，volatile关键字防止编译器（JIT）指令重排导致未初始化实例

  ```java
  public class LazySingleton {
  
      /**
       * 静态属性，可见性，防止指令重排
       */
      private volatile static LazySingleton instance;
  
      /**
       * 构造器私有
       */
      private LazySingleton() {
      }
  
      public static LazySingleton getInstance() {
          // DCL
          if (instance == null) {
              synchronized (LazySingleton.class) {
                  if (instance == null) {
                      return new LazySingleton();
                  }
              }
          }
          return instance;
      }
  }
  ```

#### 静态内部类

- 特点：懒加载的一种，只有在实际使用的时候，才会触发类的初始化

- 设计：本质上是利用类的加载机制来保证线程安全 

  ```java
  public class InnerClassSingleton implements Serializable {
      static final long serialVersionUID = 1L;
  
      private InnerClassSingleton() {
          // 防止反射破坏
          if (InnerClassHolder.instance != null) {
              throw new RuntimeException("单例模式不允许多个实例");
          }
      }
  
      public static InnerClassSingleton getInstance() {
          return InnerClassHolder.instance;
      }
  
      /**
       * 模拟反射攻击
       *
       * @param args
       */
      public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
          Constructor<InnerClassSingleton> declaredConstructor = InnerClassSingleton.class.getDeclaredConstructor();
          declaredConstructor.setAccessible(true);
          InnerClassSingleton innerClassSingleton = declaredConstructor.newInstance();
          InnerClassSingleton instance = InnerClassSingleton.getInstance();
          System.out.println(innerClassSingleton == instance);
      }
  
      /**
       * 提供readResolve()方法，防反序列化
       * 当JVM反序列化地恢复一个新对象时，系统会自动调用这个readResolve()方法返回指定好的对象，从而保证系统通过反序列化机制不会产生多个java对象
       *
       * @return
       * @throws ObjectStreamException
       */
      Object readResolve() throws ObjectStreamException {
          return InnerClassHolder.instance;
      }
  
      /**
       * 实例的创建，只有世纪调用外部类时，出发静态内部类的初始化，属于懒加载
       */
      private static class InnerClassHolder {
          private static final InnerClassSingleton instance = new InnerClassSingleton();
      }
  }
  ```

#### 枚举

- 特点：天然不支持反射创建对应的实例，且有自己的反序列化机制

- 设计：本质上是利用类的加载机制来保证线程安全 

  ```java
  public enum EnumSingleton {
      /**
       * 天然不支持反射创建对应的实例，且有自己的反序列化机制
       * 利用类加载机制保证线程安全
       */
      ENUM_INSTANCE("singleton","enum"),
      LAZY_INSTANCE("singleton","lazy");
  
      private String name;
      private String type;
  
      EnumSingleton(String name, String type) {
          this.name = name;
          this.type = type;
      }
  }
  ```

> 反射攻击及序列化攻击课参考示例代码

## 建造者模式

### 介绍

- 目的：将一个复杂对象的创建与他的表示分离，使得同样的构建过程可以创建不同的表示
- 关键：设置属性，返回对象本身
- 场景：1.需要生成的对象具有复杂的内部结构 ；2. 需要生成的对象内部属性本身相互依赖 
- 优点：1. 建造者独立，易扩展；2. 便于控制细节风险
- 缺点：1. 属性变化，建造者也要跟着变化

### Demo实现

#### 实现方式1

- 创建英雄类，内部实现建造者模式

  ```JAVA
  @Data
  public class Champ {
  
      /**
       * 昵称
       */
      private String nickname;
  
      /**
       * 召唤师技能
       */
      private List<String> sums;
  
      /**
       * 被动技能
       */
      private String passive;
  
      /**
       * 角色
       */
      private int roleId = 0;
  
      private Champ(String nickname, List<String> sums, String passive, int roleId) {
          this.nickname = nickname;
          this.sums = sums;
          this.passive = passive;
          this.roleId = roleId;
      }
  
      /**
       * 私有构造器，只能通过建造者创建
       */
      private Champ() {
      }
  
      public static ChampBuilder builder() {
          return new ChampBuilder();
      }
  
      /**
       * 建造者
       */
      public static class ChampBuilder {
  
          private String nickname;
          private List<String> sums;
          private String passive;
          private int roleId = 0;
  
          public ChampBuilder nickname(String nickname) {
              this.nickname = nickname;
              return this;
          }
  
          public ChampBuilder setSums(String... sums) {
              this.sums = Arrays.asList(sums);
              return this;
          }
  
          public ChampBuilder setPassive(String passive) {
              this.passive = passive;
              return this;
          }
  
          public ChampBuilder setRoleId(int roleId) {
              this.roleId = roleId;
              return this;
          }
  
          /**
           * 创建
           */
          public Champ build() {
              // TODO 校验参数...
              if (nickname == null || "".equalsIgnoreCase(nickname)) {
                  throw new RuntimeException("名称不可以为空");
              }
              // 构造实例
              Champ champ = new Champ();
              champ.setNickname(nickname);
              if (sums != null && sums.isEmpty()) {
                  champ.setSums(sums);
              }
              if (passive != null && "".equalsIgnoreCase(passive)) {
                  champ.setPassive(passive);
              }
              if (roleId > 0) {
                  champ.setRoleId(roleId);
              }
              return champ;
          }
      }
  }
  ```

- 测试

  ```java
  public class BuilderPatternDemo {
      public static void main(String[] args) {
          Champ champ = Champ.builder().nickname("zhao xin").setPassive("fight").build();
          System.out.println(champ);
      }
  }
  ```

- 打印

  ```
  Champ(nickname=zhao xin, sums=null, passive=null, roleId=0)
  ```

#### 实现方式2

图例

![img](https://notes-yc-01.oss-cn-shenzhen.aliyuncs.com/blog/2020/12/06/11e96272c97646bc96263e292a64b91c.png)

- 创建召唤师和装备接口

  ```java
  /**
   * 召唤师
   */
  public interface Item {
      /**
       * 名称
       */
      String name();
  
      /**
       * 属性
       */
      double price();
  
      /**
       * 装备
       */
      Equipment equip();
  }
  ```

  ```java
  /**
   * 装备
   */
  public interface Equipment {
      /**
       * 装备
       */
      String equip();
  }
  ```

- 创建装备的实现类

  ```java
  /**
   * 攻击装备
   */
  public class Attack implements Equipment {
      @Override
      public String equip() {
          return "attack";
      }
  }
  ```

  ```java
  /**
   * 防御装备
   */
  public class Defense implements Equipment {
      @Override
      public String equip() {
          return "defense";
      }
  }
  ```

- 创建召唤师item的接口抽象类

  ```java
  /**
   * 魔法伤害
   */
  public abstract class AbilityPower implements Item {
      @Override
      public abstract double price();
  
      @Override
      public Equipment equip() {
          return new Attack();
      }
  }
  ```

  ```java
  /**
   * 魔抗
   */
  public abstract class MagicResistance implements Item {
      @Override
      public abstract double price();
  
      @Override
      public Equipment equip() {
          return new Defense();
      }
  }
  ```

- 创建上述抽象类的继承类

  ```java
  /**
   * 遗失的章节
   */
  public class LostChapter extends AbilityPower{
      @Override
      public String name() {
          return "鬼书";
      }
  
      @Override
      public double price() {
          return 2700.0;
      }
  }
  ```

  ```java
  /**
   * 卢登的回升
   */
  public class LudensEcho extends AbilityPower{
      @Override
      public String name() {
          return "卢登的回升";
      }
  
      @Override
      public double price() {
          return 3100.0;
      }
  }
  ```

  ```java
  /**
   * 圣物之盾
   */
  public class RelicShield extends MagicResistance{
      @Override
      public String name() {
          return "圣物之盾";
      }
  
      @Override
      public double price() {
          return 2500.0;
      }
  }
  ```

  ```java
  /**
   * 振奋盔甲（绿甲）
   */
  public class SpiritVisage extends MagicResistance{
  
      @Override
      public String name() {
          return "绿甲";
      }
  
      @Override
      public double price() {
          return 2600.0;
      }
  }
  ```

- 创建组合类suit，包含英雄和装备

  ```java
  /**
   * 套装
   */
  public class Suit {
      private final List<Item> items = new ArrayList<>();
  
      public void addItem(Item item) {
          items.add(item);
      }
  
      public double getMoney() {
          return items.stream().map(Item::price).reduce(Double::sum).orElse(0.0);
      }
  
      public void showItems() {
          items.forEach(System.out::println);
      }
  }
  ```

- 创建建造者类，后续扩展也在此扩展

  ```java
  /**
   * 装备建造者
   */
  public class SuitBuilder {
  
      public Suit equipA() {
          Suit suit = new Suit();
          suit.addItem(new LostChapter());
          suit.addItem(new RelicShield());
          return suit;
      }
  
      public Suit equipB() {
          Suit suit = new Suit();
          suit.addItem(new LudensEcho());
          suit.addItem(new SpiritVisage());
          return suit;
      }
  }
  ```

- 测试

  ```java
  /**
   * 建造者模式--示例类
   */
  public class BuilderPatternDemo {
  
      public static void main(String[] args) {
          SuitBuilder suitBuilder = new SuitBuilder();
  
          Suit suit1 = suitBuilder.equipA();
          suit1.showItems();
          System.out.println(suit1.getMoney());
  
          System.out.println("==========");
  
          Suit suit2 = suitBuilder.equipB();
          suit2.showItems();
          System.out.println(suit2.getMoney());
      }
  }
  ```

## 原型模式

### 说明

- 目的：用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的 

  对象

- 关键：实现Cloneable接口

- 场景：1.需要生成的对象具有复杂的内部结构 ；2. 需要生成的对象内部属性本身相互依赖 

- 优点：1.性能提高; 2.逃避构造函数的约束

- 缺点：1. 必须实现 Cloneable 接口;

  > 在实际项目中，原型模式很少单独出现，一般是和工厂方法模式一起出现，通过 clone 的方法创建一个对象，然后由工厂方法提供给调用者

### Demo实现

#### 实现方式1

- 实现cloneable接口

  ```java
  @Data
  public class Champ implements Cloneable{
      
      private String name;
  
      @Override
      protected Object clone() {
          Object clone = null;
          try {
              clone = super.clone();
          } catch (CloneNotSupportedException e) {
              e.printStackTrace();
          }
          return clone;
      }
  
      public static void main(String[] args) {
          Champ champ = new Champ();
          Champ clone = (Champ) champ.clone();
      }
  }
  ```

#### 实现方式2

- 整合工厂模式

- 创建抽象类，实现Cloneable接口

  ```java
  @Data
  public abstract class Legend implements Cloneable{
      private String name;
  
      protected String type;
  
      /**
       * 大招
       */
      abstract String ult();
  
      @Override
      protected Object clone(){
          Object clone = null;
          try {
              clone = super.clone();
          } catch (CloneNotSupportedException e) {
              e.printStackTrace();
          }
          return clone;
      }
  }
  ```

- 继承抽象类

  ```java
  public class Garen extends Legend{
      public Garen() {
          type = "garen123";
      }
  
      @Override
      String ult() {
          return "德玛西亚审判";
      }
  }
  ```

  ```java
  public class Teemo extends Legend{
      public Teemo() {
          type = "teemo123";
      }
  
      @Override
      public String ult() {
          return "Noxious Trap（种蘑菇）";
      }
  }
  ```

- 缓存类，保存实例

  ```java
  public class LegendCache {
  
      private static ConcurrentHashMap<String, Legend> legendCache = new ConcurrentHashMap<>();
  
      public static Legend getLegend(String name) {
          Legend legend = legendCache.get(name);
          return (Legend) legend.clone();
      }
  
      public static void loadCache() {
          Legend garen = new Garen();
          garen.setName("garen");
          legendCache.put("garen",garen);
  
          Legend teemo = new Teemo();
          garen.setName("teemo");
          legendCache.put("teemo",teemo);
  
          Legend xinZhao = new XinZhao();
          garen.setName("xinzhao");
          legendCache.put("xinzhao",xinZhao);
      }
  }
  ```

- 测试

  ```java
  public class PrototypePatternDemo {
  
      public static void main(String[] args) {
          LegendCache.loadCache();
  
          Legend teemo1 = LegendCache.getLegend("teemo");
          Legend teemo2 = LegendCache.getLegend("teemo");
          System.out.println(teemo1.hashCode());
          System.out.println(teemo2.hashCode());
  
          Teemo teemo = new Teemo();
          Teemo teemo3 = (Teemo) teemo.clone();
          Teemo teemo4 = (Teemo) teemo.clone();
          System.out.println(teemo3.hashCode());
          System.out.println(teemo4.hashCode());
      }
  }
  ```


