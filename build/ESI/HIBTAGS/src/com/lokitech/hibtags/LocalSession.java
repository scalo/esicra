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

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * This is a simple struct that indicates the Session and what object
 * is responsible for creating and closing this session.
 */
public class LocalSession {
    private SessionFactory sf = null;
    private Session session = null;
    private OperationTagSupport owner = null;

    /**
     * Creates a local session with this session of this owner.
     */
    public LocalSession(SessionFactory sf, Session session, OperationTagSupport owner) {
        this.sf = sf;
        this.session = session;
        this.owner = owner;
    }

    /**
     * SessionFactory that created this.
     */
    public SessionFactory getSessionFactory() {
        return sf;
    }

    /**
     * Session this maintains
     */
    public Session getSession() {
        return session;
    }

    /**
     * Owner of this session.
     */
    public OperationTagSupport getOwner() {
        return owner;
    }

}
