package com.streaming.aggregator.timwe.service.impl;

import com.gamesvas.aggregator.timwe.bean.*;
import com.gamesvas.aggregator.timwe.constants.MtContext;
import com.gamesvas.aggregator.timwe.constants.NotificationTypes;
import com.gamesvas.aggregator.timwe.constants.ServiceCodes;
import com.gamesvas.aggregator.timwe.constants.SubscriptionStatusCodes;
import com.gamesvas.aggregator.timwe.service.TimWeSubscriptionService;
import com.gamesvas.aggregator.timwe.subscription.TSubscription;
import com.gamesvas.bean.jpa.CountryEntity;
import com.gamesvas.constant.Provider;
import com.gamesvas.repository.CountryRepository;
import com.gamesvas.service.mapping.ServiceMapper;
import com.gamesvas.subscription.bean.Subscription;
import com.gamesvas.subscription.bean.jpa.SubscriptionEntity;
import com.gamesvas.subscription.bean.jpa.SubscriptionPackEntity;
import com.gamesvas.subscription.bean.jpa.SubscriptionRequestEntity;
import com.gamesvas.subscription.repository.SubscriptionPackRepository;
import com.gamesvas.subscription.repository.SubscriptionRepository;
import com.gamesvas.subscription.repository.SubscriptionRequestRepository;
import com.gamesvas.utils.IpUtil;
import com.gamesvas.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

/**
 * Created by HARIKRISHNA on 1/28/2021.
 */
@Service
public class TimWeSubscriptionServiceImpl implements TimWeSubscriptionService {
    @Resource
    private CountryRepository countryRepository;

    @Resource
    private SubscriptionRepository subscriptionRepository;

    @Resource
    private SubscriptionPackRepository subscriptionPackRepository;

    @Resource
    private SubscriptionRequestRepository subscriptionRequestRepository;

    @Resource
    private ServiceMapper<com.gamesvas.subscription.bean.Subscription, SubscriptionEntity> subscriptionMapper;

    @Resource
    private TSubscription tSubscription;

    private static final Logger LOGGER = LoggerFactory.getLogger(TSubscription.class);

    @Override
    public ServiceResponse getCGSource(CgUrlRequest cgUrlRequest, HttpServletRequest request) {
        ServiceResponse response = new ServiceResponse();
        String correlatedId =  new Date().getTime() + "_" + cgUrlRequest.getMsisdn();
        Optional<CountryEntity> country;

        if (!cgUrlRequest.getLocale().isEmpty()) {
            country = countryRepository.findByLocaleAndActiveTrue(cgUrlRequest.getLocale());
            cgUrlRequest.setCountryId(country.get().getDialingCode());
        }

        try {
            String url = tSubscription.getCgUrl(cgUrlRequest, correlatedId, request);

            response.setData(url);
            response.setHasError(false);
            response.setTransactionId(correlatedId);

            if(!cgUrlRequest.getMsisdn().isEmpty()) {
                SubscriptionStatus subscriptionStatus = checkSubscription(cgUrlRequest.getMsisdn());
                String state = subscriptionStatus.getStatusCode();

                if (state.equals(SubscriptionStatusCodes.NONE)) {
                    addSubscription(correlatedId, cgUrlRequest);
                    response.setServiceCode(ServiceCodes.IS_SUCCESS_REDIRECT);
                    response.setMessage("Subscription Request Submitted successfully");
                } else if (state.equals(SubscriptionStatusCodes.PARKING)) {
                    response.setServiceCode(ServiceCodes.IS_SUCCESS_NOTIFICATION);
                    response.setMessage("Your Subscription request is in process.");
                }  else {
                    response.setServiceCode(ServiceCodes.IS_SUCCESS_ACTIVE);
                    response.setMessage("Your Subscription is in Active");
                }
            }

        } catch (Exception exception) {
            response.setHasError(true);
            response.setErrorMessage("Failed to fetch consent page.Try again later");
            response.setTransactionId(correlatedId);
            LOGGER.error("Timwe CG URL Exception:" , exception.getMessage());
        }

        return response;
    }

