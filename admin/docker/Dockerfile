FROM java:8
# 作者
MAINTAINER piaomiao <405161509@qq.com
VOLUME /tmp
# 编码
ENV LANG C.UTF-8
# 添加jar
ADD *.jar TSP.jar
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
RUN  sed -i s@/archive.ubuntu.com/@/mirrors.aliyun.com/@g /etc/apt/sources.list
RUN  apt-get clean
RUN bash -c "touch /TSP-2.5.5.jar"
EXPOSE 8081
#ENTRYPOINT ["nohup","java","-jar","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8082","-Dspring.profiles.active=prod","TSP-2.5.5.jar",">","/mydata/logs/modern.log 2",">","&1","&"]
ENTRYPOINT ["java","-jar","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8082","-Dspring.profiles.active=prod","-Duser.timezone=GMT+08","/data/TSP-2.5.5.jar"]