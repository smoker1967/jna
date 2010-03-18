/* Copyright (c) 2007 Timothy Wall, All Rights Reserved
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 */
package com.sun.jna.platform.win32;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinNT.PSID;
import com.sun.jna.platform.win32.WinNT.PSIDByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.W32APIOptions;

/**
 * Advapi32.dll Interface.
 * @author dblock[at]dblock.org
 */
public interface Advapi32 extends W32API {
	Advapi32 INSTANCE = (Advapi32) Native.loadLibrary("Advapi32",
			Advapi32.class, W32APIOptions.UNICODE_OPTIONS);

	/**
	 * Retrieves the name of the user associated with the current thread.
	 * http://msdn.microsoft.com/en-us/library/ms724432(VS.85).aspx
	 * 
	 * @param buffer
	 *  Buffer to receive the user's logon name.
	 * @param len
	 *  On input, the size of the buffer, on output the number of
	 *  characters copied into the buffer, including the terminating
	 *  null character.
	 * @return 
	 *  True if succeeded.
	 */
	public boolean GetUserNameW(char[] buffer, IntByReference len);

	/**
	 * Accepts the name of a system and anaccount as input and retrieves a security 
	 * identifier (SID) for the account and the name of the domain on which the 
	 * account was found.
	 * http://msdn.microsoft.com/en-us/library/aa379159(VS.85).aspx
	 * 
	 * @param lpSystemName
	 *  Specifies the name of the system.
	 * @param lpAccountName
	 *  Specifies the account name.
	 * @param Sid
	 *  Receives the SID structure that corresponds to the account
	 *  name pointed to by the lpAccountName parameter.
	 * @param cbSid
	 *  On input, this value specifies the size, in bytes, of the Sid
	 *  buffer. If the function fails because the buffer is too small
	 *  or if cbSid is zero, this variable receives the required
	 *  buffer size.
	 * @param ReferencedDomainName
	 *  Receives the name of the domain where the account name is found.
	 * @param cchReferencedDomainName
	 *  On input, this value specifies the size, in TCHARs, of the
	 *  ReferencedDomainName buffer. If the function fails because the
	 *  buffer is too small, this variable receives the required
	 *  buffer size, including the terminating null character.
	 * @param peUse
	 *  SID_NAME_USE enumerated type that indicates the type of the
	 *  account when the function returns.
	 * @return 
	 *  True if the function was successful, False otherwise.
	 */
	public boolean LookupAccountName(String lpSystemName,
			String lpAccountName, PSID Sid, IntByReference cbSid,
			char[] ReferencedDomainName,
			IntByReference cchReferencedDomainName, PointerByReference peUse);

	/**
	 * Retrieves the name of the account for this SID and the name of the first domain 
	 * on which this SID is found.
	 * 
	 * @param lpSystemName Specifies the target computer.
	 * @param Sid The SID to look up. 
	 * @param lpName
	 *  Buffer that receives a null-terminated string that contains the 
	 * 	account name that corresponds to the lpSid parameter. 
	 * @param cchName 
	 * 	On input, specifies the size, in TCHARs, of the lpName buffer. If the function fails 
	 *  because the buffer is too small or if cchName is zero, cchName receives the required 
	 *  buffer size, including the terminating null character. 
	 * @param ReferencedDomainName
	 *  Pointer to a buffer that receives a null-terminated string that contains the name of 
	 *  the domain where the account name was found.
	 * @param cchReferencedDomainName
	 * 	On input, specifies the size, in TCHARs, of the lpReferencedDomainName buffer. If the 
	 * 	function fails because the buffer is too small or if cchReferencedDomainName is zero, 
	 * 	cchReferencedDomainName receives the required buffer size, including the terminating 
	 * 	null character. 
	 * @param peUse
	 *  Pointer to a variable that receives a SID_NAME_USE value that indicates the type of 
	 *  the account.
	 * @return 
	 *  If the function succeeds, the function returns nonzero.
	 *  If the function fails, it returns zero. To get extended error information, call 
	 *  GetLastError.
	 */
	public boolean LookupAccountSid(String lpSystemName, PSID Sid,
			char[] lpName, IntByReference cchName,  char[] ReferencedDomainName,
	        IntByReference cchReferencedDomainName, PointerByReference peUse);
	
