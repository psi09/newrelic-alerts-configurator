package com.ocadotechnology.newrelic.alertconfiguploader.configuration.channel;

import com.ocadotechnology.newrelic.api.NewRelicApi;
import com.ocadotechnology.newrelic.api.model.channels.AlertsChannelConfiguration;

public interface ChannelTypeSupport {
    AlertsChannelConfiguration generateAlertsChannelConfiguration(NewRelicApi api);
}
