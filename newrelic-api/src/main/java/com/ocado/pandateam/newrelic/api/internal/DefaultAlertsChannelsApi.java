package com.ocado.pandateam.newrelic.api.internal;

import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import com.ocado.pandateam.newrelic.api.AlertsChannelsApi;
import com.ocado.pandateam.newrelic.api.exception.NewRelicApiException;
import com.ocado.pandateam.newrelic.api.internal.model.AlertsChannelList;
import com.ocado.pandateam.newrelic.api.internal.model.AlertsChannelWrapper;
import com.ocado.pandateam.newrelic.api.model.channels.AlertsChannel;

import java.util.List;

public class DefaultAlertsChannelsApi extends ApiBase implements AlertsChannelsApi {

    private static final String CHANNELS_URL = "/v2/alerts_channels.json";
    private static final String CHANNEL_URL = "/v2/alerts_channels/{channel_id}.json";
    private static final String POLICY_CHANNELS_URL = "/v2/alerts_policy_channels.json";

    DefaultAlertsChannelsApi(NewRelicRestClient api) {
        super(api);
    }

    @Override
    public List<AlertsChannel> list() throws NewRelicApiException {
        GetRequest request = api.get(CHANNELS_URL);
        return api.asObject(request, AlertsChannelList.class).getList();
    }

    @Override
    public AlertsChannel create(AlertsChannel channel) throws NewRelicApiException {
        RequestBodyEntity request = api.post(CHANNELS_URL).body(new AlertsChannelWrapper(channel));
        return api.asObject(request, AlertsChannelList.class).getSingle()
                .orElseThrow(() -> new NewRelicApiException("Failed to create channel"));
    }

    @Override
    public AlertsChannel delete(int channelId) throws NewRelicApiException {
        HttpRequest request = api.delete(CHANNEL_URL).routeParam("channel_id", String.valueOf(channelId));
        return api.asObject(request, AlertsChannelWrapper.class).getChannel();
    }

    @Override
    public AlertsChannel deleteFromPolicy(int policyId, int channelId) throws NewRelicApiException {
        HttpRequest request = api.delete(POLICY_CHANNELS_URL)
                .queryString("policy_id", policyId)
                .queryString("channel_id", channelId);
        return api.asObject(request, AlertsChannelWrapper.class).getChannel();
    }
}
