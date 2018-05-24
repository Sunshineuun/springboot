Ext.onReady(function () {
  var grid = new GridView({
    moduleName: 'Book'
  });

  Ext.create("Ext.container.Viewport", {
    layout: 'fit',
    items: grid
  });
});