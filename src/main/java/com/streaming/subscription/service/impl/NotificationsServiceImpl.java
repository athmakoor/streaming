package com.streaming.subscription.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.streaming.subscription.bean.jpa.NotificationEntity;
import com.streaming.subscription.repository.NotificationsRepository;
import com.streaming.subscription.service.DigitalMarketingService;
import com.streaming.subscription.service.NotificationsService;
import com.streaming.utils.TimeUtil;

@Service
public class NotificationsServiceImpl implements NotificationsService {
    @Resource
    private NotificationsRepository notificationsRepository;
    @Resource
    private DigitalMarketingService digitalMarketingService;


    @Override
    public void save(String type, HttpServletRequest request) throws UnsupportedEncodingException {
        NotificationEntity entity = new NotificationEntity();
        String status;

        entity.setCreatedAt(TimeUtil.getCurrentUTCTime());
        entity.setResponseMessage(request.getQueryString());
        entity.setType(type);

        if ("notification".equals(type)) {
            String chargeStatus = request.getParameter("charg_status");
            entity.setChargeStatus(chargeStatus);
            entity.setProvider("zain-kuwait");
            entity.setMsisdn(request.getParameter("msisdn"));
            entity.setSyncType(request.getParameter("sync_type"));
            entity.setPrice(request.getParameter("price"));

            if ("1".equals(request.getParameter("charg_status"))) {
                if ("1".equals(request.getParameter("sync_type"))) {
                    status = "renewal";
                } else {
                    status = "subscribe";
                }

                digitalMarketingService.saveSubscription(request.getParameter("msisdn"), request.getParameter("price"), "AED", status);
            }

        }

        notificationsRepository.save(entity);
    }

    private String getParamValue(String data, String key) {
        String value = "";

        String[] split = data.split("&");
        String[] split1;
        for (String str : split) {
            split1 = str.split("=");

            if (split1.length ==2 && split1[0].equals(key)) {
                value = split1[1];
            }
        }

        return value;
    }

    @Override
    public void create() {
        NotificationEntity entity;
        List<NotificationEntity> entities = new ArrayList<>();
        try {
            File file=new File("/home/nischal/Nischal/data.txt");    //creates a new file instance
            FileReader fr=new FileReader(file);   //reads the file
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
            String line;
            while((line=br.readLine())!=null)
            {
                entity = new NotificationEntity();
                entity.setCreatedAt(TimeUtil.getCurrentUTCTime());
                entity.setResponseMessage(line);
                entity.setType("notification");
                entity.setProvider("zain-kuwait");
                entity.setSyncType(getParamValue(line, "sync_type"));
                entity.setMsisdn(getParamValue(line, "msisdn"));
                entity.setPrice(getParamValue(line, "price"));

                String chargeStatus = getParamValue(line, "charg_status");
                entity.setChargeStatus(chargeStatus);
                entities.add(entity);
            }
            fr.close();    //closes the stream and release the resources
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        notificationsRepository.saveAll(entities);
    }
}
