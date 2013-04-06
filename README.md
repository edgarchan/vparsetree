# Graphical Parse Tree

Simple application that creates a graphical representation of the parse tree generated with the [parboiled](http://parboiled.org/) parsing library. It uses
the [Simple Calculator](https://github.com/sirthias/parboiled/wiki/Simple-Calculator) example grammar provided with parboiled but
should be easy to add a new one.

The web stuff is powered with [vert.x](http://vertx.io/) and [D3.js](http://d3js.org/).

## How to use it

 ```
 1. Run gradlew with runMod option
 2. Open your browser and point to localhost:8080
 3. Type an arithmetic expression to create the tree
 ```
