package com.zhidianfan.pig.yd.moduler.resv.vo;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.TableImageDO;
import lombok.Data;

import java.util.List;

/**
 * @author wangyz
 * @version v 0.1 2019-05-06 09:27 wangyz Exp $
 */
@Data
public class TableHotMapVO {

    private String imageUrl;

    private Integer tableAreaId;

    private Integer businessId;

    private List<TableImageDO> tableImages;
}
