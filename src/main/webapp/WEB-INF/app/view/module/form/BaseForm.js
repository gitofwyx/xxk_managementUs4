/**
 * 
 * 一个form窗口的基类，新增、显示、修改、审核、审批等功能继承了这个窗口
 * 
 */

Ext.define('app.view.module.form.BaseForm', {
			extend : 'Ext.form.Panel',
			alias : 'widget.baseform',

			autoScroll : true,

			buttonAlign : 'center',
			initComponent : function() {
				var me = this;
				this.buttons = [];
				this.buttons.push({
							text : '保存',
							itemId : 'save',
							glyph : 0xf0c7,
							handler : function(button){
								var form = button.up('form');
								
								button.up('form').updateRecord();
								button.up('form').getForm().getRecord().save();
							}
						},{
							text : '关闭',
							itemId : 'close',
							glyph : 0xf148,
							handler : function(button){
								button.up('window').hide();
							}
						});
				me.items = [];

				var groups = this.formScheme.tf_schemeGroups, hasTab = false;
				var hasInTabPanel = false; // 是否有嵌在页里面的tab,
				var inTabPanel;
				Ext.each(groups, function(group) {
							group.tf_numCols = group.tf_numCols || me.formScheme.tf_numCols;
							hasTab = hasTab || (group.tf_displayMode == 'tab');
							hasInTabPanel = hasInTabPanel
									|| (group.tf_displayMode == 'intabpanel');
						});
				if (hasTab) {
					var tabpanel = {
						xtype : 'tabpanel',
						frame : false,
						border : false,
						bodyPadding : '5 5 5 5',
						items : []
					};
					groups[0].tf_displayMode = 'tab'; // 如果第一个tab忘了设置
					var nowtab;

					Ext.each(groups, function(group) {
								if (group.tf_displayMode == 'tab') {
									if (nowtab)
										tabpanel.items.push(nowtab);
									nowtab = {
										xtype : 'container',
										title : group.tf_formGroupName,
										items : []
									};
								}
								nowtab.items.push(me.createFieldSetOrSubModule(group));

							});
					tabpanel.items.push(nowtab);
					me.items = tabpanel;
				} else {
					me.bodyStyle = 'padding : 5px 5px 0';
					Ext.each(groups, function(group) {
								if (group.tf_displayMode == 'intabpanel') {
									inTabPanel = {
										xtype : 'tabpanel',
										frame : false,
										border : false,
										height : 400,
										items : []
									};
									Ext.apply(inTabPanel, me
													.getOtherSetting(group.tf_otherSetting))
									me.items.push(inTabPanel);
								} else if (group.tf_displayMode == 'intab') {
									var t = me.createFieldSetOrSubModule(group);
									t.title = group.tf_formGroupName;
									inTabPanel.items.push(t)
								} else
									me.items.push(me.createFieldSetOrSubModule(group))

							})
				}
				me.callParent(arguments);
			},

			getOtherSetting : function(str) {
				if (!str)
					return {}
				else
					return Ext.decode('{' + str + '}', true)

			},

			createFieldSetOrSubModule : function(group) {
				var me = this;

				return Ext.create('app.view.module.form.FieldSet', {
							autoScroll : true,
							viewModel : this.getViewModel(),
							schemeGroup : group,
							numCols : group.tf_numCols
						})

			},

			initForm : function() {

			},

			// 不是grid中调用的显示某条记录的信息，可能是其他模块点击namefields来调用的
			setRecordId : function(id) {
				var me = this;
				this.module.model.load(id, {
							success : function(record, operation, success) {
								// success中的record中返回的raw 数据，是字符串，没有经过decode,要自己转成对象
								me.setData(Ext.create(me.module.model, Ext.decode(record.raw)));
							}
						});
			},

			setData : function(model) {
				this.data = model;
				if (this.data) {
					this.getForm().loadRecord(this.data);
				} else
					this.getForm().reset();
			}

		});