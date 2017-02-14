echo "======================================================="
echo "               TO KEEP WINDOWS OPEN					 "
echo "======================================================="
REM gource -1280x720 -s 1 -o gource.ppm \workspace\pac-tool\src
gource -720x360 -s 1 -o gource.ppm ..\src
ffmpeg -y -r 60 -f image2pipe -vcodec ppm -i gource.ppm -vcodec libx264 -preset ultrafast -pix_fmt yuv420p -crf 1 -threads 0 -bf 0 gource.x264.avi
echo " Will delete ppm"
del *.ppm
echo "======================================================="
echo "    					Finish			 	 			 "
echo "======================================================="
