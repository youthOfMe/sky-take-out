package com.sky.mapper;
import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBookMapper {
    /**
     * 新增地址数据
     * @param addressBook
     */
    @Insert("insert into address_book" +
            "(user_id, consignee, sex, phone, province_code, province_name, city_code, " +
            "city_name, district_code, district_name, detail, label, is_default) " +
            "values (#{user_id}, #{consignee}, #{sex}, #{phone}, #{province_code}, #{province_name}, #{city_code}," +
            "#{city_name}, #{district_code}, #{district_name}, #{detail}, #{label}, #{is_default})")
    void insert(AddressBook addressBook);
}
