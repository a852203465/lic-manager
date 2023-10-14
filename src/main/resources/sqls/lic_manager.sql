SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for keystore
-- ----------------------------
# DROP TABLE IF EXISTS `keystore`;
CREATE TABLE IF NOT EXISTS `keystore`  (
    `id` bigint NOT NULL COMMENT '主键',
    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
    `validity` int NULL DEFAULT 1 COMMENT '私钥证书有效期(单位：年), 默认：1',
    `store_pwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '秘钥库密码',
    `private_pwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '私钥密码',
    `public_pwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公钥密码',
    `private_key` blob NULL COMMENT '私钥',
    `public_key` blob NULL COMMENT '公钥',
    `created_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `created_time` bigint NULL DEFAULT NULL COMMENT '创建时间',
    `updated_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `updated_time` bigint NULL DEFAULT NULL COMMENT '更新时间',
    `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_unique`(`name` ASC) USING BTREE COMMENT '唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '秘钥库' ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of keystore
-- ----------------------------

-- ----------------------------
-- Table structure for license
-- ----------------------------
# DROP TABLE IF EXISTS `license`;
CREATE TABLE IF NOT EXISTS `license`  (
  `id` bigint NOT NULL COMMENT '主键',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `subject` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '证书主题',
  `private_alias` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'privatekeys' COMMENT '私钥别名',
  `app_code` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '申请码',
  `lic` blob NULL COMMENT '授权文件',
  `gen_time` bigint NULL DEFAULT NULL COMMENT '生成时间',
  `expired_time` bigint NULL DEFAULT NULL COMMENT '过期时间',
  `consumer_amount` int NULL DEFAULT 1 COMMENT '用户数量',
  `check_ip_address` int NULL DEFAULT 1 COMMENT '验证IP(0:否,1:是)',
  `check_mac_address` int NULL DEFAULT 1 COMMENT '验证MAC(0:否,1:是)',
  `created_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` bigint NULL DEFAULT NULL COMMENT '创建时间',
  `updated_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` bigint NULL DEFAULT NULL COMMENT '更新时间',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_unique`(`project_id` ASC, `name` ASC) USING BTREE COMMENT '唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'license信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of license
-- ----------------------------

-- ----------------------------
-- Table structure for project
-- ----------------------------
# DROP TABLE IF EXISTS `project`;
CREATE TABLE IF NOT EXISTS `project`  (
  `id` bigint NOT NULL COMMENT '主键',
  `keystore_id` bigint NOT NULL COMMENT '秘钥库ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `company` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司',
  `contact` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `created_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` bigint NULL DEFAULT NULL COMMENT '创建时间',
  `updated_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` bigint NULL DEFAULT NULL COMMENT '更新时间',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_unique`(`keystore_id` ASC, `name` ASC) USING BTREE COMMENT '唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
# DROP TABLE IF EXISTS `user_info`;
CREATE TABLE IF NOT EXISTS `user_info`  (
  `id` bigint NOT NULL COMMENT '主键',
  `account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `mail` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `sex` int NULL DEFAULT NULL COMMENT '性别  男：0，女：1',
  `telephone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `status` int NULL DEFAULT 1 COMMENT '账号状态, (0->已过期，1->启用，-1->禁用 )',
  `created_user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` bigint NULL DEFAULT NULL COMMENT '创建时间',
  `updated_user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` bigint NULL DEFAULT NULL COMMENT '更新时间',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_index_account`(`account` ASC) USING BTREE COMMENT '唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT IGNORE INTO `user_info` VALUES (1683039670926716929, 'admin', '$2a$10$VNJuZ/j8j6goIhgesp7unejvNKQGdTj4JSnb8OJ3/kLLwgz3Kyu3e', '贾荣', '', 0, '', 1, NULL, 1690102901504, NULL, NULL, '');

SET FOREIGN_KEY_CHECKS = 1;
