package com.xxk.management.system.controller;

import com.xxk.core.file.BaseController;
import com.xxk.management.system.service.SystemService;
import com.xxk.management.test.service.TestService;
import com.xxk.management.user.entity.RegUser;
import com.xxk.management.user.service.RebUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("/sys")
public class SysImportController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private SystemService systemService;

    @Autowired
    private RebUserService rebUserService;

    /**
     * 上传excel文件
     * @param excelFile
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/saveExcel", produces = { "application/json;charset=UTF-8" }, method = {
            RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> readXls(@RequestParam MultipartFile[] excelFile) throws IOException {
        Map<String, Object> result = new HashMap<>();
        long startTime=System.currentTimeMillis();
        String originalFilename;
        int r = 0;
        if (excelFile == null || excelFile.length == 0) {
            result.put("hasError", true);
            result.put("error", "文件上传失败，请重试");
            return result;
        }
        try {
            for (MultipartFile myfile : excelFile) {
                if (myfile.isEmpty()) {
                    result.put("hasError", true);
                    result.put("error", "文件为空");
                    return result;
                } else {
                    originalFilename = myfile.getOriginalFilename();
                    if (!(originalFilename.endsWith(".xls") || originalFilename.endsWith(".xlsx"))) {
                        result.put("hasError", true);
                        result.put("error", "文件格式错误，请重试");
                        return result;
                    }

                    List<RegUser> list = null;
                    /*根据文件格式解析文件*/
                    if (originalFilename.endsWith(".xls")) {
                        list = systemService.readRegUserXLS(myfile);
                    } else if (originalFilename.endsWith(".xlsx")) {
                        //list = readXLSX(myfile);
                    }
                    /*批量插入数据*/
                    if(!CollectionUtils.isEmpty(list)){
                        boolean ar=rebUserService.addListRegUser(list);
                        if (!ar) {
                            result.put("hasError", true);
                            result.put("error", "导入为空，检查导入格式。");
                            return result;
                        }
                    }else {
                        result.put("hasError", true);
                        result.put("error", "获取到的列表为空，取消导入。");
                        return result;
                    }

                }
            }

        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "文件解析失败，请确认表中的必填项不为空！导入已中断。");
            return result;
        }
        long hs=System.currentTimeMillis()-startTime;
        result.put("hasError", true);
        result.put("error", "导入数据总耗时："+hs+"毫秒");
        return result;
    }

}
