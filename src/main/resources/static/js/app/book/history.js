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
      width: 60
    }, {
      text: 'ID',
      dataIndex: 'id',
      hidden: true,
      hideable: false
    }, {
      text: '书名',
      dataIndex: 'bookName',
      flex: 3,
      sortable: true,
      groupable: false
    }, {
      text: '借书人',
      dataIndex: 'borrower',
      flex: 1
    }, {
      text: '是否还书',
      dataIndex: 'isEnable',
      renderer: function(value) {
        if(value === '0') {
          return '已还';
        }else if(value === '1'){
          return '未还';
        }
        return value;
      }
    }, {
      text: '开始日期',
      dataIndex: 'startDate',
      xtype : 'datecolumn',
      flex: 1,
      format: 'Y-m-d H:i:s'
    }, {
      text: '结束日期',
      dataIndex: 'endDate',
      xtype : 'datecolumn',
      flex: 1,
      format: 'Y-m-d H:i:s'
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
   * store
   * @type {Ext.data.Store}
   */
  var store = Ext.create('Ext.data.Store', {
    fields: fields,
    groupField: 'isEnable',
    proxy: {
      type: 'ajax',
      url: '/getHistoryBook',
      reader: {
        type: 'json',
        rootProperty: 'data'
      }
    },
    sorters: {
      property: 'startDate',
      direction: 'ASC'
    }
  });

  var grid = Ext.create('Ext.grid.Panel', {
    layout: {
      type: 'hbox',
      align: 'stretch'
    },
    forceFit: true,
    split: true,
    bufferedRenderer: false,
    renderTo: 'app',
    store: store,
    features: [groupingFeature],
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
