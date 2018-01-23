package com.citi.alan.myproject.tess4j.service.impl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citi.alan.myproject.tess4j.dao.OrderDetailDao;
import com.citi.alan.myproject.tess4j.dao.UserInfoDao;
import com.citi.alan.myproject.tess4j.dict.Dictionary;
import com.citi.alan.myproject.tess4j.entity.Merchant;
import com.citi.alan.myproject.tess4j.entity.OrderDetail;
import com.citi.alan.myproject.tess4j.entity.UserInfo;
import com.citi.alan.myproject.tess4j.enu.ActivityType;
import com.citi.alan.myproject.tess4j.enu.GroupType;
import com.citi.alan.myproject.tess4j.enu.ImageType;
import com.citi.alan.myproject.tess4j.enu.TransferType;
import com.citi.alan.myproject.tess4j.model.BillOrderDetail;
import com.citi.alan.myproject.tess4j.service.api.BillOrderDetectorService;
import com.citi.alan.myproject.tess4j.service.api.MerchantService;
import com.citi.alan.myproject.tess4j.util.DateUtil;
import com.citi.alan.myproject.tess4j.util.ImageUtil;
import com.citi.alan.myproject.tess4j.util.TessercatUtil;

@Service
public class BillOrderDetectorServiceImpl implements BillOrderDetectorService {

	private static Logger logger = Logger.getLogger(BillOrderDetectorServiceImpl.class);
	
	 
    @Autowired
    private TessercatUtil tessercatUtil;

    @Autowired
    private ImageUtil imageUtil;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private UserInfoDao userInfoDao;
    
    @Autowired
    MerchantService merchantService;

    @Value("${alipay.identifier}")
    private String alipayIdentifier;

    @Value("${elian.identifier}")
    private String elianIdentifier;

    private DecimalFormat decimalFormat = new DecimalFormat(".00");



    public Map<String, Object> getBillOrderDetailList(String id, String userName, String reportDateFrom, String reportDateTo, Pageable pagRequest) {
        Map<String, Object> result = new HashMap<>();
        try{
			if (!StringUtils.isEmpty(id)) {
				OrderDetail orderDetail = orderDetailDao.findOne(Integer.valueOf(id));
				if(orderDetail!=null){
					List<OrderDetail> orderDetails = new ArrayList<>();
					orderDetails.add(orderDetail);
					List<BillOrderDetail> billOrderDetails = populateBillOrderDetails(orderDetails);
					result.put("rows", billOrderDetails);
					result.put("page", 1);
					result.put("total", 1);
					return result;
				}else{
					result.put("rows", null);
					result.put("page", 1);
					result.put("total", 0);
					return result;
				}

			}
            
            Page<OrderDetail> page = orderDetailDao.findAll(new Specification<OrderDetail>() {
    			@Override
    			public Predicate toPredicate(Root<OrderDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    				Path<String> userNamePath = root.get("userInfo").get("userName");
    				Path<String> createdDatePath = root.get("createdDate");
    				List<Predicate> list = new ArrayList<>();
    				if(!StringUtils.isEmpty(userName)){
    					list.add(cb.like(userNamePath, "%"+userName+"%"));  
    				}
    				if(!StringUtils.isEmpty(reportDateFrom)){
    					list.add(cb.greaterThan(createdDatePath, reportDateFrom+" 00:00:00"));
    				}
    				if(!StringUtils.isEmpty(reportDateTo)){
    					list.add(cb.lessThan(createdDatePath, reportDateTo+" 23:59:59"));
    				}
    				Predicate [] p = new Predicate[list.size()];
    				return cb.and(list.toArray(p));
    			}
    		}, pagRequest);
            
            result = createResultMap(page);
        }catch(Exception se){
        		logger.error(se);
        }
        
        return result;

    }


    public List<BillOrderDetail> getBillOrderDetailList(String mobile) {
        List<BillOrderDetail> billOrderDetails = new ArrayList<>();
        try {
        	//get 10 records as default
        	    Pageable pagRequest = new PageRequest(0, 10, new Sort(Sort.Direction.DESC,"createdDate"));
            List<OrderDetail> orderDetails = orderDetailDao.findByUserInfoMobile(mobile, pagRequest);
            billOrderDetails = populateBillOrderDetails(orderDetails);
        } catch (Exception se) {
        		logger.error(se);
        }
        return billOrderDetails;
    }
    
