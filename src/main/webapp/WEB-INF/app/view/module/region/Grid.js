/**
 * 模块数据的主显示区域，继承自Grid
 */

Ext.define('app.view.module.region.Grid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.modulegrid',
	uses : ['app.view.module.region.GridToolbar',
			'app.view.module.factory.ColumnsFactory',
			'app.view.module.widget.GridSchemeCombo'],

	requires : ['Ext.selection.CellModel', 'Ext.grid.*', 'Ext.data.*',
			'Ext.util.*'],

	bind : {
		title : '{tf_title} {selectedNames}', // 数据绑定到ModuleModel中的tf_title和选中记录的名称
		gridSchemeId : '{gridSchemeId}' // 属性gridSchemeId
		// 设置绑定，和GridSchemeCombo是value绑定是一样的
		// ＊＊＊＊＊＊＊＊＊＊＊＊这里有个问题，就是所有的模块都绑定了相同的gridSchemeId,以后解决
	},

	tools : [{
				type : 'gear'
			}],

	columnLines : true, // 加上表格线
	multiSelect : true,

	enableLocking : true, // 使grid可以锁定列

	listeners : {
		selectionChange : 'selectionChange'
	},

	initComponent : function() {

		var viewModel = this.up('modulepanel').getViewModel();
		this.store.modulegrid = this;

		// 可以在grid中进行行编辑的设置
		this.rowEditing = new Ext.grid.plugin.RowEditing({
					clicksToEdit : 2
				});
		this.plugins = [this.rowEditing];
		this.selType = 'rowmodel';
		this.on('edit', function(editor, e) {
					// 每一行编辑完保存之后，都提交数据
					e.grid.getStore().sync({
								callback : function() {
									 e.record.commit();
								}
							});
				});

		this.viewConfig = {

			stripeRows : true, // 奇偶行不同底色
			enableTextSelection : false, // 不允许选择行中的text数据
			// 加入允许拖动功能
			plugins : [{
				ptype : 'gridviewdragdrop',
				ddGroup : 'DD_grid_' + viewModel.get('tf_moduleName'), // 拖动分组必须设置，这个分组名称为:DD_grid_Globbal
				enableDrop : false
					// 设为false，不允许在本grid中拖动
				}]

		};

		// 创建grid列
		// 默认第一个grid方案
		this.gridSchemeId = viewModel.get('tf_gridSchemes')[0].tf_schemeOrder;
		// 将第一个方案的columns生成，第一个方案是要先设置好，并不是gridschemecombo触发来生成的
		this.columns = app.view.module.factory.ColumnsFactory.getColumns(viewModel);

		this.dockedItems = [{
					xtype : 'gridtoolbar', // 按钮toolbar
					dock : 'top',
					grid : this
				}, {
					xtype : 'pagingtoolbar', // grid数据分页
					store : this.store,
					displayInfo : true,
					prependButtons : true,
					dock : 'bottom',
					items : [{ // 在最前面加入grid方案的选择Combo
						xtype : 'gridschemecombo'
					}]
				}];

		this.callParent();
	},

	/**
	 * 在选中的记录发生变化时，修改当前title，这是不用MVVM特性的做法
	 */
	refreshTitle : function() {
		var viewModel = this.up('modulepanel').getViewModel();
		var selected = this.getSelectionModel().getSelection();
		var title = viewModel.get('tf_title');
		if (selected.length > 0) {
			if (!!selected[0].getNameValue())
				title = title + '　〖<em>' + selected[0].getNameValue() + '</em>'
						+ (selected.length > 1 ? ' 等' + selected.length + '条' : '') + '〗';
		}
		this.setTitle(title);
	},

	/**
	 * 重新适应所有列的宽度
	 */
	columnAutoSize : function() {
		Ext.Array.forEach(this.columnManager.getColumns(), function(column) {
					if (!column.autoSizeDisabled) {
						column.autoSize();
					}
				})
	},

	/**
	 * 重新选择了列表方案之后，替换方案中的字段
	 */
	setGridSchemeId : function(value) {
		if (this.gridSchemeId != value) {
			this.gridSchemeId = value;
			Ext.suspendLayouts();
			this.columns = app.view.module.factory.ColumnsFactory.getColumns(this
							.up('modulepanel').getViewModel(), value);
			this.reconfigure(this.store, this.columns);
			Ext.resumeLayouts(true);
			this.columnAutoSize();
		}
	}

})
