<p align="center">
    <img src="https://img.shields.io/static/v1?label=%E5%BC%80%E5%8F%91%E6%97%B6%E9%97%B4&message=22/04/21-22/05/13&color=007bff"/>
    <img src="https://img.shields.io/static/v1?label=Spring&message=5.3.1&color=dc3545"/>
    <img src="https://img.shields.io/static/v1?label=MyBatis&message=3.5.7&color=ffc107"/>
    <img src="https://img.shields.io/static/v1?label=Bootstrap&message=3.4.1&color=17a2b8"/> 
    <a href="https://github.com/springbear2020/book-house-ssm" target="_blank">
        <img src="https://img.shields.io/static/v1?label=%E5%BC%80%E6%BA%90%E9%A1%B9%E7%9B%AE&message=book-house-ssm&color=18a745"/>
    </a>
</p>

# 一、快速开始

1. 克隆仓库：使用 Git 克隆仓库或直接下载仓库压缩包到您的计算机来获取源代码。
2. 打开工程：使用 IntelliJ IDEA 开发工具打开克隆的仓库或解压的工程文件，而后使用 Maven 项目构建工具更新工程模块依赖。
3. 创建数据库并导入数据：登录 MySQL 控制台，创建一个名为 panda_book_house 的数据库，并将 document/sql/ panda_book_house.sql 脚本中的建表语句和数据导入到 panda_book_house 数据库中。
4. 修改配置：
   - 修改 src/main/resources/jdbc.properties 文件中的数据库连接信息，设置你自己的数据库用户名和密码。
   - 修改 src/main/resources/email.properties 中的邮箱服务器信息，设置你自己的邮箱账号和服务器。
5. 部署访问：在 IntelliJ IDEA 中部署 Tomcat 并启动即可访问熊猫书屋首页。
6. 登录系统：系统的默认用户名和密码均为 admin。

# 二、背景调查

随着互联网技术的飞速发展，网络上充斥着越来越多的有用或是无用的资源，要在不计其数的资源中筛选出自己亟需的资源，需要耗费巨大的精力和时间。

尤其是各种电子图书资源（主要为 PDF）还涉及到知识产权的法律性问题，导致截至目前为止几乎没有哪个产品能完全满足快速检索电子图书资源和提供相关服务的需求。

一般情况下用户所需的电子图书资源都存放在用户的个人云盘中，现有的网站系统几乎是将所有的下载链接进行糅合，杂乱无章的整合让普通用户无所适从、望而却步。

对于急需查找电子图书的用户，需要到从繁杂的网页中检索出需要的图书的链接，再根据链接跳转到对应的云盘进行下载。不得不说，这样的方式对一般用户极其不友好，图书资源也不够规范化、有的是盗版图书、有的下载链接早已失效······ 极大程度浪费了用户的精力和时间。

因而我们的熊猫书屋系统应运而生，本着开源共享的精神，本站所有的图书资源均由用户上传，站长负责整理发布，只为用户提供一个专业的 PDF 书屋（我们的设想是在不久的将来能联系作者本人解决图书的知识产权问题，尊重知识创造）。

免责声明：本站所有 PDF 图书资源均来源于用户上传或由站长收集于网络，本站只是搜录整理他人成果。若有违规侵权，请第一时间联系站长，站长将第一时间清除对应图书资源，感谢！本站图书资源仅作学习交流分享使用，不作任何商用用途。

# 三、功能描述

![](document/img/3.1%20系统功能概览.png)

## 3.1 用户功能

1. 用户注册：用户需要使用邮箱进行注册，注册过程中需填入正确的邮箱验证码以完成验证功能，注册时默认 100 积分。
2. 用户登录：用户登录时可使用注册时使用的邮箱或是自定义的用户名进行登录。
3. 密码重置：用户可在登录时忘记密码，使用邮箱进行密码重置，也可在登入系统后重新设置密码。
4. 信息修改：用户可以修改自己的头像，维护用户个人基本信息。
5. 图书上传：用户可以上传自己收藏的 PDF 图书资源，经网站管理员审核后获取积分，上传一本图书可获得 10 积分。
6. 图书下载：用户可以在首页根据书名进行图书检索，下载自己需要的图书资源，下载一本图书减少 10 积分。
7. 图书收藏：网站首页点击图书封面可查看图书详情，可对心仪的图书进行收藏方便日后查找，也可取消收藏图书。
8. 下载记录：用户每下载一本图书对应一条图书下载记录，记录包含了下载时间、积分变换、下载图书名等信息。
9. 上传记录：用户每上传一本图书对应一条图书上传记录，记录包含了上传时间、积分变化、上传图书名等信息。
10. 登录记录：显示用户登录记录，记录登录时间、IP、登录地点等信息。
11. 背景图片：用户可浏览管理员审核后的背景图书，并加入收藏夹。
12. 每日文字：用户可浏览并收藏由第三方一言、今日诗词提供的优美文字句子。