    public List<BillOrderDetail> getExportingBillOrderDetailList(String reportDateFrom, String reportDateTo){
        List<BillOrderDetail> billOrderDetails = new ArrayList<>();
        try {
            List<OrderDetail> orderDetails = orderDetailDao.getReportByFilter(reportDateFrom, reportDateTo);
            billOrderDetails = populateBillOrderDetails(orderDetails);
        } catch (Exception se) {
                logger.error(se);
        }
        return billOrderDetails;
    }


    private List<BillOrderDetail> populateBillOrderDetails(List<OrderDetail> orderDetails) throws IllegalAccessException, InvocationTargetException {
        List<BillOrderDetail> billOrderDetails = new ArrayList<>();
        if (orderDetails != null) {
            for (OrderDetail orderDetail : orderDetails) {
                BillOrderDetail billOrderDetail = new BillOrderDetail();
                BeanUtils.copyProperties(billOrderDetail, orderDetail);
                billOrderDetail.setActivityType(Dictionary.activityMap.get(orderDetail.getActivityType()));
                billOrderDetail.setTransferType(Dictionary.tranferMap.get(orderDetail.getTransferType()));
                billOrderDetail.setUserName(orderDetail.getUserInfo().getUserName());
                billOrderDetail.setNickName(orderDetail.getUserInfo().getNickName());
                billOrderDetail.setAlipayAccount(orderDetail.getUserInfo().getAlipayAccount());
                billOrderDetail.setGroupName(orderDetail.getUserInfo().getGroupName());
                billOrderDetails.add(billOrderDetail);
            }

        }
        return billOrderDetails;
    }



    private Map<String, Object> createResultMap(Page<OrderDetail> page) throws IllegalAccessException, InvocationTargetException {
        Map<String, Object> result = new HashMap<>();
        List<OrderDetail> orderDetails = page.getContent();
        List<BillOrderDetail> billOrderDetails = populateBillOrderDetails(orderDetails);
        result.put("rows", billOrderDetails);
        result.put("page", page.getNumber());
        result.put("total", page.getTotalElements());
        return result;
    }
    
    @Transactional
    public boolean updateOrderDetail(BillOrderDetail billOrderDetail) {
        boolean flag = false;
        try {
            OrderDetail orderDetail = orderDetailDao.findOne(billOrderDetail.getId());
            BeanUtils.copyProperties(orderDetail, billOrderDetail);
            orderDetail.setTransferType(Dictionary.tranferMap.get(billOrderDetail.getTransferType()));
            orderDetail.setActivityType(Dictionary.activityMap.get(billOrderDetail.getActivityType()));
            orderDetailDao.save(orderDetail);
            flag = true;
        } catch (Exception e) {
        		logger.error(e);
        } 
        return flag;
    }
    
    public void removeOrderDetail(Integer id){
        orderDetailDao.delete(id);
        
    }

    public boolean saveOrderDetail(BillOrderDetail billOrderDetail) {
        OrderDetail orderDetail = new OrderDetail();
        boolean flag = false;
        try {
            BeanUtils.copyProperties(orderDetail, billOrderDetail);
            UserInfo userInfo = userInfoDao.findByMobile(billOrderDetail.getMobile());
            orderDetail.setUserInfo(userInfo);
            orderDetail.setCreatedDate(DateUtil.getFormatDateStr("yyyy/MM/dd HH:mm:ss"));
            Merchant merchant = merchantService.getMerchantMap().get(billOrderDetail.getMerchantName());
            orderDetail.setMerchantName(merchant.getMerchantName());
            orderDetailDao.save(orderDetail);
            flag = true;
        } catch (Exception e) {
        		logger.error(e.toString());
        		
        } 
        return flag;
    }

    @Override
    public BillOrderDetail detetctBillOrderDetail(File file, String activityType, UserInfo user) {
        BillOrderDetail billOrderDetail = new BillOrderDetail();
        try {
            String newFile = imageUtil.processImageThreshold(file.getAbsolutePath(),ImageType.BILLORDER_SCREENSHOT);
            String result = tessercatUtil.parseImage(new File(newFile));

            if (result.contains(alipayIdentifier)) {
                logger.info("this is alipay");
                billOrderDetail = processAlipayOrder(result, activityType);
            } else if (result.contains(elianIdentifier)) {
                logger.info("this is elian pay");
                billOrderDetail = processElianOrder(result, activityType);
            } else {
                logger.info("this is weixin pay");
                billOrderDetail = processWeixinOrder(result, activityType);
            }

        } catch (Exception e) {
            logger.error(e);
        }

        setUserInfoDetail(billOrderDetail, user, activityType);
        return billOrderDetail;

    }

