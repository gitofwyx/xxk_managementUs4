/**
 * 
 * 一个显示、修改、新增的的窗口基类
 * 
 */
Ext.define('app.view.module.window.BaseWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.basewindow',

			uses : ['app.view.module.form.BaseForm'],

			layout : 'fit',
			maximizable : true,
			closeAction : 'hide',

			bodyStyle : 'padding : 2px 2px 0',
			shadowOffset : 30,
			layout : 'fit',

			initComponent : function() {
				this.maxHeight = document.body.clientHeight * 0.98;

				var me = this;

				this.formScheme = this.getViewModel().get('tf_formSchemes')[0]; // 取得第一个form方案
				console.log(this.formScheme);
				this.title = this.getViewModel().get('tf_title');
				this.glyph = this.getViewModel().get('tf_glyph');

				var w = this.formScheme.tf_windowWidth;
				var h = this.formScheme.tf_windowHeight;
				// 高度为-1表示是自适应
				if (w == -1 && h == -1) {
					this.width = 600;
					this.height = 400;
					this.maximized = true;
				} else {
					if (w != -1)
						this.width = Math.min(w, document.body.clientWidth - 2);
					if (h != -1)
						this.height = Math.min(h, document.body.clientHeight - 2);
				};
				if (w == -1 && h != -1) { // 宽度最大化
					this.width = document.body.clientWidth - 40;
				}
				this.tools = [{
							type : 'collapse',
							tooltip : '当前记录导出至Excel'
						}];

				this.items = [{
							xtype : 'baseform',
							viewModel : this.getViewModel(),
							formScheme : this.formScheme
						}]
				this.callParent(arguments);
			}

		});