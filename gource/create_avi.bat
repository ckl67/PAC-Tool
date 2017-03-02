@echo off
cls
echo =======================================================
echo                       GOURCE
echo =======================================================
echo   Will generate .avi file of the Software projects 
echo   displayed as an animated tree. 
echo del .ppm
REM -1280x720 not working to much to compute.
REM CALL gource -1280x720 -s 1 -o gource.ppm ..\src
REM CALL gource -720x360 -s 1 -o gource.ppm ..\src
CALL gource -960x540 -s 5 -o gource.ppm ..\src
echo generation of .avi
@echo on
ffmpeg -y -r 60 -i audio.mp3 -f image2pipe -vcodec ppm -i gource.ppm -vcodec libx264 -preset ultrafast -pix_fmt yuv420p -crf 1 -threads 0 -bf 0 -shortest gource.x264.avi
@echo off
echo del .ppm
del "gource.ppm"
echo =======================================================
echo                        Finish			 	 			 
echo =======================================================
