/**
 *  1. 通过后台加载数据，来进行界面配置。
 *
 */
Ext.define('GridView', {
  extend: 'Ext.grid.Panel',
  alias: 'GridView',
  // 自定义属性---------------------------------------------------------------------------------------------------------
  inViewportShow: false, // 是否在窗口显示
  // 原始属性-----------------------------------------------------------------------------------------------------------
  initComponent: function () {
    var me = this;

    me.initGrid();

    this.callParent(arguments);

    // 创建视图
    me.createViewport();
  },
  initGrid: function () {
    var me = this;

    // 加载模块配置
    this.loadModuleConfig();
  },
  loadModuleConfig: function () {
    /**
     * 加载模块配置
     */
    Ext.Ajax.request({
      async: false,
      url: '/getBooks',
      params: {},
      success: function (response, opts) {
        var obj = Ext.decode(response.responseText);
        console.log(obj);
      },
      failure: function (response, opts) {
        error('数据字典加载失败！');
      }
    });
  },
  createViewport: function () {
    /**
     * 创建视图
     */
    var me = this;
    if (!me.inViewportShow)
      return;
    me.viewport = Ext.create("Ext.container.Viewport", {
      layout: 'fit',
      items: [me]
    });
  }
});
