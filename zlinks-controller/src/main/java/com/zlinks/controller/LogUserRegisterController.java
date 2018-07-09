package com.zlinks.zlinks-controller/src/main/java/com/zlinks/controller/;
import com.zlinks.zlinks-repository/src/main/java/com/zlinks/domain/.LogUserRegister;
import com.zlinks.zlinks-service/src/main/java/com/zlinks/service/.LogUserRegisterService;
import com.zlinks.common.web.BaseController;
import com.zlinks.common.web.JsonResult;
import com.zlinks.common.web.PageResult;
import com.zlinks.common.web.RestDoing;
import com.zlinks.Routes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;
/**
 * Copyright (C), 2017-2020, BBG
 * FileName: LogUserRegisterController
 * Author:   zhangjh
 * Date:     2018-7-9 16:02:01
 * Description: 控制层
 */
@RestController
@RequestMapping(value = Routes.API_VERSION)
public class LogUserRegisterController extends BaseController {

	/**
	 * logger 日志
	 */
    private Logger logger = LoggerFactory.getLogger(this.getClass());


	@Autowired
	private LogUserRegisterService logUserRegisterService;


	/**
     * @api {post} /logUserRegisters/save 01. LogUserRegister新增
     * @apiPermission Login in Users
     * @apiGroup  LogUserRegister
     * @apiVersion 1.0.1
     * @apiParam {Number} userId <code>必须参数</code>用户id
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *     "code": 0,
     *     "data": 1
     *     "desc": "Success",
     *     "timestamp": "2018-7-9 16:02:01:082"
     * }
     * @apiErrorExample {json} Error-Response:
     * HTTP/1.1 200 OK
     * {
     *     "code": 110002,
     *     "desc": "Param is null or error",
     *     "timestamp": "2018-7-9 16:02:01:479"
     * }
     */
	@RequestMapping(value = "/logUserRegisters/save", method = RequestMethod.POST)
	public JsonResult add(@RequestBody LogUserRegister logUserRegister) {

  		RestDoing doing = jsonResult -> {

            int counts = logUserRegisterServiceImpl.add(logUserRegister);
            jsonResult.data = counts;
        };
        return doing.go(request, logger);
	}

	/**
	 * 删除，返回json
	 * 
	 * @param id 主键id
	 * @return JsonResult json对象
	 */
	@RequestMapping(value = "/logUserRegisters/delete/{id}", method = RequestMethod.DELETE)
	public JsonResult deleteJson(@PathVariable("id") int id) {
		RestDoing doing = jsonResult -> {

            int counts = logUserRegisterServiceImpl.delete(logUserRegister);
            jsonResult.data = counts;
        };
        return doing.go(request, logger);
	}


	/**
	 * 详情页
	 *
	 * @param id 主键id
	 * @return String 详情页url
	 */
	@RequestMapping(value = "/logUserRegisters/info/{id}")
	public String info(@PathVariable("id") Long id) {

		RestDoing doing = jsonResult -> {

			LogUserRegister entity  = logUserRegisterServiceImpl.queryInfoById(id);
            jsonResult.data = entity;
        };
        return doing.go(request, logger);
	}

	/**
	 * 列表页面
	 * 
	 * @param findContent 搜索内容
	 * @param pageNo 页数
	 * @return Pagination 集合列表
	 */
	@RequestMapping(value = "list")
	public String list(String findContent, ModelMap modelMap, Integer pageNo) {
		try {
			Pagination<LogUserRegister> data = logUserRegisterService.findPage(modelMap, pageNo, pageSize);
			modelMap.addAttribute("data", data);
		} catch (Exception e) {
			logger.error(HPXSConstants.ERROR_STRING, e);
			redirect404();
		}
		return BASE_PATH + "list";
	}

	/**
	 * 列表数据
	 * 
	 * @param findContent 搜索内容
	 * @param pageNo 页数
	 * @return Pagination 集合列表
	 */
	@RequestMapping(value = "jsonList")
	@ResponseBody
	public JsonResult<Pagination<LogUserRegister>> jsonList(String findContent, ModelMap modelMap, Integer pageNo) {
		JsonResult<Pagination<LogUserRegister>> result = new JsonResult<Pagination<LogUserRegister>>();
		try {
			Pagination<LogUserRegister> data = logUserRegisterService.findPage(modelMap, pageNo, pageSize);
			result.setData(data);
		} catch (Exception e) {
			logger.error(HPXSConstants.ERROR_STRING, e);
			result.setCode(HPXSConstants.STATUS_ERROR);
			result.setMessage(e.getMessage());
			result.setSuccess(Boolean.FALSE);
		}
		return result;
	}

}