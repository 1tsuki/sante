if(!window.santeFillMaterials) santeFillMaterials = {};

(function(w, $){
	var parentDiv;

	santeFillMaterials.interface = {
		fill: function(keys, values, colors) {
			if(keys.length != values.length) return;
			if(values.length != colors.length) return;

			for(var i=0; i<keys.length; i++) {
				if(values[i] < 0.45) $(keys[i]).addClass("material-" + colors[i] + "-dark");
			}
		}
	};
})(window, $);

