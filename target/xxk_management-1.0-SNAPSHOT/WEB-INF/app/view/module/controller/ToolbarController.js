/**
 * 
 * module toolbar 上面事件的执行函数，包括新增，修改，删除等。
 * 
 * 这个类作为ModuleController的mixin类被引入
 * 
 */

Ext.define('app.view.module.controller.ToolbarController', {
	extend : 'Ext.Mixin',

	editRecord : function(button) {
		var window = Ext.widget('basewindow', {
					viewModel : this.getView().getViewModel()
				});
		var grid = this.getView().down('modulegrid');
		console.log(grid.getSelectionModel().getSelection()[0]);
		console.log(grid.getStore().getAt(0));

		window.down('baseform').setData(grid.getSelectionModel().getSelection()[0]);
		window.show();
	},

	// 新增一条记录
	addRecord : function(button) {
		var grid = this.getView().down('modulegrid');
		var model = Ext.create(grid.getStore().model);
		model.set(model.idProperty, null); // 设置主键为null,可自动增加
		// 插入空记录到第一条
		grid.getStore().insert(0, model);
		// 编辑第一条
		grid.rowEditing.startEdit(model);
	},

	// 根据选中的记录复制新增一条记录
	addRecordWithCopy : function() {
		var grid = this.getView().down('modulegrid'), sm = grid.getSelectionModel();
		if (sm.getSelection().length != 1) {
			Ext.toast({
						title : '警告',
						html : '请先选择一条记录，然后再执行此操作！',
						bodyStyle : 'background-color:yellow;',
						header : {
							border : 1,
							style : {
								borderColor : 'pink'
							}
						},
						border : true,
						style : {
							borderColor : 'pink'
						},
						saveDelay : 10,
						align : 'tr',
						closable : true,
						minWidth : 200,
						useXAxis : true,
						slideInDuration : 500
					});
			return;
		}
		var model = Ext.create(grid.getStore().model);
		Ext.Array.each(model.fields, function(field) { // 将选中记录的model都赋给值新的记录
					model.set(field.name, sm.getSelection()[0].get(field.name));
				});
		model.set(model.idProperty, null); // 设置为null,可自动增加
		grid.getStore().insert(0, model);
		sm.select(model); // 选中当前新增的记录
	},

	// 删除一条或多条记录
	deleteRecords : function(button) {
		var grid = this.getView().down('modulegrid'), selection = grid
				.getSelectionModel().getSelection(), message = '', infoMessage = '', modultitle;
		if (selection.length == 1) { // 如果只选择了一条
			message = ' 『' + selection[0].getNameValue() + '』 吗?';
			infoMessage = '『' + selection[0].getNameValue() + '』';
		} else { // 选择了多条记录
			message = '<ol>';
			Ext.Array.each(grid.getSelectionModel().getSelection(), function(record) {
						message += '<li>' + record.getNameValue() + '</li>';
					});
			message += '</ol>';
			infoMessage = message;
			message = '以下 ' + selection.length + ' 条记录吗?' + message;
		}
		moduletitle = '<strong>' + this.getView().getViewModel().get('tf_title')
				+ '</strong>';
		Ext.MessageBox.confirm('确定删除', '确定要删除 ' + moduletitle + ' 中的' + message,
				function(btn) {
					if (btn == 'yes') {
						grid.getStore().remove(grid.getSelectionModel().getSelection());
						grid.getStore().sync();
						Ext.toast({
									title : '删除成功',
									html : moduletitle + infoMessage + '已成功删除！',
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
									slideInDuration : 500
								});
					}
				})
	}

})
