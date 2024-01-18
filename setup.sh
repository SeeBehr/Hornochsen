apt-get update
apt-get install -y firefox
apt-get install -y libgl1-mesa-glx
echo "German" | apt-get install -y
echo "German" | apt-get xserver-xorg-video-dummy
echo "German" | apt-get x11-xserver-utils
echo "German" | xinit
rm -rf /var/lib/apt/lists/*