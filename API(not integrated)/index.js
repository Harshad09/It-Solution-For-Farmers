/*var http = require('http');


http.createServer((req, res) => {
    res.write("Welcome back");
    res.end();
}).listen(8080)*/
let port = process.env.PORT || 9000;
const express = require("express");

const app = express();

app.get('/', function(req, res) {
    //res.send("hello world"); 
    var para = new Array(5);
    for(let i=0; i<5; i++){
        para[i] = new Array(3);
        para[i][0] = Math.random() * 100;
        para[i][1] = ((Math.random() * 100)%14);
        para[i][2] = Math.random() * 100;
    }
    const value = {
        "orange" : {
            "temperature" : para[0][0],
            "pH" : para[0][1],
            "moister" : para[0][2]
        },
        "graphs" : {
            "temperature" : para[1][0],
            "pH" : para[1][1],
            "moister" : para[1][2]
        },
        "apple" : {
            "temperature" : para[2][0],
            "pH" : para[2][1],
            "moister" : para[2][2]
        },
        "tomato" : {
            "temperature" : para[3][0],
            "pH" : para[3][1],
            "moister" : para[3][2]
        },
        "carrot" : {
            "temperature" : para[4][0],
            "pH" : para[4][1],
            "moister" : para[4][2]
        }
    };
    res.send(value);
});

//let a = 

/*app.get('/aditya', function(req, res) {
    res.send("hello Aditya");
});*/

/*app.get('/aditya/:id', function(req, res) {
    let id = req.params.id;
    res.send("hello Aditya : " + id);
});

app.get('/aditya', function(req, res) {
    let id = req.query.id;
    res.send("hello Aditya : " + id);
});*/

app.listen(port, (req, res) => {
    console.log("running on port 9000...");
})