package com.home.LedScreensServer.model;

import lombok.extern.slf4j.Slf4j;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapException;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.PcapIf;
import org.jnetpcap.util.PcapUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;

@Slf4j
@Component
public class Network {
    private final LedScreen ledScreen;
    private PcapIf networkIf;
    private Pcap pcapListener;
    private Pcap pcapSender;
    private int countReceivedCard;

    List<byte[]> requests;
    int index;

    public Network(LedScreen ledScreen) {
        this.ledScreen = ledScreen;
        startDrawScreenService();

        requests = Requests.makeRequestsFromImage(ledScreen.getScreenFrame());
        index = 0;
    }

    @Scheduled(fixedRate = 60000)
    private void updateNetwork() {
        try {
            log.debug("Updating network");
            List<PcapIf> networks = Pcap.findAllDevs();
            for (PcapIf network : networks) {
                if (network.description().toString().contains("ASIX USB to Gigabit Ethernet Family Adapter")) {
                    this.networkIf = network;
                }
            }

            if (this.networkIf == null) {
                log.error("No network found");
            }

            runSender();
            runListener();

        } catch (PcapException e) {
            log.error(e.getMessage());
        }
    }

    private void runSender() {
        Thread thread = new Thread(() -> {
            try {
                if (networkIf != null) {
                    //if (pcapSender != null) pcapSender.close();
                    pcapSender = Pcap.create(networkIf);
                    pcapSender.activate();
                }
            } catch (PcapException e) {
                log.error(e.getMessage());
            }
        });
        thread.start();
    }

    private void runListener() {
        Thread thread = new Thread(() -> {
            try {
                if (networkIf != null) {
                    //if (pcapListener != null) pcapListener.close();
                    pcapListener = Pcap.create(networkIf);
                    pcapListener.setSnaplen(/*16 * 1024*/2048);
                    pcapListener.setPromisc(true);
                    pcapListener.activate();
                    pcapListener.loop(0, (String message, PcapHeader header, byte[] packet) -> {

                        //TODO add logic
                        if (packet.length == 1070) {
                            log.debug(header.toString());
                            log.debug(PcapUtils.toHexString(packet, 0, packet.length));
                            countReceivedCard++;
                        }
                    }, "");
                }
            } catch (PcapException e) {
                log.error(e.getMessage());
            }
        });
        thread.start();
    }

    @Scheduled(fixedRate = 60000, initialDelay = 5000)
    private void detectReceiverCard() {
        log.debug("Detect ReceiverCard");
        this.countReceivedCard = -1;
        sendDetectRequest(this.countReceivedCard);
    }

    private void sendDetectRequest(int countReceivedCard) {
        try {
            if (networkIf != null && pcapSender != null) {

                if (this.countReceivedCard == -1) {
                    log.debug("");
                    log.debug("Send request -1: {}", Requests.DETECT_RECEIVED_CARD1.getRequest());
                    pcapSender.sendPacket(Requests.DETECT_RECEIVED_CARD1.getRequest());
                } else {
                    byte[] request = Requests.DETECT_RECEIVED_CARD2.getRequest();
                    request[16] = (byte) (countReceivedCard % 256);
                    log.debug("");
                    log.debug("Send request {}: {}", countReceivedCard, request);

                    pcapSender.sendPacket(request);
                }
                wait(10000);

                if (this.countReceivedCard > countReceivedCard) {
                    this.countReceivedCard = countReceivedCard + 1;
                    sendDetectRequest(this.countReceivedCard);
                } else {
                    log.debug("Detected {} received cards", countReceivedCard);
                    ledScreen.setScreenProperties(countReceivedCard);
                    requests = Requests.makeRequestsFromImage(ledScreen.getScreenFrame());
                }
            }
        } catch (PcapException e) {
            log.error(e.getMessage());
        }
    }


    //@Scheduled(fixedRate = 10, timeUnit = TimeUnit.NANOSECONDS, initialDelay = 5000)
    private void startDrawScreenService() {
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    if (networkIf != null && pcapSender != null) {

                        for (byte[] request: requests) {
                            pcapSender.sendPacket(request);
                        }

//                        if (index > requests.size() - 1) index = 0;
//                        pcapSender.sendPacket(requests.get(index++));
                    }
                }
            } catch (PcapException e) {
                //log.error(e.getMessage());
            }
        });
        thread.start();

    }


    private void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
