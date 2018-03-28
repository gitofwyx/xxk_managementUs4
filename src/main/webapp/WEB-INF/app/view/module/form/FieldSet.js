/**
 * 
 * 生成form中的一个fieldSet的类
 * 
 */
Ext.define('app.view.module.form.FieldSet', {
			extend : 'Ext.form.FieldSet',
			alias : 'widget.formfieldset',

			requires : ['app.view.module.factory.FieldContainerFactory',
					'app.view.module.factory.FormFieldFactory'],

			defaultType : 'textfield',
			defaults : {},
			layout : 'anchor',
			config : {
				module : undefined, //  此模块的module定义
				schemeGroup : undefined, // 定义了此fieldSet的属性以及下面需要加的字段
				numCols : undefined,
				formtype : undefined
			},

			initComponent : function() {
				this.title = this.schemeGroup.tf_formGroupName;
				this.collapsible = this.schemeGroup.tf_collapsible;
				this.collapsed = this.schemeGroup.tf_collapsed;

				this.items = [];

				var containers = []; // 要计算一下有多少个container，如果col=2,那么二个一换行，或者指定换行
				var hiddens = []; // 隐藏的字段
				var container = [];
				var c = 0;

				for (var i in this.schemeGroup.tf_groupFields) {
					var field = this.schemeGroup.tf_groupFields[i];
					var fieldDefine = this.getViewModel()
							.getFieldDefine(field.tf_fieldId);
					// 如果是隐藏字段，那么就直接放在隐藏字段的数组里
					if (fieldDefine && fieldDefine.tf_isHidden) {
						hiddens.push(field);
						continue;
					}
				}

				for (var i in this.schemeGroup.tf_groupFields) {
					var field = this.schemeGroup.tf_groupFields[i];
					var fieldDefine = this.getViewModel()
							.getFieldDefine(field.tf_fieldId);
					if (fieldDefine && fieldDefine.tf_isHidden) {
						continue;
					}
					// 设置tf_colspan如果是0，那么置为1，如果大于tf_colspan,置为tf_colspan
					field.tf_colspan = field.tf_colspan ? field.tf_colspan : 1;
					if (field.tf_colspan > this.numCols)
						field.tf_colspan = this.numCols;
					// 如果加上这一行，超出了numCols,那么就要分二行了
					if (c + field.tf_colspan > this.numCols) {
						if (this.numCols - c > 0)
							container.push({
										spacer : true,
										flex : this.numCols - c
									});
						containers.push(container);
						container = [];
						container.push(field);
						c = field.tf_colspan;
					} else {
						container.push(field);
						c += field.tf_colspan;
						if (c >= this.numCols || field.tf_isEndRow) {
							if (this.numCols - c > 0)
								container.push({
											spacer : true,
											flex : this.numCols - c
										});
							c = 0;
							containers.push(container);
							container = [];
						}
					}
				}
				if (container.length > 0)
					containers.push(container);
				// 生成每一个container ，一个container中可以放置若干个字段，如果分栏是3,那就放3个
				for (var i in containers) {
					this.items.push(app.view.module.factory.FieldContainerFactory
							.getContainer(containers[i], this.getViewModel(), this.formtype));
				}

				// 加入隐藏的字段
				for (var i in hiddens) {
					var field = hiddens[i];
					var fieldDefine = this.getViewModel()
							.getFieldDefine(field.tf_fieldId);
					var f = app.view.module.factory.FormFieldFactory.getField(
							fieldDefine, field, this.formtype);
					this.items.push(f);
				}

				this.callParent(arguments);
			}
		})
