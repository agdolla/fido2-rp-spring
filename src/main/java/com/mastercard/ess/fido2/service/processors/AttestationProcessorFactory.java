/*
 * Copyright (c) 2018 Mastercard
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.mastercard.ess.fido2.service.processors;

import com.mastercard.ess.fido2.ctap.AttestationFormat;
import com.mastercard.ess.fido2.service.Fido2RPRuntimeException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttestationProcessorFactory {

    private final Map<AttestationFormat, AttestationFormatProcessor> processorsMap = new EnumMap<>(AttestationFormat.class);

    @Autowired
    public void setCommandProcessors(List<AttestationFormatProcessor> attestationFormatProcessors) {
        for (AttestationFormatProcessor app : attestationFormatProcessors) {
            processorsMap.put(app.getAttestationFormat(), app);
        }
    }

    public AttestationFormatProcessor getCommandProcessor(String fmtFormat) {
        try {
            AttestationFormat attestationFormat = AttestationFormat.valueOf(fmtFormat.replace('-', '_'));
            return processorsMap.get(attestationFormat);
        } catch (Exception e) {
            throw new Fido2RPRuntimeException("Unsupported format " + e.getMessage());
        }
    }
}