    @Override
    public ServiceResponse unSubscribe(String msisdn, HttpServletRequest request) {
        UnSubscribeRequest unSubscribeRequest =  new UnSubscribeRequest();
        UnSubscribeResponse unSubscribeResponse;
        ServiceResponse response = new ServiceResponse();

        Optional<SubscriptionEntity> entity = subscriptionRepository.findFirstByMsisdnOrderByIdDesc(msisdn);

        try {
            if (entity.isPresent()) {
                unSubscribeRequest.setUserIdentifier(msisdn);
                unSubscribeRequest.setUserIdentifierType("MSISDN");
                unSubscribeRequest.setClientIp(IpUtil.getClientIpAddr(request));

                unSubscribeResponse = tSubscription.unsubscribe(unSubscribeRequest);

                if (unSubscribeResponse.getCode().equals("SUCCESS")) {
                    String resStatus = unSubscribeResponse.getResponseData().getSubscriptionResult();
                    response.setHasError(false);

                    switch (resStatus) {
                        case "OPTOUT_CANCELED_OK":
                            response.setMessage("Unsubscribed SuccessFully");
                            break;
                        case "OPTOUT_ALREADY_CANCELED":
                            response.setMessage("Already Unsubscribed from fundoo games service.");
                            break;
                        case "OPTOUT_ALL_CANCELED_OK":
                            response.setMessage("Unsubscribed from all subscriptions of fundoo games service.");
                            break;
                        case "OPTOUT_ALL_ALREADY_CANCELED":
                            response.setMessage("Already All subscriptions are Unsubscribed from fundoo games service.");
                            break;
                        case "OPTOUT_MISSING_PARAM":
                            response.setHasError(true);
                            response.setMessage("Unsubscribe failed due to invalid data");
                            break;
                        case "OPTOUT_NO_SUB":
                            response.setMessage("No Subscription found for the fundoo games on the mobile number");
                            break;
                    }

                    if (!resStatus.equals("OPTOUT_NO_SUB") && !resStatus.equals("OPTOUT_MISSING_PARAM")) {
                        String message = tSubscription.getMessage(entity.get(), NotificationTypes.OPT_OUT);

                        MtResponse mtResponse = tSubscription.sendMessage(message, MtContext.SUBSCRIPTION);
                        LOGGER.info("Timwe Mt Message response" , mtResponse.toString());
                    }

                } else {
                    response.setHasError(true);
                    response.setErrorMessage("Unable Un-Subscribe. try again Later. Error: " + unSubscribeResponse.getCode());
                    LOGGER.error("Timwe UnSubscription FAILED CODE:" , unSubscribeResponse.getCode());
                }
            }
        } catch (Exception ex) {
            response.setHasError(true);
            response.setErrorMessage("Unable UnSubscribe. try again Later");
            LOGGER.error("Timwe UnSubscription Exception:" , ex.getMessage());
        }

        return response;
    }

    @Override
    public ServiceResponse updateStatus(final String correlatedId, final String statusCode) {
        Optional<SubscriptionEntity> existing = subscriptionRepository.findByClickId(correlatedId);
        SubscriptionEntity savedSubscription;
        ServiceResponse response = new ServiceResponse();
        Subscription subscription;

        if("1".equals(statusCode)) {
            if (existing.isPresent()) {
                existing.get().setStatus("parking");
                existing.get().setExpireAt(TimeUtil.getCurrentUTCTime());

                savedSubscription = subscriptionRepository.save(existing.get());
                LOGGER.info("Timwe Subscription update" , savedSubscription);

                response.setHasError(false);
                response.setMessage("Subscription Added Successfully");
                response.setTransactionId(correlatedId);
                response.setServiceCode(ServiceCodes.IS_SUCCESS_REDIRECT);
                response.setData(savedSubscription.getMsisdn());
            }
        } else {
            response.setHasError(true);
            response.setMessage("Falied to add Subscription.Try Again Later");
            response.setTransactionId(correlatedId);
            response.setServiceCode(ServiceCodes.IS_FAILED_MESSAGE);
        }

        return response;
    }

    @Override
    public ServiceResponse checkSub(final String msisdn) {
        SubscriptionStatus subscriptionStatus = checkSubscription(msisdn);
        ServiceResponse response = new ServiceResponse();

        if (subscriptionStatus.getStatusCode().equals(SubscriptionStatusCodes.NONE)) {
            response.setServiceCode(ServiceCodes.NO_SUB);
            response.setMessage("Please Subscribe to access service.");
        } else if (subscriptionStatus.getStatusCode().equals(SubscriptionStatusCodes.PARKING)) {
            response.setServiceCode(ServiceCodes.IS_SUCCESS_NOTIFICATION);
            response.setMessage("Your Subscription request is in process.");
        }  else {
            response.setServiceCode(ServiceCodes.IS_SUCCESS_ACTIVE);
            response.setMessage("Your Subscription is in Active");
        }

        return  response;
    }

    @Override
    public SubscriptionStatus getSubscriptionStatus(final String msisdn) {
        return checkSubscription(msisdn);
    }


