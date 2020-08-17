package com.ytao.mapper;

import com.ytao.pojo.TbOrderItem;
import com.ytao.pojo.TbOrderItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbOrderItemMapper {
    int countByExample(TbOrderItemExample example);

    int deleteByExample(TbOrderItemExample example);

    int insert(TbOrderItem record);

    int insertSelective(TbOrderItem record);

    List<TbOrderItem> selectByExample(TbOrderItemExample example);

    int updateByExampleSelective(@Param("record") TbOrderItem record, @Param("example") TbOrderItemExample example);

    int updateByExample(@Param("record") TbOrderItem record, @Param("example") TbOrderItemExample example);
}