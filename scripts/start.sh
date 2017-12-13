# 启动命令例子
nohup java -Djava.net.preferIPv4Stack=true -Djava.security.egd=file:/dev/./urandom -Xms256m -Xmx512m -jar demo.jar --rabbit.host=10.10.10.10 > logs/demo.out 2>&1 < /dev/null &