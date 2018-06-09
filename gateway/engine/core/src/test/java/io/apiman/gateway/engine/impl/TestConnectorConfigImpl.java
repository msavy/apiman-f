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

import java.util.LinkedHashSet;
import java.util.Set;
/**
 * Test implementation of {@link IConnectorConfig}
 *
 * @author Marc Savy {@literal <marc@rhymewithgravy.com>}
 */
@SuppressWarnings("nls")
public class TestConnectorConfigImpl extends AbstractConnectorConfig {
    static final Set<String> REQUEST = new LinkedHashSet<>();
    static final Set<String> RESPONSE = new LinkedHashSet<>();

    static {
        REQUEST.add("Transfer-Encoding");
        REQUEST.add("X-API-Key");
        REQUEST.add("Host");

        RESPONSE.add("Transfer-Encoding");
        RESPONSE.add("Connection");
    }

    TestConnectorConfigImpl() {
        super(REQUEST, RESPONSE);
    }
}
