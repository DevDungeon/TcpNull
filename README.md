TCP Null
==================

TCP server for debugging and testing. Discard incoming data or 
echo it back to client raw or as an HTTP response.

![Screenshot of main window](screenshots/mainWindow.png)

Features
-------
* Specify what interface and port to listen on
* Choose one of three actions for an incoming request:
  * Discard incoming data
  * Echo data back to client exactly as it was received
  * Echo data back to client in the form of an HTTP 200 OK response


Download the JAR
----------------
* [TCP Null from DevDungeon.com](http://www.devdungeon.com/content/tcp-null)


Source Code
-----------
* [TcpNull (GitHub.com)](https://www.github.com/DevDungeon/TcpNull)

Running
-------
* Ensure Java 7 or greater is installed
* Double click the .jar file or run at the command line with:

`java -jar TcpNull-1.0.jar`

Contact
-------
NanoDano <nanodano@devdungeon.com>

License
-------
GNU General Public License, version 2 (See LICENSE.txt)

Changelog
---------
v1.0 - 2016/10/20 - Initial stable release.