    private void setUserInfoDetail(BillOrderDetail billOrderDetail, UserInfo userInfo, String activityType) {

        billOrderDetail.setUserName(userInfo.getUserName());
        billOrderDetail.setNickName(userInfo.getNickName());
        billOrderDetail.setAlipayAccount(userInfo.getAlipayAccount());
        billOrderDetail.setGroupName(userInfo.getGroupName());
        calculateRate(billOrderDetail, activityType, userInfo);

    }

    private void calculateRate(BillOrderDetail billOrderDetail, String activityType, UserInfo userInfo) {
        String rate = "";
        String transferType = billOrderDetail.getTransferType();
        String group = userInfo.getGroupName(); 
        if (ActivityType.NO_ASSISTS.getValue().equals(activityType)) {// no assists
            rate = setRateForNewer(transferType, rate);
        } else if (ActivityType.GENERAL_ASSISTS.getValue().equals(activityType)) {// assists
            if (GroupType.ACCOUNTING_GROUP.getName().equals(group)) {
                rate = "0.2";
            } else if (GroupType.SENIOR_GROUP.getName().equals(group)) {
                if (TransferType.ELIANPAY.getValue().equals(transferType)) {
                    rate = "0.2";
                } else if (TransferType.WEIXINPAY.getValue().equals(transferType)) {
                    rate = "0.25";
                } else if (TransferType.ALIPAY.getValue().equals(transferType)) {
                    rate = "0.3";
                }
            } else if (GroupType.MIDDLE_GROUP.getName().equals(group)) {
                if (TransferType.ELIANPAY.getValue().equals(transferType)) {
                    rate = "0.25";
                } else if (TransferType.WEIXINPAY.getValue().equals(transferType)) {
                    rate = "0.3";
                } else if (TransferType.ALIPAY.getValue().equals(transferType)) {
                    rate = "0.35";
                }
            } else if (GroupType.PRIMARY_GROUP.getName().equals(group)) {
                if (TransferType.ELIANPAY.getValue().equals(transferType)) {
                    rate = "0.3";
                } else if (TransferType.WEIXINPAY.getValue().equals(transferType)) {
                    rate = "0.35";
                } else if (TransferType.ALIPAY.getValue().equals(transferType)) {
                    rate = "0.4";
                }
            } else {
                rate = setRateForNewer(transferType, rate);
            }
        }else if (ActivityType.LUCKY_GUY.getValue().equals(activityType)) {
            rate = "0.1";
        }else if (ActivityType.NEW_WELFARE.getValue().equals(activityType)) {
            rate = "0.2";
        }else if (ActivityType.BLOOD_RETURN.getValue().equals(activityType)) {
            rate = "0.15";
        }else if (ActivityType.WECHAT_FOLLOWING.getValue().equals(activityType)) {
            rate = "0.2";
        }else if (ActivityType.WEIXIN_ZERO_RATE.getValue().equals(activityType)) {
            rate = "0";
        }
        billOrderDetail.setRate(rate);
    }

    private String setRateForNewer(String transferType, String rate) {
        String calculatedRate = "";
        if (TransferType.ELIANPAY.getValue().equals(transferType)) {
            calculatedRate = "0.4";
        } else if (TransferType.WEIXINPAY.getValue().equals(transferType) || TransferType.ALIPAY.getValue().equals(transferType)) {
            calculatedRate = "0.45";
        }
        return calculatedRate;
    }

    private BillOrderDetail processAlipayOrder(String result, String activityType) {
        String[] list = result.split("\n");
        Map<String, String> resultMap = new HashMap<String, String>();
        BillOrderDetail billOrderDetail = new BillOrderDetail();

        for (int i = 0; i < list.length; i++) {
            String singleLineResult = list[i];
            if(singleLineResult.contains("1605")) {
            	 singleLineResult= "商户订单编号 "+singleLineResult;
            }
            String key = singleLineResult.substring(0, singleLineResult.indexOf(" ") + 1);
            String value = singleLineResult.substring(singleLineResult.indexOf(key) + key.length());
            if (key.contains("单金")) {
                key = "订单金额";
            } else if (key.contains("随机立")) {
                key = "随机立减";
            }else if (key.contains("奖励金")) {
                key = "奖励金";
            }
            resultMap.put(key.trim(), value.trim());
        }
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            logger.info("key:" + entry.getKey() + "\t value:" + entry.getValue());
        }

