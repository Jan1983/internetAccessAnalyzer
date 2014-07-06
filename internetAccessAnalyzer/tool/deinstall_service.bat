set service_name=InternetAccessAnalyzer
set tools="D:\Program Files (x86)\Windows Resource Kits\Tools"

net stop %service_name%

%tools%\instsrv %service_name% REMOVE

pause