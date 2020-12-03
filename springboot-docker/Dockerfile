#使用jdk8作为基础镜像，如果镜像不是本地的将会从DockerHub下载
FROM java:8
#VOLUME指向一个/tmp的目录，由于springboot使用内置的tomcat容器，tomcat默认使用/tmp作为工作目录。
#该命令效果：在宿主机的/var/lib/docker目录下创建一个临时文件并把它链接到容器中的/tmp目录
VOLUME /tmp
COPY target/springboot-docker-1.0-SNAPSHOT.jar app.jar
RUN bash -c "touch /app.jar"
#ENTRYPOINT执行项目app.jar。为了缩短tomcat启动时间，添加一个系统属性指向"/dev/urandom"作为Entropy Source
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]