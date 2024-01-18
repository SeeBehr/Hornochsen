xhost +
sudo chmod 1777 /tmp/.X11-unix
docker build -t hornochsen:v1 . 
docker run --name hornochsen -e DISPLAY=$DISPLAY -v /tmp/.X11-unix:/tmp/.X11-unix hornochsen:v1
