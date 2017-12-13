package com.bestjlb.demo.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by durianskh on 4/12/16.
 */
public class IPUtils {

    private static final String[] HEADERS_TO_TRY = {
            // 这是squid开发的字段，非RFC标准，简称XFF头，数据包通过HTTP代理或者负载均衡服务器时会添加
            // 访问路径：Client->CDN->Ngnix
            // XFF格式 X-Forwarded-For:client1, proxy1, proxy2
            // XFF头信息可以有多个，中间用逗号隔开，第一项为真实客户端IP，剩下的是经过代理或者负载均衡服务器的IP
            // 该信息头可以被伪造，该信息头可以随意增删改
            "X-Forwarded-For",

            // Proxy-Client-IP字段和WL-Proxy-Client-IP在Apache-WebLogic搭配下出现
            // 访问路径：Client->Apache WebServer+WebLogic http plugin->WebLogin
            // Instance
            // 这两个字段为了兼容，怕从Ngnix+Rsin换成Apache+WebLogic，这两个字段可以忽略
            "Proxy-Client-IP",

            "WL-Proxy-Client-IP",

            "HTTP_X_FORWARDED_FOR",

            "HTTP_X_FORWARDED",

            "HTTP_X_CLUSTER_CLIENT_IP",

            // HTTP_CLIENT_IP是代理服务器发送的HTTP头，可以忽略
            "HTTP_CLIENT_IP",

            "HTTP_FORWARDED_FOR",

            "HTTP_FORWARDED",

            "HTTP_VIA",

            // REMOTE_ADDR是客户端跟服务器“握手”是的IP，但是如果使用了“匿名代理”，REMOTE_ADDR将显示代理服务器IP
            "REMOTE_ADDR",

            "X-Real-IP"};

    public static String getClientIpAddr(HttpServletRequest request) {

        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            ip = getFirstValidIP(ip);
            if (ip != null) {
                return ip;
            }
        }

        return request.getRemoteAddr();
    }

    public static String getFirstValidIP(String ip) {
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            return null;
        }

        String[] ips = ip.split(",");
        for (int i = 0; i < ips.length; i++) {
//            Inet4Address address;
            String s = ips[i].trim();
//            try {
                // 判断是否是合法的IP地址
                if (StringUtils.isEmpty(s)
                        || "unknown".equalsIgnoreCase(s)
                        || (!InetAddressValidator.getInstance().isValidInet4Address(s))) {

                    continue;
                }

//                address = (Inet4Address) InetAddress.getByName(s);
//            } catch (UnknownHostException exception) {
//                continue;
//            }

            // 判断是否是公网IP地址
//            if (!(address.isSiteLocalAddress()
//                    || address.isAnyLocalAddress()
//                    || address.isLinkLocalAddress()
//                    || address.isLoopbackAddress()
//                    || address.isMulticastAddress())) {
//
//                return s;
//            }
            return s;
        }
        return null;
    }
}
