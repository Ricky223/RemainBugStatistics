import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

import static java.lang.System.exit;

public class remainBug {

    private static final List<String> OPERATION =
            Arrays.asList("hanyun", "hejl", "jialj", "lijh", "liangsf", "licf", "wangbin", "chaizp", "zhangjy",
                          "wangjc", "xitao", "macx", "licg", "zhoulong", "wangyue", "wanglin", "wangmh", "sunyx");

    private static final List<String> PRODUCT =
            Arrays.asList("guojp", "liushuai", "sunjy", "wanghx2", "wangrl", "wangst", "wangxin3", "yanxx", "yexw",
                          "yuanhm", "zhangchen", "zhangdan", "zhanggj", "yangdan", "liutt", "liuxx", "sunjing",
                          "liyuan", "zhanglw", "xuzq", "zhangyh2", "zhoujing", "wangjin", "zhudi", "lipeng", "lish",
                          "dulj", "hanhj", "chengcheng", "zhaodp", "jingfei", "lilu", "wangsy", "liuys", "qianmh",
                          "liuyang5", "zhangyu3", "wangna", "dongle", "liuyj", "guoxf", "qintd", "chenzl", "zhaomh",
                          "zhangyan", "yanbin", "wangxt2", "leizhang", "liby", "gaoyy");

    private static final List<String> MAP_DATA =
            Arrays.asList("yangyang7919-ex", "MaJinYuan-ex", "yangyang7919-ex", "zhuminjie-ex", "GuoChangQiu-ex");

    private static final List<String> DYNAMIC_INFO = List.of("bujianning10599-ex");

    private static final List<String> PLATFORM_DEV =
            Arrays.asList("bianzj", "dingjj", "hebing", "kangyr", "ligj", "liangyk", "liuwd", "wangxin2", "zhaokui",
                          "yanxin", "zhangtong", "jizc", "kedi", "liuxl", "gaohp", "wangsl", "gaoting", "xuyf", "xuyq",
                          "dongpeng", "liudw", "miaozhuang", "liuzc", "lixj", "zhaoyh3", "wangjd3", "mengyb",
                          "zhanglp2", "yuzq", "lilr", "lixin2", "haoql", "haoql", "jianghy", "lixin2", "qinqy", "liutm",
                          "mengyb", "chenxl4", "yuzq", "zhanglp2", "haopd", "wuqi2", "shecw", "zhaozh", "zhanghe2",
                          "shism", "zhaojl2", "wanliang", "zhanhk", "lipeng3");

    private static final List<String> ENGINE =
            Arrays.asList("sunwenjun-ex", "wanghui8808-ex", "wutianxing10118-ex", "yaoyuan7895", "zhaoqianqian5946-ex",
                          "cuizhan10786-ex", "liangzhiwei-ex", "wangyl", "yuanye10135-ex", "zhumj", "zhuhongbo-ex",
                          "qilin7938-ex", "zhangrenqiang7928-ex", "zhangzz", "wangwenda7927-ex", "liyang6187-ex",
                          "liyunfei7936-ex", "yaoyuan7895-ex", "liumingyu7926-ex", "yangyafei7940-ex",
                          "zhanggaoxiong7929-ex", "zhangzizhong-ex", "wangxue5943-ex", "turuolan7946-ex",
                          "panjie7884-ex", "zhuhb", "wutianxing10118-ex", "sunwenjun-ex", "lichu7468-ex",
                          "songqingzhen7903-ex", "pansongqiang9186-ex", "niushuli7902-ex", "liuchunling7935-ex",
                          "gengxianzhi9131-ex", "renzhigang7883-ex", "wanghui8808-ex", "wangfan7891-ex", "yaoyuan",
                          "zhengkf", "wangyunli-ex", "liyang8562-ex", "zhanghongjun8181-ex", "tianfuyuan7886-ex",
                          "luxingyu7876-ex", "wangshaojie10340-ex", "luxingyu7876-ex", "jutianhang7887-ex",
                          "yaoyuan7895-ex", "zhanggx", "juth", "sunqiunan7898-ex", "liudezhen7944-ex", "zhengkaifa-ex",
                          "liyang8680-ex");

    private static final List<String> TEST =
            Arrays.asList("duanlm", "gaoss", "qinyu", "wangfan", "wangrj", "zhangml", "wangjh2", "zhangxin4", "zhangtt",
                          "wangdi", "yangsy", "zhaiying", "qijh", "liang");