    @Override
    public Subscription save(final String status, final String clickId, final String msisdn) {
        SubscriptionEntity savedSubscription;
        Optional<SubscriptionEntity> existing = subscriptionRepository.findFirstByMsisdnAndClickIdOrderByIdDesc(msisdn, clickId);

        if (existing.isPresent()) {
            existing.get().setStatus(status);

            if(SubscriptionStatusCodes.UNSUBSCRIBE.equals(status)) {
                existing.get().setExpireAt(TimeUtil.getCurrentUTCTime().minusMinutes(1));
            } else {
                existing.get().setExpireAt(TimeUtil.getCurrentUTCTime().plusDays(1));
            }

            savedSubscription = subscriptionRepository.save(existing.get());
        } else if (!SubscriptionStatusCodes.UNSUBSCRIBE.equals(status)) {
            SubscriptionEntity entity = new SubscriptionEntity();
            entity.setCreatedAt(TimeUtil.getCurrentUTCTime());

            entity.setExpireAt(TimeUtil.getCurrentUTCTime().plusDays(7));

            entity.setStatus(status);
            entity.setClickId(clickId);
            entity.setCurrency(null);
            entity.setPrice(null);
            entity.setMsisdn(msisdn);
            entity.setProvider(Provider.TIMWE);
            savedSubscription = subscriptionRepository.save(entity);
        } else {
            savedSubscription = null;
        }

        if (savedSubscription != null) {
            return subscriptionMapper.mapEntityToDTO(savedSubscription, Subscription.class);
        } else {
            return null;
        }
    }

    @Override
    public Subscription save(final String status, final String clickId, final String msisdn, final String totalCharged, final String packId) {
        String currency = null;
        String price = null;
        SubscriptionEntity savedSubscription;
        Optional<SubscriptionPackEntity> subscriptionPackEntity = subscriptionPackRepository.findByProviderAndSku(Provider.TIMWE, packId);

        Optional<SubscriptionEntity> existing = subscriptionRepository.findFirstByMsisdnAndClickIdOrderByIdDesc(msisdn, clickId);

        if (subscriptionPackEntity.isPresent()) {
            currency = subscriptionPackEntity.get().getCurrency();
            price = subscriptionPackEntity.get().getPrice();
        }

        if (existing.isPresent()) {
            existing.get().setStatus(status);
            existing.get().setExpireAt(TimeUtil.getCurrentUTCTime().plusDays(7));
            existing.get().setCurrency(currency);
            existing.get().setPrice(price);
            existing.get().setTotalCharged(totalCharged);

            savedSubscription = subscriptionRepository.save(existing.get());
            LOGGER.info("Timwe Subscription update" , savedSubscription);
        } else {
            SubscriptionEntity entity = new SubscriptionEntity();
            entity.setCreatedAt(TimeUtil.getCurrentUTCTime());
            entity.setExpireAt(TimeUtil.getCurrentUTCTime().plusDays(7));
            entity.setStatus(status);
            entity.setClickId(clickId);
            entity.setCurrency(currency);
            entity.setPrice(price);
            entity.setMsisdn(msisdn);
            entity.setProvider(Provider.TIMWE);
            entity.setTotalCharged(totalCharged);
            savedSubscription = subscriptionRepository.save(entity);
            LOGGER.info("Timwe Subscription entry" , savedSubscription);
        }

        if("subscribe".equals(status) || "parking".equals(status)) {
            String message = tSubscription.getMessage(savedSubscription, NotificationTypes.OPT_IN);

            MtResponse mtResponse = tSubscription.sendMessage(message, MtContext.SUBSCRIPTION);
            LOGGER.info("Timwe Mt Message response" , mtResponse.toString());
        }

        return subscriptionMapper.mapEntityToDTO(savedSubscription, Subscription.class);
    }

    @Override
    public Subscription save(final String status, final String clickId, final String msisdn, final String packId, final Boolean isRenew) {
        String currency = null;
        String price = null;
        SubscriptionEntity savedSubscription;
        Optional<SubscriptionPackEntity> subscriptionPackEntity = subscriptionPackRepository.findByProviderAndSku(Provider.TIMWE, packId);

        Optional<SubscriptionEntity> existing = subscriptionRepository.findFirstByMsisdnAndClickIdOrderByIdDesc(msisdn, clickId);

        if (subscriptionPackEntity.isPresent()) {
            currency = subscriptionPackEntity.get().getCurrency();
            price = subscriptionPackEntity.get().getPrice();
        }

        if (existing.isPresent() && isRenew) {
            existing.get().setStatus(status);
            existing.get().setExpireAt(TimeUtil.getCurrentUTCTime().plusDays(7));
            existing.get().setCurrency(currency);
            existing.get().setPrice(price);

            savedSubscription = subscriptionRepository.save(existing.get());
            LOGGER.info("Timwe Subscription ReNew:" , savedSubscription);
        } else {
            SubscriptionEntity entity = new SubscriptionEntity();
            entity.setCreatedAt(TimeUtil.getCurrentUTCTime());
            entity.setExpireAt(TimeUtil.getCurrentUTCTime().plusDays(7));
            entity.setStatus(status);
            entity.setClickId(clickId);
            entity.setCurrency(currency);
            entity.setPrice(price);
            entity.setMsisdn(msisdn);
            entity.setProvider(Provider.TIMWE);
            savedSubscription = subscriptionRepository.save(entity);
            LOGGER.info("Timwe Subscription entry:" , savedSubscription);
        }

        return subscriptionMapper.mapEntityToDTO(savedSubscription, Subscription.class);
    }

