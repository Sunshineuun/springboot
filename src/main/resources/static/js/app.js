Ext.onReady(function () {

  Ext.QuickTips.init();

  var grid = new GridView({
    moduleName: 'Book'
  });

  Ext.create("Ext.container.Viewport", {
    layout: 'fit',
    items: grid
  });
});
