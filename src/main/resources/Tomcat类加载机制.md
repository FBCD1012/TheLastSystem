## Tomcat类加载机制

- Tomcat类加载机制
  - 基于java的类加载机制进行的一种软件级别的拓展理解操作，原本的java类加载机制，其实是基于双亲委派机制来实现的，但是tomcat中的类加载机制实际上是打破了这种双亲委派机制的

![q0jnymcvj6](E:\OneDrive\桌面\q0jnymcvj6.png)

- 关于java的类加载机制，Java中的类加载机制其实是非常优质的，类加载机制是保证了java类加载的过程中不会出现任何恶意代码的出现的，种机制是非常优质的，下面我对其中java类加载机制的实现进行一波梳理

![image-20230521230118842](C:\Users\FBCD\AppData\Roaming\Typora\typora-user-images\image-20230521230118842.png)

![image-20231015212353666](C:\Users\FBCD\AppData\Roaming\Typora\typora-user-images\image-20231015212353666.png)

- 对于类加载机制来说：

  - java原生的类加载机制的实现，主要是为了解决类与类之间同名冲突而产生的,当Java虚拟机加载一个类的时候，会优先加载其父类，如果父类没有，才进行自身的类加载，其实你通过这个操作你能很明显的发现，这种操作是能够解决虚拟机的负载的，例如：当一个类引用了一个主类的方法，这个主类在类加载的过程之中先于当前类加载了，已经在虚拟机中存在了内存地址，已经相关映射，这个时候这个类直接就调用其方法就行了，而不用再一次的进行类加载操作，这样减少了虚拟机的载荷，也提升了效率，运用了反射以及地址引用思想
  - 在java原生之中，必须按照双亲委派机制来进行执行！顺序是严格执行的

  其实你能很明显发现，很多框架就是基于原生机制上面的拓展,其实框架的出现就是为了解决某些痛点，同时框架也能对其中很多的操作进行封装优化处理，这是非常强大的一种应用落地，其实我还是想去干点创新的事业！

- 对于tomcat的类加载机制

  - 其中的几个类加载器的解析
    1. CommonClassLoader:普通类加载器，普通类加载器操作，最基本的类加载器，可以被容器本身以及容器中的应用访问！
    2. CatalinaClassLoader:卡特琳娜类加载器，就是tomcat的类加载器，用于加载tomcat中的部分启动类以及相关驱动类，这个类是私有的，外界是无法访问的。
       - 关于tomcat中很多的web组件，其实就能窥见一斑，某些组件其实具备自己的作用域，外界是无法进行访问的，其实就是类加载在底层进行操作，其实类加载能干的事情非常之多！怎么组合要看你自己的悟性了
    3. SharedClassLoader:共享类加载器，用于加载tomcat中各个组件之间共享类的类加载器，具体应用就是tomcat中的servletContext，这个权限是公有的
    4. WebAppClassLoader： 网站应用加载器，这个加载器是用于加载程序创造者所定义的web应用程序类，也就是tomcat落地普遍意义上的类加载
    5. JsperClassLoader:加载jsp文件，将jsp加载成为一个servlet类所进行的类加载，这里通过前面的联系起来整体理解就非常强大了

[阿里二面：你知道 Tomcat 的工作原理么？ - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/379107635)

![image-20231016143345942](C:\Users\FBCD\AppData\Roaming\Typora\typora-user-images\image-20231016143345942.png)

- 在tomcat中最为主要的两个组件就是这里了，这个组件表现了整体tomcat中的请求处理流程

  - CONNECTOR(连接器)

    - 连接器其实处理了整个tomcat服务器对于外界请求的处理操作，对外界的http请求进行处理操作，其中连接器又分为几个角色
      - PROTOCOHANDLER:这里进行处理的主要角色就是protocohandler了，它其中包含了细粒度处理请求的角色操作
        - ENDPOINT这里就是实现的请求接受并且处理的角色了
          - ACCEPTOR用于接受外部请求的处理器
          - HANDLER将外部请求进行一种处理，例如格式划分，数据分析等操作
          - ASYNCTIMOUT异步超时，如果请求超时则直接进行退出
        - Processor用于创建请求对象例如创建TOMCATrequest tomcatResponse
          - 这里的对象只是一个初步处理，也就是将外界请求处理为tomcat能够识别的请求，如果你需要对其中的请求进行细粒度操作，那么你就需要进一步抛给ADAPTER
        - ADAPTER其实就是对请求进一步处理，将tomcatrequest 和tomcatResponse转化为容器能够理解的请求操作，然后将请求发送给容器，servletRequest servletResponse，注意servlet本质就是一个网络编程接口

  - CONTAINER（容器）

    容器分为了四个子类组件：ENGINE HOST CONTEXT WRAPPER

    这四个组件是**父子关系**，前期我们已经通过了Connector进行了请求处理操作，那么下面我们就需要对请求进行处理，将请求返回给前端，这里就需要我们自己来对请求进行操作了，我们这里实现的请求就需要放入容器之中了

    **每一个子类组件都是存在一个传递通道的，我们将请求通过每一个子项组件的通道赋值传递到内部进行操作，这样我们就能将servletRequest/servletResponse进行处理的了，注意，赋值操作使用的是setattrbuite。**

    **关于其中的filterChain是位于wrapper内部的操作的，servlet实例时存储在Wrapper中的**

互联网中间件，起到的作用是不可忽视的！
