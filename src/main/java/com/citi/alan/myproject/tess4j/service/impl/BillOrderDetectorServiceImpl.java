package com.citi.alan.myproject.tess4j.service.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.citi.alan.myproject.tess4j.dao.OrderDetailDao;
import com.citi.alan.myproject.tess4j.dao.UserInfoDao;
import com.citi.alan.myproject.tess4j.dict.Dictionary;
import com.citi.alan.myproject.tess4j.entity.Merchant;
import com.citi.alan.myproject.tess4j.entity.OrderDetail;
import com.citi.alan.myproject.tess4j.entity.UserInfo;
import com.citi.alan.myproject.tess4j.enu.ActivityType;
import com.citi.alan.myproject.tess4j.enu.GroupType;
import com.citi.alan.myproject.tess4j.enu.TransferType;
import com.citi.alan.myproject.tess4j.model.BillOrderDetail;
import com.citi.alan.myproject.tess4j.service.api.BillOrderDetectorService;
import com.citi.alan.myproject.tess4j.service.api.MerchantService;
import com.citi.alan.myproject.tess4j.util.DateUtil;
import com.citi.alan.myproject.tess4j.util.ImageUtil;
import com.citi.alan.myproject.tess4j.util.TessercatUtil;

import net.sourceforge.tess4j.TesseractException;

@Service
public class BillOrderDetectorServiceImpl implements BillOrderDetectorService {

    @Autowired
    private TessercatUtil tessercatUtil;

    @Autowired
    private ImageUtil imageUtil;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Value("${alipay.identifier}")
    private String alipayIdentifier;

    @Value("${elian.identifier}")
    private String elianIdentifier;

    private Map<String, Merchant> map = new HashMap<String, Merchant>();

    private DecimalFormat decimalFormat = new DecimalFormat(".00");

    @PostConstruct
    public void loadMerchantMap() {
        map = merchantService.getMerchantMap();
    }

    public List<BillOrderDetail> getBillOrderDetailList(String userName, String scanDate) {
        List<BillOrderDetail> billOrderDetails = new ArrayList<>();
        if (!StringUtils.isAllEmpty(userName) && !StringUtils.isAllEmpty(scanDate) ) {
            billOrderDetails = getOrdersByUserNameAndScanDate(userName, scanDate);
            return billOrderDetails;
        }

        if (StringUtils.isAllEmpty(userName) && !StringUtils.isAllEmpty(scanDate)) {
            billOrderDetails = getOrdersByScanDate(scanDate);
            return billOrderDetails;
        }

        if (!StringUtils.isAllEmpty(userName) && StringUtils.isAllEmpty(scanDate)) {
            billOrderDetails = getOrdersByUserName(userName);
            return billOrderDetails;
        }

        if (StringUtils.isAllEmpty(userName) && StringUtils.isAllEmpty(scanDate)) {
            billOrderDetails = getAllBillOrderDetailList();
            return billOrderDetails;
        }
        
        return billOrderDetails;

    }

    private List<BillOrderDetail> getOrdersByUserName(String userName) {
        List<BillOrderDetail> billOrderDetails = new ArrayList<>();
        try {
            List<OrderDetail> orderDetails = orderDetailDao.findByUserInfoUserNameOrderByCreatedDateDesc(userName);
            billOrderDetails = populateBillOrderDetails(orderDetails);
        } catch (Exception se) {
            se.printStackTrace();
        }
        return billOrderDetails;
    }

    private List<BillOrderDetail> getOrdersByUserNameAndScanDate(String userName, String scanDate) {
        List<BillOrderDetail> billOrderDetails = new ArrayList<>();
        try {
            List<OrderDetail> orderDetails = orderDetailDao.findByScanDateAndUserInfoUserNameOrderByCreatedDateDesc(scanDate, userName);
            billOrderDetails = populateBillOrderDetails(orderDetails);
        } catch (Exception se) {
            se.printStackTrace();
        }
        return billOrderDetails;
    }

