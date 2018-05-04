/**
 * Created by MinMin on 2018/4/28.
 */

var column = {
  text: '名称', // 表头该列的显示名称
  sortable: true, //控制列头是否可以排序（点击列头或列菜单）
  hideable: true, // 控制该列是否可以通过列菜单隐藏。
  menuDisabled: true, // 控制是否禁用列菜单。
  draggable: true, // 控制是否可以拖拽改变各列的顺序。
  groupable: true, // 控制是否可以按该列分组，参见Ext.grid.feature.Grouping
  dataIndex: 'name', // 指定Ext.data.Store的某个field来作为该列的value值。
  renderer: function (value) {return value;}, // 指定函数，把value值转换为可显示的内容。
  align: 'left', // 设置列头及单元格的对齐方向。 可取值: 'left', 'center', and 'right'。
  autoScroll: false, // 'true'使用溢出：'自动'的组件布局元素，并在必要时自动显示滚动条， 'false'溢出的内容。 不能像这样定义overflowX or overflowY.
  border: 0, // 指定该组件的边框. 边框可以是一个数值适用于所有边 或者它可以是每个样式的CSS样式规范, 例如: '10 5 3 10'.
  defaults: {},// 为组件提供通用属性,如果子组件存在通用属性，则应用子组件的属性
  editRenderer: false, // 渲染函数，配置RowEditing使用。 用于为不可编辑的单元格在编辑状态显示自定义的值。
  resizable： true, // 设置为false则该列无法拖拽改变大小。
};
