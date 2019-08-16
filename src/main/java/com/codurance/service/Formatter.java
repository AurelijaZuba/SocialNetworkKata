package com.codurance.service;

import com.codurance.model.Message;

public interface Formatter {

    String readFormatter(Message message);
    String wallFormatter(Message message);
}
