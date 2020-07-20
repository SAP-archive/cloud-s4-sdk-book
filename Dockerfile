FROM maven:3-jdk-8-slim as builder

RUN curl --location --silent "https://cli.run.pivotal.io/stable?release=linux64-binary&source=github" | tar -zx -C /usr/local/bin && \
    cf --version

COPY . /project
WORKDIR /project
RUN mvn package -Dmaven.test.skip=true
RUN ls -la application/target/address-manager-application.war

FROM debian:buster-slim

RUN apt-get -y update && apt-get -y install ca-certificates \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

COPY --from=builder /project /project
COPY --from=builder /usr/local/bin/cf /usr/local/bin
