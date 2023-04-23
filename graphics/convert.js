const fs = require('fs');

fs.readFile('./json/results-20-open-10.json', 'utf8', (error, data) => {
    if(error){
        console.log(error);
        return;
    }
    var json = JSON.parse(data);
    console.log(json.results[0]);
    console.log(json.results[0]['mean']);
    console.log(json.results[0]['stddev']);
    console.log(json.results[0]['median']);
    console.log(json.results[0]['min']);
    console.log(json.results[0]['max']);
    console.log(json.results[0]['parameters']['VERSION']);

    {
        x: [10, 20, 50, 100, 200, 500, 1000, 2000, 2200, 2500, 3000, 4000, 5000, 10000],
            y: [0.7420601534000001, 0.7640007534800001, 0.81946599288, 0.8937391089799999, 1.0284342309199999, 1.4013982057999999, 2.1349315815800005,4.2474244064999995,4.78231999948,5.663590673300002,7.31009161026,11.47948534344,16.81981757284,61.39236260642001],
        error_y: {
        type: 'data',
            symmetric: false,
            array: [0.1, 0.2, 0.1, 0.1, 0.2, 0.3],
            arrayminus: [0.2, 0.4, 1, 0.2, 0.01, 0.15],
    },
        type: 'scatter',
            name: 'MVN 3.1.1'
    },

})
