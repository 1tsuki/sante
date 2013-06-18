if (!window.santeUserNutrients) santeUserNutrients = {};

(function(w, $) {
	var privateMethods = {
		"checkValue": function(arg) {
			if (arg == null) {
				return 1;
			}
			if (arg < 0) {
				return 0;
			}
			if (1 < arg) {
				return 1;
			}
			return arg;
		}
	};
	santeUserNutrients.interface = {
		"exec": function(week) {
            if (!week) week = 0;
            var now = new Date;
            var tmp = new Date();
            tmp.setTime(now.getTime() - 86400000 * week * 7);
			var first = tmp.getDate() - tmp.getDay();
			var last = first + 6;
			var firstday = new Date(tmp.setDate(first));
			var lastday = new Date(tmp.setDate(last));
			
			var dayString = firstday.getMonth() + "/" + firstday.getDate() + " - " + lastday.getMonth() + "/" + lastday.getDate();
			$('.today').html(dayString);
			
			$.ajax({
				url: "http://localhost:8080/sante/api/user/GetChartSource",
				data: "week=" + week,
				async: true,
				dataType: "json",
				success: function(data) {
					if (data.status.success == "false") {
						alert("栄養摂取状態の取得に失敗しました");
					} else {
						santePieGraph.interface.setItems(data.result.items);
						santePieGraph.interface.draw();
						
						var keys = ["#milk", "#egg", "#meat", "#bean", "#veg", "#fruit", "#mineral", "#crop", "#potato", "#fat", "#sugar"];
						var colors = ["red", "red", "red", "red", "green", "green", "green", "yellow", "yellow", "yellow", "yellow"];
						santeFillMaterials.interface.fill(keys, data.result.items, colors);
					}
				},
				error: function(data) {
				    santePieGraph.interface.setItems(null);
				    santePieGraph.interface.draw();
				}
			});
		}
	};
})(window, jQuery);
