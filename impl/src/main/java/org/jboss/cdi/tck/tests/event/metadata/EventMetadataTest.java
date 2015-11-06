/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc., and individual contributors
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
package org.jboss.cdi.tck.tests.event.metadata;

import static org.jboss.cdi.tck.cdi.Sections.EVENT_METADATA;
import static org.jboss.cdi.tck.util.Assert.assertAnnotationSetMatches;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.EventMetadata;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.util.TypeLiteral;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.cdi.tck.AbstractTest;
import org.jboss.cdi.tck.shrinkwrap.WebArchiveBuilder;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecAssertions;
import org.jboss.test.audit.annotations.SpecVersion;
import org.testng.annotations.Test;

/**
 * {@link InjectionPoint} methods are tested elsewhere.
 *
 * @author Martin Kouba
 */
@SpecVersion(spec = "cdi", version = "2.0-EDR1")
public class EventMetadataTest extends AbstractTest {

    @Deployment
    public static WebArchive createTestArchive() {
        return new WebArchiveBuilder().withTestClassPackage(EventMetadataTest.class).build();
    }

    @SuppressWarnings("unchecked")
    @Test(dataProvider = ARQUILLIAN_DATA_PROVIDER)
    @SpecAssertions({ @SpecAssertion(section = EVENT_METADATA, id = "a"), @SpecAssertion(section = EVENT_METADATA, id = "b"),
            @SpecAssertion(section = EVENT_METADATA, id = "c"), @SpecAssertion(section = EVENT_METADATA, id = "e") })
    public void testSimpleEvent(SimpleEventNotifier notifier, SimpleEventObserver observer) {

        assertNotNull(notifier);
        assertNotNull(observer);

        notifier.fireSimpleEvent();
        verifyMetadata(observer.getSyncMetadata(), true, SimpleEvent.class, Any.class);

        notifier.fireSimpleEventBeanManager();
        verifyMetadata(observer.getSyncMetadata(), false, SimpleEvent.class, Any.class);

        notifier.fireSimpleEventWithQualifiers();
        verifyMetadata(observer.getSyncMetadata(), true, SimpleEvent.class, Alpha.class, Bravo.class, Any.class);

        notifier.fireSimpleEventBeanManagerWithQualifiers();
        verifyMetadata(observer.getSyncMetadata(), false, SimpleEvent.class, Charlie.class, Any.class);
    }

    @SuppressWarnings({ "serial", "unchecked" })
    @Test(dataProvider = ARQUILLIAN_DATA_PROVIDER)
    @SpecAssertion(section = EVENT_METADATA, id = "c")
    public void testParameterizedResolvedType(DuckNotifier notifier, DuckObserver observer) {

        assertNotNull(notifier);
        assertNotNull(observer);

        notifier.fireStringDuck();
        verifyMetadata(observer.getMetadata(), true, new TypeLiteral<Duck<String>>() {
        }.getType(), Any.class);

        notifier.fireMapDuck();
        verifyMetadata(observer.getMetadata(), true, new TypeLiteral<Duck<Map<String, Integer>>>() {
        }.getType(), Any.class, Bravo.class);

        notifier.fireListDuck();
        verifyMetadata(observer.getMetadata(), true, new TypeLiteral<ArrayList<Duck<Number>>>() {
        }.getType(), Any.class);
    }

    private void verifyMetadata(EventMetadata metadata, boolean isInjectionPointAvailable, Type resolvedType,
            Class<? extends Annotation>... qualifiers) {
        assertNotNull(metadata);
        if (isInjectionPointAvailable) {
            assertNotNull(metadata.getInjectionPoint());
        } else {
            assertNull(metadata.getInjectionPoint());
        }
        assertEquals(metadata.getType(), resolvedType);
        assertAnnotationSetMatches(metadata.getQualifiers(), qualifiers);
    }

}
