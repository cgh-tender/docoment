package cn.com.cgh.login.auth.service.impl;

import cn.com.cgh.login.auth.mapper.TbCfgOrganizationMapper;
import cn.com.cgh.login.auth.service.ITbCfgOrganizationService;
import cn.com.cgh.romantic.pojo.TbCfgOrganization;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TbCfgOrganizationImpl extends ServiceImpl<TbCfgOrganizationMapper, TbCfgOrganization>  implements ITbCfgOrganizationService {
}
