DROP TABLE IF EXISTS t_ware_sale_statistics;
CREATE TABLE t_ware_sale_statistics
(
    id               bigint(20) NOT NULL COMMENT '主键id',
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
    create_time      datetime(3) NOT NULL COMMENT '创建时间',
    modify_user      bigint(20) DEFAULT NULL COMMENT '最终修改人',
    modify_time      datetime(3) NOT NULL COMMENT '最终修改时间',
    is_delete        tinyint(2) DEFAULT 2 COMMENT '是否删除，1：是，2：否',
    PRIMARY KEY (id) USING BTREE,
    KEY idx_business_ware (business_id,ware_inside_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品销售统计';

DROP TABLE IF EXISTS tb_cfg_datasource;
create table IF NOT EXISTS tb_cfg_datasource
(
    id          bigint(20) NOT NULL COMMENT '主键',
    create_by bigint(20) NOT NULL COMMENT '创建人',
    create_time datetime(3) NOT NULL COMMENT '创建时间',
    update_by bigint(20) NOT NULL COMMENT '最终修改人',
    update_time datetime(3) NOT NULL COMMENT '修改时间',
    url         varchar(256) NOT NULL COMMENT '数据库连接地址',
    username         varchar(256) NOT NULL COMMENT '数据库连接用户名',
    password         varchar(256) NOT NULL COMMENT '数据库连接密码',
    PRIMARY KEY (id) USING BTREE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  ROW_FORMAT=DYNAMIC COMMENT='数据库连接表';

DROP TABLE IF EXISTS tb_cfg_resource;
create table IF NOT EXISTS tb_cfg_resource
(
    id          bigint(20) NOT NULL COMMENT '主键',
    parent_id bigint(20) DEFAULT 0 COMMENT '父菜单code',
    create_by bigint(20) NOT NULL COMMENT '创建人',
    create_time datetime(3) NOT NULL COMMENT '创建时间',
    update_by bigint(20) NOT NULL COMMENT '最终修改人',
    update_time datetime(3) NOT NULL COMMENT '修改时间',
    name        varchar(64) NOT NULL COMMENT '菜单名称',
    path       varchar(256) NOT NULL COMMENT '菜单url',
    description        varchar(256) DEFAULT NULL COMMENT '描述',
    deleted   tinyint(2) DEFAULT 0 COMMENT '是否删除 1: true 删除，0：false 活跃',
    status    tinyint(2) DEFAULT 0 COMMENT '资源类型',
    redirect    varchar(32) DEFAULT NULL COMMENT '重定向 地址',
    alias    varchar(64) DEFAULT NULL COMMENT '路由别名',
    before_enter    varchar(128) DEFAULT NULL COMMENT '独享路由守卫',
    before_route_leave    varchar(128) COMMENT '离开该组件时被调用',
    component    varchar(128) COMMENT '获取跳转页面的地址',
    keep_alive  tinyint(2) DEFAULT 0 COMMENT '是否开启缓存页面 1: 是，0：否',
    meta  varchar(512) COMMENT '路由元数据',
    sort  tinyint(2) DEFAULT 0 COMMENT '排序',
    PRIMARY KEY (id) USING BTREE,
    KEY         idx_code_ware (id,parent_id) USING BTREE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  ROW_FORMAT=DYNAMIC COMMENT='菜单表';
INSERT INTO tb_cfg_resource (id, parent_id, create_by, create_time, update_by, update_time, name, path, description, deleted, status, redirect, alias, before_enter, before_route_leave, component, keep_alive, meta, sort) VALUES (157586258510479372, 0, 1, '2024-01-25 14:55:11', 1, '2024-01-25 14:55:11', 'Permission', '/permission', null, 0, 0, '/permission/page', null, null, null, null, 0, '{"activeMenu":"","affix":null,"alwaysShow":true,"breadcrumb":null,"elIcon":"","hidden":null,"keepAlive":null,"roles":[],"svgIcon":"lock","title":"权限管理"}', 0);
INSERT INTO tb_cfg_resource (id, parent_id, create_by, create_time, update_by, update_time, name, path, description, deleted, status, redirect, alias, before_enter, before_route_leave, component, keep_alive, meta, sort) VALUES (157587010129756173, 157586258510479372, 1, '2024-01-25 14:58:05', 1, '2024-01-25 14:58:05', 'PagePermission', 'page', null, 0, 0, null, null, null, null, 'permission/page', 1, '{"activeMenu":"","affix":null,"alwaysShow":null,"breadcrumb":null,"elIcon":"","hidden":null,"keepAlive":null,"roles":[],"svgIcon":"","title":"页面权限"}', 8);
INSERT INTO tb_cfg_resource (id, parent_id, create_by, create_time, update_by, update_time, name, path, description, deleted, status, redirect, alias, before_enter, before_route_leave, component, keep_alive, meta, sort) VALUES (157587010129756174, 157586258510479372, 1, '2024-01-25 14:58:05', 1, '2024-01-25 14:58:05', 'DirectivePermission', 'directive', null, 0, 0, null, null, null, null, 'permission/directive', 1, '{"activeMenu":"","affix":null,"alwaysShow":null,"breadcrumb":null,"elIcon":"","hidden":null,"keepAlive":null,"roles":[],"svgIcon":"","title":"指令权限"}', 9);
INSERT INTO tb_cfg_resource (id, parent_id, create_by, create_time, update_by, update_time, name, path, description, deleted, status, redirect, alias, before_enter, before_route_leave, component, keep_alive, meta, sort) VALUES (157587010129756175, 157586258510479372, 1, '2024-01-25 14:58:05', 1, '2024-01-25 14:58:05', 'userResource', 'user', null, 0, 0, null, null, null, null, 'permission/user', 1, '{"activeMenu":"","affix":null,"alwaysShow":null,"breadcrumb":null,"elIcon":"","hidden":null,"keepAlive":null,"roles":[],"svgIcon":"","title":"用户管理"}', 0);
INSERT INTO tb_cfg_resource (id, parent_id, create_by, create_time, update_by, update_time, name, path, description, deleted, status, redirect, alias, before_enter, before_route_leave, component, keep_alive, meta, sort) VALUES (157587010129756176, 157586258510479372, 1, '2024-01-25 14:58:05', 1, '2024-01-25 14:58:05', 'resourceResource', 'resource', null, 0, 0, null, null, null, null, 'permission/resource', 1, '{"activeMenu":"","affix":null,"alwaysShow":null,"breadcrumb":null,"elIcon":"","hidden":null,"keepAlive":null,"roles":[],"svgIcon":"","title":"菜单管理"}', 2);

DROP TABLE IF EXISTS tb_cfg_user;
CREATE TABLE IF NOT EXISTS tb_cfg_user
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime(3)   NOT NULL COMMENT '创建时间',
    update_time datetime(3)   NOT NULL COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    username        varchar(128) NOT NULL COMMENT '用户账号',
    realname        varchar(128) NOT NULL COMMENT '用户姓名',
    password        varchar(128) NOT NULL COMMENT '密码',
    phone       varchar(20) DEFAULT NULL COMMENT '手机号',
    email       varchar(20) DEFAULT NULL COMMENT '邮箱',
    gender tinyint(2) DEFAULT 0 COMMENT '性别',
    status  tinyint(2) DEFAULT 0 COMMENT '用户状态',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';
INSERT INTO tb_cfg_user (id, create_by, create_time, update_time, update_by, username, realname, password, phone, email, gender, status) VALUES (1, 1, '2024-01-25 16:10:52', '2024-01-25 08:11:07', 1, 'admin', 'admin', '{bcrypt}$2a$10$erkqLdvTsaccM.aMC1g/fOBixc2WQH/f6iEWximSb06Xh86gM3LyK', '18334774205', 'late_tender@163.com', 0, 0);
INSERT INTO tb_cfg_user (id, create_by, create_time, update_time, update_by, username, realname, password, phone, email, gender, status) VALUES (2, 1, '2024-01-25 16:10:52', '2024-01-25 08:11:07', 1, 'test', 'test', '{bcrypt}$2a$10$CT7jn1Pri2JBcVIaBle8Ie9tViTnKiwjbh5cOa6MX/CrKv2BAiqze', '18334774205', 'late_tender@163.com', 0, 0);
INSERT INTO tb_cfg_user (id, create_by, create_time, update_time, update_by, username, realname, password, phone, email, gender, status) VALUES (3, 1, '2024-01-25 16:10:52', '2024-01-25 08:11:07', 1, 'test1', 'test1', '{bcrypt}$2a$10$CT7jn1Pri2JBcVIaBle8Ie9tViTnKiwjbh5cOa6MX/CrKv2BAiqze', '18334774205', 'late_tender@163.com', 0, 0);

DROP TABLE IF EXISTS tb_cfg_role;
CREATE TABLE IF NOT EXISTS tb_cfg_role
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime(3)   NOT NULL COMMENT '创建时间',
    update_time datetime(3)   NOT NULL COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    name        varchar(255) DEFAULT NULL COMMENT '名称',
    description varchar(255) DEFAULT NULL COMMENT '描述',
    parent_id   bigint(20)   DEFAULT 0 COMMENT '父ID',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';
INSERT INTO tb_cfg_role (id, create_by, create_time, update_time, update_by, name, description, parent_id) VALUES (1, 1, '2024-01-25 16:14:37', '2024-01-25 08:14:54', 1, '超级管理员', '超级管理员', 0);
INSERT INTO tb_cfg_role (id, create_by, create_time, update_time, update_by, name, description, parent_id) VALUES (2, 1, '2024-01-25 16:14:38', '2024-01-25 08:14:54', 1, '测试人员', '测试人员', 0);
INSERT INTO tb_cfg_role (id, create_by, create_time, update_time, update_by, name, description, parent_id) VALUES (3, 1, '2024-01-25 16:14:38', '2024-01-25 08:14:54', 1, '测试人员1', '测试人员1', 0);

DROP TABLE IF EXISTS tb_user_role;
CREATE TABLE IF NOT EXISTS tb_user_role
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime(3)   NOT NULL COMMENT '创建时间',
    update_time datetime(3)   NOT NULL COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    user_id bigint(20)  NOT NULL COMMENT '用户id',
    role_id bigint(20)  NOT NULL COMMENT '角色id',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户角色关系表';
INSERT INTO tb_user_role (id, create_by, create_time, update_time, update_by, user_id, role_id) VALUES (1, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, 1, 1);
INSERT INTO tb_user_role (id, create_by, create_time, update_time, update_by, user_id, role_id) VALUES (2, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, 2, 2);
INSERT INTO tb_user_role (id, create_by, create_time, update_time, update_by, user_id, role_id) VALUES (3, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, 3, 3);

DROP TABLE IF EXISTS tb_cfg_organization;
CREATE TABLE IF NOT EXISTS tb_cfg_organization
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime(3)   NOT NULL COMMENT '创建时间',
    update_time datetime(3)   NOT NULL COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    name        varchar(255) DEFAULT NULL COMMENT '名称',
    description varchar(255) DEFAULT NULL COMMENT '描述',
    parent_id   bigint(20)   DEFAULT 0 COMMENT '父ID',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组织表';
INSERT INTO tb_cfg_organization (id, create_by, create_time, update_time, update_by, name, description, parent_id) VALUES (1, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, '总部', '总部', 0 );
INSERT INTO tb_cfg_organization (id, create_by, create_time, update_time, update_by, name, description, parent_id) VALUES (2, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, '山西省', '山西省', 1 );
INSERT INTO tb_cfg_organization (id, create_by, create_time, update_time, update_by, name, description, parent_id) VALUES (3, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, '临汾市', '临汾市', 2 );
INSERT INTO tb_cfg_organization (id, create_by, create_time, update_time, update_by, name, description, parent_id) VALUES (4, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, '太原市', '太原市', 2 );

DROP TABLE IF EXISTS tb_user_organization;
CREATE TABLE IF NOT EXISTS tb_user_organization
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime(3)   NOT NULL COMMENT '创建时间',
    update_time datetime(3)   NOT NULL COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    name        varchar(255) DEFAULT NULL COMMENT '名称',
    user_id bigint(20)  NOT NULL COMMENT '用户id',
    organization_id   bigint(20)   NOT NULL COMMENT '组织id',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE index user_id_unique(user_id)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4 COMMENT ='用户-组织关系表';
INSERT INTO tb_user_organization (id, create_by, create_time, update_time, update_by, name, user_id, organization_id) VALUES (1, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, '总部', 1, 1);
INSERT INTO tb_user_organization (id, create_by, create_time, update_time, update_by, name, user_id, organization_id) VALUES (2, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, '临汾市', 2, 3);
INSERT INTO tb_user_organization (id, create_by, create_time, update_time, update_by, name, user_id, organization_id) VALUES (3, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, '太原市', 3, 4);

DROP TABLE IF EXISTS tb_cfg_position;
CREATE TABLE IF NOT EXISTS tb_cfg_position
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime(3)   NOT NULL COMMENT '创建时间',
    update_time datetime(3)   NOT NULL COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    name        varchar(255) DEFAULT NULL COMMENT '名称',
    description varchar(255) DEFAULT NULL COMMENT '描述',
    parent_id   bigint(20)   DEFAULT 0 COMMENT '父ID',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='职位表';

INSERT INTO tb_cfg_position (id, create_by, create_time, update_time, update_by, name, description, parent_id) VALUES (1, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, '总经理', '总经理', 0);
INSERT INTO tb_cfg_position (id, create_by, create_time, update_time, update_by, name, description, parent_id) VALUES (2, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, '开发经理', '开发经理', 1);
INSERT INTO tb_cfg_position (id, create_by, create_time, update_time, update_by, name, description, parent_id) VALUES (3, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, '开发人员', '开发人员', 2);

DROP TABLE IF EXISTS tb_user_position;
CREATE TABLE IF NOT EXISTS tb_user_position
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime(3)   NOT NULL COMMENT '创建时间',
    update_time datetime(3)   NOT NULL COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    name        varchar(255) DEFAULT NULL COMMENT '名称',
    user_id bigint(20)  NOT NULL COMMENT '用户id',
    position_id   bigint(20)   NOT NULL COMMENT '职位id',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户-职位关系表';

INSERT INTO tb_user_position (id, create_by, create_time, update_time, update_by, name, user_id, position_id) VALUES (1, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, '总经理', 1, 1);
INSERT INTO tb_user_position (id, create_by, create_time, update_time, update_by, name, user_id, position_id) VALUES (2, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, '开发经理', 2, 2);
INSERT INTO tb_user_position (id, create_by, create_time, update_time, update_by, name, user_id, position_id) VALUES (3, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, '开发经理', 3, 3);

DROP TABLE IF EXISTS tb_cfg_group;
CREATE TABLE IF NOT EXISTS tb_cfg_group
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime(3)   NOT NULL COMMENT '创建时间',
    update_time datetime(3)   NOT NULL COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    name        varchar(255) DEFAULT NULL COMMENT '组名称',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户组表';

INSERT INTO tb_cfg_group (id, create_by, create_time, update_time, update_by, name) VALUES (1, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, '开发组');
INSERT INTO tb_cfg_group (id, create_by, create_time, update_time, update_by, name) VALUES (2, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 2, '人事组');

DROP TABLE IF EXISTS tb_user_group;
CREATE TABLE IF NOT EXISTS tb_user_group
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime(3)   NOT NULL COMMENT '创建时间',
    update_time datetime(3)   NOT NULL COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    user_id     bigint(20) DEFAULT NULL COMMENT '用户id',
    group_id    bigint(20) DEFAULT NULL COMMENT '用户组id',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户组表';

INSERT INTO tb_user_group (id, create_by, create_time, update_time, update_by, user_id,group_id) VALUES (1, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, 2, 1);
INSERT INTO tb_user_group (id, create_by, create_time, update_time, update_by, user_id,group_id) VALUES (2, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, 3, 1);

DROP TABLE IF EXISTS tb_role_organization;
CREATE TABLE IF NOT EXISTS tb_role_organization
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime(3)   NOT NULL COMMENT '创建时间',
    update_time datetime(3)   NOT NULL COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    role_id     bigint(20) DEFAULT NULL COMMENT '角色id',
    organization_id    bigint(20) DEFAULT NULL COMMENT '组织id',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色组织表关系表';

INSERT INTO tb_role_organization (id, create_by, create_time, update_time, update_by, role_id,organization_id) VALUES (1, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, 2, 4);
INSERT INTO tb_role_organization (id, create_by, create_time, update_time, update_by, role_id,organization_id) VALUES (2, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, 3, 4);
INSERT INTO tb_role_organization (id, create_by, create_time, update_time, update_by, role_id,organization_id) VALUES (4, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, 2, 3);
INSERT INTO tb_role_organization (id, create_by, create_time, update_time, update_by, role_id,organization_id) VALUES (5, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, 3, 3);

DROP TABLE IF EXISTS tb_role_position;
CREATE TABLE IF NOT EXISTS tb_role_position
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime(3)   NOT NULL COMMENT '创建时间',
    update_time datetime(3)   NOT NULL COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    role_id     bigint(20) DEFAULT NULL COMMENT '角色id',
    position_id    bigint(20) DEFAULT NULL COMMENT '职位id',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色职位表关系表';

INSERT INTO tb_role_position (id, create_by, create_time, update_time, update_by, role_id,position_id) VALUES (1, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, 2, 2);
INSERT INTO tb_role_position (id, create_by, create_time, update_time, update_by, role_id,position_id) VALUES (2, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, 2, 3);
INSERT INTO tb_role_position (id, create_by, create_time, update_time, update_by, role_id,position_id) VALUES (3, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, 3, 3);

DROP TABLE IF EXISTS tb_role_group;
CREATE TABLE IF NOT EXISTS tb_role_group
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime(3)   NOT NULL COMMENT '创建时间',
    update_time datetime(3)   NOT NULL COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    role_id     bigint(20) DEFAULT NULL COMMENT '角色id',
    group_id    bigint(20) DEFAULT NULL COMMENT '用户组id',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色用户组表关系表';

INSERT INTO tb_role_group (id, create_by, create_time, update_time, update_by, role_id,group_id) VALUES (1, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, 2, 1);
INSERT INTO tb_role_group (id, create_by, create_time, update_time, update_by, role_id,group_id) VALUES (2, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, 3, 2);

DROP TABLE IF EXISTS tb_role_exclusion;
CREATE TABLE IF NOT EXISTS tb_role_exclusion
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime(3)   NOT NULL COMMENT '创建时间',
    update_time datetime(3)   NOT NULL COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    role_id_one     bigint(20) DEFAULT NULL COMMENT '角色id1',
    role_id_two     bigint(20) DEFAULT NULL COMMENT '角色id2',
    description     varchar(256) DEFAULT null COMMENT '描述',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色互斥关系表';

INSERT INTO tb_role_exclusion (id, create_by, create_time, update_by, update_time, role_id_one, role_id_two, description) VALUES (1, 1, '2024-02-05 17:25:38.088', 1, '2024-02-05 17:25:38.088', 1, 2, '特殊处理');

DROP TABLE IF EXISTS tb_role_resource;
CREATE TABLE IF NOT EXISTS tb_role_resource
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime(3)   NOT NULL COMMENT '创建时间',
    update_time datetime(3)   NOT NULL COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    role_id     bigint(20) DEFAULT NULL COMMENT '角色id',
    resource_id     bigint(20) DEFAULT NULL COMMENT '资源id',
    type    tinyint(2) DEFAULT 0 COMMENT '资源类型',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色资源关系表';

DROP TABLE IF EXISTS tb_controller_log;
CREATE TABLE IF NOT EXISTS tb_controller_log
(
    id                   bigint(20) NOT NULL COMMENT '主键',
    create_by            bigint(20) NOT NULL COMMENT '创建人',
    create_time          datetime(3) NOT NULL COMMENT '创建时间',
    update_by            bigint(20) NOT NULL COMMENT '最终修改人',
    update_time          datetime(3) NOT NULL COMMENT '修改时间',
    http_method          varchar(10) COMMENT '方法名称',
    request_url          varchar(255) COMMENT '请求的 URL',
    client_ip            varchar(15) COMMENT '录请求的客户端 IP 地址',
    ip_detail            varchar(255) COMMENT '客户端IP解析',
    request_body         text COMMENT '记录请求体内容，使用 TEXT 类型',
    response_status_code varchar(255) COMMENT '响应状态码',
    response_body        text COMMENT '响应体',
    user_agent           varchar(255) COMMENT '请求的用户代理信息',
    PRIMARY KEY (id) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  ROW_FORMAT=DYNAMIC COMMENT='接口日志表';

DROP TABLE IF EXISTS tb_login_log;
CREATE TABLE IF NOT EXISTS tb_login_log
(
    id                   bigint(20) NOT NULL COMMENT '主键',
    create_by            bigint(20) NOT NULL COMMENT '创建人',
    create_time          datetime(3) NOT NULL COMMENT '创建时间',
    update_by            bigint(20) NOT NULL COMMENT '最终修改人',
    update_time          datetime(3) NOT NULL COMMENT '修改时间',
    username             varchar(255) NOT NULL COMMENT '用户名',
    user_id              bigint(20) NOT NULL COMMENT '用户id',
    client_ip            varchar(255) COMMENT '客户端IP地址',
    ip_detail            varchar(255) COMMENT '客户端IP解析',
    logout_time          datetime(3) COMMENT '登出时间',
    login_status         tinyint(2) DEFAULT 1 COMMENT '登录状态',
    user_agent           varchar(255) COMMENT '请求的用户代理信息',
    mobile               tinyint(2) DEFAULT 0 COMMENT '是否移动端',
    os_sys                varchar(128) COMMENT '操作系统',
    browser              varchar(32) COMMENT '操作平台',
    engine               varchar(32) COMMENT '操作内核',
    PRIMARY KEY (id) USING BTREE,
    KEY idx_userid (user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  ROW_FORMAT=DYNAMIC COMMENT='登录日志表';

DROP TABLE IF EXISTS tb_cfg_error;
create TABLE IF NOT EXISTS tb_cfg_error(
    id                   bigint(20) NOT NULL COMMENT '主键',
    create_by            bigint(20) NOT NULL COMMENT '创建人',
    create_time          datetime(3) NOT NULL COMMENT '创建时间',
    update_by            bigint(20) NOT NULL COMMENT '最终修改人',
    update_time          datetime(3) NOT NULL COMMENT '修改时间',
    code                 bigint(10) NOT NULL COMMENT '异常码',
    target_code          bigint(10) DEFAULT 0 COMMENT '异常码对应提示码表',
    message              varchar(255) NOT NULL COMMENT '异常信息',
    PRIMARY KEY (id) USING BTREE,
    KEY idx_code (code),
    KEY idx_target_code (target_code)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  ROW_FORMAT=DYNAMIC COMMENT='异常的操作码表
10000-19999 系统异常
    11000-11999 登录状态码
    11000 登录异常
    11001 用户名或密码错误
    11002 该账户的登录凭证已过期，请重新登录!
    11003 该账户的登录凭证已过期，请重新登录!
    11004 该账户已被禁用，请联系管理员!
    11005 该账号已被锁定，请联系管理员!
    11006 该账号已过期，请联系管理员!
    11007 没有访问权限，请联系管理员!
    11008 请输入正确的验证码!
    11009 验证码失效!
    11010 非授权访问!
    11100 退出成功
    11101 退出异常


20000-29999 代码异常
30000-39999 代理异常
    30503 服务不可用
40000-49999 服务认证异常
50000-59999 服务器异常
60000-69999 sentinel 异常
    61000 您的请求过快，请稍后重试。
    61001 sentinel 融断降级处理；请稍后在试。
';
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (161706970393346103, 1, '2024-02-05 17:25:38.088', 1, '2024-02-05 17:25:38.088', 503, 0, '当前服务不可用请联系管理员');
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (161707069177593912, 1, '2024-02-05 17:26:01.845', 1, '2024-02-05 17:26:01.845', 404, 0, '当前服务不可用请联系管理员');
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (173271543499980813, 1, '2024-03-07 21:22:05.973', 1, '2024-03-07 21:22:05.973', 61000, 0, '您的请求过快，请稍后重试。');
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (173271676643966990, 1, '2024-03-07 21:22:36.894', 1, '2024-03-07 21:22:36.894', 61001, 61000, '您的请求过快，请稍后重试。');
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (173301831609352294, 1, '2024-03-07 23:19:37.740', 1, '2024-03-07 23:19:37.740', 11000, 0, '登录异常');
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (173301870264057959, 1, '2024-03-07 23:19:46.785', 1, '2024-03-07 23:19:46.785', 11001, 0, '用户名或密码错误');
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (173301904623796328, 1, '2024-03-07 23:19:54.303', 1, '2024-03-07 23:19:54.303', 11002, 0, '该账户的登录凭证已过期，请重新登录!');
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (173301938983534697, 1, '2024-03-07 23:20:02.792', 1, '2024-03-07 23:20:02.792', 11003, 0, '该账户的登录凭证已过期，请重新登录!');
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (173301973343273066, 1, '2024-03-07 23:20:10.948', 1, '2024-03-07 23:20:10.948', 11004, 0, '该账户已被禁用，请联系管理员!');
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (173302007703011435, 1, '2024-03-07 23:20:18.511', 1, '2024-03-07 23:20:18.511', 11005, 0, '该账号已被锁定，请联系管理员!');
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (173302042062749804, 1, '2024-03-07 23:20:26.246', 1, '2024-03-07 23:20:26.246', 11006, 0, '该账号已过期，请联系管理员!');
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (173302080717455469, 1, '2024-03-07 23:20:35.829', 1, '2024-03-07 23:20:35.829', 11007, 0, '没有访问权限，请联系管理员!');
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (173302115077193838, 1, '2024-03-07 23:20:43.118', 1, '2024-03-07 23:20:43.118', 11008, 0, '请输入正确的验证码!');
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (173302149436932207, 1, '2024-03-07 23:20:51.017', 1, '2024-03-07 23:20:51.017', 11009, 0, '验证码失效!');
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (173302149436932208, 1, '2024-03-07 23:20:51.017', 1, '2024-03-07 23:20:51.017', 11010, 0, '非授权访问!');
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (173302505919217776, 1, '2024-03-07 23:22:14.670', 1, '2024-03-07 23:22:14.670', 11100, 0, '退出成功');
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (173302531689021553, 1, '2024-03-07 23:22:20.530', 1, '2024-03-07 23:22:20.530', 11101, 0, '退出异常');
INSERT INTO tb_cfg_error (id, create_by, create_time, update_by, update_time, code, target_code, message) VALUES (173302531689021554, 1, '2024-03-07 23:22:20.530', 1, '2024-03-07 23:22:20.530', 30503, 0, '服务不可用');

DROP TABLE IF EXISTS tb_cfg_table_resource;
CREATE TABLE IF NOT EXISTS tb_cfg_table_resource
(
    id                   bigint(20) NOT NULL COMMENT '主键',
    create_by            bigint(20) NOT NULL COMMENT '创建人',
    create_time          datetime(3) NOT NULL COMMENT '创建时间',
    update_by            bigint(20) NOT NULL COMMENT '最终修改人',
    update_time          datetime(3) NOT NULL COMMENT '修改时间',
    table_en           varchar(64) NOT NULL COMMENT '表名',
    table_cn           varchar(128) NOT NULL COMMENT '表名',
    filed_en             varchar(64) NOT NULL COMMENT '字段英文名',
    filed_cn             varchar(128) COMMENT '字段中文名',
    filed_type           varchar(64) COMMENT '字段类型',
    filed_length         tinyint(2) DEFAULT 0 COMMENT '字段长度',
    filed_desc           varchar(512) COMMENT '字段描述',
    filed_required       tinyint(2) DEFAULT 0 COMMENT '字段是否必填',
    filed_unique         tinyint(2) DEFAULT 0 COMMENT '字段是否唯一',
    filed_nullable        tinyint(2) DEFAULT 0 COMMENT '字段是否可为空',
    filed_pk              tinyint(2) DEFAULT 0 COMMENT '字段是否为主键',
    filed_autoincrement   tinyint(2) DEFAULT 0 COMMENT '字段是否为自增',
    filed_index           tinyint(2) DEFAULT 0 COMMENT '字段是否为索引',
    filed_foreignkey      tinyint(2) DEFAULT 0 COMMENT '字段是否为外键',
    PRIMARY KEY (id) USING BTREE,
    KEY idx_table_en (table_en) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  ROW_FORMAT=DYNAMIC COMMENT='表资源';
