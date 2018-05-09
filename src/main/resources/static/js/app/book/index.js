/**
 * Created by MinMin on 2018/5/7.
 */

/**
 * 常用属性
 *
 */
Ext.require([
  'Ext.grid.*',
  'Ext.data.*',
  'Ext.panel.*',
  'Ext.layout.container.Border'
]);


/**
 * fn 要執行的函數
 * scope 回调函数的执行范围
 * withDomReady 等待dom加载结束
 */
Ext.onReady(start);

function start() {

  Ext.tip.QuickTipManager.init();

  /**
   * columns 列
   * @type {[*]}
   */
  var columns = [
    {
      text: '序号',
      xtype: 'rownumberer',
      sealed: true,
      width: 60,
      editor: null, // 可选配置，xtype名或Field配置对象，用于配置编辑的控件。 仅当表格使用Editing插件时，才可使用。
      editRenderer: function (value) {
        // 渲染函数，配置RowEditing使用。 用于为不可编辑的单元格在编辑状态显示自定义的值。
        return value;
      }
    }, {
      text: 'ID',
      dataIndex: 'id',
      hidden: true,
      hideable: false
    }, {
      text: '书名',
      dataIndex: 'name',
      flex: 1,
      sortable: true,
      groupable: false
    }, {
      text: "作者",
      dataIndex: 'author',
      flex: 1,
      sortable: true,
      groupable: false
    }, {
      text: "归属",
      width: 125,
      dataIndex: 'affiliation',
      sortable: true
    }, {
      text: "占有",
      width: 125,
      dataIndex: 'occupy',
      sortable: true
    }
  ];
  /**
   * 属性
   * @type {Array}
   */
  var fields = [];
  Ext.each(columns, function (c) {
    if (c.dataIndex) {
      fields.push(c.dataIndex);
    }
  });

  /**
   * 分组插件
   * @type {Ext.grid.feature.Grouping}
   */
  var groupingFeature = Ext.create('Ext.grid.feature.GroupingSummary', {
    groupHeaderTpl: '{columnName}: {name} ({rows.length})', //print the number of items in the group
    showGroupsText: '取消分组', //
    groupByText: '分组', // 对于依表头分组，显示在grid表头菜单中的文本
    startCollapsed: false // true-自动折叠；false-不自动折叠
  });
  /**
   * 行编辑插件
   * @type {Ext.grid.plugin.RowEditing}
   */
  var roweditPlugins = Ext.create('Ext.grid.plugin.RowEditing', {
    clicksToEdit: 5, // 配置点击几次显示gird编辑器.
  });

  /**
   * store
   * @type {Ext.data.Store}
   */
  var store = Ext.create('Ext.data.Store', {
    fields: fields,
    pageSize: 50,
    groupField: 'occupy',
    proxy: {
      type: 'ajax',
      url: '/getBooks',
      reader: {
        type: 'json',
        rootProperty: 'data'
      }
    },
    sorters: {
      property: 'name',
      direction: 'ASC'
    }
  });

  var grid = Ext.create('Ext.grid.Panel', {
    layout: {
      type: 'hbox',
      align: 'stretch'
    },
    features: [groupingFeature],
    plugins: [roweditPlugins],
    forceFit: true,
    split: true,
    bufferedRenderer: false,
    renderTo: 'app',
    store: store,
    columns: {
      defaults: {
        editor: {
          // 仅当表格使用Editing插件时，才可使用。
          xtype: 'textfield',
          allowBlank: false
        }
      },
      items: columns
    }
  });

  store.load();
}
