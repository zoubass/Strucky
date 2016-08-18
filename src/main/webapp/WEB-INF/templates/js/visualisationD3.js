/**
 * Created by t922274 on 16.8.2016.
 */
function showGraph() {
    var width = 800, height = 600;
    console.log("here")
    var force = d3.layout.force()
        .charge(-200).linkDistance(30).size([width, height]);

    var svg = d3.select("#graph").append("svg")
        .attr("width", "100%").attr("height", "100%")
        .attr("pointer-events", "all");
    d3.json("/application/graph", visualise);

    function visualise(error, graph) {

        if (error) return;

        force.nodes(graph.nodes).links(graph.links).start();

        var link = svg.selectAll(".link")
            .data(graph.links).enter()
            .append("line").attr("class", function (d) {
                return "link " + d.type
            });

        link.append("type").attr("class", "type").text(function (d) {
            return d.type
        })

        link.append("count").attr("class", "type").text(function (d) {
            return d.count
        })

        var node = svg.selectAll(".node")
            .data(graph.nodes).enter()
            .append("circle")
            .attr("class", function (d) {
                return "node " + d.label
            })
            .attr("r", 10)
            .call(force.drag);


        // html title attribute
        node.append("title").attr("class", "title")
            .text(function (d) {
                return d.title;
            })


        // force feed algo ticks
        force.on("tick", function () {
            link.attr("x1", function (d) {
                    return d.source.x;
                })
                .attr("y1", function (d) {
                    return d.source.y;
                })
                .attr("x2", function (d) {
                    return d.target.x;
                })
                .attr("y2", function (d) {
                    return d.target.y;
                });

            node.attr("cx", function (d) {
                    return d.x;
                })
                .attr("cy", function (d) {
                    return d.y;
                });

//            title.attr("cx", function (d) {
//                        return d.x;
//                    })
//                    .attr("cy", function (d) {
//                        return d.y;
//                    });
//            type.attr("cx", function (d) {
//                        return d.x;
//                    })
//                    .attr("cy", function (d) {
//                        return d.y;
//                    });
        });
}
//function showApplicationGraph(appName) {
//    var width = 800, height = 600;
//
//    var force = d3.layout.force()
//        .charge(-200).linkDistance(30).size([width, height]);
//
//    var svg = d3.select("#graph").append("svg")
//        .attr("width", "100%").attr("height", "100%")
//        .attr("pointer-events", "all");
//
//    d3.json("/application/".concat(appName), visualise);
//}

}