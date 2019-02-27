hanniupk spring cloud 启动步骤
1、启动hanniupk-cloud-support 配置中心(先启动配置中心)、 注册中心
2、打开 hanniupk-kernel ,在项目根目录下执行 mvn clean install ,把核心类打包到个人仓库中
3、打开 hanniupk-biz-support  在项目根目录下执行 mvn clean install -Dmaven.test.skip=true ,把核心类打包到个人仓库中
4、idea  maven 导入hanniupk-system 运行SystemApplication的main方法 启动系统服务
5、idea maven 导入hanniupk-gateway 启动路由服务
