@echo off

for /f %%i in ('docker ps ^| findstr kafka-connect') do set container-id=%%i
echo %container-id%

set kafka-args=%1 %2 %3 %4 %5 %6 %7 %8 %9
for /L %%i in (0,1,8) do @shift
set kafka-args=%kafka-args% %1 %2 %3 %4 %5 %6 %7 %8 %9
for /L %%i in (0,1,8) do @shift
set kafka-args=%kafka-args% %1 %2 %3 %4 %5 %6 %7 %8 %9

docker exec -i %container-id% kafka-topics %kafka-args%
