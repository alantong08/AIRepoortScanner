package com.citi.alan.myproject.tess4j.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.citi.alan.myproject.tess4j.model.BillOrderDetail;
import com.citi.alan.myproject.tess4j.service.api.BillOrderDetectorService;
import com.citi.alan.myproject.tess4j.util.DateUtil;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;

@RequestMapping("/admin")
@Controller
public class ExcelController {
    
    private static Logger logger = Logger.getLogger(AdminController.class);
    
    @Autowired
    private BillOrderDetectorService billOrderDetectorService;

    
    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 下载文件的默认名称
        String currentDate = DateUtil.getFormatDateStr("yyyy-MM-dd");
        String fileName = "orderDetail-"+currentDate+".xls";
        response.setHeader("Content-Disposition", "attachment;filename="+fileName);
        response.setContentType("application/vnd.ms-excel");

        String scanDate = request.getParameter("scanDate");
        if (StringUtils.isEmpty(scanDate)) {
            scanDate = currentDate;
        }
        logger.info("scanDate:" + scanDate);
        try {
            List<BillOrderDetail> billOrderDetails = billOrderDetectorService.getExportingBillOrderDetailList(scanDate);
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("第一玩卡群扫码明细", ""), BillOrderDetail.class, billOrderDetails);
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            logger.error(e);
        }

    }

}
