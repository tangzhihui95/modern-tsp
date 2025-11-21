package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.core.domain.FrontQuery;
import com.modern.common.utils.StringUtils;
import com.modern.tsp.domain.TspEquipmentModel;
import com.modern.tsp.domain.TspEquipmentType;
import com.modern.tsp.mapper.TspEquipmentModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/15 11:50
 */
@Service
public class TspEquipmentModelRepository extends ServicePlusImpl<TspEquipmentModelMapper, TspEquipmentModel,TspEquipmentModel> {
    public TspEquipmentModel getByModelName(String modelName) {
        QueryWrapper<TspEquipmentModel> ew = new QueryWrapper<>();
        ew.eq("model_name",modelName);
        return this.getOne(ew);
    }

    public TspEquipmentModel getByModelNameNotId(String modelName, Long tspEquipmentModelId) {
        QueryWrapper<TspEquipmentModel> ew = new QueryWrapper<>();
        ew.eq("model_name",modelName);
        ew.notIn("id",tspEquipmentModelId);
        return this.getOne(ew);
    }

    public TspEquipmentModel getByModelNameAndType(String modelName, Long tspEquipmentTypeId) {
        QueryWrapper<TspEquipmentModel> ew = new QueryWrapper<>();
        ew.eq("model_name",modelName);
        ew.eq("tsp_equipment_type_id",tspEquipmentTypeId);
        return this.getOne(ew);
    }

    public List<TspEquipmentModel> findByTspEquipmentTypeId(Long id) {
        QueryWrapper<TspEquipmentModel> ew = new QueryWrapper<>();
        ew.eq("tsp_equipment_type_id",id);
        return this.list(ew);
    }

    public List<TspEquipmentModel> findByTspModelId(Long tspEquipmentTypeId,Long tspEquipmentModelId) {
        QueryWrapper<TspEquipmentModel> ew = new QueryWrapper<>();
        ew.eq("tsp_equipment_type_id",tspEquipmentTypeId);
        ew.eq("id",tspEquipmentModelId);
        return this.list(ew);
    }

    public int countByTspEquipmentModelId(Long id) {
        QueryWrapper<TspEquipmentModel> ew = new QueryWrapper<>();
        ew.eq("tsp_equipment_type_id",id);
        return this.count(ew);
    }

    public List<TspEquipmentModel> findByTspEquipmentIdLikeName(Long tspEquipmentId, String modelName) {
        QueryWrapper<TspEquipmentModel> ew = new QueryWrapper<>();
        ew.eq(TspEquipmentModel.ID,tspEquipmentId);
        ew.like(!StringUtils.isEmpty(modelName),"model_name",modelName);
        return this.list(ew);
    }

    public TspEquipmentModel getByIdContainsDelete(Long tspEquipmentId) {
        //QueryWrapper<TspEquipmentModel> ew = new QueryWrapper<>();
        //ew.eq(TspEquipmentModel.ID,tspEquipmentId);
        //ew.and(item->item.eq(TspEquipmentModel.IS_DELETE,0).or().eq(TspEquipmentModel.IS_DELETE,1));
        return baseMapper.getByIdContainsDelete(tspEquipmentId);
    }

    public QueryWrapper<TspEquipmentModel> getPageList(FrontQuery vo) {
        QueryWrapper<TspEquipmentModel> ew = new QueryWrapper<>();
        ew.and(StringUtils.isNotEmpty(vo.getSearch()),
                q -> q.like(TspEquipmentType.NAME, vo.getSearch())
                        .or().like(TspEquipmentType.EXTRA_TYPE, vo.getSearch()));
        ew.eq(vo.getTspEquipmentModelId() != null,"t.id",vo.getTspEquipmentModelId());
        ew.eq("t.is_delete","0");
        ew.orderByDesc("a.update_time");
        return ew;
    }
}
