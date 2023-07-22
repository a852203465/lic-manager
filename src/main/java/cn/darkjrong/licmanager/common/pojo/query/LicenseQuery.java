package cn.darkjrong.licmanager.common.pojo.query;

import cn.darkjrong.licmanager.common.pojo.dto.LicenseFilterDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * license信息查询对象
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LicenseQuery extends LicenseFilterDTO implements Serializable {

    private static final long serialVersionUID = 3365919605740992275L;





}