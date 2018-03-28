/**
 * 字段容器factory
 */
Ext.define('app.view.module.factory.FieldContainerFactory', {

	statics : {

		getContainer : function(container, module, formtype) {

			var result = {
				xtype : 'fieldcontainer',
				layout : 'hbox',
				margin : '0 0 0 0',
				items : []
			};
			for (var i in container) {
				var field = container[i];
				// 如果是空的位置
				if (field.spacer) {
					result.items.push({
								xtype : 'fieldcontainer',
								layout : 'anchor',
								margin : '0 0 0 0',
								flex : field.flex
							});
				} else {
					var fieldDefine = module.getFieldDefine(field.tf_fieldId);

					var f = app.view.module.factory.FormFieldFactory.getField(fieldDefine,
							field, formtype, module);

					var c = {
						xtype : 'fieldcontainer',
						layout : (  f.moduleName )
								? (field.tf_width != -1 ? 'table' : 'hbox')
								: 'anchor',
						flex : field.tf_colspan,
						items : []
					};
					if (c.layout == 'hbox')
						c.margin = '0 0 5 0';
					c.items.push(f);

					result.items.push(c);
				}
			}
			return result;
		}
	}
});