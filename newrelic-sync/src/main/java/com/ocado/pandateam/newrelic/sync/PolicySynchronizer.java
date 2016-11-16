package com.ocado.pandateam.newrelic.sync;

import com.ocado.pandateam.newrelic.api.NewRelicApi;
import com.ocado.pandateam.newrelic.api.exception.NewRelicApiException;
import com.ocado.pandateam.newrelic.api.model.policies.AlertPolicy;
import com.ocado.pandateam.newrelic.sync.configuration.PolicyConfiguration;
import com.ocado.pandateam.newrelic.sync.exception.NewRelicSyncException;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class PolicySynchronizer {

    private final NewRelicApi api;

    private final PolicyConfiguration config;

    public PolicySynchronizer(NewRelicApi api, PolicyConfiguration config) {
        this.api = api;
        this.config = config;
    }

    public void sync() throws NewRelicApiException, NewRelicSyncException {
        AlertPolicy configAlertPolicy = AlertPolicy.builder()
                .name(config.getPolicyName())
                .incidentPreference(config.getIncidentPreference().name())
                .build();

        Optional<AlertPolicy> policyOptional = api.getAlertsPoliciesApi().getByName(config.getPolicyName());

        if (policyOptional.isPresent()) {
            AlertPolicy oldPolicy = policyOptional.get();
            if (!StringUtils.equals(configAlertPolicy.getIncidentPreference(), oldPolicy.getIncidentPreference())) {
                api.getAlertsPoliciesApi().delete(oldPolicy.getId());
                api.getAlertsPoliciesApi().create(configAlertPolicy);
            }
        } else {
            api.getAlertsPoliciesApi().create(configAlertPolicy);
        }
    }
}
