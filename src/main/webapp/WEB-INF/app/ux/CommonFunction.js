
/**
 * 
 */

Ext.onReady(function() {

			Ext.toastInfo = function(title, text, config) {
				var param = {
					title : title,
					html : text,
					bodyStyle : 'background-color:#7bbfea;',
					header : {
						border : 1,
						style : {
							borderColor : '#9b95c9'
						}
					},
					border : true,
					style : {
						borderColor : '#9b95c9'
					},
					saveDelay : 10,
					align : 'tr',
					closable : true,
					minWidth : 200,
					useXAxis : true,
					slideInDuration : 300
				};
				Ext.apply(param, config);
				Ext.toast(param);
			}

		});
