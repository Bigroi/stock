function buildChart(containerName, dataUrl, params) {
	$.post(dataUrl, params, function(answer){
		if (answer.result > 0){
			Plotly.newPlot(containerName, answer.data, getLayout(), {displayModeBar: false});
		} else {
			alert(answer.message);
		}
	}, "json");
	
	function getLayout(){
		return {
			title : translate('label.tradeOffers.graph_title'),
			xaxis : {
				title : translate('label.tradeOffers.volume')
			},
			yaxis : {
				title : translate('label.tradeOffers.price')
			},
			showlegend : false,
			margin : {
				l : 40,
				r : 0,
				b : 40,
				t : 50,
				pad : 0
			}
		};
		
	}
}