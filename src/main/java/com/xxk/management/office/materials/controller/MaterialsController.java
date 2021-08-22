package com.xxk.management.office.materials.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.device.entity.DeviceClass;
import com.xxk.management.device.service.DeviceClassService;
import com.xxk.management.office.materials.entity.Materials;
import com.xxk.management.office.materials.service.MaterialsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("")
public class MaterialsController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private MaterialsService materialsService;
    @Autowired
    private DeviceClassService deviceClassService;




}
