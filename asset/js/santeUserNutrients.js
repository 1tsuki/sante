if (!window.santeUserNutrients) santeUserNutrients = {};

(function(w, $) {
	var weekAgo = 0;
	var privateMethods = {
		checkValue: function(arg) {
			if (!arg) {
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
		showDate: function() {
			var now = new Date();
			var tmp = new Date();
			tmp.setTime(now.getTime() - 86400000 * weekAgo * 7);
			var first = tmp.getDate() - tmp.getDay();
			var last = first + 6;
			var firstday = new Date(tmp.setDate(first));
			var lastday = new Date(tmp.setDate(last));

			var dayString = firstday.getMonth() + "/" + firstday.getDate() + "(日)<br>〜" + lastday.getMonth() + "/" + lastday.getDate() + "(土)";
			$('.today').html(dayString);
		},

		update: function(_weekAgo) {
			weekAgo = (typeof _weekAgo === 'undefined' || _weekAgo < 0) ? 0 : _weekAgo;
			santeUserNutrients.interface.showDate();

			$.ajax({
				url: "/sante/api/user/GetChartSource",
				data: {weekAgo: weekAgo},
				async: true,
				dataType: "json",
				success: function(data) {
					if (data.status.success == "false") {
						alert("栄養摂取状態の取得に失敗しました");
					} else {
						// 円グラフの更新
						santePieGraph.interface.setItems(data.result.items);
						santePieGraph.interface.draw();

						// fillMaterialsの更新
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
		},

		addNutrient: function(id, amount) {
			$.ajax({
				url: "/sante/api/user/AddNutrient",
				data: {nutrient_id: id, amount: amount},
				async: true,
				dataType: "json",
				success: function(data) {
					if (data.status.success == "false") {
						alert("栄養摂取状態の更新に失敗しました");
					} else {
						santeUserNutrients.interface.update(weekAgo);
						santeUserStats.interface.update();
					}
				},
				error: function(data) {
					santeUserNutrients.interface.update(weekAgo);
				}
			});
		}
	};
})(window, jQuery);
