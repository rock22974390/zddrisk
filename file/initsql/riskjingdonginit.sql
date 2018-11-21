/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.244.206
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 192.168.244.206:3306
 Source Schema         : lu_tale

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 09/11/2018 10:43:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for risk_accredit
-- ----------------------------
DROP TABLE IF EXISTS `risk_accredit`;
CREATE TABLE `risk_accredit`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `orderId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `userId` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `accreditInfo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户授权信息',
  `createTime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for risk_applyAmount
-- ----------------------------
DROP TABLE IF EXISTS `risk_applyAmount`;
CREATE TABLE `risk_applyAmount`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `bizNo` int(11) NULL DEFAULT NULL COMMENT '业务流水号，此字段由商户生成，需确保唯一性，用于定位每一次请求',
  `userId` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户系统内用户唯一编号',
  `modelNo` int(11) NULL DEFAULT NULL COMMENT '规格/型号（租赁场景）',
  `applyAmount` int(10) NULL DEFAULT NULL COMMENT '申请金额（单位元）',
  `applyDays` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请期限(日)',
  `applyMonths` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请期限（月）',
  `contractAmount` int(10) NULL DEFAULT NULL COMMENT '租赁物品签约价（租赁场景）',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for risk_approveResult
-- ----------------------------
DROP TABLE IF EXISTS `risk_approveResult`;
CREATE TABLE `risk_approveResult`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户唯一编号',
  `bizNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务流水号，此字段由商户生成，需确保唯一性，用于定位每一次请求',
  `applyId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '进件编号',
  `payIndex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '借款次数',
  `approveResult` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '风控结果',
  `approveCredit` int(10) NULL DEFAULT NULL COMMENT '授信额度，单位：元',
  `approveAPR` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授信年化利率（%）',
  `approveDPR` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授信日利率（‱）',
  `minLoanTerm` int(2) NULL DEFAULT NULL COMMENT '最小贷款周期',
  `maxLoanTerm` int(2) NULL DEFAULT NULL COMMENT '最大贷款周期',
  `refuseCode` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拒绝原因编码',
  `refuseReason` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拒绝原因明细说明',
  `approveTime` datetime(0) NULL DEFAULT NULL COMMENT '审批时间',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for risk_certification
-- ----------------------------
DROP TABLE IF EXISTS `risk_certification`;
CREATE TABLE `risk_certification`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userId` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `idCard` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `mobile` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `certificationType` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '认证类型',
  `certificationItem` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '认证项（param）',
  `certificationResult` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '认证结果',
  `certificationLimit` datetime(0) NOT NULL COMMENT '认证有效期',
  `flag` int(2) NULL DEFAULT NULL COMMENT '标识',
  `remark` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creatTime` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for risk_certificationUserInfo
-- ----------------------------
DROP TABLE IF EXISTS `risk_certificationUserInfo`;
CREATE TABLE `risk_certificationUserInfo`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userId` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `mobile` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册手机号',
  `realName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `nation` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族（OCR）',
  `age` int(3) NULL DEFAULT NULL COMMENT '年龄',
  `addressPermanent` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地址（OCR）',
  `idCardValidDate` datetime(0) NULL DEFAULT NULL COMMENT '身份证有效期（OCR）',
  `maritalStatus` int(1) NULL DEFAULT NULL COMMENT '婚姻状态 (1已婚 2未婚 3离异 4丧偶)',
  `education` int(1) NULL DEFAULT NULL COMMENT '文化水平(1硕士及以上2本科3大专4中专5高中6初中及以下)',
  `institution` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学校院系名称',
  `addressWork` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作地址',
  `addressHome` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家庭地址',
  `payday` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发薪日',
  `companyName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位名称',
  `companyPhone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位电话',
  `sosContactName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '紧急联系人姓名',
  `sosContactRelation` int(1) NULL DEFAULT NULL COMMENT '紧急联系人 关系 (1配偶 2父母 3子女)',
  `sosContactPhone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '紧急联系人联系方式',
  `sosContactName1` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '紧急联系人1姓名',
  `sosContactRelation1` int(1) NULL DEFAULT NULL COMMENT '紧急联系人1 关系(4兄弟姐妹5同事6同学7朋友8亲戚)',
  `sosContactPhone1` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '紧急联系人1联系方式',
  `lastLoginIp` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后登录 ip',
  `longitude` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新gps 经度',
  `latitude` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新gps 纬度',
  `gpsAddress` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新gps 地址',
  `regOs` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册设备[系统,版本,手机型号]',
  `regAppVersion` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册app 版本',
  `regFrom` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '进件引流渠道(建议0为自然流量，其他渠道请提供对应码表)',
  `regIp` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册ip',
  `bankCard` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡号',
  `bankMobile` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡预留手机号',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
