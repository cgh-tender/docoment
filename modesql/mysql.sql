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
    KEY idx_business_ware (business_id,ware_inside_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品销售统计';


create table IF NOT EXISTS tb_cfg_datasource
(
    id          bigint(20) NOT NULL auto_increment COMMENT '主键',
    create_by bigint(20) NOT NULL COMMENT '创建人',
    create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by bigint(20) NOT NULL COMMENT '最终修改人',
    update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    url         varchar(256) NOT NULL COMMENT '数据库连接地址',
    username         varchar(256) NOT NULL COMMENT '数据库连接用户名',
    password         varchar(256) NOT NULL COMMENT '数据库连接密码',
    PRIMARY KEY (id) USING BTREE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  ROW_FORMAT=DYNAMIC COMMENT='数据库连接表';

create table IF NOT EXISTS tb_cfg_resource
(
    id          bigint(20) NOT NULL auto_increment COMMENT '主键',
    parent_id bigint(20) DEFAULT 0 COMMENT '父菜单code',
    create_by bigint(20) NOT NULL COMMENT '创建人',
    create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by bigint(20) NOT NULL COMMENT '最终修改人',
    update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
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
INSERT INTO tb_cfg_resource (id, parent_id, create_by, create_time, update_by, update_time, name, path, description, deleted, status, redirect, alias, before_enter, before_route_leave, component, keep_alive, meta, sort) VALUES (157586258510479372, 0, 1, '2024-01-25 14:55:11', 1, '2024-01-25 14:55:11', 'Permission', '/permission', null, 0, 0, '/permission/page', null, null, null, 'Layouts', 0, '{"activeMenu":"","affix":null,"alwaysShow":true,"breadcrumb":null,"elIcon":"","hidden":null,"keepAlive":null,"roles":["admin","editor"],"svgIcon":"lock","title":"权限管理"}', 0);
INSERT INTO tb_cfg_resource (id, parent_id, create_by, create_time, update_by, update_time, name, path, description, deleted, status, redirect, alias, before_enter, before_route_leave, component, keep_alive, meta, sort) VALUES (157587010129756173, 157586258510479372, 1, '2024-01-25 14:58:05', 1, '2024-01-25 14:58:05', 'PagePermission', 'page', null, 0, 0, null, null, null, null, 'permission/page', 1, '{"activeMenu":"","affix":null,"alwaysShow":null,"breadcrumb":null,"elIcon":"","hidden":null,"keepAlive":null,"roles":[],"svgIcon":"","title":"页面权限"}', 0);
INSERT INTO tb_cfg_resource (id, parent_id, create_by, create_time, update_by, update_time, name, path, description, deleted, status, redirect, alias, before_enter, before_route_leave, component, keep_alive, meta, sort) VALUES (157587010129756174, 157586258510479372, 1, '2024-01-25 14:58:05', 1, '2024-01-25 14:58:05', 'DirectivePermission', 'directive', null, 0, 0, null, null, null, null, 'permission/directive', 1, '{"activeMenu":"","affix":null,"alwaysShow":null,"breadcrumb":null,"elIcon":"","hidden":null,"keepAlive":null,"roles":[],"svgIcon":"","title":"指令权限"}', 0);
INSERT INTO tb_cfg_resource (id, parent_id, create_by, create_time, update_by, update_time, name, path, description, deleted, status, redirect, alias, before_enter, before_route_leave, component, keep_alive, meta, sort) VALUES (157587010129756175, 157586258510479372, 1, '2024-01-25 14:58:05', 1, '2024-01-25 14:58:05', 'umsUser', 'directive', null, 0, 0, null, null, null, null, 'permission/directive', 1, '{"activeMenu":"","affix":null,"alwaysShow":null,"breadcrumb":null,"elIcon":"","hidden":null,"keepAlive":null,"roles":[],"svgIcon":"","title":"指令权限1"}', 0);

CREATE TABLE IF NOT EXISTS tb_cfg_user
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime   NOT NULL COMMENT '创建时间',
    update_time datetime   NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    username        varchar(128) NOT NULL COMMENT '用户名称',
    password        varchar(128) NOT NULL COMMENT '密码',
    phone       varchar(20) DEFAULT NULL COMMENT '手机号',
    email       varchar(20) DEFAULT NULL COMMENT '邮箱',
    gender tinyint(2) DEFAULT NULL COMMENT '性别',
    status  tinyint(2) DEFAULT NULL COMMENT '用户状态',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';
INSERT INTO tb_cfg_user (id, create_by, create_time, update_time, update_by, username, password, phone, email, gender, status) VALUES (1, 1, '2024-01-25 16:10:52', '2024-01-25 08:11:07', 1, 'admin', '{bcrypt}$2a$10$erkqLdvTsaccM.aMC1g/fOBixc2WQH/f6iEWximSb06Xh86gM3LyK', '18334774205', 'late_tender@163.com', 0, 0);
INSERT INTO tb_cfg_user (id, create_by, create_time, update_time, update_by, username, password, phone, email, gender, status) VALUES (2, 1, '2024-01-25 16:10:52', '2024-01-25 08:11:07', 1, 'test', '{bcrypt}$2a$10$CT7jn1Pri2JBcVIaBle8Ie9tViTnKiwjbh5cOa6MX/CrKv2BAiqze', '18334774205', 'late_tender@163.com', 0, 0);

CREATE TABLE IF NOT EXISTS tb_cfg_role
(
    id          bigint(20) NOT NULL,
    create_by   bigint(20) NOT NULL COMMENT '创建者',
    create_time datetime   NOT NULL COMMENT '创建时间',
    update_time datetime   NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by   bigint(20) NOT NULL COMMENT '更新者',
    name        varchar(255) DEFAULT NULL COMMENT '名称',
    description varchar(255) DEFAULT NULL COMMENT '描述',
    parent_id   bigint(20)   DEFAULT 0 COMMENT '父ID',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';
INSERT INTO tb_cfg_role (id, create_by, create_time, update_time, update_by, name, description, parent_id) VALUES (1, 1, '2024-01-25 16:14:37', '2024-01-25 08:14:54', 1, 'admin', '超级管理员', 0);
INSERT INTO tb_cfg_role (id, create_by, create_time, update_time, update_by, name, description, parent_id) VALUES (2, 1, '2024-01-25 16:14:38', '2024-01-25 08:14:54', 1, 'test', '测试人员', 0);

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
INSERT INTO tb_user_role (id, create_by, create_time, update_time, update_by, user_id, role_id) VALUES (157607312440164375, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, 1, 1);
INSERT INTO tb_user_role (id, create_by, create_time, update_time, update_by, user_id, role_id) VALUES (157607312440164376, 1, '2024-01-25 16:16:52', '2024-01-25 16:16:52', 1, 2, 2);


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
    description     varchar(256) DEFAULT null COMMENT '描述',
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
    type    tinyint(2) DEFAULT null COMMENT '资源类型',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色资源关系表';

CREATE TABLE IF NOT EXISTS tb_controller_log
(
    id                   bigint(20) NOT NULL auto_increment COMMENT '主键',
    create_by            bigint(20) NOT NULL COMMENT '创建人',
    create_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by            bigint(20) NOT NULL COMMENT '最终修改人',
    update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    http_method          varchar(10) COMMENT '方法名称',
    request_url          varchar(255) COMMENT '请求的 URL',
    client_ip            varchar(15) COMMENT '录请求的客户端 IP 地址',
    request_body         text COMMENT '记录请求体内容，使用 TEXT 类型',
    response_status_code varchar(255) COMMENT '响应状态码',
    response_body        text COMMENT '响应体',
    user_agent           varchar(255) COMMENT '请求的用户代理信息',
    PRIMARY KEY (id) USING BTREE
);

CREATE TABLE IF NOT EXISTS tb_login_log
(
    id                   bigint(20) NOT NULL auto_increment COMMENT '主键',
    create_by            bigint(20) NOT NULL COMMENT '创建人',
    create_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by            bigint(20) NOT NULL COMMENT '最终修改人',
    update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    username             varchar(255) NOT NULL COMMENT '用户名',
    user_id              bigint(20) NOT NULL COMMENT '用户id',
    client_ip            varchar(255) COMMENT '客户端IP地址',
    logout_time          datetime COMMENT '登出时间',
    login_status         tinyint(2) COMMENT '登录状态',
    user_agent           varchar(255) COMMENT '请求的用户代理信息',
    PRIMARY KEY (id) USING BTREE,
    KEY idx_userid (user_id)
);