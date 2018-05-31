/**
 *  1. 通过后台加载数据，来进行界面配置。
 *  2. 单元格中的值溢出，展示悬浮。
 *
 */
Ext.define('GridView', {
  extend: 'Ext.grid.Panel',
  alias: 'GridView',
  // 自定义属性---------------------------------------------------------------------------------------------------------
  moduleName: '',
  url: '',
  inViewportShow: false, // 是否在窗口显示
  configure: null,
  pageSize: 30, // 每页显示条数
  sorters: [],
  startLoad: true,
  roweditable: false, // 是否启用行编辑模式
  // 原始属性-----------------------------------------------------------------------------------------------------------
  columns: [],
  plugins: [],
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
    me.loadModuleConfig();
    me.initPanel();
    // 初始化列配置
    me.initColumns();
    // 初始化插件
    me.initPlugins();
    // 创建store
    me.createStore();
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
  },
  initPanel: function () {
    var me = this;
    var configure = me.configure;

    var keys = ['forceFit', 'autoScroll', 'rowLines',
      'columnLines', 'pageSize', 'enableColumnHide',
      'url', 'sorters', 'startLoad'];

    Ext.Array.forEach(keys, function (value, index) {
      me[value] = configure[value];
    });
  },
  initColumns: function () {
    /**
     * 创建columns
     * @type {GridView}
     */

    var me = this;
    var configure = me.configure;

    /**
     * 创建序列号列, 特殊标识通过renderer渲染
     * @type {Ext.grid.column.RowNumberer}
     */
    var rowNumberer = new Ext.grid.RowNumberer({
      header: '序号',
      width: 23,
      resizable: true,
      sealed: true,
      renderer: function (value, metadata, record, rowIndex, columnIndex, store) {
        var page = store.lastOptions.page;
        var limit = store.lastOptions.limit;
        /*if (record.raw['REVIEWING_IS'] === "0") {
         return '<span style="color:red"> <strong>' + '*' + index + '</strong> </span>'
         } else {
         return index;
         }*/
        return (page - 1) * limit + 1 + rowIndex;
      },
      listeners: {
        render: function (_this, eOpts) {
        }
      }
    });

    me.columns.push(rowNumberer);

    Ext.Array.forEach(configure.columns, function (value, index, self) {
      value.renderer = function (v) {

        /**
         *
         * eval函数执行，代码需要已单引号引起来。
         * 一大进步，知道怎么创建function对象。
         */
        var a = value.rendererFun.replace('#', v);
        var func = new Function(a);
        return func();
        // return eval('\'' + a + '\'');
      };
      me.columns.push(value);
    });
  },
  initPlugins: function () {
    var me = this;

    Ext.Array.forEach(me.configure.plugins, function (value, index, self) {
      me.plugins.push(Ext.apply(value, value.configMap))
    });

  },
  createStore: function () {
    var me = this;

    var fields = [];

    Ext.Array.forEach(me.configure.columns, function (value, index, self) {
      fields.push(value.dataIndex);
    });

    me.store = Ext.create('Ext.data.Store', {
      fields: fields,
      pageSize: me.pageSize,
      remoteSort: true, // 设置为 true 则将所有的排序操作推迟到服务器. 如果设置为 false, 则在客户端本地排序
      proxy: {
        type: 'ajax',
        url: me.url,
        timeout: 600000,
        actionMethods: {
          create: 'POST',
          read: 'POST',
          update: 'POST',
          destroy: 'POST'
        },
        reader: { // TODO
          type: 'json',
          rootProperty: 'data'
        }
      },
      autoLoad: false,
      sorters: me.configure.sorters,
      listeners: {
        load: function (store, records, successful, eOpts) {
          if (successful === false) {
            var msg = store.getProxy().getReader().rawData.result;
            if (msg) {
              error(msg);
            } else {
              error("服务器出错！");
            }
          }
        }
      }
    });

    if (me.startLoad) {
      me.store.load();
    }
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
  },
  listeners: {
    /**
     *
     * @param view 指向Ext.view.View
     * @param record 属于选项的记录
     * @param item 选项元素
     * @param index 选项索引
     * @param e 事件对象
     * @param eOpts The options object passed to Ext.util.Observable.addListener.
     */
    itemmouseenter: function (view, record, item, index, e, eOpts) {

      var flag = false; // false-不显示浮动框，true-显示浮动框。

      if (!e.getTarget(view.cellSelector)) {
        console.log(view.cellSelector);
        return;
      }

      // TODO Cannot read property 'cellIndex' of null
      // 在某些情况下，e.getTarget(view.getCellSelector()) 的结果为null
      var column = view.getGridColumns()[e.getTarget(view.getCellSelector()).cellIndex];

      if (column.xtype === 'actioncolumn') {
        return;
      }

      var str = record.data[column.dataIndex];

      if (!str) {
        if (view && view.tip) {
          view.tip.destroy();
          view.tip = null;
        }
        return;
      }

      /*
       * 对于配置数据字典，将value转换的text。
       * */
      /*if (column.dictionary && dictionary[column.dictionary]) {
       str = formatter(column.dictionary, str);
       }*/

      var brIndex = 0;
      /*
       只有当为string类型的时候，去查看是否包含换行标签</br>则取整行最长的那段字符。
       */
      if (typeof(str) === 'string') {
        brIndex = str.indexOf("</br>");
      }

      /* if(brIndex > 0 ){
       var strLine = str.split("</br>");
       var strTemp = "";
       Ext.each(strLine, function(value){
       if(value.length > strTemp.length){
       strTemp = value;
       }
       });
       str = strTemp;
       } */

      /*
       * 判断是否有换行标签，若没有计算字符长度，否则显示悬浮。
       * */
      if (brIndex <= 0) {
        var strPiex = 12; // 默认增加6像素容差。
        /*
         * 计算字符所占像素*/
        for (var i = 0; i < str.length; i++) {
          /*
           * 汉字12像素，非汉字7像素*/
          if (str.charCodeAt(i) > 255) {
            strPiex += 12;
          } else {
            strPiex += 7;
          }

          /*
           * 当大于column.width就退出显示悬浮*/
          if (strPiex >= column.getWidth()) {
            flag = true;
            break;
          }
        }
      } else {
        flag = true;
      }

      if (flag) {
        //悬浮框创建
        if (!view.tip) {
          view.tip = Ext.create('Ext.tip.ToolTip', {
            autoHide: false,
            mouseOffset: [5, 5],
            target: view.el,
            delegate: '.x-grid-cell-inner',
            renderTo: Ext.getBody(),
            bodyStyle: 'word-wrap:break-word',
            listeners: {
              beforeshow: function (thiz) {
                return !!thiz.html;
              }
            }
          });
        }
        // view.el.clean(); // 清理
        view.tip.setHtml(str); // 更新显示内容
      } else {
        if (view.tip) {
          // TODO 怎么在他不需要展示的时候隐藏掉
          view.tip.setHtml("");
          // view.tip.destroy();
          // view.tip = null;
        }
      }
    }
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
