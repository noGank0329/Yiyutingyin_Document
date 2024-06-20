/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : yiyulisten

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 10/12/2023 22:12:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog_comments
-- ----------------------------
DROP TABLE IF EXISTS `blog_comments`;
CREATE TABLE `blog_comments`  (
  `comment_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '评论标识',
  `blog_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户所发的帖子编号',
  `user_id` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论该帖子的用户标识',
  `comment_content` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论内容',
  `comment_time` datetime(0) NOT NULL COMMENT '评论时间',
  PRIMARY KEY (`comment_id`, `blog_id`) USING BTREE,
  INDEX `comments_blog_id`(`blog_id`) USING BTREE,
  INDEX `comments_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `comments_blog_id` FOREIGN KEY (`blog_id`) REFERENCES `blog_posts` (`blog_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `comments_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_comments
-- ----------------------------
INSERT INTO `blog_comments` VALUES (5, '00264814634274525184', '1234', 'zyb确实是byd', '2023-12-10 14:50:45');
INSERT INTO `blog_comments` VALUES (6, '00264814634274525184', '1234', 'zyb确实是byd', '2023-12-10 14:50:45');
INSERT INTO `blog_comments` VALUES (8, '00264814634274525184', '1234', 'zyb确实是byd', '2023-12-10 14:50:45');
INSERT INTO `blog_comments` VALUES (9, '00264814634274525184', '1234', 'zyb确实是byd', '2023-12-10 14:50:45');
INSERT INTO `blog_comments` VALUES (10, '00264814634274525184', '1234', 'zyb确实是byd', '2023-12-10 14:50:45');
INSERT INTO `blog_comments` VALUES (11, '00264814634274525184', '1234', 'zyb确实是byd', '2023-12-10 14:50:45');

-- ----------------------------
-- Table structure for blog_posts
-- ----------------------------
DROP TABLE IF EXISTS `blog_posts`;
CREATE TABLE `blog_posts`  (
  `blog_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子编号',
  `user_id` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `blog_title` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '帖子题目',
  `blog_content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '帖子内容',
  `blog_time` datetime(0) NULL DEFAULT NULL COMMENT '发布日期',
  `blog_collect_num` int(0) NULL DEFAULT 0 COMMENT '收藏数量',
  `blog_comment_num` int(0) NULL DEFAULT 0 COMMENT '评论数量',
  `blog_status` int(0) NULL DEFAULT 0 COMMENT '帖子状态，0代表待审核，1代表合规，2代表不通过，初始值都为0，需要管理员审核',
  PRIMARY KEY (`blog_id`) USING BTREE,
  INDEX `blog_id`(`blog_id`) USING BTREE,
  INDEX `blog_posts_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `blog_posts_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_posts
-- ----------------------------
INSERT INTO `blog_posts` VALUES ('00264814634274525184', '123', 'zyb是byd', '震惊！zyb承认自己是byd！！！', '2023-01-25 14:30:45', 1, 5, 1);
INSERT INTO `blog_posts` VALUES ('00264846200128278528', '1234', 'lj是byd', 'lj承认自己是byd！！！', '2023-01-25 14:30:45', 0, 0, 1);
INSERT INTO `blog_posts` VALUES ('00264846496216780800', '123', 'zyb是byd', '震惊！zyb承认自己是byd！！！', '2023-01-25 14:30:45', 0, 0, 1);
INSERT INTO `blog_posts` VALUES ('00264881854816063488', '123', 'zyb是byd', '震惊！zyb承认自己是byd！！！', '2023-01-25 14:30:45', 0, 0, 0);

-- ----------------------------
-- Table structure for feedback_info
-- ----------------------------
DROP TABLE IF EXISTS `feedback_info`;
CREATE TABLE `feedback_info`  (
  `feedback_id` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '反馈编号',
  `user_id` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `feedback` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '反馈内容',
  `feedback_type` int(0) NULL DEFAULT NULL COMMENT '类型，0，1，2(功能建议，投诉意见，其他反馈)',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '反馈时间',
  PRIMARY KEY (`feedback_id`) USING BTREE,
  INDEX `feedback_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `feedback_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for music_info
-- ----------------------------
DROP TABLE IF EXISTS `music_info`;
CREATE TABLE `music_info`  (
  `music_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '音乐编号',
  `music_name` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '音乐名称',
  `music_type` int(0) NULL DEFAULT NULL COMMENT '一级曲库类别，0,1,2代表三个一级级别曲库',
  `music_style` int(0) NULL DEFAULT NULL COMMENT '音乐风格（二级曲库类别）',
  PRIMARY KEY (`music_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for news_info
-- ----------------------------
DROP TABLE IF EXISTS `news_info`;
CREATE TABLE `news_info`  (
  `message_id` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资讯编号',
  `message_time` time(0) NULL DEFAULT NULL COMMENT '资讯发布时间',
  `message_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资讯标题',
  `message_content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资讯内容',
  `message_type` int(0) NULL DEFAULT NULL COMMENT '资讯类型，0，1，2代表(科普，专家介绍，平台通告)',
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pure_tone_audiometry_history
-- ----------------------------
DROP TABLE IF EXISTS `pure_tone_audiometry_history`;
CREATE TABLE `pure_tone_audiometry_history`  (
  `test_id` int(0) NOT NULL COMMENT '每条记录的id，唯一标识',
  `user_id` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `all_score` int(0) NULL DEFAULT NULL COMMENT '左右耳的总分',
  `left_score` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '左耳六次测试时的各个分数（100-分贝值）',
  `right_score` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '右耳六次测试时的各个分数（100-分贝值）',
  `date` datetime(0) NULL DEFAULT NULL COMMENT '测试时间',
  PRIMARY KEY (`test_id`) USING BTREE,
  INDEX `pure_tone_audiometry_history_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `pure_tone_audiometry_history_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for questionnaire_history
-- ----------------------------
DROP TABLE IF EXISTS `questionnaire_history`;
CREATE TABLE `questionnaire_history`  (
  `appraisal_id` int(0) NOT NULL COMMENT '每条记录的id，唯一标识',
  `user_id` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '5个元素，每个元素用数字1-4代表ABCD',
  `score` int(0) NULL DEFAULT NULL COMMENT '主观评分',
  `date` datetime(0) NULL DEFAULT NULL COMMENT '测试时间',
  PRIMARY KEY (`appraisal_id`) USING BTREE,
  INDEX `questionnaire_history_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `questionnaire_history_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_collections
-- ----------------------------
DROP TABLE IF EXISTS `user_collections`;
CREATE TABLE `user_collections`  (
  `user_id` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `blog_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户收藏的博客id',
  PRIMARY KEY (`user_id`, `blog_id`) USING BTREE,
  INDEX `collections_blog_id`(`blog_id`) USING BTREE,
  CONSTRAINT `collections_blog_id` FOREIGN KEY (`blog_id`) REFERENCES `blog_posts` (`blog_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `collections_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_collections
-- ----------------------------
INSERT INTO `user_collections` VALUES ('123', '00264814634274525184');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `user_id` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '身份证',
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `user_gender` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `user_nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `user_type` int(10) UNSIGNED ZEROFILL NULL DEFAULT 0000000000 COMMENT '用户类型，0代表用户，1代表医生，2代表管理员',
  `user_password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1127', 'shj', '男', 'cs', 0000000000, '123456');
INSERT INTO `user_info` VALUES ('123', 'zyb', '女', 'cjb', 0000000000, '123456');
INSERT INTO `user_info` VALUES ('1234', 'lj', '男', 'jz', 0000000001, '123456');

SET FOREIGN_KEY_CHECKS = 1;
