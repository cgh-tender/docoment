DROP TABLE IF EXISTS t_ware_sale_statistics;
CREATE TABLE t_ware_sale_statistics
(
    id               bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    business_id      bigint(20) NOT NULL COMMENT '业务机构编码',
    ware_inside_code bigint(20) NOT NULL COMMENT '商品自编码',
    weight_sale_cnt_day double (16,4) DEFAULT NULL COMMENT '平均日销量',
    last_thirty_days_sales double (16,4) DEFAULT NULL COMMENT '最近30天销量',
    last_sixty_days_sales double (16,4) DEFAULT NULL COMMENT '最近60天销量',
    last_ninety_days_sales double (16,4) DEFAULT NULL COMMENT '最近90天销量',
    same_period_sale_qty_thirty double (16,4) DEFAULT NULL COMMENT '去年同期30天销量',
    same_period_sale_qty_sixty double (16,4) DEFAULT NULL COMMENT '去年同期60天销量',
    same_period_sale_qty_ninety double (16,4) DEFAULT NULL COMMENT '去年同期90天销量',
    create_user      bigint(20) DEFAULT NULL COMMENT '创建人',
    create_time      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    modify_user      bigint(20) DEFAULT NULL COMMENT '最终修改人',
    modify_time      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最终修改时间',
    is_delete        tinyint(2) DEFAULT '2' COMMENT '是否删除，1：是，2：否',
    PRIMARY KEY (id) USING BTREE,
    KEY                idx_business_ware (business_id,ware_inside_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品销售统计';


create table sys_resource
(
    id          bigint(20) not null auto_increment comment '主键',
    code        bigint(20) not null comment '菜单code',
    parent_code bigint(20) not null comment '父菜单code',
    name        varchar(64) not null comment '菜单名称',
    displayName varchar(128) comment 'desc',
    create_user bigint(20) comment '创建人',
    create_time datetime    not null DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    modify_user bigint(20) comment '最终修改人',
    modify_time datetime             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '修改时间',
    is_delete   tinyint(2) default 2 comment '是否删除 1: 是，2：否',
    PRIMARY KEY (id) USING BTREE,
    KEY         idx_code_ware (code,parent_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  ROW_FORMAT=DYNAMIC COMMENT='商品销售统计';

CREATE TABLE IF NOT EXISTS TB_CFG_ORGANIZATION
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20)   DEFAULT NULL COMMENT '创建者',
    create_time datetime     DEFAULT NULL COMMENT '创建时间',
    update_time datetime     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by   bigint(20)   DEFAULT NULL COMMENT '更新者',
    name        varchar(255) DEFAULT NULL COMMENT '名称',
    description varchar(255) DEFAULT NULL COMMENT '描述',
    parent_id   bigint(20)   DEFAULT NULL COMMENT '父ID',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组织表';
