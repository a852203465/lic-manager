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

    SUBJECT_UNAUTHORIZED(4000, "无权访问:当前用户没有此请求所需权限"),
    USER_NAME_OR_PASSWORD_ERRORS_GREATER_THAN_5_TIMES(4001, "用户名或密码错误次数大于5次,账户已锁定, 请10分钟后再次访问"),
    THE_ACCOUNT_DOES_NOT_EXIST_PLEASE_CHANGE_THE_ACCOUNT_TO_LOGIN(4004, "账号不存在，请更换账号登录"),
    THE_ACCOUNT_OR_PASSWORD_IS_INCORRECT(4009, "账号或密码不正确"),

    THE_KEY_LIBRARY_ALREADY_EXISTS(5000, "秘钥库已存在"),
    THE_KEY_LIBRARY_DOES_NOT_EXIST(5001, "秘钥库不存在,或已删除"),
    THE_PROJECT_ALREADY_EXISTS(5002, "项目信息已存在"),
    THE_PROJECT_DOES_NOT_EXIST(5003, "项目信息不存在,或已删除"),
    THE_LICENSE_ALREADY_EXISTS(5004, "许可证已存在"),
    THE_LICENSE_DOES_NOT_EXIST(5005, "许可证不存在,或已删除"),
    UNGENERATED_LICENSE(5006, "未生成许可证,请先生成"),
    THE_USER_ALREADY_EXISTS(5007, "用户已存在"),
    THE_USER_DOES_NOT_EXIST(5008, "用户不存在,或已删除"),
    THE_OLD_PASSWORD_IS_INCORRECT(5009, "原密码不正确"),
    THE_NEW_PASSWORD_IS_THE_SAME_AS_THE_OLD_PASSWORD(5010, "新密码与原密码相同"),
    SYSTEM_ADMINISTRATOR_CANNOT_DISABLE(5011,"系统管理员不能禁用"),
    CURRENT_USER_CANNOT_DISABLE(5012,"当前用户不能禁用, 该用户为当前登录用户"),
    THE_ACCOUNT_CANNOT_BE_EMPTY(5013, "账号不能为空"),
    THE_PASSWORD_FORMAT_IS_INCORRECT(5014, "密码格式不正确"),









    ;

    private final Integer code;
    private final String message;

}
