/**
 *  1. 通过后台加载数据，来进行界面配置。
 *
 */
Ext.define('GridView', {
  extend: 'Ext.grid.Panel',
  alias: 'GridView',
  // 自定义属性---------------------------------------------------------------------------------------------------------
  moduleName: '',
  inViewportShow: false, // 是否在窗口显示
  configure: null,
  // 原始属性-----------------------------------------------------------------------------------------------------------
  columns: [],
  forceFit: true,
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
    var me = this;
    var configure = null;

    if (!me.moduleName) {
      console.error('模块名称不为空');
      debugger;
    }

    /**
     * 加载模块配置
     */
    Ext.Ajax.request({
      async: false,
      url: '/gridview/getModuleNameByConfigure/' + me.moduleName,
      success: function (response, opts) {
        configure = Ext.decode(response.responseText);
      },
      failure: function (response, opts) {
        console.error('配置信息加载失败');
        debugger;
      }
    });

    console.log(configure);
    me.configure = configure;
    me.initColumns();
  },
  initColumns: function () {
    /**
     * 创建columns
     * @type {GridView}
     */

    var me = this;
    var configure = me.configure;

    /**
     * 创建序列号列
     * @type {Ext.grid.column.RowNumberer}
     */
    var rowNumberer = new Ext.grid.RowNumberer({
      header: '序列',
      resizable: true
    });

    me.columns.push(rowNumberer);

    Ext.Array.forEach(configure.columns, function(value, index, self){
      me.columns.push(value);
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

/*
  // 自定义属性---------------------------------------------------------------------------------------------------------
  url: null, // 页面请求的URL
  dictUrl: ctx + "/dictionary/loadDictionary.do", // 字典请求URL
  dictLoad: true, // 是否加载字典
  beforeRequest: null, // 请求前的操作
  reasonUrl: null, // 说明的提交入口
  addReason: false, // 新增是否需要填写说明
  editReason: false, // 修改是否需要填写说明
  delReason: false, //  删除是否需要填写说明
  addReduction: false,
  editUrl: null, // 编辑提交的url入口
  storeFields: null, // store的字段
  startLoad: true,  // 是否进行load
  inViewportShow: false, // 自定义属性，视图设置
  navGrid: null, // 自定义属性
  viewport: null, //
  searchFieldCombobox: null,
  autoTbar: true,
  hasRightMenu: true,
  addButton: null,
  editButton: null,
  delButton: null,
  logicDelButton: null,
  clinicalSubmitButton: null,
  addHandler: null,
  editHandler: null,
  delHandler: null,
  logicDelHandler: null,
  clinicalSubmitHandler: null,
  sortname: null, // 在store中设置，排序的名称
  sortorder: null, // 在store中设置，排序的方式 desc/asc
  dictionaryParams: null,
  hasPagingToolbar: true,
  editWinWidth: 450,
  // 原始属性-----------------------------------------------------------------------------------------------------------
  forceFit: true, // 设置为true，则强制列自适应成可用宽度
  rootVisible: false, // 隐藏根节点
  loadMask: true, //
  useArrows: true, // 在tree中使用Vista-style样式的箭头
  scroll: true, //
  autoScroll: true, // 溢出，展示滚动条
  rowLines: true, // 设置为false则取消行的框线样式
  pageSize: 30, // 在store中设置每页每页显示多少条
  columnLines: true, // 添加列的框线样式
  enableColumnHide: false, // 设置为false则禁用隐藏表格中的列
  alignrightside: false, //是否加入右对齐
  isInitToolTip: true,*/
