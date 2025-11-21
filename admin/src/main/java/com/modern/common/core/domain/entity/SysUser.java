package com.modern.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modern.common.annotation.Excel;
import com.modern.common.annotation.Excels;
import com.modern.common.core.domain.BaseEntity;
import com.modern.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 用户对象 sys_user
 *
 * @author pm
 */
public class SysUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
//    @Excel(name = "用户序号", cellType = Excel.ColumnType.STRING, prompt = "用户编号")
    @Excel(name = "用户序号", cellType = Excel.ColumnType.STRING)
    @ApiModelProperty("用户ID")
    private Long userId;

    /** 部门ID */
    @Excel(name = "归属部门ID", cellType = Excel.ColumnType.NUMERIC, type = Excel.Type.IMPORT)
    @ApiModelProperty("归属部门")
    private Long deptId;

    /** 用户账号 */
    @Excel(name = "用户账号", cellType = Excel.ColumnType.STRING)
    @ApiModelProperty("用户账号")
    private String userName;

    /** 用户昵称 */
    @Excel(name = "用户姓名", cellType = Excel.ColumnType.STRING)
    @ApiModelProperty("用户姓名")
    private String nickName;

    /** 用户邮箱 */
    @Excel(name = "用户邮箱", cellType = Excel.ColumnType.STRING)
    @ApiModelProperty("用户邮箱")
    private String email;

    /** 手机号码 */
    @Excel(name = "手机号码", cellType = Excel.ColumnType.STRING)
    @ApiModelProperty("手机号码")
    private String phonenumber;

    /** 用户性别 */
    @ApiModelProperty("用户性别")
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知", cellType = Excel.ColumnType.STRING)
    private String sex;

    /** 用户头像 */
    @ApiModelProperty("用户头像")
    private String avatar;

    /** 密码 */
//    @Excel(name = "用户密码", cellType = Excel.ColumnType.STRING, type = Excel.Type.IMPORT)
    private String password;

    /** 盐加密 */
    private String salt;

    /** 帐号状态（0正常 1停用） */
    @ApiModelProperty("帐号状态（0正常 1停用）")
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用", cellType = Excel.ColumnType.STRING)
    private String status;

    @ApiModelProperty("删除标志（0代表存在 2代表删除）")
    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 最后登录IP */
    @ApiModelProperty("最后登录IP")
    @Excel(name = "最后登录IP", type = Excel.Type.EXPORT, cellType = Excel.ColumnType.STRING)
    private String loginIp;

    /** 最后登录时间 */
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    @ApiModelProperty("最后登录时间")
    private LocalDateTime loginDate;

    /** 部门对象 */
    @Excels({
        @Excel(name = "部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT),
        @Excel(name = "部门负责人", targetAttr = "leader", type = Excel.Type.EXPORT)
    })
    @ApiModelProperty("部门")
    private SysDept dept;

    /** 角色对象 */
    @ApiModelProperty("角色对象")
    private List<SysRole> roles;

    /** 角色组 */
    @ApiModelProperty("多个角色ID集合")
//    @Excel(name = "角色IDs", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT)
    private Long[] roleIds;

    @ApiModelProperty("岗位组ID集合")
    /** 岗位组 */
    private Long[] postIds;

    /** 角色ID */
    @Excel(name = "角色ID", cellType = Excel.ColumnType.NUMERIC, type = Excel.Type.IMPORT)
    @ApiModelProperty("单个角色ID")
    private Long roleId;

    /** 车辆信息 */
    @ApiModelProperty("车辆信息")
    @Excel(name = "车辆分配信息", width = 30, cellType = Excel.ColumnType.STRING)
    private String carRoleInfo;

    public String getCarRoleInfo() {
        return carRoleInfo;
    }

    public void setCarRoleInfo(String carRoleInfo) {
        this.carRoleInfo = carRoleInfo;
    }

    public SysUser()
    {

    }

    public SysUser(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    @JsonIgnore
    @JsonProperty
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getSalt()
    {
        return salt;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getLoginIp()
    {
        return loginIp;
    }

    public void setLoginIp(String loginIp)
    {
        this.loginIp = loginIp;
    }

    public LocalDateTime getLoginDate()
    {
        return loginDate;
    }

    public void setLoginDate(LocalDateTime loginDate)
    {
        this.loginDate = loginDate;
    }

    public SysDept getDept()
    {
        return dept;
    }

    public void setDept(SysDept dept)
    {
        this.dept = dept;
    }

    public List<SysRole> getRoles()
    {
        return roles;
    }

    public void setRoles(List<SysRole> roles)
    {
        this.roles = roles;
    }

    public Long[] getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds)
    {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds()
    {
        return postIds;
    }

    public void setPostIds(Long[] postIds)
    {
        this.postIds = postIds;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("userName", getUserName())
            .append("nickName", getNickName())
            .append("email", getEmail())
            .append("phonenumber", getPhonenumber())
            .append("sex", getSex())
            .append("avatar", getAvatar())
            .append("password", getPassword())
            .append("salt", getSalt())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("loginIp", getLoginIp())
            .append("loginDate", getLoginDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("dept", getDept())
            .toString();
    }

    static Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^1[3|4|5|6|7|8|9][0-9]\\d{8}$");
    static Pattern EMAIL_PATTERN = Pattern.compile("([A-Za-z0-9]+[.-_])*[A-Za-z0-9]+@[A-Za-z0-9-]+(\\.[A-Z|a-z]{2,})+");
    public String validateParam() {
        StringBuilder errorMsg = new StringBuilder();
        if (StringUtils.isEmpty(userName) || userName.length() < 2 || userName.length() > 20) {
            errorMsg.append("<br/>&emsp;&emsp;" +  "用户账号 长度必须介于 2 至 20 之间");
        }
        if (StringUtils.isEmpty(nickName)) {
            errorMsg.append("<br/>&emsp;&emsp;" +  "用户姓名 不能为空");
        }
        if (deptId == null) {
            errorMsg.append("<br/>&emsp;&emsp;" +  "归属部门ID 不能为空或非数字");
        }
        if (roleId == null) {
            errorMsg.append("<br/>&emsp;&emsp;" +  "角色ID 不能为空或非数字");
        }
        if (!PHONE_NUMBER_PATTERN.matcher(phonenumber).matches()) {
            errorMsg.append("<br/>&emsp;&emsp;" +  "手机号码 不正确");
        }
        if (StringUtils.isNotEmpty(email) && !EMAIL_PATTERN.matcher(email).matches()) {
            errorMsg.append("<br/>&emsp;&emsp;" +  "邮箱格式 不正确");
        }
        if (StringUtils.isNotEmpty(sex) && (!sex.equals("0") && !sex.equals("1") && !sex.equals("2"))) {
            errorMsg.append("<br/>&emsp;&emsp;" +  "用户性别 不正确");
        }
        if (StringUtils.isNotEmpty(status) && (!status.equals("0") && !status.equals("1"))) {
            errorMsg.append("<br/>&emsp;&emsp;" +  "帐号状态 不正确");
        }
        if (StringUtils.isNotEmpty(errorMsg)) {
            errorMsg.insert(0, "<br/>用户账户 " + userName + " 参数错误：");
        }
        return errorMsg.toString();
    }
}
