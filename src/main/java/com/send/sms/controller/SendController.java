package com.send.sms.controller;


import com.send.sms.service.SoapXmlService;
import com.send.sms.util.ConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SendController {
    @Autowired
    private SoapXmlService soapXmlService;
    @Autowired
    private ConnectionUtil connectionUtil;
    @Value("${webservice.soapUrl}")
    private String soapUrl;

    /*
    /send
    @RequestParam(name = "loginId") String loginId, @RequestParam(name = "pwd") String pwd,
                       @RequestParam(name = "mobile") String[] mobile, @RequestParam(name = "content") String content
        http://localhost:8080/send?loginId=zbjk01&pwd=cct_1234&mobile=17631089214&content=測試短信

        虛擬機：http://39.102.65.157:8081/webservice/send?loginId=zbjk01&pwd=cct_1234&mobile=17631089214&content=測試短信

    * */
    @GetMapping("/send")
    public String send(@RequestParam(name = "loginId") String loginId, @RequestParam(name = "pwd") String pwd,
                       @RequestParam(name = "mobile") String[] mobile, @RequestParam(name = "content") String content){
        //String soapUrl="http://114.247.42.150:8080/SMSG/services/SMS";
        /*String loginId = "zbjk01";
        String pwd = "cct_1234";
        String[] mobile = {"17631089214"};
        String content = "測試短信1";*/
        String soapXML = soapXmlService.getSoapXML(loginId, pwd,mobile,content);
        String s = null;
        try {
            s = connectionUtil.urlConnectionUtil(soapUrl, soapXML);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
