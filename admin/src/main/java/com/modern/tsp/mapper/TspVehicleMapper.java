package com.modern.tsp.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.base.BaseMapperPlus;
import com.modern.exInterface.entity.VehicleIntegrate;
import com.modern.exInterface.model.dto.VehicleGpsSelectListDTO;
import com.modern.tsp.domain.TspVehicle;
import com.modern.tsp.model.dto.*;
import com.modern.tsp.model.vo.AddressVo;
import com.modern.tsp.model.vo.TspVehicleAlertDataVO;
import com.modern.tsp.model.vo.TspVehicleOnlineDataVO;
import com.modern.tsp.model.vo.TspVehicleRangeDataVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * <p>
 * 摩登 - TSP - 车辆信息 Mapper 接口
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
public interface TspVehicleMapper extends BaseMapperPlus<TspVehicle> {

    @Select({"SELECT e.icc_id AS 'iccid' FROM tsp_equipment e " +
            "LEFT JOIN tsp_vehicle v ON e.id = v.tsp_equipment_id " +
            "WHERE v.vin = '${vin}';"})
    String selectEquipmentICCIDByVin(@Param("vin") String vin);

    @Select({
            "SELECT t.id,t.tsp_equipment_id,t.tsp_vehicle_std_model_id,t.certification_state,t.vin,t.state,t.send_status,t.send_time,t.create_time,a.tsp_vehicle_model_id,a.std_mode_name,b.mobile,b.real_name,c.sim,c.is_online,e.plate_code,g.vehicle_model_name " +
                    "FROM tsp_vehicle t " +
                    "LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id " +
                    "LEFT JOIN tsp_vehicle_model g ON g.id = a.tsp_vehicle_model_id " +
                    "LEFT JOIN tsp_user b on t.tsp_user_id = b.id " +
                    "LEFT JOIN tsp_vehicle_license e on e.tsp_vehicle_id = t.id " +
                    "LEFT JOIN tsp_equipment c on t.tsp_equipment_id = c.id " +
                    "${ew.customSqlSegment} "
    })
    IPage<TspVehiclePageListDTO> getPageList(Page<TspVehiclePageListDTO> page, @Param(Constants.WRAPPER) QueryWrapper<TspVehicle> ew);

    @Select({"SELECT COUNT(t.vin)  FROM tsp_vehicle t LEFT JOIN tsp_equipment a ON t.tsp_equipment_id = a.id ${ew.customSqlSegment} "})
    Integer countByIsOnline(@Param(Constants.WRAPPER) QueryWrapper<TspVehicle> ew);

    @Select({
            //            "SELECT std_mode_name, CONVERT(create_time,date) as date, count(create_time) as count " +
            //                    "FROM " +
            //                    "( SELECT t.*,a.std_mode_name " +
            //                    " FROM tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id " +
            //                    " WHERE t.create_time <= #{endTime} AND t.create_time >= #{startTime} AND t.is_delete = 0 AND t.state = 3 ) as cr " +
            //                    " GROUP BY date ORDER BY create_time ASC"
            "SELECT CONCAT('{',`name`,',','\"date\":','\"',date,'\"',',','\"count\":','\"',count,'\"','}') AS str " +
                    " FROM (SELECT CONCAT(GROUP_CONCAT( '\"',std_mode_name, '\":\"', count,'\"' )) AS name,date,SUM( count ) AS count " +
                    "                    FROM (SELECT  DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date, COUNT(*)AS count,  a.std_mode_name FROM " +
                    "                     tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id " +
                    "                     WHERE t.create_time <= #{endTime} AND t.create_time >= #{startTime} GROUP BY date,a.std_mode_name ORDER BY t.update_time ASC) AS cr " +
                    "                     GROUP BY date,count) AS rs"
    })
    List<TspVehicleVolumeDataDTO> dataStartTimeAndEndTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    @Select({
            "SELECT CONCAT('{',`name`,',','\"date\":','\"',date,'\"',',','\"count\":','\"',count,'\"','}') AS str " +
                    " FROM (SELECT CONCAT(GROUP_CONCAT( '\"',std_mode_name, '\":\"', count,'\"' )) AS name,date,SUM( count ) AS count " +
                    "                    FROM (SELECT DATE_FORMAT( t.create_time, '%Y-%m-%d' ) as date,a.std_mode_name,count(t.tsp_vehicle_std_model_id) as count FROM tsp_vehicle t " +
                    "                    LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id " +
                    "                    GROUP BY date,a.std_mode_name ORDER BY t.update_time ASC) AS cr " +
                    "                    GROUP BY date) AS rs"
    })
    List<TspVehicleVolumeDataDTO> findVolumeData();

