# EDGE Project Management

EDGE PMT V2 is based on Node-Webkit, Angular, Grunt & Google's Material Design Spec.
The former version was based on Java, JavaFX, Hibernate & Maven.

I rewrote the project because, imho, the world needs a cross-platform Project Management Tool. 
Also the old sourcecode lacked good dependency management, docs and readability.

## Dependencies

You'll need the following dependencies to build EDGE Project Management for yourself:

* node + npm (http://nodejs.org/)[http://node]
* bower (install using npm: npm install -g bower)
* grunt + grunt-cli (install using npm: npm -g install grunt)
* nwjs (npm install -g nw (_this may take some time tho..._)) 

Now it's time to install the needed dependencies. Fortunately NPM & bower solve this fairly easy:
These instructions only apply for Linux and MacOSX, for Windows you have to convert the NPM & Bower commands to the corresponding windows format.

```shell
	cd app/ && npm install . && cd -
	bower install .
```

# Build EDGE PMT

Building EDGE PMT is easy. Just create a ZIP file of all files in the app/ folder.
On Linux and MacOSX you can use the __zip command line tool__

```shell
zip -r app.zip app/*
```

On Windows you can use one of the dozens GUI Zip Tools (WinRar/WinZip/7zip will work fine).

# Running EDGE PMT:
On Linux and MacOSX you have to run the following command: `nw ./app.zip`
On Windows, all you have to do is drag-and-drop the ZIP package to the NWJS.exe (Download NWJS.exe from NWJS.io).

# Standalone Package
If you want to distribute EDGE PMT, you may consider including node-webkit and the app.zip file:
On Linux and MACOSX you can use `cat /path/to/nw ./app.zip > "EDGE PMT"
On Windows you have to use the __copy__ command from the commandline: `copy /b nw.exe+app.zip "EDGE PMT.exe"

# Contribute
You can contribute by forking this repo & commiting changes as a pull-request.
Also consider opening issues on GitHub if you encounter any (I hope you won't encounter any...)

# Copyright
Since this project started as a school project I'll continue releasing it under the MIT license.
You can use the project for your own but you have to consider putting a link to this REPO somewhere in your app/homepage.