/**
 * 一个模块的主控界面的容器，用来安放各个模块控件以及协调他们之间的关系
 */
Ext.define('app.view.module.Module', {
			extend : 'Ext.panel.Panel',

			alias : 'widget.modulepanel',

			requires : ['app.view.module.ModuleController',
					'app.view.module.ModuleModel', 'app.view.module.factory.ModelFactory'],

			uses : ['app.view.module.region.Navigate', 'app.view.module.region.Grid',
					'app.view.module.region.Detail', 'app.view.module.window.BaseWindow'],
			referenceHolder : true,

			controller : 'module',
			// MVVM架构的控制器的名称，main控制器会自动加载，这个控制器不会自动加载，需要在requires中指定，不知道是为什么
			// 这个配置去掉，把相应的设置放在initComponent里面
			// viewModel : {
			// type : 'module'
			// },
			bind : {
				// glyph : '{tf_glyph}', // 这一个绑定是无效的，在tabPanel渲染过后，再修改这个值，将不会有任何效果。
				title : '{tf_title}' // 这个绑定是有效的，可以根据ModuleModel中的值来设置title
			},
			layout : 'border', // 模块采用border布局

			initComponent : function() {
				console.log(this.moduleName + ' 正在创建');
				// 从MainModel中取得当前模块的定义数据，包括字段和各种设置的信息

				var mainmodel = this.up('app-main').getViewModel();
				this.module = mainmodel.getModuleDefine(this.moduleName);
				var viewmodel = new Ext.create('app.view.module.ModuleModel', {
							// 将该模块的定义信息传递给本模块的viewModel
							module : this.module
						});
				this.setViewModel(viewmodel);

				this.glyph = this.getViewModel().get('tf_glyph'); // 由于上面的glyph的bind无效，因此需要在这里加入glyph的设置
				this.model = app.view.module.factory.ModelFactory.getModelByModule(this.module);
				this.store = Ext.create('app.store.GridStore', {
							module : this.module,
							model : this.model,
							modulePanel : this
						});
				this.items = [{
							xtype : 'navigate', // 导航区域
							region : 'west',
							width : 250,
							collapsible : true,
							collapsed : true,
							split : true
						}, {
							xtype : 'modulegrid', // 模块的grid显示区域
							region : 'center',
							store : this.store
						}, {
							xtype : 'recorddetail', // 记录明细
							region : 'east',
							width : 250,
							collapsible : true, // 可以折叠隐藏
							collapsed : true,
							collapseMode : 'mini', // 折叠陷藏模式
							split : true
							// 可以拖动大小
					}]

				this.callParent();
			}

		})