package org.tomatosoup;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class Word {
    private int value;
    private final char[] chars;


}
