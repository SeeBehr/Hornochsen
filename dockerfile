FROM sbtscala/scala-sbt:eclipse-temurin-focal-17.0.9_9_1.9.8_3.3.1
ENV DISPLAY=:0.0
WORKDIR /hornochsen
COPY . /hornochsen
ENV DEBIAN_FRONTEND noninteractive
RUN chmod +x ./setup.sh && ./setup.sh
ENV DEBIAN_FRONTEND teletype
CMD ["/usr/bin/firefox"]