/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc., and individual contributors
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

package org.jboss.cdi.tck.tests.full.extensions.communication;

import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.enterprise.inject.spi.BeforeBeanDiscovery;
import jakarta.enterprise.inject.spi.Extension;
import jakarta.enterprise.inject.spi.ProcessAnnotatedType;

import org.jboss.cdi.tck.util.ActionSequence;

/**
 * @author Martin Kouba
 * 
 */
public class ExtensionAlpha implements Extension {

    public void observeBeforeBeanDiscovery(@Observes BeforeBeanDiscovery event) {
        ActionSequence.reset();
    }

    public void observeProcessAnnotatedType(@Observes ProcessAnnotatedType<?> event, BeanManager beanManager) {
        beanManager.getEvent().select(PatEvent.class).fire(new PatEvent(event.getAnnotatedType().getJavaClass()));
    }

    public void observeBeta(@Observes @Baz PbEvent event) {
        ActionSequence.addAction(EventBase.PB_SEQ, event.getClazz().getName());
    }

}
