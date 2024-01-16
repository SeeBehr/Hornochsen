apt-get update && apt-get install -y \
    xserver-xorg-video-dummy \
    x11-xserver-utils \
    xinit \
    && rm -rf /var/lib/apt/lists/*