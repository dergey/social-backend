package com.sergey.zhuravlev.social.util;

import com.sergey.zhuravlev.social.entity.Profile;

public class SearchStringUtils {

    public static String getSearchString(Profile profile) {
        StringBuilder sb = new StringBuilder();
        sb.append(profile.getFirstName()).append(" ").append(profile.getSecondName());
        if (profile.getMiddleName() != null) {
            sb.append(" ").append(profile.getMiddleName());
        }
        sb.append(" ").append(profile.getUsername());
        return sb.toString().toUpperCase();
    }

}
