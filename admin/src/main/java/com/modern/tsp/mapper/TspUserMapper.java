package com.modern.tsp.mapper;

import com.modern.common.core.base.BaseMapperPlus;
import com.modern.tsp.domain.TspUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 摩登 - TSP - TSP用户 Mapper 接口
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
public interface TspUserMapper extends BaseMapperPlus<TspUser> {

    @Select({
            "SELECT t.id,c.plate_code AS 'plateCode',a.vin AS 'vin',CONCAT(m.vehicle_model_name,b.std_mode_name)  AS 'vehicleModel'," +
                    "CONCAT(f.name,e.model_name) AS 'equipmentModel',d.icc_id AS 'ICCID',d.sn AS 'SN',d.imei AS 'IMEI',d.sim AS 'sim', " +
                    "(CASE g.status WHEN 'true' THEN '已实名认证' WHEN 'false' THEN '未实名认证' ELSE '未实名认证' END) AS 'status', " +
                    "g.card_auth_info AS 'cardInfo',g.receive_time AS date,a.motor_num AS 'motorNum' " +
                    "FROM tsp_user t " +
                    "LEFT JOIN tsp_vehicle a " +
                    "ON a.tsp_user_id = t.id " +
                    "LEFT JOIN tsp_vehicle_std_model b " +
                    "ON b.id = a.tsp_vehicle_std_model_id " +
                    "LEFT JOIN tsp_vehicle_model m " +
                    "ON m.id = b.tsp_vehicle_model_id " +
                    "LEFT JOIN tsp_vehicle_license c " +
                    "ON c.tsp_vehicle_id = a.id " +
                    "LEFT JOIN tsp_equipment d " +
                    "ON d.id = a.tsp_equipment_id " +
                    "LEFT JOIN tsp_equipment_model e " +
                    "ON e.id = d.tsp_equipment_model_id " +
                    "LEFT JOIN tsp_equipment_type f " +
                    "ON f.id = e.tsp_equipment_type_id " +
                    "LEFT JOIN tsp_vehicle_identification_receive g " +
                    "ON g.vin = a.vin " +
                    "WHERE t.id = #{tspUserId}"
    })
    List<Map<String, Object>> findCarInfo(Long tspUserId);

    @Select({
            "SELECT DISTINCT c.plate_code AS 'plateCode',a.vin AS 'vin',CONCAT(m.vehicle_model_name,b.std_mode_name)  AS 'vehicleModel', " +
                    "CONCAT(f.name,e.model_name) AS 'equipmentModel',d.icc_id AS 'ICCID',d.sn AS 'SN',d.imei AS 'IMEI',d.sim AS 'sim'," +
                    "(CASE g.status WHEN 'true' THEN '已实名认证' WHEN 'false' THEN '未实名认证' ELSE '未实名认证' END) AS 'status', " +
                    "g.card_auth_info AS 'cardInfo',g.receive_time AS date,a.motor_num AS 'motorNum' " +
                    "FROM tsp_user_vehicle t " +
                    "LEFT JOIN tsp_vehicle a ON a.id = t.tsp_vehicle_id " +
                    "LEFT JOIN tsp_vehicle_std_model b ON b.id = a.tsp_vehicle_std_model_id " +
                    "LEFT JOIN tsp_vehicle_model m ON m.id = b.tsp_vehicle_model_id " +
                    "LEFT JOIN tsp_vehicle_license c ON c.tsp_vehicle_id = a.id " +
                    "LEFT JOIN tsp_vehicle_equipment h ON h.tsp_vehicle_id = a.id " +
                    "LEFT JOIN tsp_equipment d ON d.id = h.tsp_equipment_id " +
                    "LEFT JOIN tsp_equipment_model e ON e.id = d.tsp_equipment_model_id " +
                    "LEFT JOIN tsp_equipment_type f ON f.id = e.tsp_equipment_type_id " +
                    "LEFT JOIN tsp_vehicle_identification_receive g ON g.vin = a.vin WHERE t.tsp_user_id = #{tspUserId}"
    })
    List<Map<String, Object>> findHistory(Long tspUserId);

    @Select({
            "SELECT id FROM provinces WHERE merger_name = #{mergerName}"
    })
    Integer getAddress(String mergerName);
}
