FROM sbtscala/scala-sbt:eclipse-temurin-focal-17.0.9_9_1.9.8_3.3.1
ENV DISPLAY=$DISPLAY
WORKDIR /hornochsen
COPY . /hornochsen
RUN chmod +x ./setup.sh && ./setup.sh
CMD ["/usr/bin/firefox"]