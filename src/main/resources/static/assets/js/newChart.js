//
//  let datas
//  function fetchSalesReportData() {
//   $.get("/ShowSalesReportWithGraph", function (data) {
//   console.log(data);
//   datas=data
//    });
//
//  }
//  fetchSalesReportData()
//var ctxL = document.getElementById("lineChart2").getContext('2d');
//var myLineChart = new Chart(ctxL, {
//  type: 'line',
//  data: {
//    labels: ["January", "February", "March", "April", "May", "June", "July"],
//    datasets: [{
//      label: "My First dataset",
//      data: [65, 59, 80, 81, 56, 55, 40],
//      backgroundColor: [
//        'rgba(240, 240, 240, .3)',
//      ],
//      borderColor: [
//        'rgba(255,255,255, .7)',
//      ],
//      borderWidth: 1
//    },
//    {
//      label: "My Second dataset",
//      data: [28, 48, 40, 19, 86, 27, 90],
//      backgroundColor: [
//        'rgba(121, 52, 243, .3)',
//      ],
//      borderColor: [
//        'rgba(255, 255, 255, .7)',
//      ],
//      borderWidth: 1
//    }
//    ]
//  },
//  options: {
//    responsive: true,
//    scales: {
//      x: {
//        ticks: {
//          color: 'red' // Change the color of the x-axis labels to red
//        }
//      },
//      y: {
//        ticks: {
//          color: 'blue' // Change the color of the y-axis labels to blue
//        }
//      }
//    }
//  }
//});





function fetchSalesReportData() {
    $.get("/ShowSalesReportWithGraph", function (data) {
        console.log(data);


        // After fetching data, update the chart
        updateChart(data);
    });
}


function updateChart(datas) {
console.log(datas.totalSalesReportForPreviousWeek)
    if (datas) {
        console.log(datas)

      var ctxB = document.getElementById("barChart").getContext('2d');
      var myBarChart = new Chart(ctxB, {
        type: 'bar',
        data: {
          labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
          datasets: [{
            label: '# of Votes',
            data: [12, 19, 3, 5, 2, 3],
            backgroundColor: [
              'rgba(255, 99, 132, 0.2)',
              'rgba(54, 162, 235, 0.2)',
              'rgba(255, 206, 86, 0.2)',
              'rgba(75, 192, 192, 0.2)',
              'rgba(153, 102, 255, 0.2)',
              'rgba(255, 159, 64, 0.2)'
            ],

            borderColor: [
              'rgba(255,99,132,1)',
              'rgba(54, 162, 235, 1)',
              'rgba(255, 206, 86, 1)',
              'rgba(75, 192, 192, 1)',
              'rgba(153, 102, 255, 1)',
              'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1
          }]
        },
        options: {
          scales: {
            yAxes: [{
              ticks: {
                beginAtZero: true
              }
            }]
          }
        }
      });
    }
}

fetchSalesReportData();

