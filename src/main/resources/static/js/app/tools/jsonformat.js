Ext.onReady(function () {
  // var clientWidth =  document.body.clientWidth;
  // var clientHeight =  document.body.clientHeight;
  var windowWidth = window.innerWidth; // 浏览器窗体的宽度
  var windowHeight = window.innerHeight; // 浏览器窗体的高度

  var panel = Ext.create('Ext.Panel', {
    fullscreen: true,
    width: windowWidth,
    height: windowHeight,
    layout: 'border',
    items: [{
      xtype: 'textareafield',
      title: 'JSON在线解析(双击自动格式化)',
      region: 'west',
      flex: 1,
      split: true,
      enableKeyEvents: true,
      listeners: {
        keydown: function (thiz, e, eOpts) {
          if(e.getKey() === Ext.event.Event.ENTER){
            var result = formatJson(this.getValue());
            Ext.getCmp('result').setValue(result);
          }
        }
      }
    }, {
      xtype: 'textareafield',
      region:'center',
      id: 'result',
      flex: 1,
      split: true
    }]
  });

  Ext.create('Ext.container.Viewport', {
    layout: 'border',
    items: [panel]
  });
});