    @Select({
            "SELECT CONCAT('{',`name`,',','\"date\":','\"',date,'\"',',','\"count\":','\"',count,'\"','}') AS str " +
                    " FROM (SELECT CONCAT(GROUP_CONCAT( '\"',std_mode_name, '\":\"', count,'\"' )) AS name,date,SUM( count ) AS count " +
                    "                    FROM (SELECT  DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date, COUNT(*)AS count,  a.std_mode_name FROM " +
                    "                     tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id " +
                    "                     WHERE DATE_SUB( CURDATE( ), INTERVAL #{day} DAY ) <= DATE( t.create_time) GROUP BY date,a.std_mode_name ORDER BY t.update_time ASC) AS cr " +
                    "                     GROUP BY date) AS rs"
    })
    List<TspVehicleVolumeDataDTO> findVolumeDataDays(@Param("day") int day);

    @Select({
            //            "SELECT std_mode_name,DATE_FORMAT( create_time, '%Y-%m-%d' ) date,count(*) count " +
            //                    "FROM ( SELECT t.*,a.std_mode_name " +
            //                    " FROM tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id " +
            //                    " WHERE DATE_SUB( CURDATE( ), INTERVAL #{month} MONTH ) <= DATE( t.create_time) AND t.is_delete = 0 AND t.state = 3 ) as cr " +
            //                    " GROUP BY date ORDER BY update_time ASC"
            "SELECT CONCAT('{',`name`,',','\"date\":','\"',date,'\"',',','\"count\":','\"',count,'\"','}') AS str " +
                    " FROM (SELECT CONCAT(GROUP_CONCAT( '\"',std_mode_name, '\":\"', count,'\"' )) AS name,date,SUM( count ) AS count " +
                    "                    FROM (SELECT  DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date, COUNT(*)AS count,  a.std_mode_name FROM " +
                    "                     tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id " +
                    "                     WHERE DATE_SUB( CURDATE( ), INTERVAL #{month} MONTH ) <= DATE( t.create_time) GROUP BY date,a.std_mode_name ORDER BY t.update_time ASC) AS cr " +
                    "                     GROUP BY date) AS rs"
    })
    List<TspVehicleVolumeDataDTO> findVolumeDataMonth(@Param("month") int month);

    @Select({
            //            "SELECT std_mode_name,DATE_FORMAT( create_time, '%Y-%m-%d' ) date,count(*) count " +
            //                    "FROM ( SELECT t.*,a.std_mode_name " +
            //                    " FROM tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id " +
            //                    " WHERE DATE_SUB( CURDATE( ), INTERVAL #{year} YEAR ) <= DATE( t.create_time) AND t.is_delete = 0 AND t.state = 3 ) as cr " +
            //                    " GROUP BY date ORDER BY update_time ASC"
            "SELECT CONCAT('{',`name`,',','\"date\":','\"',date,'\"',',','\"count\":','\"',count,'\"','}') AS str " +
                    " FROM (SELECT CONCAT(GROUP_CONCAT( '\"',std_mode_name, '\":\"', count,'\"' )) AS name,date,SUM( count ) AS count " +
                    "                    FROM (SELECT  DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date, COUNT(*)AS count,  a.std_mode_name FROM " +
                    "                     tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id " +
                    "                     WHERE DATE_SUB( CURDATE( ), INTERVAL #{year} YEAR ) <= DATE( t.create_time) GROUP BY date,a.std_mode_name ORDER BY t.update_time ASC) AS cr " +
                    "                     GROUP BY date) AS rs"
    })
    List<TspVehicleVolumeDataDTO> findVolumeYear(@Param("year") int year);

