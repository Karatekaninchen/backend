package sc.helpers

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.KXml2Driver
import sc.protocol.helpers.LobbyProtocol
import java.security.AccessController
import java.security.PrivilegedAction

/*
* Using the KXml2 parser because the default (Xpp3) and StAX can't parse some special characters in attribute values:
* <protocol><authenticate passphrase="examplepassword"/>
* <prepare gameType="swc_2018_hase_und_igel">
*   <slot displayName="HÃ¤schenschule" canTimeout="true"/>
*   <slot displayName="Testhase" canTimeout="true"/>
* </prepare>
*/
val xStream = XStream(KXml2Driver()).apply {
    setMode(XStream.NO_REFERENCES)
    classLoader =  AccessController.doPrivileged(PrivilegedAction<RuntimeJarLoader?> { RuntimeJarLoader(classLoader) })
    
    LobbyProtocol.registerMessages(this)
}
