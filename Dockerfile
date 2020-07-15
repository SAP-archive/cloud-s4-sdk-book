FROM maven:3-jdk-8-slim as builder

RUN curl --location --silent "https://cli.run.pivotal.io/stable?release=linux64-binary&source=github" | tar -zx -C /usr/local/bin && \
    cf --version

COPY . /app
WORKDIR /app
RUN mvn package -Dmaven.test.skip=true
RUN ls -la application/target/address-manager-application.war

FROM debian:buster-slim

COPY --from=builder /app /app
COPY --from=builder /usr/local/bin/cf /usr/local/bin