--==================2018-11-14修改表结构SQL======================================
ALTER table risk_applyamount ADD COLUMN  applyid varchar(20) COMMENT "进件编号";
ALTER table risk_certificationuserinfo ADD COLUMN  applyid varchar(20) COMMENT "进件编号";
ALTER table risk_accredit ADD COLUMN  type varchar(2) COMMENT "授权类型";
ALTER table risk_applyamount ADD COLUMN  UPDATE_TIME datetime COMMENT "更新时间";
ALTER table risk_certificationuserinfo ADD COLUMN  UPDATE_TIME datetime COMMENT "更新时间";
ALTER table risk_accredit ADD COLUMN  UPDATE_TIME  datetime COMMENT "更新时间";
ALTER table risk_approveresult ADD COLUMN  UPDATE_TIME datetime COMMENT "更新时间";
ALTER table risk_certification ADD COLUMN  UPDATE_TIME  datetime COMMENT "更新时间";
--==================2018-11-15修改表结构SQL======================================
alter TABLE risk_applyamount modify  COLUMN bizno VARCHAR(20);
alter TABLE risk_approveresult modify  COLUMN bizno VARCHAR (20);


--=====================================2018-11-20增加银行信息字段=====================================
ALTER TABLE risk_applyamount ADD COLUMN realName VARCHAR (10) COMMENT "姓名", ADD COLUMN idCard VARCHAR (20) COMMENT "身份证号", ADD COLUMN bankCard VARCHAR (20) COMMENT "银行卡号" ,ADD COLUMN bankName VARCHAR (20) COMMENT "所属银行", ADD COLUMN bankMobile VARCHAR (20) COMMENT "银行预留手机号";
ALTER TABLE risk_certificationuserinfo ADD COLUMN idCard VARCHAR (20) COMMENT "身份证号",ADD COLUMN bankName VARCHAR (20) COMMENT "所属银行" ;


ALTER TABLE risk_applyamount CHANGE UPDATE_TIME updateTime  datetime  COMMENT "更新时间";
ALTER TABLE risk_certificationuserinfo CHANGE UPDATE_TIME updateTime  datetime  COMMENT "更新时间";
ALTER TABLE risk_accredit CHANGE UPDATE_TIME updateTime  datetime  COMMENT "更新时间";
ALTER TABLE risk_approveresult CHANGE UPDATE_TIME updateTime  datetime  COMMENT "更新时间";
ALTER TABLE risk_certification CHANGE UPDATE_TIME updateTime  datetime  COMMENT "更新时间";
ALTER TABLE risk_applyamount CHANGE applyid applyId  VARCHAR(20)  COMMENT "进件编号";
ALTER TABLE risk_certificationuserinfo CHANGE applyid applyId  VARCHAR(20)  COMMENT "进件编号";
ALTER TABLE risk_applyamount CHANGE bizno bizNo  VARCHAR(20)  COMMENT "订单号";
ALTER TABLE risk_approveresult CHANGE bizno bizNo  VARCHAR(20)  COMMENT "订单号";

--=============================2018-11-21修改表类型=======================
alter TABLE risk_certificationuserinfo modify  COLUMN idCardValidDate VARCHAR(30)  COMMENT " 身份证有效日期";

alter TABLE risk_certificationuserinfo modify  COLUMN regOs mediumtext COMMENT " 注册设备[系统,版本,手机型号]";
ALTER TABLE risk_accredit CHANGE orderId taskId   varchar(20)  COMMENT "第三方授权编号";

alter TABLE risk_accredit modify  COLUMN accreditInfo text COMMENT " 用户授权信息";
alter TABLE risk_applyamount modify  COLUMN bankName varchar(50) COMMENT " 所属银行";
alter TABLE risk_certificationuserinfo modify  COLUMN gpsAddress varchar(200) COMMENT " 最后更新gps 地址";
alter TABLE risk_certificationuserinfo modify  COLUMN bankName varchar(50) COMMENT " 所属银行";
ALTER table risk_certificationuserinfo ADD COLUMN  productId VARCHAR(3) COMMENT "产品形态";

alter TABLE risk_certificationuserinfo modify  COLUMN realName varchar(20) COMMENT " 姓名";
alter TABLE risk_certificationuserinfo modify  COLUMN sosContactName varchar(20) COMMENT " 紧急联系人姓名";
alter TABLE risk_certificationuserinfo modify  COLUMN sosContactName1 varchar(20) COMMENT " 紧急联系人姓名1";
alter TABLE risk_certificationuserinfo modify  COLUMN longitude varchar(20) COMMENT " 最后更新gps 经度";
alter TABLE risk_certificationuserinfo modify  COLUMN latitude varchar(20) COMMENT " 最后更新gps 纬度";
alter TABLE risk_certificationuserinfo modify  COLUMN regFrom varchar(5) COMMENT " 进件引流渠道(建议0为自然流量，其他渠道请提供对应码表)";


alter TABLE risk_accredit modify  COLUMN taskId varchar(50)  COMMENT "第三方授权编号";



