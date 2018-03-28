/**
 * 模块的控制器
 */

Ext.define('app.view.module.ModuleController', {
	extend : 'Ext.app.ViewController',

	requires : ['Ext.MessageBox', 'Ext.window.Toast'],

	alias : 'controller.module',

	mixins : ['app.view.module.controller.ToolbarController'],
	
	init : function() {
		console.log('modulecontroller.init')
	},

	// 用户修改了grid列表方案后执行
	gridSchemeChange : function(combo, schemeId) {
		console.log(schemeId);
		this.getView().down('modulegrid').changeGridScheme(schemeId)

	},

	// 选中的记录发生变化过后的事件
	selectionChange : function(model, selected, eOpts) {
		// 设置删除按钮的状态
		this.getView().down('toolbar button#delete')[selected.length > 0
				? 'enable'
				: 'disable']();

		var viewModel = this.getView().getViewModel();
		// 下面将组织选中的记录的name显示在title上，有二种方案可供选择，一种是用下面的MVVM特性，第二种是调用refreshTitle()
		var selectedNames = ''
		if (selected.length > 0) {
			if (!!selected[0].getNameValue())
				selectedNames = selectedNames + '　『<em>' + selected[0].getNameValue()
						+ '</em>'
						+ (selected.length > 1 ? ' 等' + selected.length + '条' : '') + '』';
		}
		viewModel.set('selectedNames', selectedNames); // 修改ModuleModel中的数据，修改好后会自动更新bind的title
		// this.getView().down('grid').refreshTitle(); // 这是不用MVVM特性的做法
	}


})