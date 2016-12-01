/**
 * Created by t922274 on 16.8.2016.
 */
//function showGraph() {
var graph = document.getElementById("graph");
wipe(graph)
var width = 800, height = 600;
var force = d3.layout.force()
    .charge(-200).linkDistance(30).size([width, height]);

var svg = d3.select("#graph").append("svg")
    .attr("width", "100%").attr("height", "100%")
    .attr("pointer-events", "all");
var URL = document.URL;
d3.json(URL+"application/graph", visualise);

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
        .attr("r", function (d) {
            return d.label == ("application") ? 15 : 10;
        }).on('click', showDetail)
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
    });
    //}
}


/** move me to the toolbar js**/
function showDetail(circle) {
    var consumedBody = document.getElementById("consumedTbody");

    //remove previously created rows
    // while (consumedBody.firstChild) {
    //     consumedBody.removeChild(consumedBody.firstChild);
    // }
    wipe(consumedBody);
    d3.json(URL+"application/" + circle.title + "/info/", showInfo);

    function showInfo(app) {
        if (app != null) {
            document.getElementById("appName").innerHTML = app.name;

            //if (app.consumeRelationship. 0) {
            try {
                app.consumeRelationship.forEach(function (rel) {
                    var tr = document.createElement('tr');
                    var td1 = document.createElement('td');
                    var td2 = document.createElement('td');
                    var td3 = document.createElement('td');

                    td1.innerHTML = rel.method.name;
                    td2.innerHTML = rel.method.version;
                    td3.innerHTML = rel.totalUsage;

                    tr.appendChild(td1);
                    tr.appendChild(td2);
                    tr.appendChild(td3);
                    tr.style.color = "#00d3ff"

                    consumedBody.appendChild(tr);
                });
            } catch (err) {

            }

            //if (app.providedMethods.length() > 0) {
            app.providedMethods.forEach(function (method) {
                var tr = document.createElement('tr');
                var td1 = document.createElement('td');
                var td2 = document.createElement('td');
                var td3 = document.createElement('td');

                td1.innerHTML = method.name;
                td2.innerHTML = method.version;
                td3.innerHTML = "poskytovana";

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                if (method.version == 120) tr.style.color = "#03510d";
                if (method.version == 110) tr.style.color = "#0fa106";
                if (method.version == 100) tr.style.color = "#b6ff36";
                if (method.version == 0) tr.style.color = "#e4e218";
                consumedBody.appendChild(tr);
            });
        }

    }
}

function wipe(node) {
    if (node != null) {
        while (node.firstChild) {
            node.removeChild(node.firstChild);
        }
    }
}