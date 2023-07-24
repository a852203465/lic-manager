# base image
FROM registry.cn-shenzhen.aliyuncs.com/a852203465/alpine-jdk8:slim2

# MAINTAINER
MAINTAINER 852203465@qq.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

# put jar into container
ADD *.jar app.jar

# running required command
ENTRYPOINT ["/bin/sh", "-c", "set -e && java -Xms512m -Xmx1024m -Djava.security.egd=file:/dev/./urandom -Dfile.encoding=utf-8 -jar /app.jar"]