        try {
            String date = resultMap.get("创建时间").replaceAll("o", "0").replaceAll("O", "0");
            setScanDate(billOrderDetail, date);
        } catch (Exception se) {
            logger.error(se);
        }

        try {
            String merchantsOrderNum = resultMap.get("商户订单编号").replaceAll("o", "0").replaceAll("O", "0");
            billOrderDetail.setOrderNum(merchantsOrderNum);
            String merchantsNo = merchantsOrderNum.substring(0, 12);

            // Merchant merchant = map.get(merchantsNo);
            billOrderDetail.setMerchantName(merchantsNo);
        } catch (Exception se) {
            logger.error(se);
        }

        try {
            String orderAmount = resultMap.get("订单金额");
            orderAmount = parseOrderAmount(orderAmount);
            Float actualAmount = Float.valueOf(orderAmount);
            
            String redBag = resultMap.get("红包");
            float redBagPrice = 0f;
            if(!StringUtils.isEmpty(redBag)) {
            		redBag = redBag.replaceAll("_", "").replaceAll("-", "").replaceAll("一", "").replaceAll("o", "0").replaceAll("O", "0");
            		redBagPrice = Float.valueOf(redBag);
            }
            
            String randomDiscountedPriceStr = resultMap.get("随机立减");
            float randomDiscountedPrice = 0f;
            if(!StringUtils.isEmpty(randomDiscountedPriceStr)) {
            		randomDiscountedPriceStr = randomDiscountedPriceStr.replaceAll("_", "").replaceAll("-", "").replaceAll("一", "").replaceAll("o", "0").replaceAll("O", "0");
            		randomDiscountedPrice = Float.valueOf(randomDiscountedPriceStr);
            		billOrderDetail.setDiscountedPrice(decimalFormat.format(randomDiscountedPrice));
            }
            		
            String bonusStr = resultMap.get("奖励金");
            float bonus = 0f;
            if(!StringUtils.isEmpty(bonusStr)) {
        			bonusStr = bonusStr.replaceAll("_", "").replaceAll("-", "").replaceAll("一", "").replaceAll("o", "0").replaceAll("O", "0");
        			bonus = Float.valueOf(bonusStr);
            }
            actualAmount = actualAmount - redBagPrice - randomDiscountedPrice - bonus;
            billOrderDetail.setDiscountedPrice(decimalFormat.format(randomDiscountedPrice));
            billOrderDetail.setActualPrice(decimalFormat.format(actualAmount));
        } catch (Exception se) {
            logger.error(se);
        }

        billOrderDetail.setTransferType(TransferType.ALIPAY.getValue());
        billOrderDetail.setActivityType(activityType);

