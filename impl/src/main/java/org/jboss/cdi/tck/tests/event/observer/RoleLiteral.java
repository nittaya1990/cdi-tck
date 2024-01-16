/*
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.cdi.tck.tests.event.observer;

import jakarta.enterprise.util.AnnotationLiteral;

@SuppressWarnings("all")
public class RoleLiteral extends AnnotationLiteral<Role> implements Role {

    private static final long serialVersionUID = 1L;

    private String value = null;

    private String nonbindingValue = null;

    public RoleLiteral(String value, String nonbindingValue) {
        this.value = value;
        this.nonbindingValue = nonbindingValue;
    }

    public String value() {
        return value;
    }

    @Override
    public String nonbindingValue() {
        return nonbindingValue;
    }

}
