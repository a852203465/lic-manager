package cn.darkjrong.licmanager.common.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 主页VO
 *
 * @author Rong.Jia
 * @date 2023/07/23
 */
@Data
@ApiModel("主页信息")
public class HomePageVO implements Serializable {

    private Long keystoreTotal;
    private Long projectTotal;
    private Long licenseTotal;
    private Long userInfoTotal;

}
