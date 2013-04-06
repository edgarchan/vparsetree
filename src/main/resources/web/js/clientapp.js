/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



var ClusterGraph = (function(){
	
	var width = 1260, height = 2200;

	var cluster = d3.layout.cluster()
	    .size([height, width - 160]);

	var diagonal = d3.svg.diagonal()
	    .projection(function(d) { return [d.y, d.x]; });

	var svg = d3.select("#graph").append("svg")
	    .attr("width", width)
	    .attr("height", height)
	    .append("g")
	    .attr("transform", "translate(40,0)");

	return {
	
      loadInfo : function(root) {
						  var nodes = cluster.nodes(root),
						      links = cluster.links(nodes);
						
						  var link = svg.selectAll(".link")
						      .data(links)
						      .enter().append("path")
						      .attr("class", "link")
						      .attr("d", diagonal);
						
						  var node = svg.selectAll(".node")
						      .data(nodes)
						      .enter().append("g")
						      .attr("class", "node")
						      .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; });
						
						  node.append("circle")
						      .attr("r", 6);
						
						  node.append("text")
						      .attr("dx", function(d) { return d.children ? -8 : 8; })
						      .attr("dy", 3)
						      .style("text-anchor", function(d) { return d.children ? "end" : "start"; })
						      .text(function(d) { return d.name; });
					},
					
	  clearInfo : function(){
		  				svg.selectAll("text").remove();
		  				svg.selectAll("circle").remove();
		  				svg.selectAll(".link").remove();
		  				svg.selectAll(".node").remove();
	              }
    };

	
}());


(function ClientApp(graph) {

  var app = this;
  
  var eb = new vertx.EventBus(window.location.protocol + '//' + window.location.hostname + ':' + window.location.port + '/eventbus');

  app.info =  function(){
    var self = this;
    self.tag = ko.observable("");
    self.enviar = function(){
	  app.sendInfo( self );    	
    };
  };
     
   
  app.sendInfo = function( toSend ){     
      var jsinf = ko.toJS(toSend);
      var appmsg = {
        action: "incoming",
        document: jsinf
      };
      
      eb.send(
        'acme.service',
        appmsg, 
        function(reply) {
            app.editable = true;           
            console.log(  JSON.stringify(reply) );
            graph.clearInfo();
            graph.loadInfo(reply);
        }
      );     
   };
  
  
  eb.onopen = function() {   
    ko.applyBindings(new app.info());  
  };

  eb.onclose = function() {
    eb = null;
  };

  
})(ClusterGraph);