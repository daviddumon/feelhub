package com.feelhub.domain.cloudinary;

import com.feelhub.tools.FeelhubApplicationProperties;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class CloudinaryLink {

    public CloudinaryLink() {
        final FeelhubApplicationProperties feelhubApplicationProperties = new FeelhubApplicationProperties();
        apiKey = feelhubApplicationProperties.getCloudinaryApiKey();
        apiSecret = feelhubApplicationProperties.getCloudinaryApiSecret();
        cloudinaryName = feelhubApplicationProperties.getCloudinaryName();
    }

    public String getIllustration(final Map<String, String> params) throws IOException {
        buildUploadParams(params);
        final String responseData = getResponseData(params);
        return extractIllustrationFromJsonResponse(responseData);
    }

    private void buildUploadParams(final Map<String, String> params) {
        params.put("timestamp", new Long(System.currentTimeMillis() / 1000L).toString());
        try {
            params.put("signature", apiSignRequest(params));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        params.put("api_key", apiKey);
    }

    private String getResponseData(final Map<String, String> params) throws IOException {
        final HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30 * 1000);
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30 * 1000);
        final HttpPost postMethod = new HttpPost(cloudinaryApiUrl());
        setEntityForRequest(params, postMethod);
        final HttpResponse response = client.execute(postMethod);
        return readFully(response.getEntity().getContent());
    }

    private void setEntityForRequest(final Map<String, String> params, final HttpPost postMethod) throws UnsupportedEncodingException {
        final MultipartEntity multipart = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        for (final Map.Entry<String, String> param : params.entrySet()) {
            if (StringUtils.isNotBlank(param.getValue())) {
                multipart.addPart(param.getKey(), new StringBody(param.getValue(), Charset.forName("UTF-8")));
            }
        }
        postMethod.setEntity(multipart);
    }

    private static String readFully(final InputStream in) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = in.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return new String(baos.toByteArray());
    }

    private String cloudinaryApiUrl() {
        final String cloudinary = "https://api.cloudinary.com";
        final String resource_type = "image";
        return StringUtils.join(new String[]{cloudinary, "v1_1", cloudinaryName, resource_type, "upload"}, "/");
    }

    private String apiSignRequest(final Map<String, String> paramsToSign) throws NoSuchAlgorithmException {
        final String to_sign = getRequestStringToSign(paramsToSign);
        final MessageDigest md = MessageDigest.getInstance("SHA-1");
        return Hex.encodeHexString(md.digest((to_sign + apiSecret).getBytes()));
    }

    private String getRequestStringToSign(final Map<String, String> paramsToSign) {
        final Collection<String> params = new ArrayList<String>();
        for (final Map.Entry<String, String> param : new TreeMap<String, String>(paramsToSign).entrySet()) {
            if (StringUtils.isNotBlank(param.getValue()) && !param.getKey().equalsIgnoreCase("file")) {
                params.add(param.getKey() + "=" + param.getValue());
            }
        }
        return StringUtils.join(params, "&");
    }

    private String extractIllustrationFromJsonResponse(final String responseData) {
        try {
            final Map<String, Object> result = (Map<String, Object>) JSONValue.parseWithException(responseData);
            if (result.containsKey("error")) {
                throw new CloudinaryException("Error with cloudinary " + result.get("error").toString());
            }
            return result.get("secure_url").toString();
        } catch (ParseException e) {
            LOGGER.error(responseData);
            throw new CloudinaryException(responseData, e);
        }
    }

    private final String apiKey;
    private final String apiSecret;
    private final String cloudinaryName;
    private static final Logger LOGGER = LoggerFactory.getLogger(CloudinaryLink.class);
}

