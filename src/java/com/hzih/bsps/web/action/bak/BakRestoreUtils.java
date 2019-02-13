package com.hzih.bsps.web.action.bak;

import com.hzih.bsps.utils.StringContext;
import com.hzih.bsps.web.action.mysql.MysqlBakUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 14-7-21.
 */
public class BakRestoreUtils {
    private static Logger logger = Logger.getLogger(BakRestoreUtils.class);

    public static boolean bak(String path) {
        boolean fg = false;
        try {
            fg = MysqlBakUtils.backup();
        } catch (IOException e) {
            logger.info(e.getMessage(), e);
            return false;
        }
        if (fg) {
            if (path.endsWith(File.separator)) {
                TarUtils tarUtils = new TarUtils();
                //单个文件打包
//                    tarUtils.execute(path + path, path + X509Context.bak_file + ".tar", path + X509Context.bak_file);
                List<String> array = new ArrayList<>();
                array.add(StringContext.config_path);
                array.add(StringContext.license_path);
                array.add(StringContext.iptables_rules_files);
                array.add(StringContext.server_path);
                array.add(StringContext.mysql_bak_sql);
                tarUtils.execute(array, path + StringContext.bak_file + ".tar", path + StringContext.bak_file);
            } else {
                path += File.separator;
                TarUtils tarUtils = new TarUtils();
                //单个文件打包
//                    tarUtils.execute(path, path + X509Context.bak_file + ".tar", path + X509Context.bak_file);
                List<String> array = new ArrayList<>();
                array.add(StringContext.config_path);
                array.add(StringContext.license_path);
                array.add(StringContext.iptables_rules_files);
                array.add(StringContext.server_path);
                array.add(StringContext.mysql_bak_sql);
                //多个文件打包
                tarUtils.execute(array, path + StringContext.bak_file + ".tar", path + StringContext.bak_file);
            }
            File f = new File(path + StringContext.bak_file);
            if (f.exists() && f.length() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean bakRestore(String path) {
        if (path.endsWith(File.separator)) {
            GZip.unTargzFile(path + StringContext.bak_file, path);
            try {
                MysqlBakUtils.recover();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                return false;
            }
            return true;
        } else {
            path += File.separator;
            GZip.unTargzFile(path + StringContext.bak_file, path);
            try {
                MysqlBakUtils.recover();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                return false;
            }
            return true;
        }
    }
}
