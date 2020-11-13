package com.inet;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import com.inet.code.entity.Message;
import com.inet.code.mapper.MessageMapper;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
class INetApplicationTests {

   /* @Test
     void test(){
        System.out.println(TIMEONE(new Date()));
    }

    private Boolean TIMEONE(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int getDay = calendar.get(Calendar.DAY_OF_MONTH);
        if(getDay == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
            return true;
        }else{
            return false;
        }
    }*/


}
