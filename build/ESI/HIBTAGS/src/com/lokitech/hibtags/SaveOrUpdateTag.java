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
import org.hibernate.HibernateException;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.hibernate.Transaction;

/**
 * Effectively wraps Session.saveOrUpdate() functionality.  This is most likely used as a way
 * to save a recently created object (persist for the first time), but it could also work as a
 * way to manually do a save or update.
 */
public class SaveOrUpdateTag extends OperationTagSupport {
    private String targetEL = null;

    /**
     * Specify the value to be saved or updated in EL.
     */
    public void setTarget(String value) {
        targetEL = value;
    }

    /**
     * Create the query object, apply the parameters, and go from there.
     */
    public int doEndTag() throws JspException {
        LocalSession localSession = null;
        boolean created = false;
        //System.out.println("Save/Update");
        try {
            //Let's figure out what it is we're supposed to save or persist
            Object target = (Object) ExpressionEvaluatorManager.evaluate("target", targetEL,
                   Object.class, this, pageContext);

            //If this is null, complain
            if (target == null) {
                throw new JspException("SaveOrUpdate target " + targetEL + " evaluated to null.");
            }

            //Find the session
            localSession = SessionLookup.open(this);

            //Now try to save or update this
            try {
                //Transaction tx = localSession.getSession().beginTransaction();
                localSession.getSession().saveOrUpdate(target);
                //tx.commit();
            } catch (HibernateException he) {
                SessionLookup.rollback(localSession);
                //System.out.println("rollback Save/Update");
                StringBuffer message = new StringBuffer()
                    .append("Error saving or updating target ")
                    .append(target)
                    .append(": ")
                    .append(he.getMessage());
                throw new JspException(message.toString(), he);
            }
        } finally {
            SessionLookup.close(localSession, this);
        }
        return super.doEndTag();
    }
}