        return billOrderDetail;
    }


    /**
     * processs Elian Pay order
     * 
     * @param result
     * @param activityType
     * @return
     */
    private BillOrderDetail processElianOrder(String result, String activityType) {

        String[] list = result.split("\n");
        Map<String, String> resultMap = new HashMap<String, String>();
        BillOrderDetail billOrderDetail = new BillOrderDetail();

        for (int i = 0; i < list.length; i++) {
            String singleLineResult = list[i];
            if(singleLineResult.contains("交易 时间")){
    				singleLineResult = singleLineResult.replace("交易 时间", "交易时间");
            }
            String key = singleLineResult.substring(0, singleLineResult.indexOf(" ") + 1);
            String value = singleLineResult.substring(singleLineResult.indexOf(key) + key.length());
            if (key.contains("实付金额")) {
                key = "订单实付金额";
            } else if (value.contains("时间") || key.contains("交易时间")) {
                key = "支付时间";
                if (value.indexOf("时间") > 0) {
                    value = value.substring(value.indexOf("时间") + 2).trim();
                } else {
                    value = value.trim();
                }

            } else if (key.contains("订单编号") || key.contains("订单号")  || key.contains("订单缩号")) {
                key = "商户订单号"; 
            }
            resultMap.put(key.trim(), value.trim());
        }

        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            logger.info("key:" + entry.getKey() + "\t value:" + entry.getValue());
        }

        try {
            String date = resultMap.get("支付时间").replaceAll("o", "0").replaceAll("O", "0");
            setScanDate(billOrderDetail, date);
        } catch (Exception se) {
            logger.error(se);
        }

        try {
            String orderNum = resultMap.get("商户订单号").replaceAll(" ", "").replaceAll("o", "0").replaceAll("O", "0").replaceAll("’", "").replaceAll("'", "");
            billOrderDetail.setOrderNum(orderNum);
            String merchantsNo = orderNum.substring(0, 12);
            billOrderDetail.setMerchantName(merchantsNo);
        } catch (Exception e) {
            logger.error(e);
        }
        try {
        		String orderAmountStr = resultMap.get("订单实付金额");
        		orderAmountStr = parseOrderAmount(orderAmountStr);
            float orderPrice = Float.valueOf(orderAmountStr);
            billOrderDetail.setActualPrice(decimalFormat.format(orderPrice));
        } catch (Exception se) {
            logger.error(se);
        }

        billOrderDetail.setTransferType(TransferType.ELIANPAY.getValue());
        billOrderDetail.setActivityType(activityType);

        return billOrderDetail;

    }


    private BillOrderDetail processWeixinOrder(String result, String activityType) {
        String[] list = result.split("\n");
        Map<String, String> resultMap = new HashMap<String, String>();
        BillOrderDetail billOrderDetail = new BillOrderDetail();

        for (int i = 0; i < list.length; i++) {
            String singleLineResult = list[i];
            if(singleLineResult.contains("商户 单号")){
            		singleLineResult = singleLineResult.replace("商户 单号", "商户单号");
            }else if(singleLineResult.contains("支付 时间")){
        			singleLineResult = singleLineResult.replace("支付 时间", "支付时间");
            }

            String key = singleLineResult.substring(0, singleLineResult.indexOf(" ") + 1);
            String value = singleLineResult.substring(singleLineResult.indexOf(key) + key.length());
            if (key.contains("付款金额")) {
                key = "付款金额";
            } 
            resultMap.put(key.trim(), value.trim());
        }
        
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            logger.info("key:" + entry.getKey() + "\t value:" + entry.getValue());
        }

        try {
            String date = resultMap.get("支付时间").replaceAll("o", "0").replaceAll("O", "0");
            setScanDate(billOrderDetail, date);
        } catch (Exception se) {
            logger.error(se);
        }

        try {
            String orderNum = resultMap.get("商户单号").replaceAll("o", "0").replaceAll("O", "0");
            billOrderDetail.setOrderNum(orderNum);
            String merchantsNo = orderNum.substring(0, 12);
            billOrderDetail.setMerchantName(merchantsNo);
        } catch (Exception e) {
            logger.error(e);
        }

        try {
            String orderAmount = resultMap.get("付款金额");
            orderAmount = parseOrderAmount(orderAmount);
            float orderPrice = Float.valueOf(orderAmount);
            billOrderDetail.setActualPrice(decimalFormat.format(orderPrice));
        } catch (Exception e) {
            logger.error(e);
        }

        billOrderDetail.setTransferType(TransferType.WEIXINPAY.getValue());
        billOrderDetail.setActivityType(activityType);
        return billOrderDetail;
    }
    
	private void setScanDate(BillOrderDetail billOrderDetail, String date) {
		if (date.contains("-") || date.contains("_")) {
			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			String day = date.substring(8, 10);
			// billOrderDetail.setDate(month + "/" + day + "/" + year);
			billOrderDetail.setScanDate(year + "-" + month + "-" + day);
		} else {
			String year = date.substring(0, 4);
			String month = date.substring(4, 6);
			String day = date.substring(6, 8);
			// billOrderDetail.setDate(month + "/" + day + "/" + year);
			billOrderDetail.setScanDate(year + "-" + month + "-" + day);
		}

	}
    
	private String parseOrderAmount(String orderAmountStr) {
		String firstChar = orderAmountStr.substring(0, 1);
		if(NumberUtils.isNumber(firstChar)) {
			orderAmountStr = replaceDirtyChars(orderAmountStr);
		}else {
			orderAmountStr = replaceDirtyChars(orderAmountStr.substring(1));
		}
		orderAmountStr = orderAmountStr.substring(0, orderAmountStr.indexOf(".")+2);
		return orderAmountStr;
	}
	
	private String replaceDirtyChars(String orderAmountStr) {
		return orderAmountStr.replaceAll("′", "")
				.replaceAll("O", "0")
				.replaceAll("o", "0")
				.replaceAll(",", "")
				.replaceAll("，", "")
				.replace(" ", "").trim();
	}

}
