Ext.onReady(function () {

  /*var grid = new Ext.ux.GridView({
    moduleName: 'Book'
  });*/

  Ext.create("Ext.container.Viewport", {
    layout: 'fit',
    items: {
      xtype: 'uxgridview',
      moduleName: 'Book',
      dictionaryParams: [[], ['TYPE']] //TODO
    }
  });
});
