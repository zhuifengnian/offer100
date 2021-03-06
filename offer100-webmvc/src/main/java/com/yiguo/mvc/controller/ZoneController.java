package com.yiguo.mvc.controller;

import com.yiguo.bean.Page;
import com.yiguo.bean.Zone;
import com.yiguo.offer100.common.page.PageInfo;
import com.yiguo.service.ZoneService;
import com.yiguo.service.ZoneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.dubbo.monitor.MonitorService.FAILURE;
import static com.alibaba.dubbo.monitor.MonitorService.SUCCESS;

@Controller
@Api(value = "地区接口")
@RequestMapping("/zone")
public class ZoneController {
    @Autowired
    ZoneService zoneService;



    @ApiOperation(value = "创建地区",notes = "根据Zone对象创建Zone")
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postZone(@RequestBody Zone zone) {
        // 处理"/Zones/"的POST请求，用来创建Zone
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        String f="false";
        Integer count=  zoneService.selectByName(zone.getName());
        if(count==0) {
            zoneService.insert(zone);
            f="true";
        }
        return f;
    }

    @ApiOperation(value="查询地区信息", notes="根据url的id来获取地区详细信息")
    @ResponseBody
    @RequestMapping(value = "/{id}", method ={RequestMethod.GET})
    public Zone getZone(@PathVariable Integer id ) {
        // 处理"/Zones/{id}"的GET请求，用来获取url中id值的Zone信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        return zoneService.selectByPrimaryKey(id);
    }

    @ApiOperation(value="更新地区信息", notes="根据url的id来指定更新对象，并根据传过来的Zone信息来更新地区详细信息")
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putZone(@PathVariable Integer id, @RequestBody Zone zone) {
        // 处理"/Zones/{id}"的PUT请求，用来更新Zone信息
        if (zoneService.findById(id) > 0) {
            zone.setId(id);
            int num = zoneService.updateByPrimaryKeySelective(zone);
            if (num > 0) {
                return SUCCESS;
            } else
                return FAILURE;
        }
        return "this id does not exist";
    }

    @ApiOperation(value="删除地区信息", notes="根据url的id来指定删除对象")
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteZone(@PathVariable Integer id) {
        // 处理"/Zones/{id}"的DELETE请求，用来删除Zone
        String f="删除成功";
        zoneService.deleteByPrimaryKey(id);
        if(zoneService.selectByPrimaryKey(id)!=null)
            f="删除失败";
        return f;
    }

    @ApiOperation(value="获取子地区信息", notes="根据父id来获取地区详细信息")
    @ResponseBody
    @RequestMapping(value = "getZoneByParentId/{id}", method ={RequestMethod.GET})
    public List<Zone> getZoneByParentId(@PathVariable Integer id) {
        // 处理"/Zones/{id}"的GET请求，用来获取url中id值的Zone信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        Zone zone=new Zone();
        zone.setParentId(id);
        Page page= new Page();
        page.setPageNumber(1);
        page.setPageSize(99);
        return (zoneService.select(zone,page));
    }

}
