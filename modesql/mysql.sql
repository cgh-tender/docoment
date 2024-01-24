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


create table IF NOT EXISTS tb_cfg_datasource
(
    id          bigint(20) NOT NULL auto_increment comment '主键',
    create_by bigint(20) NOT NULL comment '创建人',
    create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    update_by bigint(20) NOT NULL comment '最终修改人',
    update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '修改时间',
    url         varchar(256) NOT NULL comment '数据库连接地址',
    username         varchar(256) NOT NULL comment '数据库连接用户名',
    password         varchar(256) NOT NULL comment '数据库连接密码',
    PRIMARY KEY (id) USING BTREE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  ROW_FORMAT=DYNAMIC COMMENT='数据库连接表';

create table IF NOT EXISTS tb_cfg_resource
(
    id          bigint(20) NOT NULL auto_increment comment '主键',
    code        bigint(20) NOT NULL comment '菜单code',
    parent_code bigint(20) NOT NULL comment '父菜单code',
    name        varchar(64) NOT NULL comment '菜单名称',
    description        varchar(256) DEFAULT NULL comment '描述',
    deleted   tinyint(2) DEFAULT 0 comment '是否删除 1: 是，0：否',
    url       varchar(256) NOT NULL comment '菜单url',
    status    tinyint(2) NOT NULL comment '资源类型',
    create_by bigint(20) NOT NULL comment '创建人',
    create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    update_by bigint(20) NOT NULL comment '最终修改人',
    update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '修改时间',
    PRIMARY KEY (id) USING BTREE,
    KEY         idx_code_ware (code,parent_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  ROW_FORMAT=DYNAMIC COMMENT='菜单表';

CREATE TABLE IF NOT EXISTS tb_cfg_user
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime   NOT NULL COMMENT '创建时间',
    update_time datetime   NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    username        varchar(128) DEFAULT NULL COMMENT '用户名称',
    phone       varchar(20) DEFAULT NULL COMMENT '手机号',
    email       varchar(20) DEFAULT NULL COMMENT '邮箱',
    gender tinyint(2) DEFAULT NULL COMMENT '性别',
    status  tinyint(2) DEFAULT NULL COMMENT '用户状态',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

CREATE TABLE IF NOT EXISTS tb_cfg_role
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime   NOT NULL COMMENT '创建时间',
    update_time datetime   NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    name        varchar(255) DEFAULT NULL COMMENT '名称',
    description varchar(255) DEFAULT NULL COMMENT '描述',
    parent_id   bigint(20)   DEFAULT NULL COMMENT '父ID',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组织表';

CREATE TABLE IF NOT EXISTS tb_user_role
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime   NOT NULL COMMENT '创建时间',
    update_time datetime   NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    user_id bigint(20)  NOT NULL COMMENT '用户id',
    role_id bigint(20)  NOT NULL COMMENT '角色id',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户角色关系表';

CREATE TABLE IF NOT EXISTS tb_cfg_organization
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime   NOT NULL COMMENT '创建时间',
    update_time datetime   NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    name        varchar(255) DEFAULT NULL COMMENT '名称',
    description varchar(255) DEFAULT NULL COMMENT '描述',
    parent_id   bigint(20)   DEFAULT NULL COMMENT '父ID',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组织表';

CREATE TABLE IF NOT EXISTS tb_user_organization
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime   NOT NULL COMMENT '创建时间',
    update_time datetime   NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    name        varchar(255) DEFAULT NULL COMMENT '名称',
    user_id bigint(20)  NOT NULL COMMENT '用户id',
    organization_id   bigint(20)   NOT NULL COMMENT '组织id',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户-组织关系表';

CREATE TABLE IF NOT EXISTS tb_cfg_position
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime   NOT NULL COMMENT '创建时间',
    update_time datetime   NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    name        varchar(255) DEFAULT NULL COMMENT '名称',
    description varchar(255) DEFAULT NULL COMMENT '描述',
    parent_id   bigint(20)   DEFAULT NULL COMMENT '父ID',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='职位表';

CREATE TABLE IF NOT EXISTS tb_user_position
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime   NOT NULL COMMENT '创建时间',
    update_time datetime   NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    name        varchar(255) DEFAULT NULL COMMENT '名称',
    user_id bigint(20)  NOT NULL COMMENT '用户id',
    position_id   bigint(20)   NOT NULL COMMENT '组织id',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户-职位关系表';

CREATE TABLE IF NOT EXISTS tb_cfg_group
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime   NOT NULL COMMENT '创建时间',
    update_time datetime   NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    name        varchar(255) DEFAULT NULL COMMENT '组名称',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户组表';

CREATE TABLE IF NOT EXISTS tb_user_group
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime   NOT NULL COMMENT '创建时间',
    update_time datetime   NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    user_id     bigint(20) DEFAULT NULL COMMENT '用户id',
    group_id    bigint(20) DEFAULT NULL COMMENT '用户组id',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户组表';

CREATE TABLE IF NOT EXISTS tb_role_organization
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime   NOT NULL COMMENT '创建时间',
    update_time datetime   NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    role_id     bigint(20) DEFAULT NULL COMMENT '角色id',
    organization_id    bigint(20) DEFAULT NULL COMMENT '组织id',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色组织表关系表';

CREATE TABLE IF NOT EXISTS tb_role_position
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime   NOT NULL COMMENT '创建时间',
    update_time datetime   NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    role_id     bigint(20) DEFAULT NULL COMMENT '角色id',
    position_id    bigint(20) DEFAULT NULL COMMENT '职位id',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色职位表关系表';

CREATE TABLE IF NOT EXISTS tb_role_group
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime   NOT NULL COMMENT '创建时间',
    update_time datetime   NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    role_id     bigint(20) DEFAULT NULL COMMENT '角色id',
    group_id    bigint(20) DEFAULT NULL COMMENT '用户组id',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色用户组表关系表';

CREATE TABLE IF NOT EXISTS tb_role_exclusion
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime   NOT NULL COMMENT '创建时间',
    update_time datetime   NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    role_id_one     bigint(20) DEFAULT NULL COMMENT '角色id1',
    role_id_two     bigint(20) DEFAULT NULL COMMENT '角色id2',
    description     varchar(256) DEFAULT null comment '描述',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色互斥关系表';

CREATE TABLE IF NOT EXISTS tb_role_resource
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime   NOT NULL COMMENT '创建时间',
    update_time datetime   NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    role_id     bigint(20) DEFAULT NULL COMMENT '角色id',
    resource_id     bigint(20) DEFAULT NULL COMMENT '资源id',
    type    tinyint(2) DEFAULT null comment '资源类型',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色资源关系表';

