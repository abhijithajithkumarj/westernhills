



     let datas;

    function fetchSalesReportDataPie(){
    $.get("/ShowSalesReportWithGraph", function (data){

    updateSalesReportPie(data);


    })



   }


  function updateSalesReportPie(data){


  var ctxD = document.getElementById("doughnutChart").getContext('2d');
  var myLineChart = new Chart(ctxD, {
    type: 'doughnut',
    data: {
      labels: ["Week", "Month", "Year", ],
      datasets: [{
        data: [data.totalSalesReportForPreviousWeek,  data.totalGenerateMonthlySalesReport , data.totalGenerateYearlySalesReport,],
        backgroundColor: ["#F0F0F0", "#46BFBD", "#FDB45C", ],
        hoverBackgroundColor: ["#F0F0FF", "#5AD3D1", "#FFC870"]
      }]
    },
    options: {
      responsive: true
    }
  });
  }
   fetchSalesReportDataPie()