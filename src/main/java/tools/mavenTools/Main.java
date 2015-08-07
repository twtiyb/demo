package tools.mavenTools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.JarFilter;

import java.io.*;
import java.util.StringTokenizer;

/**
 * @author lihzh-home
 */
public class Main {

    private final Log _log = LogFactory.getLog(Main.class);
    private PropertyHelper propHelper = new PropertyHelper("config");
    private boolean isDelete = Boolean.valueOf(propHelper.getValue("delete-installed-jar"));
    private static final String KEY_JARPATH = "jar-path";
    private static final String ENCODE = "gbk";
    private static String CMD_INSTALL_FILE;

    public static void main(String[] args) {
        Main a = new Main();
        a.testRun();
    }

    private void testRun() {
        String path = propHelper.getValue(KEY_JARPATH);
        _log.info("The path of the jars is [" + path + "].");
        File file = new File(path);
        if (!file.isDirectory()) {
            _log.warn("The path must be a directory.");
            return;
        }
        FilenameFilter filter = new JarFilter();
        File[] jarFiles = file.listFiles(filter);
        for (File jar : jarFiles) {
            installJarToMaven(jar);
            if (isDelete) {
                _log.info("Delete the original jar file [" + jar.getName() + "].");
                jar.delete();
            }
        }
    }

    private void installJarToMaven(File file) {
        String fileName = file.getName();
        String jarName = getJarName(fileName);
        StringTokenizer strToken = new StringTokenizer(jarName, "-");
        String groupId = null;
        String artifactId = null;
        String version = null;
        if (strToken.hasMoreTokens()) {
            if (groupId == null) {
                groupId = strToken.nextToken();
                if (strToken.hasMoreTokens()) {
                    artifactId = strToken.nextToken();
                    if (strToken.hasMoreTokens()) {
                        version = strToken.nextToken();
                    }
                } else {
                    version = artifactId = groupId;
                }
            }
        }
        _log.info("Jar [" + jarName + "] will be installed with the groupId=" + groupId + " ,"
                + "artifactId=" + artifactId + " , version=" + version + ".");
        executeInstall(groupId, artifactId, version, file.getPath());
    }

    private void executeInstall(String groupId, String artifactId,
                                String version, String path) {
        CMD_INSTALL_FILE = createInstallFileCMD(groupId, artifactId,
                version, path);
        String[] cmds = new String[]{"cmd", "/C", CMD_INSTALL_FILE};
        try {
            Process process = Runtime.getRuntime().exec(cmds);
            printResult(process);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printResult(Process process) throws IOException {
        InputStream is = process.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, ENCODE));
        String lineStr;
        while ((lineStr = br.readLine()) != null) {
            System.out.println(lineStr);
        }
    }

    private String createInstallFileCMD(String groupId,
                                        String artifactId, String version, String path) {
        StringBuffer sb = new StringBuffer();
        sb.append("mvn install:install-file -DgroupId=").append(groupId)
                .append(" -DartifactId=").append(artifactId)
                .append(" -Dversion=").append(version)
                .append(" -Dpackaging=jar")
                .append(" -Dfile=").append(path);
        _log.debug(sb.toString());
        return sb.toString();
    }

    private static String getJarName(String fileName) {
        int index = fileName.indexOf(".jar");
        return fileName.substring(0, index);
    }

}
