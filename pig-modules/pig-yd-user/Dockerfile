FROM registry.cn-hangzhou.aliyuncs.com/sherry/java:1.8.0_111
VOLUME /tmp
ADD pig-yd-user.jar pig-yd-user.jar
RUN sh -c 'touch /pig-yd-user.jar'
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' > /etc/timezone
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java -Xms$Xms -Xmx$Xmx $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /pig-yd-user.jar" ]