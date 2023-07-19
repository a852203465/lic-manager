/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : lic_manager

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 19/07/2023 13:35:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for keystore
-- ----------------------------
# DROP TABLE IF EXISTS `keystore`;
CREATE TABLE IF NOT EXISTS `keystore`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `validity` int NULL DEFAULT 1 COMMENT '私钥证书有效期(单位：年), 默认：1',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '私钥密码',
  `private_key` blob NULL COMMENT '私钥',
  `public_key` blob NULL COMMENT '公钥',
  `created_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` bigint NULL DEFAULT NULL COMMENT '创建时间',
  `updated_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` bigint NULL DEFAULT NULL COMMENT '更新时间',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_unique`(`name` ASC) USING BTREE COMMENT '唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '秘钥库' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of keystore
-- ----------------------------
-- INSERT INTO `keystore` VALUES (1681537871448354818, '测试', 1, '123456a', 0xFEEDFEED000000020000000100000001000B707269766174656B65797300000189676D5C070000018F3082018B300E060A2B060104012A02110101050004820177F67A7119D75DFFAE1F85E94605ACC971ADBA0B3E16D21CEF477875D19E57DFA54F165AA1B584876F4C0F670E2596EEB09774DB5D112A19ABC703065B3EF8B27DC99543AB70E728BC86645CA8022BC67EB04FC82DC68DE0BA0F007B0EFF6D7D6D41625CA06C8BCC5E4B4E03BC284E863043BE2C1623E58ED098B3CA1CB660F26B04233FCAC11E4726E64BE4BD1E686AF3AB36990817960D9261AFE6825D450596A70B17CEB3AFBE6C72C9CD22758B8F318D7A18D1BCBDD0C5817979E029A63A9D4E9A2DC1123C24310D40DCE1F1B8877F9FDFDFC574E7C9FFECFB7A56B3823822BBBAAED8C0193FE82856980CC176687C4DA10DD0AA56B1AE2B84653F1258EC2FBE6EA7437D62FCF6DC123941D4032209B795E69826713402A650AC6D475543AAC8EF4D3911A0D17C32E7FDE2A34F98F4B54A32E8B9E65501100ACB89F47B2A0D0A8852A10C85F8A8B53F5F5E5749D286087817196B5A255F88DFEE71817B4A1A4E451B3D2B6ADEA3C8944C5E2CDA68BC920529AB25AC3F000000010005582E353039000002FA308202F6308202B4A00302010202043847F227300B06072A8648CE3804030500305E310B300906035504061302434E310B300906035504081302424A310B300906035504071302424A310D300B060355040A13047A6C6578310D300B060355040B13047A6C6578311730150603550403130E6C6F63616C686F73743A38303830301E170D3233303731383035313630375A170D3234303731373035313630375A305E310B300906035504061302434E310B300906035504081302424A310B300906035504071302424A310D300B060355040A13047A6C6578310D300B060355040B13047A6C6578311730150603550403130E6C6F63616C686F73743A38303830308201B83082012C06072A8648CE3804013082011F02818100FD7F53811D75122952DF4A9C2EECE4E7F611B7523CEF4400C31E3F80B6512669455D402251FB593D8D58FABFC5F5BA30F6CB9B556CD7813B801D346FF26660B76B9950A5A49F9FE8047B1022C24FBBA9D7FEB7C61BF83B57E7C6A8A6150F04FB83F6D3C51EC3023554135A169132F675F3AE2B61D72AEFF22203199DD14801C70215009760508F15230BCCB292B982A2EB840BF0581CF502818100F7E1A085D69B3DDECBBCAB5C36B857B97994AFBBFA3AEA82F9574C0B3D0782675159578EBAD4594FE67107108180B449167123E84C281613B7CF09328CC8A6E13C167A8B547C8D28E0A3AE1E2BB3A675916EA37F0BFA213562F1FB627A01243BCCA4F1BEA8519089A883DFE15AE59F06928B665E807B552564014C3BFECF492A0381850002818100F02DA476A1C400F6595C27076EBD4CB362AF7813631BF972B2375396D2E296817DCB8FE6C834C1FEDE8117C15BE2E2403FA48A066141DEAD22916D0D62A823CF301107A4644DECD8889D31F46C1687C374C413C3319EBEFFE13BC64B37FFFFFE75B20CAC4829176CDAC287FB7BB99A8DBDAE138F4A08EE63B636E5171086622B300B06072A8648CE3804030500032F00302C021441E819F7B585A2BB0070974E3FE4C6B4BBE25007021460294D521EC4B368A65CD59018A662627C4DC70A3A037003FCF3F330B486B4A563C391FFCAD96665, 0xFEEDFEED000000020000000100000002000A7075626C69636365727400000189676DB1640005582E353039000002FA308202F6308202B4A00302010202043847F227300B06072A8648CE3804030500305E310B300906035504061302434E310B300906035504081302424A310B300906035504071302424A310D300B060355040A13047A6C6578310D300B060355040B13047A6C6578311730150603550403130E6C6F63616C686F73743A38303830301E170D3233303731383035313630375A170D3234303731373035313630375A305E310B300906035504061302434E310B300906035504081302424A310B300906035504071302424A310D300B060355040A13047A6C6578310D300B060355040B13047A6C6578311730150603550403130E6C6F63616C686F73743A38303830308201B83082012C06072A8648CE3804013082011F02818100FD7F53811D75122952DF4A9C2EECE4E7F611B7523CEF4400C31E3F80B6512669455D402251FB593D8D58FABFC5F5BA30F6CB9B556CD7813B801D346FF26660B76B9950A5A49F9FE8047B1022C24FBBA9D7FEB7C61BF83B57E7C6A8A6150F04FB83F6D3C51EC3023554135A169132F675F3AE2B61D72AEFF22203199DD14801C70215009760508F15230BCCB292B982A2EB840BF0581CF502818100F7E1A085D69B3DDECBBCAB5C36B857B97994AFBBFA3AEA82F9574C0B3D0782675159578EBAD4594FE67107108180B449167123E84C281613B7CF09328CC8A6E13C167A8B547C8D28E0A3AE1E2BB3A675916EA37F0BFA213562F1FB627A01243BCCA4F1BEA8519089A883DFE15AE59F06928B665E807B552564014C3BFECF492A0381850002818100F02DA476A1C400F6595C27076EBD4CB362AF7813631BF972B2375396D2E296817DCB8FE6C834C1FEDE8117C15BE2E2403FA48A066141DEAD22916D0D62A823CF301107A4644DECD8889D31F46C1687C374C413C3319EBEFFE13BC64B37FFFFFE75B20CAC4829176CDAC287FB7BB99A8DBDAE138F4A08EE63B636E5171086622B300B06072A8648CE3804030500032F00302C021441E819F7B585A2BB0070974E3FE4C6B4BBE25007021460294D521EC4B368A65CD59018A662627C4DC70A684C09D5225C294BA5BC4E9EA1FDBC39478F1B7D, NULL, 1689744844620, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;