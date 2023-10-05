


function fetchSalesReportData() {
    $.get("/ShowSalesReportWithGraph", function (data) {
        console.log(data);


        // After fetching data, update the chart
        updateChart(data);
    });
}


function updateChart(data) {
console.log(data.totalSalesReportForPreviousWeek)
    if (data) {


      var ctxB = document.getElementById("barChart").getContext('2d');
      var myBarChart = new Chart(ctxB, {
        type: 'bar',
        data: {
          labels: ["Week", "Month", "Year", ],
          datasets: [{
            label: '# of Votes',
            data: [data.totalSalesReportForPreviousWeek,  data.totalGenerateMonthlySalesReport,  data.totalGenerateYearlySalesReport ],
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

