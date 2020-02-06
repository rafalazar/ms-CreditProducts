FROM openjdk:8
VOLUME /tmp
EXPOSE 8105
ADD target/service-CreditProducts-0.0.1-SNAPSHOT.jar mscredit.jar
ENTRYPOINT ["java","-jar","mscredit.jar"]