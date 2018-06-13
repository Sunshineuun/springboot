var moduleName = 'Book';
Ext.onReady(function () {

  /*var grid = new Ext.ux.GridView({
    moduleName: 'Book'
  });*/

  Ext.create("Ext.ux.GridView", {
    moduleName: moduleName
  });
});
