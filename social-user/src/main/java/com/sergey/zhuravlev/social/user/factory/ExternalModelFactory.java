package com.sergey.zhuravlev.social.user.factory;

import com.sergey.zhuravlev.social.model.user.UserModel;
import com.sergey.zhuravlev.social.user.jpa.entity.User;

public class ExternalModelFactory {

    public static UserModel toUserModel(User user) {
        if (user == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setEmail(user.getEmail());
        userModel.setPhone(user.getPhone());
        userModel.setCreateAt(user.getCreateAt());
        userModel.setUpdateAt(user.getUpdateAt());
        return userModel;
    }

}
