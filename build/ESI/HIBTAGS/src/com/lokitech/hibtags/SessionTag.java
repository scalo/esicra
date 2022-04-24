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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TryCatchFinally;
import org.apache.taglibs.standard.tag.common.core.Util;

/**
 * Acquire a session for the duration of this tag.  Implements try/finally and in the
 * finally, it will close the session if still open.  The session object can optionally
 * be exposed for use.
 */
public class SessionTag extends OperationTagSupport implements TryCatchFinally {
    private String var = null;
    private int scope = PageContext.PAGE_SCOPE;

    private LocalSession localSession = null;
    private boolean created = false;

    /**
     * Store the session in a scoped attribute.
     */
    public void setVar(String value) {
        var = value;
    }

    /**
     * Specify what scope to store the session in.
     */
    public void setScope(String value) {
        scope = Util.getScope(value);
    }

    /**
     * Constructs a session object from the SessionFactory.  The body content is
     * immediately spat back out to the underlying writer.
     */
    public int doStartTag() throws JspException {
        //Construct a session
        localSession = SessionLookup.open(this);

        if (var != null) {
            pageContext.setAttribute(var, localSession.getSession(), scope);
        }

        //Spit out output to underlying writer
        return EVAL_BODY_INCLUDE;
    }

    /**
     * Does nothing
     */
    public void doCatch(Throwable t) throws Throwable {
        SessionLookup.rollback(localSession);
        throw t;
    }

    /**
     * Remove the session as a scoped attribute (if done), and close it if this tag created it.
     */
    public void doFinally() {
        //Remove it as an attribute
        if (var != null) {
            pageContext.removeAttribute(var, scope);
        }
        try {
            SessionLookup.close(localSession, this);
        } catch (JspException je) {
            //This is a bad pattern
            //The only saving grace is that if there was an error we would potentially
            //  swallow, it would have been handled by doCatch.
            //Nonetheless, this is doing the flush and we need to report it
            pageContext.getServletContext().log(je, "Error closing session");
            throw new RuntimeException(je.getMessage());
        }
    }

    /**
     * The Session this tag provides or exposes.
     */
    public LocalSession getLocalSession() {
        return localSession;
    }

}
