if(!window.santeUserStats) santeUserStats = {};

(function(w, $) {
	santeUserStats.interface = {
		"exec": function() {
			$.ajax({
				url: "http://localhost/sante/api/user/GetStats",
				async: true,
				dataType: "json",
				success: function(data) {
					if (data.status.success == "false") {
						alert("調理実績の取得に失敗しました");
					} else {
						$("dd#nutrients-balance").html(data.result.stats.nutrientsBalance + "%");
						$("dd#total-cooked").html(data.result.stats.totalCooked + "回");
						$("dd#consecutively-cooked").html(data.result.stats.consecutivelyCooked + "日");
					}
				},
				error: function(data) {
					alert("調理実績の取得に失敗しました");
				}
			});
		}
	}
})(window, jQuery);

$(document).ready(function() {
	santeUserStats.interface.exec();
});