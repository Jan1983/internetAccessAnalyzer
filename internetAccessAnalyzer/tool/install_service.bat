set service_name=InternetAccessAnalyzer
set tools="D:\Program Files (x86)\Windows Resource Kits\Tools"

%tools%\instsrv %service_name% %tools%\srvany.exe

%tools%\sleep 8

regedit /s "D:\Workspace\Java\internetAccessAnalyzer\tool\dienst.reg"

%tools%\sleep 8

net start %service_name%
pause