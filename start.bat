docker build -t hornochsen:v1 . 
docker run -e DISPLAY=$DISPLAY -v /tmp/.X11-unix:/tmp/.X11-unix hornochsen:v1
