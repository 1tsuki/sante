if (!window.santePieGraph) santePieGraph = {};

(function(w, $){
	// some alias to compress code
	var m = Math, pi = m.PI, cos = m.cos, sin = m.sin, round = m.round;

	// prefix values
	var fontsize = 20;
	var font = 'ＭＳ Ｐゴシック';
	var colormap = ['#F05050','#F05050','#F05050','#F05050','#509650','#509650','#509650','#DCDC00','#DCDC00','#DCDC00','#DCDC00'];
	var auxlineNum = 5;

	// variables
	var c, ctx, width = 220, height = 220, radius, radDiv, radOffset = -pi/2, centerX, centerY;
	var timer = 25, params, items;

	var color = {
		mod: function(color, amount) {
			var usePound = false;
			if ( color[0] == "#" ) {
				color = color.slice(1);
				usePound = true;
			}
			var num = parseInt(color,16);
			var r = (num >> 16) + amount;
			if(255 < r) r = 255;
			else if(r < 0) r = 0;
			var g = ((num >> 8) & 0x00FF) + amount;
			if(255 < g) g = 255;
			else if(g < 0) g = 0;
			var b = (num & 0x0000FF) + amount;
			if(255 < b) b = 255;
			else if(b < 0) b = 0;
			var newColor = b | (g << 8) | (r << 16);

			return (usePound?"#":"") + (b | (g << 8) | (r << 16)).toString(16);
		},

		darken: function(color, factor) {
			var newColor = "rgba(";

			color = color.replace("rgba", "");
			color = color.replace("(", "");
			color = color.replace(")", "");
			colors = color.split(",");

			for(var i=0; i<3; i++) {
				newVar = round(colors[i] * factor);
				if(255 < newVar) newVar = 255;
				newColor += newVar + ",";
			}
			newColor += "1)";

			return newColor;
		},

		lighten: function(color, factor) {
			var newColor = "rgba(";

			color = color.replace("rgba", "");
			color = color.replace("(", "");
			color = color.replace(")", "");
			colors = color.split(",");

			for(var i=0; i<3; i++) {
				newVar = round(colors[i] + factor*50);
				if(newVar < 0) newVar = 0;
				newColor += newVar + ",";
			}
			newColor += "1)";

			return newColor;
		}
	};

	var draw = {
		arc: function(startAngle, endAngle, radius) {
			ctx.beginPath();
			ctx.moveTo(centerX, centerY);
			ctx.arc(centerX, centerY, radius, startAngle, endAngle, false);
			ctx.lineTo(centerX, centerY);
			ctx.closePath();
		},

		fan: function(startAngle, endAngle, innerRadius, length) {
			ctx.beginPath();
			ctx.arc(centerX, centerY, innerRadius, startAngle, endAngle, false);
			ctx.arc(centerX, centerY, innerRadius + length, endAngle, startAngle, true);
			ctx.closePath();
		},

		circle: function(radius) {
			ctx.beginPath();
			ctx.arc(centerX, centerY, radius, 0, pi*2, false);
			ctx.closePath();
		}
	};

	var privateMethods = {
		initSize: function() {
			c.width = width;
			c.height = height;
			radius = (width < height) ? width/2-fontsize : height/2-fontsize;
			radDiv = pi*2/params.length;
			centerX = radius+fontsize;
			centerY = radius+fontsize;
			privateMethods.initContext();
		},

		initContext: function() {
			ctx = c.getContext('2d');
			ctx.textBaseline = 'middle';
			ctx.textAlign = 'center';
			ctx.font = 'bold ' + fontsize + 'px ' + font;
		},

		drawGraph: function() {
			privateMethods.drawPie();
			privateMethods.drawLabel();
			privateMethods.drawItems();
		},

		drawPie: function() {
			// pie
			ctx.fillStyle = "rgba(250, 250, 250, 1)";
			ctx.strokeStyle = "rgba(200, 200, 200, 1)";
			draw.circle(radius);
			ctx.fill();
			ctx.stroke();

			// grid
			for(var i=0; i<params.length; i++) {
				var rad = radDiv*i + radOffset;
				targetX = centerX + cos(rad)*radius;
				targetY = centerY + sin(rad)*radius;
				ctx.beginPath();
				ctx.moveTo(centerX, centerY);
				ctx.lineTo(targetX, targetY);
				ctx.closePath();
				ctx.stroke();
			}
		},

		drawLabel: function() {
			ctx.fillStyle= "rgba(10,10,10,1)";
			ctx.moveTo(centerX + cos(radOffset)*radius, centerY+sin(radOffset)*radius);
			for(var i=0; i<params.length; i++) {
				var rad = radOffset + radDiv*(i+0.5);
				targetX = centerX + cos(rad)*(radius+fontsize/2+3);
				targetY = centerY + sin(rad)*(radius+fontsize/2+3);

				// draw params
				if(i < params.length/4) {
					ctx.fillText(params[i], targetX, targetY);
				} else if(i < params.length*2/4) {
					ctx.fillText(params[i], targetX, targetY);
				} else if(i < params.length*3/4) {
					ctx.fillText(params[i], targetX, targetY);
				} else {
					ctx.fillText(params[i], targetX, targetY);
				}
			}
		},

		drawItems: function() {
			if (typeof items === "undefined" || !items) {
				privateMethods.drawErrorMessage();
			} else {

				for(var i=0; i<items.length; i++) {
					privateMethods.drawItem(items[i], i);
				}
			}
		},

		drawItem: function(item, key) {
			if(isNaN(item) || item <= 0) {
				return;
			}
			setTimeout(privateMethods.drawItemValue, 100, item, key, 0);
		},

		drawItemValue: function(item, key, counter) {
			if (item[key] <= 0 || item*auxlineNum <= counter || auxlineNum <= counter) {
				return;
			}
			var startAngle = radOffset + radDiv*key;
			var endAngle = radOffset + radDiv*(key + 1);
			var length = radius/auxlineNum;
			var innerRadius = length*counter;
			ctx.fillStyle = color.mod(colormap[key], counter*10);
			draw.fan(startAngle, endAngle, innerRadius, length);
			ctx.fill();
			setTimeout(privateMethods.drawItemValue, timer, item, key, ++counter);
		},

		drawErrorMessage: function() {
			ctx.fillStyle= "rgba(10,10,10,1)";
			ctx.fillText("NO DATA", centerX, centerY);
		}
	};

	santePieGraph.interface = {
		init: function(_id, _params, _items) {
			c = $(_id)[0];
			santePieGraph.interface.setParams(_params);
			santePieGraph.interface.setItems(_items);
			privateMethods.initSize();
			privateMethods.initContext();
		},

		draw: function() {
			ctx.clearRect(0, 0, c.width, c.height);
			privateMethods.drawGraph();
		},

		setParams: function(_params) {
			params = _params;
		},

		setItems: function(_items) {
			if (_items) {
				if (_items.length != params.length) {
					console.log("Invalid item length");
				}
			}
			items = _items;
		},

		setOffsetAngle: function(_offsetAngle) {
			radOffset = _offsetAngle*pi/180-pi/2;
		},

		setSize: function(_width, _height) {
			width = _width;
			height = _height;
			privateMethods.initSize();
		},

		setFont: function(_font) {
			font = _font;
			privateMethods.initContext();
		},

		setFontsize: function(_fontsize) {
			fontsize = _fontsize;
			privateMethods.initContext();
		},

		setAuxlineNum: function(_auxlineNum) {
			auxlineNum = _auxlineNum;
		},

		setTimer: function(_timer) {
			timer = _timer;
		}
	};
})(window, jQuery);