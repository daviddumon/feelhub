package com.feelhub.web.mail.mandrill;

import com.google.common.collect.Lists;

import java.util.List;

public class MandrillMessage {

    public List<MandrillRecipient> to = Lists.newArrayList();
    public String subject;
    public String from_email;
    public List<MergeVar> global_merge_vars = Lists.newArrayList();
    public List<String> tags = Lists.newArrayList();

    public void addMergeVar(final String name, final String content) {
        global_merge_vars.add(new MergeVar(name, content));
    }

    public void addTag(final String tag) {
        tags.add(tag);
    }
}
