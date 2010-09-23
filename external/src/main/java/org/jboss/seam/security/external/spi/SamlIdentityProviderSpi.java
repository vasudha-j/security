/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.seam.security.external.spi;

import org.jboss.seam.security.external.api.ResponseHolder;
import org.jboss.seam.security.external.saml.api.SamlIdentityProviderApi;
import org.jboss.seam.security.external.saml.api.SamlIdpSession;

/**
 * Interface that needs to be implemented by applications that want to act as a
 * SAML identity provider. It is the counterpart of the
 * {@link SamlIdentityProviderApi}.
 * 
 * Most methods in this interface have a responseHolder parameter, which
 * contains the HTTP response. This is a way of handing over the control over
 * the browser to the application. The application is responsible for writing
 * the response (either a normal HTML response, or an error, or a redirect).
 * Typically, the application will redirect the user to a URL within the
 * application.
 * 
 * @author Marcel Kolsteren
 * 
 */
public interface SamlIdentityProviderSpi
{
   /**
    * This method is called after receipt of an authentication request from a
    * service provider. Upon receipt of this call, the application should try to
    * authenticate the user, or, if the user is already logged in, reuse an
    * existing session of the user. The result of the authentication needs to be
    * reported back using the API calls
    * {@link SamlIdentityProviderApi#authenticationSucceeded} or
    * {@link SamlIdentityProviderApi#authenticationFailed}. Those API calls
    * should be called in the same dialogue as this SPI call. When redirecting
    * the user to a page where she can be authenticated, it is convenient to use
    * {@link ResponseHolder#redirectWithDialoguePropagation(String)}, so that
    * the current dialogue is automatically propagated to the next request.
    * 
    * @param responseHolder object holding the HTTP servlet response
    */
   void authenticate(ResponseHolder responseHolder);

   /**
    * When the service provider receives a logout request from a service
    * provider, this method is called. The implementation of this method must
    * take for granted that the user has been logged out.
    * 
    * @param session that has been removed
    */
   void loggedOut(SamlIdpSession session);

   /**
    * This method is the asynchronous callbacks related to
    * {@link SamlIdentityProviderApi#globalLogout()}. It is called when the
    * global logout was successful. Before this callback is called, the dialogue
    * that was active at the time of the API call is restored. An implementation
    * of this method will typically redirect the user to a page where a message
    * is shown that the user has been logged out.
    * 
    * @param responseHolder object holding the HTTP servlet response
    */
   void globalLogoutSucceeded(ResponseHolder responseHolder);

   /**
    * <p>
    * This method is one of the asynchronous callbacks related to
    * {@link SamlIdentityProviderApi#globalLogout}. It is called when the single
    * logout was unsuccessful. Before this callback is called, the dialogue that
    * was active at the time of the API call is restored. An implementation of
    * this method will typically redirect the user to a page where a message is
    * shown that the user could not be logged out.
    * </p>
    * 
    * <p>
    * The fact that the single logout failed doesn't mean that all parts of the
    * single logout failed. Possibly only one of the session participants
    * couldn't perform a successful logout, while the others could.
    * </p>
    * 
    * @param responseHolder object holding the HTTP servlet response
    */
   void globalLogoutFailed(ResponseHolder responseHolder);
}