    private static final List<String> HMI_DEV =
            Arrays.asList("chenxl", "chengmeng2", "fengyj", "gaoming", "gaoming2", "guandw", "hedj", "lidy2", "liujy4",
                          "maqiang", "yaofl", "qubing2", "sunjq", "sunxj", "wangping2", "zhangjl", "zhanglei2",
                          "zhangxin82157", "zhaoyc", "zhoujian3", "zhouxx", "mengyue", "wangping", "qizj", "wangzhuo",
                          "guandw", "hugl", "fengyj", "wangjj", "sunyu", "wangjw", "sunzp2", "liyang3", "liucy2",
                          "guch");

    private static final List<String> PM = Arrays.asList("chenrj", "liuzq2", "wangbo", "xigf", "congtao", "dongduo");

    private static final List<String> BUSINESS = List.of("zhanglh");

    private static final Map<String, List<String>> PEOPLE_MAP = new HashMap<>() {{
        put("业务层", OPERATION);
        put("产品", PRODUCT);
        put("地图数据", MAP_DATA);
        put("平台研发", PLATFORM_DEV);
        put("引擎", ENGINE);
        put("测试", TEST);
        put("终端研发", HMI_DEV);
        put("项目", PM);
        put("动态信息", DYNAMIC_INFO);
        put("商务", BUSINESS);
    }};


    public static String getKeyFromValue(String value) {
        for (Map.Entry<String, List<String>> entry : PEOPLE_MAP.entrySet()) {
            if (entry.getValue().contains(value)) {
                return entry.getKey();
            }
        }
        return "";
    }

    private static List<List<String>> processToday(String fileName) {
        List<List<String>> data = readData(fileName);
        data.get(0).set(3, "所属");
        data.get(0).set(5, "Bug等级");

        data.sort(Comparator.comparing((List<String> row) -> row.get(0)).reversed());

        return data;
    }

