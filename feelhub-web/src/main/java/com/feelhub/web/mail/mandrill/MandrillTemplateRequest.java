package com.feelhub.web.mail.mandrill;

import com.google.common.collect.Lists;

import java.util.List;

public class MandrillTemplateRequest {

    public String template_name;
    public MandrillMessage message;
    public boolean async;
    public String key;

    public List<String> template_content = Lists.newArrayList();
}
