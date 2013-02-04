package com.feelhub.domain.cloudinary;

import com.feelhub.tools.FeelhubApplicationProperties;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.*;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONValue;

import java.io.*;
import java.security.*;
import java.util.*;

public class CloudinaryLink {

    public CloudinaryLink() {
        final FeelhubApplicationProperties feelhubApplicationProperties = new FeelhubApplicationProperties();
        apiKey = feelhubApplicationProperties.getCloudinaryApiKey();
        apiSecret = feelhubApplicationProperties.getCloudinaryApiSecret();
        cloudinaryName = feelhubApplicationProperties.getCloudinaryName();
    }

    public String getIllustration(Map<String, String> params) throws IOException {
        buildUploadParams(params);
        String responseData = getResponseData(params);
        return extractIllustrationFromJsonResponse(responseData);
    }

    private void buildUploadParams(Map<String, String> params) {
        params.put("timestamp", new Long(System.currentTimeMillis() / 1000L).toString());
        try {
            params.put("signature", apiSignRequest(params));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        params.put("api_key", apiKey);
    }

    private String getResponseData(final Map<String, String> params) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost postMethod = new HttpPost(cloudinaryApiUrl());
        setEntityForRequest(params, postMethod);
        HttpResponse response = client.execute(postMethod);
        return readFully(response.getEntity().getContent());
    }

    private void setEntityForRequest(final Map<String, String> params, final HttpPost postMethod) throws UnsupportedEncodingException {
        MultipartEntity multipart = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (StringUtils.isNotBlank(param.getValue())) {
                multipart.addPart(param.getKey(), new StringBody(param.getValue()));
            }
        }
        postMethod.setEntity(multipart);
    }

    private static String readFully(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = in.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return new String(baos.toByteArray());
    }

    private String cloudinaryApiUrl() {
        String cloudinary = "https://api.cloudinary.com";
        String resource_type = "image";
        return StringUtils.join(new String[]{cloudinary, "v1_1", cloudinaryName, resource_type, "upload"}, "/");
    }

    private String apiSignRequest(Map<String, String> paramsToSign) throws NoSuchAlgorithmException {
        String to_sign = getRequestStringToSign(paramsToSign);
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        return Hex.encodeHexString(md.digest((to_sign + apiSecret).getBytes()));
    }

    private String getRequestStringToSign(final Map<String, String> paramsToSign) {
        Collection<String> params = new ArrayList<String>();
        for (Map.Entry<String, String> param : new TreeMap<String, String>(paramsToSign).entrySet()) {
            if (StringUtils.isNotBlank(param.getValue())) {
                params.add(param.getKey() + "=" + param.getValue());
            }
        }
        return StringUtils.join(params, "&");
    }

    private String extractIllustrationFromJsonResponse(final String responseData) {
        try {
            Map<String, String> result = (Map<String, String>) JSONValue.parseWithException(responseData);
            if (result.containsKey("error")) {
                throw new CloudinaryException();
            }
            return result.get("secure_url");
        } catch (Exception e) {
            throw new CloudinaryException();
        }
    }

    private final String apiKey;
    private final String apiSecret;
    private final String cloudinaryName;
}

