package com.pals.cyborg.Interface;

import java.util.ArrayList;

public interface OnParseCompleted {

    void OnParsed(ArrayList<?> data);
    void OnParseFailed(String err);
}