## 3.2 管理员功能

1. 图片获取：管理员需利用爬虫技术从 Pixabay 网站获取精美图片审核后作为壁纸资源。
2. 管理员登录：只提供管理员登录功能（无注册功能），管理员身份信息直接由站长进行下发。
3. 壁纸管理：管理员管理页面背景图片信息上传及维护。
4. 图书管理：管理员需对用户上传的电子图书进行管理，包括保存新的图书记录、上传图书、下发积分给用户等操作。

# 四、效果展示

## 4.1 用户功能

### 4.1.1 用户注册

给用户名、邮箱输入框绑定失焦事件，失去焦点时判断用户名和邮箱格式，格式正确则向服务器发起 AJAX 请求以验证用户名和邮箱的存在性，存在则友好提示用户更换用户名或邮箱。用户注册时给每个输入框绑定失去焦点事件，使用正则表达式初步验证各表单项是否符合格式要求，不符合要求则阻止表单提交。

![](document/img/4.1%20用户注册.png)

### 4.1.2 邮箱验证码

使用  javax.mail.jar  第三方工具包，实现当用户点击获取按钮时向用户输入的有效邮箱地址发送验证码，验证码时由后台随机生成的六位长字符串，使用 AJAX 发起异步请求。通知栏友好提示各种通知信息。

![](document/img/4.2%20邮箱验证码.png)

### 4.1.3 用户登录

用户登录时可选择用户名或邮箱进行登录，使用正则表达式验证用户名或密码不允许为空，验证通过则跳转到系统主页面，验证失败则提示用户用户名或密码错误。

![](document/img/4.3%20用户登录.png)

### 4.1.4 系统首页

![](document/img/4.4%20系统首页.png)

### 4.1.5 数据分页

当处于第一页时禁用上一页，当处于末页时禁用下一页。

![](document/img/4.5%20数据分页.png)

### 4.1.6 上传图书

用户登录后可在系统主要上传入口进行电子图书资源的上传。图书上传过程中使用 JavaScript 验证图书文件格式必须为 PDF，图书封面文件格式必须为 JPG 或 PNG，不是正确的格式则不允许上传。用户图书资源上传成功后，提示用户需待管理员审核并发布图书资源后才给用户发放积分，每本图书下发 10 积分。

![](document/img/4.6%20上传图书.png)

### 4.1.7 下载图书

用户可在图书首页进行图书的下载（点击图书封面图片），请求服务器获取下载 token 数据成功后向七牛服务器发起下请求。图书下载或上传成功，将在首页的小铃铛里通知用户积分变化，下载一本图书减少 10 积分，上传一本图书增加 10 积分，用户注册时默认积分为 100 积分。

![](document/img/4.7%20下载图书.png)

### 4.1.8 记录查看

用户可在首页和个人资料页查看个人历史登录记录，包含了登录 ip、登录时间、登录地点等信息（从百度地图 API 获取）。用户可在首页入口查看个人图书上传和下载记录。

![](document/img/4.8%20记录查看.png)

### 4.1.9 壁纸图片

以图片轮播的方式浏览精美壁纸图片，图片来源于 Pixabay 网站。

![](document/img/4.9%20壁纸查看.png)

### 4.1.10 文字句子

从第三方一言和今日诗词 API 处获取小句子和诗词（6s 轮播）。

![](document/img/4.10%20文字句子.png)

## 4.2 管理员功能

### 4.2.1 Pixabay 图片管理

利用 pixabay 网站提供的 API 以及 Python 爬虫技术，定期定量从网站获取精美图片作为背景图。对获取到的 pixabay 网站图书资源进行管理时，需要管理员进行登录，未登录则提示管理员。 管理员登录时验证用户名及密码是否正确。

![](document/img/4.11%20Pixabay.png)

### 4.2.3 背景图片上传

![](document/img/4.12%20壁纸上传.png)

# 五、技术选型

|                           前端技术                           |
| :----------------------------------------------------------: |
| HTML、CSS、JavaScript、jQuery、AJAX、Regular Expression、Bootstrap |

|                           后端技术                           |
| :----------------------------------------------------------: |
| Spring、SpringMVC、MyBatis、Tomcat、MySQL、Maven、Servlet、Logback、Junit、Jackson、Druid、Mail、Lombok、Qiniu、PageHelper、Thymeleaf、Python、JDBC、BaiduMap、Git |

|                           开发工具                           |
| :----------------------------------------------------------: |
| IDEA 2021.3.3、jdk 1.8.0_311、MySQL5.7.19、Maven3.8.4、Git 2.34.1、Tomcat 8.0.50、XShell6、Xftp6、Typora 0.11.13、Snipaste  、ioDraw、Navicat、CentOS7.6 |

# 六、待办事项

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

# 七、许可证

```
MIT License

Copyright (c) 2023 Spring-_-Bear

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

