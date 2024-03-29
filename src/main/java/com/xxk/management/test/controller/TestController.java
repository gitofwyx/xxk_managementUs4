package com.xxk.management.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.PrivacyUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.WeChat.access_token.entity.AccessToken;
import com.xxk.management.WeChat.access_token.service.Access_tokenService;
import com.xxk.management.WeChat.jsapi_ticket.entity.Jsapi_ticket;
import com.xxk.management.WeChat.jsapi_ticket.service.Jsapi_ticketService;
import com.xxk.management.roles.entity.Roles;
import com.xxk.management.roles.service.RolesService;
import com.xxk.management.test.service.TestService;
import com.xxk.management.user.entity.RegUser;
import com.xxk.management.user.service.RebUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
import java.io.InputStream;
import java.util.*;
import java.util.function.Supplier;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
public class TestController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private TestService testService;

    @Autowired
    private RebUserService rebUserService;

    @Autowired
    private Access_tokenService access_tokenService;

    @Autowired
    private Jsapi_ticketService jsapi_ticketService;

    private Supplier<RegUser> regUserSupplier = RegUser::new;


    @ResponseBody
    @RequestMapping(value = "/t1", method = RequestMethod.GET)
    public Object t1() {
        Map<String, Object> result = new HashMap<>();
        String v1 = null;
        try {
            v1 = testService.test1("");
            log.warn(v1);
        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "获取数据出错");
        }
        return v1;
    }

    @ResponseBody
    @RequestMapping(value = "/t2", method = RequestMethod.GET)
    public Object t2(@RequestParam(value = "sleep") String sleep) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (sleep.equals("on")) {
                Thread.currentThread().sleep(10000);
            }
        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "获取数据出错");
        }
        return sleep;
    }

    @ResponseBody
    @RequestMapping(value = "/addRegUserTest", method = RequestMethod.POST)
    public Map<String, Object> addRegUserTest(RegUser user) {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        try {
            user.setId(id);
            //user.setId("fa97900c-919c-4666-aab4-d73c1e5144fb");
            user.setPassword("123");
            user.setCreateDate(createDate);
            user.setCreateUserId(id);
            user.setUpdateDate(createDate);
            user.setUpdateUserId(id);
            user.setDeleteFlag("0");

            boolean Result = testService.addRegUserTest(user);
            if (!(Result)) {
                result.put("hasError", true);
                result.put("error", "更新出错！");
            } else {
                result.put("success", true);
            }
        } catch (Exception e) {
            result.put("hasError", true);
            result.put("error", "更新出错！");
            log.warn(e);
        }
        return result;//结束！！！
        //return "system/index";
    }

    @ResponseBody
    @RequestMapping("/updateRegUserTest")
    public Map<String, Boolean> updateRegUserTest(RegUser user) {
        Map<String, Boolean> result = new HashMap<>();
        //String phone=(String)request.getAttribute("phone");
        //String phone = request.getParameter("phone");
        String updateDate = DateUtil.getFullTime();
        try {
            user.setUpdateUserId("admin");
            user.setUpdateDate(updateDate);
            if (testService.updateRegUserTest(user) == false) {
                log.error("更新出错");
                result.put("success", true);
                return result;
            }
            result.put("success", true);
        } catch (Exception e) {
            log.error(e);
            result.put("error", false);
        } finally {
            return result;
        }
    }

    @ResponseBody
    @RequestMapping("/delRegUserTest")
    public Map<String, Boolean> delRegUserTest(@RequestParam("ids[]") List<String> ids) {
        Map<String, Boolean> result = new HashMap<>();
        try {
            if (testService.deleteListRegUserTest(ids) == false) {
                log.error("删除出错");
                result.put("error", false);
                return result;
            }
            result.put("success", true);
        } catch (Exception e) {
            log.error(e);
            result.put("error", false);
        } finally {
            return result;
        }
    }

    /**
     * 上传excel文件
     *
     * @param excelFile
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/saveExcel", produces = {"application/json;charset=UTF-8"}, method = {
            RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> readXls(@RequestParam MultipartFile[] excelFile) throws IOException {
        Map<String, Object> result = new HashMap<>();
        long startTime = System.currentTimeMillis();
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
                        //list = readXLS(myfile);
                    } else if (originalFilename.endsWith(".xlsx")) {
                        //list = readXLSX(myfile);
                    }
                    /*批量插入数据*/
                    if (!CollectionUtils.isEmpty(list)) {
                      /*  boolean ar=rebUserService.addListRegUser(list);
                        if (!ar) {
                            result.put("hasError", true);
                            result.put("error", "导入为空，检查导入格式。");
                            return result;
                        }*/
                    } else {
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
        long hs = System.currentTimeMillis() - startTime;
        result.put("hasError", true);
        result.put("error", "导入数据总耗时：" + hs + "毫秒");
        return result;
    }

    /**
     * 解析.xls格式的excel文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    public List<RegUser> readXLS(MultipartFile file) throws IOException {
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
                RegUser regUser = regUserSupplier.get();
                HSSFCell txt = hssfRow.getCell(0);
                if (txt == null) {
                    errorNum += 1;
                    errorMsg += "第" + (rowNum + 1) + "行（数据）格式错误,不能为空&";
                    continue;
                }
                txt.setCellType(CellType.STRING);
                if ("工号".equals(txt.getStringCellValue()) || StringUtils.isBlank(txt.getStringCellValue())) {
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

        if (is != null) {
            is.close();
        }
        return list;
    }

    @ResponseBody
    @RequestMapping("/chatBotSend")
    public String chatBotSend() {
        String WEBHOOK_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=48ff7828-9fe4-4115-86a8-a2db2d9bc91a";
        Map<String, Object> msgMap = new HashMap<>();
        Map<String, Object> textMap = new HashMap<>();
        List<String> mentioned_mobile_list=new ArrayList<String>();

        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            //构建一个json格式字符串textMsg，其内容是接收方需要的参数和消息内容
            textMap.put("content","你好，我是你贤哥啊");
            textMap.put("mentioned_mobile_list",mentioned_mobile_list);
            mentioned_mobile_list.add("@all");
            msgMap.put("msgtype","markdown");
            msgMap.put("text",textMap);

            JSONObject msgJson = new JSONObject(msgMap);
            //String textMsg = "{\"msgtype\":\"text\",\"text\":{\"content\":\"你好，我是你贤哥啊\"},\"at\":{\"atMobiles\":[\"xxx\"],\"isAtAll\":false}}";
            StringEntity se = new StringEntity(msgJson.toJSONString(), "utf-8");
            httppost.setEntity(se);
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(result);
            }
        } catch (Exception e) {
            return "false";
        }
        return "我是你贤哥啊";
    }

    @ResponseBody
    @RequestMapping(value = "/jacobTest",method = RequestMethod.GET)
    public String jacobTest (@RequestParam(value = "sKr") String sKr) {
        ActiveXComponent ax = null;
       if("".equals(sKr)||sKr==null){
           sKr="输入不正确";
       }
        try { ax = new ActiveXComponent("Sapi.SpVoice");

            //运行时输出语音内容
            Dispatch spVoice = ax.getObject();
            // 音量 0-100
            ax.setProperty("Volume", new Variant(100));
            // 语音朗读速度 -10 到 +10
            ax.setProperty("Rate", new Variant(-4));
            // 执行朗读
            Dispatch.call(spVoice, "Speak", new Variant(sKr));

            //下面是构建文件流把生成语音文件

            /*ax = new ActiveXComponent("Sapi.SpFileStream");
            Dispatch spFileStream = ax.getObject();

            ax = new ActiveXComponent("Sapi.SpAudioFormat");
            Dispatch spAudioFormat = ax.getObject();

            //设置音频流格式
            Dispatch.put(spAudioFormat, "Type", new Variant(22));
            //设置文件输出流格式
            Dispatch.putRef(spFileStream, "Format", spAudioFormat);
            //调用输出 文件流打开方法，创建一个.wav文件
            Dispatch.call(spFileStream, "Open", new Variant("E:\\test.wav"), new Variant(3), new Variant(true));
            //设置声音对象的音频输出流为输出文件对象
            Dispatch.putRef(spVoice, "AudioOutputStream", spFileStream);
            //设置音量 0到100
            Dispatch.put(spVoice, "Volume", new Variant(100));
            //设置朗读速度
            Dispatch.put(spVoice, "Rate", new Variant(-2));
            //开始朗读
            Dispatch.call(spVoice, "Speak", new Variant(str));

            //关闭输出文件
            Dispatch.call(spFileStream, "Close");
            Dispatch.putRef(spVoice, "AudioOutputStream", null);

            spAudioFormat.safeRelease();
            spFileStream.safeRelease();*/
            spVoice.safeRelease();
            ax.safeRelease();

        } catch (Exception e) { e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/testWeChatToKen")
    public Jsapi_ticket testWeChatToKen() {

        Jsapi_ticket jsapi_ticket=null;
        try {

             jsapi_ticket=jsapi_ticketService.makeJsapi_ticket();
        } catch (Exception e) {
            return null;
        }
        return jsapi_ticket;
    }


}
