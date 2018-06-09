/*
 * Copyright 2018 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apiman.gateway.engine.impl;

import io.apiman.gateway.engine.IConnectorConfig;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Connector configuration with lazy initialisation of customised suppressions.
 *
 * Implementors should provide a static immutable default maps to {@link AbstractConnectorConfig}.
 * This will be copied when needed into a mutable map (i.e. when the user adds/removes something).
 *
 * Future work could include caching common suppression maps, potentially.
 *
 * @author Marc Savy {@literal <marc@rhymewithgravy.com>}
 */
public abstract class AbstractConnectorConfig implements IConnectorConfig {

    Set<String> suppressedRequestHeaders;
    boolean modifiedDefaultRequestHeaders = false;

    Set<String> suppressedResponseHeaders;
    boolean modifiedDefaultResponseHeaders = false;

// TODO: cache common request and response suppression maps. Some quick and dirty way of looking it up?
//    static LRUMap<String, Set<String>> cache = new LRUMap<String, Set<String>>(1000) {
//        private static final long serialVersionUID = -18620070710843930L;
//
//        @Override
//        protected void handleRemovedElem(java.util.Map.Entry<String, Set<String>> eldest) {
//            // Don't need to do anything.
//        }
//    };

    /**
     * Suppressed request headers
     * Suppressed response headers
     *
     * @param suppressedRequestHeaders request headers to suppress
     * @param suppressedResponseHeaders response headers to suppress
     */
    public AbstractConnectorConfig(Set<String> suppressedRequestHeaders, Set<String> suppressedResponseHeaders) {
        this.suppressedRequestHeaders = suppressedRequestHeaders;
        this.suppressedResponseHeaders = suppressedResponseHeaders;
    }

    /**
     * Construct no suppressed request nor response headers.
     */
    public AbstractConnectorConfig() {
        this.suppressedRequestHeaders = Collections.emptySet();
        this.suppressedResponseHeaders = Collections.emptySet();
    }

    @Override
    public void suppressRequestHeader(String headerName) {
        if (!suppressedRequestHeaders.contains(headerName)) {
            copyRequestMap();
            suppressedRequestHeaders.add(headerName);
        }
    }

    @Override
    public void suppressResponseHeader(String headerName) {
        if (!suppressedResponseHeaders.contains(headerName)) {
            copyResponseMap();
            suppressedResponseHeaders.add(headerName);
        }
    }

    @Override
    public void permitRequestHeader(String headerName) {
        if (suppressedRequestHeaders.contains(headerName)) {
            copyRequestMap();
            suppressedRequestHeaders.remove(headerName);
        }
    }

    @Override
    public void permitResponseHeader(String headerName) {
        if (suppressedResponseHeaders.contains(headerName)) {
            copyResponseMap();
            suppressedResponseHeaders.remove(headerName);
        }
    }

    @Override
    public Set<String> getSuppressedRequestHeaders() {
        return Collections.unmodifiableSet(suppressedRequestHeaders);
    }

    @Override
    public Set<String> getSuppressedResponseHeaders() {
        return Collections.unmodifiableSet(suppressedResponseHeaders);
    }

    private void copyRequestMap() {
        if (!modifiedDefaultRequestHeaders) {
            this.suppressedRequestHeaders = new LinkedHashSet<>(suppressedRequestHeaders);
            modifiedDefaultRequestHeaders = true;
        }
    }

    private void copyResponseMap() {
        if (!modifiedDefaultResponseHeaders) {
            this.suppressedResponseHeaders = new LinkedHashSet<>(suppressedResponseHeaders);
            modifiedDefaultResponseHeaders = true;
        }
    }
}
