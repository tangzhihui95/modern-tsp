package com.modern.web.service.impl.system;

import com.modern.common.annotation.DataScope;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.constant.UserConstants;
import com.modern.common.core.domain.entity.SysDept;
import com.modern.common.core.domain.entity.SysRole;
import com.modern.common.core.domain.entity.SysUser;
import com.modern.common.core.domain.model.LoginUser;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.StringUtils;
import com.modern.common.utils.spring.SpringUtils;
import com.modern.common.web.service.TokenService;
import com.modern.tsp.domain.TspVehicle;
import com.modern.web.domain.system.SysPost;
import com.modern.web.domain.system.SysUserPost;
import com.modern.web.domain.system.SysUserRole;
import com.modern.web.domain.vo.system.SysUserBindVehicleVO;
import com.modern.web.domain.vo.system.SysUserPasswordResetVO;
import com.modern.web.mapper.system.*;
import com.modern.web.service.system.ISysConfigService;
import com.modern.web.service.system.ISysUserService;
import com.modern.xtsp.service.XTspVehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.modern.common.utils.SecurityUtils.getLoginUser;

/**
 * 用户 业务层处理
 *
 * @author pm
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private XTspVehicleService xTspVehicleService;

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user) {

        return userMapper.selectUserList(user);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user) {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user) {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName) {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName) {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysPost post : list) {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String userName) {
        int count = userMapper.checkUserNameUnique(userName);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(Long userId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users)) {
                throw new ServiceException("没有权限访问用户数据！");
            }
        }
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        user.setDelFlag("0");
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(SysUser user) {
        return userMapper.insertUser(user) > 0;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @CacheEvict(value = "TspVehicle", allEntries = true)
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        return userMapper.updateUser(user);
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    @CacheEvict(value = "TspVehicle", allEntries = true)
    @Override
    @Transactional
    public void insertUserAuth(Long userId, Long[] roleIds) {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar   头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return userMapper.resetUserPwd(userName, password);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds) {
        if (StringUtils.isNotNull(roleIds)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roleIds) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserByIds(Long[] userIds) {
        for (Long userId : userIds) {
            checkUserAllowed(new SysUser(userId));
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPost(userIds);
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @CacheEvict(value = "TspVehicle", allEntries = true)
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(userList) || userList.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList) {
            try {
                // 验证用户、部门ID、角色ID是否存在
                SysUser sysUser = userMapper.selectUserByUserName(user.getUserName());
                SysDept sysDept = deptMapper.selectDeptById(user.getDeptId());
                SysRole sysRole = roleMapper.selectRoleById(user.getRoleId());
                if (StringUtils.isNotNull(sysUser) && sysUser.getIsDelete() > 0) {  //数据已删除
                    sysUser = null;
                }
                if (StringUtils.isNotNull(sysDept) && sysDept.getIsDelete() > 0) {  //数据已删除
                    sysDept = null;
                }
                if (StringUtils.isNotNull(sysRole) && sysRole.getIsDelete() > 0) {  //数据已删除
                    sysRole = null;
                }
                if ((StringUtils.isNotNull(sysUser) && !isUpdateSupport) || StringUtils.isNull(sysDept) || StringUtils.isNull(sysRole)) {
                    failureNum++;

                    StringBuilder thisUserFailureMsg = new StringBuilder();
                    thisUserFailureMsg.append("<br/>用户账户 " + user.getUserName() + " 参数错误：");
                    if (StringUtils.isNotNull(sysUser) && !isUpdateSupport) {
                        thisUserFailureMsg.append("<br/>&emsp;&emsp;" + "用户账户 " + user.getUserName() + " 已存在");
                    }
                    if (StringUtils.isNull(sysDept)) {
                        thisUserFailureMsg.append("<br/>&emsp;&emsp;" + "部门ID " + user.getDeptId() + " 不存在");
                    }
                    if (StringUtils.isNull(sysRole)) {
                        thisUserFailureMsg.append("<br/>&emsp;&emsp;" + "角色ID " + user.getRoleId() + " 不存在");
                    }

                    failureMsg.append(thisUserFailureMsg);
                } else {
                    if (StringUtils.isNull(sysUser)) {
                        //新增用户
                        user.setPassword(SecurityUtils.encryptPassword(password));
                        user.setCreateBy(operName);
                        Long[] roleIds = {user.getRoleId()};
                        user.setRoleIds(roleIds);
                        this.insertUser(user);
                        successNum++;
                        successMsg.append("<br/>"  + "用户账户 " + user.getUserName() + " 导入成功");
                    } else {
                        //更新用户 u != null && isUpdateSupport
                        user.setUpdateBy(operName);
                        this.updateUser(user);
                        successNum++;
                        successMsg.append("<br/>"  + "用户账户 " + user.getUserName() + " 更新成功");
                    }
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>"  + "用户账户 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + "<br/>" + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            if (successNum > 0) {
                failureMsg.append("<br/><br/>成功导入 " + successNum + " 条数据如下：");
                failureMsg.append(successMsg);
            }
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public void passwordReset(SysUserPasswordResetVO vo) {
        SysUser sysUser = userMapper.selectUserById(vo.getUserId());
        if (sysUser == null) {
            ErrorEnum.SYS_USER_USER_NULL_ERR.throwErr();
        }
        if (SecurityUtils.matchesPassword(vo.getPassword(), sysUser.getPassword())) {
            ErrorEnum.SYS_USER_PSW_IDENTICI_ERR.throwErr();
        }
        if (!vo.getPassword().equals(vo.getConferMaPassword())){
            ErrorEnum.SYS_USER_PSW_ATYPISM_ERR.throwErr();
        }
        LoginUser loginUser = getLoginUser();
        // 更新缓存用户密码
        loginUser.getUser().setPassword(SecurityUtils.encryptPassword(vo.getPassword()));
        sysUser.setUpdateBy(SecurityUtils.getUsername());
        userMapper.updateUser(sysUser);
        // 刷新缓存信息
        tokenService.setLoginUser(loginUser);
        this.checkUserAllowed(sysUser);
    }

    /**
     * 车辆绑定
     * 车辆绑定操作是否有权限
     * 车辆操作后的用户的数据查看权限取决于角色
     *
     * 操作联动业务应开启事务
     * @param vo vo
     */
    @CacheEvict(value = "TspVehicle", allEntries = true)
    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void bindVehicle(SysUserBindVehicleVO vo) {
        SysUser sysUser = userMapper.selectUserById(vo.getUserId());
        if (sysUser == null){
            ErrorEnum.SYS_USER_USER_NULL_ERR.throwErr();
        }
        List<SysRole> roles = roleMapper.selectRolePermissionByUserId(vo.getUserId());
        if (CollectionUtils.isEmpty(roles)){
            ErrorEnum.SYS_USER_ROLE_NOT_PERMISSINON_ERR.throwErr();
        }

//        for (Long vehicleId : vo.getVehicleIds()) {
//            TspVehicle vehicle = tspVehicleService.getById(vehicleId);
//            if (vehicle == null){
//                ErrorEnum.SYS_USER_BIND_VEHICLE_NULL_ERR.throwErr();
//            }
//            TspUseVehicle useVehicle = tspUseVehicleService.getByTspVehicleIdAndUserId(vehicle.getId(),vo.getUserId());
//            if (useVehicle == null){
//                // 重新建立绑定信息
//                //TODO builder方式可能会有缺点
//                TspUseVehicle builder = TspUseVehicle.builder()
//                        .sysUserId(vo.getUserId())
//                        .tspVehicleId(vehicleId)
//                        .bindTime(LocalDateUtils.getCurrentTime())
//                        .createBy(SecurityUtils.getUsername())
//                        .updateBy(SecurityUtils.getUsername()).build();
//                tspUseVehicleService.save(builder);
//            }else {
//                // 已存在的绑定信息只做更新人操作其他不变
//                useVehicle.setUpdateBy(SecurityUtils.getUsername());
//                tspUseVehicleService.updateById(useVehicle);
//            }
//        }
    }

    @Override
    public List<SysUser> selectExportUserList(SysUser user, List<Long> ids) {
        List<SysUser> list;
        if(ids != null && ids.size() > 0) {
            //通过选中条件筛选
            list = userMapper.selectBatchIds(ids);
        } else {
            //通过查询条件查询
            list = selectUserList(user);
        }
        for (SysUser s : list) {
            List<TspVehicle> vehicles = xTspVehicleService.getAllVehiclesBySysUserId(s.getUserId());
            String carRoleInfo = vehicles.stream().map(v -> "车辆VIN:" + v.getVin()).collect(Collectors.joining("; "));
            s.setCarRoleInfo(carRoleInfo);
        }

        return list != null ? list : new ArrayList<>();
    }
}