    private static void outputXLSX(String fileName, List<List<String>> data) {
        @SuppressWarnings("resource")
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        fileName = fileName + ".xlsx";

        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < data.get(i).size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(data.get(i).get(j));
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found");
        } catch (IOException e) {
            System.err.println("I/O Problem");
        }
    }

    public static void exportToCSV(List<List<String>> data, String fileName) {
        try (PrintWriter writer = new PrintWriter("新" + fileName)) {
            for (List<String> row : data) {
                writer.println(String.join(",", row));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<List<String>> processYesterday(String fileName, List<List<String>> todayData) {
        List<List<String>> yesterdayData = readData(fileName);

        int devCount = 0;
        int NGCount = 0;
        int newBothDayCount = 0;
        int reopenCount = 0;
        int devACount = 0;
        int devBCount = 0;
        int devCCount = 0;
        int newACount = 0;
        int newBCount = 0;
        int newCCount = 0;
        int devHMICount = 0;
        int devOpCount = 0;
        int devPlatformCount = 0;

        for (List<String> todayRow : todayData) {
            boolean NEW = true;
            for (List<String> yesterdayRow : yesterdayData) {

                // 今天有 昨天也有
                if (Objects.equals(todayRow.get(0), yesterdayRow.get(0))) {
                    todayRow.add(1, "Still Exists");
                    todayRow.add(3, yesterdayRow.get(1));

                    NEW = false;
                    break;
                }
            }

            if (NEW) {
                todayRow.add(1, "NONE");
                todayRow.add(3, "NONE");
            }

            String existBothDay = todayRow.get(1);
            String todayStatus = todayRow.get(2);
            String yesterdayStatus = todayRow.get(3);

            if (todayStatus.equals(yesterdayStatus)) {
                todayRow.add(4, "一样");
            } else {
                todayRow.add(4, "不一样");
            }

            String belong = todayRow.get(6);
            String bugRank = todayRow.get(8);

            if ("".equals(belong)) {
                System.out.println(todayRow.get(5) + " 新增");
            }

            // 今天修改完成状态，昨天不是修改完成状态
            // 今天是发布状态，昨天不是修改完成状态 并且昨天不是发布状态
            if ((todayStatus.equals("修改完成") && !yesterdayStatus.equals("修改完成")) ||
                (todayStatus.equals("发布") && !yesterdayStatus.equals("修改完成") &&
                 !yesterdayStatus.equals("发布"))) {
                devCount++;

                // TODO: print here to know who doesn't have belonging

                // System.out.println(belong);
                switch (belong) {
                    case "业务层" -> devOpCount++;
                    case "终端研发" -> devHMICount++;
                    case "平台研发" -> devPlatformCount++;
                }

                switch (bugRank) {
                    case "A" -> devACount++;
                    case "B" -> devBCount++;
                    case "C" -> devCCount++;
                }
            }

            if (todayStatus.equals("NG") && !yesterdayStatus.equals("NG")) {
                NGCount++;
            }

            if (todayStatus.equals("重开") && !yesterdayStatus.equals("重开")) {
                reopenCount++;
            }

            if (existBothDay.equals("NONE") && !todayStatus.equals("重开")) {
                newBothDayCount++;
                switch (bugRank) {
                    case "A" -> newACount++;
                    case "B" -> newBCount++;
                    case "C" -> newCCount++;
                }
            }

        }

        // 留下的就是关闭的
        Iterator<List<String>> it = yesterdayData.iterator();
        while (it.hasNext()) {
            List<String> yesterdayRow = it.next();
            for (List<String> todayRow : todayData) {
                if (Objects.equals(todayRow.get(0), yesterdayRow.get(0))) {
                    it.remove();
                    break;
                }
            }
        }

        // 昨天的数据
        int closedACount = 0;
        int closedBCount = 0;
        int closedCCount = 0;
        for (List<String> subList : yesterdayData) {
            String bugRank = subList.get(5);
            if ("A".equals(bugRank)) {
                closedACount++;
            } else if ("B".equals(bugRank)) {
                closedBCount++;
            } else if ("C".equals(bugRank)) {
                closedCCount++;
            }
        }

        System.out.println(
                "关闭：" + yesterdayData.size() + " (A " + closedACount + "/B " + closedBCount + "/C " + closedCCount +
                ")");
        System.out.println(
                "新增：" + newBothDayCount + " (A " + newACount + "/B " + newBCount + "/C " + newCCount + ")");

        System.out.println(
                "本日修改完成：" + devCount + " (" + devHMICount + "/" + devOpCount + "/" + devPlatformCount + ") (A " +
                devACount + "/B " + devBCount + "/C " + devCCount + ")");

        System.out.println("NG " + NGCount + "/重开 " + reopenCount);

        return todayData;
    }

    /**
     * To read data from csv file
     *
     * @param fileName file name
     * @return the 2D array of data in cvs
     */
    private static List<List<String>> readData(String fileName) {
        List<List<String>> data = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(fileName);

            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.newFormat('|'));
            List<CSVRecord> records = csvParser.getRecords();

            if (records.get(0).size() < 2) {
                fileReader = new FileReader(fileName);
                csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
                records = csvParser.getRecords();
            }

            CSVRecord header = records.get(0);
            List<String> headerList = header.stream().toList();
            final int bugIDPos = headerList.indexOf("问题ID");
            int fujiIDPos = 0;

            if (headerList.contains("父级ID")) {
                fujiIDPos = headerList.indexOf("父级ID");
            }

            for (CSVRecord record : records) {
                List<String> row = new ArrayList<>();
                for (String field : record) {
                    row.add(field);
                }

                row.remove(bugIDPos);
                if (fujiIDPos != 0) {
                    row.remove(fujiIDPos - 1);
                }

                row.add(3, getKeyFromValue(row.get(2)));

                data.add(row);
            }
        } catch (IOException e) {
            System.err.println("No Such File: " + fileName);
        }

        return data;
    }

    private static void summary(List<List<String>> newData) {
        int ACount = 0;
        int BCount = 0;
        int CCount = 0;

        int HMIAnalyzingCount = 0;
        int HMIAnalyzingACount = 0;
        int HMIAnalyzingBCount = 0;
        int HMIAnalyzingCCount = 0;
        int HMIDoneCount = 0;

        int OpAnalyzingCount = 0;
        int OpAnalyzingACount = 0;
        int OpAnalyzingBCount = 0;
        int OpAnalyzingCCount = 0;
        int OpDoneCount = 0;

        int PlatAnalyzingCount = 0;
        int PlatAnalyzingACount = 0;
        int PlatAnalyzingBCount = 0;
        int PlatAnalyzingCCount = 0;
        int PlatDoneCount = 0;

        int ProductCount = 0;
        int ProductACount = 0;
        int ProductBCount = 0;
        int ProductCCount = 0;

        int EngineAnalyzingCount = 0;
        int EngineAnalyzingACount = 0;
        int EngineAnalyzingBCount = 0;
        int EngineAnalyzingCCount = 0;
        int EngineDoneCount = 0;

        int MapAnalyzingCount = 0;
        int MapAnalyzingACount = 0;
        int MapAnalyzingBCount = 0;
        int MapAnalyzingCCount = 0;
        int MapDoneCount = 0;

        int DIAnalyzingCount = 0;
        int DIAnalyzingACount = 0;
        int DIAnalyzingBCount = 0;
        int DIAnalyzingCCount = 0;
        int DIDoneCount = 0;

        int ProjectCount = 0;
        int ProjectACount = 0;
        int ProjectBCount = 0;
        int ProjectCCount = 0;

        int BusinessCount = 0;
        int BusinessACount = 0;
        int BusinessBCount = 0;
        int BusinessCCount = 0;

        int TestPendingCount = 0;
        int TestOtherCount = 0;
        for (List<String> subList : newData) {
            String status = subList.get(2);
            String belonging = subList.get(6);
            String bugRank = subList.get(8);

            if ("终端研发".equals(belonging)) {
                if ("修改完成".equals(status)) {
                    HMIDoneCount++;
                } else {
                    if ("A".equals(bugRank)) {
                        HMIAnalyzingACount++;
                    } else if ("B".equals(bugRank)) {
                        HMIAnalyzingBCount++;
                    } else if ("C".equals(bugRank)) {
                        HMIAnalyzingCCount++;
                    }
                    HMIAnalyzingCount++;
                }
            }

            if ("业务层".equals(belonging)) {
                if ("修改完成".equals(status)) {
                    OpDoneCount++;
                } else {
                    if ("A".equals(bugRank)) {
                        OpAnalyzingACount++;
                    } else if ("B".equals(bugRank)) {
                        OpAnalyzingBCount++;
                    } else if ("C".equals(bugRank)) {
                        OpAnalyzingCCount++;
                    }
                    OpAnalyzingCount++;
                }
            }

            if ("平台研发".equals(belonging)) {
                if (status.equals("修改完成")) {
                    PlatDoneCount++;
                } else {
                    if ("A".equals(bugRank)) {
                        PlatAnalyzingACount++;
                    } else if ("B".equals(bugRank)) {
                        PlatAnalyzingBCount++;
                    } else if ("C".equals(bugRank)) {
                        PlatAnalyzingCCount++;
                    }
                    PlatAnalyzingCount++;
                }
            }

            if ("产品".equals(belonging)) {
                if ("A".equals(bugRank)) {
                    ProductACount++;
                } else if ("B".equals(bugRank)) {
                    ProductBCount++;
                } else if ("C".equals(bugRank)) {
                    ProductCCount++;
                }
                ProductCount++;
            }

            if ("引擎".equals(belonging)) {
                if ("修改完成".equals(status)) {
                    EngineDoneCount++;
                } else {
                    if ("A".equals(bugRank)) {
                        EngineAnalyzingACount++;
                    } else if ("B".equals(bugRank)) {
                        EngineAnalyzingBCount++;
                    } else if ("C".equals(bugRank)) {
                        EngineAnalyzingCCount++;
                    }
                    EngineAnalyzingCount++;
                }
            }

            if ("地图数据".equals(belonging)) {
                if ("修改完成".equals(status)) {
                    MapDoneCount++;
                } else {
                    if ("A".equals(bugRank)) {
                        MapAnalyzingACount++;
                    } else if ("B".equals(bugRank)) {
                        MapAnalyzingBCount++;
                    } else if ("C".equals(bugRank)) {
                        MapAnalyzingCCount++;
                    }
                    MapAnalyzingCount++;
                }
            }

            if ("动态信息".equals(belonging)) {
                if ("修改完成".equals(status)) {
                    DIDoneCount++;
                } else {
                    if ("A".equals(bugRank)) {
                        DIAnalyzingACount++;
                    } else if ("B".equals(bugRank)) {
                        DIAnalyzingBCount++;
                    } else if ("C".equals(bugRank)) {
                        DIAnalyzingCCount++;
                    }
                    DIAnalyzingCount++;
                }
            }

            if ("项目".equals(belonging)) {
                if ("A".equals(bugRank)) {
                    ProjectACount++;
                } else if ("B".equals(bugRank)) {
                    ProjectBCount++;
                } else if ("C".equals(bugRank)) {
                    ProjectCCount++;
                }
                ProjectCount++;
            }

            if ("商务".equals(belonging)) {
                if ("A".equals(bugRank)) {
                    BusinessACount++;
                } else if ("B".equals(bugRank)) {
                    BusinessBCount++;
                } else if ("C".equals(bugRank)) {
                    BusinessCCount++;
                }
                BusinessCount++;
            }

            if ("测试".equals(belonging)) {
                if (("发布".equals(status))) {
                    TestPendingCount++;
                } else {
                    TestOtherCount++;
                }
            }


            if ("A".equals(bugRank)) {
                ACount++;
            } else if ("B".equals(bugRank)) {
                BCount++;
            } else if ("C".equals(bugRank)) {
                CCount++;
            }

        }

        System.out.println(
                "整体残件：" + (newData.size() - 1) + " (A " + ACount + "/B " + BCount + "/C " + CCount + ")");

        int fixableCount =
                HMIAnalyzingCount + OpAnalyzingCount + PlatAnalyzingCount + ProductCount + EngineAnalyzingCount +
                MapAnalyzingCount + DIAnalyzingCount;
        int fixableACount =
                HMIAnalyzingACount + OpAnalyzingACount + PlatAnalyzingACount + ProductACount + EngineAnalyzingACount +
                MapAnalyzingACount + DIAnalyzingACount;
        int fixableBCount =
                HMIAnalyzingBCount + OpAnalyzingBCount + PlatAnalyzingBCount + ProductBCount + EngineAnalyzingBCount +
                MapAnalyzingBCount + DIAnalyzingBCount;
        int fixableCCount =
                HMIAnalyzingCCount + OpAnalyzingCCount + PlatAnalyzingCCount + ProductCCount + EngineAnalyzingCCount +
                MapAnalyzingCCount + DIAnalyzingCCount;
        System.out.println(
                "可修改残件：" + fixableCount + " (A " + fixableACount + "/B " + fixableBCount + "/C " + fixableCCount +
                ")");

        System.out.println(
                "HMI：" + HMIAnalyzingCount + " (A " + HMIAnalyzingACount + "/B " + HMIAnalyzingBCount + "/C " +
                HMIAnalyzingCCount + ")");

        System.out.println(
                "业务层：" + OpAnalyzingCount + " (A " + OpAnalyzingACount + "/B " + OpAnalyzingBCount + "/C " +
                OpAnalyzingCCount + ")");

        System.out.println(
                "平台研发：" + PlatAnalyzingCount + " (A " + PlatAnalyzingACount + "/B " + PlatAnalyzingBCount + "/C " +
                PlatAnalyzingCCount + ")");

        System.out.println(
                "产品：" + ProductCount + " (A " + ProductACount + "/B " + ProductBCount + "/C " + ProductCCount + ")");

        System.out.println(
                "引擎：" + EngineAnalyzingCount + " (A " + EngineAnalyzingACount + "/B " + EngineAnalyzingBCount +
                "/C " + EngineAnalyzingCCount + ")");

        System.out.println(
                "地图数据：" + MapAnalyzingCount + " (A " + MapAnalyzingACount + "/B " + MapAnalyzingBCount + "/C " +
                MapAnalyzingCCount + ")");

        System.out.println(
                "动态信息：" + DIAnalyzingCount + " (A " + DIAnalyzingACount + "/B " + DIAnalyzingBCount + "/C " +
                DIAnalyzingCCount + ")");

        System.out.println(
                "项目：" + ProjectCount + " (A " + ProjectACount + "/B " + ProjectBCount + "/C " + ProjectCCount + ")");

        System.out.println(
                "商务：" + BusinessCount + " (A " + BusinessACount + "/B " + BusinessBCount + "/C " + BusinessCCount +
                ")");

        System.out.println("HMI完成: " + HMIDoneCount);
        System.out.println("业务层完成: " + OpDoneCount);
        System.out.println("平台研发完成: " + PlatDoneCount);
        System.out.println("引擎完成: " + EngineDoneCount);
        System.out.println("地图数据完成: " + MapDoneCount);
        System.out.println("动态信息完成: " + DIDoneCount);
        System.out.println("测试待效确: " + TestPendingCount + " 其他：" + TestOtherCount);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("java remainBug <today> <yesterday>");
            exit(1);
        }

        String todayFile = args[0];
        String yesterdayFile = args[1];

        List<List<String>> todayData = processToday(todayFile);

        List<List<String>> newData = processYesterday(yesterdayFile, todayData);

        newData.get(0).set(1, "两日票存");
        newData.get(0).set(2, "今日状态");
        newData.get(0).set(3, "昨日状态");
        newData.get(0).set(4, "状态对比");

        summary(newData);

        //        outputXLSX(todayFile, newData);

        exportToCSV(newData, todayFile);

    }

}