    @Select(value = {
            "SELECT " +
                    "t.vin,c.sn,d.plate_code " +
                    "FROM " +
                    "tsp_vehicle t " +
//                    "LEFT JOIN tsp_user b ON t.tsp_user_id = b.id " +
                    "LEFT JOIN tsp_equipment c ON t.tsp_equipment_id = c.id " +
                    "LEFT JOIN tsp_vehicle_license d ON t.id = d.tsp_vehicle_id ${ew.customSqlSegment}"
    })
    List<VehicleGpsSelectListDTO> findSelectList(@Param(Constants.WRAPPER) Wrapper<TspVehicle> ew);

    @Select("SELECT id FROM tsp_vehicle where tsp_equipment_id = #{tspEquipmentId} limit 1")
    Long getByEquipmentId(Long tspEquipmentId);

    @Select("SELECT tsp_equipment_id FROM tsp_vehicle where id = #{id} limit 1")
    Long getByVehicleId(Long id);

    @Update("update tsp_vehicle set state = #{state} where id = #{tspVehicleId}")
    int updateSetState(@Param("state") Integer state, @Param("tspVehicleId") Long tspVehicleId);

    @Update("update tsp_vehicle set tsp_equipment_id = null where id = #{tspVehicleId}")
    int updateSetNull(Long tspVehicleId);

    @Select({
            "SELECT DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date,IFNULL( COUNT( t.id ), 0 ) AS sale " +
                    " FROM vehicle_integrate t " +
                    " LEFT JOIN tsp_vehicle a ON t.vin = a.vin " +
                    " LEFT JOIN tsp_equipment b ON a.tsp_equipment_id = b.id " +
                    " LEFT JOIN tsp_vehicle_license c ON a.id = c.tsp_vehicle_id ${ew.customSqlSegment} "
    })
    List<TspVehicleActivityDataDTO> findActivityDataVehicle(Wrapper<VehicleIntegrate> ew);

    @Select({
            "SELECT DATE_FORMAT( create_time, '%Y-%m-%d' ) AS date FROM vehicle_integrate GROUP BY DATE_FORMAT( create_time, '%Y-%m-%d' )"
    })
    List<String> getAllDate();

    @Select({
            "SELECT DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date " +
                    " FROM vehicle_integrate t " +
                    " LEFT JOIN tsp_vehicle a ON t.vin = a.vin " +
                    " LEFT JOIN tsp_equipment b ON a.tsp_equipment_id = b.id " +
                    " LEFT JOIN tsp_vehicle_license c ON a.id = c.tsp_vehicle_id ${ew.customSqlSegment} "

    })
    List<String> getAllDateSearch(@Param(Constants.WRAPPER) Wrapper<VehicleIntegrate> ew);

    @Select({
            "SELECT t.vin FROM vehicle_integrate t " +
                    "LEFT JOIN tsp_vehicle a ON t.vin = a.vin ${ew.customSqlSegment}"
    })
    List<String> getAllVin(@Param(Constants.WRAPPER) Wrapper<VehicleIntegrate> ew);

    @Select({
            "SELECT t.mileage from vehicle_integrate t " +
                    "LEFT JOIN tsp_vehicle a ON a.vin = t.vin " +
                    "LEFT JOIN tsp_vehicle_license b ON a.id = b.tsp_vehicle_id " +
                    "LEFT JOIN tsp_equipment c ON c.id = a.tsp_equipment_id ${ew.customSqlSegment}"
    })
    Integer findEachMileage(@Param(Constants.WRAPPER) Wrapper<VehicleIntegrate> ew);

    @Select({
            "select count(vin) from tsp_vehicle ${ew.customSqlSegment}"
    })
    Integer getVehicleNumberByDate(@Param(Constants.WRAPPER) Wrapper<TspVehicle> ew);

