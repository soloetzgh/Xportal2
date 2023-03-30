package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.ConsoleFile;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.CryptographerMin;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.text.CharacterIterator;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Part;
import org.apache.log4j.Logger;

public class ConsoleFilesDao {

    private static final GeneralUtility utility = new GeneralUtility();
    private static final Gson gson = new Gson();
    private static final Config config = new Config();

    private static final Logger log = Logger.getLogger(ConsoleFilesDao.class.getName());
    private static final Map<String, String> banks = new HashMap<>();
    private static final Map<String, String> terminals = new HashMap<>();
    private static final String consoleFilesPath;

    static {
        consoleFilesPath = config.getProperty("CONSOLE_FILES_PATH");
    }

    public static void main(String[] args) throws IOException {
        String json = "{\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"status\":\"locked\",\"service\":\"momoupdate\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[1],[17]|0060000290:0067620000000010,[2000000000000048]|00000000000000000002:0069900596470004,[2000000000000049],[2000000000000053]|ALL,[2000000000000054],[2000000000000060]|000,[2000000000000062]|ALL,[2000000000000063]|KNUST,[2000000000000064]|ALL,[22]|000,[2500000000000049]|2,[2500000000000050]|ALL,[2500000000000053]|3,[29],[50]|2,[71]|0230010002,[91]|ALL\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[0],[1],[2],[3],[4],[5],[6],[7],[8],[9],[10],[11],[20],[21],[24],[25],[26],[27],[28],[30],[31],[33],[35],[37],[39],[40],[41],[43],[44]\",\"userName\":\"Eugene.Arthur\",\"ClientId\":\"\",\"user_id\":\"9000000000000490\",\"pageNumber\":1,\"rowsPerPage\":1,\"uniqueTransId\":\"[{\\\"reference\\\":\\\"09FG03121446546403016\\\",\\\"clientid\\\":\\\"\\\"}]\",\"trans_code\":\"\",\"trans_status\":\"\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"card_num\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"0:0:0:0:0:0:0:1\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"options\":{}}";
        System.out.println(new ConsoleFilesDao().listFilesUsingFilesList("\\\\172.16.42.7\\d\\SettlementFiles\\021\\", "2021-11-11 23:59", "2021-11-18 23:59"));
        System.exit(0);
    }

    public List<ConsoleFile> getConsoleFiles(String jsonString) {

        log.info("Console Files trx request received >> " + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String bank = apiData.getBank_code();

        String user_code = apiData.getUserCode();

        String type_id = apiData.getType_id();
        final String service = apiData.getService();
        List<String> roleList = new ArrayList<>();

        List<ConsoleFile> records = new ArrayList<>();

        String userRoles = "";

        if (bank == null || bank.trim().isEmpty() || bank.trim().equals("000")) {
            bank = "ALL";
        }

        if (!type_id.isEmpty()) {

            if (type_id.contains("[0]") || type_id.contains("[6]")) {

            } else if (type_id.contains("[53]")) {
                bank = utility.getRoleParameters("[123]", user_code);

            } else {
                return records;
            }
        } else {
            return records;
        }

        records = listFilesUsingFilesList(consoleFilesPath + bank, start_dt, end_dt);

        return records;
    }

    public String[] saveConsoleFiles(String jsonString, Part filePart) {

        String[] resp = {"01", "An Error Occured"};
        log.info("Console Files trx request received >> " + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String bank = apiData.getBank_code();

        String user_code = apiData.getUserCode();

        String type_id = apiData.getType_id();
        final String service = apiData.getService();
        List<String> roleList = new ArrayList<>();

        List<ConsoleFile> records = new ArrayList<>();

        String userRoles = "";

        if (bank == null || bank.trim().isEmpty() || bank.trim().equals("000")) {
            bank = "ALL";
        }

        if (!type_id.isEmpty()) {

            if (!utility.getRoleParameters("[124]", apiData.getUserCode()).isEmpty()) {

            } else {
                resp[1] = "Authorization Required";
                return resp;
            }
        } else {
            resp[1] = "Authorization Required";
            return resp;
        }

        FileOutputStream outputStream = null;
        InputStream fileContent = null;
        try {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            String fileDirectory = consoleFilesPath + bank;

            File directory = new File(fileDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String filePath = fileDirectory + File.separator + fileName;
            outputStream = new FileOutputStream(new File(filePath));

            fileContent = filePart.getInputStream();

            int readBytes = 0;
            byte[] readArray = new byte[1024];

            while ((readBytes = fileContent.read(readArray)) != -1) {
                outputStream.write(readArray, 0, readBytes);
            }
            File file = new File(filePath);
            resp[0] = file.exists() ? "00" : "01";
            resp[1] = file.exists() ? "File Uploaded Successfully" : "File Upload Failed";
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);

        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

            }
            if (fileContent != null) {
                try {
                    fileContent.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

            }
        }

        return resp;
    }

    public List<ConsoleFile> listFilesUsingFilesList(String dir, String start_dt, String end_dt) {
        List<ConsoleFile> fileList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        File directoryPath = new File(dir);

        if (directoryPath.exists()) {
            try {
                Date dt1 = sdf2.parse(start_dt);
                Date dt2 = sdf2.parse(end_dt);
                final long d1 = dt1.getTime();
                final long d2 = dt2.getTime();

                FileFilter fileFilter = file -> {
                    long lm = file.lastModified();
                    String name = file.getName().toLowerCase();
                    return lm >= d1 && lm <= d2 && name.endsWith(".zip");
                };
                File filesList[] = directoryPath.listFiles(fileFilter);
                Arrays.sort(filesList, Comparator.comparingLong(File::lastModified).reversed());

                for (File file : filesList) {
                    ConsoleFile cf = new ConsoleFile();
                    try {
                        cf.setFileName(file.getName());
                        cf.setFileSize(humanReadableByteCountBin(file.length()));
                        cf.setFileDate(sdf.format(file.lastModified()));
                        cf.setFilePath(CryptographerMin.encryptEncodeToHex(file.getAbsolutePath(), ""));
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                    fileList.add(cf);
                }
            } catch (Exception et) {
                log.error(et.getMessage(), et);
            }
        } else {
            log.info("Console Files Directory: " + dir + "doesn''t exist.");
        }
        return fileList;
    }

    public static String humanReadableByteCountBin(long bytes) {
        long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024) {
            return bytes + " B";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(bytes);
        return String.format("%.1f %ciB", value / 1024.0, ci.current());
    }

}
