package org.joychou.controller;

import org.joychou.security.SecurityUtil;
import org.joychou.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class CommandInject {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Executes a shell command to list the contents of a directory.
     *
     * <p>This GET endpoint is mapped to {@code /codeinject}. It constructs a shell command by appending the
     * provided {@code filepath} to {@code ls -la} and executes it using a {@link ProcessBuilder}. The combined output
     * (standard and error) of the command is then returned as a string.</p>
     *
     * <p><strong>Security Note:</strong> The {@code filepath} parameter is used directly in the shell command without
     * sanitization, which may lead to command injection vulnerabilities if untrusted input is provided.</p>
     *
     * @param filepath the directory path whose contents are to be listed
     * @return the output of the directory listing command as a string
     * @throws IOException if an error occurs while starting the process
     */
    @GetMapping("/codeinject")
    public String codeInject(String filepath) throws IOException {

        String[] cmdList = new String[]{"sh", "-c", "ls -la " + filepath};
        ProcessBuilder builder = new ProcessBuilder(cmdList);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        return WebUtils.convertStreamToString(process.getInputStream());
    }

    /**
     * Executes a shell command using 'curl' to request the host specified in the HTTP request header.
     * <p>
     * The method retrieves the 'host' header from the provided HttpServletRequest, logs its value, and
     * constructs a shell command to perform a curl request to that host. The output of the executed
     * command is returned as a string.
     * </p>
     *
     * @param request the HTTP request containing the 'host' header
     * @return the output of the curl command as a String
     * @throws IOException if an I/O error occurs during command execution
     */
    @GetMapping("/codeinject/host")
    public String codeInjectHost(HttpServletRequest request) throws IOException {

        String host = request.getHeader("host");
        logger.info(host);
        String[] cmdList = new String[]{"sh", "-c", "curl " + host};
        ProcessBuilder builder = new ProcessBuilder(cmdList);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        return WebUtils.convertStreamToString(process.getInputStream());
    }

    /**
     * Executes a shell command to list a directory's contents using a security-filtered file path.
     *
     * <p>The provided file path is first filtered using a security utility. If the filter returns null,
     * indicating a potential security violation, a warning message is returned. Otherwise, the method
     * constructs and executes the command "ls -la" on the filtered path, capturing and returning the
     * command's output.</p>
     *
     * @param filepath the file path to be filtered and used for the directory listing
     * @return the output of the directory listing command, or a security violation warning message if filtering fails
     * @throws IOException if an error occurs while starting the process
     */
    @GetMapping("/codeinject/sec")
    public String codeInjectSec(String filepath) throws IOException {
        String filterFilePath = SecurityUtil.cmdFilter(filepath);
        if (null == filterFilePath) {
            return "Bad boy. I got u.";
        }
        String[] cmdList = new String[]{"sh", "-c", "ls -la " + filterFilePath};
        ProcessBuilder builder = new ProcessBuilder(cmdList);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        return WebUtils.convertStreamToString(process.getInputStream());
    }
}
