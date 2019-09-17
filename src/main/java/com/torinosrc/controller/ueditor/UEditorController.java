
package com.torinosrc.controller.ueditor;

import com.baidu.ueditor.ActionEnter;
import com.torinosrc.service.sysuser.SysUserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * <b><code>UEditorController</code></b>
 * <p/>
 * UEditorController
 * <p/>
 * <b>Creation Time:</b> 2017/01/17 21:18.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-rs 1.0.0
 */
@Api(value = "[Torino Source]UEdiotr统一接入接口",tags = "[Torino Source]UEdiotr统一接入接口",description = "")
@RestController
@RequestMapping(value = "/ueditor")
public class UEditorController {
    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(UEditorController.class);

    /** The service. */
    @Autowired
    private SysUserService sysUserService;

    public static String ueditorConfig;

    @Value("${ueditor.config}")
    public void setUeditorConfig(String ueditorConfig) {
        this.ueditorConfig = ueditorConfig;
    }

        @ApiOperation(value = "[Torino Source]UEditor中dispatch接口", notes = "UEditor中dispatch接口")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/dispatch", method={ RequestMethod.GET, RequestMethod.POST })
    public void config(HttpServletRequest request, HttpServletResponse response,
                       @ApiParam(value = "action", defaultValue = "config", required = true) @RequestParam(value = "action", defaultValue = "", required = true) String action) {
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        response.setHeader("Content-Type" , "text/html");
            PrintWriter writer = null;
        try {
            String a = request.getRequestURI();
            String exec = new ActionEnter(request, rootPath).exec();
            writer = response.getWriter();
            writer.write(exec);
            writer.flush();
        }catch (Throwable t) {
            String error = "Failed to call UEditor!";
            LOG.error(error, t);
        } finally {
            if (writer == null) {
                writer.close();
            }
        }
    }



}
