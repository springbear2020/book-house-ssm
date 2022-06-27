/*
 Navicat Premium Data Transfer

 Source Server         : root-localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : panda_book_house

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 27/06/2022 13:41:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for log_login
-- ----------------------------
DROP TABLE IF EXISTS `log_login`;
CREATE TABLE `log_login`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '登录日志 id',
  `user_id` int NOT NULL COMMENT '用户 id',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `ip` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录 ip',
  `location` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录位置',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '登录时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of log_login
-- ----------------------------
INSERT INTO `log_login` VALUES (1, 1, 'bear', '127.0.0.1', '未知地点', '2022-06-27 13:26:58');

-- ----------------------------
-- Table structure for t_background
-- ----------------------------
DROP TABLE IF EXISTS `t_background`;
CREATE TABLE `t_background`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '背景 id',
  `user_id` int NOT NULL COMMENT '用户 id（外键约束于 t_user）',
  `status` int NOT NULL COMMENT '记录状态（正常、异常）',
  `upload_time` date NOT NULL COMMENT '添加时间',
  `url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '访问地址 url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_background
-- ----------------------------
INSERT INTO `t_background` VALUES (1, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133757.png');
INSERT INTO `t_background` VALUES (2, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133801.png');
INSERT INTO `t_background` VALUES (3, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133804.png');
INSERT INTO `t_background` VALUES (4, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133808.png');
INSERT INTO `t_background` VALUES (5, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133812.png');
INSERT INTO `t_background` VALUES (6, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133817.png');
INSERT INTO `t_background` VALUES (7, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133820.png');
INSERT INTO `t_background` VALUES (8, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133824.png');
INSERT INTO `t_background` VALUES (9, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133827.png');
INSERT INTO `t_background` VALUES (10, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133830.png');
INSERT INTO `t_background` VALUES (11, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133833.png');
INSERT INTO `t_background` VALUES (12, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133905.png');
INSERT INTO `t_background` VALUES (13, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133910.png');
INSERT INTO `t_background` VALUES (14, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133913.png');
INSERT INTO `t_background` VALUES (15, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133919.png');
INSERT INTO `t_background` VALUES (16, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133922.png');
INSERT INTO `t_background` VALUES (17, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133925.png');
INSERT INTO `t_background` VALUES (18, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133930.png');
INSERT INTO `t_background` VALUES (19, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133933.png');
INSERT INTO `t_background` VALUES (20, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133936.png');
INSERT INTO `t_background` VALUES (21, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627133939.png');
INSERT INTO `t_background` VALUES (22, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134021.png');
INSERT INTO `t_background` VALUES (23, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134024.png');
INSERT INTO `t_background` VALUES (24, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134027.png');
INSERT INTO `t_background` VALUES (25, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134031.png');
INSERT INTO `t_background` VALUES (26, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134034.png');
INSERT INTO `t_background` VALUES (27, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134037.png');
INSERT INTO `t_background` VALUES (28, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134040.png');
INSERT INTO `t_background` VALUES (29, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134043.png');
INSERT INTO `t_background` VALUES (30, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134047.png');
INSERT INTO `t_background` VALUES (31, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134049.png');
INSERT INTO `t_background` VALUES (32, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134053.png');
INSERT INTO `t_background` VALUES (33, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134056.png');
INSERT INTO `t_background` VALUES (34, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134100.png');
INSERT INTO `t_background` VALUES (35, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134103.png');
INSERT INTO `t_background` VALUES (36, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134106.png');
INSERT INTO `t_background` VALUES (37, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134110.png');
INSERT INTO `t_background` VALUES (38, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134113.png');
INSERT INTO `t_background` VALUES (39, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134117.png');
INSERT INTO `t_background` VALUES (40, 1, 0, '2022-06-27', 'static/wallpaper/1-20220627134120.png');

-- ----------------------------
-- Table structure for t_book
-- ----------------------------
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '图书 id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书名',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作者',
  `translator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '译者',
  `downloads` int NOT NULL COMMENT '下载量',
  `cover_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '封面保存路径',
  `comments` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图书评价',
  `book_state` int NOT NULL COMMENT '图书状态（上架、下架、待处理）',
  `upload_user_id` int NOT NULL COMMENT '上传用户 id',
  `upload_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '上传用户名',
  `upload_time` date NOT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_book
-- ----------------------------
INSERT INTO `t_book` VALUES (1, '克拉拉与太阳', '[英] 石黑一雄', '宋佥', 1666, 'static/cover/1-20220627132825.png', '“太阳总有办法照到我们，不管我们在哪里。”\r\n\r\n~\r\n\r\n克拉拉是一个专为陪伴儿童而设计的太阳能人工智能机器人（AF），具有极高的观察、推理与共情能力。她坐在商店展示橱窗里，注视着街头路人以及前来浏览橱窗的孩子们的一举一动。她始终期待着很快就会有人挑中她，不过，当这种永久改变境遇的可能性出现时，克拉拉却被提醒不要过分相信人类的诺言。\r\n\r\n在《克拉拉与太阳》这部作品中，石黑一雄通过一位令人难忘的叙述者的视角，观察千变万化的现代社会，探索了一个根本性的问题：究竟什么是爱？\r\n\r\n~\r\n\r\n“你相信有‘人心’这回事吗？\r\n\r\n我不仅仅是指那个器官，当然喽。\r\n\r\n我说的是这个词的文学意义。\r\n\r\n人心。你相信有这样东西吗？\r\n\r\n某种让我们每个人成为独特个体的东西？”', 0, 1, 'bear', '2022-06-27');
INSERT INTO `t_book` VALUES (2, '字母表谜案', '大山诚一郎', '曹逸冰', 2429, 'static/cover/1-20220627132903.png', '一座神秘的公寓，不定期举行推理合战。红茶、曲奇，搭配寒意逼人的谜案，与案件相关的字母似乎是重要线索。一群特殊的房客，联手组成侦探团，秘密追踪警视厅也难以锁定的幕后真凶。\r\n\r\n有被毒妄想的贵妇人，竟应验般惨遭毒杀？\r\n\r\n午夜零点神秘来电后，美术馆密室惊现死尸。\r\n\r\n豪华游轮杀人现场，诡异的死亡留言无人能解。\r\n\r\n绑架案中遇害幼童父亲的手记，字里行间令人细思极恐……\r\n\r\n越细微的异常，越接近真相，也越令人不寒而栗！', 0, 1, 'bear', '2022-06-27');
INSERT INTO `t_book` VALUES (3, '从零开始的女性主义', '[日本] 上野千鹤子 / 田房永子', '吕灵芝', 528, 'static/cover/1-20220627132934.png', '女性主义是什么？为什么我们需要女性主义？如何用女性主义进行思考？\r\n\r\n围绕上述种种问题，日本著名学者上野千鹤子与知名漫画家田房永子，开始了一场从零开始的女性主义课堂。她们用幽默的语言、传神的漫画，回溯女性主义的发展历程，畅谈母女关系、性、工作、婚恋、育儿等日常生活中的议题，驳斥关于女性的刻板偏见。\r\n\r\n这堂课不仅开启了一趟全新的旅程，更告诉我们：女性主义，不仅仅关乎女性，更关乎每个人的日常生活。', 0, 1, 'bear', '2022-06-27');
INSERT INTO `t_book` VALUES (4, '置身事内', '兰小欢', '', 6078, 'static/cover/1-20220627133015.png', '本书是是兰小欢多年教学、调研与研究内容的凝练，将经济学原理与中国经济发展的实践有机融合，以地方政府投融资为主线，深入浅出地论述了中国经济的发展，笔触简练客观，并广泛采纳了各领域学者的最新研究成果。全书分上下两篇。上篇解释微观机制，包括地方政府的基本事务、收支、土地融资和开发、投资和债务等；下篇解释这些微观行为与宏观现象的联系，包括城市化和工业化、房价、地区差异、债务风险、国内经济结构失衡、国际贸易冲突等。最后一章提炼和总结全书内容。', 0, 1, 'bear', '2022-06-27');
INSERT INTO `t_book` VALUES (5, '也许你该找个人聊聊', '[美]洛莉·戈特利布', '张含笑', 2881, 'static/cover/1-20220627133046.png', '本书是2019年美国众多媒体推荐的心理自助书——它荣登《纽约时报》《时代》《人物》《综艺》等媒体年度必读书榜单，是美国亚马逊当年度销售TOP10图书、哈佛商学院年度推荐图书，它的有声书版本也是当年Audible非虚构类有声书第一名。刚上市一个月，这本书就冲上了美国亚马逊图书总榜TOP100和《纽约时报》畅销榜；出版两年来，已经在全球授权了四十多个语言版本，总销量超过七十万册，并且正在拍摄电视连续剧，由曾出演《绝望主妇》的知名演员伊娃·朗格利亚担纲主演。', 0, 1, 'bear', '2022-06-27');
INSERT INTO `t_book` VALUES (6, '刘擎西方现代思想讲义', '刘擎', '', 5780, 'static/cover/1-20220627133143.png', '人生的意义，人们向往的自由和公平的价值，人类文明的复杂冲突与未来趋势……这些让你困惑的大小问题，过去也困扰过韦伯、尼采、萨特等杰出的头脑。他们尽最大努力做出阐释，为后人提供了宝贵的思想标识。在这部讲义里，刘擎介绍了现代视域下的19位思想大家，广泛而系统地讨论工具理性的利弊，如何面对虚无主义，消费主义对人的异化，财富分配的公平正义和全球化等议题。思想不惑，精神明亮。你将在这19位大家的生平故事中，理解他们建构思想大厦的地基与框架。你还会在思想大厦之上，直面个人生活和社会公共领域的诸多难题，收获审慎而真诚的回答。', 0, 1, 'bear', '2022-06-27');
INSERT INTO `t_book` VALUES (7, '下沉年代', '[美] 乔治·帕克', '刘冉', 6705, 'static/cover/1-20220627133220.png', '阅读《下沉年代》，如同坐在第一排观看美国梦的午夜葬礼\r\n\r\n“这是一个时代苍凉的侧影：受挫的努力，被辜负的信任，凋零的生机，以及日渐黯淡的希望。力透纸背的书写，栩栩如生的人物，呈现了美国三十多年的沧桑巨变，也为当下社会撕裂的悲剧写下了发人深省的前传。”——刘擎，华东师范大学教授', 0, 1, 'bear', '2022-06-27');
INSERT INTO `t_book` VALUES (8, '文城', '余华', '', 7877, 'static/cover/1-20220627133307.png', '★时隔八年，余华全新长篇重磅归来！\r\n\r\n◆人生就是自己的往事和他人的序章\r\n\r\n时代的洪流推着每个人做出各自的选择。\r\n\r\n这是一个荒蛮的年代，结束的尚未结束，开始的尚未开始。\r\n\r\n◆我们总是在不同时代、不同国家、不同语言的作家那里，读到自己的感受，甚至是自己的生活。假如文学中真的存在某些神秘的力量，我想可能就是这些。\r\n\r\n——余华', 0, 1, 'bear', '2022-06-27');
INSERT INTO `t_book` VALUES (9, '海边的房间', '黄丽群', '', 6622, 'static/cover/1-20220627133330.png', '台湾新生代小说家黄丽群的代表短篇小说集，十二个坏掉的人，十二个令人倒吸一口凉气的好故事。小说家熬制典雅细密的汉语，精巧布局，将人间悲欢斩落整齐，写出一个城市畸爱者的幽冷世界：老公寓里的弃女和养父，乡间卜算师与患病的儿子，梦游的宅男，中年独居女人和三花猫……语言的俏皮与一 个个意料之外被冻住的结尾，以及对平凡人事细致入微的体察，构成作品特有的文字张力。无常往往最平常，老灵魂的世情书写，温热冷艳，拨动平凡市井里的人心与天机，失意人的情欲与哀伤，我们日常的困顿与孤独。', 0, 1, 'bear', '2022-06-27');
INSERT INTO `t_book` VALUES (10, '桶川跟踪狂杀人事件', '[日] 清水洁', '王华懋', 8943, 'static/cover/1-20220627133404.png', '为了回应被害者的「遗言」而开始活动的清水记者，带着甚至是「愚直」的执念，最终将犯人和警察双方逼迫得走投无路，真的很精彩。我认为这才是记者的精神，这就是正义。我不常使用“感动”、“正义”等词语（而我这次使用了），所以我关于这部著作的感想，应该可以得到大家的信任。\r\n\r\n——明治大学法科大学院教授 瀬木比吕志', 0, 1, 'bear', '2022-06-27');
INSERT INTO `t_book` VALUES (11, '寻找米兰·昆德拉', '[法] 阿丽亚娜·舍曼', '王东亮', 5165, 'static/cover/1-20220627133526.png', '本书作者阿丽亚娜·舍曼从二十岁起，就渴望能与《玩笑》的作者昆德拉相遇。为了追寻昆德拉的足迹，她一直走在寻找米兰·昆德拉的道路上，从东欧到西欧、从布拉格到雷恩、从科西嘉到美丽岛往返穿行。她结识了昆德拉的夫人薇拉，与她一起追忆作家的往昔岁月。她采访过知名出版商、电影人、电视主播，凭吊过遇害的作曲家和钢琴家，接触过年迈的持不同政见者和金盆洗手的间谍特工，也聆听过德斯诺斯和阿波利奈尔的诗篇……她通过作品阅读昆德拉的人生，又通过小说家的人生去解读他的作品，而这样的作品和人生，经过几许翻译迷失，从此分裂在两个祖国之间。', 0, 1, 'bear', '2022-06-27');
INSERT INTO `t_book` VALUES (12, '活着', '余华', '', 6688, 'static/cover/1-20220627133604.png', '活着(新版)》讲述了农村人福贵悲惨的人生遭遇。福贵本是个阔少爷，可他嗜赌如命，终于赌光了家业，一贫如洗。他的父亲被他活活气死，母亲则在穷困中患了重病，福贵前去求药，却在途中被国民党抓去当壮丁。经过几番波折回到家里，才知道母亲早已去世，妻子家珍含辛茹苦地养大两个儿女。此后更加悲惨的命运一次又一次降临到福贵身上，他的妻子、儿女和孙子相继死去，最后只剩福贵和一头老牛相依为命，但老人依旧活着，仿佛比往日更加洒脱与坚强。', 0, 1, 'bear', '2022-06-27');

-- ----------------------------
-- Table structure for t_pixabay
-- ----------------------------
DROP TABLE IF EXISTS `t_pixabay`;
CREATE TABLE `t_pixabay`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '图片 id',
  `condition` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '检索条件',
  `tags` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签',
  `views` int NULL DEFAULT NULL COMMENT '浏览量',
  `downloads` int NULL DEFAULT NULL COMMENT '下载量',
  `collections` int NULL DEFAULT NULL COMMENT '收藏量',
  `likes` int NULL DEFAULT NULL COMMENT '点赞量',
  `comments` int NULL DEFAULT NULL COMMENT '评论量',
  `add_time` date NULL DEFAULT NULL COMMENT '添加时间',
  `url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片访问地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_pixabay
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户 id',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱地址',
  `type` int NOT NULL COMMENT '用户类型（普通用户、管理员）',
  `status` int NOT NULL COMMENT '用户状态（正常、异常）',
  `register_date` date NOT NULL COMMENT '注册时间',
  `portrait_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '头像保存路径',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_user_email`(`email` ASC) USING BTREE,
  UNIQUE INDEX `uniq_user_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'bear', 'springbear', 'springbear2020@163.com', 0, 0, '2022-06-27', 'static/img/portrait.png');

SET FOREIGN_KEY_CHECKS = 1;
