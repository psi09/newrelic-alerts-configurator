package com.ocado.pandateam.newrelic.sync.configuration.condition;

import com.ocado.pandateam.newrelic.sync.configuration.condition.terms.TermsConfiguration;

import java.util.Collection;

/**
 * Alerts condition configuration.
 * Implementations:
 * <ul>
 *     <li>{@link ApmAppCondition}</li>
 *     <li>{@link ApmKeyTransactionCondition}</li>
 * </ul>
 */
public interface Condition {
    ConditionType getType();
    String getConditionName();
    boolean isEnabled();
    Collection<String> getEntities();
    String getMetricAsString();
    String getRunBookUrl();
    Collection<TermsConfiguration> getTerms();

    default String getConditionScopeAsString() {
        return null;
    }
}