	/**
	 * Convert a security identifier (SID) to a string format suitable for display, 
	 * storage, or transmission.
	 * http://msdn.microsoft.com/en-us/library/aa376399(VS.85).aspx
	 * 
	 * @param Sid
	 *            The SID structure to be converted.
	 * @param StringSid
	 *            Pointer to a variable that receives a pointer to a
	 *            null-terminated SID string. To free the returned buffer, call
	 *            the LocalFree function.
	 * @return True if the function was successful, False otherwise.
	 */
	public boolean ConvertSidToStringSid(PSID Sid,
			PointerByReference StringSid);	
	
	/**
	 * Convert a string-format security identifier (SID) into a valid, functional SID.
	 * http://msdn.microsoft.com/en-us/library/aa376402(VS.85).aspx
	 * 
	 * @param StringSid The string-format SID to convert.
	 * @param Sid Receives a pointer to the converted SID.
	 * @return True if the function was successful, False otherwise.
	 */
	public boolean ConvertStringSidToSid(String StringSid,
			PSIDByReference Sid);
	
	/**
	 * Returns the length, in bytes, of a valid security identifier (SID).
	 * http://msdn.microsoft.com/en-us/library/aa446642(VS.85).aspx
	 * 
	 * @param sid A pointer to the SID structure whose length is returned.
	 * @return Length of the SID.
	 */
	public int GetLengthSid(PSID pSid);
	
	/**
	 * The IsValidSid function validates a security identifier (SID) by verifying that 
	 * the revision number is within a known range, and that the number of subauthorities 
	 * is less than the maximum.
	 * @param pSid 
	 *  Pointer to the SID structure to validate. This parameter cannot be NULL.
	 * @return
	 *  If the SID structure is valid, the return value is nonzero.
	 *  If the SID structure is not valid, the return value is zero. There is no extended 
	 *  error information for this function; do not call GetLastError.
	 */
	public boolean IsValidSid(PSID pSid);
	
	/**
	 * The LogonUser function attempts to log a user on to the local computer. The local computer is
	 * the computer from which LogonUser was called. You cannot use LogonUser to log on to a remote
	 * computer. You specify the user with a user name and domain, and authenticate the user with a 
	 * plaintext password. If the function succeeds, you receive a handle to a token that represents 
	 * the logged-on user. You can then use this token handle to impersonate the specified user or, 
	 * in most cases, to create a process that runs in the context of the specified user.
	 * @param lpszUsername
	 *  A pointer to a null-terminated string that specifies the name of the user. This is the name of 
	 *  the user account to log on to. If you use the user principal name (UPN) format, 
	 *  user@DNS_domain_name, the lpszDomain parameter must be NULL. 
	 * @param lpszDomain
	 *  A pointer to a null-terminated string that specifies the name of the domain or server whose 
	 *  account database contains the lpszUsername account. If this parameter is NULL, the user name 
	 *  must be specified in UPN format. If this parameter is ".", the function validates the account 
	 *  using only the local account database. 
	 * @param lpszPassword
	 *  A pointer to a null-terminated string that specifies the plaintext password for the user 
	 *  account specified by lpszUsername. 
	 * @param logonType
	 *  The type of logon operation to perform.
	 * @param logonProvider
	 *  Specifies the logon provider.
	 * @param phToken
	 *  A pointer to a handle variable that receives a handle to a token that represents the specified user. 
	 * @return
	 *  If the function succeeds, the function returns nonzero.
	 *  If the function fails, it returns zero. To get extended error information, call GetLastError.
	 */
	public boolean LogonUser(
			String lpszUsername,
			String lpszDomain,
			String lpszPassword,
			int logonType,
			int logonProvider,
			HANDLEByReference phToken);	
	
	/**
	 * The OpenThreadToken function opens the access token associated with a thread.
	 * @param ThreadHandle
	 *  Handle to the thread whose access token is opened. 
	 * @param DesiredAccess
	 *  Specifies an access mask that specifies the requested types of access to the access token. 
	 *  These requested access types are reconciled against the token's discretionary access 
	 *  control list (DACL) to determine which accesses are granted or denied. 
	 * @param OpenAsSelf
	 *  Indicates whether the access check is to be made against the security context of the 
	 *  thread calling the OpenThreadToken function or against the security context of the 
	 *  process for the calling thread. 
	 * @param TokenHandle
	 *  Pointer to a variable that receives the handle to the newly opened access token.
	 * @return
	 *  If the function succeeds, the return value is nonzero.
	 *  If the function fails, the return value is zero. To get extended error information, 
	 *  call GetLastError. 
	 */
	public boolean OpenThreadToken(
			HANDLE ThreadHandle,
			int DesiredAccess,
			boolean OpenAsSelf,
			HANDLEByReference TokenHandle);
	
