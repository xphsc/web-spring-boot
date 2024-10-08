### 简介
* 基于SpringBoot、Spring表达式语言 (SpEL)、annotation的操作日志

### 简介
* 使用annotation来标注方法，标记操作内容
* 使用SpEL来动态生成操作日志内容，使操作日志记录更加详细（记录操作内容ID等关键信息）
* 同一个方法，不同类型用户（admin，user等）使用时，获取不同的操作者

## 快速开始
1**配置属性**: 在你的 `application.properties` 或 `application.yml` 中配置缓存的属性。
```
web:
 log
 enabled: true
```
Properties
```
web.log.enabled=true
~~
2. **实现日志接收监听。
~~~
public class OperationLogEventListener implements ApplicationListener<OperationLogEvent> {
    @Override
    public void onApplicationEvent(OperationLogEvent operationLogEvent) {
        OperationLog operationLog = operationLogEvent.getOperationLog();
    }
~~~
3.  通过注解LogField记录日志
~~~~
public class SysUserDTO {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 部门名称
     */
    @LogField(description = "部门名称")
    private String deptName;
    /**
     * 用户名
     */
    @LogField(description = "用户名")
    private String userName;

    /**
     * 用户昵称
     */
    @LogField(description = "用户昵称")
    private String nickName;

    /**
     * 邮箱
     */
    @LogField(description = "邮箱")
    @Sensitive(value = SensitiveType.EMAIL)
    private String email;

    /**
     * 电话号码
     */
    @LogField(description = "电话号码")
    @Sensitive(value = SensitiveType.FIXED_PHONE)
    @Phone
    private String phoneNumber;}
~~~~
4.  controller通过注解LogField记录日志
~~~~
public class SysUserController {
    @PatchMapping("updateUser")
    @ApiOperation(value = "通过id修改用户`")
    @SysOperationLog(description = "通过id修改用户", actionType = ActionType.UPDATE)
    public Object updateUser(@RequestBody SysUserDTO sysUserDTO) {
    //修改与原数据比对
      OperationLogContext.putVariable(sysUserMapper.selectByPrimaryKey(sysUserDTO.getUserId()));
        this.sysUserService.updateUser(sysUserDTO);
        return Response.ok();
    }

}

5. 自定义content内容
~~~
 @PatchMapping("resetPassword")
    @ApiOperation(value = "通过id修改重置密码")
    @SysOperationLog(description = "通过id修改重置密码", actionType = ActionType.UPDATE, content ="'设置用户'+ #userName +' 的新密码为' + #userPasswordQuery.newPassword" )
    public Object resetPassword(@RequestBody UserPasswordQuery userPasswordQuery) {
      OperationLogContext.putVariable("userName",this.sysUserMapper.selectByPrimaryKey(passwordQuery.getUserId()).getUserName());
        this.sysUserService.resetPassword(userPasswordQuery);
        return Response.ok();
    }
~~~~