    @Select({
            "SELECT DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date FROM vehicle_integrate t " +
                    "LEFT JOIN tsp_vehicle a ON a.vin = t.vin " +
                    "LEFT JOIN tsp_vehicle_license b ON a.id = b.tsp_vehicle_id " +
                    "LEFT JOIN tsp_equipment c ON c.id = a.tsp_equipment_id " +
                    "WHERE t.vin LIKE #{search} OR c.sn LIKE #{search} OR b.plate_code LIKE #{search} " +
                    "GROUP BY DATE_FORMAT( create_time, '%Y-%m-%d' )"
    })
    List<String> getVinDate(String search);

    @Select({
            "SELECT t.vin as 'vin',t.provider_name as 'providerName',t.configure_name as 'configureName',t.color as 'color'," +
                    "t.batch_no as 'batchNo',t.purpose as 'purpose',DATE_FORMAT( t.ex_factory_date, '%Y-%m-%d' ) AS 'exFactoryDate'," +
                    "DATE_FORMAT( t.operate_date, '%Y-%m-%d' ) AS 'operateDate',t.label as 'label',t.remark as 'remark'," +
                    "f.sn as 'sn',c.name as 'name',d.model_name as 'modelName',g.vehicle_model_name as 'vehicleModelName'," +
                    "a.std_mode_name as 'stdModelName',t.engine_num as 'engineNum',t.motor_num as 'motorNum',t.cdu_num as 'cduNum'," +
                    "t.motor_brand as 'motorBrand',t.ess_num as 'essNum',t.ess_model as 'essModel' " +
                    "FROM tsp_vehicle t " +
                    "LEFT JOIN tsp_vehicle_std_model a " +
                    "ON a.id = t.tsp_vehicle_std_model_id " +
                    "LEFT JOIN tsp_user b " +
                    "ON b.id = t.tsp_user_id " +
                    "LEFT JOIN tsp_vehicle_model g " +
                    "ON g.id = a.tsp_vehicle_model_id " +
                    "LEFT JOIN tsp_equipment f ON f.id = t.tsp_equipment_id " +
                    "LEFT JOIN tsp_equipment_model d ON d.id = f.tsp_equipment_model_id " +
                    "LEFT JOIN tsp_vehicle_license e ON e.tsp_vehicle_id = t.id " +
                    "LEFT JOIN tsp_equipment_type c ON c.id = d.tsp_equipment_type_id ${ew.customSqlSegment}"
    })
    List<TspVehicleExFactoryTemplateDTO> getExFactoryList(Page<Object> of, @Param(Constants.WRAPPER) QueryWrapper<TspVehicle> ew);

    @Select({
            "SELECT t.vin as 'vin',t.purpose as 'purpose',x.purchaser_state as 'purchaserState',x.purchaser as 'purchaser',x.vehicle_id_card as 'vehicleIdCard'," +
                    "x.price_tax as 'priceTax',x.invoice_no as 'invoiceNo',DATE_FORMAT( x.invoicing_date, '%Y-%m-%d' ) AS 'invoicingDate'," +
                    "x.is_san_bao as 'isSanBao',x.sales_unit_name as 'salesUnitName',x.sales_unit_address 'salesUnitAddress'," +
                    "y.vehicle_status as 'vehicleStatus',y.sales_channel as 'salesChannel',y.channel_type as 'channelType'," +
                    "y.employee_name as 'employeeName',y.new_vehicle_flag as 'newVehicleFlag' " +
                    "FROM tsp_vehicle t " +
                    "RIGHT JOIN tsp_vehicle_market x ON x.tsp_vehicle_id = t.id " +
                    "LEFT JOIN tsp_vehicle_std_model a " +
                    "ON a.id = t.tsp_vehicle_std_model_id " +
                    "LEFT JOIN tsp_user b " +
                    "ON b.id = t.tsp_user_id " +
                    "LEFT JOIN tsp_equipment f ON f.id = t.tsp_equipment_id " +
                    "LEFT JOIN tsp_equipment_model d ON d.id = f.tsp_equipment_model_id " +
                    "LEFT JOIN tsp_vehicle_license e ON e.tsp_vehicle_id = t.id " +
                    "LEFT JOIN tsp_vehicle_other y ON t.id = y.tsp_vehicle_id ${ew.customSqlSegment}"
    })
    List<TspVehicleSaleTemplateDTO> getSalesList(Page<Object> of, @Param(Constants.WRAPPER) QueryWrapper<TspVehicle> ew);

