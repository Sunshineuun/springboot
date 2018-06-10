/**
 *  1. 通过后台加载数据，来进行界面配置。
 *  2. 单元格中的值溢出，展示悬浮。
 *
 */
Ext.define('Ext.ux.GridView', {
  extend: 'Ext.grid.Panel',
  alias: 'widget.uxgridview',
  // 自定义属性---------------------------------------------------------------------------------------------------------
  moduleName: '',
  url: '',
  inViewportShow: false, // 是否在窗口显示
  configure: null,
  pageSize: 30, // 每页显示条数
  sorters: [],
  startLoad: true,
  roweditable: false, // 是否启用行编辑模式
  dictLoad: true,
  dictUrl: '/loadDictionary',
  dictionaryParams: [],
  hasRightMenu: true,
  navGrid: {
    addText: '新增',
    delText: '删除',
    loaddictionaryoption: {
      afterLoadDictionary: null
    }
  },
  // 原始属性-----------------------------------------------------------------------------------------------------------
  id: moduleName,
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
    // 初始化菜单
    me.initRightMenu();
    // 加载字典
    me.loadDictionary();
    // 创建store
    me.createStore();
  },
  loadDictionary: function () {
    var me = this;
    if (!me.dictLoad || !me.dictUrl) {
      return;
    }

    // 增加默认的字典
    /*if (!me.dictionaryParams) {
     me.dictionaryParams = [["EXPLAINTYPE", "ADD_EXPLAINTYPE"]];
     } else {
     me.dictionaryParams[0].push("EXPLAINTYPE");
     me.dictionaryParams[0].push("ADD_EXPLAINTYPE");
     }*/

    var params = {};

    if (me.dictionaryParams[0]) {
      params["typeCodeStr"] = array2Str(me.dictionaryParams[0]);
    }
    if (me.dictionaryParams[1]) {
      params["queryIdStr"] = array2Str(me.dictionaryParams[1]);
    }

    if (!Ext.isEmpty(params)) {
      Ext.Ajax.request({
        async: false,
        url: me.dictUrl,
        params: params,
        success: function (response, opts) {
          var obj = Ext.decode(response.responseText);
          if (obj) {
            for (var key in obj) {
              dictionary[key] = eval(obj[key]);
            }
          }
        },
        failure: function (response, opts) {
          error('数据字典加载失败！');
        }
      });
    }

    var afterLoadDictionary = me.navGrid.loaddictionaryoption.afterLoadDictionary;
    if (afterLoadDictionary && typeof(afterLoadDictionary) === "function")
      afterLoadDictionary(dictionary);
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
      width: 50,
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
      if (value.ptype === 'rowediting') {
        var listeners = {
          listeners: {
            edit: function (editor, e, eOpts) {
              me.submitHandler(editor, e, eOpts);
            }
          }
        };
        Ext.apply(value, listeners);
      }
      me.plugins.push(Ext.apply(value, value.configMap))
    });

  },
  initRightMenu: function () {
    var me = this;
    if (me.hasRightMenu) {
      var array = [{
        text: me.navGrid.addText,
        iconCls: "table_add",
        handler: me.addHandler
      }, {
        text: me.navGrid.delText,
        iconCls: "table_delete",
        handler: me.delHandler
      }];
      me.rightMenu = new Ext.menu.Menu({
        items: array
      });

      me.addListener('itemcontextmenu', function (his, record, item, index, e) {
        e.preventDefault();
        e.stopEvent();// 取消浏览器默认事件
        me.rightMenu.showAt(e.getXY());// 菜单打开的位置
      });
    }
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
  submitHandler: function (editor, e, eOpts) {
    //rowediiting 提交逻辑
    //获取record
    var formData = e.record.getData();
    var grid = Ext.getCmp(moduleName);
    var url = '/execute/edit';
    console.debug(formData);

    if (formData['id'] === 'add') {
      url = '/execute/add';
    }

    Ext.Ajax.request({
      url: url,
      params: formData,
      success: function (response) {
        var result = Ext.decode(response.responseText);
        if (result.success) {
          //页面效果，提交数据
          e.record.commit();
          //重新排序，防止出现错位现象
          grid.store.sort('id', 'DESC');
        }
      }
    });
  },
  /**
   *
   * @param item 被点击项目
   * @param e 事件
   */
  addHandler: function (item, e) {
    var grid = Ext.getCmp(moduleName);
    var store = grid.getStore();
    console.debug(store.getModifiedRecords());
    // store.add({});
    store.insert(0, {id: 'add'});
    grid.getPlugin('rowediting').startEdit(0, 0);
  },
  delHandler: function () {
    var grid = Ext.getCmp(moduleName);
    var selection = grid.getSelectionModel().getSelection()[0];

    console.log(selection);
    Ext.Ajax.request({
      url: '/execute/del',
      params: selection.data,
      success: function (response) {
        var result = Ext.decode(response.responseText);
        if (result.success) {
          //重新排序，防止出现错位现象
          grid.store.sort('id', 'DESC');
        }
      }
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


Ext.define('Ext.ux.Combobox', {
  extend: 'Ext.form.field.ComboBox',
  alias: 'widget.uxcombobox',
  initComponent: function () {
    var me = this;
    me.createStore();

    this.callParent();

    if (me.url) {
      me.on("expand", function () {
        me.getStore().load();
      })
    }
  },
  url: null,
  hasEmpty: false,
  labelAlign: "right",
  values: null,
  trigger1Cls: 'x-form-clear-trigger',
  trigger2Cls: 'x-form-arrow-trigger',
  onTrigger1Click: function () {
    this.clearValue();
    this.fireEvent('clear', this);
  },
  onTrigger2Click: function () {
    this.onTriggerClick();
  },
  changeLocalStore: function (values, initValue) {
    var me = this;
    if (!me.url && values) {
      me.store.loadData(values);
      if (initValue && values.length > 0)
        me.setValue(values[0][0]);
      else
        me.setValue(null);
    }
  },
  changeRemoteStore: function (url) {
    var me = this;
    if (me.url && url) {
      me.url = url;
      me.store.setProxy({
        type: "ajax",
        url: me.url
      });
    }
  },
  bindParams: null,
  setComboValue: function (value, doSelect) {
    var me = this;

    if (me.queryMode === "remote") {
      if (me.store.proxy.extraParams)
        Ext.apply(me.store.proxy.extraParams, {
          "searchParam": value
        });
      else
        me.store.proxy.extraParams = {
          "searchParam": value
        };
      me.store.load();

      Ext.apply(me.store.proxy.extraParams, {
        "searchParam": null
      });

      me.setValue(value, doSelect);
    } else {
      me.setValue(value, doSelect);
    }
  },
  createStore: function () {
    var me = this;
    if (!me.url) {
      if (me.hasEmpty)
        me.values.push([]);

      me.queryMode = "local";
      me.store = Ext.create('Ext.data.ArrayStore', {
        fields: [me.valueField, me.displayField],
        data: me.values
      });
    } else if (me.url) {
      me.queryMode = "remote";
      me.store = Ext.create('Ext.data.Store', {
        pageSize: me.limit ? me.limit : 25,
        proxy: {
          type: "ajax",
          actionMethods: "post",
          url: me.url,
          reader: {
            type: "array",
            root: "result"
          }
        },
        fields: [me.valueField, me.displayField],
        listeners: {
          beforeload: function (store, options) {
            if (me.bindParams) {
              if (store.proxy.extraParams) {
                Ext.apply(
                  store.proxy.extraParams,
                  me.bindParams());
              } else {
                store.proxy.extraParams = me
                  .bindParams();
              }
            }

            if (me.hasEmpty) {
              Ext.apply(store.proxy.extraParams, {
                hasEmpty: true
              });
            } else {
              Ext.apply(store.proxy.extraParams, {
                hasEmpty: false
              });
            }
          }
        }
      });
    }
  },
  queryMode: 'local',
  forceSelection: true,
  triggerAction: 'all',
  displayField: 'label',
  valueField: 'value'
});
