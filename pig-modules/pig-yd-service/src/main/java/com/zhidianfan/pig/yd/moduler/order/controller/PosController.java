//package com.zhidianfan.pig.yd.moduler.order.controller;
//
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
//import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
//import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
//import com.zhidianfan.pig.yd.moduler.order.bo.PosPcCodeBO;
//import com.zhidianfan.pig.yd.moduler.order.bo.PosTagBo;
//import com.zhidianfan.pig.yd.moduler.order.bo.TableStatusBO;
//import com.zhidianfan.pig.yd.moduler.order.bo.TablesBO;
//import com.zhidianfan.pig.yd.moduler.order.dto.OrderDTO;
//import com.zhidianfan.pig.yd.moduler.order.service.XopService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.Map;
//
//
///**
// * 西软对外对接接口
// *
// * @author danda
// */
//@RestController
//@RequestMapping("/xop")
//public class PosController {
//	/**
//	 * 西软业务逻辑处理类
//	 */
//	@Autowired
//	private XopService xopService;
//
//	/**
//	 * 获取营业点信息
//	 *
//	 * @param businessId 酒店id
//	 * @return 营业点信息
//	 */
//	@GetMapping(value = "/pccodes",params = "businessId")
//	public ResponseEntity pccodes(@RequestParam Integer businessId) {
////		if (businessId == null) {
////			return ResponseEntity.badRequest().body(new ErrorTip(400, "酒店id不能为空"));
////		}
//		PosPcCodeBO posPcCodeBO = xopService.posPcCodes(businessId);
//		if(posPcCodeBO == null || !posPcCodeBO.isSuccess()){
//			Map<String,Object> result = Maps.newHashMap();
//			result.put("code",400);
//			result.put("msg","营业点酒店不存在");
//			return ResponseEntity.badRequest().body(result);
//		}
//		return ResponseEntity.ok(posPcCodeBO);
//	}
//
//	/**
//	 * 获取营业点下面所有桌位
//	 *
//	 * @param businessId 酒店id
//	 * @param pcCode     酒店营业点
//	 * @return 酒店营业点下所有酒店
//	 */
//	@GetMapping("/tables")
//	public ResponseEntity tables(Integer businessId,String pcCode) {
//		List<String> errors = Lists.newArrayList();
//		if(businessId == null){
//			errors.add("酒店id不能为空");
//		}
//		if(pcCode == null){
//			errors.add("酒店营业点不能为空");
//		}
//		if (errors.size() != 0) {
//			Map<String,Object> result = Maps.newHashMap();
//			result.put("code",400);
//			result.put("msg",errors);
//			return ResponseEntity.badRequest().body(result);
//		}
//		TablesBO tablesBO = xopService.tables(businessId, pcCode);
//		if(tablesBO == null || !tablesBO.isSuccess()){
//			Map<String,Object> result = Maps.newHashMap();
//			result.put("code",400);
//			result.put("msg","桌位信息不存在");
//			return ResponseEntity.badRequest().body(result);
//		}
//		return ResponseEntity.ok(tablesBO);
//	}
//
//	/**
//	 * 获取桌位状态
//	 *
//	 * @param businessId 酒店id
//	 * @return 酒店桌位状态
//	 */
//	@GetMapping("/table/status")
//	public ResponseEntity tableStatus(Integer businessId) {
//		if (businessId == null) {
//			return ResponseEntity.badRequest().body(new ErrorTip(400, "酒店id不能为空"));
//		}
//		TableStatusBO tableStatusBO = xopService.tableStatus(businessId);
//		if(tableStatusBO == null || !tableStatusBO.isSuccess()){
//			Map<String,Object> result = Maps.newHashMap();
//			result.put("code",400);
//			result.put("msg","未查询到桌位信息");
//			return ResponseEntity.badRequest().body(result);
//		}
//		return ResponseEntity.ok(tableStatusBO);
//	}
//
//	/**
//	 * 酒店订餐列表
//	 *
//	 * @param businessId 酒店id
//	 * @return 酒店所有订餐列表
//	 */
//	@GetMapping("/pos/tag")
//	public ResponseEntity posTag(Integer businessId) {
//		if (businessId == null) {
//			return ResponseEntity.badRequest().body(new ErrorTip(400, "酒店id不能为空"));
//		}
//		PosTagBo posTagBo = xopService.posTag(businessId);
//		if(posTagBo == null || !posTagBo.isSuccess()){
//			Map<String,Object> result = Maps.newHashMap();
//			result.put("code",400);
//			result.put("msg","未查询到餐次类别");
//			return ResponseEntity.badRequest().body(result);
//		}
//		return ResponseEntity.ok(posTagBo);
//	}
//
//	/**
//	 * 同步西软订单信息
//	 *
//	 * @param orderDTO 需要同步的订单数据
//	 */
//	@PostMapping("/order")
//	public ResponseEntity updateOrder(@Valid OrderDTO orderDTO, BindingResult bindingResult) {
//		if (bindingResult.hasErrors()) {
//			List<String> errors = Lists.newArrayList();
//			bindingResult.getAllErrors().forEach(objectError -> {
//				errors.add(objectError.getDefaultMessage());
//			});
//			Map<String,Object> result = Maps.newHashMap();
//			result.put("code",400);
//			result.put("msg",errors);
//			return ResponseEntity.badRequest().body(result);
//		}
//		boolean b = xopService.asyncOrder(orderDTO);
//		Tip tip = b ? new SuccessTip() : new ErrorTip();
//		return ResponseEntity.ok(tip);
//	}
//
//	/**
//	 * 西软取消订单同步
//	 *
//	 * @param businessId   酒店id
//	 * @param thirdOrderNo 西软的第三方订单编号
//	 * @return 取消订单的状态
//	 */
//	@GetMapping("/order/cancel")
//	public ResponseEntity cancelOrder(Integer businessId,String thirdOrderNo) {
//		List<String> errors = Lists.newArrayList();
//		if(businessId == null){
//			errors.add("酒店id不能为空");
//		}
//		if(thirdOrderNo == null){
//			errors.add("第三方订单号不能为空");
//		}
//		if (errors.size() != 0) {
//			Map<String,Object> result = Maps.newHashMap();
//			result.put("code",400);
//			result.put("msg",errors);
//			return ResponseEntity.badRequest().body(result);
//		}
//		boolean b = xopService.asyncCancelOrder(businessId, thirdOrderNo);
//		Tip tip = b ? new SuccessTip() : new ErrorTip();
//		return ResponseEntity.ok(tip);
//	}
//}
