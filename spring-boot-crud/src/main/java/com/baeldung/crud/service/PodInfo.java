package com.baeldung.crud.service;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

@Slf4j
public class PodInfo {

    public String getIpAddress() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        }
        catch( Exception e ) {
            log.error("Error getting IP address", e);
            return "ERROR";
        }
    }

    public String getHostName() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostName();
        }
        catch( Exception e ) {
            log.error("Error getting localhost name", e);
            return "ERROR";
        }
    }
}
