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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Utility class to get handles to session objects.  It uses the JNDI lookup for session factory
 * and creates a session from that.
 */
public class SessionLookup {
    /**
     * Optionally look for a session tag parent, and if not available, find the session factory and
     * open a session in the context of this tag.
     */
    public static LocalSession open(OperationTagSupport tag) throws JspException {
        //First manually look for a hib:session tag, and grab that session if it is available.

        //This means hib:session tags can nest themselves quietly.

        //Do this the easy way.
        SessionTag sessionTag = (SessionTag)
                TagSupport.findAncestorWithClass(tag, SessionTag.class);
        if (sessionTag != null) {
            //Woohoo!  just return this session
            LocalSession session = sessionTag.getLocalSession();
            //Only return this session if it's the same session factory
            if (session.getOwner().getSessionFactory().equals(tag.getSessionFactory())) {
                //System.out.println("old session");
                return session;
            }
        }
        //System.out.println("new session");
        //Need to create a new session
        return create(tag);
    }

    public static LocalSession create(OperationTagSupport tag) throws JspException {
        //TODO take an optional application init param as the JNDI location of the SessionFactory.
        /*
        SessionFactory sf = null;
        String name = "java:comp/env/" + tag.getSessionFactory();
        try {
            Context ctx = new InitialContext();
            sf = (SessionFactory) ctx.lookup(name);
        } catch (Throwable ne) {
            StringBuffer message = new StringBuffer()
                .append("Could not find session factory ")
                .append(name);
            throw new JspException(message.toString(), ne);
        }
        if (sf == null) {
            throw new JspException("There is no session factory at " + name);
        }
        */
        SessionFactory sf = HibernateFactory.currentSessionFactory();
        Session session = null;
        //This would be done with a tag
        try {
            session = sf.openSession();
            //session = HibernateUtil.currentSession();
        } catch (HibernateException he) {
            StringBuffer message = new StringBuffer()
                .append("Unable to open a hibernate session: ")
                .append(he.getMessage());
            throw new JspException(message.toString(), he);
        }
        
        return new LocalSession(sf, session, tag);
    }

    /**
     * Rolls back the current session, if I'm the owner of it.  Ignore all errors.  This
     * is what should be called if there is an exception in our tags.
     */
    public static void rollback(LocalSession localSession) {
        //Skip this if the session was closed already somehow.
        Session session = localSession.getSession();
        if (!session.isOpen()) {
            return;
        }
        //Rollback and close no matter what
        session.clear();
        try {
            session.connection().rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            session.close();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
    }

    /**
     * Flushes and closes this session if I'm the owner of it.
     */
    public static void close(LocalSession session, OperationTagSupport tag) throws JspException {
        
        //Close it if necessary
        if (session != null && session.getSession().isOpen() && tag != null && tag.equals(session.getOwner())) {
            //System.out.println("Owner "+session.getOwner());
            //We need to flush and close the session
            try {
                session.getSession().flush();
            } catch (HibernateException he) {
                StringBuffer message = new StringBuffer()
                    .append("Flush hibernate session error: ")
                    .append(he.getMessage());
                throw new JspException(message.toString(), he);
            }
            try {
                //System.out.println("close session");
                session.getSession().close();
                //HibernateUtil.closeSession();
            } catch (Exception he) {
                StringBuffer message = new StringBuffer()
                    .append("Close hibernate session error: ")
                    .append(he.getMessage());
                throw new JspException(message.toString( ), he);
            }
        }else{
          //System.out.println("not close");
        }
    }
}
