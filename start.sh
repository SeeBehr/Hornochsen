xhost +
docker build -t hornochsen:v1 .
docker run -d --name hornochsen -e DISPLAY=:0.0 -v /tmp/.X11-unix:/tmp/.X11-unix hornochsen:v1
#docker run -d --name hornochsen hornochsen:v1