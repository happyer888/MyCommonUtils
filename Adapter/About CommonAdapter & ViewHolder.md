# 用途:

* 项目中ListView、GridView几乎是Android开发必用的组件
* Adapter作用是为这些控件绑定数据
* 将除getView方法外的部分抽取成本工具类,方便直接使用.
* 以后每次写Adapter时，不管何种item布局，只需重写一个方法绑定数据即可.

# 使用方法

* 1.导入CommonAdapter.java和ViewHolder.java到对应的包中.
* 2.在项目中,新建一个adapter类继承CommonAdpter,创建构造器,重写抽象方法.
* 3.初始化ViewHolder,绑定数据,进行复用即可.