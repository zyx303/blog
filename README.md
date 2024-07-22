[地址](http://121.41.83.146:8080/#/)

## 技术栈
vue + vuex + element-ui + springboot + mybatisplus + redis + 阿里云oss + mysql	

## 实现
~~抄！~~
	整个项目是复制的b站大学[B站最通俗易懂手把手SpringBoot+Vue项目实战-前后端分离博客项目-Java项目](https://www.bilibili.com/video/BV1hq4y1F7zk/?spm_id_from=333.1007.top_right_bar_window_custom_collection.content.click&vd_source=4d4b7830e0ec6c5f72ff630db3205013)
	前端clone下来稍微改了改，把评论删了，浏览量删了，反正用不上，把唐氏儿的图换了，基本是套皮。后期有空换一套二次元模版。
	 后端基本是照着原视频码的，其中将七牛云oss换成了阿里云（浙大的学生优惠真香），工具类也是自己封装的。后台用户管理还有注册的相关接口没有写。

## 部署
这个教程没讲部署，给我一顿折腾。
[别人的部署博客](https://www.yuque.com/huanfqc/my-blog/myblog#rhZ19)
用的阿里云的ecs（浙大的学生优惠真香）

## 总结和收获
大致掌握了spring的web多模块开发流程：
- 创建父项目，在pom中加入依赖锁定
- 创建子项目，可以建一个公共的framework项目，存放前后台公用的实体类，工具类等
- 一个api的开发流程基本是：
	1.设计表，创建表。创建实体，mapper等（我用的easycode生成，弹幕说mybatisx更好用），继承Basemapper（mybatisplus），记得加泛型
	2.controller层：创建Controller，加上RestController注解；写接口，加上mapping;注入service；调用service的方法
	3.service层： 创建接口，继承Iservice，记得加泛型；创建方法；在impl创建实现类，继承	serviceImpl，加泛型；实现方法，一般直接用mybatisplus。要自己写sql的时候用mybatisx创建mapper,记得默认扫描路径是resource下的mapper目录
			(没有的自己建）


一些坑：
- 创建spring项目的时候一定要注意maven配置，先把本地仓库还有镜像配置好，不然后期要改费老命了
- 这个项目前端中的镜像仓库过期了，要把锁定依赖的文件删了再npm i
- 阿里云网页端的ssh上传文件慢的一批，用xftp传文件是好习惯
- mysql表用utf8mb4，不然中文乱码。部署用dump命令还是乱码，但是用dbeaver就没问题，很奇怪。
- 
