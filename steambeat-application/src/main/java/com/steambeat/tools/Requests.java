package com.steambeat.tools;

import org.restlet.Request;
import org.restlet.data.*;

import java.util.*;

public final class Requests {

    public static Request create(final Method method, final String address) {
        final Request request = new Request(method, address);
        Requests.clientInfo = new ClientInfo();
        setEncodings();
        setMediaTypes();
        setCharacters();
        request.setClientInfo(Requests.clientInfo);
        return request;
    }

    private static void setCharacters() {
        final List<Preference<CharacterSet>> characters = new ArrayList<Preference<CharacterSet>>();
        characters.add(new Preference<CharacterSet>(CharacterSet.UTF_8));
        characters.add(new Preference<CharacterSet>(CharacterSet.ISO_8859_1));
        Requests.clientInfo.setAcceptedCharacterSets(characters);
    }

    private static void setMediaTypes() {
        final List<Preference<MediaType>> mediaTypes = new ArrayList<Preference<MediaType>>();
        mediaTypes.add(new Preference<MediaType>(MediaType.TEXT_HTML));
        mediaTypes.add(new Preference<MediaType>(MediaType.TEXT_XML));
        mediaTypes.add(new Preference<MediaType>(MediaType.APPLICATION_XHTML));
        mediaTypes.add(new Preference<MediaType>(MediaType.APPLICATION_XML));
        Requests.clientInfo.setAcceptedMediaTypes(mediaTypes);
    }

    private static void setEncodings() {
        final List<Preference<Encoding>> encodings = new ArrayList<Preference<Encoding>>();
        encodings.add(new Preference<Encoding>(Encoding.GZIP));
        encodings.add(new Preference<Encoding>(Encoding.DEFLATE));
        Requests.clientInfo.setAcceptedEncodings(encodings);
    }

    private static ClientInfo clientInfo;

    private Requests() {

    }
}
