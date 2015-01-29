# EDGE Project Management

EDGE PMT V2 is based on Node-Webkit, Angular, Grunt & Google's Material Design Spec.
The former version was based on Java, JavaFX, Hibernate & Maven.

I rewrote the project because, imho, the world needs a cross-platform Project Management Tool. 
Also the old sourcecode lacked good dependency management, docs and readability.

## Build instructions

You'll need the following dependencies to build EDGE Project Management for yourself:

* node + npm (http://nodejs.org/)[http://node]
* bower (install using npm: npm install bower)
* grunt + grunt-cli (install using npm: npm install grunt)

Now it's time to install the needed dependencies. Fortunately NPM & bower solve this fairly easy:

```shell
	npm install .
	bower install .
```

Next thing you want to do is to compile the source code using nwjs (_if you run a different OS than MacOS, you should install NWJS program which corresponds with your OS).
For NWJS downloads check: (NWJS.io)[http://nwjs.io/]