    @Select({
            "SELECT COUNT(t.id) FROM tsp_vehicle t " +
                    "LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id " +
                    "LEFT JOIN tsp_vehicle_model g ON g.id = a.tsp_vehicle_model_id " +
                    "LEFT JOIN tsp_user b on t.tsp_user_id = b.id " +
                    "LEFT JOIN tsp_equipment c on t.tsp_equipment_id = c.id " +
                    "LEFT JOIN tsp_vehicle_license e on e.tsp_vehicle_id = t.id " +
                    "LEFT JOIN tsp_equipment f on f.id = t.tsp_equipment_id " +
                    "LEFT JOIN tsp_vehicle_audit d on t.id = d.tsp_vehicle_id ${ew.customSqlSegment}"
    })
    Integer getCount(@Param(Constants.WRAPPER) QueryWrapper<TspVehicle> ew);


    // 流量统计
    @Select({
            "SELECT count(t.id) FROM tsp_vehicle t " +
                    "LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id " +
                    "LEFT JOIN tsp_vehicle_model b ON b.id = a.tsp_vehicle_model_id " +
                    "LEFT JOIN tsp_user c on t.tsp_user_id = c.id " +
                    "LEFT JOIN tsp_vehicle_license d on d.tsp_vehicle_id = t.id "

    })


    //t.vin,c.real_name,mobile,d.plate_code,a.std_mode_name
    Integer getFlowDataCount(@Param(Constants.WRAPPER) QueryWrapper<TspVehicle> ew);



    //流量分页
    @Select({
            "SELECT t.vin,c.real_name,c.mobile,d.plate_code,a.std_mode_name FROM tsp_vehicle t " +
                    "LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id " +
                    "LEFT JOIN tsp_vehicle_model b ON b.id = a.tsp_vehicle_model_id " +
                    "LEFT JOIN tsp_user c on t.tsp_user_id = c.id " +
                    "LEFT JOIN tsp_vehicle_license d on d.tsp_vehicle_id = t.id ${ew.customSqlSegment} "

    })
    IPage<TspVehicleFlowDataDTO> getPageListFlowData(Page<TspVehicleFlowDataDTO> page, @Param(Constants.WRAPPER) QueryWrapper<TspVehicle> ew);

    @Select({
            "SELECT t.vin AS 'vin',a.plate_code AS 'plateCode',b.sim AS 'sim'," +
                    "(CASE b.operator " +
                    "WHEN 1 THEN '移动'" +
                    "WHEN 2 THEN '联通'" +
                    "WHEN 3 THEN '电信' ELSE '未知' END) AS 'operator'," +
                    "b.sn AS 'sn',b.icc_id AS 'iccid',b.imei AS 'imei' FROM tsp_vehicle t " +
                    "LEFT JOIN tsp_vehicle_license a ON a.tsp_vehicle_id = t.id " +
                    "LEFT JOIN tsp_equipment b ON t.tsp_equipment_id = b.id " +
                    "WHERE t.id = #{tspVehicleId}"
    })
    Map<String, Object> getRealNameMessage(Long tspVehicleId);

    @Select({
            "SELECT mobile as 'mobile',real_name as 'realName',id_card as 'idCard' FROM tsp_use_vehicle_record WHERE tsp_vehicle_id = #{tspVehicleId}"
    })
    List<Map<String, Object>> getBind(Long tspVehicleId);

    List<String> selectVin(@Param("vo") TspVehicleOnlineDataVO vo);

    List<String> selectVinByDealerId(@Param("dealerId") Long dealerId);

    List<String> selectVinByAddress(@Param("vo") AddressVo addressVo);

    Long countAllCar(@Param("vo") TspVehicleOnlineDataVO vo);

    List<String> selectVinByDealerIds(@Param("dealerIds")Set<Long> dealerIds);

    List<VolumeDTO> volumeChartData(@Param("vo") TspVehicleAlertDataVO vo);

}
