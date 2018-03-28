/**
 * 根据module的数据来生成模块的model
 */
Ext.define('app.view.module.factory.ModelFactory', {

	statics : {
		getModelByModule : function(module) {
						
			var model = Ext.define('app.model.' + module.tf_moduleName, {
				extend : 'Ext.data.Model',
				module : module,
				idProperty : module.tf_primaryKey,
				nameFields : module.tf_nameFields,
				titleTpl : module.tf_titleTpl,
				titleTemplate : null,
				fields : this.getFields(module),
				proxy : {
					type : 'rest',
					batchActions : true,
					extraParams : {
						moduleName : module.tf_moduleName
					},
					api : {
						// 在这里加rest/是因为在web.xml中
						// <url-pattern>/rest/*</url-pattern>这一句，spring会根据rest
						// 后面的参数去进行匹配
						read : 'rest/module/fetchdata.do',
						update : 'rest/module/update.do',
						create : 'rest/module/create.do',
						destroy : 'rest/module/remove.do'
					},
					actionMethods : {
						create : 'POST',
						read : 'GET',
						update : 'PUT',
						destroy : 'DELETE'
					},
					reader : {
						type : 'json',
						root : 'records',
						totalProperty : 'totalCount'
					},
					writer : {
						type : 'json',
						writeRecordId : true,
						writeAllFields : false
						// 没有修改过的字段不加入到update和delete的json中去
					},
					listeners : {
						exception : function(proxy, response, operation) {
							// 将出错信息加到proxy中去，传递到store的sync中显示出错信息，显示后将此属性删除
							proxy.errorInfo = Ext.decode(response.responseText, true);
							// 如果出错信息解析出错，则加入一个缺省的
							if (!proxy.errorInfo)
								proxy.errorInfo = {
									resultCode : -1,
									errorMessage : '未知原因:' + response.responseText
								}
						}
					}
				},

				getTitleTpl : function() {
					if (!this.titleTemplate) {
						if (this.titleTpl)
							this.titleTemplate = new Ext.Template(this.titleTpl);
						else
							this.titleTemplate = new Ext.Template('{' + this.nameFields + '}');
					}
					return this.titleTemplate.apply(this.getData())
				},

				// 此条记录是否可以修改
				canEdit : function() {
					if (this.module.tf_hasAuditing && this.get('tf_auditinged'))
						return false;
					if (this.module.tf_hasApprove && this.get('tf_shNowCount') > 0)
						return false;
					return true;
				},

				// 此条记录是否可以进行操作
				canOperate : function() {
					if (this.module.tf_hasAuditing && this.get('tf_auditinged'))
						return false;
					return true;
				},

				// 此条记录是否可以删除
				canDelete : function() {
					if (this.module.tf_hasAuditing && this.get('tf_auditinged'))
						return {
							canDelete : false,
							message : '【' + this.getTitleTpl() + '】已进行过审核，不允许进行删除操作!'
						};
					if (this.module.tf_hasApprove && this.get('tf_shNowCount') > 0)
						return {
							canDelete : false,
							message : '【' + this.getTitleTpl() + '】正在审批或已经审批完成,不允许进行删除操作!'
						};
					return true;
				},

				// 取得主键值
				getIdValue : function() {
					return this.get(this.idProperty);
				},

				// 取得当前记录的名字字段
				getNameValue : function() {
					if (this.nameFields)
						return this.get(this.nameFields);
					else
						return null;
				}

			});
			return model;
		},
		// String("String"), Boolean("Boolean"), Integer("Integer"),
		// Date("Date"), Double("Double"), Float("Float"); Percent

		getFields : function(module) {
			var fields = [];

			for (var i in module.tf_fields) {
				var fd = module.tf_fields[i];

				var field = {
					name : fd.tf_fieldName,
					title : fd.tf_title,
					type : this.getTypeByStr(fd.tf_fieldType)
				};
				if (field.type == 'string') {
					field.useNull = true;
					field.serialize = this.convertToNull;
				}

				if (fd.tf_fieldType == 'Date') {
					field.dateWriteFormat = 'Y-m-d';
					field.dateReadFormat = 'Y-m-d';
				}
				if (fd.tf_fieldType == 'Datetime')
					field.dateReadFormat = 'Y-m-d H:i:s';
				field.tf_haveAttachment = fd.tf_haveAttachment;
				fields.push(field);
			}
			return fields;

		},

		getTypeByStr : function(str) {
			console.log(str);
			switch (str) {
				case 'String' :
					return 'string';
				case 'Boolean' :
					return 'boolean';
				case 'Integer' :
					return 'int';
				case 'Date' :
					return 'date';
				case 'Datetime' :
					return 'date';
				case 'Double' :
				case 'Money' :
				case 'Percent' :
					return 'float';
				default :
					return 'string';
			}
		},

		// 如果是空字符串，返回null
		convertToNull : function(v) {
			return v ? v : null;
		}

	}

});
