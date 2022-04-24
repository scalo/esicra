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
import java.math.BigDecimal;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import org.hibernate.HibernateException;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.IdentifierType;
import org.hibernate.type.Type;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.core.Util;

/**
 * Effectively wraps Session.load(Class, id) functionality.  This is a convenient way to
 * do something such as converting request.getParameter() to the objects.  Ideally it's
 * only creating a proxy instance of it, but what can you do?
 */
public class LoadTag extends OperationTagSupport {
    
    private String var = null;
    private int scope = PageContext.PAGE_SCOPE;
    private String className = null;
    private String valueEL = null;

    /**
     * Specify the attribute name to store this as.
     */
    public void setVar(String value) {
        var = value;
    }

    /**
     * Specify what scope to store the Object in
     */
    public void setScope(String value) {
        scope = Util.getScope(value);
    }

    /**
     * Specify the class that we use
     */
    public void setClassname(String value){
        className = value;
    }

    /**
     * Specify the EL for the ID
     * (aggiunta valutazione EL)
     */
    public void setValue(String value) throws JspException {  
        valueEL=(String) ExpressionEvaluatorManager.evaluate(
          var,
          value,
          String.class,
          pageContext
        );
        //valueEL = value;
    }

    /**
     * Create the query object, apply the parameters, and go from there.
     */
    public int doEndTag() throws JspException {
        LocalSession localSession = null;
        boolean created = false;
        //System.out.println("load class: "+className);  
        try {
            Class clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException cnfe) {
                StringBuffer message = new StringBuffer()
                    .append("Class ")
                    .append(className)
                    .append(" could not be loaded.  Please check your classpath.");
                throw new JspException(message.toString(), cnfe);
            }

            //Find the session
            localSession = SessionLookup.open(this);
          
            //We want to determine the type of class we need, and then evaluate to that
            Class idClazz = Serializable.class;
            try {
                ClassMetadata clazzMD = localSession.getSessionFactory().getClassMetadata(clazz);
                if (clazzMD == null) {
                    SessionLookup.rollback(localSession);

                    //This is not a class that can be serialized in this session.
                    StringBuffer message = new StringBuffer()
                        .append("Class ")
                        .append(className)
                        .append(" is not handled by this session (")
                        //.append(getSessionFactory())
                        .append(").  Please check your mapping files.");
                    throw new JspException(message.toString());
                }
                Type clazzType = clazzMD.getIdentifierType();
                /*
                if (!(clazzType instanceof IdentifierType)) {
                    SessionLookup.rollback(localSession);

                    //out.println("bad, bad bad");
                    StringBuffer message = new StringBuffer()
                        .append("Identifier for class (")
                        .append(className)
                        .append(") is not org.hibernate.IdentifierType");
                    throw new JspException(message.toString());
                }
                */
                idClazz = clazzType.getReturnedClass();
            } catch (HibernateException he) {
                SessionLookup.rollback(localSession);

                //Should only happen from getting class metadata.
                //When we use a logging framework, save this as info or debug
                //System.err.println(he);
                throw new JspException("Unknown error during <hib:load>" + he.getMessage(), he);
            }
            //System.out.println(idClazz.getName());
            Serializable id=null;
            if(idClazz.getName().endsWith("BigDecimal") ){
              id=new BigDecimal(valueEL);
              //System.out.println("BigDecimal:" + id);
            }else{
             id= (Serializable) ExpressionEvaluatorManager.evaluate("value", valueEL,
                    idClazz, this, pageContext);
            }

            if (id == null) {
                SessionLookup.rollback(localSession);

                StringBuffer message = new StringBuffer()
                    .append("Value of ")
                    .append(valueEL)
                    .append("evaluated to null, which is not a valid primary key for ")
                    .append(className);
                throw new JspException(message.toString());
            }

            //Now try to load this
            Object obj = null;
            try {
                obj = localSession.getSession().load(clazz, id);
            } catch (HibernateException he) {
                SessionLookup.rollback(localSession);

                StringBuffer message = new StringBuffer()
                    .append("Error loading ")
                    .append(className)
                    .append(" with primary key of ")
                    .append(id)
                    .append(" (")
                    .append(valueEL)
                    .append(") :")
                    .append(he.getMessage());
                throw new JspException(message.toString(), he);
            }
            //Store the object as an attribute
            pageContext.setAttribute(var, obj, scope);
        } finally {
            SessionLookup.close(localSession, this);
        }
        return super.doEndTag();
    }
}