	/**
	 * The OpenProcessToken function opens the access token associated with a process.
	 * @param ProcessHandle
	 *  Handle to the process whose access token is opened. The process must have the 
	 *  PROCESS_QUERY_INFORMATION access permission. 
	 * @param DesiredAccess
	 *  Specifies an access mask that specifies the requested types of access to the access 
	 *  token. These requested access types are compared with the discretionary access 
	 *  control list (DACL) of the token to determine which accesses are granted or denied. 
	 * @param TokenHandle
	 *  Pointer to a handle that identifies the newly opened access token when the function returns. 
	 * @return
	 *  If the function succeeds, the return value is nonzero.
	 *  If the function fails, the return value is zero. To get extended error information, 
	 *  call GetLastError.
	 */
	public boolean OpenProcessToken(
			HANDLE ProcessHandle,
			int DesiredAccess,
			HANDLEByReference TokenHandle);

	/**
	 * The DuplicateToken function creates a new access token that duplicates 
	 * one already in existence.
	 * 
	 * @param ExistingTokenHandle
	 *  Handle to an access token opened with TOKEN_DUPLICATE access. 
	 * @param ImpersonationLevel
	 *  Specifies a SECURITY_IMPERSONATION_LEVEL enumerated type that supplies 
	 *  the impersonation level of the new token. 
	 * @param DuplicateTokenHandle
	 *  Pointer to a variable that receives a handle to the duplicate token. 
	 *  This handle has TOKEN_IMPERSONATE and TOKEN_QUERY access to the new token. 
	 * @return
	 *  If the function succeeds, the return value is nonzero.
	 *  If the function fails, the return value is zero. To get extended error information, 
	 *  call GetLastError.
	 */
	public boolean DuplicateToken(
			HANDLE ExistingTokenHandle,
			int ImpersonationLevel, 
			HANDLEByReference DuplicateTokenHandle);
	
	
	/**
	 * Retrieves a specified type of information about an access token. 
	 * The calling process must have appropriate access rights to obtain the information.

	 * @param tokenHandle
	 *  Handle to an access token from which information is retrieved. If TokenInformationClass
	 *  specifies TokenSource, the handle must have TOKEN_QUERY_SOURCE access. For all other 
	 *  TokenInformationClass values, the handle must have TOKEN_QUERY access. 
	 * @param tokenInformationClass
	 *  Specifies a value from the TOKEN_INFORMATION_CLASS enumerated type to identify the type of 
	 *  information the function retrieves. 
	 * @param tokenInformation
	 *  Pointer to a buffer the function fills with the requested information. The structure put 
	 *  into this buffer depends upon the type of information specified by the TokenInformationClass 
	 *  parameter.
	 * @param tokenInformationLengt
	 *  Specifies the size, in bytes, of the buffer pointed to by the TokenInformation parameter. 
	 *  If TokenInformation is NULL, this parameter must be zero. 
	 * @param returnLength
	 *  Pointer to a variable that receives the number of bytes needed for the buffer pointed to by 
	 *  the TokenInformation parameter. If this value is larger than the value specified in the 
	 *  TokenInformationLength parameter, the function fails and stores no data in the buffer.
	 * @return
	 *  If the function succeeds, the return value is nonzero.
	 *  If the function fails, the return value is zero. To get extended error information, call GetLastError.
	 */
	public boolean GetTokenInformation(
			HANDLE tokenHandle,
			int tokenInformationClass,
			Structure tokenInformation,
			int tokenInformationLength,
			IntByReference returnLength);
	
	/**
	 * The ImpersonateLoggedOnUser function lets the calling thread impersonate the 
	 * security context of a logged-on user. The user is represented by a token 
	 * handle.
	 * @param hToken
	 *  Handle to a primary or impersonation access token that represents a logged-on 
	 *  user. This can be a token handle returned by a call to LogonUser, 
	 *  CreateRestrictedToken, DuplicateToken, DuplicateTokenEx, OpenProcessToken, 
	 *  or OpenThreadToken functions. If hToken is a primary token, it must have 
	 *  TOKEN_QUERY and TOKEN_DUPLICATE access. If hToken is an impersonation token, 
	 *  it must have TOKEN_QUERY and TOKEN_IMPERSONATE access. 
	 * @return
	 *  If the function succeeds, the return value is nonzero.
	 */
	public boolean ImpersonateLoggedOnUser(
			HANDLE hToken); 
	
	/**
	 * The RevertToSelf function terminates the impersonation of a client application.
	 * @return If the function succeeds, the return value is nonzero. 
	 */
	public boolean RevertToSelf();
}