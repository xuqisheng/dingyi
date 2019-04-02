package com.zhidianfan.pig.yd.utils;


import com.zhidianfan.pig.common.util.HttpKit;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Author: huzp
 * @Date: 2018/11/13 13:47
 * @DESCRIPTION excel 下载 与导入
 */
public class ExcelUtil {


    private static final Map<String, String[]> namesMap = new HashMap<>(3);
    private static final Map<String, String> fileNameMap = new HashMap<>(3);
    private static final Map<String, String[]> headsMap = new HashMap<>(3);
    private static final Map<String, int[]> widthsMap = new HashMap<>(3);

    static {


        headsMap.put("resvline", new String[]{"就餐时间", "排队号", "预计到达时间", "姓名", "手机号", "人数", "备注", "状态"});
        headsMap.put("resvorder", new String[]{"批次号", "就餐时间", "客户名称", "客户号码", "桌位信息", "订单状态"});
        headsMap.put("vipinfo", new String[]{"客户姓名", "客户性别", "客户手机号码", "公司", "职位", "客户价值名称", "客户分类名称", "就餐次数", "最后就餐时间"});
        headsMap.put("vip", new String[]{"姓名", "性别（男/女）", "电话", "公司", "职位", "生日", "备用电话", "喜好","忌口", "标签"});
        headsMap.put("sncode", new String[]{"SN码"});


        namesMap.put("resvline", new String[]{"eatTime", "lineSort", "resvTime", "vipName", "vipPhone", "resvNum", "remark", "status"});
        namesMap.put("resvorder", new String[]{"batchNo", "eatTime", "vipName", "vipPhone", "tableInfo", "status"});
        namesMap.put("vipinfo", new String[]{"vipName", "vipSex", "vipPhone", "vipCompany", "vipPostion", "vipValueName", "vipClassName", "actResvTimes", "lastEatTime"});
        namesMap.put("vip", new String[]{"vipName", "vipSex", "vipPhone", "vipCompany", "vipPostion", "vipBirthday", "vipPhone2", "hobby", "detest","tag"});
        namesMap.put("sncode", new String[]{"code"});


        widthsMap.put("resvline", new int[]{8129, 4096, 4096, 4096, 4096, 4096, 4096, 4096});
        widthsMap.put("resvorder", new int[]{8129, 8129, 4096, 4096, 12288, 8129});
        widthsMap.put("vipinfo", new int[]{4096, 4096, 4096,4096, 4096, 4096, 4096, 4096, 4096});
        widthsMap.put("vip", new int[]{4096, 4096, 4096, 4096, 4096, 4096, 4096, 4096, 4096, 4096});
        widthsMap.put("sncode", new int[]{8129});



        fileNameMap.put("resvline", "排队预订单.xlsx");
        fileNameMap.put("resvorder", "预订单.xlsx");
        fileNameMap.put("vipinfo", "客户信息.xlsx");
        fileNameMap.put("vip", "客户信息.xlsx");
        fileNameMap.put("sncode", "SN码.xlsx");


    }


    /**
     * excel准备工作
     *
     * @param workbook workbook
     * @param sheet    sheet
     * @param sign     菜单标签
     */
    private static void prepareExcel(SXSSFWorkbook workbook, Sheet sheet, String sign) {

        String[] heads = headsMap.get(sign);
        CellStyle style = workbook.createCellStyle();
        style.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints((float) 18.75);
        Cell cell;
        CellStyle cellStyle = getTitleStyle(workbook);
        //首行
        for (int i = 0; i < heads.length; i++) {
            sheet.setColumnWidth(i, widthsMap.get(sign)[i]);
            cell = titleRow.createCell(i);
            cell.setCellValue(heads[i]);
            cell.setCellStyle(cellStyle);
        }
    }

    /**
     * 获取单元格格式
     */
    private static CellStyle getTitleStyle(SXSSFWorkbook workbook) {

        CellStyle cellStyle = workbook.createCellStyle();
        Font fontStyle = workbook.createFont(); // 字体样式
        fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 加粗
        fontStyle.setFontName("宋体"); // 字体
        fontStyle.setFontHeightInPoints((short) 14);
        cellStyle.setFont(fontStyle);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setWrapText(true);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor((short) 27);

        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM); //下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);//左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);//右边框

