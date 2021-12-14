package com.xxk.management.system.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.PrivacyUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.office.devices.service.StockDevicesService;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.stock.service.StockService;
import com.xxk.management.storage.dao.DeliveryDao;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;
import com.xxk.management.storage.service.DeliveryReportService;
import com.xxk.management.storage.service.DeliveryService;
import com.xxk.management.system.service.SystemService;
import com.xxk.management.user.entity.RegUser;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class SystemServiceImpl implements SystemService {


    private static Logger log = LogManager.getLogger();

    private Supplier<RegUser> regUserSupplier = RegUser::new;

    /**
     * 解析.xls格式的excel文件
     * @param file
     * @return
     * @throws IOException
     */
    public List<RegUser> readRegUserXLS(MultipartFile file) throws IOException {
        InputStream is = file.getInputStream();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        String updateDate = DateUtil.getFullTime();
        List<RegUser> list = new ArrayList<>();
        // 循环工作表Sheet
        // 循环行Row
        int errorNum = 0;
        int okNum = 0;
        String errorMsg = "";
        HSSFSheet hssfSheet = hssfWorkbook.getSheet("记录1");
        for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow != null) {
                RegUser regUser=regUserSupplier.get();
                HSSFCell txt = hssfRow.getCell(0);
                if (txt == null) {
                    errorNum += 1;
                    errorMsg += "第" + (rowNum + 1) + "行（数据）格式错误,不能为空&";
                    continue;
                }
                txt.setCellType(CellType.STRING);
                if("工号".equals(txt.getStringCellValue())|| StringUtils.isBlank(txt.getStringCellValue())){
                    errorNum += 1;
                    errorMsg += "第" + (rowNum + 1) + "行（数据）不是设定的导入字符(工号)";
                    continue;
                }
                errorMsg += "第" + (rowNum + 1) + "行（数据）导入开始 ";
                regUser.setId(UUIdUtil.getUUID());
                regUser.setAccount(hssfRow.getCell(0).getStringCellValue());
                regUser.setRealName(hssfRow.getCell(1).getStringCellValue());
                regUser.setPassword("123");
                regUser.setName(PrivacyUtil.replaceNameS(regUser.getRealName()));
                regUser.setCreateUserId("auto");
                regUser.setCreateDate(updateDate);
                regUser.setDeleteFlag("0");
                list.add(regUser);
                okNum += 1;
            }

        }
        String message = "导入结果:&成功导入" + okNum + "条数据 失败" + errorNum + "条&错误记录:&"
                + errorMsg;

        if(is!=null){
            is.close();
        }
        return list;
    }
}
