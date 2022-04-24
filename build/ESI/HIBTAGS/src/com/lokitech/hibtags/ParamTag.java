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

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

/**
 * Hibernate equivalent of sql:param.
 */
public class ParamTag extends BodyTagSupport {
    private String valueEL = null;

    /**
     * Sets the parameter value for the operation tag.
     */
    public void setValue(String valueEL) {
        this.valueEL = valueEL;
    }

    /**
     * Evaluate the value parameter, find the appropriate operation tag, and
     * add this parameter to it.  Also checks for 0-length or space-filled strings,
     * and makes them null.
     */
    public int doStartTag() throws JspException {
        Object value = null;
        if (valueEL != null) {
            value = (Object) ExpressionEvaluatorManager.evaluate("value", valueEL,
                   Object.class, this, pageContext);
        }
        OperationParamTagSupport parent = (OperationParamTagSupport)
            findAncestorWithClass(this, OperationParamTagSupport.class);
        if (parent == null) {
            throw new JspTagException("<hib:param> found outside an operation tag (<hib:find> or <hib:filter>).");
        }

        Object paramValue = null;
        if (value != null) {
            paramValue = value;
        } else if (bodyContent != null) {
            paramValue = bodyContent.getString().trim();
            if (((String) paramValue).trim().length() == 0) {
                paramValue = null;
            }
        }

        parent.addParameter(paramValue);
        return EVAL_PAGE;
    }

}
