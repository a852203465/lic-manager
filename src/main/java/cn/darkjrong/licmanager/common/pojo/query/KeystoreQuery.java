package cn.darkjrong.licmanager.common.pojo.query;

import cn.darkjrong.licmanager.common.pojo.dto.KeystoreFilterDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 秘钥库查询对象
 * </p>
 *
 * @author Rong.Jia
 * @since 2023-07-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class KeystoreQuery extends KeystoreFilterDTO implements Serializable {

    private static final long serialVersionUID = 3072774236798956556L;


}
