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
import javax.servlet.jsp.JspException;
import org.hibernate.HibernateException;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;


/**
 * Effectively wraps Session.update() functionality.
 */
public class UpdateTag extends OperationTagSupport {
    private String targetEL = null;
    private String identifierEL = null;

    /**
     * Specify the value to be updated in EL.
     */
    public void setTarget(String value) {
        targetEL = value;
    }

    /**
     * Specify the target to be updated in EL
     */
    public void setIdentifier(String value) {
        identifierEL = value;
    }

    /**
     * Find the object, the session, and do the update.
     */
    public int doEndTag() throws JspException {
        LocalSession localSession = null;
        boolean created = false;
        //System.out.println("Update");
        try {
            //Let's figure out what it is we're supposed to save or persist
            Object target = (Object) ExpressionEvaluatorManager.evaluate("target", targetEL,
                   Object.class, this, pageContext);

            //If this is null, complain
            if (target == null) {
                throw new JspException("Update target " + targetEL + " evaluated to null");
            }

            // if the identifier was supplied, we'll use that one
            Serializable identifier = null;
            if (identifierEL != null) {
                identifier = (Serializable) ExpressionEvaluatorManager.evaluate("identifier",
                        identifierEL, Serializable.class, this, pageContext);
            }

            //First find the session
            localSession = SessionLookup.open(this);

            //Now try to update this, possibly with the identifier specified.
            try {
                
                if (identifier == null) {
                     localSession.getSession().update(target);
                } else {
                     localSession.getSession().update(target, identifier);
                }
                
            } catch (HibernateException he) {
                SessionLookup.rollback(localSession);
                //System.out.println("rollback update");
                StringBuffer message = new StringBuffer()
                    .append("Error updating ")
                    .append(target);
                if (identifier != null) {
                    message.append(", identifier ")
                        .append(identifier);
                }
                message.append(": ")
                    .append(he.getMessage());
                throw new JspException(message.toString(), he);
            }
        } finally {
            SessionLookup.close(localSession, this);
        }
        return super.doEndTag();
    }
}
