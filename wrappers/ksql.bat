@echo off

for /f %%i in ('docker ps ^| findstr ksql-cli') do set container-id=%%i
echo %container-id%

set ksql-args=%1 %2 %3 %4 %5 %6 %7 %8 %9
for /L %%i in (0,1,8) do @shift
set ksql-args=%ksql-args% %1 %2 %3 %4 %5 %6 %7 %8 %9
for /L %%i in (0,1,8) do @shift
set ksql-args=%ksql-args% %1 %2 %3 %4 %5 %6 %7 %8 %9

docker exec -it %container-id% /bin/bash ksql %kafka-args%
