xhost +
docker build -t hornochsen:v1 .
docker run -d --name hornochsen -v /tmp/.X11-unix:/tmp/.X11-unix hornochsen:v1
docker logs -f hornochsen