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
				url: "http://localhost/sante/api/user/GetNutrients",
				async: true,
				dataType: "json",
				success: function(data) {
					if (data.status.success == "false") {
						alert("栄養摂取状態の取得に失敗しました");
					} else {
						var nutrients = data.result.nutrients;
						console.log(nutrients.ingested);
						console.log(nutrients.desired);
						var milk = privateMethods.checkValue(nutrients.ingested.milk / nutrients.desired.milk);
						var egg = privateMethods.checkValue(nutrients.ingested.egg / nutrients.desired.egg);
						var meat = privateMethods.checkValue(nutrients.ingested.meat / nutrients.desired.meat);
						var bean = privateMethods.checkValue(nutrients.ingested.bean / nutrients.desired.bean);
						var vegetable = privateMethods.checkValue(nutrients.ingested.vegetable / nutrients.desired.vegetable);
						var potato = privateMethods.checkValue(nutrients.ingested.potato / nutrients.desired.potato);
						var fruit = privateMethods.checkValue(nutrients.ingested.fruit / nutrients.desired.fruit);
						var mineral = privateMethods.checkValue(nutrients.ingested.mineral / nutrients.desired.mineral);
						var crop = privateMethods.checkValue(nutrients.ingested.crop / nutrients.desired.crop);
						var fat = privateMethods.checkValue(nutrients.ingested.fat / nutrients.desired.fat);
						var suguar = privateMethods.checkValue(nutrients.ingested.suguar / nutrients.desired.suguar);
						var items = [["摂取量", milk, egg, meat, bean, vegetable, potato, fruit, mineral, crop, fat, suguar]];
						console.log(items);
						santePieGraph.interface.setItems(items);
						santePieGraph.interface.draw();
					}
				}
			});
		}
	};
})(window, jQuery);
