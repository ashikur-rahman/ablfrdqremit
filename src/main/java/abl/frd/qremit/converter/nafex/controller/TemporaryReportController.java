package abl.frd.qremit.converter.nafex.controller;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import abl.frd.qremit.converter.nafex.helper.MyUserDetails;
import abl.frd.qremit.converter.nafex.service.TemporaryReportService;

@RestController
public class TemporaryReportController {
    @Autowired
    TemporaryReportService temporaryReportService;

    public TemporaryReportController(){
    }

    @GetMapping("/generateTemporaryReport")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> generateTemporaryReport(@AuthenticationPrincipal MyUserDetails userDetails){
        Map<String, Object> resp = new HashMap<>();
        resp = temporaryReportService.processTemporaryReport();
        return ResponseEntity.ok(resp);
    }

}
