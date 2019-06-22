# Dictionary
English-Chinese Translation Dictionary based on Java

## Derby User Manual
Download Derby from http://db.apache.org/derby/derby_downloads.html

choose the version which suits you

What I'm using is db-derby-10.15.1.3-bin

Unzip the compression package to D:\Derby

##Configure in windows:

1.Set system variable

name: DERBY_HOME
path: D:\Derby\db-derby-10.15.1.3-bin

name: CLASSPATH
path: %DERBY_HOME%\lib \derby.jar;%DERBY_HOME%\lib\derbyclient.jar;%DERBY_HOME%\lib\derbytools.jar;%DERBY_HOME%\lib\derbynet.jar

2.Check install

java org.apache.derby.tools.sysinfo

3.Test ij

java org.apache.derby.tools.ij

4.Start Server

click server_start.bat

##Add lib to eclipse

1.Java Build Path

Libraries -->  Add Extern JARS

Classpath -->  Path(derby.jar && derbyclient.jar && derbynet.jar && derbytools.jar)
