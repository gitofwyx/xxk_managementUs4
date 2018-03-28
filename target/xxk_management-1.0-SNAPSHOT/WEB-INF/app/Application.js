/**
 * The main application class. An instance of this class is created by app.js
 * when it calls Ext.application(). This is the ideal place to handle
 * application launch and initialization details.
 */
Ext.define('app.Application', {
			extend : 'Ext.app.Application',

			name : 'app',
			views : [], // ＭＶＣ用到的view
			controllers : ['Root'
			// MVC控制器的名称，会自动到 /app/controller下去加载同名的js文件
			// 对于Root,会去自动加载 /app/Controller/Root.js 这个文件
			],
			stores : [],
			launch : function() {
				// 需要执行的语句可以加在此处

			}
		});
