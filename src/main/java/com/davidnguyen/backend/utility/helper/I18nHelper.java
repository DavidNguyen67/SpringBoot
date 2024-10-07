package com.davidnguyen.backend.utility.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;


@Service
public class I18nHelper {
    private final ResourceBundle bundle;

    @Autowired
    public I18nHelper(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String getMessage(String code) {
        return bundle.getString(code);
    }
}