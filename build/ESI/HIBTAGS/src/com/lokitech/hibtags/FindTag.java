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

import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.core.Util;

/**
 * Effectively wraps Session.find() functionality.  However, it does this using the Query object
 * so finer grained control over the query is available, returning list().
 */
public class FindTag extends OperationParamTagSupport {
    private String var = null;
    private int scope = PageContext.PAGE_SCOPE;
    private String firstResultEL = null;
    private String maxResultsEL = null;

    /**
     * Store the results (List) in a scoped attribute.
     */
    public void setVar(String value) {
        var = value;
    }

    /**
     * Specify what scope to store the results are in.
     */
    public void setScope(String value) {
        scope = Util.getScope(value);
    }

    /**
     * Optionally specify the first result in EL.
     */
    public void setFirstResult(String valueEL) {
        firstResultEL = valueEL;
    }

    /**
     * Optionally specify the max results in EL.
     */
    public void setMaxResults(String valueEL) {
        maxResultsEL = valueEL;
    }

    public int doStartTag() throws JspException {
        resetParameters();
        return super.doStartTag();
    }

    /**
     * Create the query object, apply the parameters, and go from there.
     */
    public int doEndTag() throws JspException {
        LocalSession localSession = null;
        boolean created = false;
        List results = null;
        Query query = null;

        try {
            //Now create the query
            String queryString = null;
            if (bodyContent != null) {
                queryString = bodyContent.getString().trim();
                if (queryString.length() == 0) {
                    queryString = null;
                }
                //<<
                else{
                  queryString=(String) ExpressionEvaluatorManager.evaluate(
                    var,
                    queryString,
                    String.class,
                    pageContext
                  );
                }
                //>>
            }
            if (queryString == null) {
                throw new JspException("No HQL provided in <hib:find>.");
            }
            //System.out.println("'find' queryString: "+queryString);
            //Find the session
            localSession = SessionLookup.open(this);

            //Create the query
            try {
                query = localSession.getSession().createQuery(queryString);
            } catch (HibernateException he) {
                SessionLookup.rollback(localSession);

                StringBuffer message = new StringBuffer()
                    .append("Could not parse HQL:\r\n")
                    .append(queryString)
                    .append("\r\nError: ")
                    .append(he.getMessage());
                throw new JspException(message.toString(), he);
            }

            //Set the parameters
            for (int i = 0; i < paramValues.size(); i++) {
                try {
                    query.setParameter(i, paramValues.get(i));
                } catch (HibernateException he) {
                    SessionLookup.rollback(localSession);

                    StringBuffer message = new StringBuffer()
                        .append("Error while setting parameter (")
                        .append(i)
                        .append(" of ")
                        .append(paramValues.size())
                        .append(": ")
                        .append(he.getMessage());
                    throw new JspException(message.toString(), he);
                }
            }
            if (firstResultEL != null) {
                Integer firstResult = (Integer)
                        ExpressionEvaluatorManager.evaluate("firstResult", firstResultEL,
                        Integer.class, this, pageContext);
                query.setFirstResult(firstResult.intValue());
            }
            if (maxResultsEL != null) {
                Integer maxResults = (Integer)
                        ExpressionEvaluatorManager.evaluate("maxResults", maxResultsEL,
                        Integer.class, this, pageContext);
                query.setMaxResults(maxResults.intValue());
            }

            try {
                results = query.list();
            } catch (HibernateException he) {
                SessionLookup.rollback(localSession);

                StringBuffer message = new StringBuffer()
                    .append("Error executing HQL: \r\n")
                    .append(queryString)
                    .append("\r\nError: ")
                    .append(he.getMessage());
                throw new JspException(message.toString(), he);
            }

            pageContext.setAttribute(var, results, scope);

        } finally {
            SessionLookup.close(localSession, this);
        }
        return super.doEndTag();
    }
}
