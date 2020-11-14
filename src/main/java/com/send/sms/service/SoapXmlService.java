package com.send.sms.service;

public interface SoapXmlService {

    String getSoapXML(String loginId,String pwd,String[] mobile,String content);

}
