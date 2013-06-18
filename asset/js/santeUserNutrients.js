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
		"exec": function() {
			$.ajax({
				url: "http://localhost:8080/sante/api/user/GetChartSource",
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
				}
			});
		}
	};
})(window, jQuery);
