docker build -t hornochsen:v1 . 
docker run --name hornochsen -p 6080:80 -p 5900:5900 hornochsen:v1
