/**
 * Grid 的一些操作的控制，包括grid中的金额字段的金额单位，是否自动调整列宽，是否自动选中
 * 
 * 
 */
Ext.define('app.view.main.controller.GridController', {
			extend : 'Ext.Mixin',

			init : function() {
				Ext.log('GridController init');
				var vm = this.getView().getViewModel();
				// 绑定金额单位修改过后需要去执行的程序
				vm.bind('{monetary.value}', 'onMonetaryChange', this);
				vm.bind('{autocolumnmode.value}', 'onAutoColumnModeChange', this);
				vm.bind('{monetaryposition.value}', 'onMoneraryPositionChange', this);
			},

			// grid列自动刷新方式
			onAutoColumnModeChange : function(value) {
				Ext.log({
							level : 'info',
							msg : '列宽自动适应方式:' + value
						});

				if (this.getView().down('settingmenu').getMenu().isVisible()) {
					Ext.toastInfo('信息', '列表列宽自动适应方式：' + value, {
								align : 'tl'
							});
				}
			},

			onMoneraryPositionChange : function(value) {

				Ext.log({
							level : 'info',
							msg : '金额单位显示位置:' + value
						});
				if (this.getView().down('settingmenu').getMenu().isVisible())
					Ext.toastInfo('信息', '金额单位显示位置：' + value, {
								align : 'tl'
							});

				Ext.monetaryPosition = value;
				Ext.monetaryText = Ext.monetaryPosition === 'behindnumber'
						? Ext.monetary.monetaryText
						: ''; // 设置当前的全局的金额单位

				Ext.each(this.getView().query('modulegrid'), function(grid) {
							if (grid.rendered) {
								grid.getView().refresh();
								Ext.Array.forEach(grid.columnManager.getColumns(), function(
												column) {
											// 如果可以改变大小，并且是金额字段，则在改变了金额单位以后，自动调整一下列宽
											if (column.fieldDefine.tf_isCurrency) {
												if (Ext.monetaryPosition === 'behindnumber') {
													var brpos = column.text.lastIndexOf('<br/>')
													if (brpos != -1)
														column.setText(column.text.substr(0, brpos));
												} else {
													column.setText(column.text + '<br/>('
															+ Ext.monetary.unitText + ')')
												}

												if (!column.resizeDisabled && column.fieldDefine) {
													column.autoSize();
												}
											}
										})
							}
						});

			},

			// 金额单位修改过后执行
			onMonetaryChange : function(value) {
				Ext.log({
							level : 'info',
							msg : '金额单位变更:' + value
						});
				if (this.getView().down('settingmenu').getMenu().isVisible())
					Ext.toastInfo('信息', '列表金额单位：'
									+ app.view.main.menu.Monetary.getMonetary(value).unitText, {
								align : 'tl'
							});

				var m = app.view.main.menu.Monetary.getMonetary(value);
				Ext.monetary = m;
				Ext.monetaryText = Ext.monetaryPosition === 'behindnumber'
						? Ext.monetary.monetaryText
						: ''; // 设置当前的全局的金额单位
				Ext.monetaryUnit = m.monetaryUnit;
				Ext.each(this.getView().query('modulegrid'), function(grid) {
							if (grid.rendered) {
								grid.getView().refresh();
								Ext.Array.forEach(grid.columnManager.getColumns(), function(
												column) {
											// 如果可以改变大小，并且是金额字段，则在改变了金额单位以后，自动调整一下列宽
											if (column.fieldDefine.tf_isCurrency) {

												if (Ext.monetaryPosition !== 'behindnumber') {
													var text = column.text;
													var brpos = text.lastIndexOf('<br/>')
													if (brpos != -1)
														text = text.substr(0, brpos);

													column.setText(text + '<br/>('
															+ Ext.monetary.unitText + ')')
												}

												if (!column.resizeDisabled && column.fieldDefine) {
													column.autoSize();
												}
											}
										})
							}
						});
			}
		});
