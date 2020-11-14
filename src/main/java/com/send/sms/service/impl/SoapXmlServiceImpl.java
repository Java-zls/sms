package com.send.sms.service.impl;


import com.send.sms.service.SoapXmlService;
import com.send.sms.util.ConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class SoapXmlServiceImpl implements SoapXmlService {

    @Autowired
    private ConnectionUtil connectionUtil;

    @Override
    public String getSoapXML(String loginId, String pwd, String[] mobile, String content) {
        String xml = connectionUtil.getXML(mobile, content);
        xml = xml.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("'", "&apos;").replaceAll("\"", "&quot;");
        String soapXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://webservice.sms.api.poweru.cn/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <ser:AddSMSList>\n" +
                "         <loginId>" + loginId + "</loginId>\n" +
                "         <passWord>" + pwd + "</passWord>\n" +
                "         <XML>" + xml + "</XML>\n" +
                "      </ser:AddSMSList>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        return soapXML;
    }
}
