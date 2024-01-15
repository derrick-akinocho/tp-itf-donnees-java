package com.ipi.javaio.web;

import com.ipi.javaio.export.SalarieAideADomicileExportXlsxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("export")
public class SalarieAideADomicileExportXlsxController {

    @Autowired
    private final SalarieAideADomicileExportXlsxService salarieAideADomicileExportXlsxService;

    public SalarieAideADomicileExportXlsxController(SalarieAideADomicileExportXlsxService salarieAideADomicileExportXlsxService) {
        this.salarieAideADomicileExportXlsxService = salarieAideADomicileExportXlsxService;
    }

    @GetMapping("/salarieAideADomicile/xlsx")
    public void salarieAideADomicileXlsx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export_salaries.xlsx\"");
        this.salarieAideADomicileExportXlsxService.export(response.getOutputStream());
    }
}
