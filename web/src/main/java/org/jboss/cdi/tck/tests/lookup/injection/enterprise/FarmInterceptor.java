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
package org.jboss.cdi.tck.tests.lookup.injection.enterprise;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;

public class FarmInterceptor {

    @Inject
    private Sheep sheep;
    private boolean initializerCalled = false;

    @Inject
    public void initialize(Sheep sheep) {
        initializerCalled = sheep != null;
    }

    @AroundInvoke
    public Object intercept(InvocationContext invocation) throws Exception {
        if ((sheep != null) && initializerCalled) {
            return (Integer) invocation.proceed() + 1;
        } else {
            throw new RuntimeException("Sheep not injected.");
        }
    }
}