    private List<BillOrderDetail> getOrdersByScanDate(String scanDate) {
        List<BillOrderDetail> billOrderDetails = new ArrayList<>();
        try {
            List<OrderDetail> orderDetails = orderDetailDao.findByScanDateOrderByCreatedDateDesc(scanDate);
            billOrderDetails = populateBillOrderDetails(orderDetails);
        } catch (Exception se) {
            se.printStackTrace();
        }
        return billOrderDetails;
    }

    public List<BillOrderDetail> getBillOrderDetailList(String mobile) {
        List<BillOrderDetail> billOrderDetails = new ArrayList<>();
        try {
            List<OrderDetail> orderDetails = orderDetailDao.findByUserInfoMobileOrderByCreatedDateDesc(mobile);
            billOrderDetails = populateBillOrderDetails(orderDetails);
        } catch (Exception se) {
            se.printStackTrace();
        }
        return billOrderDetails;
    }

    private List<BillOrderDetail> populateBillOrderDetails(List<OrderDetail> orderDetails) throws IllegalAccessException, InvocationTargetException {
        List<BillOrderDetail> billOrderDetails = new ArrayList<>();
        if (orderDetails != null) {
           // Collections.sort(orderDetails, (o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));
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

    private List<BillOrderDetail> getAllBillOrderDetailList() {
        List<BillOrderDetail> billOrderDetails = new ArrayList<>();
        try {
            Pageable pagRequest = new PageRequest(1, 10, new Sort(Sort.Direction.DESC,"createdDate"));
            List<OrderDetail> orderDetails = orderDetailDao.findAll(pagRequest);
            billOrderDetails = populateBillOrderDetails(orderDetails);
        } catch (Exception se) {
            se.printStackTrace();
        }
        return billOrderDetails;

    }
    
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
            e.printStackTrace();
        } 
        return flag;
    }

    public boolean saveOrderDetail(BillOrderDetail billOrderDetail) {
        OrderDetail orderDetail = new OrderDetail();
        boolean flag = false;
        try {
            BeanUtils.copyProperties(orderDetail, billOrderDetail);
            UserInfo userInfo = userInfoDao.findByMobile(billOrderDetail.getMobile());
            orderDetail.setUserInfo(userInfo);
            orderDetail.setCreatedDate(DateUtil.getFormatDateStr("yyyy/MM/dd HH:mm:ss"));
            orderDetailDao.save(orderDetail);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return flag;
    }

    @Override
    public BillOrderDetail detetctBillOrderDetail(File file, String activityType, UserInfo user) {
        BillOrderDetail billOrderDetail = new BillOrderDetail();
        try {
            String newFile = imageUtil.processImageThreshold(file.getAbsolutePath());
            String result = tessercatUtil.parseImage(new File(newFile));

            if (result.contains(alipayIdentifier)) {
                System.out.println("this is alipay");
                billOrderDetail = processAlipayOrder(result, activityType);
                // chceckMerchantIsAllowed();
            } else if (result.contains(elianIdentifier)) {
                System.out.println("this is elian pay");
                billOrderDetail = processElianOrder(result, activityType);
            } else {
                System.out.println("this is weixin pay");
                billOrderDetail = processWeixinOrder(result, activityType);
            }

        } catch (IOException | TesseractException e) {
            e.printStackTrace();
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
        if (ActivityType.NO_ASSISTS.getValue().equals(activityType)) {// no
                                                                      // assists
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
            String key = singleLineResult.substring(0, singleLineResult.indexOf(" ") + 1);
            String value = singleLineResult.substring(singleLineResult.indexOf(key) + key.length());
            if (key.contains("单金")) {
                key = "订单金额";
            } else if (key.contains("随机立减")) {
                key = "随机立减";
            }
            resultMap.put(key.trim(), value.trim());
        }
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            System.out.println("key:" + entry.getKey() + "\t value:" + entry.getValue());
        }

        try {
            String date = resultMap.get("创建时间");
            setScanDate(billOrderDetail, date);
        } catch (Exception se) {
            se.printStackTrace();
        }

        try {
            String merchantsOrderNum = resultMap.get("").replaceAll("o", "0");
            billOrderDetail.setOrderNum(merchantsOrderNum);
            String merchantsNo = merchantsOrderNum.substring(0, 12);

            // Merchant merchant = map.get(merchantsNo);
            billOrderDetail.setMerchantName(merchantsNo);
        } catch (Exception se) {
            se.printStackTrace();
        }

        try {
            String orderAmount = resultMap.get("订单金额").replaceAll("o", "0");
            Float actualAmount = Float.valueOf(orderAmount);
            String discountedPrice = resultMap.get("随机立减").substring(1).replaceAll("o", "0").replaceAll("O", "0");

            if (!StringUtils.isEmpty(discountedPrice)) {
                Float disPrice = Float.valueOf(discountedPrice);
                actualAmount = actualAmount - disPrice;
                billOrderDetail.setDiscountedPrice(decimalFormat.format(disPrice));
            }
            billOrderDetail.setActualPrice(decimalFormat.format(actualAmount));
        } catch (Exception se) {
            se.printStackTrace();
        }

        billOrderDetail.setTransferType(TransferType.ALIPAY.getValue());
        billOrderDetail.setActivityType(activityType);

        return billOrderDetail;
    }

    private void setScanDate(BillOrderDetail billOrderDetail, String date) {
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        String day = date.substring(8, 10);
        // billOrderDetail.setDate(month + "/" + day + "/" + year);
        billOrderDetail.setScanDate(year + "-" + month + "-" + day);
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
            String key = singleLineResult.substring(0, singleLineResult.indexOf(" ") + 1);
            String value = singleLineResult.substring(singleLineResult.indexOf(key) + key.length());
            if (key.contains("实付金额")) {
                key = "订单金额";
            } else if (value.contains("时间") || key.contains("交易时间")) {
                key = "支付时间";
                if (value.indexOf("时间") > 0) {
                    value = value.substring(value.indexOf("时间") + 2).trim();
                } else {
                    value = value.trim();
                }

            } else if (key.contains("订单编号")) {
                key = "商户订单号";
            }
            resultMap.put(key.trim(), value.trim());
        }

        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            System.out.println("key:" + entry.getKey() + "\t value:" + entry.getValue());
        }

