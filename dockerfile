FROM sbtscala/scala-sbt:eclipse-temurin-focal-17.0.9_9_1.9.8_3.3.1
ENV DISPLAY=$DISPLAY
WORKDIR /hornochsen
COPY . /hornochsen
RUN apt-get update && apt-get install -y firefox
RUN apt-get update && apt-get install -y libgl1-mesa-glx

#CMD ["/usr/bin/firefox"]