        return cellStyle;

    }

    private static CellStyle getStyle(SXSSFWorkbook workbook) {

        CellStyle cellStyle = workbook.createCellStyle();
        Font fontStyle = workbook.createFont(); // 字体样式
        fontStyle.setFontName("宋体"); // 字体
        fontStyle.setFontHeightInPoints((short) 12);
        cellStyle.setFont(fontStyle);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setWrapText(true);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM); //下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);//左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);//右边框

        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor((short) 9);
        return cellStyle;
    }


    /**
     * 返回到页面下载
     *
     * @param response response
     * @param workbook workbook
     * @param fileName 文件名
     * @throws IOException 导出异常
     */
    private static void returnExcelToPage(HttpServletResponse response, SXSSFWorkbook workbook,
                                          String fileName) {

        OutputStream outputStream = null;

        try {

            response.setHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode(fileName, "UTF-8"));

            response.setContentType("application/vnd.ms-excel;charset=UTF-8");

            outputStream = response.getOutputStream();

            workbook.write(outputStream);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @param sign    标志
     * @param records 记录
     */
    public static void ListExport2Excel(String sign, List<?> records) {


        SXSSFWorkbook workbook = new SXSSFWorkbook(100);// 创建一个Excel文件,超过100行,将会写入磁盘

        Row row;

        int rowNum;
        CellStyle contentStyle = getStyle(workbook);
        Cell cell;
        //创建sheet页
        Sheet sheet;

        try {
            sheet = workbook.createSheet();
            ExcelUtil.prepareExcel(workbook, sheet, sign);

            String[] nameStrings = namesMap.get(sign);

            //第几行开始
            rowNum = 1;
            for (Object obj : records) {

                row = sheet.createRow(rowNum++);

                //第几列
                int j = 0;

                for (String name : nameStrings) {
                    String method = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                    Method m = obj.getClass().getMethod(method);
                    Object invoke = m.invoke(obj);
                    //如果 某个字段为空则为空字符串
                    String value = (invoke == null ? "" : invoke.toString());
                    //设置值 设置style
                    cell = row.createCell(j);
                    cell.setCellValue(value);
                    cell.setCellStyle(contentStyle);
                    j++;
                }
            }

            //web 下载
            HttpServletResponse response = HttpKit.getResponse();
            ExcelUtil.returnExcelToPage(response, workbook, fileNameMap.get(sign));


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 客户信息excel导入生成list 专用方法
     *
     * @param file 文件
     * @return 返回
     */
    public static List<Map<String, Object>> ReadExcel(Part file, String sign) {


        //cellName 为数据库字段 , cellNameC为excel中文字段名
        String[] cellName = namesMap.get(sign),
                cellNameC = headsMap.get(sign);

        List<Map<String, Object>> list = new ArrayList<>();

        // 获取workbook对象
        Workbook workbook;

        try {

            InputStream is = file.getInputStream();

            workbook = getWorkbook(file);

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                return list;
            }

            int firstRowIndex = sheet.getFirstRowNum();
            int lastRowIndex = sheet.getLastRowNum();
            // 读取首行 即,表头
            Row firstRow = sheet.getRow(firstRowIndex);
            if (firstRow.getLastCellNum() != cellNameC.length) {
                return list;
            }
            for (int i = firstRow.getFirstCellNum(); i < firstRow.getLastCellNum(); i++) {
                Cell cell = firstRow.getCell(i);
                String cellValue = getCellValue(cell);
                if (!cellValue.equals(cellNameC[i])) {
                    return list;
                }
            }

            // 读取数据行
            int firstColumnIndex = 0;
            int lastColumnIndex = cellName.length;
            for (int rowIndex = 1; rowIndex <= lastRowIndex; rowIndex++) {
                Row currentRow = sheet.getRow(rowIndex);// 当前行
                Map<String, Object> map = new LinkedHashMap<>();
                for (int columnIndex = firstColumnIndex; columnIndex < lastColumnIndex; columnIndex++) {
                    Object currentCellValue = getCellFormatValue(currentRow.getCell(columnIndex));// 当前单元格的值
                    map.put(cellName[columnIndex], currentCellValue);

                }
                list.add(map);
            }
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private static Workbook getWorkbook(Part file) throws IOException {
        Workbook workbook = null;
        InputStream is = file.getInputStream();
        if (file.getSubmittedFileName().endsWith("xls")) {
            workbook = new HSSFWorkbook(is);
        } else if (file.getSubmittedFileName().endsWith("xlsx")) {
            workbook = new XSSFWorkbook(is);
        }
        return workbook;
    }


    public static void preReadCheck(Part file) throws FileNotFoundException {
        // 常规检查
        if (null == file) {
            throw new FileNotFoundException("传入的文件不存在");
        }

        if (!(file.getSubmittedFileName().endsWith("xls") || file.getSubmittedFileName().endsWith("xlsx") || file.getSubmittedFileName().endsWith("csv"))) {
            throw new FileNotFoundException("传入的文件不是excel");
        }

    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        //将数字文本转为字符串文本
        // 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
        // 加上下面这句，临时把它当做文本来读取
        cell.setCellType(Cell.CELL_TYPE_STRING);

        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    }

    private static Object getCellFormatValue(Cell cell) {
        Object cellValue;
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getRichStringCellValue().getString().trim();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date theDate = cell.getDateCellValue();
                        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
                        cellValue = dff.format(theDate);
                    } else {
                        DecimalFormat df = new DecimalFormat("0");
                        cellValue = df.format(cell.getNumericCellValue());
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cellValue = cell.getCellFormula();
                    break;
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }


}
