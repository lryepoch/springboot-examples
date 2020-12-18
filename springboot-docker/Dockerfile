#使用jdk8作为基础镜像，如果镜像不是本地的将会从DockerHub下载
FROM java:8
#VOLUME指向一个/tmp的目录，由于springboot使用内置的tomcat容器，tomcat默认使用/tmp作为工作目录。
#该命令效果：在宿主机的/var/lib/docker目录下创建一个临时文件并把它链接到容器中的/tmp目录。创建/tmp目录并持久化到Docker数据文件夹
VOLUME /tmp
#拷贝文件并且重命名
#add 和 copy 都是复制文件 / 文件夹
#add 可以从网络 / 本地复制; copy 仅从本地复制, 语义更明确, 推荐使用 copy
COPY target/springboot-docker-1.0-SNAPSHOT.jar app.jar
RUN bash -c "touch /app.jar"
#表示容器运行后默认执行的命令。ENTRYPOINT执行项目app.jar。为了缩短tomcat启动时间，添加一个系统属性指向"/dev/urandom"作为Entropy Source
#-D 是新增参数，参数的名字叫 java.security.egd ，参数的值叫 file:/dev/./urandom。这可以加快随机数产生过程，因为SecureRandom在java各种组件中使用广泛，可以可靠的产生随机数。但在大量产生随机数的场景下，性能会较低。
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]