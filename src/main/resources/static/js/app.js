/**
 * Created by MinMin on 2018/4/27.
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

  // 导航栏
  Ext.create('Ext.tree.Panel', {
    title: 'Simple Tree',
    width: 200,
    height: 150,
    store: store,
    rootVisible: false,
    renderTo: Ext.getBody()
  });

  // 主体展示
  var tabPanel = Ext.create('Ext.TabPanel', {});

  var viewport = Ext.create('Ext.Viewport', {
    layout: 'border',
    items: [{
      region: 'west'
    }, {
      region: 'center'
    }]
  });
}
