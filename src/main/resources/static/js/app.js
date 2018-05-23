Ext.onReady(function () {
  var grid = new GridView();

  Ext.create("Ext.container.Viewport", {
    layout: 'fit',
    items: grid
  });
});