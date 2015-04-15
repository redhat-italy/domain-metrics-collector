//package objecttocsv;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.StringWriter;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import com.opencsv.CSVReader;
//import com.opencsv.CSVWriter;
//import com.opencsv.bean.CsvToBean;
//import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
//import com.redhat.it.customers.dmc.core.dto.collector.qdk.raw.AppDMRRawQueryDataKey;
//
//public class OpenCSVParserExample {
//
//    public static void main(String[] args) throws IOException {
//
//        List<AppDMRRawQueryDataKey> emps = parseCSVFileLineByLine();
//        System.out.println("**********");
//        parseCSVFileAsList();
//        System.out.println("**********");
//        parseCSVToBeanList();
//        System.out.println("**********");
//        writeCSVData(emps);
//    }
//
//    private static void parseCSVToBeanList() throws IOException {
//
//        HeaderColumnNameTranslateMappingStrategy<AppDMRRawQueryDataKey> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<AppDMRRawQueryDataKey>();
//        beanStrategy.setType(AppDMRRawQueryDataKey.class);
//
//        Map<String, String> columnMapping = new HashMap<String, String>();
//        columnMapping.put("ID", "id");
//        columnMapping.put("Name", "name");
//        columnMapping.put("Role", "role");
//        // columnMapping.put("Salary", "salary");
//
//        beanStrategy.setColumnMapping(columnMapping);
//
//        CsvToBean<AppDMRRawQueryDataKey> csvToBean = new CsvToBean<AppDMRRawQueryDataKey>();
//        CSVReader reader = new CSVReader(new FileReader("employees.csv"));
//        List<AppDMRRawQueryDataKey> emps = csvToBean
//                .parse(beanStrategy, reader);
//        System.out.println(emps);
//    }
//
//    private static void writeCSVData(List<AppDMRRawQueryDataKey> emps)
//            throws IOException {
//        StringWriter writer = new StringWriter();
//        CSVWriter csvWriter = new CSVWriter(writer, '#');
//        List<String[]> data = toStringArray(emps);
//        csvWriter.writeAll(data);
//        csvWriter.close();
//        System.out.println(writer);
//    }
//
//    private static List<String[]> toStringArray(List<AppDMRRawQueryDataKey> emps) {
//        List<String[]> records = new ArrayList<String[]>();
//        // add header record
//        records.add(new String[] { "ID", "Name", "Role", "Salary" });
//        Iterator<AppDMRRawQueryDataKey> it = emps.iterator();
//        while (it.hasNext()) {
//            AppDMRRawQueryDataKey emp = it.next();
//            records.add(new String[] { emp.getId(), emp.getName(),
//                    emp.getRole(), emp.getSalary() });
//        }
//        return records;
//    }
//
//    private static List<AppDMRRawQueryDataKey> parseCSVFileLineByLine()
//            throws IOException {
//        // create CSVReader object
//        CSVReader reader = new CSVReader(new FileReader("employees.csv"), ',');
//
//        List<AppDMRRawQueryDataKey> emps = new ArrayList<AppDMRRawQueryDataKey>();
//        // read line by line
//        String[] record = null;
//        // skip header row
//        reader.readNext();
//
//        while ((record = reader.readNext()) != null) {
//            AppDMRRawQueryDataKey emp = new AppDMRRawQueryDataKey();
//            emp.setId(record[0]);
//            emp.setName(record[1]);
//            emp.setRole(record[2]);
//            emp.setSalary(record[3]);
//            emps.add(emp);
//        }
//
//        reader.close();
//
//        System.out.println(emps);
//        return emps;
//    }
//
//    private static void parseCSVFileAsList() throws IOException {
//        // create CSVReader object
//        CSVReader reader = new CSVReader(new FileReader("employees.csv"), ',');
//
//        List<AppDMRRawQueryDataKey> emps = new ArrayList<AppDMRRawQueryDataKey>();
//        // read all lines at once
//        List<String[]> records = reader.readAll();
//
//        Iterator<String[]> iterator = records.iterator();
//        // skip header row
//        iterator.next();
//
//        while (iterator.hasNext()) {
//            String[] record = iterator.next();
//            AppDMRRawQueryDataKey emp = new AppDMRRawQueryDataKey();
//            emp.setId(record[0]);
//            emp.setName(record[1]);
//            emp.setRole(record[2]);
//            emp.setSalary(record[3]);
//            emps.add(emp);
//        }
//
//        reader.close();
//
//        System.out.println(emps);
//    }
//
//}