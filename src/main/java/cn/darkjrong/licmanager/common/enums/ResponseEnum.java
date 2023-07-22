package cn.darkjrong.licmanager.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应枚举
 *
 * @author Rong.Jia
 * @date 2023/07/19
 */
@AllArgsConstructor
@Getter
public enum ResponseEnum {

    // 成功
    SUCCESS(0, "成功"),

    // 参数不正确
    PARAMETER_ERROR(1, "参数不正确"),

    // 失败
    ERROR(-1, "失败"),
    SYSTEM_ERROR(500, "系统错误"),
    FILE_LIMIT_EXCEEDED(-1, "文件超出限制, 请选择较小文件"),

    ACCESS_TOKEN_INVALID(401, "access_token无效"),
    REFRESH_TOKEN_INVALID(401, "refresh_token无效"),
    UNAUTHORIZED(401, "无权访问(未授权)"),
    AUTHORIZATION_EXPIRES(401, "授权过期, 请求重新登录"),
    NOT_LOGGED_IN(401, "未登录，或者授权过期"),
    ANONYMOUS_SUBJECT_UNAUTHORIZED(401, "无权访问:当前用户是匿名用户，请先登录"),
    AUTHENTICATION_FAILED(401, "身份验证未通过"),
    MISSING_TOKEN_AUTHENTICATION_FAILED(401, "缺失令牌,鉴权失败"),

    // 未找到
    NOT_FOUND(404, "请求接口不存在"),

    // 请求方式错误
    REQUEST_MODE_ERROR(405, "请求方式错误, 请检查"),

    //媒体类型不支持
    MEDIA_TYPE_NOT_SUPPORTED(415, "媒体类型不支持"),

    REQUEST_PARAMETER_FORMAT_IS_INCORRECT(9999, "请求参数格式不正确"),
    THE_PARAMETER_TYPE_IS_INCORRECT(9999, "参数类型不正确"),
    LACK_OF_PARAMETER(9999, "缺少必要参数，请检查"),

    THE_ID_CANNOT_BE_EMPTY(1002, "ID 不能为空"),
    THE_NAME_CANNOT_BE_EMPTY(1003, "名称不能为空"),
    DATA_QUOTE(1004, "数据被引用，无法执行操作"),
    TIME_IS_EMPTY(1005, "时间为空"),
    INVALID_SPECIFIED_STATE(1006, "指定状态无效"),
    THE_STARTING_TIME_CANNOT_BE_LESS_THAN_OR_EQUAL_TO_THE_CURRENT_TIME(1007, "开始时间不能小于等于当前时间"),
    THE_END_TIME_CANNOT_BE_LESS_THAN_OR_EQUAL_TO_THE_START_TIME(1008, "结束时间不能等于小于开始时间"),
    THE_END_TIME_CANNOT_BE_LESS_THAN_OR_EQUAL_TO_THE_CURRENT_TIME(1009, "结束时间不能小于等于当前时间"),
    THE_CODE_CANNOT_BE_EMPTY(1010, "CODE不能为空"),
    FILE_UPLOAD_EXCEPTION_RETRY(1012, "文件上传异常, 请重试"),
    FILE_DOES_NOT_EXIST(1013, "文件不存在, 请检查"),

    SUBJECT_UNAUTHORIZED(4000, "无权访问:当前用户没有此请求所需权限"),
    USER_NAME_OR_PASSWORD_ERRORS_GREATER_THAN_5_TIMES(4001, "用户名或密码错误次数大于5次,账户已锁定, 请10分钟后再次访问"),
    ACCOUNT_AUTHORIZATION_EXPIRED(4002, "账号授权过期"),
    ACCOUNT_LOGIN_IS_PROHIBITED(4003, "账号禁止登陆"),
    THE_ACCOUNT_DOES_NOT_EXIST_PLEASE_CHANGE_THE_ACCOUNT_TO_LOGIN(4004, "账号不存在，请更换账号登录"),
    PROHIBIT_THE_LOGIN(4005, "禁止登录"),
    NO_PERMISSIONS(4006, "暂无权限， 请联系管理员"),
    THE_ROLE_IDS_DOES_NOT_EXIST_PLEASE_CHANGE_THE_ACCOUNT_TO_LOGIN(4007, "角色不存在，请联系管理员分配角色"),
    ACCOUNT_AUTOMATIC_LOGOUT(4008, "账号已自动退出登录，无需再次退出登录"),
    THE_ACCOUNT_OR_PASSWORD_IS_INCORRECT(4009, "账号或密码不正确"),
    THE_ACCOUNT_IS_NOT_EXISTS(4010, "账号不存在"),

    THE_KEY_LIBRARY_ALREADY_EXISTS(5000, "秘钥库已存在"),
    THE_KEY_LIBRARY_DOES_NOT_EXIST(5001, "秘钥库不存在,或已删除"),
    THE_PROJECT_ALREADY_EXISTS(5002, "项目信息已存在"),
    THE_PROJECT_DOES_NOT_EXIST(5003, "项目信息不存在,或已删除"),














    ;

    private final Integer code;
    private final String message;

}
