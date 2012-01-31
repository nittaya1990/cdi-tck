/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.cdi.tck.tests.extensions.annotated.synthetic;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessSyntheticAnnotatedType;

import org.jboss.cdi.tck.tests.extensions.alternative.metadata.AnnotatedTypeWrapper;

public class ModifyingExtension implements Extension {

    public <T> void modify(@Observes ProcessSyntheticAnnotatedType<T> event) {
        Class<T> clazz = event.getAnnotatedType().getJavaClass();
        if (Orange.class.equals(clazz)) {
            event.veto();
        } else if (Apple.class.equals(clazz)) {
            event.setAnnotatedType(new AnnotatedTypeWrapper<T>(event.getAnnotatedType(), true, Fresh.Literal.INSTANCE));
        }
    }
}