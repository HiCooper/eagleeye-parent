package com.berry.eagleeye.management.common.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2018/9/1 14:41
 * fileName：ImportExcelUtil
 * Use：
 */
public class ExcelUtil {

    /**
     * 2003- 版本的excel
     */
    private final static String EXCEL_2003_L = ".xls";
    /**
     * 2007+ 版本的excel
     */
    private final static String EXCEL_2007_U = ".xlsx";

    public final static String ENC = "utf-8";

    /**
     * 行复制功能
     *
     * @param sheet
     * @param index
     * @param fromRow
     */
    public static Row insertRow(Sheet sheet, int index, Row fromRow) {
        Row newRow = sheet.createRow(index);
        for (Iterator<Cell> cellIt = fromRow.cellIterator(); cellIt.hasNext(); ) {
            Cell tmpCell = cellIt.next();
            Cell newCell = newRow.createCell(tmpCell.getColumnIndex());
            newCell.setCellValue(getCellValue(tmpCell).toString());
        }
        return newRow;
    }

    /**
     * 解析excel文件 file -> list
     *
     * @param file          文件
     * @param sheetIndex    指定解析sheet 索引，从0开始
     * @param formatJsonMap 文件头信息字段匹配信息（如文件中有表头姓名,一个值为'张三'）map.put("表头","name")，返回的map信息为： {key:name,value:"张三"}
     *                      详见 测试接口 importExcel
     * @return List<Map < String, Object>>
     * @throws Exception 解析异常
     */
    public static List<Map<String, Object>> parseExcel(InputStream inputStream, String fileName, Integer sheetIndex, Map<String, String> formatJsonMap) throws Exception {
        List<Map<String, Object>> data = new ArrayList<>();

        Workbook workbook = getWorkbook(inputStream, Objects.requireNonNull(fileName));
        //遍历Excel中所有的sheet
        for (int k = 0; k < workbook.getNumberOfSheets(); k++) {
            if (sheetIndex != null && k != sheetIndex) {
                continue;
            }
            Sheet sheet = workbook.getSheetAt(k);
            int firstRow = sheet.getFirstRowNum();
            int lastRow = sheet.getLastRowNum();

            // 行循环
            for (int i = firstRow + 1; i < lastRow + 1; i++) {
                Map<String, Object> map = new HashMap<>(16);

                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                int firstCell = row.getFirstCellNum();
                int lastCell = row.getLastCellNum();

                // 单元格循环
                for (int j = firstCell; j < lastCell; j++) {
                    // 获取表头信息
                    Cell head = sheet.getRow(firstRow).getCell(j);
                    if (head != null) {
                        String key = formatJsonMap.get(getCellValue(head).toString());
                        if (key == null) {
                            continue;
                        }
                        // 获取当前单元格
                        Cell cell = row.getCell(j);
                        // 获取单元格值
                        if (cell != null) {
                            Object val = getCellValue(cell);
                            map.put(key, val);
                        }
                    }
                }
                if (!map.isEmpty()) {
                    data.add(map);
                }
            }
            // 执行完指定 sheet 后 停止 循环
            if (sheetIndex != null) {
                break;
            }
        }
        workbook.close();
        return data;
    }

    /**
     * 写excel文件,
     *
     * @param data           待导出数据，excel指定封装实体
     * @param headRowNameMap 头信息map
     * @param fileName       导出文件名 已 xlsx 或 xls 结尾
     * @param outputStream   输出流
     */
    public static void writeExcel(List<?> data, LinkedHashMap<String, String> headRowNameMap, String fileName, ServletOutputStream outputStream) throws Exception {
        Workbook wb = getWorkbook(null, fileName);
        Sheet sheet = wb.createSheet("sheet1");

        // 创建表头
        Row headRow = sheet.createRow(0);
        CellStyle bigHeadCellStyle = wb.createCellStyle();
        bigHeadCellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = wb.createFont();
        font.setBold(true);
        bigHeadCellStyle.setFont(font);

        CellStyle subHeadCellStyle = wb.createCellStyle();
        subHeadCellStyle.setAlignment(HorizontalAlignment.CENTER);

        // 创建两种单元格格式
        // 列名 用
        CellStyle cs = wb.createCellStyle();
        // 主体数据用
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        cs.setFont(f);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());
        cs2.setFont(f2);

        // 设置主表头信息
        setHead(headRowNameMap, headRow, cs);

        // 写内容
        Row line;
        int rowCount = 1;
        for (Object object : data) {
            line = sheet.createRow(rowCount);
            // 设置表数据
            int cellCount = 0;
            for (String key : headRowNameMap.keySet()) {
                Method method = object.getClass().getDeclaredMethod("get" + getMethodName(key));
                if (method.getName().startsWith("get")) {
                    Object o = method.invoke(object);
                    Cell cell = line.createCell(cellCount);
                    if (o != null) {
                        cell.setCellValue(o.toString());
                        cell.setCellStyle(cs2);
                    }
                }
                cellCount++;
            }
            rowCount++;
        }
        wb.write(outputStream);
        outputStream.close();
        wb.close();
    }


    /**
     * 设置表头
     *
     * @param headRowNameMap
     * @param headRow
     * @param cellStyle
     */
    private static void setHead(Map<String, String> headRowNameMap, Row headRow, CellStyle cellStyle) {
        Cell cell;
        int cellCount = 0;
        for (String value : headRowNameMap.values()) {
            cell = headRow.createCell(cellCount);
            cell.setCellValue(value);
            cell.setCellStyle(cellStyle);
            cellCount++;
        }
    }

    /**
     * 获取方法名
     *
     * @param fieldName
     * @return
     */
    private static String getMethodName(String fieldName) {
        byte[] items = fieldName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr    输入流
     * @param fileName 输出流
     * @return
     * @throws Exception
     */
    private static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (EXCEL_2003_L.equals(fileType)) {
            //2003-
            if (inStr != null) {
                wb = new HSSFWorkbook(inStr);
            } else {
                wb = new HSSFWorkbook();
            }
        } else if (EXCEL_2007_U.equals(fileType)) {
            //2007+
            if (inStr != null) {
                wb = new XSSFWorkbook(inStr);
            } else {
                wb = new XSSFWorkbook();
            }
        } else {
            throw new Exception("文件名必须以xls或xlsx结尾！");
        }
        return wb;
    }

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    private static Object getCellValue(Cell cell) {
        Object value = null;
        //格式化number String字符
        DecimalFormat df = new DecimalFormat("0");
        //日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        //格式化数字
        DecimalFormat df2 = new DecimalFormat("0.00");

        switch (cell.getCellType()) {
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }

}