        try {
            String date = resultMap.get("支付时间");
            setScanDate(billOrderDetail, date);
        } catch (Exception se) {
            se.printStackTrace();
        }

        try {
            String orderNum = resultMap.get("商户订单号").replaceAll("o", "0").replaceAll("O", "0");
            billOrderDetail.setOrderNum(orderNum);
            String merchantsNo = orderNum.substring(0, 12);
            billOrderDetail.setMerchantName(merchantsNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String orderAmount = resultMap.get("订单金额").replaceAll("o", "0").replaceAll(",", "").replaceAll("，", "");
            orderAmount = orderAmount.substring(0, orderAmount.indexOf(".")).replace(" ", "");
            float orderPrice = Float.valueOf(orderAmount);
            billOrderDetail.setActualPrice(decimalFormat.format(orderPrice));
        } catch (Exception se) {
            se.printStackTrace();
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
            String key = singleLineResult.substring(0, singleLineResult.indexOf(" ") + 1);
            String value = singleLineResult.substring(singleLineResult.indexOf(key) + key.length());
            if (key.contains("付款金额")) {
                key = "订单金额";
            } else if (value.contains("单号")) {
                key = "商户订单号";
                value = value.substring(value.indexOf("单号") + 3).trim();
            }
            resultMap.put(key.trim(), value.trim());
        }

        try {
            String date = resultMap.get("支付时间");
            setScanDate(billOrderDetail, date);
        } catch (Exception se) {
            se.printStackTrace();
        }

        try {
            String orderNum = resultMap.get("商户订单号").replaceAll("o", "0").replaceAll("O", "0");
            billOrderDetail.setOrderNum(orderNum);
            String merchantsNo = orderNum.substring(0, 12);
            billOrderDetail.setMerchantName(merchantsNo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String orderAmount = resultMap.get("订单金额").substring(1).replaceAll("o", "0").replaceAll(" ", "");
            float orderPrice = Float.valueOf(orderAmount);
            billOrderDetail.setActualPrice(decimalFormat.format(orderPrice));
        } catch (Exception e) {
            e.printStackTrace();
        }

        billOrderDetail.setTransferType(TransferType.WEIXINPAY.getValue());
        billOrderDetail.setActivityType(activityType);
        return billOrderDetail;
    }

}