    private Subscription addSubscription(String correlatedId, CgUrlRequest request) {
        TimWeSubscriptionRequest subscriptionRequest = tSubscription.getSubscription(correlatedId, request);

        Optional<SubscriptionPackEntity> packEntity = subscriptionPackRepository.findByProviderAndSku(Provider.TIMWE, subscriptionRequest.getPackId());

        if(packEntity.isPresent()) {
            subscriptionRequest.setProductId(packEntity.get().getProductId());
            subscriptionRequest.setPricepointId(packEntity.get().getSku());
            subscriptionRequest.setCurrency(packEntity.get().getCurrency());
            subscriptionRequest.setPrice(packEntity.get().getPrice());
        }

        SubscriptionEntity entity = new SubscriptionEntity();
        entity.setCreatedAt(TimeUtil.getCurrentUTCTime());
        entity.setExpireAt(TimeUtil.getCurrentUTCTime().plusDays(7));
        entity.setStatus("new");
        entity.setClickId(subscriptionRequest.getCorrelatorId());
        entity.setCurrency(subscriptionRequest.getCurrency());
        entity.setPrice(subscriptionRequest.getPrice());
        entity.setMsisdn(request.getMsisdn());
        entity.setProvider(Provider.TIMWE);
        entity.setPackId(packEntity.get().getSku());

        SubscriptionEntity savedSubscription = subscriptionRepository.save(entity);

        subscriptionRequest.setMsisdn(request.getMsisdn());
        addSubscriptionRequest(subscriptionRequest);

        LOGGER.info("Timwe Subscription entry:" , savedSubscription);
        return subscriptionMapper.mapEntityToDTO(savedSubscription, com.gamesvas.subscription.bean.Subscription.class);
    }

    private void addSubscriptionRequest(TimWeSubscriptionRequest request) {
        SubscriptionRequestEntity entity = new SubscriptionRequestEntity();

        entity.setClickId(request.getCorrelatorId());
        entity.setMsisdn(request.getMsisdn());
        entity.setCreatedAt(TimeUtil.getCurrentUTCTime());
        entity.setPrice(request.getPrice());
        entity.setRequestBody(request.toString());

        subscriptionRequestRepository.save(entity);
    }

    private SubscriptionStatus checkSubscription(String msisdn) {
        SubscriptionStatus subscriptionStatus = new SubscriptionStatus();
        Optional<SubscriptionEntity> existing = subscriptionRepository.findFirstByMsisdnOrderByIdDesc(msisdn);
        String status;

        if(existing.isPresent()) {
            status = existing.get().getStatus();
            Boolean isActive =  existing.get().getExpireAt().isAfter(TimeUtil.getCurrentUTCTime());
            subscriptionStatus.setStatusCode(status);
            subscriptionStatus.setCorrelatedId(existing.get().getClickId());
            subscriptionStatus.setStatus(SubscriptionStatusCodes.EXPIRED);

            if (!isActive || status.equals(SubscriptionStatusCodes.UNSUBSCRIBE)) {
                subscriptionStatus.setStatusCode(SubscriptionStatusCodes.NONE);
                subscriptionStatus.setStatus(SubscriptionStatusCodes.UNSUBSCRIBE);
            } else if (isActive && (status.equals(SubscriptionStatusCodes.NEW) || status.equals(SubscriptionStatusCodes.PARKING))) {
                subscriptionStatus.setStatusCode(SubscriptionStatusCodes.PARKING);
                subscriptionStatus.setStatus(SubscriptionStatusCodes.PARKING);
            } else if (isActive) {
                subscriptionStatus.setStatus(SubscriptionStatusCodes.SUBSCRIBE);
            }
        } else {
            subscriptionStatus.setStatusCode(SubscriptionStatusCodes.NONE);
            subscriptionStatus.setStatus(SubscriptionStatusCodes.NONE);
        }

        return subscriptionStatus;
    }
}
