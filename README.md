
> 开源项目：[熊猫书屋](https://github.com/springbear2020/panda-book-house)


> 开发时间：2022.04.21 - 2022.05.13



@[toc]

# 一、快速开始

方案一：

1. 克隆仓库：使用 Git 克隆仓库或直接下载仓库压缩包到您的计算机
2. 打开工程：使用 `IntelliJ IDEA`  打开克隆的仓库或解压的工程文件
3. 创建数据库和表并插入数据：登录 MySQL ，创建 `panda_book_house` 数据库，将 `src/main/resources/sql/panda_book_house.sql` 文件中的数据库表导入 panda_book_house 数据库中
4. 修改数据库连接信息：修改 `src/main/resources/properties/jdbc.properties` 中的数据库连接信息，设置你自己的用户名和密码 
5. 修改邮箱服务器信息（可选，不设置则用户注册时邮箱验证码功能不可用）：修改 `src/main/resources/properties/email.properties` 中的邮箱连接信息，设置你自己的邮箱账号和服务器（[smtp 开启](https://blog.csdn.net/smilehappiness/article/details/108145215)）
6. 部署访问：在 IntelliJ IDEA 中部署 `Tomcat` 即可访问熊猫书屋首页
7. 登录系统：默认用户名和密码均为 `admin`

方案二：

1. 克隆仓库：使用 Git 克隆仓库或直接下载仓库压缩包到您的计算机

2. 拷贝 war 包：将 `RELEASE` 目录下的 `panda-book-house.war` 包拷贝到 `Tomcat` 安装目录下的 `webapps` 目录中

3. 创建数据库和表并插入数据：登录 MySQL ，创建 `panda_book_house` 数据库，将 `src/main/resources/sql/panda_book_house.sql` 文件中的数据库表导入 panda_book_house 数据库中

4. 创建数据库用户：在 MySQL 控制台创建 `admin` 用户，密码也为 `admin`，并赋予 admin 用户所有操作权限

   ```sql
   create user 'admin'@'localhost' identified by 'admin';
   grant all on panda_book_house.* to 'admin'@'localhost' with grant option;
   ```

5. 启动 Tomcat：双击 Tomcat 安装目录下 `bin` 目录中的 `startup.bat` 启动 Tomcat

6. 访问首页：在浏览器地址栏输入 `http://localhost:8080/panda-book-house/` 即可访问熊猫书屋首页

7. 登录系统：默认用户名和密码均为 `admin`


# 二、背景调查

随着互联网技术的飞速发展，网络上充斥着越来越多的有用或是无用的资源，要在不计其数的资源中筛选出自己亟需的资源，需要耗费巨大的精力和时间。尤其是各种电子图书资源（主要为 PDF）还涉及到知识产权的法律性问题，导致截至目前为止几乎没有哪个产品能完全满足快速检索电子图书资源和提供相关服务的需求。一般情况下用户所需的电子图书资源都存放在用户的个人云盘中，现有的网站系统几乎是将所有的下载链接进行糅合，杂乱无章的整合让普通用户无所适从、望而却步。对于急需查找电子图书的用户，需要到从繁杂的网页中检索出需要的图书的链接，再根据链接跳转到对应的云盘进行下载。不得不说，这样的方式对一般用户极其不友好，图书资源也不够规范化、有的是盗版图书、有的下载链接早已失效······ 极大程度浪费了用户的精力和时间。因而我们的熊猫书屋系统应运而生，本着开源共享的精神，本站所有的图书资源均由用户上传，站长负责整理发布，只为用户提供一个专业的 PDF 书屋（我们的设想是在不久的将来能联系作者本人解决图书的知识产权问题，尊重知识创造）
#  三、功能描述

![在这里插入图片描述](https://img-blog.csdnimg.cn/8bc3fea9a2d34656bb3a8956e48f17cd.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAU3ByaW5nLV8tQmVhcg==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

## 3.1 用户功能

1. 用户注册：用户需要使用邮箱进行注册，注册过程中需填入正确的邮箱验证码以完成验证功能，注册时默认 100 积分
2. 用户登录：用户登录时可使用注册时使用的邮箱或是自定义的用户名进行登录
3. 密码重置：用户可在登录时忘记密码，使用邮箱进行密码重置，也可在登入系统后重新设置密码
4. 信息修改：用户可以修改自己的头像，维护用户个人基本信息
5. 图书上传：用户可以上传自己收藏的 PDF 图书资源，经网站管理员审核后获取积分，上传一本图书可获得 10 积分
6. 图书下载：用户可以在首页根据书名进行图书检索，下载自己需要的图书资源，下载一本图书减少 10 积分
7. 图书收藏：网站首页点击图书封面可查看图书详情，可对心仪的图书进行收藏方便日后查找，也可取消收藏图书
8. 下载记录：用户每下载一本图书对应一条图书下载记录，记录包含了下载时间、积分变换、下载图书名等信息
9. 上传记录：用户每上传一本图书对应一条图书上传记录，记录包含了上传时间、积分变化、上传图书名等信息
10. 登录记录：显示用户登录记录，记录登录时间、IP、登录地点等信息
11. 背景图片：用户可浏览管理员审核后的背景图书，并加入收藏夹
12. 每日文字：用户可浏览并收藏由第三方一言、今日诗词提供的优美文字句子

## 3.2 管理员功能

1. 图片获取：管理员需利用爬虫技术从 Pixabay 网站获取精美图片审核后作为壁纸资源
2. 管理员登录：只提供管理员登录功能（无注册功能），管理员身份信息直接由站长进行下发
3. 壁纸管理：管理员管理页面背景图片信息上传及维护
4. 图书管理：管理员需对用户上传的电子图书进行管理，包括保存新的图书记录、上传图书、下发积分给用户等操作

# 四、功能演示
## 4.1 用户功能
### 1. 用户注册

给用户名、邮箱输入框绑定失焦事件，失去焦点时判断用户名和邮箱格式，格式正确则向服务器发起 AJAX 请求以验证用户名和邮箱的存在性，存在则友好提示用户更换用户名或邮箱。用户注册时给每个输入框绑定失去焦点事件，使用正则表达式初步验证各表单项是否符合格式要求，不符合要求则阻止表单提交。

![在这里插入图片描述](https://img-blog.csdnimg.cn/641fe3fd4e544d26bd12bd0809ffe612.png#pic_center)


### 2. 邮箱验证码 

使用 `javax.mail.jar` 第三方工具包，实现当用户点击获取按钮时向用户输入的有效邮箱地址发送验证码，验证码时由后台随机生成的六位长字符串，使用 AJAX 发起异步请求。通知栏友好提示各种通知信息

![在这里插入图片描述](https://img-blog.csdnimg.cn/260f8920acbe4d8b985c4f1f405e29e6.png#pic_center)


### 3. 用户登录

用户登录时可选择用户名或邮箱进行登录，使用正则表达式验证用户名或密码不允许为空，验证通过则跳转到系统主页面，验证失败则提示用户用户名或密码错误。


![在这里插入图片描述](https://img-blog.csdnimg.cn/1e352f7a9f7e44329d8a42e61471dd68.png#pic_center)


### 4. 书屋首页

![在这里插入图片描述](https://img-blog.csdnimg.cn/74b1b1482faa481782e2f186302902d6.png#pic_center)


### 5. 分页查看

当处于第一页时禁用上一页，当处于末页时禁用下一页

![在这里插入图片描述](https://img-blog.csdnimg.cn/86fbd49417704141a74fbe9d80000e03.png#pic_center)


### 6. 图书上传

用户登录后可在系统主要上传入口进行电子图书资源的上传。图书上传过程中使用 JavaScript 验证图书文件格式必须为 PDF，图书封面文件格式必须为 JPG 或 PNG，不是正确的格式则不允许上传。用户图书资源上传成功后，提示用户需待管理员审核并发布图书资源后才给用户发放积分，每本图书下发 10 积分。

![在这里插入图片描述](https://img-blog.csdnimg.cn/679a007e4e6744418a0a578834fba494.png#pic_center)


### 7. 图书下载

用户可在图书首页进行图书的下载（点击图书封面图片），请求服务器获取下载 token 数据成功后向七牛服务器发起下请求。图书下载或上传成功，将在首页的小铃铛里通知用户积分变化，下载一本图书减少 10 积分，上传一本图书增加 10 积分，用户注册时默认积分为 100 积分

![在这里插入图片描述](https://img-blog.csdnimg.cn/a60a533ba6b549b4a409ca4f3f25bd54.png#pic_center)


![在这里插入图片描述](https://img-blog.csdnimg.cn/1b90129aabaf48a79559ea234feeb8c8.png#pic_center)


### 8. 记录查看

用户可在首页和个人资料页查看个人历史登录记录，包含了登录 ip、登录时间、登录地点等信息（从百度地图 API 获取）。用户可在首页入口查看个人图书上传和下载记录

![在这里插入图片描述](https://img-blog.csdnimg.cn/471e633aa75542c7b0644646fd93c143.png#pic_center)


----

![在这里插入图片描述](https://img-blog.csdnimg.cn/17475c47c7294125ba3b94f97ccd2ee9.png#pic_center)


---

![在这里插入图片描述](https://img-blog.csdnimg.cn/8c88fd27ada747caa1e455132b9b6b8f.png#pic_center)


### 9. 壁纸图片

图片轮播：浏览精美壁纸图片（图片来源于 Pixabay 网站）


![在这里插入图片描述](https://img-blog.csdnimg.cn/87f23122546a4f8296f5e6947f34262f.png#pic_center)


### 10. 文字句子

从第三方一言和今日诗词 API 处获取小句子和诗词（6s 轮播）


![在这里插入图片描述](https://img-blog.csdnimg.cn/9176c79a60b34a20ae541da2af6f3f72.png#pic_center)


## 4.2 管理员功能

### 1. 管理员登录

![在这里插入图片描述](https://img-blog.csdnimg.cn/b162a5c75d7546dea43dbac01bda3a80.png#pic_center)


### 2. Pixabay

利用 pixabay 网站提供的 API 以及 Python 爬虫技术，定期定量从网站获取精美图片作为背景图。对获取到的 pixabay 网站图书资源进行管理时，需要管理员进行登录，未登录则提示管理员。 管理员登录时验证用户名及密码是否正确。

![在这里插入图片描述](https://img-blog.csdnimg.cn/8e4535fc1c484f759bd441690c3b9316.png#pic_center)


![在这里插入图片描述](https://img-blog.csdnimg.cn/1b9b65b5c29e437ca840540797926b56.png#pic_center)


### 3. 壁纸上传

![在这里插入图片描述](https://img-blog.csdnimg.cn/349f920d7fe5473bbab89f578a52e9a4.png#pic_center)


# 五、技术选型

## 5.1. 涉及技术说明

> 前端：HTML、CSS、JavaScript、jQuery、AJAX、Regular Expression、Bootstrap

> 后端：Spring、SpringMVC、MyBatis、Tomcat、MySQL、Maven、Servlet、Logback、Junit、Jackson、Druid、Mail、Lombok、Qiniu、PageHelper、Thymeleaf、Python、JDBC、BaiduMap、Git

## 5.2. 开发工具说明

> IDEA 2021.3.3、jdk 1.8.0_311、MySQL5.7.19、Maven3.8.4、Git 2.34.1、Tomcat 8.0.50、XShell 6、Xftp6、Typora 0.11.13、Snipaste  、ioDraw、Navicat、CentOS7.6

# 六、免责声明
本站所有 PDF 图书资源均来源于用户上传或由站长收集于网络，本站只是搜录整理他人成果。若有违规侵权，请第一时间联系站长 springbear2020@163.com ，站长将第一时间清除对应图书资源，感谢！本站图书资源仅作学习交流分享使用，不作任何商用用途。

# 七、TODO

| Num  | 事项                                       |
| :--: | :----------------------------------------- |
|  1   | 用户个人资料模块                           |
|  2   | 图书收藏                                   |
|  3   | 图书信息管理维护                           |
|  4   | 密码找回                                   |
|  5   | 通过邮箱或是用户名登录                     |
|  6   | 服务端设置验证码有效时长                   |
|  7   | 图书资源反爬虫爬取（下载图书时图片验证码） |
|  8   | 首页加载图书方式改为随机加载               |
|  9   | 对用户上传的图书信息进行审核               |
|  10  | 图书上传下载积分制（10）                   |
|  11  | 壁纸、文字收藏和取消收藏功能               |
|  12  | 图书、壁纸直接下载功能                     |
|  13  | 首页免责声明、友情链接信息                 |
|  14  | 服务端 Python 爬虫无法运行的问题           |
|  15  | 用户、图书、壁纸等信息的维护与显示         |
|  16  | 管理员登录记录                             |
|  17  | 服务端添加拦截器、异常处理器以提供友好提示 |
|  18  | 图书搜索提供多种检索方式                   |
|  19  | 用户消息通知功能                           |
|  20  | 图书点击量统计方式（用户未登录时也统计）   |
|  21  | 壁纸页面点击切换图片功能                   |
|  22  | 用户上传背景图片                           |
|  23  | 图书资源有效时长鉴权                       |
|  24  | 深、浅主题风格切换                         |
