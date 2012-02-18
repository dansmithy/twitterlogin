package com.github.dansmithy.twitterlogin;

import com.github.dansmithy.twitterlogin.service.scribe.ConfigurableTwitterApi;

public class ATUtils {

    public static int getRestDriverPort() {
        return Integer.parseInt(ConfigurableTwitterApi.getRestDriverPort());
    } 
}
