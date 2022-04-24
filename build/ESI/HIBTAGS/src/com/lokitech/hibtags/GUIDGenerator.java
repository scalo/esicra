/*
 * Copyright (c) 2003, Loki Technologies
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this list of
 *   conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice, this list of
 *   conditions and the following disclaimer in the documentation and/or other materials
 *   provided with the distribution.
 *
 * * Neither the name of the Loki Technologies, Lokitech, nor the names of its contributors
 *   may be used to endorse or promote products derived from this software without specific
 *   prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.lokitech.hibtags;

import java.io.Serializable;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.apache.commons.lang.RandomStringUtils;

/**
 * Implements the Hibernate id generator based on the Common's lang RandomStringUtils.
 */
public class GUIDGenerator implements IdentifierGenerator {
    /**
     * Possible characters that we will allow in our GUID.  Note that we cannot just
     * use randomAlphanumeric or other such functions because that generates both
     * upper and lower case characters, which will get messed in the database since
     * databases are typically case insensitive.
     */
    private final static char[] VALID_CHARS =
           {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * Primitive GUID generator
     */
    public GUIDGenerator() {
        //Do nothing constructor
    }

    /**
     * Just simply wrap the org.apache.commons.lang.RandomStringUtils
     */
    public Serializable generate(SessionImplementor session, Object object)
            throws  HibernateException {
        //TODO: inspect the Object's meta data to determine how large a GUID it needs
        return RandomStringUtils.random(16, VALID_CHARS);
    }
}
