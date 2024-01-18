apt-get update
echo "German" | apt-get install -y xserver-xorg-video-dummy
echo "German" | apt-get install -y x11-xserver-utils
echo "German" | xinit
echo "German" | apt-get install -y firefox
echo "German" | apt-get install -y libgl1-mesa-glx
rm -rf /var/lib/apt/lists/*