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

import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * A convenience class from which hibernate tags which require multiple children tags
 * as its parameters can derive
 */
public abstract class OperationTagSupport extends BodyTagSupport {
    /**
     * Session factory parameter.
     */
    protected String sessionFactory = null;

    /**
     * Sets the optional session factory to use.
     */
    public void setSessionFactory(String value) {
        sessionFactory = value;
    }

  /**
     * Return the session factory name to use to the SessionLookup class.  This is
     * either what the tag supplied, or what is set in the context init parameter
     * called "com.lokitech.hibtags.sessionFactory", or just "hibernate/SessionFactory"
     * if everything else is exhausted.
    */
    protected String getSessionFactory() {
        if (sessionFactory != null) {
            return sessionFactory;
        }
        
        String fromContext = pageContext.getServletContext().
                getInitParameter("com.lokitech.hibtags.sessionFactory");
        if (fromContext == null) {
            return "hibernate/SessionFactory";
        } else {
            return fromContext;
        }
       
    }
  
}
